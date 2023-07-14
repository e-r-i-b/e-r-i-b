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
 * ������� ����� ����������� ��� ��������� �/�� ���.
 * � ����������� ���� �������� JAXB-�������� �� XSD-������� ���.
 * �����! ����� ������������ ��� ������ � ������� ERIBAdapter.xsd ��� ���������� � ETSM � 16 ������.
 */
@ThreadSafe
public abstract class AbstractETSMMessageMarshallerR16 extends JAXBSerializerBase
{
	///////////////////////////////////////////////////////////////////////////
	// Constants

	/**
	 * ���� � XSD ETSM
	 */
	private static final String LOAN_APPLICATION_XSD_PATH = "com/rssl/phizicgate/esberibgate/loanclaim/etsm/xsd/LoanApplicationRelease16.xsd";

	/**
	 * ���� � ������ � XSD-�������� ETSM
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
			throw new ConfigurationException("�� ������� ��������� JAXB-�������� ��� ETSM-���������� �� ���� " + ETSM_GENERATED_PACKAGE_PATH, e);
		}

		try
		{
			loanApplicationXSDSchema = XmlHelper.schemaByResourceNames(LOAN_APPLICATION_XSD_PATH);
		}
		catch (SAXException e)
		{
			throw new InternalErrorException("���� �� �������� XSD-����� " + LOAN_APPLICATION_XSD_PATH, e);
		}
	}

	///////////////////////////////////////////////////////////////////////////

	protected AbstractETSMMessageMarshallerR16()
	{
		super(jaxbContext, "UTF-8");
	}
}
