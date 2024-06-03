package karazin.scala.users.group.week2.homework

import munit.ScalaCheckSuite
import org.scalacheck.Prop.*
import karazin.scala.users.group.week2.homework.adt.ErrorOr
import karazin.scala.users.group.model.DummyError
import adt.ErrorOr.*

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
  property("flatmap returns Error  if `f` fails") {
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

  // Fix ??? according to your naming
  // Check the property when ErrorOr represents some value case
  property("withFilter returns Some if applied to Some and predicate is true") {
    forAll { (v: String) =>
      ErrorOr(v).withFilter(_ => true) == ErrorOr(v)
    }
  }

  // Fix ??? according to your naming
  // Check the property when ErrorOr represents some value case
  property("withFilter returns Error if applied to Some and predicate is false") {
    forAll { (v: Int) =>
      Some(v).withFilter(_ => false) match {
        case Error(error) if error.isInstanceOf[Throwable] => assert(true)
        case _ => fail("Error")
      }
    }
  }

  // Fix ??? according to your naming
  // Check the property when ErrorOr represents some error case
  property("withFilter returns Error if applied to Error and predicate is true") {
    forAll { (v: Int) =>
      Error(DummyError).withFilter(_ => true) == Error(DummyError)
    }
  }

  // Fix ??? according to your naming
  // Check the property when ErrorOr represents some error case
  property("withFilter returns Error if applied to Error and predicate is false") {
    forAll { (v: Int) =>
      Error(DummyError).withFilter(_ => false) == Error(DummyError)
    }
  }

  // Fix ??? according to your naming
  // Check the property when a nested ErrorOr represents some value case
  property("flatten returns Some if applied to a nested Some") {
    forAll { (v: String) =>
      ErrorOr(ErrorOr(v)).flatten == ErrorOr(v)
    }
  }

  // Fix ??? according to your naming
  // Check the property when a nested ErrorOr represents some error case
  property("flatten returns Error if applied to a nested Error") {
    forAll { (v: Int) =>
      ErrorOr(Error(DummyError)).flatten == Error(DummyError)
    }
  }

  // Fix ??? according to your naming
  // Check the property when ErrorOr represents some error case
  property("flatten returns Error if applied to Error") {
    Error(DummyError).flatten == Error(DummyError)
  }

  // Fix ??? according to your naming
  // Check the property when a nested value is not an ErrorOr
  property("flatten compilation fails if applied to a none-ErrorOr nested") {
    assertNoDiff(
      compileErrors("ErrorOr(42).flatten"),
      """error:
        |Cannot prove that Int <:< karazin.scala.users.group.week2.homework.adt.ErrorOr[U]
        |
        |where:    U is a type variable with constraint 
        |.
        |ErrorOr(42).flatten
        |                  ^
        |""".stripMargin
    )
  }

  // Fix ??? according to your naming
  // Check the property when ErrorOr represents some value case
  property("foreach applies side effect if applied to Some") {
    forAll { (v: String) =>
      var sideEffect = false
      ErrorOr(v).foreach(_ => sideEffect = true)
      sideEffect
    }
  }

  // Fix ??? according to your naming
  // Check the property when ErrorOr represents some error case
  property("foreach ignores side effect if applied to Error") {
    propBoolean {
      var sideEffect = true
      Error(DummyError).foreach(_ => sideEffect = false)
      sideEffect
    }
  }

  // Fix ??? according to your naming
  // Check the property when ErrorOr represents some value case
  property("fold returns the result of the function application if applied to Some") {
    forAll { (v: Int, default: Int, f: Int => String) =>
      ErrorOr(v).fold(default)(f) == f(v)
    }
  }

  // Fix ??? according to your naming
  // Check the property when ErrorOr represents some error case
  property("fold returns default if applied to Error") {
    forAll { (default: Int, f: Int => String) =>
      Error(DummyError).fold(default)(f) == default
    }
  }

  // Fix ??? according to your naming
  // Check the property when ErrorOr represents some value case
  property("foldLeft returns the result of the function application if applied to Some") {
    forAll { (v: Int, acc: Int, f: (Int, Int) => Int) =>
      ErrorOr(v).foldLeft(acc)(f) == f(acc, v)
    }
  }

  // Fix ??? according to your naming
  // Check the property when ErrorOr represents some error case
  property("foldLeft returns acc if applied to Error") {
    forAll { (acc: Int, f: (Int, Int) => Int) =>
      Error(DummyError).foldLeft(acc)(f) == acc
    }
  }

}
