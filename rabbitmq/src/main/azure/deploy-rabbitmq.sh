#!/bin/bash

# Login if not already
# az login
# az account show

echo "Setting up environment variables..."
echo "----------------------------------"
PROJECT="openrewrite"
UNIQ_KEY="$PROJECT-$RANDOM"
RESOURCE_GROUP="rg-$PROJECT"
# Pick your closest region az account list-locations --output table --query "sort_by([].{Name: name, DisplayName: displayName}, &Name)"
LOCATION="swedencentral"
TAG="$PROJECT"
# Azure Service Bus
SERVICE_BUS_NAMESPACE="sb-$UNIQ_KEY"

echo "Creating the resource group..."
echo "------------------------------"
az group create \
  --name "$RESOURCE_GROUP" \
  --location "$LOCATION" \
  --tags system="$TAG"

echo "Creating the Service Bus namespace..."
echo "------------------------------"
az servicebus namespace create \
  --resource-group "$RESOURCE_GROUP" \
  --location "$LOCATION" \
  --name "$SERVICE_BUS_NAMESPACE" \
  --sku Premium

echo "Creating the Service Bus queues..."
echo "------------------------------"
az servicebus queue create \
  --resource-group "$RESOURCE_GROUP" \
  --namespace-name "$SERVICE_BUS_NAMESPACE" \
  --name foo
az servicebus queue create \
  --resource-group "$RESOURCE_GROUP" \
  --namespace-name "$SERVICE_BUS_NAMESPACE" \
  --name bar

# 
echo "Retrieving the connection string for the Service Bus namespace..."
echo "------------------------------"
SERVICE_BUS_QUEUE_CONNECTION_STRING=$(
  az servicebus namespace authorization-rule keys list \
    --resource-group "$RESOURCE_GROUP" --namespace-name "$SERVICE_BUS_NAMESPACE" --name RootManageSharedAccessKey \
    --query primaryConnectionString --output tsv
  )

echo SERVICE_BUS_QUEUE_CONNECTION_STRING="$SERVICE_BUS_QUEUE_CONNECTION_STRING"
