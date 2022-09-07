#!/bin/bash

TODAY=`date --iso-8601`

mkdir -p backups

pg_dump  -v -h localhost -U postgres -d postgres > backups/$TODAY.backup

if [ "$?" -ne 0 ]; then echo "Help" | echo "Backup failed"; exit 1; fi