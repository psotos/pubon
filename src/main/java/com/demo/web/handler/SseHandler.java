package com.demo.web.handler;

import java.time.Duration;
import java.time.LocalTime;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * TODO: Add a description
 * 
 * @author Niranjan Nanda
 */
@Component
public class SseHandler {
	
	public Flux<ServerSentEvent<String>> handleSSERequest(final ServerRequest request) {
		return Mono.subscriberContext()
			.flatMapMany(context -> Flux.interval(Duration.ofSeconds(1L))
					.map(sequence -> ServerSentEvent.<String>builder()
							.id(String.valueOf(sequence))
							.event("periodic-event")
							.data("SSE - " + LocalTime.now().toString())
							.build()
					)
			 );
	}
	
	public Mono<ServerResponse> streamSSE(final ServerRequest request) {
		final Flux<ServerSentEvent<String>> sseEventFlux = this.handleSSERequest(request);
		final ParameterizedTypeReference<ServerSentEvent<String>> typeRef = new ParameterizedTypeReference<ServerSentEvent<String>>() {};
		return ServerResponse
				.ok()
				.contentType(MediaType.TEXT_EVENT_STREAM)
				.body(sseEventFlux, typeRef)
				;
	}
}
