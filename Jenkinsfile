pipeline {
    agent any

    environment {
        REMOTE_USER = 'jenkins'
        REMOTE_HOST = '192.168.35.110'
        REMOTE_PATH = '/home/jenkins/aptzip'
        APP_NAME = 'springboot-app.jar'
    }

    stages {
        stage('Git Clone') {
            steps {
                git branch: 'deploy', url: 'https://github.com/Mt-Ugh/apt.zip_be.git'
            }
        }

        stage('Build JAR') {
            steps {
                sh './mvnw clean package -DskipTests'
            }
        }

        stage('Send JAR & Dockerfile') {
            steps {
                sh """
                    ssh ${REMOTE_USER}@${REMOTE_HOST} 'mkdir -p ${REMOTE_PATH}'
                    scp target/*.jar ${REMOTE_USER}@${REMOTE_HOST}:${REMOTE_PATH}/${APP_NAME}
                    scp Dockerfile ${REMOTE_USER}@${REMOTE_HOST}:${REMOTE_PATH}/Dockerfile
                """
            }
        }

        stage('Download application.properties from MinIO') {
            steps {
                withCredentials([usernamePassword(credentialsId: 'minio-cred', usernameVariable: 'MINIO_USER', passwordVariable: 'MINIO_PASS')]) {
                    sh """
                        mc alias set aptminio http://192.168.35.120:9000 \$MINIO_USER \$MINIO_PASS
                        mc cp aptminio/aptzip/configs/application.properties ./application.properties
                        ssh ${REMOTE_USER}@${REMOTE_HOST} 'mkdir -p ${REMOTE_PATH}/src/main/resources'
                        scp application.properties ${REMOTE_USER}@${REMOTE_HOST}:${REMOTE_PATH}/src/main/resources/application.properties
                    """
                }
            }
        }

        stage('Remote Docker Build & Run') {
            steps {
                sh """
                    ssh ${REMOTE_USER}@${REMOTE_HOST} '
                        cd ${REMOTE_PATH} &&
                        docker stop aptzip || true &&
                        docker rm aptzip || true &&
                        docker build -t aptzip-app . &&
                        docker run -d --name aptzip -p 8080:8080 aptzip-app
                    '
                """
            }
        }
    }
}
