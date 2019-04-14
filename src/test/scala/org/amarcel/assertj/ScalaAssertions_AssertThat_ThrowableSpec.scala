package org.amarcel.assertj

import org.amarcel.assertj.ScalaAssertions_AssertThat_ThrowableSpec.BoomException
import org.scalatest.FeatureSpec

class ScalaAssertions_AssertThat_ThrowableSpec extends FeatureSpec {

  feature("Nominal") {
    scenario("assertThatExceptionOfType") {
      testAssertThatExceptionOfType()
    }
    scenario("assertThatCode") {
      testAssertThatCode()
    }
    scenario("catchThrowable") {
      testCatchThrowable()
    }
    scenario("catchThrowableOfType") {
      testCatchThrowableOfType()
    }
  }

  def testAssertThatExceptionOfType(): Unit = {
    ScalaAssertions
      .assertThatExceptionOfType[BoomException]
      .isThrownBy(() => {
        throw new BoomException("boom!")
      })
      .withMessage("boom!")
      .withMessage("%s!", "boom")
      .withMessageContaining("boom")
      .withNoCause
  }

  def testAssertThatCode(): Unit = {
    ScalaAssertions
      .assertThatCode(() => {
        val x = 42
      })
      .doesNotThrowAnyException()
  }

  def testCatchThrowable(): Unit = {
    // when
    val thrown: Throwable =
      ScalaAssertions.catchThrowable(() => {
        throw new BoomException("boom!")
      })

    // then
    ScalaAssertions
      .assertThat(thrown)
      .isInstanceOf(classOf[BoomException])
      .hasMessageContaining("boom")
      .hasMessageStartingWith("b")
      .hasMessageEndingWith("!")
  }

  def testCatchThrowableOfType(): Unit = {
    // when
    val thrown: BoomException =
      ScalaAssertions.catchThrowableOfType[BoomException](() => {
        throw new BoomException("boom!")
      })

    // then
    ScalaAssertions
      .assertThatThrowableOfType(thrown)
      .satisfies(boomEx => {
        ScalaAssertions.assertThat(boomEx.prettyMessage()).startsWith("pretty")
      })
      .withMessageEndingWith("!")
  }
}

object ScalaAssertions_AssertThat_ThrowableSpec {

  class BoomException(message: String) extends Exception(message) {
    def prettyMessage(): String = {
      s"pretty $message"
    }
  }

}