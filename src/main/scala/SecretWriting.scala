import scala.io.Source
import scala.util.Try

trait Codec {
  type Codec = String => String
  type Dictionary = Map[Char, Char]

  val theCode: Dictionary =
    (('a' to 'z').map(c => (c, ('z' - c + 'a').toChar)).toMap ++
      ('A' to 'Z').map(C => (C, ('Z' - C + 'A').toChar)).toMap)
      .withDefault(identity)

  lazy val reverseCode: Dictionary =
    theCode.toSeq.map(_.swap).toMap.withDefault(identity)

  private def run(code: Dictionary)(input: String): String =
    input.grouped(3).map(_.map(code).reverse).mkString

  val encode: Codec = run(theCode)
  val decode: Codec = run(reverseCode)

}

object Main extends Codec {

  def linesOf(filePath: String): Either[Throwable, List[String]] =
    Try(Source.fromFile(filePath).getLines().toList).toEither

  def main(args: Array[String]): Unit = {
    val program: Either[String, List[String]] = for {
      e <- args.headOption.collect {
        case "-e" | "--encode" => true
        case "-d" | "--decode" => false
      }
        .toRight("Must provide -e[ncode] or -d[ecode] option")

      fileName <- Try(args(1)).toEither.left.map(_ => "Must specify file path as argument")

      lines <- linesOf(fileName).left.map("Failed to read the input file: " + _.getMessage)

    } yield lines.map(if (e) encode else decode)

    program.fold(System.err.println, _.foreach(println))
  }
}
