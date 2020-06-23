package com.learningScala.chapter.eight.exercise_4
import javax.sound.midi.MidiChannel

/**
 * JVM 라이브러리는 작동하는 MIDI 사운드 신서사이저를 포함한다.
 * 다음은 짧은 악보를 연주하는 예제다.
 *
 * scala> val synth = javax.sound.midi.MidiSystem.getSynthesizer
 * synth: javax.sound.midi.Synthesizer = com.sun.media.sound
 * .SoftSynthesizer@283a8ad6
 *
 * scala> synth.open()
 *
 * scala> val channel = synth.getChannels.head
 * channel: javax.sound.midi.MidiChannel = com.sun.media.sound
 * .SoftChannelProxy@606d6d2c
 *
 * scala> channel.noteOn(50, 80); Thread.sleep(250); channel.noteOff(30)
 *
 * scala> synth.close()
 *
 */

object fourth {

  class Calliope(volume: Int) {

    private val duration = 250L
    private lazy val synth = javax.sound.midi.MidiSystem.getSynthesizer

    /**
     * Plays a series of MIDI notes at the specified volume for this calliope
     * @param notes a sequence of MIDI notes as integers
     */
    def play(notes: Seq[Int]): Unit = {
      playChannel { channel =>
        for (note <- notes) {
          channel.noteOn(note, volume)
          Thread.sleep(duration)
          channel.noteOn(note, 0)
        }
      }
    }

    /**
     * Provides a mechanism to play music in a channel without
     * worrying about opening and closing synths.
     */
    private def playChannel(f: MidiChannel => Unit): Unit = {
      synth.open()
      val channel: MidiChannel = synth.getChannels.head
      f(channel)
      synth.close()
    }
  }


  class CalliopePlaying {

    val calliope = new Calliope(95)

    def playScale(): Unit = {
      calliope.play(Seq(60, 62, 64, 65, 67, 69, 71, 72))
    }

    def playMary(): Unit = {
      val (c, d, e) = (60, 62, 64)
      val mary = Seq(0,e,d,c,d,e,e,e,0,d,d,d,0,e,e,e,0,e,d,c,d,e,e,e,e,d,d,e,d,c,0)
      calliope.play(mary)
    }
  }

  def main(args: Array[String]): Unit ={
    val test = new CalliopePlaying
    test.playScale()
    test.playMary()
  }
}
