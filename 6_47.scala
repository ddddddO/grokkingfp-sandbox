object scala647 {
  case class TvShow(title: String, start: Int, end: Int)

  // 6_29.scalaのエラー原因を返す版
  def parseShows(rawShows: List[String]): Either[String, List[TvShow]] = {
    val initial: Either[String, List[TvShow]] = Right(List.empty)
    rawShows.map(parseShow)
            .foldLeft(initial)(addOrResign)  
  }
  // scala> scala647.parseShows(List("aaa"))
  // val res0: Either[String, List[scala647.TvShow]] = Left(Can't extract name from aaa)                                                                                                                        
  // scala> scala647.parseShows(List("aaa (2020)", "bbb (2005-2008)"))
  // val res1: Either[String, List[scala647.TvShow]] = Right(List(TvShow(aaa,2020,2020), TvShow(bbb,2005,2008)))                                                                                                                        
  // scala> scala647.parseShows(List("aaa (2020)", "bbb (2005-2008)", "xxx"))
  // val res2: Either[String, List[scala647.TvShow]] = Left(Can't extract name from xxx)

  def addOrResign(parsedShows: Either[String, List[TvShow]], newParsedShow: Either[String, TvShow]): Either[String, List[TvShow]] = {
    for {
      shows <- parsedShows
      parsedShow <- newParsedShow
    } yield shows.appended(parsedShow)
  }

  def parseShow(rawShow: String): Either[String, TvShow] = {
    for {
      name <- extractName(rawShow)
      yearStart <- extractYearStart(rawShow).orElse(extractSingleYear(rawShow))
      yearEnd <- extractYearEnd(rawShow).orElse(extractSingleYear(rawShow))
    } yield TvShow(name, yearStart, yearEnd)
  }

  def extractName(rawShow: String): Either[String, String] = {
    val bracketOpen = rawShow.indexOf('(')
    if (bracketOpen > 0)
      Right(rawShow.substring(0, bracketOpen).trim)
    else Left(s"Can't extract name from $rawShow")
  }

  def extractYearStart(rawShow: String): Either[String, Int] = {
    val bracketOpen = rawShow.indexOf('(')
    val dash = rawShow.indexOf('-')
    for {
      yearStr <- if (bracketOpen != -1 && dash > bracketOpen + 1)
                  Right(rawShow.substring(bracketOpen + 1, dash))
                else Left(s"Can't extract start year from $rawShow")
      year    <- yearStr.toIntOption.toRight(s"Can't parse $yearStr")
    } yield year
  }

  def extractYearEnd(rawShow: String): Either[String, Int] = {
    val dash = rawShow.indexOf('-')
    val bracketClose = rawShow.indexOf(')')
    for {
      yearStr <- if (dash != -1 && bracketClose > dash + 1)
                  Right(rawShow.substring(dash + 1, bracketClose))
                else Left(s"Can't extract end year from $rawShow")
      year    <- yearStr.toIntOption.toRight(s"Can't parse $yearStr")
    } yield year
  }

  def extractSingleYear(rawShow: String): Either[String, Int] = {
    val dash = rawShow.indexOf('-')
    val bracketOpen = rawShow.indexOf('(')
    val bracketClose = rawShow.indexOf(')')
    for {
      yearStr <- if (dash == -1 && bracketOpen != -1 && bracketClose > bracketOpen + 1)
                  Right(rawShow.substring(bracketOpen + 1, bracketClose))
                else Left(s"Can't extract single year from $rawShow")
      year <- yearStr.toIntOption.toRight(s"Can't parse $yearStr")
    } yield year
  }
}
