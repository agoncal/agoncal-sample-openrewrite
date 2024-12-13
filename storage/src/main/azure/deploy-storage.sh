#!/bin/bash

# Login if not already
# az login
# az account show

echo "Setting up environment variables..."
echo "----------------------------------"
RND=$RANDOM
PROJECT="openrewrite"
UNIQ_KEY="$PROJECT-$RND"
RESOURCE_GROUP="rg-$PROJECT"
# Pick your closest region az account list-locations --output table --query "sort_by([].{Name: name, DisplayName: displayName}, &Name)"
LOCATION="swedencentral"
TAG="$PROJECT"
# Storage Account
STORAGE_ACCOUNT_NAME="st$PROJECT$RND"
SHARE_NAME="share-$UNIQ_KEY"

echo "Creating the resource group..."
echo "------------------------------"
az group create \
  --name "$RESOURCE_GROUP" \
  --location "$LOCATION" \
  --tags system="$TAG"


echo "Registering Storage..."
echo "----------------------"
az provider register \
  --namespace "Microsoft.Storage"
  

echo "Creating a Storage Account..."
echo "------------------------------"
az storage account create \
  --resource-group $RESOURCE_GROUP \
  --location "$LOCATION" \
  --name $STORAGE_ACCOUNT_NAME \
  --tags system="$TAG"


echo "Retrieving the DB connection string..."
echo "------------------------------"
STORAGE_ACCOUNT_CONNECTION_STRING=$(az storage account show-connection-string --resource-group $RESOURCE_GROUP --name $STORAGE_ACCOUNT_NAME --output tsv)

echo STORAGE_ACCOUNT_CONNECTION_STRING="$STORAGE_ACCOUNT_CONNECTION_STRING"

echo "Creating a Share Storage..."
echo "------------------------------"
az storage share create \
  --name $SHARE_NAME \
  --connection-string $STORAGE_ACCOUNT_CONNECTION_STRING


echo "Uploading files..."
echo "------------------"
az storage file upload \
  --share-name $SHARE_NAME \
  --source storage/src/main/resources/bio-duke-ellington.txt \
  --connection-string $STORAGE_ACCOUNT_CONNECTION_STRING

az storage file upload \
  --share-name $SHARE_NAME \
  --source storage/src/main/resources/bio-ella-fitzgerald.txt \
  --connection-string $STORAGE_ACCOUNT_CONNECTION_STRING

az storage file upload \
  --share-name $SHARE_NAME \
  --source storage/src/main/resources/bio-louis-armstrong.txt \
  --connection-string $STORAGE_ACCOUNT_CONNECTION_STRING


echo "Listing files..."
echo "----------------"
az storage file list \
  --share-name $SHARE_NAME \
  --connection-string $STORAGE_ACCOUNT_CONNECTION_STRING \
  --output table