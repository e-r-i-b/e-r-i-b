package com.rssl.phizic.business.forms.expressions.xpath;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.payments.ListsURIResolver;
import com.rssl.phizic.business.xslt.lists.EntitiesListSourcesFactory;
import com.rssl.phizic.business.xslt.lists.EntityListSource;

import java.util.List;
import javax.xml.xpath.XPathFunction;
import javax.xml.xpath.XPathFunctionException;

/**
 * @author Krenev
 * @ created 17.11.2008
 * @ $Author$
 * @ $Revision$
 */
public class DocumentFunction implements XPathFunction
{
	public Object evaluate(List args) throws XPathFunctionException
	{
		if (args.size() != 1)
		{
			return null;
		}
		String dictionaryName = (String) args.get(0);
		try
		{
			EntitiesListSourcesFactory factory = EntitiesListSourcesFactory.getInstance();

			EntityListSource source = factory.getEntityListSource(ListsURIResolver.getEntityListName(dictionaryName));
			return source.getDocument(ListsURIResolver.getEntityListParams(dictionaryName));
		}
		catch (BusinessException e)
		{
			throw new XPathFunctionException(e);
		}
		catch (BusinessLogicException e)
		{
			throw new XPathFunctionException(e);
		}
	}
}
