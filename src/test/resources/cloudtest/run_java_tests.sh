#!/bin/bash
set +e
export WORKING_DIR=$1
export LOGGING_FOLDER=$2
#export XML_REPORT_PATH="target/surefire-reports"
export XML_REPORT_PATH="target/cucumber"
set -a; source /etc/environment; set +a;
export JAVA_HOME="$JAVA_HOME_17_X64"
echo "$JAVA_HOME"
java --version
pwd
ls -lath
#printenv
echo $ENV_NAME
echo $tags
echo $namespace
echo $stabilityLevel
echo $botType

echo "Run testcases at Stability Level: $stabilityLevel for botType(MIX or PVA): $botType"
shopt -s nocasematch
TEXT_MAP="textMap.json"
if [[ "$stabilityLevel" == "CORP-CD1" ]]; then
  if [[ "$botType" == "MIX" ]]; then
    TEXT_MAP="textMap_CORP-CD1_MIXBot.json"
    echo "matched 1"
  elif [[ "$botType" == "PVA" ]]; then
    TEXT_MAP="textMap_CORP-CD1_PVABot.json"
  else
    TEXT_MAP="textMap_CORP-CD1_MIXBot.json"
  fi
elif [[ "$stabilityLevel" == "TST" ]]; then
  if [[ "$botType" == "MIX" ]]; then
    TEXT_MAP="textMap_TST_MIXBot.json"
  elif [[ "$botType" == "PVA" ]]; then
    TEXT_MAP="textMap_TST_PVABot.json"
  else
    TEXT_MAP="textMap_TST_MIXBot.json"
  fi
elif [[ "$stabilityLevel" == "PPE" ]]; then
  if [[ "$botType" == "MIX" ]]; then
    TEXT_MAP="textMap_PPE_MIXBot.json"
  elif [[ "$botType" == "PVA" ]]; then
    TEXT_MAP="textMap_PPE_PVABot.json"
  else
    TEXT_MAP="textMap_PPE_MIXBot.json"
  fi
elif [[ "$stabilityLevel" == "HM-PP" ]]; then
  if [[ "$botType" == "MIX" ]]; then
    TEXT_MAP="textMap_HM-PP_MIXBot.json"
  elif [[ "$botType" == "PVA" ]]; then
    TEXT_MAP="textMap_HM-PP_PVABot.json"
  else
    TEXT_MAP="textMap_HM-PP_MIXBot.json"
  fi
elif [[ "$stabilityLevel" == "PRD-CAN" ]]; then
  if [[ "$botType" == "MIX" ]]; then
    TEXT_MAP="textMap_PRD-CAN_MIXBot.json"
  elif [[ "$botType" == "PVA" ]]; then
    TEXT_MAP="textMap_PRD-CAN_PVABot.json"
  else
    TEXT_MAP="textMap_PRD-CAN_MIXBot.json"
  fi
elif [[ "$stabilityLevel" == "PRD-EU" ]]; then
  if [[ "$botType" == "MIX" ]]; then
    TEXT_MAP="textMap_PRD-EU_MIXBot.json"
  elif [[ "$botType" == "PVA" ]]; then
    TEXT_MAP="textMap_PRD-EU_PVABot.json"
  else
    TEXT_MAP="textMap_PRD-EU_MIXBot.json"
  fi
elif [[ "$stabilityLevel" == "GCC" ]]; then
  if [[ "$botType" == "MIX" ]]; then
    TEXT_MAP="textMap_GCC_MIXBot.json"
  elif [[ "$botType" == "PVA" ]]; then
    TEXT_MAP="textMap_GCC_PVABot.json"
  else
    TEXT_MAP="textMap_GCC_MIXBot.json"
  fi
fi
shopt -u nocasematch
echo
echo $TEXT_MAP
echo "Chosen textMap json file name: $TEXT_MAP"
export TEXT_MAP=$TEXT_MAP

start_time=$(date -d "$(date)" +"%Y-%m-%dT%H:%M:%S.%3NZ")
echo "-----Start time captured as $start_time-------"

# run java tests using separate test pom
# Note: The order in which the spring profiles are specified is important. Current order means that "t"est" is base profile
# and some of its settings can be overridden in the "devenv" profile.
#mvn clean test -Dspring.profiles.active=cloudtest,$ExecutionEnv -DsuiteXmlFile="$WORKING_DIR"/src/test/resources/hm-e2e-demotest-suite.xml -s settings.xml -Pcloudtest
#mvn clean test -Dspring.profiles.active=cloudtest,$ExecutionEnv -DsuiteXmlFile="$WORKING_DIR"/src/test/resources/mastersuite.xml -DVstsAccessToken=${VstsAccessToken} -s settings.xml -Pcloudtest

# create file for saving failed scenarios list
touch "$WORKING_DIR"/src/test/resources/failedScenarios.txt

mvn test -Dtest="RunCucumberTest" -Dcucumber.glue="com.msft.onecall.client" "-Dcucumber.filter.tags=$tags" -Dcucumber.plugin="pretty,junit:target/cucumber/report.xml,html:target/cucumber/html-report.html,rerun:rerun.txt" site -DVstsAccessToken=${VstsAccessToken} -s settings.xml

# Creating directory for storing multiple junit reports
mkdir "$LOGGING_FOLDER"/Junit_Reports

cp "$WORKING_DIR"/target/surefire-reports/TEST-com.msft.onecall.client.RunCucumberTest.xml "$LOGGING_FOLDER"/Junit_Reports/JUnit_0.xml
cp "$WORKING_DIR"/target/surefire-reports/TEST-com.msft.onecall.client.RunCucumberTest.xml "$WORKING_DIR"/src/test/resources/merge.xml
python3 "$WORKING_DIR"/src/test/resources/cloudtest/excludeSkippedTestCount.py "$LOGGING_FOLDER"/Junit_Reports/JUnit_0.xml

# Variable for keeping track of rerun count
rerunCount=1

# Loop to handle multiple rerun of failed tests after waiting for sometime between 2 consecutive runs
while [ $rerunCount -lt 4 ]; do
  value=$(cat "$WORKING_DIR"/src/test/resources/failedScenarios.txt)
  echo "Starting rerun = $rerunCount"
  echo "Scenarios to rerun $value"
  if ! [[ "$value" == "" ]]; then
    echo "Waiting 180 seconds before rerun $rerunCount"
    sleep 180
    echo "Wait time completed starting rerun $rerunCount for tests $value"
    truncate -s 0 "$WORKING_DIR"/src/test/resources/failedScenarios.txt
    mvn test -Dtest="RunCucumberTest" -Dcucumber.glue="com.msft.onecall.client" "-Dcucumber.filter.tags=$value" -Dcucumber.plugin="pretty,junit:target/cucumber/report.xml,html:target/cucumber/html-report.html,rerun:rerun.txt" site -DVstsAccessToken=${VstsAccessToken} -s settings.xml
    cp "$WORKING_DIR"/target/surefire-reports/TEST-com.msft.onecall.client.RunCucumberTest.xml "$LOGGING_FOLDER"/Junit_Reports/JUnit_"$rerunCount".xml
    python3 "$WORKING_DIR"/src/test/resources/cloudtest/excludeSkippedTestCount.py "$LOGGING_FOLDER"/Junit_Reports/JUnit_"$rerunCount".xml
    java "$WORKING_DIR"/src/test/java/com/msft/onecall/client/JunitXmlFilesMerger.java "$WORKING_DIR"/src/test/resources/merge.xml "$LOGGING_FOLDER"/Junit_Reports/JUnit_"$rerunCount".xml
  else
    echo "No tests to rerun skipping rerun(s)"
  fi
  # increment the value
  rerunCount=`expr $rerunCount + 1`
done

#Seggregate test result in CloudTest Junit.xml format
export PYTHONPATH="$WORKING_DIR"
# source "$WORKING_DIR"/test_venv/bin/activate

python3 "$WORKING_DIR"/src/test/resources/cloudtest/reports.py $WORKING_DIR $XML_REPORT_PATH

# Replacing existing old RunCucumberTest.xml with final merged report
rm "$WORKING_DIR"/target/surefire-reports/TEST-com.msft.onecall.client.RunCucumberTest.xml
cp "$WORKING_DIR"/src/test/resources/merge.xml "$WORKING_DIR"/target/surefire-reports/TEST-com.msft.onecall.client.RunCucumberTest.xml

# Remove the entries for skipped test cases from the "TEST-com.msft.onecall.client.RunCucumberTest.xml"
# to exclude them from the total test case count
python3 "$WORKING_DIR"/src/test/resources/cloudtest/excludeSkippedTestCount.py "$WORKING_DIR"/target/surefire-reports/TEST-com.msft.onecall.client.RunCucumberTest.xml

# Move Generated JUnit reports under cloud test logging directory to display results
ls -lath
ls -lath "$WORKING_DIR"
ls -lath "$WORKING_DIR"/src/test/resources/cloudtest
cp "$WORKING_DIR"/target/surefire-reports/TEST-com.msft.onecall.client.RunCucumberTest.xml "$LOGGING_FOLDER"/JUnit.xml
cp "$WORKING_DIR"/JUnit.xml "$LOGGING_FOLDER"/JUnit_aggregated.xml
cp -r "$WORKING_DIR"/target/surefire-reports/* "$LOGGING_FOLDER"/
cp -r "$WORKING_DIR"/target/cucumber/html-report.html "$LOGGING_FOLDER"/html-report.html
mkdir "$LOGGING_FOLDER"/site
cp -r "$WORKING_DIR"/target/site/* "$LOGGING_FOLDER"/site

end_time=$(date -d "$(date)" +"%Y-%m-%dT%H:%M:%S.%3NZ")
echo "-----End time captured as $end_time-------"

# To run scripts for K8S pods and images
if [[ "$stabilityLevel" == "CORP-CD1" ]]; then
  sudo chmod 755 "$WORKING_DIR"/src/test/resources/kubectl_config.sh
  /"$WORKING_DIR"/src/test/resources/kubectl_config.sh $namespace $LOGGING_FOLDER $start_time $end_time
fi