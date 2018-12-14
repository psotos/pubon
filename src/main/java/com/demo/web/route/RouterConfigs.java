package com.demo.web.route;

import com.demo.web.handler.SseHandler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.config.EnableWebFlux;
import org.springframework.web.reactive.function.server.*;


/**
 * Configures the web routes.
 * 
 * @author Niranjan Nanda
 */
@Configuration
@EnableWebFlux
public class RouterConfigs {
	
	private static final String HEALTH_CHECK = "/health/check";
	private static final String EVENTS_URL = "/events";
	
	@Autowired
	private SseHandler sseHandler;
	
	@Bean
	public RouterFunction<ServerResponse> routes() {
		return RouterFunctions
			.route(RequestPredicates.GET(HEALTH_CHECK), request -> ServerResponse.ok().build())
			.andNest(emptyPathPredicate(), nestedRoutes())
			;
	}
	
	private RequestPredicate emptyPathPredicate() {
		return RequestPredicates.path("");
	}
	
	private RouterFunction<ServerResponse> nestedRoutes() {
		return RouterFunctions
				.route(RequestPredicates.GET(EVENTS_URL), sseHandler::streamSSE)
				;
	}
	
	
	/*
	 * @Configuration
public class RouterConfig {

    @Bean
    public RouterFunction<ServerResponse> routerFunction() {
        return RouterFunctions.route().GET("/events", request -> {
            Flux<User> body = Flux.interval(Duration.ofMillis(300)).map(id -> new User(id, "Niranjan"));
            return ServerResponse.ok().contentType(MediaType.TEXT_EVENT_STREAM)
                    .body(body, User.class);
        }).build();
    }
}
	 */
}
