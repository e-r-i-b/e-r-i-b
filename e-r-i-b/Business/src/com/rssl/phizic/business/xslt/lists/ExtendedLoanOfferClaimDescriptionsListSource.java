package com.rssl.phizic.business.xslt.lists;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.loanclaim.LoanClaimHelper;
import com.rssl.phizic.business.xslt.lists.builder.Entity;
import com.rssl.phizic.business.xslt.lists.builder.EntityList;
import com.rssl.phizic.utils.xml.XmlHelper;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.StringReader;
import java.util.Map;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;

/**
 * @author Balovtsev
 * @since 11.02.2015.
 */
public class ExtendedLoanOfferClaimDescriptionsListSource implements EntityListSource
{
	public Source getSource(Map<String, String> params) throws BusinessException, BusinessLogicException
	{
		return new StreamSource(new StringReader(getEntityList().toString()));
	}

	public Document getDocument(Map<String, String> params) throws BusinessException, BusinessLogicException
	{
		try
		{
			return XmlHelper.parse(getEntityList().toString());
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

	public EntityList getEntityList()
	{
		EntityList  list  = new EntityList();
		for (String term : LoanClaimHelper.getLoanClaimTerms(0L))
		{
			list.addEntity(new Entity("term", term));
		}

		return list;
	}
}
