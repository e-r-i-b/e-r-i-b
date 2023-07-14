package com.rssl.phizic.business.payments.forms.validators;

import com.rssl.phizic.gate.bankroll.Card;

import java.util.Map;

/**
 *
 * ��������� ��������� ���������� ������ � ����������� ���� �� �����
 *
 * @author Balovtsev
 * @created 02.03.2011
 * @ $Author$
 * @ $Revision$
 */

public class VirtualCardTransferToValidator extends VirtualCardTransferValidatorBase
{
	public boolean validate(Map values)
	{
		Card consumer = getCard( values.get(FIELD_FROM_RESOURCE) );
		Card supplier = getCard( values.get(FIELD_TO_RESOURCE)   );

		if((consumer != null && consumer.isVirtual()) && supplier == null)
		{
			return false;
		}
		return true;
	}
}
