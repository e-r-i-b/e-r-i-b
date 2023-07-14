package com.rssl.phizicgate.sbcms.messaging.mock;

import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.messaging.GateMessagingValidationException;
import com.rssl.phizic.utils.XSDSchemeValidator;
import com.rssl.phizic.utils.ValidateException;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import java.io.InputStream;
import javax.xml.XMLConstants;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;

/**
 * @author Egorova
 * @ created 04.12.2008
 * @ $Author$
 * @ $Revision$
 */
public class XMLRequestValidator
{
	private Schema schema;

	public XMLRequestValidator() throws GateException
	{
		schema = getSchema();
	}

	/**
	* @param message сообщение для валидации
	* @throws com.rssl.phizic.gate.messaging.GateMessagingValidationException сообщение не прошло валидацию
	* @throws GateException все остальные неприятности
	 */
	public void validate(Document message) throws GateMessagingValidationException, GateException
	{
		try
		{
			XSDSchemeValidator.validate(schema, message);
		}
		catch (ValidateException e)
		{
			throw new GateMessagingValidationException(e.getMessage());
		}
	}

	private Schema getSchema() throws GateException
	{
		try
		{
			SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);

			ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
			InputStream stream = classLoader.getResourceAsStream("com/rssl/phizicgate/sbcms/messaging/mock/xml/PG_msg_type_1.xsd");
			Source schemaFile = new StreamSource(stream);
			return factory.newSchema(schemaFile);
		}
		catch (SAXException e)
		{
			throw new GateException(e);
		}
	}
}
