package com.rssl.phizic.web.common.mobile.payments.services;

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
	private boolean includeServices; //признак, отвечающий за необходимость получения подкатегорий
	//out
    private Set<CategoryServiceType> categories = new LinkedHashSet<CategoryServiceType>(); //множество категорий. для mAPI < 5.20

	public boolean isIncludeServices()
	{
		return includeServices;
	}

	public void setIncludeServices(boolean includeServices)
	{
		this.includeServices = includeServices;
	}

	public Set<CategoryServiceType> getPaymentCategories()
    {
        return categories;
    }

    public void setPaymentCategories(Set<CategoryServiceType> categories)
    {
        this.categories = categories;
    }
}
