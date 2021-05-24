[![Java CI with Maven](https://github.com/ishmum123/studentlog/actions/workflows/maven.yml/badge.svg)](https://github.com/ishmum123/studentlog/actions/workflows/maven.yml)

## Student Log
This project implements a student logging mechanism with the hopes of being an ideal (small-scale) spring boot structure


## Keycloak command to run
1. Go to keycloak folder
2. To start run

sudo docker build -t my-keycloak .
 
sudo docker run -dp 8000:8080 my-keycloak

3. To stop the docker image

sudo docker stop $(sudo docker ps -q --filter ancestor='my-keycloak' )

4. Create user

use 
http://localhost:8180/auth/realms/MyMarketplace/protocol/openid-connect/token 
to obtain access_token and give admin-cli client manage-users role from realm-management client in 
Service Account Roles and then call 
http://localhost:8180/auth/admin/realms/MyMarketplace/users
with access token in header and "enabled": true, "username": "user" body. make sure your enabled boolean 
look like this one and dont use "" I think keycloak reads your request as String.

