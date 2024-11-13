#!/bin/bash

# Define static client ID and client secret
CLIENT_ID="gateway-client"
CLIENT_SECRET="very-safe-secret"

# Start Keycloak in the background
/opt/keycloak/bin/kc.sh start-dev &

# Wait for Keycloak to start
sleep 30

# Login to Keycloak Admin CLI
/opt/keycloak/bin/kcadm.sh config credentials --server http://localhost:8180 --realm master --user admin --password admin

# Create Realm
/opt/keycloak/bin/kcadm.sh create realms -s realm=bowlingrealm -s enabled=true

# Create Client with static client ID and secret
/opt/keycloak/bin/kcadm.sh create clients -r bowlingrealm -s clientId=$CLIENT_ID -s enabled=true -s protocol=openid-connect \
  -s 'redirectUris=["*"]' -s clientAuthenticatorType=client-secret -s secret=$CLIENT_SECRET -s publicClient=false -s serviceAccountsEnabled=true

# Retrieve the UUID of the client by filtering the output for the client ID
CLIENT_UUID=$(/opt/keycloak/bin/kcadm.sh get clients -r bowlingrealm --fields id,clientId | grep -B 1 "\"$CLIENT_ID\"" | grep '"id"' | sed 's/.*"id" : "\(.*\)".*/\1/')

# Set the client secret using the UUID
/opt/keycloak/bin/kcadm.sh update clients/$CLIENT_UUID -r bowlingrealm -s clientAuthenticatorType=client-secret -s secret=$CLIENT_SECRET

# Create Bryan User
/opt/keycloak/bin/kcadm.sh create users -r bowlingrealm -s username=bryan -s enabled=true
/opt/keycloak/bin/kcadm.sh set-password -r bowlingrealm --username bryan --new-password bryan123

# Create Dennis User
/opt/keycloak/bin/kcadm.sh create users -r bowlingrealm -s username=dennis -s enabled=true
/opt/keycloak/bin/kcadm.sh set-password -r bowlingrealm --username dennis --new-password dennis123

# Wait for Keycloak to be fully ready
wait
