package com.rssl.phizic.web.common.mobile.incomes;

import com.rssl.phizic.web.common.client.incomes.ListIncomeLevelAction;
import com.rssl.phizic.web.common.client.incomes.ListIncomeLevelForm;

/**
 * @author Dorzhinov
 * @ created 12.02.2013
 * @ $Author$
 * @ $Revision$
 */
public class ListIncomeLevelMobileAction extends ListIncomeLevelAction
{
	@Override
	protected Long getIncomeId(ListIncomeLevelForm form)
	{
		ListIncomeLevelMobileForm mobileForm = (ListIncomeLevelMobileForm) form;
		return mobileForm.getId();
	}
}
