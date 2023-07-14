package com.rssl.phizic.business.loanProduct;

import com.rssl.common.forms.TemporalDocumentException;
import com.rssl.common.forms.validators.MultiFieldsValidatorBase;
import com.rssl.phizic.business.loans.products.ModifiableLoanProduct;
import com.rssl.phizic.business.loans.products.ModifiableLoanProductService;
import com.rssl.phizic.business.BusinessException;

import java.util.Map;

/**
 * @author gulov
 * @ created 17.08.2011
 * @ $Authors$
 * @ $Revision$
 */
public class LoanProductValidator extends MultiFieldsValidatorBase
{
	private static final String LOAN_PRODUCT_ID  = "loan";
	private static final String CHANGE_DATE  = "changeDate";
	private static final ModifiableLoanProductService service = new ModifiableLoanProductService();

	public boolean validate(Map values) throws TemporalDocumentException
	{
		Long conditionId  = (Long) retrieveFieldValue(LOAN_PRODUCT_ID, values);
		Long changeDate  = Long.parseLong((String) retrieveFieldValue(CHANGE_DATE, values));

		try
		{
			ModifiableLoanProduct product = service.getOfferProductByConditionId(conditionId);

			return product.getChangeDate().getTimeInMillis() == changeDate ? true : false;

		}
		catch (BusinessException e)
		{
			throw new TemporalDocumentException(e);
		}
	}
}
