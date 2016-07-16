RED='\033[0;31m'
GREEN='\033[0;32m'
BLACK='\033[0m'
echo "${BLACK}Checking zip folder extension..."
if file $1
then
	if [ ${1: -4} == ".zip" ]
	then
		echo "${GREEN}Zip folder extension correct"
	   	unzip -P pcp9100 "$1"
	else
		echo "${RED}Zip folder extension should be *.zip"
		echo "\033[0m"
		exit
	fi
fi
echo "${BLACK}Checking zip folder name convention..."
if [[ "$1" == CS15M0??_P6.zip ]];
then
	echo "${GREEN}Zip folder name convention correct"
else
	echo "${RED}Zip folder name convention is not correct"
	echo "\033[0m"
	exit
fi
zip_file="${1}"
new_name="${zip_file%.*}" 
echo "${BLACK}Checking for $new_name folder..."
if [ -d "$new_name" ]; then
	echo "${GREEN}$new_name folder found"
else
	echo "${RED}$new_name folder not found"
	echo "\033[0m"
	exit
fi
cd $new_name
COUNTER=0
for entry in "."/*
do
  	if [[ "$COUNTER" == 0 ]];
  	then
  		echo "${BLACK}Checking for CODE folder..."
  		if [[ "$entry" == "./CODE" ]];
  		then
  			echo "${GREEN}CODE folder found"
  		else
	  		echo "${RED}CODE folder not found"
	  		echo "\033[0m"
	  		exit
	  	fi
  	fi
  	if [ $COUNTER == 1 ]
  	then
  		echo "${BLACK}Checking for OUTPUT folder..."
	  	if [[ "$entry" == "./OUTPUT" ]];
	  	then
	  		echo "${GREEN}OUTPUT folder found"
	  	else
	  		echo "${RED}OUTPUT folder not found"
	  		echo "\033[0m"
	  		exit
	  	fi
  	fi
  	COUNTER=$((COUNTER + 1))
done
cd CODE
COUNTER=0
echo "${BLACK}Checking for Solution.java file..."
for entry in "."/*
do
	if [[ "$entry" == "./Solution.java" ]];
	then
		echo "${GREEN}Solution.java found"
		COUNTER=$((COUNTER + 1))
		break
	fi
done
if [ $COUNTER == 0 ]
then
	echo "${RED}Solution.java not found"
	echo "\033[0m"
	exit
fi

cd ../OUTPUT
COUNTER=0
echo "${BLACK}Checking for 1.txt file..."
for entry in "."/*
do
	if [ $COUNTER == 1 ]
	then
		echo "${RED}More than one output found"
		echo "\033[0m"
		exit
	fi
	if [[ "$entry" == "./1.txt" ]];
	then
		echo "${GREEN}1.txt found"
		COUNTER=$((COUNTER + 1))
	fi
done
if [ $COUNTER == 0 ]
then
	echo "${RED}1.txt not found"
	echo "\033[0m"
	exit
fi
echo "${GREEN}EVERYTHING LOOKS GOOD FOR SUBMISSION!"
echo "\033[0m"
exit