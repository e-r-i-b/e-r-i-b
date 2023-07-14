package com.rssl.phizic.business.documents.payments;

import com.rssl.phizic.gate.documents.GateDocument;
import com.rssl.common.forms.doc.TypeOfPayment;

/**
 * @author Mescheryakova
 * @ created 30.05.2011
 * @ $Author$
 * @ $Revision$
 */

@Deprecated
public class LoanCardProductClaim extends LoanCardClaimBase
{
 	private final static String CARD_PRODUCT_ID_ATTRIBUTE_NAME = "cardProductId"; // идентификатор карточного продукта
    private final static String INCOME_ATTRIBUTE_NAME = "income"; //id уровня дохода
	/**
    * Фактичский тип документа
    */
	public Class<? extends GateDocument> getType()
	{
		return com.rssl.phizic.gate.claims.cards.LoanCardProductClaim.class;
	}

	/**
	 * @return идентификатор карточного продукта
	 */
	public Long getCardProductId()
	{
		try
		{
			return  Long.valueOf(getNullSaveAttributeStringValue(CARD_PRODUCT_ID_ATTRIBUTE_NAME));
		}
		catch(NumberFormatException e)
		{
			return null;
		}
	}

	public String getIdentityFieldName()
	{
		return CARD_PRODUCT_ID_ATTRIBUTE_NAME;
	}

	public String getIdentityFieldValue()
	{
		return getNullSaveAttributeStringValue(CARD_PRODUCT_ID_ATTRIBUTE_NAME);
	}

    public String getIncome()
    {
        return getNullSaveAttributeStringValue(INCOME_ATTRIBUTE_NAME);
    }

	public TypeOfPayment getTypeOfPayment()
	{
		return TypeOfPayment.INTERNAL_PAYMENT_OPERATION;
	}
}

