# Business Applications by jBPM - IFTTT Integration

This is a demo business application built with https://start.jbpm.org and expanded
to add integration with the IFTTT service. 

![Sample of demo](img/demoscreenshot.png?raw=true)

The demo simulates a simple driver pickup request where users can enter in their 
name and address where they need somome to come pick them up. Once the 
form is submitted, a business process is executed which calls the jBPM IFTTT Workitem.
This workitem sends a request to an IFTTT Applet which receives the WebHook request and then
send an SMS with the pickup information as well as opens Google Maps on the phone
with the address entered.

## Getting Started 
1. Clone and build the needed kie server thymeleaf dialect project:
```
git clone https://github.com/tsurdilo/thymeleaf-kie-server-dialect.git
cd thymeleaf-kie-server-dialect
mvn clean install
```

2. Clone this repository locally and start the demo app:

```
git clone https://github.com/business-applications/sample-dashboard-thymeleaf.git
cd sample-dashboard-thymeleaf
cd sample-dashboard-thymeleaf-service
chmod 755 launch.sh (only needed for unix environments , use launch.bat for windows)
./launch.sh (or launch.bat for windows)
```

3. Setup account for IFTTT (https://platform.ifttt.com/) to get an access key.
Once logged in set up your sample platform and go to "Applets". Create a new applet which 
receives a webhook request with EventName "orderpickup":

![Applet trigger](img/demotrigger.png?raw=true)


And two actions (one that sens a SMS and one that opens Google Maps):

![Applet action1](img/demoaction1.png?raw=true)

![Applet action2](img/demoaction2.png?raw=true)


Save the applet and turn it on in IFTTT:

![Turn on applet](img/turnonapplet.png?raw=true)

4. Download the IFTTT app on your phone. Set it up with same account as you registered with in step 3
and give it permissions to send SMS on your phone.

5. Update the Workitem Handler with your IFTTT access key:
in sample-ifttt-kjar/src/main/resources/META-INF/kie-deployment-descriptor.xml

replace the YOUR_ACCESS_KEY_HERE:

```xml
<work-item-handlers>
        <work-item-handler>
            <resolver>mvel</resolver>
            <identifier>new org.jbpm.process.workitem.ifttt.IFTTTWorkitemHandler("YOUR_ACCESS_KEY_HERE")</identifier>
            <parameters/>
            <name>IFTTTCall</name>
        </work-item-handler>
    </work-item-handlers>
```


with the real access key you get after registering on the IFTTT platform.

6. go to sample-ifttt-service and start the appliaction:

for Windows users:

```
launch.bat clean install
```

or for Unix/Linux users

```
chmod 755 launch.bat (only has to be done once)
./launch.sh clean install
```

## Accessing your application

Once the app has started you cann access the landing page at 

```
localhost:8090/demo
```

Enter in the values, first name, last name and address. The address can also be for example "nearest post office"
or "chinese restaurant", anything that Google Maps is capable of resolving.

Press Submit. 

Once the process completes, a SMS should be sent to your phone and Google Maps should be opened 
with driving directions to the location entered in the form.

![Turn on applet](img/demoresults.png?raw=true)

![Turn on applet](img/sms.png?raw=true)

