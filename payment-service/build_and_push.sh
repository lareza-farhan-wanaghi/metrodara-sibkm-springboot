#!/bin/sh

bash mvnw clean install
docker build -t 890890123890/payment-service .
docker push 890890123890/payment-service