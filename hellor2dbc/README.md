# R2DBC

This is to try out R2DBC, which is a way to stream to and from certain databases
as of now, only Postgresql, H2DB and MSSQL are supported.

The framework seems to be fairly simple, and does not offer any support for lazy-loading, caching or such.

I have yet to actually implement r2dbc, this is mostly a placeholder for when I actually have the time to do it.


```bash
# To run regular tests with gradle
./gradlew clean build

# To run regular tests with maven
./mvnw clean install
```
