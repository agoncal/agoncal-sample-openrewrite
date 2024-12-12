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
REDIS_NAME="redis-$UNIQ_KEY"

echo "Creating the resource group..."
echo "------------------------------"
az group create \
  --name "$RESOURCE_GROUP" \
  --location "$LOCATION" \
  --tags system="$TAG"

echo "Creating the Redis instance..."
echo "------------------------------"
az redis create \
  --resource-group "$RESOURCE_GROUP" \
  --location "$LOCATION" \
  --name $REDIS_NAME \
  --sku Basic \
  --vm-size c0

echo "Retrieving the connection string for Redis..."
echo "------------------------------"
echo REDIS_CONNECTION_STRING="$REDIS_NAME.redis.cache.windows.net"
