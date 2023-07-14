package com.rssl.phizgate.common.ws;

import org.apache.axis.encoding.ser.BaseDeserializerFactory;

import javax.xml.namespace.QName;
import javax.xml.rpc.JAXRPCException;
import javax.xml.rpc.encoding.Deserializer;

/**
 * User: Moshenko
 * Date: 24.04.12
 * Time: 18:04
 */
public class AnyTypeDeserializerFactory extends BaseDeserializerFactory
{
	public AnyTypeDeserializerFactory(Class deserClass)
	{
		super(AnyTypeDeserializer.class);
	}

	public AnyTypeDeserializerFactory(Class javaType, QName xmlType)
	{
		super(AnyTypeDeserializer.class,xmlType, javaType);
	}

	@Override
	public Deserializer getDeserializerAs(String mechanismType) throws JAXRPCException
	{
		return new AnyTypeDeserializer(javaType);
	}
}
