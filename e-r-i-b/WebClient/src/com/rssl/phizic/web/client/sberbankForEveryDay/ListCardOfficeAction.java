package com.rssl.phizic.web.client.sberbankForEveryDay;

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
import com.rssl.phizic.operations.sberbankForEveryDay.ListCardOfficeOperation;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.web.common.ListFormBase;
import com.rssl.phizic.web.common.client.cards.ListCreditCardOfficeAction;
import com.rssl.phizic.web.common.client.cards.ListCreditCardOfficeForm;

import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * @author lukina
 * @ created 27.01.2015
 * @ $Author$
 * @ $Revision$
 */
public class ListCardOfficeAction extends ListCreditCardOfficeAction
{
	public static final String TB_NOT_ALLOWED_ERROR_MESSAGE = "В вашем регионе временно отключена возможность заказа карт. Попробуйте позже";

	private static final RegionDictionaryService regionService = new RegionDictionaryService();

	protected ListEntitiesOperation createListOperation(ListFormBase frm) throws BusinessException, BusinessLogicException
	{
		return createOperation(ListCardOfficeOperation.class, "ListCardOfficeOperation");
	}

	@Override
	protected void fillQuery(Query query, Map<String, Object> filterParams) throws BusinessException, BusinessLogicException
	{
		String region = StringHelper.getEmptyIfNull(filterParams.get("region"));
		String street = StringHelper.getEmptyIfNull(filterParams.get("street"));
		String regionCodeTB = StringHelper.getEmptyIfNull(filterParams.get("regionCodeTB"));
		String name =  StringHelper.getEmptyIfNull(filterParams.get("name"));

		query.setParameter("region", region);
		query.setParameter("street", street);
		query.setParameter("name", name);

		if (regionCodeTB.equals(""))
		{
			List<String> allowedTb = ConfigFactory.getConfig(SBNKDConfig.class).getAllowedTB();
			if (!allowedTb.isEmpty())
				regionCodeTB = allowedTb.get(0);
		}

		query.setParameter("regionCodeTB", regionCodeTB);
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

		if (allowedTb.contains(StringHelper.getEmptyIfNull(filterParams.get("regionCodeTB"))) || StringHelper.isEmpty(regionId) || "-1".equals(regionId))
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
}
