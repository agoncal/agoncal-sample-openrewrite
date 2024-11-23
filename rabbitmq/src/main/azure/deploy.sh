#!/bin/bash

# Generate a random number for unique resource naming
export RNDM=$RANDOM

# Set the environment variables
export RESOURCE_GROUP=rg-antoniomanug-$RNDM
export REGION=pick_your_closest_region
export SERVICE_BUS=sb-antoniomanug-$RNDM

# Create the resource group  
az group create --name $RESOURCE_GROUP --location $REGION

# Create the Service Bus namespace
az servicebus namespace create \
  --resource-group $RESOURCE_GROUP \
  --name $SERVICE_BUS \
  --sku Premium \
  --location $REGION    

# Create the Service Bus queues  
az servicebus queue create \
  --resource-group $RESOURCE_GROUP \
  --namespace-name $SERVICE_BUS \
  --name foo
az servicebus queue create \
  --resource-group $RESOURCE_GROUP \
  --namespace-name $SERVICE_BUS \
  --name bar

# Retrieve the connection string for the Service Bus namespace
export SERVICE_BUS_QUEUE_CONNECTION_STRING=$(
  az servicebus namespace authorization-rule keys list \
    --resource-group $RESOURCE_GROUP --namespace-name $SERVICE_BUS --name RootManageSharedAccessKey \
    --query primaryConnectionString --output tsv
  )

echo SERVICE_BUS_QUEUE_CONNECTION_STRING=$SERVICE_BUS_QUEUE_CONNECTION_STRING  