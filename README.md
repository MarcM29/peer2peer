# peer2peer

Authors: Marc McCombe & Jurgen Beu

A peer to peer file sharing app with distributed directories

-Goal was to create a service that allowed clients to upload and download specific files to other peers using the service. By utilizing distributed directories that simply store clients addresses and the names of their available files and act as traffic cops by helping direct a user to the correct peer that possesses their desired file by using UDP requests/responses. Once the user with the desired file is located, TCP connection is established between users to initiate file transfer. Clients have 2 programs (client and server .java) Client.java acts as the program for requesting and receiving files while Server.java acts as the program for accepting incoming requests from other peers for specific files the client possesses.

-Completed from scratch using java socket programming, hash tables, routing, dynamic IP addresses, UDP requests and responses and file transfer along a TCP connection
