package pointless

import shapeless.{SingletonTypeUtils, Witness}

import scala.language.dynamics
import scala.language.experimental.macros
import scala.reflect.macros.whitebox

/**
  * Gives access to a class' methods in a more point-free-ish style
  * 
  * 
  */
class Point[T] extends Dynamic {

 def selectDynamic(methodSelector: String): Point.F1[T] = macro PointMacros.parameterLessMethod[T]

}

object Point {
  type F1[-X] = (X) => _

  def apply[T] = new Point[T]
}

class PointMacros(val c: whitebox.Context) extends SingletonTypeUtils {

  import c.universe._


  def parameterLessMethod[T: WeakTypeTag](methodSelector: Tree): Tree = {

    val tpe = weakTypeOf[T]

    val q"${methodName: String}" = methodSelector

    val method = TermName(methodName)

    q"{(c: $tpe) => c.$method}"
  }

}
