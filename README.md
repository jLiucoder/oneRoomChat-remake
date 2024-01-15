<h1>One Room Chat App</h1>
This project aims to develop a real-time multi-user chat application with a single chat room that allows users to communicate with each other. The application will have a React-based front-end, a Java Spring Boot back-end with a PostgreSQL database and Kafka as streams processing component, and an added layer of Redis cache to store the messages

This project was completed as a part of my NYU Computer Engineering graduate course - CS9053 : Introduction to Java by Dr. Constantine (Dean) Christakos

I was responsible mainly for the backend development for the course project, after the class ended, I redid the frontend and added some more features to refine the project.

<h2>Features</h2>

* User registration and authentication
* Dashboard for multiple users to log into a single chat room
* Send and receive messages in real-time in the chat room using web sockets
* PostgreSQL database to store user details and chat messages
* Kafka for queueing messages and new registered users for storing new Users / new messages in the database
* Redis for caching retrieved messages to avoid unnecessary requests to the database
* Chat searching feature across all the messages in the chat room
* React front-end with robust UI/UX with Redux toolkit as the state management tool
* Spring Boot REST APIs for front end to interact with the backend

<h2>Tech Stack</h2>

* Java Spring Boot
* React, Redux and Tailwind CSS
* PostgreSQL
* Kafka
* Redis
* Docker

The application follows a layered architecture with the following components:

Frontend: React frontend with login, signup, and chat dashboard pages. Chat dashboard uses web sockets for real-time communication.

Backend: Java Spring Boot application with REST APIs and web socket server. The first spring server acts as producer for Kafka and connects to DB for fetch operations. The second backend server is also a Java Spring Boot app and will act as a consumer to consume from the kafka stream. The main purpose of this server is to perform CRUD operation in the database.

Kafka: Message queue for reliability and scalability.

Redis: Caching service for messages.
Database: PostgreSQL database to store user and chat data.

<h2>Architecture</h2>
<img src="https://github.com/jLiucoder/oneRoomChat-remake/blob/main/Architecture.png" />

<h3>Prerequisites</h3>
  
  * Intellij Idea
    
  * Docker
    
  * Docker compose

<h2>Steps to run</h2>

  
1. clone the repository
2. go to root level of the project and run docker compose to start Kafka, Zookeeper, Redis and Postgres services
  * <code>docker compose up</code>
3. Open Intellij Idea and start the two Spring boot projects, as the first and the second server showing above in the diagram
4. go to chat client and start the frontend service
  * <code>npm install</code>
  * <code>npm start</code>
5. go to localhost:3000 and start using the App by creating a new user!
