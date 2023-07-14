package com.rssl.phizic.web.common.mobile.payments;

import com.rssl.phizic.web.common.descriptions.ExtendedDescriptionDataAction;
import com.rssl.phizic.web.common.descriptions.ExtendedDescriptionDataForm;

/**
 * Просмотр правил покупки
 * @author Pankin
 * @ created 20.05.2013
 * @ $Author$
 * @ $Revision$
 */

public class ExtendedDescriptionDataMobileAction extends ExtendedDescriptionDataAction
{
	protected String getDescriptionKey(ExtendedDescriptionDataForm frm)
	{
		return ((ExtendedDescriptionDataMobileForm) frm).getAgreementId();
	}
}
