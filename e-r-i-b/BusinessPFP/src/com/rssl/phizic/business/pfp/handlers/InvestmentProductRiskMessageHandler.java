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
 * Хендлер, добавляющий собщение о рисках использования инвестиционных продуктов
 */

public class InvestmentProductRiskMessageHandler extends PersonalFinanceProfileHandlerBase
{
	private static final String MESSAGE = "При использовании инвестиционных продуктов " +
			"стоимость Ваших вложений может как вырасти, так и стать ниже первоначальной суммы вложенных средств. " +
			"Доход, полученный в прошлом, не гарантирует доходность в будущем. " +
			"ОАО «Сбербанк России» не несёт ответственности за самостоятельно принятые инвестиционные решения";

	public void process(PersonalFinanceProfile profile, StateMachineEvent stateMachineEvent) throws DocumentLogicException
	{
		stateMachineEvent.addMessage(MESSAGE);
	}
}
