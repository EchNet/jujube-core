# terrane-core

Supplemental types and data structures for the Java language.  Inspired by work in numeric algorithms
for [projecteuler.net](http://projecteuler.net).

## Goals

Provide memory-efficient alternatives to collections of "boxed" native types.  For example, class IntString 
may be used in place of List\<Integer\> in many applications.

Provide basic types not covered by the Java SDK: Matrix, Rational, et al.

Leverage the Java Collections API and maintain interoperability with it. 

## Major Types

### String

Classes IntString, LongString, ByteString, and their cousins are immutable, variable-length arrays. 
Like Java String, but not just for characters.  Support splice() and splice() operations, in 
the spirit of ECMA Script.  Usable as hash keys.

### Sequence

Classes IntSequence, LongSequence, ByteSequence and their cousins are collections or generators of 
native type elements that may be traversed in one direction.  A sequence produces a cursor,
which is a read-only iterator.  There is API support for mapping and filtering of sequences.

### Matrix

2D arrays are often useful.  Classes LongMatrix, DoubleMatrix, and their cousins fill the void.

### Rational

Representing a number as a numerator/denominator pair, rather than as a floating point number,
avoids erroneous loss of precision in some applications.  Class LongRational represents a 
rational number and provides arithmetic operations on rationals.

## References

Maps and Lists of native types are not provided here.
I recommend checking out the [Colt library](http://acs.lbl.gov/software/colt/).
