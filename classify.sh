#!/bin/bash

if [ $# -lt 2 ]; then
  echo "Usage: ./classify.sh URL PathToImage"
  exit 0
fi

URL=$1
FILE=$2

IMGBASE64=$( base64 $FILE -w 0 )
echo -n "Result: "
curl -s -X POST -H 'Content-type: application/json; charset=UTF8' -d @- "$URL/classify" <<CURL_DATA
  {"image":"$IMGBASE64"}
CURL_DATA
echo ""
