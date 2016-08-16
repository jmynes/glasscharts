#!/bin/bash

# pre-reqs needed to install ruby and run the seed.rb script on Ubuntu

sudo apt-get update
sudo apt-get install -y ruby ruby-dev build-essential

sudo gem install mongo
