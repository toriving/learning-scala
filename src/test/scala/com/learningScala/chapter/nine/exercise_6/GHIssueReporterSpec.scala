package com.learningScala.chapter.nine.exercise_5
import java.io.ByteArrayOutputStream
import org.scalatest._

trait PrintlnTesting {

  /**
   * Captures and returns all stdout / println output.
   * @param f a function with no input or return values
   * @return the text printed to stdout while executing the function
   */
  def withPrintlnCapture(f: => Unit): String = {
    val buffer = new ByteArrayOutputStream()
    Console.withOut(buffer)(f)
    buffer.toString
  }
}

class GHIssueReporterSpec extends FlatSpec with Matchers with PrintlnTesting {

  import com.learningScala.chapter.nine.exercise_5.GHIssueReporter._


  "The GHIssueReporter app" should "catch invalid input" in {
    val sp = " *"

    withPrintlnCapture { main(Array("")) } should include("Usage: GHIssueReporter user/repo")
    withPrintlnCapture { main(Array("hohoho")) } should include("Usage: GHIssueReporter user/repo")
    withPrintlnCapture { main(Array("hi", "there")) } should include("Usage: GHIssueReporter user/repo")
    withPrintlnCapture { main(Array("hi", "there", "everyone")) } should include("Usage: GHIssueReporter user/repo")
    withPrintlnCapture { main(Array("hi/there", "everyone")) } should include("Usage: GHIssueReporter user/repo")
  }

  it should "parse the number of issues to report" in {
    val output = withPrintlnCapture { main(Array("slick/slick","1")) }
    output should not include "Usage: GHIssueReporter user/repo"
    output should include ("Comments")
    output should include ("Labels")
  }

  it should "build a report from a list of issues" in {
    val issues = GithubIssue.parseIssuesFromJson(sampleJsonIssue)
    val report = buildReport(issues)
    report should include("|4239   |")
    report should include("|Trivial refactoring of scala / actors                                 |")
    report should include("|jxcoder        |")
    report should include("|5        |")
    report should include("|reviewed,tested     |")
  }

  it should "format a single issue into a line of text" in {
    val issues = GithubIssue.parseIssuesFromJson(sampleJsonIssue)
    val report = formatIssue(issues.head)
    report should include("|4239   |")
    report should include("|Trivial refactoring of scala / actors                                 |")
    report should include("|jxcoder        |")
    report should include("|5        |")
    report should include("|reviewed,tested     |")
  }

  it should "read issues live from Github" in {
    val json = githubClosedIssues("slick", "slick", 3)
    json should not equal ""
    json should include("milestone")
    json should include("api.github.com")
    json should include("created_at")
    json should include("organizations_url")

  }

  "The GithubIssue object" should "parse a JSON string into a new instance" in {
    val issues = GithubIssue.parseIssuesFromJson(sampleJsonIssue)
    issues.size should equal(1)

    val issue = issues.head
    issue.number should equal(4239)
    issue.title should include("Trivial refactoring of scala")
    issue.user.login should equal("jxcoder")
    issue.labels.map(_.name) should contain("reviewed")
    issue.labels.map(_.name) should contain("tested")
  }



  val sampleJsonIssue = """[{"url": "https://api.github.com/repos/scala/scala/issues/4239","labels_url": "https://api.github.com/repos/scala/scala/issues/4239/labels{/name}","comments_url": "https://api.github.com/repos/scala/scala/issues/4239/comments","events_url": "https://api.github.com/repos/scala/scala/issues/4239/events","html_url": "https://github.com/scala/scala/pull/4239","id": 53791036,"number": 4239,"title": "Trivial refactoring of scala / actors","user": {"login": "jxcoder","id": 1075547,"avatar_url": "https://avatars.githubusercontent.com/u/1075547?v=3","gravatar_id": "","url": "https://api.github.com/users/jxcoder","html_url": "https://github.com/jxcoder","followers_url": "https://api.github.com/users/jxcoder/followers","following_url": "https://api.github.com/users/jxcoder/following{/other_user}","gists_url": "https://api.github.com/users/jxcoder/gists{/gist_id}","starred_url": "https://api.github.com/users/jxcoder/starred{/owner}{/repo}","subscriptions_url": "https://api.github.com/users/jxcoder/subscriptions","organizations_url": "https://api.github.com/users/jxcoder/orgs","repos_url": "https://api.github.com/users/jxcoder/repos","events_url": "https://api.github.com/users/jxcoder/events{/privacy}","received_events_url": "https://api.github.com/users/jxcoder/received_events","type": "User","site_admin": false},"labels": [{"url": "https://api.github.com/repos/scala/scala/labels/reviewed","name": "reviewed","color": "02e10c"},{"url": "https://api.github.com/repos/scala/scala/labels/tested","name": "tested","color": "d7e102"}],"state": "closed","locked": false,"assignee": null,"milestone": {"url": "https://api.github.com/repos/scala/scala/milestones/45","labels_url": "https://api.github.com/repos/scala/scala/milestones/45/labels","id": 899891,"number": 45,"title": "2.11.6","description": "Merge to 2.11.x.\r\n\r\nRelease by end of Q1 2015.","creator": {"login": "adriaanm","id": 91083,"avatar_url": "https://avatars.githubusercontent.com/u/91083?v=3","gravatar_id": "","url": "https://api.github.com/users/adriaanm","html_url": "https://github.com/adriaanm","followers_url": "https://api.github.com/users/adriaanm/followers","following_url": "https://api.github.com/users/adriaanm/following{/other_user}","gists_url": "https://api.github.com/users/adriaanm/gists{/gist_id}","starred_url": "https://api.github.com/users/adriaanm/starred{/owner}{/repo}","subscriptions_url": "https://api.github.com/users/adriaanm/subscriptions","organizations_url": "https://api.github.com/users/adriaanm/orgs","repos_url": "https://api.github.com/users/adriaanm/repos","events_url": "https://api.github.com/users/adriaanm/events{/privacy}","received_events_url": "https://api.github.com/users/adriaanm/received_events","type": "User","site_admin": false},"open_issues": 26,"closed_issues": 9,"state": "open","created_at": "2014-12-11T01:11:35Z","updated_at": "2015-01-10T14:22:22Z","due_on": "2016-03-25T07:00:00Z","closed_at": null},"comments": 5,"created_at": "2015-01-08T19:36:11Z","updated_at": "2015-01-09T02:09:25Z","closed_at": "2015-01-08T21:17:45Z","pull_request": {"url": "https://api.github.com/repos/scala/scala/pulls/4239","html_url": "https://github.com/scala/scala/pull/4239","diff_url": "https://github.com/scala/scala/pull/4239.diff","patch_url": "https://github.com/scala/scala/pull/4239.patch"},"body": "Updated versions from 2013 to 2015.\r\nRemoved empty lines at the end of file."}]"""
}


