package com.rssl.phizic.web.atm.payments.services;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.config.atm.AtmApiConfig;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.dataaccess.query.Query;
import com.rssl.phizic.operations.ext.sbrf.payment.ListServicesPaymentSearchOperation;
import com.rssl.phizic.operations.ext.sbrf.payment.SearchServicesPaymentApiOperation;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.web.actions.payments.IndexForm;
import com.rssl.phizic.web.actions.payments.ListPaymentServiceFormBase;
import com.rssl.phizic.web.actions.payments.catalog.ApiSearchCatalogActionBase;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * <b>Поиск</b> поставщиков. Для atmAPI.
 @author Pankin
 @ created 19.11.2010
 @ $Author$
 @ $Revision$
 /atm/private/provider/search.do
 */
public class SearchServiceProviderATMAction extends ApiSearchCatalogActionBase
{
	private static final String FORWARD_START_FULL = "StartFull";

	public ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		SearchServiceProviderATMForm frm = (SearchServiceProviderATMForm) form;
		Boolean isShowServices = !getFinalDescendants(frm);

		if (StringHelper.isNotEmpty(frm.getSearch()))
		{
			search(frm);
			//поиск по услугам
			if (isShowServices && frm.isIncludeServices())
			{
				ListServicesPaymentSearchOperation operation = createSearchOperation();
				Query queryServices = operation.createQuery("searchServices");
				fillQueryServices(queryServices, frm);
				frm.setServices(queryServices.<Object[]>executeList());
			}
			else
			{
				frm.setServices(Collections.<Object[]>emptyList());
			}
		}
		else
		{
			frm.setSearchResults(Collections.emptyList());
		}
		
		return mapping.findForward(isShowServices ? FORWARD_START_FULL : FORWARD_START);
	}

	protected void fillSearchQuery(Query query, IndexForm form) throws BusinessException
	{
		SearchServiceProviderATMForm frm = (SearchServiceProviderATMForm) form;
		super.fillSearchQuery(query, frm);
		query.setParameter("includeServices", frm.isIncludeServices());
	}

	protected String getSearchResultQueryName(IndexForm frm)
	{
		return getFinalDescendants(frm)?"searchResult":"searchResultFull";
	}

	protected String getChanel()
	{
		return "ATMAPI";
	}

	protected boolean getFinalDescendants(IndexForm frm)
	{
		return !ConfigFactory.getConfig(AtmApiConfig.class).isShowServices();
	}

	protected Long getRegionId(ListPaymentServiceFormBase form) throws BusinessException
	{
		SearchServiceProviderATMForm frm = (SearchServiceProviderATMForm) form;
		return ListATMActionBase.getRegionIdWithGuid(frm);
	}

	private void fillQueryServices(Query query, SearchServiceProviderATMForm form)
	{
		String search = form.getSearch();
		search = search.trim();
		query.setParameter("search", QUOTE_PATTERN.matcher(search).replaceAll(QUOTE));
	}
}
