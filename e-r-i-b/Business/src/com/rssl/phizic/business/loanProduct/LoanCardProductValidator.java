package com.rssl.phizic.business.loanProduct;

import com.rssl.common.forms.TemporalDocumentException;
import com.rssl.common.forms.validators.MultiFieldsValidatorBase;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.creditcards.products.CreditCardProduct;
import com.rssl.phizic.business.creditcards.products.CreditCardProductService;

import java.util.Map;

/**
 * @author gulov
 * @ created 19.08.2011
 * @ $Authors$
 * @ $Revision$
 */
public class LoanCardProductValidator extends MultiFieldsValidatorBase
{
	private static final String LOAN_PRODUCT_ID  = "loan";
	private static final String CHANGE_DATE  = "changeDate";
	private static final CreditCardProductService service = new CreditCardProductService();

	public boolean validate(Map values) throws TemporalDocumentException
	{
		Long conditionId  = (Long) retrieveFieldValue(LOAN_PRODUCT_ID, values);
		Long changeDate  = Long.parseLong((String) retrieveFieldValue(CHANGE_DATE, values));

		try
		{
			CreditCardProduct product = service.findCreditCardProductByCreditCardConditionId(conditionId);
			return product.getChangeDate().getTimeInMillis() == changeDate;
		}
		catch (BusinessException e)
		{
			throw new TemporalDocumentException(e);
		}
	}
}
