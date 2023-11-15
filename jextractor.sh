#!/bin/bash

set -e

rm -rf com

TREE_SITTER=/home/user/tree-sitter/libtree-sitter.so.0.0
LANGUAGE=/home/user/cpy-tree/langs/tree-sitter-python/src/libpython.so

jextract -t com.github \
    -l ${TREE_SITTER} \
    -l ${LANGUAGE} \
    /home/user/tree-sitter/lib/include/tree_sitter/api.h
    