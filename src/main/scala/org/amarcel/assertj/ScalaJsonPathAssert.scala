package org.amarcel.assertj

import java.math.BigDecimal
import java.util
import scala.reflect.ClassTag

import org.amarcel.assertj.ScalaAssertions.ScalaStringAssert
import org.amarcel.jackson.ScalaJackson
import org.assertj.core.api._

import com.fasterxml.jackson.databind.ObjectMapper
import com.jayway.jsonpath.spi.json.{JacksonJsonProvider, JsonProvider}
import com.jayway.jsonpath.spi.mapper.{JacksonMappingProvider, MappingProvider}
import com.jayway.jsonpath.{Configuration, DocumentContext, Option}

/**
  * Class instantiated with method `ScalaAssertions.assertThatJsonPath` (but never instantiated by user).
  * <p>
  *   About JsonPath.
  *   JsonPath is the "XPath" for json, but it is not normalized.
  *   It is implemented with java library https://github.com/json-path/JsonPath
  * </p>
  */
class ScalaJsonPathAssert(actual: DocumentContext)
  extends AbstractAssert[ScalaJsonPathAssert, DocumentContext](actual, classOf[ScalaJsonPathAssert]) {

  //----------------------------------------
  // Methods jsonPathAsXxx : basic types
  //----------------------------------------

  /**
    * Extracts a JSON text using a JsonPath expression and wrap it in a `StringAssert`.
    *
    * @param jsonPath JsonPath to extract the string
    * @return an instance of `ScalaStringAssert`
    */
  def jsonPathAsString(jsonPath: String): ScalaStringAssert = {
    ScalaAssertions.assertThat(actual.read(jsonPath, classOf[String]))
  }

  /**
    * Extracts a JSON number using a JsonPath expression and wrap it in an `IntegerAssert`
    *
    * @param jsonPath JsonPath to extract the number
    * @return an instance of `IntegerAssert`
    */
  def jsonPathAsInt(jsonPath: String): AbstractIntegerAssert[_ <: AbstractIntegerAssert[_]] = {
    Assertions.assertThat(actual.read(jsonPath, classOf[Int]))
  }

  /**
    * Extracts a JSON number using a JsonPath expression and wrap it in an `BigDecimalAssert`
    *
    * @param jsonPath JsonPath to extract the number
    * @return an instance of `BigDecimalAssert`
    */
  def jsonPathAsDouble(jsonPath: String): AbstractDoubleAssert[_ <: AbstractDoubleAssert[_]] = {
    Assertions.assertThat(actual.read(jsonPath, classOf[Double]))
  }

  /**
    * Extracts a JSON number using a JsonPath expression and wrap it in an `BigDecimalAssert`
    *
    * @param jsonPath JsonPath to extract the number
    * @return an instance of `BigDecimalAssert`
    */
  def jsonPathAsBigDecimal(jsonPath: String): AbstractBigDecimalAssert[_ <: AbstractBigDecimalAssert[_]] = {
    Assertions.assertThat(actual.read(jsonPath, classOf[BigDecimal]))
  }

  /**
    * Extracts a JSON boolean using a JsonPath expression and wrap it in an `BooleanAssert`
    *
    * @param jsonPath JsonPath to extract the number
    * @return an instance of `BooleanAssert`
    */
  def jsonPathAsBoolean(jsonPath: String): AbstractBooleanAssert[_ <: AbstractBooleanAssert[_]] = {
    Assertions.assertThat(actual.read(jsonPath, classOf[Boolean]))
  }

  //----------------------------------------
  // Method jsonPathAsList
  //----------------------------------------
  /**
    * Extracts a JSON array using a JsonPath expression and wrap it in a `ListAssert`. This method requires
    * the JsonPath to be <a href="https://github.com/jayway/JsonPath#jsonprovider-spi">configured with Jackson or
    * Gson</a>.
    * <p>
    * Case classes are supported if they are put in a companion object.
    * </p>
    *
    * @param jsonPath JsonPath to extract the array
    * @return an instance of `ListAssert`
    */
  def jsonPathAsList[T](jsonPath: String)(implicit ctag: ClassTag[Array[T]]): ListAssert[T] = {
    val array = actual.read(jsonPath, ctag.runtimeClass).asInstanceOf[Array[T]]
    ScalaAssertions.assertThat(array)
  }

  //----------------------------------------
  // Method jsonPathAs : case class
  //----------------------------------------
  /**
    * Extracts a any JSON type using a JsonPath expression and wrap it in an `ObjectAssert`.
    * Use this method to check for nulls or to do type checks.
    * <p>
    * Case classes are supported if they are put in an object (not into a class).
    * </p>
    *
    * @param jsonPath JsonPath to extract the type
    * @return an instance of `ObjectAssert`
    */
  def jsonPathAs[T](jsonPath: String)(implicit ctag: ClassTag[T]): ObjectAssert[T] = {
    val value = actual.read(jsonPath, ctag.runtimeClass).asInstanceOf[T]
    Assertions.assertThat(value)
  }

}

class JsonPathConfigDefaults(defaultJsonProvider: JsonProvider, defaultMappingProvider: MappingProvider) extends Configuration.Defaults {

  def this(objectMapper: ObjectMapper) {
    this(new JacksonJsonProvider(objectMapper), new JacksonMappingProvider(objectMapper))
  }

  def this() {
    this(ScalaJackson.scalaObjectMapper())
  }

  override def jsonProvider(): JsonProvider = defaultJsonProvider

  override def mappingProvider(): MappingProvider = defaultMappingProvider

  override def options(): util.Set[Option] = util.EnumSet.noneOf(classOf[Option])
}
