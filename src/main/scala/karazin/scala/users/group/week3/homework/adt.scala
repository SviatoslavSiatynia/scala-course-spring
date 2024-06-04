package karazin.scala.users.group.week3.homework

import karazin.scala.users.group.week3.{Functor, Monad}

import scala.concurrent.Future

object adt:

  case class ErrorOrT[F[_], A](value: F[ErrorOr[A]]):
    def flatMap[B](f: A => ErrorOrT[F, B])(using M: Monad[F]): ErrorOrT[F, B] =
      ErrorOrT(M.flatMap[ErrorOr[A], ErrorOr[B]](value) {
          case ErrorOr.Some(v) => f(v).value
          case ErrorOr.Error(e) => M.pure(ErrorOr.Error(e))
        }
      )
      
    def map[B](f: A => B)(using Functor: Functor[F]): ErrorOrT[F, B] =
      ErrorOrT(Functor.map(value)(v => v.map(f)))

    def withFilter(p: A => Boolean)(using Functor: Functor[F]): ErrorOrT[F, A] =
      ErrorOrT(Functor.map(value) (v => v.withFilter(p)))

  enum ErrorOr[+V]:

    /*
      Two case must be defined:
      * a case for a regular value
      * a case for an error (it should contain an actual throwable)
     */

    case Some(v: V) extends ErrorOr[V]

    case Error(e: Throwable) extends ErrorOr[Nothing]

    /*
      The method is used for defining execution pipelines
      Provide a type parameter, an argument and a result type

      Make sure that if an internal function is failed with an exception
      the exception is not thrown but the case for an error is returned
    */
    def flatMap[Q](f: V => ErrorOr[Q]): ErrorOr[Q] =
      this match
        case Error(err) => Error(err)
        case Some(v) =>
          try
            f(v)
          catch
            case ex: Throwable => Error(ex)

    /*
      The method is used for changing the internal object
      Provide a type parameter, an argument and a result type

      Make sure that if an internal function is failed with an exception
      the exception is not thrown but the case for an error is returned
     */
    def map[Q](f: V => Q): ErrorOr[Q] =
      this match
        case Error(err) => Error(err)
        case Some(v) =>
          try
            ErrorOr.Some(f(v))
          catch
            case ex: Throwable => Error(ex)

    def withFilter(p: V => Boolean): ErrorOr[V] =
      this match
        case ErrorOr.Some(v) => if p(v) then ErrorOr.Some(v) else Error(new Exception())
        case Error(err) => Error(err)

    def flatten[U](using ev: V <:< ErrorOr[U]): ErrorOr[U] =
      this match
        case Error(err) => Error(err)
        case ErrorOr.Some(v) =>
          try
            ev(v)
          catch
            case ex: Throwable => Error(ex)

    def foreach[U](f: V => U): Unit =
      this match
        case ErrorOr.Some(v) => f(v)
        case _ => ()

    def fold[Q](ifEmpty: => Q)(f: V => Q): Q =
      this match
        case ErrorOr.Some(v) => f(v)
        case _ => ifEmpty

    def foldLeft[Q](z: Q)(op: (Q, V) => Q): Q =
      this match
        case ErrorOr.Some(v) => op(z, v)
        case _ => z

    def foldRight[Q](z: Q)(op: (V, Q) => Q): Q =
      this match
        case ErrorOr.Some(v) => op(v, z)
        case _ => z


  // Companion object to define constructor
  object ErrorOr:
    /*
      Provide a type parameter, an argument and a result type

      Make sure that if an internal function is failed with an exception
      the exception is not thrown but the case for an error is returned
    */
    def apply[V](expr: => V): ErrorOr[V] =
      try
        Some(expr)
      catch
        case e: Throwable => Error(e)

