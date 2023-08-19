case class TvShow(title: String, start: Int, end: Int)

def extractSingleYearOrEndYear(rawShow: String): Option[Int] = {
  extractSingleYear(rawShow).orElse(extractYearEnd(rawShow))
}
// scala> extractSingleYearOrEndYear("Breaking Bad (-2013)")
// val res65: Option[Int] = Some(2013)                                                                                                                  
// scala> extractSingleYear("Breaking Bad (-2013)")
// val res66: Option[Int] = None

def extractAnyYear(rawShow: String): Option[Int] = {
  extractYearStart(rawShow)
  .orElse(extractYearEnd(rawShow))
  .orElse(extractSingleYear(rawShow))
}

def extractSingleYearIfNameExists(rawShow: String): Option[Int] = {
  extractName(rawShow).flatMap(name => extractSingleYear(rawShow))
}

def extractAnyYearIfNameExists(rawShow: String): Option[Int] = {
  extractName(rawShow).flatMap(name => extractAnyYear(rawShow))
}

// ------

def parseShow(rawShow: String): Option[TvShow] = {
  for {
    name <- extractName(rawShow)
    yearStart <- extractYearStart(rawShow)
    yearEnd <- extractYearEnd(rawShow)
  } yield TvShow(name, yearStart, yearEnd)
}
// scala> parseShow("Breaking Bad (2008-2013)")
// val res61: Option[TvShow] = Some(TvShow(Breaking Bad,2008,2013))

def extractName(rawShow: String): Option[String] = {
  val bracketOpen = rawShow.indexOf('(')
  if (bracketOpen > 0) Some(rawShow.substring(0, bracketOpen).trim)
  else None
}
// scala> extractName("Breaking Bad (2008-2013)")
// val res57: Option[String] = Some(Breaking Bad)

def extractYearStart(rawShow: String): Option[Int] = {
  val bracketOpen = rawShow.indexOf('(')
  val dash = rawShow.indexOf('-')
  for {
    yaerStr <- if (bracketOpen != -1 && dash > bracketOpen + 1) Some(rawShow.substring(bracketOpen + 1, dash))
               else None
    year    <- yaerStr.toIntOption
  } yield year
}
// scala> extractYearStart("Breaking Bad (2008-2013)")
// val res59: Option[Int] = Some(2008)

def extractYearEnd(rawShow: String): Option[Int] = {
  val dash = rawShow.indexOf('-')
  val bracketClose = rawShow.indexOf(')')
  for {
    yearStr <- if (dash != -1 && bracketClose > dash + 1) Some(rawShow.substring(dash + 1, bracketClose))
               else None
    year    <- yearStr.toIntOption
  } yield year
}
// scala> extractYearEnd("Breaking Bad (2008-2013)")
// val res60: Option[Int] = Some(2013)

def extractSingleYear(rawShow: String): Option[Int] = {
  val dash = rawShow.indexOf('-')
  val bracketOpen = rawShow.indexOf('(')
  val bracketClose = rawShow.indexOf(')')
  for {
    yearStr <- if (dash == -1 && bracketOpen != -1 && bracketClose > bracketOpen + 1) Some(rawShow.substring(bracketOpen + 1, bracketClose))
               else None
    year <- yearStr.toIntOption
  } yield year
}
// scala> extractSingleYear("Chernoby1 (2019)")
// val res62: Option[Int] = Some(2019)
