#!/bin/bash

while true 
do
	DATE=$(date +"%Y-%m-%d_%H%M%S")
	fswebcam -r 1280x720 --no-banner /home/pi/Documents/Monografia/projetoMonografia1/img-/$DATE.jpg
	sleep 3
done