#!/bin/bash

FILES=trainingdata/*
for f in $FILES
do
  echo "Training $f"
  IMGBASE64=$( base64 $f -w 0 )
  LABEL=$( echo -n $f | cut -f 1 -d '.' | cut -f 2 -d '/')

  curl -X POST -H 'Content-type: application/json; charset=UTF8' -d @- http://localhost:8080/train <<CURL_DATA
  {"image":"$IMGBASE64","label":"$LABEL"}
CURL_DATA

  echo ""
done

curl -s 'http://localhost:8080/documents' | jq -c '.[] | {label: .label, word: .word, count: .count}'
echo "Training done, results above"
