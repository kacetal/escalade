## Escalade  
Username: user  
Password: user

## DB
1. cd docker/
2. docker-compose up
3. SQL script with test data 'escalade_public_comment.sql'

## Deploy
1. git clone https://github.com/kacetal/escalade.git
2. bash mvnw package
3. cd target/
4. java -jar escalade-1.0.0.jar

## System
#### Java
openjdk version "12" 2019-03-19  
OpenJDK Runtime Environment (build 12+32)  
OpenJDK 64-Bit Server VM (build 12+32, mixed mode, sharing)  
#### Maven
Apache Maven 3.6.0    
#### Docker-Compose
docker-compose version 1.18.0, build 8dd22a9  
