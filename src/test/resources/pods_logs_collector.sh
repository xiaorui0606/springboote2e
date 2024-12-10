#!/bin/bash

namespace=$1
startTime=$2
endTime=$3
export LOGGING_FOLDER=$4
prefix1="ivr"
#prefix2="onecall"
shift

echo "---------------Logs collection starts from here------------"

# Get the list of pod names in the namespace
#$podNames = kubectl get pods -n $namespace --output=jsonpath='{.items[*].metadata.name}'
#podNames=$(kubectl get pods -n $namespace | grep -E "^$prefix1|^$prefix2" | awk '{print $1}')
podNames=$(kubectl get pods -n $namespace | grep "^$prefix1" | awk '{print $1}')
  for podName in $podNames; do
    containers=$(kubectl get pods $podName -n $namespace -o jsonpath='{.spec.containers[*].name}')
      for container in $containers; do
      echo "********** Logs collection for $container of $podName starts here **********" >> "$LOGGING_FOLDER"/Pods_logs.txt
      #kubectl logs $podName -c $container -n $namespace --since-time="$startTime" | awk '$0 < "$endTime"' >> "$LOGGING_FOLDER"/"$container"-"$podName"-logs.txt
      kubectl logs $podName -c $container -n $namespace --since-time="$startTime" >> "$LOGGING_FOLDER"/Pods_logs.txt
      echo "********** Logs collection for $container of $podName ends here **********" >> "$LOGGING_FOLDER"/Pods_logs.txt
      echo "" >> "$LOGGING_FOLDER"/Pods_logs.txt
      done
  done