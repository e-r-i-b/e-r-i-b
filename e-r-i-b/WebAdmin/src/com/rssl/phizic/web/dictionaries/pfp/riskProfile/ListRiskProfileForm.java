package com.rssl.phizic.web.dictionaries.pfp.riskProfile;

import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.phizic.business.dictionaries.pfp.riskProfile.ProductType;
import com.rssl.phizic.business.dictionaries.pfp.riskProfile.RiskProfile;
import com.rssl.phizic.business.dictionaries.pfp.riskProfile.RiskProfileUtil;
import com.rssl.phizic.web.common.ListFormBase;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author akrenev
 * @ created 08.02.2012
 * @ $Author$
 * @ $Revision$
 *
 * форма списка риск-профилей
 */

public class ListRiskProfileForm extends ListFormBase
{
	public static final Form FILTER_FORM = FormBuilder.EMPTY_FORM;
	private static final List<ProductType> productTypeList = RiskProfileUtil.getProductTypes(); //типы продуктов
	private Map<String, List<RiskProfile>> riskProfilesMap = new HashMap<String, List<RiskProfile>>();

	/**
	 * @return список возможных типов продуктов
	 */
	public List<ProductType> getProductTypeList()
	{
		//noinspection ReturnOfCollectionOrArrayField
		return productTypeList;
	}

	/**
	 * добавить список риск-профилей для сегмента
	 * @param segment сегмент
	 * @param riskProfiles риск-профили
	 */
	public void addRiskProfiles(String segment, List<RiskProfile> riskProfiles)
	{
		riskProfilesMap.put(segment, riskProfiles);
	}

	/**
	 * @return риск-профили, разбитые по сегментам
	 */
	public Map<String, List<RiskProfile>> getRiskProfilesMap()
	{
		return Collections.unmodifiableMap(riskProfilesMap);
	}
}
