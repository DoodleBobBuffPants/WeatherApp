Read Me


To run the code we execute MainScreen.java, or the supplied executable jar file. This initiates the home screen and the rest of the application follows.


The main java library is used with jdk-10, mainly utilising swing and awt tools throughout as can be seen in the imports and the elements used.


The additional libraries are listed below:


Commons-codec-1.10
Commons-logging-1.2
Fluent-hc-4.5.5
Httpclient-4.5.5
Httpclient-cache-4.5.5
Httpclient-win-4.5.5
Httpcore-4.4.9
Httpmime-4.5.5
Jackson-annotations-2.9.0
Jackson-core-2.9.5
Jackson-databind-2.9.5
Jna-4.4.0
Jna-platform-4.4.0
LGoodDatePicker-10.3.1


Most of these come with maven and allow http clients and connections to be made. Jackson is for JSON parsing as it makes it very easy with the ObjectMapper. The date picker is used in the settings screen for easy selection of times, and because it was the only one we could find that lets us do the job on desktop.