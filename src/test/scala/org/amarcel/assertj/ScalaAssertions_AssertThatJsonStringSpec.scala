package org.amarcel.assertj

import org.scalatest.FeatureSpec

class ScalaAssertions_AssertThatJsonStringSpec extends FeatureSpec {

  import ScalaAssertions_AssertThatJsonStringSpec._

  feature("Methods jsonStringAsXxx on basic types") {
    scenario("Method jsonPathAsString") {
      test_IsStrictEqual()
    }
  }

  def test_IsStrictEqual(): Unit = {
    val jsonString1 = """{"name":"Bob", "size":150}"""
    ScalaAssertions
      .assertThatJsonString(jsonString)
      .isStrictlyEqualTo(jsonString1)
  }

}

object ScalaAssertions_AssertThatJsonStringSpec {
  val jsonString =
    """
      {
        "name": "Bob",
        "size": 150
      }
    """
}
