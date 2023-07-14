package com.rssl.phizic.business.payments.forms.validators;

import com.rssl.phizic.gate.bankroll.Card;

import java.util.Map;

/**
 *
 * Валидатор запрещает переводить деньги со счёта на виртуальную карту
 *
 * @author Balovtsev
 * @created 03.03.2011
 * @ $Author$
 * @ $Revision$
 */

public class VirtualCardTransferFromValidator extends VirtualCardTransferValidatorBase
{
	public boolean validate(Map values)
	{
		Card consumer = getCard( values.get(FIELD_FROM_RESOURCE) );
		Card supplier = getCard( values.get(FIELD_TO_RESOURCE)   );

		if((supplier != null && supplier.isVirtual()) && consumer == null)
		{
			return false;
		}
		return true;
	}
}
