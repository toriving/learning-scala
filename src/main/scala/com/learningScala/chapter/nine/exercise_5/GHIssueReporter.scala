package com.learningScala.chapter.nine.exercise_5
import org.json4s.DefaultFormats
import org.json4s.native.JsonMethods


case class GithubUser(login: String)

case class GithubLabel(name: String)

case class GithubIssue(number: Int, title: String, user: GithubUser, labels: List[GithubLabel], comments: Int)
object GithubIssue {

  implicit val formats = DefaultFormats

  def parseIssuesFromJson(jsonText: String): List[GithubIssue] = {
    JsonMethods.parse(jsonText).extract[List[GithubIssue]]
  }

}




/**
 * The Github Issue Reporter prints a report of recently closed issues in the given github repo.
 */
object GHIssueReporter {

  /**
   * Retrieves the latest closed Github issues and prints a report
   */
  def report(user: String, repo: String, count: Int = 10): Unit = {
    println(s"Creating a report for $user / $repo on $count issues")

    val content: String = githubClosedIssues(user, repo, count)
    val issues: List[GithubIssue] = GithubIssue.parseIssuesFromJson(content)
    val reportContent = buildReport(issues)
    println(reportContent)
  }

  /**
   * Returns a formatted report of the given issues with column names and a horizontal border
   */
  def buildReport(issues: List[GithubIssue]): String = {
    val issueRows = issues map formatIssue
    val maxLength = issueRows.maxBy(_.size).size
    val border = "=" * maxLength

    val rows = formattedHeader :: border :: issueRows
    rows mkString ("\n", "\n", "\n")
  }

  /**
   * Format a Github issue as a single row in the report
   */
  def formatIssue(i: GithubIssue): String = {
    val labelNames = i.labels.map(_.name).mkString(",")
    val fields: List[String] = List(i.number.toString, i.title, i.user.login, i.comments.toString, labelNames)
    val columns = formatFixedWidthColumns(fields)

    columns mkString ("|","|","|")
  }

  /**
   * The report header
   */
  lazy val formattedHeader = {
    val columns = formatFixedWidthColumns(List("Id", "Title", "User", "Comments", "Labels"))
    columns mkString ("|","|","|")
  }

  /**
   * Format the given strings into fixed-width columns for the issue report
   * @param cols a list of the 5 output fields
   * @return the output fields with fixed-width formatting
   */
  def formatFixedWidthColumns(cols: List[String]): List[String] = {
    if (cols.size < 5) cols
    else List(
      f"${cols(0)}%-7.7s",
      f"${cols(1)}%-70.70s",
      f"${cols(2)}%-15.15s",
      f"${cols(3)}%-9.9s",
      f"${cols(4)}%-20.20s"
    )
  }

  /**
   * Return a JSON document of recently closed issues in the given github repo
   */
  def githubClosedIssues(user: String, repo: String, count: Int): String = {

    val url = s"https://api.github.com/repos/$user/$repo/issues?state=closed&per_page=$count"

    val lines = io.Source.fromURL(url).getLines().toList
    val content = lines.map(_.trim).mkString("")
    content
  }


  def main(args: Array[String]): Unit = {

    // These regex patterns ensure the input is valid and parseable
    val userRepoRegex = """(\w+)/(\w+)""".r
    val numIssuesRegex = """(\d+)""".r

    args.toList match {
      case userRepoRegex(user, repo) :: numIssuesRegex(numIssues) :: Nil =>
        report(user, repo, numIssues.toInt)
      case userRepoRegex(user, repo) :: Nil =>
        report(user, repo)
      case _ =>
        println("Usage: GHIssueReporter user/repo [number of issues]")
    }
  }

}