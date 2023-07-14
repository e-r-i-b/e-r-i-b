package com.rssl.phizic.business.ermb.sms.command;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.ermb.ErmbProfileImpl;
import com.rssl.phizic.business.ermb.ErmbTariff;
import com.rssl.phizic.business.ermb.ErmbTariffService;
import com.rssl.phizic.common.types.exceptions.InternalErrorException;
import com.rssl.phizic.common.types.exceptions.UserErrorException;

import java.util.List;

/**
 * СМС-команда "Показать текущий тариф"
 * @author Rtischeva
 * @ created 09.04.2013
 * @ $Author$
 * @ $Revision$
 */
class ShowTariffCommand extends CommandBase
{
	private final static ErmbTariffService tariffService = new ErmbTariffService();

	///////////////////////////////////////////////////////////////////////////

	public void doExecute()
	{
		try
		{
			if (!getPhoneTransmitter().equals(getErmbProfile().getMainPhoneNumber()))
				throw new UserErrorException(messageBuilder.buildNotActiveErmbPhoneMessage());
			ErmbProfileImpl profile = getErmbProfile();
			ErmbTariff activeTarif = profile.getTarif();
			List<ErmbTariff> allTariffs = tariffService.getAllTariffs();

			sendMessage(messageBuilder.buildTariffMessage(activeTarif, allTariffs));
		}
		catch (BusinessException e)
		{
			throw new InternalErrorException(e);
		}

	}

	@Override
	public String toString()
	{
		return "ТАРИФ[]";
	}

	public String getCommandName()
	{
		return "SHOW_TARIFF";
	}
}
