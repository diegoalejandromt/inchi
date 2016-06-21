The following program can be run using a Java 8 JVM
In order to run, type in a terminal:

java -jar inchi.jar <N>

where N is the number of entries you wish to display.

You may add the following sytem properties at runtime, if you do not wish to download the sample data from the internet

java -Ddictionary.file=<dict.file> -Dchembl.file=<chembl.file> -jar inchi.jar <N>

Please replace <dict.file> with the path to to the dictionary file, and <chembl.file> to the path of the compressed chembl file.
j
*Time complexity.*
The program uses indexing for  both the dictionary entries and the Chembl Ids. This indexing uses lemmatization, a process where the names of each compound is split in its lemmas, or to make it clearer into its stems. Each stem is stored in a HashMap, which values point to the word or chemblId they were exctracted from. This process speeds up the search, since it is not necessary to evaluate all words against all chemblIds in each run.

The time complexity of the Mapper class rankWords method is O(logN). The ratio given by the test shows a constant number which neither converges to zero or infinity. Giving then a good estimate that the algorithm is O(logN).

*Tests*
In order to run the tests, please run the following command:
mvn test -DargLine="-Ddictionary.file=<dict.file> -Dchembl.file=<chembl.file>"
This assumes you have previously installed maven in your system. Please replace <dict.file> with the path to to the dictionary file, and <chembl.file> to the path of the compressed chembl file.

