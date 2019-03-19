# Simple Search Engine
Simple search engine implemented as an inverted index returning result list sorted by TF-IDF.
The program takes a single argument - path to the file containing a list of documents. 
## Input file format 
Document strings delimited by newlines, for example:
```
the brown fox jumped over the brown dog
the lazy brown dog sat in the corner
the red fox bit the lazy dog
```

## Running
### Windows
```
cd search-engine
mvnw.cmd package
java -jar target\search-engine-1.0.jar example-data\docs.txt
```
### Linux
```
cd search-engine
./mvnw package
java -jar target/search-engine-1.0.jar example-data/docs.txt
```
Use `Ctrl/Cmd + C` to exit