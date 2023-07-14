package com.rssl.phizic.operations.ext.sbrf.payment;

import com.rssl.phizic.business.locale.MultiLocaleQueryHelper;
import com.rssl.phizic.dataaccess.query.Query;

import java.util.HashSet;
import java.util.Set;

/**
 * @author lukina
 * @ created 18.04.2013
 * @ $Author$
 * @ $Revision$
 * Получение списка услуг для страницы "Платежи и переводы"
 */

public class ListCategoryOperation extends ListServicesPaymentOperation
{
	private static final Set<String> names = new HashSet<String> ();

	static
	{
		names.add("toplist.payments.api");
		names.add("services.available");
		names.add("providers.available.descendants");
		names.add("providers.available");
	}

	@Override
	public Query createQuery(String name)
	{
		if(names.contains(name))
			return MultiLocaleQueryHelper.getOperationQuery(this, name, getInstanceName());
		return super.createQuery(name);
	}
}
