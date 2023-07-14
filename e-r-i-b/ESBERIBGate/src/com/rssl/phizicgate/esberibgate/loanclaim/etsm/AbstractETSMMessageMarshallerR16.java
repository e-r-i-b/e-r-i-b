package com.rssl.phizicgate.esberibgate.loanclaim.etsm;

import com.rssl.phizic.common.types.annotation.ThreadSafe;
import com.rssl.phizic.common.types.exceptions.InternalErrorException;
import com.rssl.phizic.config.ConfigurationException;
import com.rssl.phizic.logging.system.LogModule;
import com.rssl.phizic.utils.xml.XmlHelper;
import com.rssl.phizic.utils.xml.jaxb.JAXBSerializerBase;
import com.rssl.phizicgate.esberibgate.loanclaim.etsm.generated.release_16.ChargeLoanApplicationRq;
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
 * Важно! Класс предназначен для работы с версией ERIBAdapter.xsd для интеграции с ETSM в 16 релизе.
 */
@ThreadSafe
public abstract class AbstractETSMMessageMarshallerR16 extends JAXBSerializerBase
{
	///////////////////////////////////////////////////////////////////////////
	// Constants

	/**
	 * Путь к XSD ETSM
	 */
	private static final String LOAN_APPLICATION_XSD_PATH = "com/rssl/phizicgate/esberibgate/loanclaim/etsm/xsd/LoanApplicationRelease16.xsd";

	/**
	 * Путь к пакету с XSD-классами ETSM
	 */
	private static final String ETSM_GENERATED_PACKAGE_PATH = ChargeLoanApplicationRq.class.getPackage().getName();

	///////////////////////////////////////////////////////////////////////////
	// Statics

	protected static final Log log = LogFactory.getLog(LogModule.Gate.toString());

	private static final JAXBContext jaxbContext;

	protected static final Schema loanApplicationXSDSchema;

	static
	{
		try
		{
			jaxbContext = JAXBContext.newInstance(ETSM_GENERATED_PACKAGE_PATH);
		}
		catch (JAXBException e)
		{
			throw new ConfigurationException("Не удалось загрузить JAXB-контекст для ETSM-маршаллера по пути " + ETSM_GENERATED_PACKAGE_PATH, e);
		}

		try
		{
			loanApplicationXSDSchema = XmlHelper.schemaByResourceNames(LOAN_APPLICATION_XSD_PATH);
		}
		catch (SAXException e)
		{
			throw new InternalErrorException("Сбой на загрузке XSD-схемы " + LOAN_APPLICATION_XSD_PATH, e);
		}
	}

	///////////////////////////////////////////////////////////////////////////

	protected AbstractETSMMessageMarshallerR16()
	{
		super(jaxbContext, "UTF-8");
	}
}
