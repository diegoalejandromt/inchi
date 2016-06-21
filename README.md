The following program can be run using a Java 8 JVM
In order to run, type in a terminal:

java -jar inchi.jar <N>

where N is the number of entries you wish to display.

*Time complexity.*
=====================
The program uses indexing for  both the dictionary entries and the Chembl Ids. This indexing uses lemmatization, a process where the names of each compound is split in its lemmas, or to make it clearer into its stems. Each stem is stored in a HashMap, which values point to the word or chemblId they were exctracted from. This process speeds up the search, since it is not necessary to evaluate all words against all chemblIds in each run.

The time complexity of the Mapper class rankWords method is O(N^2) since it has a nested loop depending on both the size of words in the dictionary, and the size of the compounds database.



