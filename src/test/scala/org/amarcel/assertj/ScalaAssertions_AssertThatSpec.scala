package org.amarcel.assertj

import scala.collection.{immutable, mutable}

import org.scalatest.FeatureSpec

class ScalaAssertions_AssertThatSpec extends FeatureSpec {

  import ScalaAssertions_AssertThatSpec._

  this.feature("For basic types") {
    scenario("String") {
      test_String()
    }

    scenario("Boolean") {
      test_Boolean()
    }

    scenario("Int") {
      test_Int()
    }

    scenario("Case class") {
      test_CaseClass()
    }
  }

  feature("For Array") {
    scenario("Array of Int (primitive type, e.g. 1)") {
      test_ArrayOfInt()
    }

    scenario("Array of case class") {
      test_ArrayOfCaseClass()
    }
  }

  feature("For collections (not Array)") {
    scenario("scala.immutable.Iterable") {
      test_immutableIterable()
    }
    scenario("scala.mutable.Iterable") {
      test_mutableIterable()
    }

    scenario("scala.immutable.Seq") {
      test_immutableSeq()
    }

    scenario("scala.mutable.Seq") {
      test_mutableSeq()
    }

    scenario("scala.immutable.Map") {
      test_immutableMap()
    }
    scenario("scala.mutable.Map") {
      test_mutableMap()
    }
  }

  //----------------------------------------
  // Feature : basic types
  //----------------------------------------
  def test_String(): Unit = {
    ScalaAssertions.assertThat("azerty")
      .isEqualToIgnoringCase("azeRTY")
      .isEqualTo("azerty")
      .isNotEqualTo("qwerty")
  }

  def test_Boolean(): Unit = {
    val b = true

    ScalaAssertions.assertThat(b)
      .isTrue
      .isEqualTo(true)
  }

  def test_Int(): Unit = {
    val i = 12

    ScalaAssertions.assertThat(i)
      .isEqualTo(12)
      .isBetween(11, 13)
      .isBetween(11, 13)
  }

  def test_CaseClass(): Unit = {
    val person = Person("Bob", 151)
    ScalaAssertions.assertThat(Person("Bob", 151))
      .isEqualTo(person)
      .extracting(x => x.name)
      .isEqualTo("Bob")
  }

  //----------------------------------------
  // Feature : Array
  //----------------------------------------
  def test_ArrayOfInt(): Unit = {
    ScalaAssertions.assertThat(Array(1, 2, 3))
      .containsExactly(1, 2, 3)
      .containsExactlyInAnyOrder(3, 2, 1)
      .hasSize(3)
  }

  def test_ArrayOfCaseClass(): Unit = {
    val person1 = Person("Bob", 151)
    val person2 = Person("John", 152)
    val person3 = Person("Mary", 153)
    val array = Array(person1, person2, person3)

    ScalaAssertions.assertThat(array)
      .containsExactly(person1, person2, person3)
      .containsExactlyInAnyOrder(person3, person2, person1)
      .hasSize(3)
  }

  //----------------------------------------
  // Feature : Collections 
  //----------------------------------------
  def test_immutableIterable(): Unit = {
    ScalaAssertions.assertThat(immutable.Iterable(1, 2, 3))
      .containsExactly(1, 2, 3)
      .containsExactlyInAnyOrder(3, 2, 1)
      .doesNotContain(5)
      .hasSize(3)
  }

  def test_mutableIterable(): Unit = {
    ScalaAssertions.assertThat(mutable.Iterable(1, 2, 3))
      .containsExactly(1, 2, 3)
      .containsExactlyInAnyOrder(3, 2, 1)
      .hasSize(3)
  }

  def test_immutableSeq(): Unit = {
    ScalaAssertions.assertThat(immutable.Seq(1, 2, 3))
      .containsExactly(1, 2, 3)
      .containsExactlyInAnyOrder(3, 2, 1)
      .hasSize(3)
  }

  def test_mutableSeq(): Unit = {
    ScalaAssertions.assertThat(mutable.Seq(1, 2, 3))
      .containsExactly(1, 2, 3)
      .containsExactlyInAnyOrder(3, 2, 1)
      .hasSize(3)
  }

  def test_immutableMap(): Unit = {
    ScalaAssertions.assertThat(immutable.Map("a" -> 1, "b" -> 2))
      .containsKeys("a", "b")
      .containsValues(2, 1)
      .hasSize(2)
  }

  def test_mutableMap(): Unit = {
    ScalaAssertions.assertThat(mutable.Map("a" -> 1, "b" -> 2))
      .containsKeys("a", "b")
      .containsValues(2, 1)
      .hasSize(2)
  }

}

object ScalaAssertions_AssertThatSpec {

  case class Person(name: String, size: Int)

}
