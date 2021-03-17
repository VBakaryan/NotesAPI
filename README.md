# User Notes Application
## Overview
* The user notes application consists of three microservices (***apigateway*** , ***usernotes***  and ***etl*** )
* 10 test users and 5000 notes will be automatically populated during the ***usernotes***  microservice runtime
* ETL job is being triggered via scheduled cron job, where the date is specified in ***application.yml*** file of mentioned microservice (currently it set to be at 12pm of each day)

## Setup:
* Install JAVA 11
* `git clone https://github.com/VBakaryan/NotesAPI.git`
* move to each microsercice directory and run`mvn clean install` to install the project dependencies
* run `mvn spring-boot:run` in same directory for running the microservice

## Run by docker
* run `docker-compose up` (builds and runs docker containers for microservices)
* run `docker-compose down` (stops docker containers)

## Test endpoints
* use one of test users (ex.`test_1@test.com`) for getting oauth2 token (the cURL command included in ***/documentation/curl*** file)
* use access token to test other endpoints included in ***/documentation/curl*** file

## Test ETL
* On the cron job execution time the last updated data about user and notes are being read from db, converted to json and parquet formats, and uploaded to S3 bucket


