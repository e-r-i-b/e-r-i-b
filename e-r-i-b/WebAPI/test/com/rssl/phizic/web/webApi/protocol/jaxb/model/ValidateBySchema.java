package com.rssl.phizic.web.webApi.protocol.jaxb.model;

import junit.framework.*;

import java.io.*;
import javax.xml.*;
import javax.xml.bind.*;
import javax.xml.bind.helpers.*;
import javax.xml.transform.*;
import javax.xml.transform.stream.*;
import javax.xml.validation.*;

/**
 * @author Balovtsev
 * @since 22.04.14
 */
public abstract class ValidateBySchema extends TestCase
{
	protected final static String SCHEMA_FILE_PATH = "./schema.xsd";
	protected final static String XML_FILE_PATH    = "./generated.xml";

	public void setUp() throws Exception
	{
		JAXBContext.newInstance(getTestClass()).generateSchema(new TestSchemaOutputResolver());
	}

	private class TestSchemaOutputResolver extends SchemaOutputResolver
	{
		public Result createOutput( String namespaceUri, String suggestedFileName ) throws IOException
		{
			return new StreamResult(new File(SCHEMA_FILE_PATH));
		}
	}

	protected void validateObject(String testMethodName, Object object) throws Exception
	{
		JAXBContext context = JAXBContext.newInstance(getTestClass());

		Marshaller marshaller = context.createMarshaller();
		marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

		System.out.println("==== Start " + testMethodName + " output ====");
		marshaller.marshal(object, System.out);
		System.out.println("==== End output ====");

		marshaller.marshal(object, new File(XML_FILE_PATH));

		Unmarshaller unmarshaller = context.createUnmarshaller();
		unmarshaller.setSchema(getSchema());
		unmarshaller.setEventHandler(new DefaultValidationEventHandler());
		unmarshaller.unmarshal(new File(XML_FILE_PATH));
	}

	protected Schema getSchema() throws Exception
	{
		SchemaFactory sf = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
		return sf.newSchema(new File(SCHEMA_FILE_PATH));
	}

	protected abstract Class getTestClass();
}
