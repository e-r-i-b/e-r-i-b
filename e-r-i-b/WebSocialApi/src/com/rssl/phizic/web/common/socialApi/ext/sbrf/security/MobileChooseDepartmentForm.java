package com.rssl.phizic.web.common.socialApi.ext.sbrf.security;

import com.rssl.phizic.web.common.client.ext.sbrf.security.ChooseDepartmentForm;

/**
 * @author Dorzhinov
 * @ created 25.10.2011
 * @ $Author$
 * @ $Revision$
 */
public class MobileChooseDepartmentForm extends ChooseDepartmentForm
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
