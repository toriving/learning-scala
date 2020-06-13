package com.learningScala.chapter.seven.exercise_6

/**
 * 프로젝트를 위해 최근 GitHub 커밋(commit)을 전달하는 함수를 작성하라.
 *  GitHub은 특정 사용자, 리포지토리, 브랜치의 최근 커밋에 대한 RSS 피드를 제공하는데,
 *  여기에는 정규 표현식으로 파싱할 수 있는 XML이 포함되어 있다.
 *  여러분의 함수는 사용자, 리포지토리, 브랜치를 받아서 RSS 피드를 읽어 들이고 파싱한 다음, 커밋 정보를 출력해야 한다.
 *  커밋 정보에는 각 커밋에 대한 날짜, 제목, 저자가 포함되어야 한다.
 *  여러분은 다음 RSS URL을 이용하여 주어진 리포지토리와 브랜치의 최근 커밋을 가져올 수 있다.
 *  https://github.com/<user name>/<repo name>/commits/<branch name>.atom
 *  다음은 RSS 피드를 단일 문자열로 잡아내는 한 가지 방법을 보여준다.
 *  scala> val u = "https://github.com/scala/scala/commits/2.11.x.atom"
 *  u: String = https://github.com/scala/scala/commits/2.11.x.atom
 *
 *  scala> val s = io.Source.fromURL(u)
 *  s: scala.io.BufferedSource = non-empty iterator
 *
 *  scala> val text = s.getLines.map(_.trim).mkString("")
 *  text: String = <?xml version="1.0" encoding="UTF-8"?><feed xmlns=...
 *
 *  XML로 작업하는 것은 다소 까다롭다. 여러분은 아마도 text.split(<token>)을 사용하여 텍스트를 개별
 *  <entry> 항목으로 분리하고, <title>과 다른 요소들을 파싱하기 위해 정규 표현식 캡처그룹을 사용해볼 수 있다.
 *  또는 XML 파일의 모든 줄을 반복하면서 여러분이 발견한 대로 버퍼에 요소들을 추가하고 이를 새로운 리스트로 전환할 수도 있다.
 */


object sixth {

  def githubRss(user: String, repo: String, branch: String): String = {
    val url = s"https://github.com/$user/$repo/commits/$branch.atom"
    val lines = io.Source.fromURL(url).getLines.toList
    val xml = lines.map(_.trim).mkString("")
    xml
  }

  def xmlToEntryList(xml: String): Array[String] = xml.split("</?entry>").filterNot(_.isEmpty).tail


  def child(xml: String, name: String): Option[String] = {
    val p = s".*<$name>(.*)</$name>.*".r
    xml match {
      case p(result) => Option(result)
      case _ => None
    }
  }

  def report(entryXml: String): Option[String] = {
    for {
      title <- child(entryXml, "title")
      date <- child(entryXml, "updated").map(_.replaceAll("T.*",""))
      author <- child(entryXml, "name")
    }
      yield s"title:  $title\ndate:   $date\nauthor: $author"
  }

  def getGithubReport(user: String, repo: String, branch: String): String = {
    val xml = githubRss(user, repo, branch)
    val entries = xmlToEntryList(xml).toList
    val formattedEntries = entries flatMap report
    val title = s"Github commit activity for $repo:$branch"
    title :: formattedEntries mkString ("\n" + "=" * 80 + "\n")
  }


  // a: 사용자, 리포지토리, 브랜치 매개변수를 튜플 매개변수로 옮겨라.

  def getGithubReport(urb: (String,String,String)): String = {
    val xml = githubRss(urb._1, urb._2, urb._3)
    val entries = xmlToEntryList(xml).toList
    val formattedEntries = entries flatMap report
    val title = s"Github commit activity for ${urb._2}:${urb._3}"
    title :: formattedEntries mkString ("\n" + "=" * 80 + "\n")
  }

  /**
   * b: 연습문제 (a) 다음으로, 함수가 GItHub 프로젝트 리스트를 취해서 명시된 프로젝트 순서대로
   * 각 프로젝트의 커밋에 대한 리포트를 출력하도록 만들어라
   */

  def getGithubCommitReports(urb: (String,String,String)): List[String] = {
    val xml = githubRss(urb._1, urb._2, urb._3)
    val entries = xmlToEntryList(xml).toList
    val branchInfo = s"branch: ${urb._2}:${urb._3}\n"
    entries flatMap report map (branchInfo + _)
  }

  def getGithubReports(urbs: List[(String,String,String)]): String = {
    val commits = urbs flatMap getGithubCommitReports
    val separator = "\n" + "="*60 + "\n"
    val title = s"Github activity for ${urbs map (_._1) mkString (", ")} repos"
    title :: commits mkString separator
  }

  /**
   * c: 연습문제 (b)에 이어, 퓨처를 동시에 사용하여 모든 프로젝트와 커밋 데이터를 가져오고,
   * 그 결과를 기다린(5초이상 기다리지는 않는다) 후에 명시된 프로젝트 순서대로
   * 각 프로젝트에 대한 커밋 리포트를 출력하라
   */

  def getGithubReportsC(urbs: List[(String,String,String)]): String = {
    val commits = List.newBuilder[String]

    import concurrent.ExecutionContext.Implicits.global
    val futures = urbs map { urb =>
      concurrent.Future { commits ++= getGithubCommitReports(urb) }
    }
    val future = concurrent.Future.sequence(futures)

    import concurrent.duration._
    concurrent.Await.result(future, Duration(5, SECONDS))

    val separator = "\n" + "="*60 + "\n"
    val title = s"Github activity for ${urbs map (_._1) mkString (", ")} repos"
    (title :: commits.result) mkString separator
  }

  /**
   * d: 연습문제 (c)에 이어, 커밋을 섞어서 커밋 일자 기준으로 정렬한 후에 추가적인 'repo' 컬럼으로 리포트를 출력하라.
   */

  def getGithubReportsD(urbs: List[(String,String,String)]): String = {
    val commits = List.newBuilder[String]

    import concurrent.ExecutionContext.Implicits.global
    val futures = urbs map { urb =>
      concurrent.Future { commits ++= getGithubCommitReports(urb) }
    }
    val future = concurrent.Future.sequence(futures)

    import concurrent.duration._
    concurrent.Await.result(future, Duration(5, SECONDS))

    val separator = "\n" + "="*60 + "\n"
    val title = s"Github activity for ${urbs map (_._1) mkString (", ")} repos"

    val sortedCommits = commits.result.sortBy { c =>
      c.replaceAll("(?s).*date:   ","").replaceAll("(?s)\\s.*","")
    }.reverse

    (title :: sortedCommits) mkString separator
  }

  def main(args: Array[String]): Unit = {

    val xml = githubRss("toriving", "learning-scala", "master")

    val entries = xmlToEntryList(xml); println(s"Got ${entries.size} entries")
    val firstTitle = child(entries(0), "title")
    val firstReport = report(entries(0))
    val slickReport = getGithubReport("toriving", "learning-scala", "master")

//    println(xml)
//    println(entries)
//    println(firstTitle)
//    println(firstReport)
//    println(slickReport)

    // b

    val slickUrb = ("slick","slick","master")
    val akkaUrb = ("akka","akka","master")
    val scalaUrb = ("scala","scala","2.11.x")
    val scalazUrb = ("scalaz","scalaz","series/7.2.x")
//    println( getGithubReports(List(slickUrb, akkaUrb)) )
//    println( getGithubReportsC(List(scalaUrb, scalazUrb)) )
    println( getGithubReportsD(List(slickUrb, scalazUrb)) )

  }
}
