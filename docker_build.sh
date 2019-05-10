#!/usr/bin/env bash

WORK_DIR="$( pwd )"
DOCKER_WORKING_DIR="$WORK_DIR/target/docker"
DOCKERFILE_DIR="$WORK_DIR/docker"
AGENT_FILE="$WORK_DIR/perses-agent.jar"
CONTROLLER_FILE="$WORK_DIR/Controller/target/perses-injector.jar"
DOCKER_IMAGE=perses

rm -rf $DOCKER_WORKING_DIR
mkdir -p $DOCKER_WORKING_DIR
cp -R $DOCKERFILE_DIR/* $DOCKER_WORKING_DIR/.
cp -p $AGENT_FILE $DOCKER_WORKING_DIR/perses-agent..jar
cp -p $CONTROLLER_FILE $DOCKER_WORKING_DIR/perses-injector.jar
#cp -p $DOCKERFILE_DIR/run-procurement-service.sh $DOCKER_WORKING_DIR/run

docker build -t $DOCKER_IMAGE $DOCKER_WORKING_DIR