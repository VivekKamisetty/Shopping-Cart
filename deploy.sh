#!/bin/bash

set -e

ECR_URL="785761195285.dkr.ecr.us-east-2.amazonaws.com/shopping-app-backend"
IMAGE_TAG="latest"
DEPLOYMENT_NAME="shopping-app"
CONTAINER_NAME="shopping-app"
NAMESPACE="default"

./mvnw clean package -DskipTests

echo "Building Docker image"
docker build -t ${CONTAINER_NAME} .
docker tag ${CONTAINER_NAME}:latest ${ECR_URL}:${IMAGE_TAG}


aws ecr get-login-password --region us-east-2 | docker login --username AWS --password-stdin ${ECR_URL}
docker push ${ECR_URL}:${IMAGE_TAG}

kubectl set image deployment/${DEPLOYMENT_NAME} ${CONTAINER_NAME}=${ECR_URL}:${IMAGE_TAG} -n ${NAMESPACE}

kubectl rollout status deployment/${DEPLOYMENT_NAME}


