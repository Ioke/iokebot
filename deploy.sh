#!/bin/sh

ant
cd ../tmp/iokebot
rm -rf *
jar xf ../../iokebot/iokebot.war
~/Desktop/Stuff/GAE/appengine-java-sdk/bin/appcfg.sh update .

