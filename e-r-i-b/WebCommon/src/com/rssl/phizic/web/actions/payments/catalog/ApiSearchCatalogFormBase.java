package com.rssl.phizic.web.actions.payments.catalog;

import com.rssl.phizic.web.actions.payments.ListPaymentServiceFormBase;

/**
 * @author krenev
 * @ created 20.10.14
 * @ $Author$
 * @ $Revision$
 * Базовый класс формы поиска в каталоге услуг для каналов, отличных от веба
 */
public class ApiSearchCatalogFormBase extends ApiCatalogFormBase
{
	private String search;

	public String getSearch()
	{
		return search;
	}

	public void setSearch(String search)
	{
		this.search = search;
	}
}
