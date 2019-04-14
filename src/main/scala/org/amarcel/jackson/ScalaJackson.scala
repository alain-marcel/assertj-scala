package org.amarcel.jackson

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.scala._
import com.fasterxml.jackson.module.scala.deser.ScalaNumberDeserializersModule
import com.fasterxml.jackson.module.scala.experimental.ScalaObjectMapper
import com.fasterxml.jackson.module.scala.introspect.ScalaAnnotationIntrospectorModule

object ScalaJackson {

  def scalaObjectMapper(): ObjectMapper = {
    (new ObjectMapper() with ScalaObjectMapper)
      .registerModule(
        // all defined in DefaultScalaModule except UntypedObjectDeserializerModule
        new JacksonModule
          with IteratorModule
          with EnumerationModule
          with OptionModule
          with SeqModule
          with IterableModule
          with TupleModule
          with MapModule
          with SetModule
          with ScalaNumberDeserializersModule
          with ScalaAnnotationIntrospectorModule
          // with UntypedObjectDeserializerModule
          with EitherModule
      )
  }

}
