package uk.co.devproltd.experiments

import cats.implicits._
import com.monovore.decline.Opts._
import com.monovore.decline._

import scala.io.Source

object MyApp extends Codec {

  val fileNameArgument = argument[String]("file")
  val encodeFlag = flag("encode", "encode the text specified", "e").map(_ => Encode)
  val decodeFlag = flag("decode", "decode the text specified", "d").map(_ => Decode)
  val operation = encodeFlag orElse decodeFlag

  sealed abstract class Operation(val fn: Codec)

  case object Encode extends Operation(encode)

  case object Decode extends Operation(decode)

  def linesOf(filePath: String): List[String] = {
    val fileSource = Source.fromFile(filePath)
    try {
      fileSource.getLines().toList
    } finally {
      fileSource.close()
    }
  }

  def main: Opts[Unit] = (fileNameArgument, operation).mapN { (filePath, op) =>
    linesOf(filePath).map(op.fn).foreach(println)
  }

}

object SecretWriting extends CommandApp(
  name = "Secret Writing",
  header =
    """
      |                        _
      | ___  ___  ___ _ __ ___| |_
      |/ __|/ _ \/ __| '__/ _ \ __|
      |\__ \  __/ (__| | |  __/ |_
      ||___/\___|\___|_|  \___|\__| Writing
      |
      |""".stripMargin,
  main = MyApp.main,
  version = "0.1.0"
)
