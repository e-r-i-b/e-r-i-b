package com.rssl.phizic.business.pfp.handlers;

import com.rssl.common.forms.DocumentLogicException;
import com.rssl.common.forms.doc.StateMachineEvent;
import com.rssl.phizic.business.pfp.PersonalFinanceProfile;
import com.rssl.phizic.operations.pfp.FinancePlanCalculator;

/**
 * @author lepihina
 * @ created 17.09.13
 * @ $Author$
 * @ $Revision$
 */
public class CheckNegativeAmountsInFinancePlanHandler extends PersonalFinanceProfileHandlerBase
{
	private static final String ERROR_MESSAGE = "� ��� ���� ����, ������� �� ����� ���� ������������. �������������� ������������� ��������������, ����� ������� ��� ���� � ����.";

	public void process(PersonalFinanceProfile profile, StateMachineEvent stateMachineEvent) throws DocumentLogicException
	{
		FinancePlanCalculator financePlanCalculator = new FinancePlanCalculator(profile);
		if (!financePlanCalculator.getNegativeBalanceList().isEmpty())
			throw makeValidationFail(ERROR_MESSAGE);
	}
}