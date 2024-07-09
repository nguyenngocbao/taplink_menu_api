pipeline {
    agent any
    tools {
        maven 'maven'  // Sử dụng Maven mặc định
        jdk 'JDK 17'    // Sử dụng JDK mặc định
    }
    stages {
        stage('Checkout') {
            steps {
                git 'https://github.com/nguyenngocbao/taplink_menu_api.git'
            }
        }
        stage('Check Java Version') {
                    steps {
                        sh 'java -version'
                    }
                }
        stage('Build') {
            steps {
                sh 'mvn clean package'
            }
        }
    }
}