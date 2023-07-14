package com.rssl.phizicgate.esberibgate.bki;

import com.rssl.phizgate.common.credit.bki.BKIConstants;
import com.rssl.phizgate.common.credit.etsm.ETSMLoanClaimConstants;
import com.rssl.phizic.common.types.exceptions.InternalErrorException;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.config.loanclaim.LoanClaimConfig;
import com.rssl.phizic.utils.xml.XmlHelper;
import com.rssl.phizicgate.esberibgate.bki.generated.EnquiryResponseERIB;
import org.xml.sax.SAXException;

import java.io.Reader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.validation.Schema;

/**
 * Парсер xml с ответом из БКИ
 * Валидирует xml по схеме
 * @author Puzikov
 * @ created 16.10.14
 * @ $Author$
 * @ $Revision$
 */

public class CreditResponseParser
{
	private final JAXBContext jaxbContext;
	private final Schema loanXSDSchema;

	/**
	 * ctor
	 * @param etsm парсить сообщения TSM
	 * @param bki парсить сообщения БКИ
	 */
	public CreditResponseParser(boolean etsm, boolean bki)
	{
		if (!etsm && !bki)
			throw new IllegalArgumentException("Неизвестный тип парсера");

		List<String> schemas = new ArrayList<String>(3);
		List<Class> classes = new ArrayList<Class>(2);
		if (etsm)
		{
			LoanClaimConfig loanClaimConfig = ConfigFactory.getConfig(LoanClaimConfig.class);
			if (loanClaimConfig.isUseXSDRelease16Version())
				schemas.add(ETSMLoanClaimConstants.XSD_RELEASE_16);
			else
				schemas.add(ETSMLoanClaimConstants.XSD_RELEASE_13);
			classes.add(com.rssl.phizicgate.esberibgate.loanclaim.etsm.generated.release_16.StatusLoanApplicationRq.class);
			classes.add(com.rssl.phizicgate.esberibgate.loanclaim.etsm.generated.release_13.StatusLoanApplicationRq.class);
		}
		if (bki)
		{
			schemas.add(BKIConstants.XSD);
			classes.add(EnquiryResponseERIB.class);
		}
		try
		{
			loanXSDSchema = XmlHelper.schemaByResourceNames(schemas.toArray(new String[schemas.size()]));
			jaxbContext = JAXBContext.newInstance(classes.toArray(new Class[classes.size()]));
		}
		catch (SAXException e)
		{
			throw new InternalErrorException("[ETSM/BKI] Сбой на загрузке XSD-схемы", e);
		}
		catch (JAXBException e)
		{
			throw new InternalErrorException("[ETSM/BKI] Сбой на загрузке JAXB-контекста", e);
		}
	}

	/**
	 * Распарсить ответ из БКИ с валидацией
	 * @param responseXml xml из БКИ
	 * @return ответ
	 */
	public Object parse(String responseXml) throws JAXBException
	{
		Reader reader = new StringReader(responseXml);
		Unmarshaller unmarshaller = jaxbContext.createUnmarshaller();
		unmarshaller.setSchema(loanXSDSchema);
		return unmarshaller.unmarshal(reader);
	}
}
