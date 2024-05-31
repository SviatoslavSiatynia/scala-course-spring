package karazin.scala.users.group.week3.homework

// Do not forget to import custom implementation
import karazin.scala.users.group.week3.Functor
import karazin.scala.users.group.week3.future.given
import karazin.scala.users.group.week3.homework.adt.*
import karazin.scala.users.group.week3.homework.model.*
import karazin.scala.users.group.week3.homework.model.Post.PostId
import karazin.scala.users.group.week3.homework.model.User.UserId
import karazin.scala.users.group.week3.homework.services.*

import java.util.concurrent.Executors
import scala.concurrent.{ExecutionContext, ExecutionContextExecutorService, Future}
import scala.concurrent.ExecutionContext.global
import scala.util.{Failure, Success}

object program extends App:

  val singleThreadPoolContext1: ExecutionContextExecutorService =
    ExecutionContext.fromExecutorService(Executors.newSingleThreadExecutor())

  val fixedTreadPoolContext1: ExecutionContextExecutorService =
    ExecutionContext.fromExecutorService(Executors.newFixedThreadPool(10))

  val singleThreadPoolContext2: ExecutionContextExecutorService =
    ExecutionContext.fromExecutorService(Executors.newSingleThreadExecutor())

  val fixedTreadPoolContext2: ExecutionContextExecutorService =
    ExecutionContext.fromExecutorService(Executors.newFixedThreadPool(10))

  given ExecutionContext = ExecutionContext.global

  /*
    Replace `ErrorOr[List[PostView]] result type with `ErrorOrT[Future, List[PostView]]
    Provide the implementation for the service
   */
  def getPostsViews(apiKey: String)(commentsFilter: List[Comment] => Boolean,
                                    likesFilter: List[Like] => Boolean)
                   (using ExecutionContext): ErrorOrT[Future, List[PostView]] =
    println(s"Main thread: ${Thread.currentThread().getName}")

    val getUserProfileService = getUserProfile(apiKey)(using fixedTreadPoolContext1)

    for
      profile <- getUserProfileService
      posts <- getPosts(profile.userId)
      postsView <- posts.foldLeft(ErrorOrT(Future.successful(ErrorOr(List.empty[PostView])))) {
        (views, post) => views flatMap (list => getPostView(post)(commentsFilter, likesFilter) map (list :+ _))
      }
    yield postsView

  getPostsViews("apiKey")(_ => true, _ => true).value.onComplete {
    case Success(value)     => println(s"Completed with $value")
    case Failure(exception) => println(s"Failed with $exception")
  }

  Thread.sleep(10000)

  singleThreadPoolContext1.shutdown()
  fixedTreadPoolContext1.shutdown()

  /*
    Replace `ErrorOr[PostView] result type with `ErrorOrT[Future, PostView]
    Provide the implementation for the service
  */
  def getPostView(post: Post)(commentsFilter: List[Comment] => Boolean,
                              likesFilter: List[Like] => Boolean)
                 (using ExecutionContext): ErrorOrT[Future, PostView] =
    println(s"Main thread: ${Thread.currentThread().getName}")

    val getCommentsService = getComments(post.postId)(using fixedTreadPoolContext2)
    val getLikesService = getLikes(post.postId)(using fixedTreadPoolContext2)
    val getSharesService = getShares(post.postId)(using fixedTreadPoolContext2)

    for
      comments <- getCommentsService
      if commentsFilter(comments)
      likes <- getLikesService
      if likesFilter(likes)
      shares <- getSharesService
    yield PostView(post, comments, likes, shares)
