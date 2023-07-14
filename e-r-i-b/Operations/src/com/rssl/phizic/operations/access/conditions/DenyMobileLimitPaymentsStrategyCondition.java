package com.rssl.phizic.operations.access.conditions;

import com.rssl.phizic.auth.modes.StrategyCondition;
import com.rssl.phizic.business.documents.payments.BusinessDocument;
import com.rssl.phizic.security.ConfirmableObject;
import com.rssl.phizic.security.util.MobileApiUtil;
import com.rssl.phizic.util.ApplicationUtil;
import com.rssl.phizic.utils.mobile.MobileAPIVersions;

/**
 * «апрещает проведение платежа созданного не на основе шаблона в том случае, когда клиент находитс€ в доавторизационной зоне.
 *
 * @author Balovtsev
 * @since  19.08.2014
 */
public class DenyMobileLimitPaymentsStrategyCondition implements StrategyCondition
{
	public static final String MOBILE_LIMITED_VERSION_ERROR = "¬ данной версии системы ¬ы можете выполн€ть только оплату по шаблону.";

	public String getWarning()
	{
		return MOBILE_LIMITED_VERSION_ERROR;
	}

	public boolean checkCondition(ConfirmableObject object)
	{
		if(!(object instanceof BusinessDocument))
			return true;
		BusinessDocument document = (BusinessDocument) object;

		if (ApplicationUtil.isMobileApi() && MobileApiUtil.isMobileApiGE(MobileAPIVersions.V5_00))
		{
			if (!MobileApiUtil.isAuthorizedZone())
			{
				return document.isByTemplate();
			}
		}

		return true;
	}
}
