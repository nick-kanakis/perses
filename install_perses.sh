#!/usr/bin/env bash

echo '*****************************************'
echo '     Perses agent installation script'
echo '*****************************************'

echo ''
echo '1. Checking if wget is installed...'
if ! [ -x "$(command -v wget)" ]; then
    echo '--> wget is NOT installed, lets install it...'
    echo 'Executing apt-get update'
    apt-get update

    echo 'Executing apt-get install wget'
    apt-get install wget

    if ! [ -x "$(command -v wget)" ]; then
        echo 'Failed to install wget (!), exiting script'
        exit 1
    fi
    echo 'wget is now installed!'
else
    echo '--> wget is already installed!'
fi
echo ''

JAR_DIRECTORY='perses'
echo "2. Creating a jar directory AT ${JAR_DIRECTORY}"
mkdir -p ${JAR_DIRECTORY}
echo ''

echo '3. Downloading the perses-agent.jar'
wget -O ${JAR_DIRECTORY}/perses-agent.jar https://github.com/nicolasmanic/perses/releases/download/0.0.0/perses-agent.jar
echo ''

echo '4. Downloading the perses-injector.jar'
wget -O ${JAR_DIRECTORY}/perses-injector.jar https://github.com/nicolasmanic/perses/releases/download/0.0.0/perses-injector-jar-with-dependencies.jar
echo ''

echo '5. Making perses-injector.jar exectuable'
chmod +x ${JAR_DIRECTORY}/perses-injector.jar
echo ''

echo '6. Executing the perses-injector application'
java -jar ${JAR_DIRECTORY}/perses-injector.jar
echo ''
