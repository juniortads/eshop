pipeline {
  environment {
    registry = "https://451393511481.dkr.ecr.sa-east-1.amazonaws.com"
    registryCredential = "ecr:sa-east-1:ecr-id"
  }
  agent any
  stages {
    stage('Cloning Git') {
      steps {
        git(
            url: 'https://github.com/juniortads/eshop.git',
            credentialsId: 'eshop_gilmarsilva',
            branch: "master"
        )
      }
    }
    stage('Building image') {
      steps {
        script{
            sh "docker images ls"
            sh 'docker-compose -f ./docker-compose_aws.yml build'
        }
      }
    }
    stage('Deploy image to ECR') {
      steps {
        script{
                docker.withRegistry(registry, registryCredential)
                {
                    docker.image('repo-docker/identity').push(env.BUILD_NUMBER)
                    //docker.image('repo-docker/basket').push(env.BUILD_NUMBER)
                    //docker.image('repo-docker/catalog').push(env.BUILD_NUMBER)
                    //docker.image('repo-docker/ordering').push(env.BUILD_NUMBER)
                    //docker.image('repo-docker/orderingbackgroundtasks').push(env.BUILD_NUMBER)
                    //docker.image('repo-docker/marketing').push(env.BUILD_NUMBER)
                    //docker.image('repo-docker/payment').push(env.BUILD_NUMBER)
                    //docker.image('repo-docker/locations').push(env.BUILD_NUMBER)
                    //docker.image('repo-docker/webhooks').push(env.BUILD_NUMBER)
                    //docker.image('repo-docker/orderingsignalrhub').push(env.BUILD_NUMBER)
                    //docker.image('repo-docker/webstatus').push(env.BUILD_NUMBER)
                    //docker.image('repo-docker/webspa').push(env.BUILD_NUMBER)
                    //docker.image('repo-docker/webmvc').push(env.BUILD_NUMBER)
                    //docker.image('repo-docker/webhooksclient').push(env.BUILD_NUMBER)
                }
        }
      }
    }
  }
  post { 
        always { 
             sh "docker image prune -a --force"
        }
    }
}