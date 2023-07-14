package com.rssl.phizic.web.atm.ext.sbrf.security;

import com.rssl.phizic.web.common.client.ext.sbrf.security.ChooseDepartmentForm;

/**
 * @author Dorzhinov
 * @ created 25.10.2011
 * @ $Author$
 * @ $Revision$
 */
public class ChooseDepartmentATMForm extends ChooseDepartmentForm
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
