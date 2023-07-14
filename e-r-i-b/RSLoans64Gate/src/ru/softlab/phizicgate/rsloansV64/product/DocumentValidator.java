package ru.softlab.phizicgate.rsloansV64.product;

import com.rssl.phizic.gate.exceptions.GateException;
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

public class DocumentValidator
{
	private Schema schema;
	private static final String LOANS_DOCUMENT_SCHEME = "ru/softlab/phizicgate/rsloansV64/config/loans64.xsd";

	DocumentValidator() throws GateException
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
			throw new GateException(e);
		}
	}

	private Schema getSchema() throws GateException
	{
		try
		{
			SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);

			ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
			InputStream stream = classLoader.getResourceAsStream(LOANS_DOCUMENT_SCHEME);
			Source schemaFile = new StreamSource(stream);
			return factory.newSchema(schemaFile);

		}
		catch (SAXException e)
		{
			throw new GateException(e);
		}
	}
}
