/*
    This pipeline will do the following:
    1. Run this job on the node 'windowsLaptop' which has java, maven & docker installed.
    2. Code is downloaded to node from gitHub, we can view this using mapped volume ./volumes/slave
    3. Stage1 - Compile the code using 'mvn clean package -DskipTests'
    4. Stage2 - Build a docker image out of it as described in the Dockerfile of the repo
    5. Stage3 - Login to the users Dockerhub account using the 'Jenkins credentials' --> username/generated access token
              - Then push the built image once login succeeds
    6. Post Build - Logouts of the Dockerhub account

*/

pipeline{

    // Here we are assigning a Jenkins node labelled docker as default for all the stages.
    agent{
        label 'docker'
    }

    // Defining static variables inside the env block
    environment{
        IMAGE_NAME='sdasa/selenium-docker'
        IMAGE_TAG_LATEST='latest'
        DOCKER_HUB_CREDS= credentials('DockerHubPersonalToken')
    }

    stages{

        stage('Building project jar'){
            steps{
                echo "Compiling project and Building jar"
                sh "mvn clean package -DskipTests"
            }
        }

        stage('Building Docker Image'){
            steps{
                echo "Building Docker Image"
                sh "docker build -t ${IMAGE_NAME}:${IMAGE_TAG_LATEST} ."
            }
        }

        stage('Pushing Image to DockerHub with Credentials') {
            steps {
                script {
                    echo "Logging out of Docker if already logged in"
                    sh "docker logout"

                    echo "Logging in to DockerHub with secure credentials"
                    sh 'echo ${DOCKER_HUB_CREDS_PSW} | docker login -u ${DOCKER_HUB_CREDS_USR} --password-stdin'

                    echo "Pusing the Image - ${IMAGE_NAME}"
                    sh "docker push ${IMAGE_NAME}:${IMAGE_TAG}"

                    // TBD:Implementation for custom tag
                    }
                }
            }
        }

    post{
        success{
            echo "Building and Pushing of Image successful, Logging out of Docker"
            sh "docker logout"
        }
        failure{
            echo "Job FAILED !!"
        }
        always{
            echo "Running Always block"
        }
    }
}