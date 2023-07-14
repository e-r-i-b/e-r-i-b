package com.rssl.phizic.web.ext.sbrf.payments;

import com.rssl.phizic.web.actions.ActionFormBase;

/**
 * @author Balovtsev
 * @version 24.09.13 9:31
 */
public class ChangeDepositMinimumBalanceAgreementForm extends ActionFormBase
{
	private Long   documentId;
	private String collateralAgreement;

	public Long getDocumentId()
	{
		return documentId;
	}

	public void setDocumentId(Long documentId)
	{
		this.documentId = documentId;
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
