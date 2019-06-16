#!/usr/bin/env bash

export KODILLA_HOME=/Users/przemyslaw/Documents/kodilla/kodilla-tasks

start_runcrud()
{
  $KODILLA_HOME/runcrud.sh start
  end
}

fail() {
  echo "There is a problem with running runcrud.sh script."
}

end() {
  echo "Work is finished"
}

start_webBrowser(){
open -a Safari http://localhost:8080/crud/v1/task/getTasks
}

if start_runcrud; then
sleep 2
   start_webBrowser
else
   fail
fi