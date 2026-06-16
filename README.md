# currency-converter
Basic currency conversion app utilizing the Frankfurter API

The whole app can be easily started by running the following command from the root directory:  
`docker compose up -d`

### DB Start Up
`docker compose up -d`

Can be viewed at http://localhost:8090  
**Email:** test@user.com  
**Password:** password

#### DB Server Details:  
**hostname:** db  
**port:** 5432  
**user:** postgres  
**password:** password  

### Backend Start Up
Note: Must have your JAVA_HOME environment variable pointing to your Java SDK for it to work
```
export JAVA_HOME=/java/sdk/directory
cd currency-converter
./gradlew bootRun
```