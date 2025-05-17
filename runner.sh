#!/bin/bash

echo " This script is used to run our sdasa/selenium-docker container post checking the Selenium Grid status "

echo " ================================================================================ "
echo " The following env variabled have been passed via docker-compose.yaml file "
echo " 			GRID_ENABLED	: ${GRID_ENABLED:-true}				"
echo " 			GRID_HOST		: ${GRID_HOST:-hub}					"
echo " 			BROWSER			: ${BROWSER:-chrome}				"
echo "			THREAD_COUNT	: ${THREAD_COUNT:-2}				"
echo " ================================================================================ "

echo " Now checking if the previous container Grid is ready to accept incoming requests "

COUNT=1

while [ "$( curl -s http://${GRID_HOST:-hub}:4444/status | jq -r .value.ready )" != true ]
do
	echo " Attempt : ${COUNT}, The HUB is NOT ready "
	COUNT=$((COUNT+1))
		if [ "${COUNT}" -ge 30 ]
		then
			echo " The HUB is NOT ready even after 30 seconds, Aborting !! "
			exit 1
		fi
	sleep 1
done

echo " GRID is up and running, Proceeding with triggering tests "

# Invoking the java cmd to trigger from sdasa/selenium-docker container
echo " The following command will be triggered now : "
echo " ##################################################################################################################################################################### "
echo " java -cp "libs/*" -Dgrid.enabled=${GRID_ENABLED} -Dgrid.host=${GRID_HOST} -Dbrowser=${BROWSER} org.testng.TestNG -threadcount "${THREAD_COUNT}" testSuites/testng.xml "
echo " ##################################################################################################################################################################### "

java -cp 'libs/*' -Dgrid.enabled=${GRID_ENABLED} -Dgrid.host=${GRID_HOST} -Dbrowser=${BROWSER} org.testng.TestNG -threadcount "${THREAD_COUNT}" testSuites/testng.xml
	
echo " ##################################################################################################################################################################### "
echo " 												Execution is complete, copying files to results dir			    														 "
echo " ##################################################################################################################################################################### "
cp -r test-output logs extentReports results

exit 0
	
