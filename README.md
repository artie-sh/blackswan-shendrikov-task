## Black Swan Technologies test automation sample by Artie Shendrikov

This test case was created as a part of the practical task

## Running test in Maven

A minimalistic command needed to run the test is
>```mvn clean test -Dusername=${username} -Dpassword=${password} -Denvironment=${environment}```

This assumes that chromedriver executable is available in the project folder, otherwise chromedriver location must be specified explicitly.
A more verbose command to run tests with additional configs:
>```mvn clean test -Dusername=${username} -Dpassword=${password} -Denvironment=${environment} -Dwebdriver.chrome.driver=${chromedriver_location} -Dwebdriver.wait.seconds=${webdriver.wait.seconds} -Dretry.attempts.number=${retry.attempts.number} -Dclose.browser=${close.browser}```
