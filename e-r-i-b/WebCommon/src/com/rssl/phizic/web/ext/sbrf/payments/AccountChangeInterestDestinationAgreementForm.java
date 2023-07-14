package com.rssl.phizic.web.ext.sbrf.payments;

import com.rssl.phizic.web.actions.ActionFormBase;

/**
 * Форма для просмотра доп. соглашения по заявке на изменение порядка уплаты процентов
 * @author Jatsky
 * @ created 01.10.13
 * @ $Author$
 * @ $Revision$
 */

public class AccountChangeInterestDestinationAgreementForm extends ActionFormBase
{
	private Long id;
	private String collateralAgreement;

	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public String getCollateralAgreement()
	{
		return collateralAgreement;
	}

	public void setCollateralAgreement(String collateralAgreement)
	{
		this.collateralAgreement = collateralAgreement;
	}
}