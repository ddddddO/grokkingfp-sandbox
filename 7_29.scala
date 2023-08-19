object scala729 {
  case class Artist(name: String, genre: MusicGenre, origin: Location, yearsActive: YearsActive)

  enum MusicGenre {
    case HeavyMetal
    case Pop
    case HardRock
  }
  import MusicGenre._

  opaque type Location = String
  object Location {
    def apply(value: String): Location = value
    extension (a: Location) def name: String = a
  }

  enum YearsActive {
    case StillActive(since: Int)
    case ActiveBetween(start: Int, end: Int)
  }
  import YearsActive._

  def activeLength(artist: Artist, currentYear: Int): Int = {
    artist.yearsActive match {
      case ActiveBetween(start, end) => end - start
      case StillActive(since) => currentYear - since
    }
  }

  def test(): Unit = {
    assert(activeLength(Artist("B'z", HardRock, Location("ja"), StillActive(1980)), 2023) == 43)
  }                                                                                                                             
  // scala> scala729.test()
  //                         <- succeeded
}
