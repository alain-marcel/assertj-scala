package org.amarcel.assertj

import org.scalatest.FeatureSpec

class ScalaAssertions_AssertThatJsonPathSpec extends FeatureSpec {

  import ScalaAssertions_AssertThatJsonPathSpec._

  feature("Methods jsonPathAsXxx on basic types") {

    scenario("Method jsonPathAsString") {
      test_jsonPathAsString()
    }
    scenario("Method jsonPathAsInt") {
      test_jsonPathAsInt()
    }
    scenario("Method jsonPathAsDouble") {
      test_jsonPathAsDouble()
    }
    scenario("Method jsonPathAsBigDecimal") {
      test_jsonPathAsBigDecimal()
    }
    scenario("Method jsonPathAsBoolean") {
      test_jsonPathAsBoolean()
    }
  }

  feature("Method jsonPathAsList") {
    scenario("With list of String (explicit)") {
      test_jsonPathAsList__String_1()
    }
    scenario("With list of String (with type inference)") {
      test_jsonPathAsList__String_2()
    }
    scenario("With list of Double (explicit)") {
      test_jsonPathAsList__Double_1()
    }
    scenario("With list of Double (with type inference)") {
      test_jsonPathAsList__Double_2()
    }
    scenario("With list of case class") {
      test_jsonPathAsList__CaseClass()
    }
  }

  feature("Method jsonPathAs") {
    scenario("With String") {
      test_jsonPathAs__String()
    }
    scenario("With case class") {
      test_jsonPathAs__CaseClass()
    }
  }

  //----------------------------------------
  // Feature : basic types
  //----------------------------------------
  def test_jsonPathAsString(): Unit = {
    ScalaAssertions
      .assertThatJsonPath(jsonString)
      .jsonPathAsString("$.store.book[-1].author")
      .isEqualTo("J. R. R. Tolkien")
  }

  def test_jsonPathAsInt(): Unit = {
    ScalaAssertions.assertThatJsonPath(jsonString)
      .jsonPathAsInt("$.expensive")
      .isEqualTo(10)
  }

  def test_jsonPathAsDouble(): Unit = {
    ScalaAssertions
      .assertThatJsonPath(jsonString)
      .jsonPathAsDouble("$.store.book[-1].price")
      .isEqualTo(22.99)
  }

  def test_jsonPathAsBigDecimal(): Unit = {
    ScalaAssertions
      .assertThatJsonPath(jsonString)
      .jsonPathAsBigDecimal("$.store.book[-1].price")
      .isGreaterThanOrEqualTo(new java.math.BigDecimal(22))
  }

  def test_jsonPathAsBoolean(): Unit = {
    ScalaAssertions
      .assertThatJsonPath(jsonString)
      .jsonPathAsBoolean("$.store.bicycle.electric")
      .isFalse
  }

  //----------------------------------------
  // Feature : jsonPathAsList
  //----------------------------------------
  def test_jsonPathAsList__String_1(): Unit = {
    ScalaAssertions
      .assertThatJsonPath(jsonString)
      .jsonPathAsList[String]("$.store.book[*].author")
      .containsExactlyInAnyOrder("Nigel Rees", "Evelyn Waugh", "Herman Melville", "J. R. R. Tolkien")
  }

  def test_jsonPathAsList__String_2(): Unit = {
    ScalaAssertions
      .assertThatJsonPath(jsonString)
      .jsonPathAsList[String]("$.store.book[*].author")
      .containsExactlyInAnyOrder("Nigel Rees", "Evelyn Waugh", "Herman Melville", "J. R. R. Tolkien")
  }

  def test_jsonPathAsList__Double_1(): Unit = {
    ScalaAssertions
      .assertThatJsonPath(jsonString)
      .jsonPathAsList("$.store.book[*].price")
      .containsExactlyInAnyOrder(8.95, 12.99, 8.99, 22.99)
  }

  def test_jsonPathAsList__Double_2(): Unit = {
    ScalaAssertions.assertThatJsonPath(jsonString)
      .jsonPathAsList[Double]("$.store.book[*].price")
      .containsExactlyInAnyOrder(8.95, 12.99, 8.99, 22.99)
  }

  def test_jsonPathAsList__CaseClass(): Unit = {
    ScalaAssertions
      .assertThatJsonPath(jsonString)
      .jsonPathAsList[Book]("$.store.book[*]")
      .anySatisfy(book => ScalaAssertions.assertThat(book.author).contains("Tolkien"))
  }

  //----------------------------------------
  // Feature : jsonPathAs
  //----------------------------------------
  def test_jsonPathAs__String(): Unit = {
    ScalaAssertions
      .assertThatJsonPath(jsonString)
      .jsonPathAs[String]("$.store.book[-1].title")
      .isEqualTo("The Lord of the Rings")
  }

  def test_jsonPathAs__CaseClass(): Unit = {
    ScalaAssertions
      .assertThatJsonPath(jsonString)
      .jsonPathAs[Book]("$.store.book[-1]")
      .satisfies(book => ScalaAssertions.assertThat(book.author).contains("Tolkien"))
  }

}

object ScalaAssertions_AssertThatJsonPathSpec {

  case class Book(category: String, author: String, title: String, isbn: Option[String], price: Double)

  val jsonString =
    """
      {
        "store": {
            "book": [
                {
                    "category": "reference",
                    "author": "Nigel Rees",
                    "title": "Sayings of the Century",
                    "price": 8.95
                },
                {
                    "category": "fiction",
                    "author": "Evelyn Waugh",
                    "title": "Sword of Honour",
                    "price": 12.99
                },
                {
                    "category": "fiction",
                    "author": "Herman Melville",
                    "title": "Moby Dick",
                    "isbn": "0-553-21311-3",
                    "price": 8.99
                },
                {
                    "category": "fiction",
                    "author": "J. R. R. Tolkien",
                    "title": "The Lord of the Rings",
                    "isbn": "0-395-19395-8",
                    "price": 22.99
                }
            ],
            "bicycle": {
                "color": "red",
                "price": 19.95,
                "electric": false
            }
        },
        "expensive": 10
      }
    """

}
