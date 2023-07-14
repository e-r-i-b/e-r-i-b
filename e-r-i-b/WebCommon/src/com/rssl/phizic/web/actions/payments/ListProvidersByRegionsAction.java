package com.rssl.phizic.web.actions.payments;


import com.rssl.phizic.business.BusinessException;
import org.apache.commons.collections.CollectionUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author lukina
 * @ created 03.05.2012
 * @ $Author$
 * @ $Revision$
 */

public class ListProvidersByRegionsAction extends IndexAction
{
	public ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ListProvidersByRegionsForm frm = (ListProvidersByRegionsForm) form;
		String search = frm.getSearchServices();
		if (search != null && search.trim().length() > 0)
		{
			search(frm);
		}
		return getCurrentMapping().findForward(FORWARD_START);
	}

	protected String getPageList(IndexForm form)
	{
		ListProvidersByRegionsForm frm = (ListProvidersByRegionsForm) form;
		return frm.getPageListByRegion();
	}

	protected String getSearchResultQueryName(IndexForm frm)
	{
		return "searchAllRegions";
	}
	//поиск регионов для найденных поставщиков
	protected void findRegions(IndexForm frm)  throws Exception
	{
		List<Object>  searchRes = frm.getSearchResults();
		List<String> providers = new ArrayList<String>();
		for (Object res : searchRes)
		{
			Object[] res1 = (Object[]) res;
			providers.add((String) res1[2]);
		}
		if (CollectionUtils.isEmpty(providers))
			return;

		frm.setRegions(getRegionList(providers));
	}

	protected String[] getFunctionality(IndexForm frm) throws BusinessException
	{
		if (frm.getPageType().equals("index"))
			return getFunctionalityPayments();
		if (frm.getPageType().equals("autopayment"))
			return getFunctionalityAutopayments();
		if (frm.getPageType().equals("basketServices"))
			return getFunctionalityBasket();
		return new String[]{};
	}

	private String[] getFunctionalityBasket() throws BusinessException
	{
		return new String[]{"BASKET"};

	}
	private String[] getFunctionalityPayments() throws BusinessException
	{
		return new String[]{PAYMENTS_FUNCTIONALITY};

	}

	private String[] getFunctionalityAutopayments() throws BusinessException
	{
		if (isIQWaveAutoPaymentPermit() && isESBAutoPaymentPermit())
		{
			return new String[]{CatalogActionBase.ESB_AUTOPAYMENTS_FUNCTIONALITY, CatalogActionBase.IQW_AUTOPAYMENTS_FUNCTIONALITY};
		}
		if (isIQWaveAutoPaymentPermit())
		{
			return new String[]{CatalogActionBase.IQ_WAVE_UUID_PARAMETER_NAME};
		}
		if (isESBAutoPaymentPermit())
		{
			return new String[]{CatalogActionBase.ESB_AUTOPAYMENTS_FUNCTIONALITY};
		}
		return new String[]{};
	}

}
