package com.rssl.phizic.web.common.socialApi.payments.services;

import com.rssl.phizic.business.dictionaries.payment.services.CategoryServiceType;
import com.rssl.phizic.web.actions.ActionFormBase;
import com.rssl.phizic.web.actions.payments.catalog.ApiCatalogFormBase;

import java.util.LinkedHashSet;
import java.util.Set;
import java.util.List;

/**
 * @author Dorzhinov
 * @ created 12.01.2012
 * @ $Author$
 * @ $Revision$
 */
public class ListCategoryMobileForm extends ApiCatalogFormBase
{
	//in
	private boolean includeServices; //признак, отвечающий за необходимость получения подкатегорий

	public boolean isIncludeServices()
	{
		return includeServices;
	}

	public void setIncludeServices(boolean includeServices)
	{
		this.includeServices = includeServices;
	}
}
