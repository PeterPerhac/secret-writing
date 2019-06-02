package uk.co.devproltd.experiments

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
