package karazin.scala.users.group.week4.homework

object errors:

  type DomainErrors = List[DomainError]
  sealed trait DomainError:
    def errorMessage: String

  case object FilterError extends DomainError:
    def errorMessage: String = "The object doesn't meet the filter condition"

  case object UsernameHasSpecialCharacters extends DomainError:
    def errorMessage: String = "Username cannot contain special characters."

  case object PasswordDoesNotMeetCriteria extends DomainError:
    def errorMessage: String =
      """
        |Password must be at least 10 characters long, including an uppercase and a lowercase letter,
        |one number and one special character.
        |""".stripMargin

  case object EmailDoesNotMeetCriteria extends DomainError:
    def errorMessage: String =
      """
        |Invalid email format. An email must contain only letters, numbers,
        |and valid special characters, with a single "@" symbol and a valid domain.
        |""".stripMargin

  case object InvalidAgeRange extends DomainError:
    def errorMessage: String = "Age must be between 18 and 100."

  case object PhoneNumberDoesNotMeetCriteria extends DomainError:
    def errorMessage: String =
      """
        |Invalid phone number format. A phone number must be in international format
        |and can start with a "+" followed by 1 to 14 digits.
        |""".stripMargin