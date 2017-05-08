## Task ##
you will find a list of vehicle license

numbers. Place these in a text file Kentekens.txt. These are the cars

that are owned by our company.

For more information about these vehicles, you can use a web service to retrieve data:

This service wsdl document is located at:

https://ws1.webservices.nl/soap_doclit?wsdl

The method carRDWCarData() will give you more info on the vehicle. This

call will require authentication. You can user the following username

and password:

solicitant_User

a1fefd74c35225f8eab80e2865dec014

More information on this method you can find at:

https://webview.webservices.nl/documentation/files/service_car-
php.html#Car.carRDWCarData

More info on the webservice you can find here:

https://webview.webservices.nl/documentation/files/interfaces/soap/soap

_using-txt.html

The assignment

Create a list of all our cars, with the vehicle license, brand and

model of each car which needs a new APK (government regulated technical

car test, see field apk_due_date). This is very important because it is

not allowed to drive with a car that has an expired APK.


## Technologies/Libraries Used ##

1. Java (1.8)
2. Jackson (2.8.1) - To deserialize retrieved Json string into Java model
2. Jackson (2.8.1) - To deserialize retrieved Json string into Java model
3. Logback (1.1.7) - To log tweets
4.Junit (4.11) - To Unit Testing the Code
5. Maven Shade Plugin (2.4.3) - To create fat executable jar

## Implementation Highlights ##

TwitterStatistics components are de-coupled. Message retrieving and processing happens in separate threads. It uses Java's ExecutorService to create and manage threads for TweetsRetriever and TweetsDeserializer. Please read below (core classes section) to see purpose of TweetsRetriever and TweetsDeserializer.

TweetsRetriever retrieves tweets and enqueues them into a queue as it retrieves. TweetsDeserializer monitors the queue and de-serialize the tweets as and when new tweet is available in the queue.

Implementation uses Java 8 lambda expressions wherever suitable.

TwitterStatistics handles non status tweets (e.g limit notices) sent by twitter and skips it's processing.

TwitterStatistics ensures resource cleaning and connection closing in all cases. It closes the streaming connection, shuts down thread pools before exiting.

The default logging level is set to "info" and it logs on console. Please update src/main/resources/logback.xml to turn on "debug" mode in case you want to see more finer details.

The TwitterStatistics is configurable by passing different JVM parameters while running TwitterStatistics. Please see usage for further details.

## Usage ##

The attachement contains an executable jar (twitter-statistics-1.0.0.jar) which can be run to execute the TwitterStatistics.

`java -jar twitter-statistics-1.0.0.jar`

Follow on-screen instructions to authenticate with twitter. Post successfull authentication, the TwitterStatistics will track 'bieber' for either 100 tweets or 30 seconds which ever occurs first.

Please pass following JVM arguements while running the TwitterStatistics to test TwitterStatistics.

-Dtracking.keywords=<Comma separated list of keywords to track commas as logical ORs, while spaces are equivalent to logical ANDs (e.g. ‘the twitter’ is the AND twitter, and ‘the,twitter’ is the OR twitter).>
-Dtracking.limit=<Maximum number of tweets to retrieve>
-Dtracking.timeoutseconds=<Tracking timeout in seconds>

For example, following execution will track "picnic" keyword to retrieve tweets for 20 seconds or 50 tweets, whichever occurs first

`java -Dtracking.keywords=earth -Dtracking.limit=50 -Dtracking.timeoutseconds=20 -jar twitter-statistics-1.0.0.jar`

## Core Classes ##

+ `TwitterStatisticsRunner`: TwitterStatisticsrunner to demo TwitterStatistics. It contains main() method.
+ `TwitterStatistics`: TwitterStatistics class to expose TwitterStatistics functionality. External TwitterStatistics/class should use this class's methods to authenticate, track and print tweets received from twitter. It contains following important methods.
        * authenticate() - Authenticates the TwitterStatistics with twitter
        * startTracking(stringToTrack, maxMessages, trackingTime) - Starts retrieving tweets from twitter.
        * printMessagesGroupedByAuthor() - Groups tweets by authors, sorts them chronologically and prints to console using TweetsPrinter

+ `TweetsRetriever`: Callable implementation to retrieve tweets from twitter and puts into a queue to be processes further by TweetsDeserializer
+ `TweetsDeserializer`: Callable implementation to de-serialize tweets retrieved from twitter into Java objects using Jackson
+ `TweetsProcessor`: Processes de-serialized twitter tweets to group tweets by twitterUser, sorts them chronologically

