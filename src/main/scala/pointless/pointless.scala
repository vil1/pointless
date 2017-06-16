import cats.{FlatMap, Functor}

import scala.collection.generic.{CanBuildFrom, FilterMonadic}
import scala.collection.immutable.StringOps
import scala.language.higherKinds

package object pointless {

  implicit class Function1Ops[A, B](f: A => B) {
    def &&&[C](g: A => C): A => (B, C) = (a: A) => (f(a), g(a))

    def |||[C](g: C => B): Either[A, C] => B = _.fold[B](f, g)

    def >>>[C](g: B => C): A => C = f andThen g

    def <<<[C](g: C => A): C => B = f compose g

  }

  implicit class PointOps[A](point: A) {
    def |>[AA >: A, B](f: AA => B) : B = f(point)
  }

  def a[T] = new Point[T]

  def id[T]: T => T = (t:T) => t

  private[pointless] case class Alternation[A, B](pred: A => Boolean, ifTrue: A => B) {
    def |(ifFalse: A => B): A => B = (a: A) => if (pred(a)) ifTrue(a) else ifFalse(a)
  }

  implicit class PredicateOps[A](p: A => Boolean) {
    def ?[B](f: A => B) = Alternation(p, f)
  }


  class StdMapper[A, Repr, X <: FilterMonadic[A, Repr]] {
    def apply[B](f: A => B)(implicit ev: CanBuildFrom[Repr, B, Repr], Repr: Repr => X): Repr => Repr = (fa: Repr) => Repr(fa) map f
  }

  class Mapper[F[_]] {
    def apply[A, B](f: A => B)(implicit F:Functor[F]): F[A] => F[B] = F.map(_)(f)
  }

  class FlatMapper[F[_]] {
    def apply[A, B](f: A => F[B])(implicit F: FlatMap[F]): F[A] => F[B] = F.flatMap(_)(f)
  }

  def map[F[_]] = new Mapper[F]

  def flatMap[F[_]] = new FlatMapper[F]

  def mapS = new StdMapper[Char, String, StringOps]
}

