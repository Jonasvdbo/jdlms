#!/bin/bash

rm ../../src/main/java-gen/org/openmuc/jdlms/internal/asn1/iso/acse/* 

asn1bean-compiler -o "../../src/main/java-gen/" -p "org.openmuc.jdlms.internal.asn1.iso" -f iso-acse-layer.asn
