package com.rssl.phizic.business.payments.forms.validators;

import com.rssl.common.forms.TemporalDocumentException;
import com.rssl.common.forms.validators.MultiFieldsValidatorBase;
import com.rssl.ikfl.crediting.OfferId;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.loanclaim.LoanClaimHelper;
import com.rssl.phizic.utils.StringHelper;

import java.math.BigDecimal;
import java.util.Map;

/**
 * @author Balovtsev
 * @since 12.02.2015.
 */
public class ExtendedLoanClaimChangesMultiFieldValidator extends MultiFieldsValidatorBase
{
	public boolean validate(Map values) throws TemporalDocumentException
	{
		Long loanPeriod = (Long) values.get("loanPeriod");

		try
		{
			if (values.get("useLoanOffer") == Boolean.TRUE)
			{
				BigDecimal loanOfferAmount = (BigDecimal) values.get("loanOfferAmount");
				OfferId    loanOfferId     = OfferId.fromString((String) values.get("loanOfferId"));

				String message = LoanClaimHelper.validateLoanOffer(loanOfferId, loanOfferAmount, loanPeriod.intValue());
				if (StringHelper.isNotEmpty(message))
				{
					setMessage(message);
					return false;
				}
			}
			else
			{
				Long   conditionId         = (Long)   values.get("condId");
				Long   conditionCurrencyId = (Long)   values.get("condCurrId");
				String jsonCondition       = (String) values.get("jsonCondition");
				String jsonCurrency        = (String) values.get("jsonCurrency");

				if (LoanClaimHelper.hasConditionChange(conditionId, conditionCurrencyId, jsonCondition, jsonCurrency))
				{
					setMessage("По выбранному кредиту изменились условия предоставления денежных средств. Пожалуйста, ознакомьтесь с новыми условиями.");
					return false;
				}

				BigDecimal loanAmount = (BigDecimal) values.get("loanAmount");
				String     error      = LoanClaimHelper.validateCreditProductCondition(loanPeriod.intValue(), conditionCurrencyId, conditionId, loanAmount);

				if (StringHelper.isNotEmpty(error))
				{
					setMessage(error);
					return false;
				}
			}
		}
		catch (BusinessException e)
		{
			throw new TemporalDocumentException(e);
		}

		return true;
	}
}
