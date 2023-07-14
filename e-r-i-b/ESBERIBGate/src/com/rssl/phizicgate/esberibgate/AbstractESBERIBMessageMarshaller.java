package com.rssl.phizicgate.esberibgate;

import com.rssl.phizic.common.types.annotation.ThreadSafe;
import com.rssl.phizic.common.types.exceptions.InternalErrorException;
import com.rssl.phizic.config.ConfigurationException;
import com.rssl.phizic.logging.system.LogModule;
import com.rssl.phizic.utils.xml.XmlHelper;
import com.rssl.phizic.utils.xml.jaxb.JAXBSerializerBase;
import com.rssl.phizicgate.esberibgate.ws.jms.segment.federal.generated.BankAcctInqRq;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.xml.sax.SAXException;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.validation.Schema;

/**
 * @author Erkin
 * @ created 16.03.2015
 * @ $Author$
 * @ $Revision$
 */

/**
 * Базовый класс маршаллеров для сообщений в/из КСШ.
 * В статическом поле содержит JAXB-контекст по XSD-классам КСШ.
 * Важно! Класс предназначен для работы со "стандартной" версией ERIBAdapter.xsd.
 */
@ThreadSafe
public abstract class AbstractESBERIBMessageMarshaller extends JAXBSerializerBase
{
	///////////////////////////////////////////////////////////////////////////
	// Constants

	private static final String ERIB_ADAPTER_XSD_PATH = "com/rssl/phizicgate/esberibgate/ws/ERIBAdapter.xsd";

	/**
	 * Путь к пакету с XSD-классами ERIBAdapter.xsd
	 */
	private static final String ESBERIB_GENERATED_PACKAGE_PATH = BankAcctInqRq.class.getPackage().getName();

	///////////////////////////////////////////////////////////////////////////
	// Statics

	protected static final Log log = LogFactory.getLog(LogModule.Gate.toString());

	private static final JAXBContext jaxbContext;

	protected static final Schema eribAdapterXSDSchema;

	static
	{
		try
		{
			jaxbContext = JAXBContext.newInstance(ESBERIB_GENERATED_PACKAGE_PATH);
		}
		catch (JAXBException e)
		{
			throw new ConfigurationException("Не удалось загрузить JAXB-контекст для ESBERIB-маршаллера по пути " + ESBERIB_GENERATED_PACKAGE_PATH, e);
		}

		try
		{
			eribAdapterXSDSchema = XmlHelper.schemaByResourceNames(ERIB_ADAPTER_XSD_PATH);
		}
		catch (SAXException e)
		{
			throw new InternalErrorException("Сбой на загрузке XSD-схемы " + ERIB_ADAPTER_XSD_PATH, e);
		}
	}

	///////////////////////////////////////////////////////////////////////////

	protected AbstractESBERIBMessageMarshaller()
	{
		super(jaxbContext, "UTF-8");
	}
}
