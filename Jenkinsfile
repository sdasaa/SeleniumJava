/*
    This pipeline will do the following:
    1. Run this job on the node 'windowsLaptop' which has java, maven & docker installed.
    2. Code is downloaded to node from gitHub, we can view this using mapped volume ./volumes/slave
    3. Stage1 - Compile the code using 'mvn clean package -DskipTests'
    4. Stage2 - Build a docker image out of it as described in the Dockerfile of the repo
    5. Stage3 - Login to the users Dockerhub account using the 'Jenking credentials' --> username/generated access token
              - Then push the built image once login succeeds
    6. Post Build - Logouts of the Dockerhub account

*/

pipeline{

    agent{
        label 'windows'
    }

    stages{

        stage('building jar'){
            steps{
                echo " Building the project jar "
                bat "mvn clean package -DskipTests"
            }
        }

        stage('Building Docker Image'){
            steps{
                echo " Building Docker Image "
                bat "docker build -t sdasa/selenium-docker:latest ."
            }
        }

        stage('Pushing Image to DockerHub with Credentials'){
            environment{
                DOCKER_CRED = credentials('DockerHubPersonalToken')
            }
            steps{
                echo " Entering credentials to login to Dockerhub "
                // Unsecure way
                // bat "docker login -u ${DOCKER_CRED_USR} -p ${DOCKER_CRED_PSW}"
                // Secure way
                // ****************** NOTE:Using single quotes here, since the cmd needs to be passed as is and not expanded version *****************
                bat "echo ${DOCKER_CRED_PSW} | docker login -u ${DOCKER_CRED_USR} --password-stdin"
                bat "docker push sdasa/selenium-docker:latest"
            }

        }
    }

    post{
        always{
            bat "docker logout"
        }
    }
}