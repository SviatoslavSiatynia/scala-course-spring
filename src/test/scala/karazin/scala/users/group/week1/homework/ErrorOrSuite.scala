package karazin.scala.users.group.week1.homework

import munit.ScalaCheckSuite
import org.scalacheck.Prop.*
import adt.ErrorOr
import adt.ErrorOr.Some
import adt.ErrorOr.Error
import karazin.scala.users.group.model.DummyError

class ErrorOrSuite extends ScalaCheckSuite {

  // Fix ??? according to your naming
  property("applying pure value returns Some") {
    forAll { (v: String) =>
      ErrorOr(v) == Some(v)
    }
  }

  // Fix ??? according to your naming
  property("applying value which throws an exception returns Error") {
    forAll { (throwable: Throwable) =>
      ErrorOr.apply(throw throwable) == Error(throwable)
    }
  }

  // Fix ??? according to your naming
  // Check the property when ErrorOr represents some non-error case and function returns non-error case
  property("flatmap returns Some if `f` returns Some") {
    forAll { (v: Int, f: Int => String) =>
      ErrorOr(v).flatMap(v => ErrorOr(f(v))) == ErrorOr(f(v))
    }
  }

  // Fix ???? according to your naming
  // Check the property when ErrorOr represents some non-error case but function returns error case
  property("flatmap returns Error if `f` returns Error") {
    forAll { (v: Int, throwable: Throwable) =>
      ErrorOr(v).flatMap(v => throw throwable) == Error(throwable)
    }
  }

  // Fix ??? according to your naming
  // Check property when ErrorOr represents some non-error case
  // and function returns non-error case but execution of function fails with some exception
  property("flatmap returns Error if `f` fails") {
    forAll { (v: Int) =>
      ErrorOr(v).flatMap(v => throw DummyError) == Error(DummyError)
    }
  }

  // Fix ??? according to your naming
  // Check the property when ErrorOr represents some error case
  property("flatmap returns Error immediately") {
    propBoolean {
      Error(DummyError).flatMap(v => throw DummyError) == Error(DummyError)
    }
  }

  // Fix ??? according to your naming
  // Check the property when ErrorOr represents some non-error case and function returns non-error case
  property("map returns Some if `f` returns Some") {
    forAll { (v: Int, f: Int => String) =>
      Some(v).map(v => f(v)) == Some(f(v))
    }
  }

  // Fix ??? according to your naming
  // Check property when ErrorOr represents some non-error case
  // and function returns non-error case but execution of function fails with some exception
  property("map returns Error if `f` fails") {
    forAll { (v: Int) =>
      ErrorOr(v).map(v => throw DummyError) == Error(DummyError)
    }
  }

  // Fix ??? according to your naming
  // Check the property when ErrorOr represents some error case
  property("map returns Error immediately") {
    propBoolean {
      Error(DummyError).flatMap(v => throw DummyError) == Error(DummyError)
    }
  }

}
