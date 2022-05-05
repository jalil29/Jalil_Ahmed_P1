@ECHO off

::Insert your URL here, DO NOT PUT A SPACE BETWEEN = and the URL
set baseurl=http://expenseservice-env.eba-2x8gaxwa.us-east-2.elasticbeanstalk.com/

::Insert Chrome Executable location here
set chromeEXE=C:\Program Files (x86)\Google\Chrome\Application\chrome.exe

echo base %baseurl%
"%chromeEXE%" --new-window "%baseurl%" "%baseurl%expenses" "%baseurl%expenses/1" "%baseurl%expenses?status=approved" "%baseurl%expenses?status=pending" "%baseurl%expenses?status=declined"
"%chromeEXE%" --new-window "%baseurl%" "%baseurl%employees" "%baseurl%employees/1" "%baseurl%employees/1/expenses" 


::remove '::' to uncomment
set baseurl=http://localhost:5000/
::"%chromeEXE%" --new-window "%baseurl%" "%baseurl%expenses" "%baseurl%expenses/1" "%baseurl%expenses?status=approved" "%baseurl%expenses?status=pending" "%baseurl%expenses?status=declined"
::"%chromeEXE%" --new-window "%baseurl%" "%baseurl%employees" "%baseurl%employees/1" "%baseurl%employees/1/expenses" 