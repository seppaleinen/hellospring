# Hellospring

This is my project for trying out (mostly) spring related stuff...

Under each folder there should be a readme file, explaining a bit more about  
what it's supposed to test out!

I want there to be a gradle example of how to run each, and a maven.  
All of them should as of now work as is, and on all from jdk8 to jdk11!


Pipeline is at https://travis-ci.org/seppaleinen/hellospring

##### Commands
```bash
# To run with gradle
./gradlew clean build

# To run with maven
./mvnw clean install

# To try out in docker with different jdks
docker build .
```

For some unknown reason on my computer at home, maven seems to be quite  
a bit faster than gradle, which kind of surprised me.