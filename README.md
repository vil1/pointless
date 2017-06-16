# Pointless : a desperate attempt to make point-free Scala less painful


This library defines some operators that make working with functions easier, as well as macros that help working with methods as if they where regular functions. 

``` scala
import pointless._

val alphaToUpper = map[List](a[Char].isDigit ? id[Char] | a[Char].toUpper)
// alphaToUpper: List[Char] => List[Char] = pointless.package$Mapper$$Lambda$1317/1688378155@7d2ed875

val stringToUpperChars = a[String].toList >>> alphaToUpper
// stringToUpperChars: String => List[Char] = scala.Function1$$Lambda$1320/1488513694@6676ba7d

scala> "hoay" |> stringToUpperChars
res0: List[Char] = List(H, O, A, Y)
```
