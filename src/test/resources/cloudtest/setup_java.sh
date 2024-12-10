#!/bin/bash
set -e
export WORKING_DIR=$1
export PYTHONPATH="$WORKING_DIR"
apt-get update -y