#!/bin/bash

auth="elastic:elastic"

version="$(cat version)"
for file in `ls composable/component/*.json`
do
  fieldset=`echo $file | cut -d/ -f5 | cut -d. -f1 | tr A-Z a-z`
  component_name="ecs_${version}_${fieldset}"
  api="_component_template/${component_name}"

  # echo "$file => $api"
  curl -k --user "$auth" -XPUT "https://localhost:9200/$api" --header "Content-Type: application/json" -d @"$file"
done
