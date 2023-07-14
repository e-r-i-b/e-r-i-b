package com.rssl.phizic.business.ermb.sms.command;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.ermb.ErmbProfileImpl;
import com.rssl.phizic.common.types.TextMessage;
import com.rssl.phizic.common.types.exceptions.InternalErrorException;
import com.rssl.phizic.common.types.exceptions.UserErrorException;

/**
 * —мс-команда "быстрые сервисы" (отключение быстрых сервисов)
 * @author Rtischeva
 * @ created 15.04.2013
 * @ $Author$
 * @ $Revision$
 */
class QuickServicesCommand extends CommandBase
{
	public void doExecute()
	{
		if (!getPhoneTransmitter().equals(getErmbProfile().getMainPhoneNumber()))
			throw new UserErrorException(messageBuilder.buildNotActiveErmbPhoneMessage());
		ErmbProfileImpl profile = getErmbProfile();
		profile.setFastServiceAvailable(false);
		try
		{
			profileService.addOrUpdate(profile);
		}
		catch(BusinessException e)
		{
			throw new InternalErrorException(e);
		}

		TextMessage message = messageBuilder.buildQuickServiceMessage(false);

		if (message != null)
			sendMessage(message);
	}

	@Override
	public String toString()
	{
		return "ЌќЋ№[]";
	}

	public String getCommandName()
	{
		return "QUICK_SERVICES";
	}
}
