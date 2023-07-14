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
	private static final String OFFER_ID_ATTRIBUTE_NAME = "offer-id"; // id предложения
	private static final String OFFER_TYPE_ATTRIBUTE_NAME = "offer-type";   // тип предложения
	private static final String DEPARTMENT_TB = "tb";   // тб выбранного департамента
	private static final String DEPARTMENT_OSB = "osb";   // осб выбранного департамента
	private static final String DEPARTMENT_VSP = "vsp";   // всп выбранного департамента

	/**
	 * @return id предложения
	 */
	public String getOfferId()
	{
		return getNullSaveAttributeStringValue(OFFER_ID_ATTRIBUTE_NAME);
	}

	/**
	 * @return получает номер типа предложения (в таком виде оно у нас хранится у платежа в БД)
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
	 * @return получет тип предложения
	 */
	public LoanCardOfferType getOfferTypeString()
	{
		return LoanCardOfferType.valueOf(Integer.valueOf(getNullSaveAttributeStringValue(OFFER_TYPE_ATTRIBUTE_NAME)));
	}

 	/**
    * Фактичский тип документа
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
