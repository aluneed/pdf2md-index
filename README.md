# pdf2md-index

package  
```mvn package```

full package  
```mvn assembly:assembly```

run  
```java -jar pdf2md-index-0.1-jar-with-dependencies.jar [filePath] <--firstChapter=[index]> <--noSectionCode>```

`--firstChapter` is optional, meaning the index(starts from 1) of literal chapter 1 in the pdf doc  
default 1

`--noSectionCode` is optional, making this program do not add section code such as "1.2.2" in front of the section title    

