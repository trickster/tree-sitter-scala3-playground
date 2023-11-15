# Using tree-sitter library from Scala

This is a simple example of using the [tree-sitter](https://tree-sitter.github.io/tree-sitter/) library from Scala.

It uses `jextract` to generate the bindings to the C library. Grab it from the [link](https://github.com/openjdk/jextract) here and use the download link from the repo.

Download the `tree-sitter` library from [here](https://github.com/tree-sitter/tree-sitter) and run `make` in the root folder. This will create the `libtree-sitter.so` file in the root folder. Set the `TREE_SITTER` variable in `jextractor.sh` to this path.

Download your language grammar (for example `tree-sitter-python`) and build the shared library from the `src` directory. Set the `LANGUAGE` variable in `jextractor.sh` to this path.

Build command:

```sh
cd tree-sitter-python/src
clang -O3 -shared -fPIC parser.c scanner.c -std=c99 -o libpython.so
```

Run the `jextractor.sh` script to generate the bindings. This will create the `com/github` folder with all the compiled Java classes.

`scala-cli` does the rest.

Ensure that you use `JVM 21`, for example `sdk install java 21-tem`.
