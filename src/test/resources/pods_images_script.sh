#!/bin/bash

namespace="$1"
namespace_tts="ttsaas-dev"
namespace_nraas="nraas-dev"
namespace_dlg="dlgaas-dev"
namespace_nlu="nlu-nluaas-dev"
namespace_asr="58e0d5e7-7466-4a03-8809-b52c8cedefb1"
export LOGGING_FOLDER=$2
shift

echo "Printing all deployments and their images in $namespace"

deployments_cc=$(kubectl get deployments --no-headers -o custom-columns=":metadata.name" -n $namespace)
deployments_tts=$(kubectl get deployments --no-headers -o custom-columns=":metadata.name" -n $namespace_tts)
deployments_nraas=$(kubectl get deployments --no-headers -o custom-columns=":metadata.name" -n $namespace_nraas)
deployments_dlg=$(kubectl get deployments --no-headers -o custom-columns=":metadata.name" -n $namespace_dlg)
deployments_nlu=$(kubectl get deployments --no-headers -o custom-columns=":metadata.name" -n $namespace_nlu)
deployments_asr=$(kubectl get deployments --no-headers -o custom-columns=":metadata.name" -n $namespace_asr)

for deployment in $deployments_cc; do
  images=$(kubectl get deploy "$deployment" -o=jsonpath='{$.spec.template.spec.containers[:].image}' -n $namespace)

	# Print the images to the console
      echo "Images for CC deployment $deployment:"
      echo "$images"

      # Split the images into separate lines and append to the file
      echo "Images for CC deployment $deployment:" >> "$LOGGING_FOLDER"/Deployed_Images.txt
      echo "$images" | tr ' ' '\n' >> "$LOGGING_FOLDER"/Deployed_Images.txt
      echo "" >> "$LOGGING_FOLDER"/Deployed_Images.txt

done

for deployment in $deployments_tts; do
  images=$(kubectl get deploy "$deployment" -o=jsonpath='{$.spec.template.spec.containers[:].image}' -n $namespace_tts)

	# Print the images to the console
      echo "Images for TTS deployment $deployment:"
      echo "$images"

      # Split the images into separate lines and append to the file
      echo "Images for TTS deployment $deployment:" >> "$LOGGING_FOLDER"/Deployed_Images.txt
      echo "$images" | tr ' ' '\n' >> "$LOGGING_FOLDER"/Deployed_Images.txt
      echo "" >> "$LOGGING_FOLDER"/Deployed_Images.txt

done

for deployment in $deployments_nraas; do
  images=$(kubectl get deploy "$deployment" -o=jsonpath='{$.spec.template.spec.containers[:].image}' -n $namespace_nraas)

	# Print the images to the console
      echo "Images for NRaaS deployment $deployment:"
      echo "$images"

      # Split the images into separate lines and append to the file
      echo "Images for NRaas deployment $deployment:" >> "$LOGGING_FOLDER"/Deployed_Images.txt
      echo "$images" | tr ' ' '\n' >> "$LOGGING_FOLDER"/Deployed_Images.txt
      echo "" >> "$LOGGING_FOLDER"/Deployed_Images.txt

done

for deployment in $deployments_dlg; do
  images=$(kubectl get deploy "$deployment" -o=jsonpath='{$.spec.template.spec.containers[:].image}' -n $namespace_dlg)

	# Print the images to the console
      echo "Images for DLG deployment $deployment:"
      echo "$images"

      # Split the images into separate lines and append to the file
      echo "Images for DLG deployment $deployment:" >> "$LOGGING_FOLDER"/Deployed_Images.txt
      echo "$images" | tr ' ' '\n' >> "$LOGGING_FOLDER"/Deployed_Images.txt
      echo "" >> "$LOGGING_FOLDER"/Deployed_Images.txt

done

for deployment in $deployments_nlu; do
  images=$(kubectl get deploy "$deployment" -o=jsonpath='{$.spec.template.spec.containers[:].image}' -n $namespace_nlu)

	# Print the images to the console
      echo "Images for NLU deployment $deployment:"
      echo "$images"

      # Split the images into separate lines and append to the file
      echo "Images for NLU deployment $deployment:" >> "$LOGGING_FOLDER"/Deployed_Images.txt
      echo "$images" | tr ' ' '\n' >> "$LOGGING_FOLDER"/Deployed_Images.txt
      echo "" >> "$LOGGING_FOLDER"/Deployed_Images.txt

done

for deployment in $deployments_asr; do
  images=$(kubectl get deploy "$deployment" -o=jsonpath='{$.spec.template.spec.containers[:].image}' -n $namespace_asr)

	# Print the images to the console
      echo "Images for ASR deployment $deployment:"
      echo "$images"

      # Split the images into separate lines and append to the file
      echo "Images for ASR deployment $deployment:" >> "$LOGGING_FOLDER"/Deployed_Images.txt
      echo "$images" | tr ' ' '\n' >> "$LOGGING_FOLDER"/Deployed_Images.txt
      echo "" >> "$LOGGING_FOLDER"/Deployed_Images.txt

done