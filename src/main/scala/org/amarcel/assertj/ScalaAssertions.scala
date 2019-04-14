package org.amarcel.assertj

import java.util.concurrent.atomic.AtomicBoolean
import java.util.function._
import scala.collection.JavaConverters._
import scala.collection.{immutable, mutable}
import scala.compat.java8.FunctionConverters._
import scala.reflect.ClassTag

import org.assertj.core.api.ThrowableAssert.ThrowingCallable
import org.assertj.core.api._

import com.jayway.jsonpath.{Configuration, JsonPath}

/**
  * AssertJ assertions for scala.
  */
object ScalaAssertions {

  private val jsonPathInitialized = new AtomicBoolean

  //----------------------------------------
  // Assertion for basic types
  //----------------------------------------
  def assertThat(actual: Boolean): BooleanAssert = new BooleanAssert(actual)

  def assertThat(actual: Byte): ByteAssert = new ByteAssert(actual)

  def assertThat(actual: Char): CharacterAssert = new CharacterAssert(actual)

  def assertThat(actual: Int): IntegerAssert = new IntegerAssert(actual)

  def assertThat(actual: Short): ShortAssert = new ShortAssert(actual)

  def assertThat(actual: Long): LongAssert = new LongAssert(actual)

  def assertThat(actual: Float): FloatAssert = new FloatAssert(actual)

  def assertThat(actual: Double): DoubleAssert = new DoubleAssert(actual)

  def assertThat[T](actual: T): ScalaObjectAssert[T] = new ScalaObjectAssert[T](actual)

  def assertThat(actual: Throwable): ThrowableAssert = new ThrowableAssert(actual)

  def assertThatThrowableOfType[T <: Throwable](actual: T): ThrowableAssertAlternative[T] = new ThrowableAssertAlternative[T](actual)

  def assertThat(actual: String): ScalaStringAssert = new ScalaStringAssert(actual)

  //----------------------------------------
  // Assertion for Array
  //----------------------------------------
  def assertThat(actual: Array[Boolean]): BooleanArrayAssert = new BooleanArrayAssert(actual)

  def assertThat(actual: Array[Byte]): ByteArrayAssert = new ByteArrayAssert(actual)

  def assertThat(actual: Array[Char]): CharArrayAssert = new CharArrayAssert(actual)

  def assertThat(actual: Array[Int]): IntArrayAssert = new IntArrayAssert(actual)

  def assertThat(actual: Array[Short]): ShortArrayAssert = new ShortArrayAssert(actual)

  def assertThat(actual: Array[Long]): LongArrayAssert = new LongArrayAssert(actual)

  def assertThat(actual: Array[Double]): DoubleArrayAssert = new DoubleArrayAssert(actual)

  def assertThat(actual: Array[Float]): FloatArrayAssert = new FloatArrayAssert(actual)

  def assertThat[T <: AnyRef](actual: Array[T]): ObjectArrayAssert[T] = new ObjectArrayAssert[T](actual)

  //----------------------------------------
  // Assertions for collections : Iterable, Seq, Map
  //----------------------------------------
  def assertThat[V <: Any](actual: Iterable[V]): IterableAssert[V] = Assertions.assertThat[V](actual.asJava)

  def assertThat[V <: Any](actual: Seq[V]): ListAssert[V] = Assertions.assertThat[V](actual.asJava)

  def assertThat[K <: Any, V <: Any](actual: immutable.Map[K, V]): MapAssert[K, V] = Assertions.assertThat[K, V](actual.asJava)

  def assertThat[K <: Any, V <: Any](actual: mutable.Map[K, V]): MapAssert[K, V] = Assertions.assertThat[K, V](actual.asJava)

  //----------------------------------------
  // Assertions for Throwable
  //----------------------------------------

  /**
    * Assert a Throwable is thrown.
    */
  def assertThatExceptionOfType[T <: Throwable]()(implicit ctag: ClassTag[T]): ThrowableTypeAssert[T] =
    Assertions.assertThatExceptionOfType(ctag.runtimeClass.asInstanceOf[Class[T]])

  def assertThatCode(shouldRaiseOrNotThrowable: ThrowingCallable): ThrowableAssert =
    assertThat(catchThrowable(shouldRaiseOrNotThrowable))

  def catchThrowable(shouldRaiseThrowable: ThrowingCallable): Throwable = Assertions.catchThrowable(shouldRaiseThrowable)

  def catchThrowableOfType[T <: Throwable](shouldRaiseThrowable: ThrowingCallable)(implicit ctag: ClassTag[T]): T =
    Assertions.catchThrowableOfType(shouldRaiseThrowable, ctag.runtimeClass.asInstanceOf[Class[T]])

  //----------------------------------------
  // Assertion for Json
  //----------------------------------------

  /**
    * Assertion on a JsonString with JsonPath.
    * Method name is not assertThat in order to have different signature from assertThat(String).
    * See `ScalaJsonPathAssert` for all available methods.
    */
  def assertThatJsonPath(actualJsonString: String): ScalaJsonPathAssert = {

    if ( jsonPathInitialized.compareAndSet(false, true) ) {
      Configuration.setDefaults(new JsonPathConfigDefaults())
    }
    new ScalaJsonPathAssert(JsonPath.parse(actualJsonString))
  }

  /**
    * Assertion on a JsonString as whole.
    * Method name is not assertThat in order to have different signature from assertThat(String).
    * See `ScalaJsonStringAssert` for all available methods.
    */
  def assertThatJsonString(actualJsonString: String): ScalaJsonStringAssert = {
    new ScalaJsonStringAssert(actualJsonString: String)
  }

  //----------------------------------------
  // Utilities
  //----------------------------------------
  // String
  class ScalaStringAssert(actual: String) extends AbstractStringAssert[ScalaStringAssert](actual, classOf[ScalaStringAssert]) {

    def isEqualTo(expected: String): ScalaStringAssert = {
      isEqualTo(expected: Object)
    }
  }

  class ScalaObjectAssert[T](actual: T) extends AbstractObjectAssert[ScalaObjectAssert[T], T](actual, classOf[ScalaObjectAssert[T]]) {

    def extracting[V](extractor: T => V): ScalaObjectAssert[V] = {
      val javaExtractor = extractor.asJava: Function[_ >: T, _]
      extracting(javaExtractor).asInstanceOf[ScalaObjectAssert[V]]
    }

    override def newObjectAssert(objectUnderTest: Any): ScalaObjectAssert[Any] = new ScalaObjectAssert[Any](objectUnderTest)
  }

  // Does not work  
  //  // Throwable
  //  class ScalaThrowableAssert[T <: Throwable](actual: T)
  //    extends AbstractThrowableAssert[T, ScalaThrowableAssert[T]](actual, classOf[ScalaThrowableAssert[T]]) {
  //  }

}
