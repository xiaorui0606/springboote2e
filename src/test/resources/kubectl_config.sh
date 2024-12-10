#!/bin/bash

echo "kube_config setup started"
# Get the namespace and prefix argument
namespace=$1
export LOGGING_FOLDER=$2
startTime=$3
endTime=$4
#prefix="ivr"
shift

echo "-------Installing azure CLI-------"
curl -sL https://aka.ms/InstallAzureCLIDeb | sudo bash
echo "------Installing kubectl and kubelogin --------"
sudo az aks install-cli

echo "-----az login with uid of AKS cluster------"
AZURE_CLOUD_NAME="AzureCloud"
AKS_RG_NAME="corp-cd1-aks-rg"
AKS_NAME="corp-cd1-k8s"
AKS_SUBSCRIPTION="3fda682c-486b-43db-aa36-bd5b2411aebc"
az cloud set --name "${AZURE_CLOUD_NAME}"
# az login "${AZ_LOGIN_PARAMETERS[@]}"
az login --identity --username /subscriptions/e1045f52-8d86-4551-b69b-a9038e7c15a6/resourceGroups/rg-eu-ent-shd-cc/providers/Microsoft.ManagedIdentity/userAssignedIdentities/uai-eu-ent-shd-non-prod-cc
az aks get-credentials -g "${AKS_RG_NAME}" -n "${AKS_NAME}" --subscription "${AKS_SUBSCRIPTION}" --overwrite
export KUBECONFIG="/root/.kube/config"
kubelogin convert-kubeconfig -l azurecli
kubectl version

# Executing the actual script to print pods and images
echo "Executing the actual script to print pods and images"
sudo chmod 755 "$WORKING_DIR"/src/test/resources/pods_images_script.sh
/"$WORKING_DIR"/src/test/resources/pods_images_script.sh $namespace $LOGGING_FOLDER

# Logs Collection scripts for all pods for a specified time range
sudo chmod 755 "$WORKING_DIR"/src/test/resources/pods_logs_collector.sh
/"$WORKING_DIR"/src/test/resources/pods_logs_collector.sh $namespace $startTime $endTime $LOGGING_FOLDER