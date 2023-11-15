//> using javaOpt --enable-preview, --enable-native-access=ALL-UNNAMED, -Djava.library.path=/home/user/tree-sitter:/home/user/cpy-tree/langs/tree-sitter-python/src
//> using jar "."

// sdk default java 21-tem
// scala-cli run treesc.sc -cp .

// To run this,
// First build tree-sitter, clone the repo and run make, should be available at /home/user/tree-sitter/libtree-sitter.so
// Build tree-sitter-python with `clang -O3 -shared -fPIC parser.c scanner.c -std=c99 -o libpython.so` in /home/user/cpy-tree/langs/tree-sitter-python/src
// Should be available at /home/user/cpy-tree/langs/tree-sitter-python/src/libpython.so

// Use jextract tool to generate the bindings for tree-sitter

import java.lang.foreign.*
import java.lang.invoke.MethodHandle
import scala.jdk.CollectionConverters.*
import scala.util.Using

val linker = Linker.nativeLinker()
val stdlib = linker.defaultLookup()
val loaderLookUp = SymbolLookup.loaderLookup()

val input = "def foo():\n  print('hello')\n"

import com.github.api_h.*
import com.github.*

println("----------")
Using(Arena.ofConfined()) { arena =>

  val parser = ts_parser_new()

  val tsPy = linker
    .downcallHandle(
      loaderLookUp.find("tree_sitter_python").orElseThrow(),
      FunctionDescriptor.of(
        ValueLayout.ADDRESS
      )
    )
  val language = tsPy.invoke().asInstanceOf[MemorySegment]
  val res = ts_parser_set_language(parser, language)
  assert(res == true)
  val cString = arena.allocateUtf8String(input)
  val tree = ts_parser_parse_string(
    parser,
    MemorySegment.NULL,
    cString,
    cString.byteSize().toInt
  )

  val rootNode = ts_tree_root_node(arena, tree)
  val syntaxTree = ts_node_string(rootNode)

  println(syntaxTree.getUtf8String(0))
  ts_parser_delete(parser)
}
