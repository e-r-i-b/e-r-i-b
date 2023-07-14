package com.rssl.phizic.business.ermb.sms.command;

import com.rssl.phizic.common.types.exceptions.UserErrorException;
import com.rssl.phizic.payment.BlockingCardTaskImpl;
import com.rssl.phizic.security.ConfirmBean;
import com.rssl.phizic.security.ConfirmToken;
import com.rssl.phizic.security.PersonConfirmManager;
import com.rssl.phizic.utils.StringHelper;

/**
 * @author Erkin
 * @ created 27.05.2013
 * @ $Author$
 * @ $Revision$
 */

/**
 * СМС-команда "Подтверждение"
 */
class ConfirmCommand extends CommandBase
{
	/**
	 * Токен подтверждения
	 * Если не указан, используется confirmCode
	 */
	private ConfirmToken confirmToken;

	private String confirmCode;

	///////////////////////////////////////////////////////////////////////////

	void setConfirmToken(ConfirmToken confirmToken)
	{
		this.confirmToken = confirmToken;
	}

	void setConfirmCode(String confirmCode)
	{
		this.confirmCode = confirmCode;
	}

	public void doExecute()
	{
		PersonConfirmManager confirmManager = getPersonConfirmManager();

		if (confirmToken == null)
			confirmToken = confirmManager.captureConfirm(confirmCode, getPhoneTransmitter(), true);

		if (confirmToken == null)
			throw new UserErrorException(messageBuilder.buildIncorrectConfirmCodeMessage());

		ConfirmBean confirmBean = (ConfirmBean)confirmToken;
		boolean isBlockingCard = StringHelper.equals(confirmBean.getConfirmableTaskClass().getName(), BlockingCardTaskImpl.class.getName());
		boolean isExpired = confirmBean.isExpired();
		//для блокировки карт отдельное сообщение
		if (isBlockingCard && isExpired)
			throw new UserErrorException(messageBuilder.buildIncorrectCardBlockConfirmCodeMessage());

		confirmManager.grantConfirm(confirmToken);
	}

	@Override
	public String toString()
	{
		return "ПОДТВЕРЖДЕНИЕ[]";
	}

	public String getCommandName()
	{
		return "CONFIRM";
	}
}
