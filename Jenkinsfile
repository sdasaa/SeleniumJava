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
        label 'windowsLaptop'
    }

    stages{

        stage1('compile & build Jar'){
            steps{
                echo " This stage will compile and build the project "
                bat "mvn clean package -DskipTests"
            }
        }

        stage2('Build Docker image'){
            steps{
                echo " This stage will build a docker image from the Dockerfile "
                bat "docker build -t sdasa/selenium-docker:latest ."
            }
        }

        stage3('Push image to DockerHub'){
            steps{
                echo " This stage will push the Docker image to DockerHub "
                bat "docker push sdasa/selenium-docker"
            }

        }
    }
}