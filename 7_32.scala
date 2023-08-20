object model732 {
  case class Playlist(name: String, kind: PlaylistKind, songs: List[Song])
  case class Song(artist: Artist, title: String)

  opaque type Artist = String
  object Artist {
    def apply(name: String): Artist = name
  }

  opaque type User = String
  object User {
    def apply(name: String): User = name
  }

  enum MusicGenre {
    case House
    case Funk
    case HipHop
  }

  enum PlaylistKind {
    case CuratedByUser(user: User)
    case BasedOnArtist(artist: Artist)
    case BasedOnGenres(genres: Set[MusicGenre])    
  }
}

import model732._, model732.MusicGenre._, model732.PlaylistKind._

def gatherSongs(playlists: List[Playlist], artist: Artist, genre: MusicGenre): List[Song] = {
  playlists.foldLeft(List.empty[Song])((songs, playlist) =>
    val matchingSongs = playlist.kind match {
      case CuratedByUser(user) => playlist.songs.filter(_.artist == artist)
      case BasedOnArtist(playlistArtist) => if (playlistArtist == artist) playlist.songs
                                            else List.empty
      case BasedOnGenres(genres) => if (genres.contains(genre)) playlist.songs
                                    else List.empty
    }
    songs.appendedAll(matchingSongs)
  )
}
// scala> gatherSongs(List(playlist1, playlist3), fooFighters, HipHop)
// val res2: List[model732.Song] = List(Song(Foo Fighters,Breakout), Song(Foo Fighters,Learn To Fly), Song(Foo Fighters,My Hero))

val fooFighters = Artist("Foo Fighters")
val playlist1 = Playlist(
  "This is Foo Fighters",
  BasedOnArtist(fooFighters),
  List(Song(fooFighters, "Breakout"), Song(fooFighters, "Learn To Fly")),
)

val playlist2 = Playlist(
  "Deep Focus",
  BasedOnGenres(Set(House, Funk)),
  List(Song(Artist("Daft Punk"), "One More Time"), Song(Artist("The Chemical Brothers"), "Hey Boy Hey Girl")),
)

val playlist3 = Playlist(
  "My Playlist",
  CuratedByUser(User("dddddO")),
  List(Song(fooFighters, "My Hero"), Song(Artist("Iron Maiden"), "The Trooper")),
)
