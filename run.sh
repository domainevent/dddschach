#!/bin/bash
docker build -t myapp .
docker run -it --rm -p 8080:8080 myapp