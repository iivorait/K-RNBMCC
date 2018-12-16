#!/bin/bash

FILE=$1

IMGBASE64=$( base64 $1 -w 0 )
echo -n "Result: "
curl -s -X POST -H 'Content-type: application/json; charset=UTF8' -d @- http://localhost:8080/classify <<CURL_DATA
  {"image":"$IMGBASE64"}
CURL_DATA
echo ""
