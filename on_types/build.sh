#!/usr/bin/env bash

SCRIPT_DIR=$( cd -- "$( dirname -- "${BASH_SOURCE[0]}" )" &> /dev/null && pwd )

asciidoctor-pdf -r asciidoctor-mathematical -a mathematical-format=svg -o on_types.pdf $SCRIPT_DIR/README.adoc
rm $SCRIPT_DIR/*.svg 
