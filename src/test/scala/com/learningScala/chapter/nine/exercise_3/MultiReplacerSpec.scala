package com.learningScala.chapter.nine.exercise_3
import java.io.File

import com.learningScala.chapter.nine.exercise_2.SafeStringUtils
import org.scalatest._

class MultiReplacerSpec extends FlatSpec with Matchers {

  import MultiReplacer._

  val content = "Twas brillig, and the slithy toves"

  "The MultiReplacer app" should "replace basic patterns" in {
    val testFile = newFile(content)

    main(Array("brill[^,]*", "the night before xmas", testFile.getName))
    read(testFile) should equal("Twas the night before xmas, and the slithy toves")

    main(Array("the slithy.*", "all thru the house", testFile.getName))
    read(testFile) should equal("Twas the night before xmas, and all thru the house")
  }

  it should "create a backup file before replacing text" in {
    val testFile = newFile(content)

    main(Array("brill[^,]*", "the night before xmas", testFile.getName))
    read(testFile) should equal("Twas the night before xmas, and the slithy toves")

    val backupFile = new File(testFile.getName + ".bak")
    read(backupFile) should equal(content)
  }

  it should "create a backup file of any file" in {
    val testFile = newFile(content)
    createBackupFile(content, testFile)
    val backupFile = new File(testFile.getName + ".bak")
    read(backupFile) should equal(read(testFile))
  }

  it should "replace content in a file" in {
    val testFile = newFile(content)

    replaceInFile("Twas brilli", "I was sleepin", testFile)
    read(testFile) should equal("I was sleeping, and the slithy toves")
  }

  it should "replace content in a series of files by file name" in {
    val testFile1 = newFile(content)
    val testFile2 = newFile(content)

    val files = List(testFile1.getName, testFile2.getName)
    replaceInFileNames("Twas", "Twasn't", files)
    read(testFile1) should equal("Twasn't brillig, and the slithy toves")
    read(testFile2) should equal("Twasn't brillig, and the slithy toves")
  }

  private def newFile(content: String): File = {
    val testFile = new File(s"testy_${SafeStringUtils.randomLetters(20)}.txt")
    write(content, testFile)
    testFile
  }

}