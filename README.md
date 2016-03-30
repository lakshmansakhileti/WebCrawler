# WebCrawler

WebCrawler is project which can read mail data form specified Url and Year. Which can modify the year value in crawer.properties file.

To run the project execute following command

 java -jar crawler.jar WebCrawler
 
when you run the above command you can sea a file name on your desktop with YearXXX (e.g.Year 2016).

With in that we have all mail information in month wise in a folder structure.

# Usage of Cubertura code coverage plugin.


Which is plug in of maven which can useful for code coverage using junits.

Configuration :

 <plugin>
		<groupId>org.apache.maven.plugins</groupId>
		<artifactId>maven-project-info-reports-plugin</artifactId>
		<version>2.7</version>
		<configuration>
			<dependencyLocationsEnabled>false</dependencyLocationsEnabled>
		</configuration>
	</plugin>
	
    <plugin>
        <groupId>org.codehaus.mojo</groupId>
        <artifactId>cobertura-maven-plugin</artifactId>
        <version>2.7</version>
        <configuration>
        	
        	<instrumentation>
            <excludes>
              <exclude>com/pramati/crawler/*.class</exclude>
               <exclude>com/pramati/service/*.class</exclude>
            </excludes>
          </instrumentation>
			<formats>
				<format>html</format>
				<format>xml</format>
			</formats>
			 <aggregate>true</aggregate>
		</configuration>
		<executions>
          <execution>
            <goals>
              <goal>clean</goal>
            </goals>
          </execution>
        </executions>
		
      </plugin>
      
    
     <exclude>com/pramati/crawler/*.class</exclude> tag is used to specify exclude while code coverage.
     <format>html</format> tag used to specify in which format we want to display report.
    


# Usage of poi api.

HSSF and XSSF for Excel Documents

We use HSSF for Excel generation which can be useful for read/write data into xml file.

Here we created a work book on desktop namely MailInformation.xmls and write mail inform into that in a month wise  sheets.

HWPF and XWPF for Word Documents

which can useful for read/write data into word 97 - 2007 file.

Here we create a word file on desktop namely MailInformation.docx and write all mail information in table format. 


