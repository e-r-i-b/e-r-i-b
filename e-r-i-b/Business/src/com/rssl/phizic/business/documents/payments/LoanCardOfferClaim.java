package com.rssl.phizic.business.documents.payments;

import com.rssl.common.forms.doc.TypeOfPayment;
import com.rssl.phizic.business.loanCardOffer.LoanCardOfferType;
import com.rssl.phizic.gate.documents.GateDocument;

/**
 * @author Mescheryakova
 * @ created 26.05.2011
 * @ $Author$
 * @ $Revision$
 */

@Deprecated
public class LoanCardOfferClaim extends LoanCardClaimBase
{
	private static final String OFFER_ID_ATTRIBUTE_NAME = "offer-id"; // id �����������
	private static final String OFFER_TYPE_ATTRIBUTE_NAME = "offer-type";   // ��� �����������
	private static final String DEPARTMENT_TB = "tb";   // �� ���������� ������������
	private static final String DEPARTMENT_OSB = "osb";   // ��� ���������� ������������
	private static final String DEPARTMENT_VSP = "vsp";   // ��� ���������� ������������

	/**
	 * @return id �����������
	 */
	public String getOfferId()
	{
		return getNullSaveAttributeStringValue(OFFER_ID_ATTRIBUTE_NAME);
	}

	/**
	 * @return �������� ����� ���� ����������� (� ����� ���� ��� � ��� �������� � ������� � ��)
	 */
	public Long getOfferType()
	{
		return Long.valueOf(getNullSaveAttributeStringValue(OFFER_TYPE_ATTRIBUTE_NAME));
	}

	public String getDepartmentTb()
	{
		return String.valueOf(getNullSaveAttributeStringValue(DEPARTMENT_TB));
	}

	public String getDepartmentOsb()
	{
		return String.valueOf(getNullSaveAttributeStringValue(DEPARTMENT_OSB));
	}

	public String getDepartmentVsp()
	{
		return String.valueOf(getNullSaveAttributeStringValue(DEPARTMENT_VSP));
	}

	/**
	 * @return ������� ��� �����������
	 */
	public LoanCardOfferType getOfferTypeString()
	{
		return LoanCardOfferType.valueOf(Integer.valueOf(getNullSaveAttributeStringValue(OFFER_TYPE_ATTRIBUTE_NAME)));
	}

 	/**
    * ���������� ��� ���������
    */
	public Class<? extends GateDocument> getType()
	{
		return com.rssl.phizic.gate.claims.cards.LoanCardOfferClaim.class;
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
}
