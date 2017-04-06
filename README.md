NOTE: This is 90% done, all of the JVM tests work & the scalajs code compiles/etc.  Not scala.js testing has been done.

-----

Based upon https://github.com/JodaOrg/joda-money using https://github.com/timowest/scalagen

Simply added the plugin to the joda-money pom.xml:

<plugin>
  <groupId>com.mysema.scalagen</groupId>
  <artifactId>scalagen-maven-plugin</artifactId>
  <version>0.2.2</version>
</plugin>

Then, executed mvn scalagen:main to generate src/main/scala/
That directory tree is copied, as well as joda-money/src/main/resources/org/joda/money/MoneyData.csv

Then, executed mvn scalagen:test to generate src/test/scala

-----

```

$ sbt scalajodamoneyJVM/test

....

[info] Run completed in 3 seconds, 219 milliseconds.
[info] Total number of tests run: 1485
[info] Suites: completed 11, aborted 0
[info] Tests: succeeded 1485, failed 0, canceled 0, ignored 0, pending 0
[info] All tests passed.
[success] Total time: 4 s, completed Apr 6, 2017 9:47:29 AM

```