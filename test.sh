#!/bin/bash
javac Sudoku.java
for f in {1..50}
do
    time java Sudoku >> /dev/null
done
