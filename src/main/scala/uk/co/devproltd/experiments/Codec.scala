package uk.co.devproltd.experiments

trait Codec {
  type CodecFn = String => String
  private type Dictionary = Map[Char, Char]

  private val theCode: Dictionary =
    Map(
      'a' -> '౿', 'b' -> 'Ʒ', 'c' -> '～', 'd' -> 'Ʌ', 'e' -> '⫛',
      'f' -> '⭃', 'g' -> '྾', 'h' -> 'Ξ', 'i' -> 'Ф', 'j' -> '₪',
      'k' -> 'Ⴉ', 'l' -> 'Ⴤ', 'm' -> 'Ꮘ', 'n' -> '⊕', 'o' -> '⋈',
      'p' -> '⧰', 'q' -> '※', 'r' -> '⸕', 's' -> '⅄', 't' -> '∅',
      'u' -> '᎖', 'v' -> '℈', 'w' -> '℧', 'x' -> '↯', 'y' -> '҂',
      'z' -> '୰')
      .withDefault(identity)

//  private val theCode: Dictionary =
//    (('a' to 'z').map(c => (c, ('z' - c + 'a').toChar)).toMap ++
//      ('A' to 'Z').map(C => (C, ('Z' - C + 'A').toChar)).toMap)
//      .withDefault(identity)

  private lazy val reverseCode: Dictionary =
    theCode.toSeq.map(_.swap).toMap.withDefault(identity)

  private def run(code: Dictionary)(input: String): String =
    input.grouped(3).map(_.map(code).reverse).mkString

  lazy val encode: CodecFn = run(theCode)
  lazy val decode: CodecFn = run(reverseCode)

}
