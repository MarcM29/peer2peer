# *This was a school project from 2020*

# Peer 2 peer shared photo service

Authors: Marc McCombe & Jurgen Beu

A peer to peer file sharing app with distributed directories

-Goal was to create a service that allowed clients to upload and download specific files to other peers using the service. By utilizing distributed directories that simply store clients addresses and the names of their available files, these directories simply help direct a user to the correct peer that possesses their desired file by using UDP requests/responses. Once the user with the desired file is located, TCP connection is established between users to initiate file transfer. Clients have 2 programs (client and server .java) Client.java acts as the program for requesting and receiving files while Server.java acts as the program for accepting incoming requests from other peers for specific files the client possesses.

-IPs/Ports of directories are included in program (these were always on machines that were used as the directories), any peer that joined/left the p2p network would have their ip/port dynamically collected and stored for use.

-Completed from scratch using java socket programming, hash tables, routing, dynamic IP addresses, UDP requests and responses and file transfer along a TCP connection
