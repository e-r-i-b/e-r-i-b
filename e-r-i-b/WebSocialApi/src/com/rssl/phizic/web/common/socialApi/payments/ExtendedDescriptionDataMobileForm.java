package com.rssl.phizic.web.common.socialApi.payments;

import com.rssl.phizic.web.common.descriptions.ExtendedDescriptionDataForm;

/**
 * Форма просмотра правил покупки
 * @author Pankin
 * @ created 20.05.2013
 * @ $Author$
 * @ $Revision$
 */

public class ExtendedDescriptionDataMobileForm extends ExtendedDescriptionDataForm
{
	private String agreementId;

	public String getAgreementId()
	{
		return agreementId;
	}

	public void setAgreementId(String agreementId)
	{
		this.agreementId = agreementId;
	}
}
