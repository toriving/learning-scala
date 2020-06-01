package com.learningScala.chapter.nine.exercise_4
import java.io.{PrintWriter, File}
import org.scalatest._

class FileSummySpec extends FlatSpec with Matchers {

  import FileSummy._

  "The FileSummy app" should "correctly summarize a short file" in {
    val file = newFile("this is is not a test")
    val stats = buildFileStats(file)

    stats.words should equal(6)
    stats.toppies.head should equal("is")
  }

  it should "format the stats correctly" in {
    val file = newFile("this is is not a test")
    val stats = buildFileStats(file)
    val formatted = formatStats(stats)

    formatted should include ("21 chars")
    formatted should include ("6 words")
    formatted should include ("1 paragraphs")
    formatted should include (file.getName)
  }

  it should "recognize paragraphs, ignoring non-word ones" in {
    val contents = """


The fire is slowly dying,
And my dear, we're still good-by-ing.
But, as long as you love me so,
Let It Snow! Let It Snow! Let It snow

{}

Oh, it doesn't show signs of stopping,
And I've brought some corn for popping,
Since the lights are turned way down low,
Let It Snow! Let It Snow! Let It Snow!
    """

    val file = newFile(contents)
    val stats = buildFileStats(file)
    stats.paragraphs should equal(2)
  }


  private def newFile(content: String): File = {
    val testFile = new File(s"summytest_${SafeStringUtils.randomLetters(20)}.txt")
    val writer = new PrintWriter(testFile)
    writer.write(content)
    writer.close()
    testFile
  }

}