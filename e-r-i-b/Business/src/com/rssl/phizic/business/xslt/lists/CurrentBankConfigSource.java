package com.rssl.phizic.business.xslt.lists;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.utils.xml.XmlHelper;
import com.rssl.phizic.BankContextConfig;
import com.rssl.phizic.config.ConfigFactory;

import java.util.Map;
import java.io.StringReader;
import java.io.IOException;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

/**
 * @author egorova
 * @ created 13.02.2009
 * @ $Author$
 * @ $Revision$
 */
public class CurrentBankConfigSource implements EntityListSource
{
	public Source getSource( final Map<String,String> params ) throws BusinessException
	{
		EntityListBuilder builder = getEntityListBuilder();
		return new StreamSource(new StringReader(builder.toString()));
	}

	public Document getDocument(Map<String, String> params) throws BusinessException
	{
		EntityListBuilder builder = getEntityListBuilder();
		try
		{
			return XmlHelper.parse(builder.toString());
		}
		catch (ParserConfigurationException e)
		{
			throw new BusinessException(e);
		}
		catch (SAXException e)
		{
			throw new BusinessException(e);
		}
		catch (IOException e)
		{
			throw new BusinessException(e);
		}
	}

	private EntityListBuilder getEntityListBuilder() throws BusinessException
	{
		BankContextConfig bankContext = ConfigFactory.getConfig(BankContextConfig.class);

		EntityListBuilder builder = new EntityListBuilder();

	    builder.openEntityTag("bankConfig");

		builder.appentField("name", bankContext.getBankName());
		builder.appentField("bic", bankContext.getBankBIC());
		builder.appentField("corrAcc", bankContext.getBankCorAcc());

		builder.closeEntityTag();
		
		return builder;
	}


}
