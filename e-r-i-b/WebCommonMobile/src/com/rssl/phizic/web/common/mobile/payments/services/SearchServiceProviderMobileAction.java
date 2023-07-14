package com.rssl.phizic.web.common.mobile.payments.services;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.dictionaries.regions.Region;
import com.rssl.phizic.business.regions.RegionHelper;
import com.rssl.phizic.dataaccess.DataAccessException;
import com.rssl.phizic.dataaccess.query.Query;
import com.rssl.phizic.operations.ext.sbrf.payment.SearchServicesPaymentApiOperation;
import com.rssl.phizic.security.util.MobileApiUtil;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.utils.mobile.MobileAPIVersions;
import com.rssl.phizic.web.actions.payments.ListPaymentServiceFormBase;
import com.rssl.phizic.web.actions.payments.catalog.ApiSearchCatalogActionBase;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * <b>Поиск</b> поставщиков. Для версий API 3.01 и выше.
 @author Pankin
 @ created 19.11.2010
 @ $Author$
 @ $Revision$

 */
public class SearchServiceProviderMobileAction extends ApiSearchCatalogActionBase
{
    private static final long API_ALL_REGION_ID = 0L;
	private static final String FORWARD_START_LT_52 = "StartLT52";

	public ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		boolean mobileApiGE52 = MobileApiUtil.isMobileApiGE(MobileAPIVersions.V5_20);
		if (mobileApiGE52)
		{
			return super.start(mapping, form, request, response);
		}
		return searchLT52(mapping, form);
	}

	protected String getChanel()
	{
		return "MAPI";
	}

	//-------Мусор старых АПИ
	private ActionForward searchLT52(ActionMapping mapping, ActionForm form) throws BusinessException, DataAccessException
	{
		SearchServiceProviderMobileForm frm = (SearchServiceProviderMobileForm) form;
		SearchServicesPaymentApiOperation operation = createOperation("SearchServicesPaymentApiOperation", "RurPayJurSB");

		Query query = operation.createQuery("searchMApiLT52");
		fillQueryLT52(query, frm, operation);
		frm.setSearchResults(query.executeList());
		return mapping.findForward(FORWARD_START_LT_52);
	}

	private void fillQueryLT52(Query query, SearchServiceProviderMobileForm form, SearchServicesPaymentApiOperation operation) throws BusinessException
	{
		String search = form.getSearch();
		if(!StringHelper.isEmpty(search))
		{
			search = search.trim();
			if (DIGIT_PATTERN.matcher(search).matches())
			{
				query.setParameter("search_long", search);
				query.setParameter("search", search);
			}
			else
			{
				query.setParameter("search_long", null);
				query.setParameter("search", QUOTE_PATTERN.matcher(search).replaceAll(QUOTE));
			}
		}
		else
		{
			query.setParameter("search_long", null);
			query.setParameter("search", null);
		}

		query.setParameter("mApi", true);
		query.setParameter("versionApi", MobileApiUtil.getApiVersionNumber().getSolid());
		query.setParameter("autoPaymentOnly", form.isAutoPaymentOnly());

		//регионы
		Long regionId = getRegionId(form);
		query.setParameter("region_id", RegionHelper.isAllRegionsSelected(regionId) ? null : regionId);
		query.setParameter("parent_region_id", operation.getRegionParentId(regionId));
		
		//типы переводов
		int cardProviderAllowed = operation.getCardProvidersAllowed();
		query.setParameter("isCardAccountType", cardProviderAllowed == 1);
		query.setParameter("isDepositType", operation.getAccountProvidersAllowed() == 1);

		query.setParameter("federalProvidersAllowed", cardProviderAllowed == 2 ? 1 : 0);
		query.setParameter("isFederalProvider", cardProviderAllowed == 2);
		query.setParameter("clientType", operation.getClientType());

		//доступность АП
		operation.setIQWaveAutoPaymentPermit(isIQWaveAutoPaymentPermit());
		operation.setESBAutoPaymentPermit(isESBAutoPaymentPermit());
	}

	/**
	 * Возвращает приходящий параметр regionId, если он задан.
	 * Если параметр отсутствует, то возвращается id текущего region-а клиента.
	 * Если текущий регион не задан, то возвращается null.
	 * @throws BusinessException
	 */
	protected Long getRegionId(ListPaymentServiceFormBase frm) throws BusinessException
	{
		SearchServiceProviderMobileForm form = (SearchServiceProviderMobileForm) frm;
		Long regionId = form.getRegionId();

        if(regionId == null)
        {
            Region region = RegionHelper.getCurrentRegion();
		    return region != null ? region.getId() : null;
        }

        if(regionId == API_ALL_REGION_ID)
            return null;

        return regionId;
	}
}
