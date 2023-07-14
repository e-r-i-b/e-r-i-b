package com.rssl.phizic.web.common.socialApi.payments.services;

import com.rssl.phizic.web.actions.payments.IndexForm;
import com.rssl.phizic.web.actions.payments.catalog.ApiCatalogActionBase;

/**
 * @author Dorzhinov
 * @ created 12.01.2012
 * @ $Author$
 * @ $Revision$
 */
public class ListCategoryServiceProviderMobileAction extends ApiCatalogActionBase
{

	protected String getChanel()
	{
		return "MAPI";
	}

	protected boolean getFinalDescendants(IndexForm form)
	{
		ListCategoryMobileForm frm = (ListCategoryMobileForm) form;
		return !frm.isIncludeServices();
	}
}
