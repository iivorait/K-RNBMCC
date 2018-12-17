#!/bin/bash

if [ $# -lt 1 ]; then
  echo "Usage: ./train.sh URL"
  exit 0
fi

# Files are named as label.y.xxx where label is the correct label
FILES=training_data/*
URL=$1

for f in $FILES
do
  echo "Training $f"
  IMGBASE64=$( base64 $f -w 0 )
  LABEL=$( echo -n $f | cut -f 1 -d '.' | cut -f 2 -d '/')

  curl -X POST -H 'Content-type: application/json; charset=UTF8' -d @- "$URL/train" <<CURL_DATA
  {"image":"$IMGBASE64","label":"$LABEL"}
CURL_DATA

  echo ""
done

curl -s "$URL/documents" | jq -c '.[] | {label: .label, word: .word, count: .count}'
echo "Training done, results above"
