package karazin.scala.users.group.week3.homework

import karazin.scala.users.group.week3.Monad
import karazin.scala.users.group.week3.homework.model.Post.PostId
import karazin.scala.users.group.week3.homework.model.User.UserId

import java.util.UUID
import scala.concurrent.{ExecutionContext, Future}

// Do not forget to import custom implementation
import karazin.scala.users.group.week3.homework.adt.*
import karazin.scala.users.group.week3.homework.model.*


object services:

  // Emulation
  def getUserProfile(apiKey: String)
                    (using EC: ExecutionContext): ErrorOrT[Future, UserProfile] =
    ErrorOrT[Future, UserProfile](
      Future {
        /*
         We are using `println` for simplicity.
         Don't do this in a production ready code.
        */
        println("Start getting user profile...")

        /*
         We are using `println` for simplicity.
         Don't do this in a production ready code.
        */
        println(s"Current thread for `getUserProfile`: ${Thread.currentThread().getName}")

        Thread.sleep(2000)

        /*
         We are using `println` for simplicity.
         Don't do this in a production ready code.
        */
        println("Got user profile...")

        ErrorOr.Some(UserProfile(UserId.generate))
      }(EC).recover {
        case e: Exception =>
          ErrorOr.Error(e)
      }
    )

  // Emulation
  def getPosts(userId: UserId)
              (using EC: ExecutionContext): ErrorOrT[Future, List[Post]] =
    ErrorOrT[Future, List[Post]](
      Future {
        /*
         We are using `println` for simplicity.
         Don't do this in a production ready code.
        */
        println("Start getting posts...")

        /*
         We are using `println` for simplicity.
         Don't do this in a production ready code.
        */
        println(s"Current thread for `getPosts`: ${Thread.currentThread().getName}")

        Thread.sleep(2000)

        /*
         We are using `println` for simplicity.
         Don't do this in a production ready code.
        */
        println("Got posts...")

        ErrorOr.Some(Post(userId, PostId.generate) :: Nil)
      }(EC).recover {
        case e: Exception =>
          ErrorOr.Error(e)
      }
    )

  // Emulation
  def getComments(postId: PostId)
                 (using EC: ExecutionContext):  ErrorOrT[Future, List[Comment]] =
    ErrorOrT[Future, List[Comment]](
      Future {
        /*
         We are using `println` for simplicity.
         Don't do this in a production ready code.
        */
        println("Start getting comments...")

        /*
         We are using `println` for simplicity.
         Don't do this in a production ready code.
        */
        println(s"Current thread for `getComments`: ${Thread.currentThread().getName}")

        Thread.sleep(2000)

        /*
         We are using `println` for simplicity.
         Don't do this in a production ready code.
        */
        println("Got comments...")

        ErrorOr.Some(Comment(UserId.generate, PostId.generate) :: Nil)
      }(EC).recover {
        case e: Exception =>
          ErrorOr.Error(e)
      }
    )

  // Emulation
  def getLikes(postId: PostId)
              (using EC: ExecutionContext):  ErrorOrT[Future, List[Like]] =
    ErrorOrT[Future, List[Like]](
      Future {
        /*
         We are using `println` for simplicity.
         Don't do this in a production ready code.
        */
        println("Start getting likes...")

        /*
         We are using `println` for simplicity.
         Don't do this in a production ready code.
        */
        println(s"Current thread for `getLikes`: ${Thread.currentThread().getName}")

        Thread.sleep(2000)

        /*
         We are using `println` for simplicity.
         Don't do this in a production ready code.
        */
        println("Got likes...")

        ErrorOr.Some(Like(UserId.generate, PostId.generate) :: Nil)
      }(EC).recover {
        case e: Exception =>
          ErrorOr.Error(e)
      }
    )
  def getShares(postId: PostId)
               (using EC: ExecutionContext):  ErrorOrT[Future, List[Share]] =
    ErrorOrT[Future, List[Share]](
      Future {
        /*
         We are using `println` for simplicity.
         Don't do this in a production ready code.
        */
        println("Start getting shares...")

        /*
         We are using `println` for simplicity.
         Don't do this in a production ready code.
        */
        println(s"Current thread for `getShares`: ${Thread.currentThread().getName}")

        Thread.sleep(2000)

        /*
         We are using `println` for simplicity.
         Don't do this in a production ready code.
        */
        println("Got shares...")

        ErrorOr.Some(Share(UserId.generate, PostId.generate) :: Nil)
      }(EC).recover {
        case e: Exception =>
          ErrorOr.Error(e)
      }
    )
