package com.learningScala.chapter.nine.exercise_4
import java.io.File

/**
 * FileSummy is an app that prints a short summary of the content of one or more files
 */
object FileSummy extends FileStatsBuilder with FileStatsFormatting {

  def summarize(file: File): Unit = {
    val stats = buildFileStats(file)
    val formatted = formatStats(stats)
    println(formatted)
  }

  def main(args: Array[String]) {
    val files = args map (new File(_)) filter (_.exists())
    files foreach summarize
  }

}

case class Stats(fileName: String, chars: Int, words: Int, paragraphs: Int, toppies: List[String])

trait FileStatsBuilder {

  def buildFileStats(file: File): Stats = {

    def read(file: File) = io.Source.fromFile(file).getLines().mkString("\n")

    val s: String = read(file).trim

    val words = s.split("""\W+""")
    val paragraphs = s.split("""\w+\W*\n\n""")

    val toppies: List[String] = words
      .map(_.toLowerCase)
      .groupBy(w => w).toList
      .sortBy(_._2.size).reverse
      .map(_._1)
      .take(20)

    Stats(file.getName, s.size, words.size, paragraphs.size, toppies)
  }

}

trait FileStatsFormatting {

  def formatStats(stats: Stats): String = {
    import stats._

    val formatted = s"""File "$fileName" has $chars chars, $words words and $paragraphs paragraphs.
The top 20 words were: ${toppies.mkString(", ")}."""

    formatted
      .replaceAll("\n", " ")
      .replaceAll(", ([^,]*)$", ", and $1")
  }

}