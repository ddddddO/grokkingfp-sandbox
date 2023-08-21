object model737 {
  case class Artist(name: String, genre: MusicGenre, origin: Location, yearsActive: YearsActive)

  enum MusicGenre {
    case HeavyMetal
    case Pop
    case HardRock
  }
  import MusicGenre._

  enum YearsActive {
    case StillActive(since: Int)
    case ActiveBetween(start: Int, end: Int)
  }
  import YearsActive._

  opaque type Location = String
  object Location {
    def apply(value: String): Location = value
    extension (a: Location) def name: String = a
  }

  val artists = List(
    Artist("Metallica", HeavyMetal, Location("U.S."), StillActive(since = 1981)),
    Artist("Led Zeppelin", HardRock, Location("England"), ActiveBetween(1968, 1980)),
    Artist("Bee Gees", Pop, Location("England"), ActiveBetween(1958, 2003))
  )
}

object beforeRefactor737 {
  import model737._, model737.YearsActive._

  def wasArtistActive(artist: Artist, yearStart: Int, yearEnd: Int): Boolean = artist.yearsActive match {
    case StillActive(since)        => since <= yearEnd
    case ActiveBetween(start, end) => start <= yearEnd && end >= yearStart
  }

  // 引数を見ても、どのように振舞うかわからない
  def searchArtists(
      artists: List[Artist],
      genres: List[MusicGenre],
      locations: List[Location],
      searchByActiveYears: Boolean,
      activeAfter: Int,
      activeBefore: Int
  ): List[Artist] = artists.filter(artist =>
    (genres.isEmpty || genres.contains(artist.genre)) &&
      (locations.isEmpty || locations.contains(artist.origin)) &&
      (!searchByActiveYears || wasArtistActive(artist, activeAfter, activeBefore))
  )
}

object afterRefactor737 {
  import model737._, model737.YearsActive._, model737.MusicGenre._
  import beforeRefactor737.wasArtistActive

  enum SearchCondition {
    case SearchByGenre(genres: List[MusicGenre])
    case SearchByOrigin(locations: List[Location])
    case SearchByActiveYears(start: Int, end: Int)
  }
  import SearchCondition._

  // SearchCondition のように振る舞いをモデル化することで、どのように振舞うかわかりやすくなる
  // 要件の追加もしやすい。なにより綺麗
  def searchArtists(
      artists: List[Artist],
      requiredConditions: List[SearchCondition]
  ): List[Artist] = artists.filter(artist =>
    requiredConditions.forall(condition =>
      condition match {
        case SearchByGenre(genres)           => genres.contains(artist.genre)
        case SearchByOrigin(locations)       => locations.contains(artist.origin)
        case SearchByActiveYears(start, end) => wasArtistActive(artist, start, end)
      }
    )
  )

  def tmp(): Unit = {
    // 呼び出し例
    assert(searchArtists(
      artists,
      List(
        SearchByGenre(List(Pop)),
        SearchByOrigin(List(Location("England"))),
        SearchByActiveYears(1950, 2022)
      )
    ) == List(
      Artist("Bee Gees", Pop, Location("England"), ActiveBetween(1958, 2003))
    ))
  }
  // scala> afterRefactor737.tmp()
  //                                  <- succeeded!                                                                                             
  // scala> 
}