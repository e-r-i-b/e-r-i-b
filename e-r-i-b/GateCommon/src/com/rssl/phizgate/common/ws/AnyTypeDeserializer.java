package com.rssl.phizgate.common.ws;

import org.apache.axis.encoding.DeserializationContext;
import org.apache.axis.encoding.DeserializerImpl;
import org.apache.axis.encoding.SerializationContext;
import org.apache.axis.message.MessageElement;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;

import java.io.StringWriter;
import java.io.Writer;
import java.util.Iterator;

/**
 * User: Moshenko
 * Date: 20.04.12
 * Time: 12:23
 * Десериализатор типа xsd:anyType.
 * Необходим для корректной интерпретации  элемента  PfrInfo в сообщении  PfrGetInfoInqRs.
 * Написан к запросу  BUG039731.
 */
public class AnyTypeDeserializer extends DeserializerImpl
{
	public static final String TYPE_NOT_SUPPORDET = "AnyTypeDeserializer не поддерживает типы отличные от AnyType";

	private Class javaType;

	public AnyTypeDeserializer(Class javaType)
	{
		this.javaType = javaType;
	}

	@Override
	public void onStartElement(String namespace, String localName, String prefix, Attributes attributes, DeserializationContext context) throws SAXException
	{
		if  (javaType == AnyType.class)
		{
			String result = "";

			MessageElement curElement = context.getCurElement();
			try
			{
				Writer writer = new StringWriter();
				SerializationContext serializationContext = new SerializationContext(writer, context.getMessageContext());
				serializationContext.registerPrefixForURI("",curElement.getNamespaceURI());
				Iterator it = curElement.getChildElements();
				while (it.hasNext()) {
					MessageElement child = (MessageElement) it.next();
					child.output(serializationContext);
				}
				result = writer.toString();
			}
			catch (Exception e)
			{
				throw new SAXException(e);
			};

			setValue(result);
			isEnded = true;
			valueComplete();
		}
		else
			throw new SAXException(TYPE_NOT_SUPPORDET);
	}
}

