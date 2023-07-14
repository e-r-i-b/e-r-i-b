package com.rssl.phizic.business.documents.payments;

import com.rssl.phizic.gate.documents.GateDocument;
import com.rssl.common.forms.doc.TypeOfPayment;

/**
 * @author Mescheryakova
 * @ created 30.05.2011
 * @ $Author$
 * @ $Revision$
 */

public class LoanProductClaim extends LoanProductClaimBase
{
	private final static String PRODUCT_ID = "productId";  // id продукта

	/**
    * Фактичский тип документа
    */
	public Class<? extends GateDocument> getType()
	{
		return LoanProductClaim.class;
	}

	/**
	 * @return id кредитного продукта
	 */
	public Long getProductId()
	{
		try
		{
			return  Long.valueOf(getNullSaveAttributeStringValue(PRODUCT_ID));
		}
		catch(NumberFormatException e)
		{
			return null;
		}                                                       
	}

	public String getIdentityFieldName()
	{
		return PRODUCT_ID;
	}

	public String getIdentityFieldValue()
	{
		return getNullSaveAttributeStringValue(PRODUCT_ID);
	}

	public TypeOfPayment getTypeOfPayment()
	{
		return TypeOfPayment.INTERNAL_PAYMENT_OPERATION;
	}
}