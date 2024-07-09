pipeline {
    agent any
    tools {
        maven 'Default'  // Sử dụng Maven mặc định
        jdk 'Default'    // Sử dụng JDK mặc định
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