pipeline {
    agent any

    environment {
        GIT_URL = 'https://github.com/zinsile/skala-stock-server.git'
        GIT_BRANCH = 'v4' // 또는 master
        GIT_ID = 'skala-stock-server' // GitHub PAT credential ID
        IMAGE_REGISTRY = 'amdp-registry.skala-ai.com/skala25a'
        IMAGE_NAME = 'sk056-skala-stock-server'
        IMAGE_TAG = '2.0.0'
        DOCKER_CREDENTIAL_ID = 'skala-image-registry-id'  // Harbor 인증 정보 ID
    }

    stages {
        stage('Clone Repository') {
            steps {
                git branch: "${GIT_BRANCH}",
                    url: "${GIT_URL}",
                    credentialsId: "${GIT_ID}"   // GitHub PAT credential ID
            }
        }

        stage('Build') {
            steps {
                // dir('skala-stock-server') { // gradlew 있는 경로
                sh './gradlew build -x test'
                // }
            }
        }

        stage('Docker Build & Push') {
            steps {
                script {
                    docker.withRegistry("https://${IMAGE_REGISTRY}", "${DOCKER_CREDENTIAL_ID}") {
                        def appImage = docker.build("${IMAGE_REGISTRY}/${IMAGE_NAME}:${IMAGE_TAG}", "--platform=linux/amd64 .")
                        appImage.push()
                    }
                }
            }
        }

        stage('Deploy to Kubernetes') {
            steps {
                sh '''
                    kubectl apply -f ./k8s
                    kubectl rollout status deployment/sk056-skala-stock-server 
                '''
            }
        }
    }
}

