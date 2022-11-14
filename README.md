# PassAway

_logo here_

_video demo here_

## Introduction

_Intro to be written here_

## Requirements

Operating Systems: Windows 64-Bit, MacOS 64-Bit (X86_64, Arm64), Linux 64-Bit (X86_64, Arm64)

Dependencies:
-  Java/OpenJDK: >=18.0.0.0 recommended
-  NodeJS: >=18.0.0 recommended
-  NPM: >=8.11.0 recommended (Alternative: Yarn)
-  Docker: >=20.10.0 recommended

Developed on:
-  MacOS 12.4 (Arm64), OpenJDK v18.0.1.1, NodeJS v18.3.0, NPM v8.11.0, Docker v20.10.11

## Instructions for Setup & Deployment

1.  When cloning the repo for the first time, change directory to the `record-ui` folder and run the command `npm install` once. 

2.  Return back to the repo's root directory and ensure that Docker Engine is installed and running. 

3.  Obtain the `.env` files for both the front-end and back-end, and place them in the root folder of each component respectively. 

4.  Configure the Environment Variable MYSQL_PASSWORD to the desired pre-deployment password (i.e. password just for compilation, not real deployment; should default to `root`; no change needed in development) and ensure that the `.env` file in `/record` matches that password. 

5.  Run the command `sh ./prepare.sh` (Unix-like OS) or `.\prepare.bat` (Windows OS) and comply with the prompt's instructions. Use the appropriate SQL files from the SQL folder. 

6.  Change the environment variable MYSQL_PASSWORD and the `.env` file in `/record` to the desired deployment password (no change needed for development). 

7.  Run the command `sh ./run.sh` (Unix-like OS) or `.\run.bat` (Windows OS) and comply with the prompt's instructions. Use the appropriate SQL files from the SQL folder. The SQL files used should be the same as before. 

8.  The program should be up and running. To shut down the program and clean up any newly created files, images, etc., type `Ctrl-C` to exit the CLI program, and run the command `sh ./shutdown.sh` (Unix-like OS) or `.\shutdown.bat` (Windows OS). 

The application can also be started without a Docker context by starting each part of the application manually, starting with the MySQL database server first when building and running the application. Refer to the README of each service for more information. 

## Results

The app should be accessible at [http://localhost/](http://localhost/) with the back-end itself accessible at [http://localhost:8000/](http://localhost:8000/). 

## Tested by

_Release testers listed here_

## Project Structure

This section will describe the representation of each of the folders or files in the repo.

```
.
├── record
│   └── <source-codes>
├── record-ui
│   └── <source-codes>
├── sql
│   └── <source-codes>
├── .gitignore
├── docker-compose.yml
├── prepare.bat
├── prepare.sh
├── README.md
├── run.bat
├── run.sh
├── shutdown.bat
└── shutdown.sh
```

### `/record`
This folder contains all the source code for the Java Spring Boot back-end service, with the dockerfile included. This back-end service acts as a REST service for the VueJS front-end.The app is then built into a JAR file and loaded into a container. 

### `/record-ui`
This folder contains all the source code for the VueJS front-end SPA service., with the dockerfile included. This service communicates with the Java back-end service to dynamically present the web app. The app is built into a single HTML file which is then served statically using an ExpressJS-powered server in a container. 

### `/sql`
This folder contains all the starter SQL files to configure the Relational Database. The database used is in a container spun up by Docker. 

### `/prepare.bat`/`/prepare.sh`
This file helps compile, prepare and build the different services into their respective Docker images. Ensure that the MYSQL_PASSWORD is set to pre-deployment password (defaults to `root`). The program this file executes will prompt for user response when user action is needed. This program requires the user to setup the database using the given SQL files. 

### `/run.bat`/`/run.sh`
This file helps run the different services in their respective Docker containers using Docker Compose. Before this program executes, the password in the `.env` file of the back-end service and the MYSQL_PASSWORD environment variable has to be modified to the deployment password. The program this file executes will prompt for user response when user action is needed. This program requires the user to setup the database using the given SQL files. 

### `/shutdown.bat`/`/shutdown.sh`
This file helps shutdown and remove any containers and images produced by setting up and running this whole application.  

## Attributions

_Attributions of copyrights here_
