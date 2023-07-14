package com.rssl.phizgate.common.ws;

import org.apache.axis.encoding.ser.BaseSerializerFactory;

import javax.xml.namespace.QName;
import javax.xml.rpc.JAXRPCException;
import javax.xml.rpc.encoding.Serializer;

/**
 * @author Erkin
 * @ created 22.03.2012
 * @ $Author$
 * @ $Revision$
 */

/**
 * Фабрика для сериализатора xsd:anyType
 */
public class AnyTypeSerializerFactory extends BaseSerializerFactory
{
	public AnyTypeSerializerFactory()
	{
		super(AnyTypeSerializer.class);
	}

	public AnyTypeSerializerFactory(Class javaType, QName xmlType)
	{
        super(AnyTypeSerializer.class, xmlType, javaType);
    }

	@Override
    public Serializer getSerializerAs(String mechanismType) throws JAXRPCException
    {
	    return new AnyTypeSerializer(javaType, xmlType);
    }

}