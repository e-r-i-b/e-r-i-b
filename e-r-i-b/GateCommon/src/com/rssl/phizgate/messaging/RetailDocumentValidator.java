package com.rssl.phizgate.messaging;

import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.gate.config.GateConnectionConfig;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.messaging.GateMessagingValidationException;
import com.rssl.phizic.utils.ValidateException;
import com.rssl.phizic.utils.XSDSchemeValidator;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import java.io.InputStream;
import javax.xml.XMLConstants;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;

/**
 * @author Omeliyanchuk
 * @ created 13.12.2007
 * @ $Author$
 * @ $Revision$
 */

public class RetailDocumentValidator
{
	private static final String RETAIL_DOCUMENT_SCHEME = "com.rssl.iccs.retail.document.scheme";
	private Schema schema;

	public RetailDocumentValidator() throws GateException
	{
		schema = getSchema();
	}

	/**
	 * @param message сообщение для валидации
	 * @throws com.rssl.phizic.gate.exceptions.GateException все остальные неприятности
	 */
	public void validate(Document message) throws GateException
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

			//TODO переделать
			String scheme = ConfigFactory.getConfig(GateConnectionConfig.class).getProperty(RETAIL_DOCUMENT_SCHEME);
			if (scheme == null)
			{
				return null;
			}
			ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
			InputStream stream = classLoader.getResourceAsStream(ConfigFactory.getConfig(GateConnectionConfig.class).getProperty(RETAIL_DOCUMENT_SCHEME));
			Source schemaFile = new StreamSource(stream);
			return factory.newSchema(schemaFile);

		}
		catch (SAXException e)
		{
			throw new GateException(e);
		}
	}
}
