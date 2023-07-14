package com.rssl.phizic.web.actions.payments.catalog;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.operations.ext.sbrf.payment.ListServicesPaymentSearchOperation;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.web.actions.payments.IndexForm;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import java.util.Collections;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author krenev
 * @ created 20.10.14
 * @ $Author$
 * @ $Revision$
 * Базовый класс поиска в каталоге услуг для каналов, отличных от веба
 */
public abstract class ApiSearchCatalogActionBase extends ApiCatalogActionBase
{

	public ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ApiSearchCatalogFormBase frm = (ApiSearchCatalogFormBase) form;
		if (StringHelper.isNotEmpty(getSearchString(frm)))
		{
			return search(frm);
		}
		frm.setSearchResults(Collections.emptyList());
		return mapping.findForward(FORWARD_START);
	}

	protected String getSearchString(IndexForm form)
	{
		ApiSearchCatalogFormBase frm = (ApiSearchCatalogFormBase) form;
		return frm.getSearch();
	}

	protected boolean getFinalDescendants(IndexForm frm)
	{
		return true;
	}

	protected ListServicesPaymentSearchOperation createSearchOperation() throws BusinessException, BusinessLogicException
	{
		return createOperation("SearchServicesPaymentApiOperation", "RurPayJurSB");
	}
}
