A script to merge multiple files into one, for uploading single file solutions to competitive programming platforms.
```sh
cd src/main/kotlin
cat examples/Bookclub.kt graphClasses/Typealiases.kt graphClasses/GraphContract.kt graphClasses/IntGraph.kt \
graphClasses/Graph.kt graphClasses/Grid.kt graphClasses/BFS.kt graphClasses/DFS.kt graphClasses/Maxflow.kt \
graphClasses/AdvancedRead.kt | sed 's/package graphClasses//g' > ../../../../GraphSolutions/src/main/kotlin/MergedSolution.kt 
cd ../../..
```
