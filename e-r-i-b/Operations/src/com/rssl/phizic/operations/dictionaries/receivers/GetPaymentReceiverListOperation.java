package com.rssl.phizic.operations.dictionaries.receivers;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.resources.external.MultiInstanceExternalResourceService;
import com.rssl.phizic.business.resources.external.PaymentSystemIdLink;
import com.rssl.phizic.dataaccess.DataAccessException;
import com.rssl.phizic.dataaccess.query.Query;
import com.rssl.phizic.operations.dictionaries.PaymentReceiverOperationBase;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *  ласс, представл€ющий операцию, с ним св€зан запрос
 * @author Kidyaev
 * @ created 29.11.2005
 * @ $Author$
 * @ $Revision$
 */
public class GetPaymentReceiverListOperation extends PaymentReceiverOperationBase
{
	private static final MultiInstanceExternalResourceService externalResourceService = new MultiInstanceExternalResourceService();

	public Query createQuery(String name, Map filters)  throws DataAccessException
	{
		Query query = super.createQuery(name);
		setQueryParamenters(filters, query);
		return query;
	}

	private void setQueryParamenters(Map filters, Query query) throws DataAccessException
	{
		List<String> namedParameters = query.getNamedParameters();
		if (namedParameters.isEmpty())
			return;
		List<String> extraParameters = filterExtraParameters(namedParameters);
		if (extraParameters.isEmpty())
			return;
		for (String namedParameter: extraParameters)
		{
				if (filters.get(namedParameter)!=null)
				{
					query.setParameter( namedParameter , filters.get(namedParameter).toString().toUpperCase());
				}
				else
				{
					query.setParameter( namedParameter , null);
				}
		}
	}
	private	List<String> filterExtraParameters(List<String> namedParameters)
	{
		List<String> namedParameteres = new ArrayList<String>();
		int i=0;
		for (String namedParameter: namedParameters)
		{
		  if (namedParameter.startsWith("extra_"))
		 {
		   namedParameteres.add(i, namedParameter.substring(namedParameter.indexOf("_")+1));
		   i++;
		 }
		}
		return namedParameteres;
	}

	public String getExternalPaymentSystemIdLink() throws BusinessException, BusinessLogicException
	{
		List<PaymentSystemIdLink> list = externalResourceService.getLinks(getPerson().getLogin(), PaymentSystemIdLink.class, getInstanceName());
		return list.size() > 0 ? list.get(0).getValue() : null;
	}
}
