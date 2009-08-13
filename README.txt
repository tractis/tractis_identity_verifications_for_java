

	Overview
------------------------------------------------------------------------------------------
This is a sample webapp to illustrate how Tractis Identity Verifications work on Java. 
You can use the taglibs provided out of the box or customize them to adapt them to your needs.

Tractis Identity Verifications taglibs encapsulates some simple actions that allows Java servers
to perform authentication processes based on electronic certificates using Tractis platform.

	Usage
------------------------------------------------------------------------------------------

You'll find those usage examples at jsp directory.

You need at least 3 pages:
+ Login page containing call to Tractis Identity Verification (index.jsp)
+ Callback page, where Tractis will redirect the user after verification (verification_callback.jsp)
+ Result page to determine the result of the process (here we have 2 authentication_ok.jsp and authentication_failed.jsp)
	
After all the process if Verification was successful session will contain verified identity attributes as shown
in (authentication_ok.jsp)

	Configuration
------------------------------------------------------------------------------------------	
Configuration is placed at tractis-settings.properties, here you MUST configure API_KEY that could
be requested at Tractis site, and callbacks to your system.

To create this API_KEY you must:
	   1. Sign in at Tractis and go to http://www.tractis.com/identity_verifications.
   	   2. Generate an API Key introducing the url of the site that will call Identity Verification services
   	   2.b (Optional) A more fine grained configuration about which attributes request and verify could be performed here 
	
	Deploy
------------------------------------------------------------------------------------------	
Ant file (build.xml) contains a simple deploy task for packing, just call : ant pack 
After just copy the dist/titl.war to the webcontainer deploy directory
		
	Additional Check List
------------------------------------------------------------------------------------------
On tomcat deploys ensure connector encoding has been set to utf-8 
<Connector port="8081" protocol="HTTP/1.1" connectionTimeout="20000" URIEncoding="UTF-8"/>

To have service working please check parameters at tractis-settings.properties

	License
------------------------------------------------------------------------------------------
	
	The MIT License

Copyright (c) 2009 Tractis

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in
all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
THE SOFTWARE.

	
