# K RESTful Naive Bayes Multiclass Classifier

The software classifies documents in image format, such as scans of invoices,
clinical documents and advertisements. The software is used over a RESTful API
with images embedded in Base64 encoding in a JSON body.

## Usage

First the classifier is trained with labeled samples of each class.
```javascript
POST http://URL/train
{
  "label": "invoice",
  "image": "/9j/4AAQSkZJRgA..."
}
```
See train.sh for example implementation.

During training the document/image is OCR'ed (Optical character recognition) and
the occurrence count of each word in each label is stored. The software uses by
default an H2 in-memory database that is cleared on restart.

After the training documents can be classified.
```javascript
POST http://URL/classify
{
  "image": "fdsFSDFDSfdJtrr..."
}
```
See classify.sh for example implementation.
The result is in the response's label field, along with classification
probabilities.
```javascript
{
  "label":"invoice",
  "probabilities":{
    "espacenet":"0.00%",
    "wipo":"0.00%",
    "invoice":"100.00%"
  }
}
```

During classification the document/image is OCR'ed and the probability of each
word occurring in each class is calculated
(https://en.wikipedia.org/wiki/Naive_Bayes_classifier#Document_classification
  up to the point of assuming two mutually exclusive classes, as this software
  can classify more than two classes).

## Installation / Running

Requirements for running and building:
- Java SE 8 (tested) JDK/JRE
- Maven (sudo apt-get install maven)
- Tesseract (sudo apt-get install tesseract-ocr)

1. Adjust the "<os.classifier>linux-x86</os.classifier>" in pom.xml to match
   your platform (options listed in pom.xml)
2. Build the software; mvn package
3. Execute the package from the directory where "tessdata" resides (the root of
   the project);
   sudo java -jar target/K-RNBMCC-1.0-SNAPSHOT.jar --server.port=80

The train.sh and classify.sh require:
   - jq (sudo apt-get install jq)
   - base64 (sudo apt-get install coreutils)
   - curl (sudo apt-get install curl)

train.sh sends the contents of training_data directory to a server URL given as
the first argument (for example http://localhost:80), thus training the model.
classify.sh is also given the URL as the first parameter and a path to an image
as the second parameter. The testing_data directory contains images that were
not in the training_data directory for classification testing.

## External licenses

tessdata from https://github.com/tesseract-ocr/tessdata
Apache License, Version 2.0

wipo and espacenet dataset from
http://machinelearning.inginf.units.it/data-and-tools/ghega-dataset

invoice dataset from http://madm.dfki.de/downloads-ds-doctor-bills
(c) DFKI, Research Department Knowledge Management, http://km.dfki.de
