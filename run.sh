#!/bin/bash
docker build -t dddschach .
docker run -it --rm -p 8080:8080 dddschach