package karazin.scala.users.group.week4.homework

import cats.data.ValidatedNec
import cats.syntax.all.*
import karazin.scala.users.group.week4.homework.errors.{DomainError, PasswordDoesNotMeetCriteria, UsernameHasSpecialCharacters, EmailDoesNotMeetCriteria, InvalidAgeRange, PhoneNumberDoesNotMeetCriteria}
import karazin.scala.users.group.week4.homework.model.RegistrationData

object validation:

  type ValidationResult[A] = ValidatedNec[DomainError, A]

  def validateUsername(username: String): ValidationResult[String] =
    if username.matches("^[a-zA-Z0-9]+$") then username.validNec
    else UsernameHasSpecialCharacters.invalidNec

  def validatePassword(password: String): ValidationResult[String] =
    if password.matches("(?=^.{10,}$)((?=.*\\d)|(?=.*\\W+))(?![.\\n])(?=.*[A-Z])(?=.*[a-z]).*$") then password.validNec
    else PasswordDoesNotMeetCriteria.invalidNec

  def validateEmail(email: String): ValidationResult[String] =
    if email.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$") then email.validNec
    else EmailDoesNotMeetCriteria.invalidNec

  def validateAge(age: Int): ValidationResult[Int] =
    if age >= 18 && age <= 100 then age.validNec
    else InvalidAgeRange.invalidNec

  def validatePhone(phone: String): ValidationResult[String] =
    if phone.matches("^\\+?[1-9]\\d{1,14}$") then phone.validNec
    else PhoneNumberDoesNotMeetCriteria.invalidNec

  def validateRegistrationForm(username: String, password: String, email: String, age: Int, phone: String): Either[List[DomainError], RegistrationData] =
    (validateUsername(username),
      validatePassword(password),
      validateEmail(email),
      validateAge(age),
      validatePhone(phone)).mapN(RegistrationData.apply).toEither.leftMap(_.toList)
