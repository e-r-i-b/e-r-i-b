package com.rssl.phizic.business.xslt.lists;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.config.ApplicationConfig;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.config.atm.AtmApiConfig;
import com.rssl.phizic.config.mobile.MobileApiConfig;
import com.rssl.phizic.utils.xml.XmlHelper;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.StringReader;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;

/**
 * @author EgorovaA
 * @ created 15.08.14
 * @ $Author$
 * @ $Revision$
 */
public class OldDepositCodesSource implements EntityListSource
{
	public Source getSource(Map<String, String> params) throws BusinessException, BusinessLogicException
	{
		EntityListBuilder builder = getEntityListBuilder(params);
		return new StreamSource(new StringReader(builder.toString()));
	}

	public Document getDocument(Map<String, String> params) throws BusinessException
	{
		EntityListBuilder builder = getEntityListBuilder(params);
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

	private EntityListBuilder getEntityListBuilder( final Map<String,String> params ) throws BusinessException
	{
		ApplicationConfig config = ApplicationConfig.getIt();

		Map<String, String> codes = new HashMap<String, String>();
		if (config.getApplicationInfo().isATM())
		{
			AtmApiConfig atmApiConfig = ConfigFactory.getConfig(AtmApiConfig.class);
			codes = atmApiConfig.getOldDepositCodesList();
		}
		else
		{
			MobileApiConfig mobileApiConfig = ConfigFactory.getConfig(MobileApiConfig.class);
			codes = mobileApiConfig.getOldDepositCodesList();
		}
		return getEntityListBuilderByParams(codes);
	}

	private EntityListBuilder getEntityListBuilderByParams(Map<String, String> params)
	{
		EntityListBuilder builder = new EntityListBuilder();
		builder.openEntityListTag();

		for (Map.Entry<String, String> depositCode : params.entrySet())
		{
			builder.openEntityTag("entity");
			builder.appentField("deposit", depositCode.getKey());
			builder.appentField("groupCode", depositCode.getValue());
			builder.closeEntityTag();
		}
		builder.closeEntityListTag();

		return builder;
	}
}
