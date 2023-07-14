package com.rssl.phizic.business.payments.forms.validators;

import com.rssl.common.forms.validators.FieldValidatorBase;
import com.rssl.common.forms.TemporalDocumentException;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.resources.external.DepositLink;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.context.PersonData;
import com.rssl.phizic.gate.deposit.DepositState;

/**
 * @author Krenev
 * @ created 28.07.2008
 * @ $Author$
 * @ $Revision$
 */
public class DepositIsOpenValidator extends FieldValidatorBase
{
	public DepositIsOpenValidator()
	{
		setMessage("¬клад закрыт");
	}

	public boolean validate(String value) throws TemporalDocumentException
	{
		try
		{
			if (isValueEmpty(value))
				return true;

			PersonData data = PersonContext.getPersonDataProvider().getPersonData();
			DepositLink depositLink = data.getDeposit(Long.valueOf(value));
			if (depositLink == null)
				return false;
			return depositLink.getDeposit().getState() == DepositState.open;
		}
		catch (BusinessException e)
		{
			throw new RuntimeException(e);
		}
		catch (BusinessLogicException e)
		{
			throw new RuntimeException(e);
		}
	}
}
