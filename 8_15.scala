import cats.effect.IO
import ch08_SchedulingMeetings.calendarEntriesApiCall

def calendarEntries(name: String): IO[List[MeetingTime]] = {
  IO.delay(calendarEntriesApiCall(name))
}

import ch08_SchedulingMeetings.createMeetingApiCall
def createMeeting(names: List[String], meetingTime: MeetingTime): IO[Unit] = {
  IO.delay(createMeetingApiCall(names, meetingTime))
}

def scheduledMeetings(person1: String, person2: String): IO[List[MeetingTime]] = {
  for {
    person1Entries <- calendarEntries(person1)
    person2Entries <- calendarEntries(person2)
  } yield person2Entries.appendedAll(person2Entries)
}

val scheduledMeetingsProgram = scheduledMeetings("Alice", "Bob")
// scala> scheduledMeetingsProgram
// val res1: cats.effect.IO[List[MeetingTime]] = IO(...)
//                                                                                                                                            
// scala> scheduledMeetingsProgram.unsafeRunSync()
// val res2: List[MeetingTime] = List(MeetingTime(9,10), MeetingTime(9,10))
//                                                                                                                                            
// scala>

def meetingsOverlap(meeting1: MeetingTime, meeting2: MeetingTime): Boolean = {
  meeting1.endHour > meeting2.startHour && meeting2.endHour > meeting1.startHour
}

def possibleMeetings(existingMeetings: List[MeetingTime], startHour: Int, endHour: Int, lengthHours: Int): List[MeetingTime] = {
  val slots = List
    .range(startHour, endHour - lengthHours + 1)
    .map(startHour => MeetingTime(startHour, startHour + lengthHours))
  
  slots.filter(slot =>
    existingMeetings.forall(meeting => !meetingsOverlap(meeting, slot))
  )
}

def scheduleNonRetry(person1: String, person2: String, lengthHours: Int): IO[Option[MeetingTime]] = {
  for {
    existingMeetings <- scheduledMeetings(person1, person2)
    meetings = possibleMeetings(existingMeetings, 8, 16, lengthHours)
  } yield meetings.headOption
}
// scala> scheduleNonRetry("Alice", "Bob", 3).unsafeRunSync()
// val res24: Option[MeetingTime] = Some(MeetingTime(10,13))
//                                                                                                                                            
// scala> scheduleNonRetry("Alice", "Bob", 3).unsafeRunSync()
// java.lang.RuntimeException: Connection error
//   at ch08_SchedulingMeetingsAPI.calendarEntriesApiCall(ch08_SchedulingMeetingsAPI.java:19)
//   at ch08_SchedulingMeetings$.calendarEntriesApiCall(ch08_SchedulingMeetings.scala:27)
//   at rs$line$27$.calendarEntries$$anonfun$1(rs$line$27:5)
//   at delay @ rs$line$27$.calendarEntries(rs$line$27:5)
//   at flatMap @ rs$line$27$.scheduledMeetings(rs$line$27:17)
//   at map @ rs$line$27$.scheduleNonRetry(rs$line$27:46)
//   at map @ rs$line$27$.scheduleNonRetry(rs$line$27:47)
//                                                                                                                                            
// scala> 

def scheduleWithRetryAndFallback(person1: String, person2: String, lengthHours: Int): IO[Option[MeetingTime]] = {
  for {
    existingMeetings <- scheduledMeetings(person1, person2)
      .orElse(scheduledMeetings(person1, person2))
      .orElse(IO.pure(List.empty))
    meetings = possibleMeetings(existingMeetings, 8, 16, lengthHours)
  } yield meetings.headOption
}
// scala> scheduleWithRetryAndFallback("Alice", "Bob", 3).unsafeRunSync()
// val res38: Option[MeetingTime] = Some(MeetingTime(10,13))
//                                                                                                                                            
// scala> scheduleWithRetryAndFallback("Alice", "Bob", 3).unsafeRunSync()
// val res39: Option[MeetingTime] = Some(MeetingTime(8,11))
                                                       
def schedule(person1: String, person2: String, lengthHours: Int): IO[Option[MeetingTime]] = {
  for {
    existingMeetings <- scheduledMeetings(person1, person2)
      .orElse(scheduledMeetings(person1, person2))
      .orElse(IO.pure(List.empty))
    meetings = possibleMeetings(existingMeetings, 8, 16, lengthHours)
    possibleMeeting = meetings.headOption
    _ <- possibleMeeting match {
      case Some(meeting) => createMeeting(List(person1, person2), meeting)
                              // 上のmeeting作成が失敗した場合、以下で、再度作成処理を実行するようにリトライ処理を実装しているが、このmeeting作成処理が冪等な処理ではない場合、ほんとうにリトライすべきかなど考慮が必要
                              .orElse(createMeeting(List(person1, person2), meeting))
                              .orElse(IO.unit)
      case None          => IO.unit
    }
  } yield possibleMeeting
}