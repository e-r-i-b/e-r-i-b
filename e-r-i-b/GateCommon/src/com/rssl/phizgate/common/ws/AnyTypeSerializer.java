package com.rssl.phizgate.common.ws;

import org.apache.axis.description.TypeDesc;
import org.apache.axis.encoding.SerializationContext;
import org.apache.axis.encoding.ser.SimpleSerializer;
import org.xml.sax.Attributes;

import java.io.IOException;
import javax.xml.namespace.QName;

/**
 *  Запись строки в контекст осуществляется без экранирования.
 */
public class AnyTypeSerializer extends SimpleSerializer
{
	public static final String TYPE_NOT_SUPPORDET = "AnyTypeSerializer не поддерживает типы отличные от AnyType";

	public AnyTypeSerializer(Class javaType, QName xmlType, TypeDesc typeDesc)
	{
		super(javaType, xmlType, typeDesc);
	}

	public AnyTypeSerializer(Class javaType, QName xmlType)
	{
		super(javaType, xmlType);
	}

	public void serialize(QName name, Attributes attributes,
	                      Object value, SerializationContext context)
			throws IOException
	{
		if (value instanceof  AnyType)
		{
			context.setWriteXMLType(null);
			String valueStr = null;
			if (value != null)
			{
				valueStr = getValueAsString(value, context);
			}
			context.startElement(name, attributes);
			if (valueStr != null)
			{
				context.writeString(valueStr);
			}
			context.endElement();
		}
		else
			throw new IOException(TYPE_NOT_SUPPORDET);
	}
}
