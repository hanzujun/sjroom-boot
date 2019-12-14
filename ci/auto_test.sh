#!/bin/bash

COMMITTER=$(git log -1 --format=%cE)
echo ${COMMITTER}

mvn clean package

if [ $? -eq 0 ]; then
     echo "do something for auto_test here."
     echo "auto_test over."
fi
