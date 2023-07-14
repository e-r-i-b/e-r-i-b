package com.rssl.phizic.business.pfp.handlers;

import com.rssl.common.forms.DocumentLogicException;
import com.rssl.common.forms.doc.StateMachineEvent;
import com.rssl.phizic.business.pfp.PersonalFinanceProfile;

/**
 * @author akrenev
 * @ created 14.09.2012
 * @ $Author$
 * @ $Revision$
 *
 * Хендлер добавляющий сообщение о том что планирование старое
 */
public class NeedUpdatePFPMessageHandler extends PersonalFinanceProfileHandlerBase
{
	private static final String MESSAGE = "Уважаемые клиенты! Персональное финансовое планирование обновлено. " +
			"В отчете проставлена доходность продуктов по умолчанию. Если Вы хотите ее изменить, нажмите на кнопку «Назад». " +
			"Если Вы хотите пройти новое планирование, нажмите на кнопку «Повторить планирование».";

	public void process(PersonalFinanceProfile profile, StateMachineEvent stateMachineEvent) throws DocumentLogicException
	{
		stateMachineEvent.addMessage(MESSAGE);
	}
}
