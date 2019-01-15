#!/bin/bash

docker run --name mongo-auth \
    -p 27017:27017 \
    -v "/data/mongo-auth:/data/db"
    -d \
    mongo:4.1.6


