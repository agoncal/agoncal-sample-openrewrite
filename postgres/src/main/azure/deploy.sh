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
# Postgres
POSTGRES_DB="db-${PROJECT}"
POSTGRES_DB_SCHEMA="${PROJECT}"
POSTGRES_DB_ADMIN="db${PROJECT}admin"
POSTGRES_DB_PWD="db-${PROJECT}-p#ssw0rd-12046"
POSTGRES_DB_VERSION="16"
POSTGRES_SKU="Standard_B1ms"
POSTGRES_TIER="Burstable"
POSTGRES_CONNECT_STRING="jdbc:postgresql://${POSTGRES_DB}.postgres.database.azure.com:5432/${POSTGRES_DB_SCHEMA}?ssl=true&sslmode=require"

echo "Creating the resource group..."
echo "------------------------------"
az group create \
  --name "$RESOURCE_GROUP" \
  --location "$LOCATION" \
  --tags system="$TAG"

echo "Creating a Postgres Flexible Server..."
echo "------------------------------"
az postgres flexible-server create \
  --resource-group "$RESOURCE_GROUP" \
  --location "$LOCATION" \
  --tags system="$TAG" \
  --name "$POSTGRES_DB" \
  --admin-user "$POSTGRES_DB_ADMIN" \
  --admin-password "$POSTGRES_DB_PWD" \
  --public all \
  --tier "$POSTGRES_TIER" \
  --sku-name "$POSTGRES_SKU" \
  --storage-size 32 \
  --version "$POSTGRES_DB_VERSION"

echo "Creating the Postgres Database Schema..."
echo "------------------------------"
az postgres flexible-server db create \
  --resource-group "$RESOURCE_GROUP" \
  --server-name "$POSTGRES_DB" \
  --database-name "$POSTGRES_DB_SCHEMA"


echo "Retrieving the DB connection string..."
echo "------------------------------"

echo POSTGRES_CONNECT_STRING="$POSTGRES_CONNECT_STRING"
