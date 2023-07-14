package com.rssl.phizic.web.common.client.cards;

import com.rssl.common.forms.Form;
import com.rssl.phizic.auth.GuestLogin;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.dictionaries.regions.Region;
import com.rssl.phizic.business.dictionaries.regions.RegionDictionaryService;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.config.sbnkd.SBNKDConfig;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.dataaccess.query.Query;
import com.rssl.phizic.operations.ListEntitiesOperation;
import com.rssl.phizic.operations.departments.CreditCardOfficeOperation;
import com.rssl.phizic.business.regions.RegionHelper;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.web.common.ListActionBase;
import com.rssl.phizic.web.common.ListFormBase;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Mescheryakova
 * @ created 25.04.2012
 * @ $Author$
 * @ $Revision$
 */

public class ListCreditCardOfficeAction extends ListActionBase
{

	public static final String TB_NOT_ALLOWED_ERROR_MESSAGE = "В вашем регионе временно отключена возможность заказа карт. Попробуйте позже";

	private static final RegionDictionaryService regionService = new RegionDictionaryService();

	protected Map<String, String> getAditionalKeyMethodMap()
	{
		Map<String, String> aditionalKeyMethodMap = super.getAditionalKeyMethodMap();
		aditionalKeyMethodMap.put("button.search", "filter");
		return aditionalKeyMethodMap;
	}

	protected ListEntitiesOperation createListOperation(ListFormBase frm) throws BusinessException, BusinessLogicException
	{
		return createOperation(CreditCardOfficeOperation.class, "CreditCardOfficeService");
	}

	protected Form getFilterForm(ListFormBase frm, ListEntitiesOperation operation)
	{
		return ListCreditCardOfficeForm.FILTER_FORM;
	}

	protected void fillQuery(Query query, Map<String, Object> filterParams) throws BusinessException, BusinessLogicException
	{
		final String  like = "%";
		String address = like;
		String reverse_address = like;

		String region = (String) filterParams.get("region");
		String street = (String) filterParams.get("street");
		String regionCodeTB = (String) filterParams.get("regionCodeTB");
		String name =  filterParams.get("name") == null ? like : (String) filterParams.get("name");

		if (!StringHelper.isEmpty(region) && !StringHelper.isEmpty(street))
		{
			address = region + like + street;
			reverse_address = street + like + region;
		}
		else if(!StringHelper.isEmpty(region))
		{
			address = region;
			reverse_address = region;
		}
		else if(!StringHelper.isEmpty(street))
		{
			address = street;
			reverse_address = street;
		}
		query.setParameter("address", address);
		query.setParameter("reverse_address", reverse_address);
		query.setParameter("name", name);
		query.setParameter("regionCodeTB", regionCodeTB);
	}

	protected Map<String, Object> getDefaultFilterParameters(ListFormBase frm, ListEntitiesOperation operation)	throws BusinessException, BusinessLogicException
	{
		Map<String, Object> result = new HashMap<String, Object>();
		Region region = RegionHelper.getCurrentRegion();

		if (region != null && region.getParent() != null)
		{
			Region parent = RegionHelper.getParentRegion(region);
			fillFilterParameters(result, parent);
		}
		else if (region != null)
		{
			fillFilterParameters(result, region);
		}

		return result;
	}

	@Override
	protected void doFilter(Map<String, Object> filterParams, ListEntitiesOperation operation, ListFormBase frm) throws Exception
	{
		List<String> allowedTb = ConfigFactory.getConfig(SBNKDConfig.class).getAllowedTB();
		ListCreditCardOfficeForm form = (ListCreditCardOfficeForm) frm;
		String regionId;
        regionId = form.getRegionId();
        if (StringHelper.isEmpty(regionId))
            regionId = StringHelper.getEmptyIfNull(filterParams.get("regionId"));

		Region region = null;
        if (StringHelper.isNotEmpty(regionId))
            region = regionService.findById(Long.valueOf(regionId));

		if (region == null) {
            filterParams.put("regionCodeTB", null);
        } else {
            filterParams.put("regionCodeTB", region.getCodeTB());
            filterParams.put("regionName", region.getName());
        }
		if (allowedTb.contains(StringHelper.getEmptyIfNull(filterParams.get("regionCodeTB"))))
		{
			Query query = operation.createQuery(getQueryName(frm));
			fillQuery(query, filterParams);
			frm.setData(query.executeList());
		}
		else
		{
			frm.setData(Collections.EMPTY_LIST);
			saveError(currentRequest(), TB_NOT_ALLOWED_ERROR_MESSAGE);
		}
		frm.setFilters(filterParams);
		updateFormAdditionalData(frm, operation);
	}

	private void fillFilterParameters(Map<String, Object> result, Region region)	throws BusinessException, BusinessLogicException
	{
		result.put("regionCodeTB", region.getCodeTB());
		result.put("regionId", region.getId());
		result.put("regionName", region.getName());
	}

}
