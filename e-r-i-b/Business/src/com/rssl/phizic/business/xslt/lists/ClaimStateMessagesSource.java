package com.rssl.phizic.business.xslt.lists;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.loans.ClaimStateMessagesService;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.utils.xml.XmlHelper;

import java.util.Map;
import java.io.StringReader;
import java.io.IOException;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.w3c.dom.Document;

/**
 * @author gladishev
 * @ created 19.02.2008
 * @ $Author$
 * @ $Revision$
 */

/**
 * ƒл€ получени€ сообщени€ дл€ статуса за€вки в xslt можно написать что-то вроде
 * <xsl:value-of select="document( concat('claim-state-messages.xml?claimId=',$claimId) )/entity-list/entity/field[@name='message']"/>
 */

public class ClaimStateMessagesSource implements EntityListSource
{
	public Source getSource( final Map<String,String> params ) throws BusinessException
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

	private EntityListBuilder getEntityListBuilder(final Map<String,String> params) throws BusinessException
	{
		String claimId = params.get("claimId");

		if (claimId == null || claimId.equals(""))
			throw new BusinessException("Ќе указан id за€вки");

		String message = ConfigFactory.getConfig(ClaimStateMessagesService.class).getMessage(Long.parseLong(claimId));

		EntityListBuilder builder = new EntityListBuilder();

		builder.openEntityListTag();
		if (message != null)
		{
			builder.openEntityTag(claimId);
			builder.appentField("message", message);
			builder.closeEntityTag();
		}
		builder.closeEntityListTag();

		return builder;
	}
}
