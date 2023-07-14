package com.rssl.phizic.business.web.widget.strategy;

import com.rssl.phizic.business.persons.PersonHelper;
import com.rssl.phizic.business.profile.ProfileUtils;
import com.rssl.phizic.business.web.WidgetDefinition;

/**
 * @author Dorzhinov
 * @ created 15.01.2013
 * @ $Author$
 * @ $Revision$
 */
public class OffersWidgetAccessor extends GenericWidgetAccessor
{
	@Override
	protected boolean checkData(WidgetDefinition widgetDefinition)
	{
//		return PersonHelper.hasRegularPassportRF(person) && ProfileUtils.isShowBankOffersOnMain();
		return true;
	}
}
