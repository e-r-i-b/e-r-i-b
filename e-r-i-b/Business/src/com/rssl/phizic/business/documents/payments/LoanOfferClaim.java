package com.rssl.phizic.business.documents.payments;

import com.rssl.phizic.gate.documents.GateDocument;
import com.rssl.common.forms.doc.TypeOfPayment;

/**
 * @author Mescheryakova
 * @ created 23.05.2011
 * @ $Author$
 * @ $Revision$
 */

public class LoanOfferClaim extends LoanProductClaimBase
{
	private static final String MONEY_OUT_BOUNDS = "is-money-out-off-bounds";
	/**
    * ���������� ��� ���������
    */
	public Class<? extends GateDocument> getType()
	{
		return LoanOfferClaim.class;
	}

	public String getIdentityFieldName()
	{
		return null;
	}

	public String getIdentityFieldValue()
	{
		return null;
	}

	public TypeOfPayment getTypeOfPayment()
	{
		return TypeOfPayment.INTERNAL_PAYMENT_OPERATION;
	}

	/**
	 * @return ������� ����� �� ����� �� ������������ ������� ��� ���������� ������(� BUG052409)
	 */
	public Boolean isMoneyOutBounds()
	{
		return Boolean.valueOf(getNullSaveAttributeStringValue(MONEY_OUT_BOUNDS));
	}


	/**
	 * @param isMoneyOutBounds ������� ������ ����� �� ���������� �������
	 */
	public void setMoneyOutBounds(boolean isMoneyOutBounds)
	{
		setNullSaveAttributeBooleanValue(MONEY_OUT_BOUNDS, isMoneyOutBounds);
	}


}
