package com.rssl.phizic.business.pfp.handlers;

import com.rssl.common.forms.DocumentLogicException;
import com.rssl.common.forms.doc.StateMachineEvent;
import com.rssl.phizic.business.pfp.PersonalFinanceProfile;

/**
 * @author akrenev
 * @ created 02.10.13
 * @ $Author$
 * @ $Revision$
 *
 * �������, ����������� �������� � ������ ������������� �������������� ���������
 */

public class InvestmentProductRiskMessageHandler extends PersonalFinanceProfileHandlerBase
{
	private static final String MESSAGE = "��� ������������� �������������� ��������� " +
			"��������� ����� �������� ����� ��� �������, ��� � ����� ���� �������������� ����� ��������� �������. " +
			"�����, ���������� � �������, �� ����������� ���������� � �������. " +
			"��� ��������� ������ �� ���� ��������������� �� �������������� �������� �������������� �������";

	public void process(PersonalFinanceProfile profile, StateMachineEvent stateMachineEvent) throws DocumentLogicException
	{
		stateMachineEvent.addMessage(MESSAGE);
	}
}
