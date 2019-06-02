package uk.co.devproltd.experiments

import cats.implicits._
import com.monovore.decline.Opts._
import com.monovore.decline._

import scala.io.Source

object MyApp extends Codec {

  private val fileNameArgument = argument[String]("file")
  private val encodeFlag = flag("encode", "encode the text in file specified", "e").map(_ => encode)
  private val decodeFlag = flag("decode", "decode the text in file specified", "d").map(_ => decode)
  private val operation = encodeFlag orElse decodeFlag

  val main: Opts[Unit] = (fileNameArgument, operation).mapN { (file, operation) =>
    linesOf(file).foreach(operation andThen println)
  }

  private def linesOf(filePath: String): List[String] = {
    val fileSource = Source.fromFile(filePath)
    try {
      fileSource.getLines().toList
    } finally {
      fileSource.close()
    }
  }

}

object SecretWriting extends CommandApp(
  name = "secret-writing",
  header =
    """|                        _
       | ___  ___  ___ _ __ ___| |_
       |/ __|/ _ \/ __| '__/ _ \ __|
       |\__ \  __/ (__| | |  __/ |_
       ||___/\___|\___|_|  \___|\__| Writing
       |
       |""".stripMargin,
  main = MyApp.main,
  version = "0.1.0"
)
