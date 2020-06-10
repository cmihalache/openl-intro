# openl-intro
This project is useful for those who are starting to learn about the [OpenL Tablets](http://openl-tablets.org/) Business Rules. It contains the following modules:


## openl-intro-p1-rules-engine
This module contains various scenarios to be implemented with OpenL Tablets, in order to gradually learn to work with it. The code is only in the tests. In order to better understand the scenarios, [the presentation](/docs/Presentation.pdf) should be helpful.


## openl-intro-p2-myRules-model
This module contains only the domain classes, that are necessary when communicating with our OpenL Tablets project, in a distributed application


## openl-intro-p3-myRules-rules
This module contains only the OpenL Tablets project. It will produce the project zip file after the build.


## openl-intro-p4-myRules-services
This module contains our web services. It is a Spring Boot application, that will communicate directly with the deployed OpenL Tablets project.


## openl-intro-p5-utils
This module contains some utility java classes used, for instance, to deploy an OpenL Tablets project zip file in a given database.
