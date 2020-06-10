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


  def main(args: Array[String]): Unit = {

    val xml = githubRss("toriving", "learning-scala", "master")

    val entries = xmlToEntryList(xml); println(s"Got ${entries.size} entries")
    val firstTitle = child(entries(0), "title")
    val firstReport = report(entries(0))
    val slickReport = getGithubReport("toriving", "learning-scala", "master")

    println(xml)
    println(entries)
    println(firstTitle)
    println(firstReport)
    println(slickReport)

  }
}
