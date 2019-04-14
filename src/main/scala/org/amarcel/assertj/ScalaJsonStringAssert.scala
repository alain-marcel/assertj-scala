package org.amarcel.assertj

import org.skyscreamer.jsonassert.{JSONAssert, JSONCompareMode}

/**
  * Class instantiated with method `ScalaAssertions.assertThatJsonString` (but never instantiated by user).
  * <p>
  * Checking options :
  *   <ul>
  *       <li>Strict checking. Not extensible, and strict array ordering.</li>
  *       <li>Lenient checking. Extensible, and non-strict array ordering.</li>
  *       <li>Strict order checking. Extensible, and strict array ordering.</li>
  *       <li>Non-extensible checking. Not extensible, and non-strict array ordering.</li>
  *   </ul>
  * </p>
  *
  * <p>
  * Note  
  *   - Extensible : means that json may contain extra fields
  *                  but elements in array are not affected by this property
  */
//noinspection AccessorLikeMethodIsUnit
class ScalaJsonStringAssert(actualJsonString: String) {

  /**
    * Default comparison : isStrictEqualTo.
    */
  def isEqualTo(jsonString: String): Unit = {
    JSONAssert.assertEquals(jsonString, actualJsonString, JSONCompareMode.STRICT)
  }

  /**
    * Strict checking.  Not extensible, and strict array ordering.
    */
  def isStrictlyEqualTo(jsonString: String): Unit = {
    JSONAssert.assertEquals(jsonString, actualJsonString, JSONCompareMode.STRICT)
  }

  /**
    * Lenient checking.  Extensible, and non-strict array ordering.
    */
  def isLenientEqualTo(jsonString: String): Unit = {
    JSONAssert.assertEquals(jsonString, actualJsonString, JSONCompareMode.LENIENT)
  }

  /**
    * Non-extensible checking.  Not extensible, and non-strict array ordering.
    */
  def isNonExtensibleEqualTo(jsonString: String): Unit = {
    JSONAssert.assertEquals(jsonString, actualJsonString, JSONCompareMode.NON_EXTENSIBLE)
  }

  /**
    * Strict order checking.  Extensible, and strict array ordering.
    */
  def isStrictOrderEqualTo(jsonString: String): Unit = {
    JSONAssert.assertEquals(jsonString, actualJsonString, JSONCompareMode.STRICT_ORDER)
  }

}
