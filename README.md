# Demo-Automation-Project
Create a Assignment for the parabank application.
I have used Selenium Webdriver + TestNG and create visual test freamwork in java language using Hybrid Model.

To run the application you need to follow the below step:
1. I have created one controller file which is a main file to handle all the test suite,
   - you need to mark as "Y" in the controller file which test suite you want to run it.
   - you need to mark as "Y" in the controller file on which browser you want to run it(currently i have run it on chrome browser)
   - you need to set iteration time how many time you want to run the testsuite.
   
2. I have created input data folder and in this folder create excel file of our testcase.
   - In the Testcase excle file have multiple sheet with test step.
   - In the frist sheet i have added list of the testcase, if user wants to run any test cases that cases should be mark as "Y" other wise it should be mark as "N"
   - i have added screensort michenisum in to the script if any case fail then screensort will taken of failer part and it will store in output folder as date wise.
   - if you want to take a screensort for the every step the i have added one michenisum, in to the excle sheet added one column name as screensort. if user mark as "Y" for any test step then that screensort will be taken during the runing time and it will store in otput folder.
   - Also HTML testNG report will generated and report will store in Report folder, you can open and analisys the report.
3. After set the above step we are ready to run the out test suite or testcases.
4. To run the test, go to the run button >> In the Run as > java application
5. script will be run and consol log also generated.
6. After run the script, Please check the output folder as fail screensort and to show the report check report folder, report will be generated.      
