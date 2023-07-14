package com.rssl.phizic.limits.handlers;

import com.rssl.phizic.logging.system.LogModule;
import com.rssl.phizic.utils.XSDSchemeValidator;
import com.rssl.phizic.utils.xml.XmlHelper;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import javax.xml.validation.Schema;

/**
 * @author osminin
 * @ created 22.01.14
 * @ $Author$
 * @ $Revision$
 */
public abstract class TransactionProcessorBase implements TransactionProcessor
{
	private static final Log log = LogFactory.getLog(LogModule.Web.toString());

	private Schema schema;

	/**
	 * ctor
	 * @param xsdSchemePath пусть к xsd
	 */
	protected TransactionProcessorBase(String xsdSchemePath)
	{
		try
		{
			schema = XmlHelper.schemaByFileName(xsdSchemePath);
		}
		catch (SAXException e)
		{
			log.error("Ошибка при создании схемы по xsd", e);
		}
	}

	public void process(Document request) throws Exception
	{
		if (schema != null)
		{
			//Валидируем запрос
			XSDSchemeValidator.validate(schema, request);
			//Обрабатываем валидный запрос
			doProcess(request.getDocumentElement());
		}
	}

	protected abstract void doProcess(Element root) throws Exception;
}
