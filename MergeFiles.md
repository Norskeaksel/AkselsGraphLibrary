A script to merge multiple files into one, for uploading single file solutions to competitive programming platforms.
It expects the files to end with blank line to keep file content on seperate lines.
The script also removes package declarations and places all in imports at the start of the file.
```sh
cd src/main/kotlin
cat examples/Honeyheist.kt graphClasses/Typealiases.kt graphClasses/GraphContract.kt graphClasses/IntGraph.kt \
graphClasses/Graph.kt graphClasses/Grid.kt graphClasses/BFS.kt graphClasses/DFS.kt graphClasses/GetPath.kt \
graphClasses/Dijkstra.kt graphClasses/AdvancedRead.kt | \
sed '/^package/d' | sed '/import graphClasses\.\*/d' | \
sed -n '/^import /p; /^import /!H; $ { g; s/^\n//; p; }' \
> ../../../../GraphSolutions/src/main/kotlin/MergedSolution.kt
cd ../../../..
echo "$(pwd)/GraphSolutions/src/main/kotlin/MergedSolution.kt" | clip
cd GraphLibrary
```

```sh
cd src/main/kotlin
cat examples/Units.kt graphClasses/AdvancedRead.kt | sed 's/package graphClasses//g' > \
../../../../GraphSolutions/src/main/kotlin/MergedSolution.kt 
cd ../../..
```
