# PuBON
**Pu**sh **B**ased **O**bject **N**otification. A Java framework, specific to Spring 5+ Reactive Framework, for interfacing with [PubonJs](https://github.com/psotos/pubonjs), in order to deliver a full stack reactive platform similar to Google's Firestore.

# goal
The goal of this library is to create a free, full stack reactive platform for producing object change notifications when they occur on the Spring 5 back end, and distributing those changes to clients on the front end.

# technology
Spring 5 Reactive via our PuBON Spring library will send change notifications, which contain the updated document or collection along with some metadata via HTTP-2 push notitifications.
