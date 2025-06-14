#!/bin/bash

# Prupose:
#	1. Echo's env variables that are set in the system
#	2. Check if Grid container is up and in READY state
#	3. Invokes test run using java cmd
#	4. Copies the test run artifacts to results dir

######################## 1.Echo's env variables that are set in the system ########################
echo " This script is used to run our sdasa/selenium-docker container post checking the Selenium Grid status "

echo " ================================================================================ "
echo " The following env variables have been passed via docker-compose.yaml file "
echo " 			GRID_ENABLED	: ${GRID_ENABLED:-true}				"
echo " 			GRID_HOST		: ${GRID_HOST:-hub}					"
echo " 			BROWSER			: ${BROWSER:-chrome}				"
echo "			THREAD_COUNT	: ${THREAD_COUNT:-2}				"
echo " ================================================================================ "

######################## 2.Check if Grid container is up and in READY state ########################
echo " Now checking if the previous container Grid is ready to accept incoming requests "

COUNT=1

while [ "$( curl -s http://${GRID_HOST:-hub}:4444/status | jq -r .value.ready )" != true ]
do
  echo "The ready status is:"
  echo "$( curl -s http://${GRID_HOST:-hub}:4444/status | jq -r .value.ready )"
	echo " Attempt : ${COUNT}, The HUB is NOT ready "
	COUNT=$((COUNT+1))
		if [ "${COUNT}" -ge 30 ]
		then
			echo " The HUB is NOT ready even after 30 seconds, Aborting !! "
			exit 1
		fi
	sleep 5
done

######################## 3.Invokes test run using java cmd  ########################
echo " GRID is up and running, Proceeding with triggering tests "

# Invoking the java cmd to trigger from sdasa/selenium-docker container
echo " The following command will be triggered now : "
echo " ##################################################################################################################################################################### "
echo " java -cp "libs/*" -Dgrid.enabled=${GRID_ENABLED} -Dgrid.host=${GRID_HOST} -Dbrowser=${BROWSER} org.testng.TestNG -threadcount "${THREAD_COUNT}" testSuites/testng.xml "
echo " ##################################################################################################################################################################### "

java -cp 'libs/*' -Dgrid.enabled=${GRID_ENABLED} -Dgrid.host=${GRID_HOST} -Dbrowser=${BROWSER} org.testng.TestNG -threadcount "${THREAD_COUNT}" testSuites/testng.xml

######################## 4.Copies the test run artifacts to results dir ########################

# Copying the test run artifacts to results dir, which is volume mapped
echo " ##################################################################################################################################################################### "
echo " 												Execution is complete, copying files to results dir			    														 "
echo " ##################################################################################################################################################################### "
cp -r test-output logs extentReports results

exit 0