Based upon https://github.com/JodaOrg/joda-money using https://github.com/timowest/scalagen

Simply added the plugin to the joda-money pom.xml:

<plugin>
  <groupId>com.mysema.scalagen</groupId>
  <artifactId>scalagen-maven-plugin</artifactId>
  <version>0.2.2</version>
</plugin>

Then, executed mvn scalagen:main to generate src/main/scala/

That directory tree is copied, as well as joda-money/src/main/resources/org/joda/money/MoneyData.csv
