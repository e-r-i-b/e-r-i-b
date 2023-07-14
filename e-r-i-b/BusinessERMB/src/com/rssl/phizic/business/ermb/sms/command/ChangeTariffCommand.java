package com.rssl.phizic.business.ermb.sms.command;

import com.rssl.phizic.bankroll.BankrollProductLink;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.cards.CardsUtil;
import com.rssl.phizic.business.ermb.*;
import com.rssl.phizic.business.ermb.profile.ErmbUpdateListener;
import com.rssl.phizic.business.ermb.profile.events.ErmbProfileEvent;
import com.rssl.phizic.business.resources.external.CardLink;
import com.rssl.phizic.business.resources.external.NotBlockedCardFilter;
import com.rssl.phizic.business.resources.external.NotExpiredCardFilter;
import com.rssl.phizic.common.types.annotation.MandatoryParameter;
import com.rssl.phizic.common.types.exceptions.InternalErrorException;
import com.rssl.phizic.common.types.exceptions.UserErrorException;
import com.rssl.phizic.gate.bankroll.Card;
import com.rssl.phizic.security.ConfirmableTask;
import com.rssl.phizic.utils.StringHelper;

import java.util.Collection;

/**
 * @author Erkin
 * @ created 08.05.2013
 * @ $Author$
 * @ $Revision$
 */

/**
 * СМС-команда "Сменить тариф"
 */
public class ChangeTariffCommand extends CommandBase implements ConfirmableTask
{
	private final static ErmbTariffService tariffService = new ErmbTariffService();

	/**
	 * Имя целевого тарифа
	 */
	private String name;
	private static transient final NotBlockedCardFilter notBlockedCardFilter = new NotBlockedCardFilter();
	private static transient final NotExpiredCardFilter notExpiredCardFilter = new NotExpiredCardFilter();

	///////////////////////////////////////////////////////////////////////////

	@MandatoryParameter
	void setName(String name)
	{
		this.name = name;
	}

	public void doExecute()
	{
		if (!getPhoneTransmitter().equals(getErmbProfile().getMainPhoneNumber()))
			throw new UserErrorException(messageBuilder.buildNotActiveErmbPhoneMessage());
		// 1. Получаем новый тариф
		ErmbTariff newTariff = obtainNewTariff();

		//2. Получаем текущий тариф
		ErmbTariff currentTariff = getErmbProfile().getTarif();

		//3. Делаем проверки
		checkConditions(newTariff, currentTariff);

		// 4. Ждём подтверждения
		getPersonConfirmManager().askForConfirm(this);
	}

	public void confirmGranted()
	{
		// 5. Меняем тариф
		// Получаем тариф ещё раз, т.к. за время ожидания подтверждения тариф мог измениться
		ErmbTariff newTariff = obtainNewTariff();

		//Делаем проверки
		ErmbTariff currentTariff = getErmbProfile().getTarif();
		checkConditions(newTariff, currentTariff);

		checkCost(newTariff);

		if (newTariff != null)
			applyNewTariff(newTariff);
	}

	private ErmbTariff obtainNewTariff()
	{
		try
		{
			ErmbTariff newTariff = tariffService.getTariffByName(name);
			if (newTariff == null)
				throw new UserErrorException(messageBuilder.buildIncorrectTariffNameMessage());
			return newTariff;
		}
		catch (BusinessException e)
		{
			throw new InternalErrorException(e);
		}
	}

	private void checkConditions(ErmbTariff newTariff, ErmbTariff currentTariff)
	{
		if (newTariff.getTariffStatus() != ErmbTariffStatus.ACTIVE)
			throw new UserErrorException(messageBuilder.buildNotActiveTariffMessage());
		if (currentTariff != null)
		{
			if (StringHelper.equalsIgnoreCaseStrip(name, currentTariff.getName()))
				throw new UserErrorException(messageBuilder.buildAlreadyConnectedTariffMessage());
		}
	}

	private void applyNewTariff(ErmbTariff newTariff)
	{
		try
		{
			ErmbProfileImpl profile = getErmbProfile();
			ErmbProfileEvent profileEvent = new ErmbProfileEvent(ErmbHelper.copyProfile(profile));
			ErmbHelper.updateErmbTariff(profile, newTariff);
			profileEvent.setNewProfile(profile);

			ErmbUpdateListener updateListener = ErmbUpdateListener.getListener();
			updateListener.beforeProfileUpdate(profileEvent);
			profileService.addOrUpdate(profile);
			updateListener.afterProfileUpdate(profileEvent);

			sendMessage(messageBuilder.buildChangeTariffSuccessMessage(newTariff));
		}
		catch (BusinessException e)
		{
			throw new InternalErrorException(e);
		}
	}

	private void checkCost(ErmbTariff newTariff)
	{
		Collection<BankrollProductLink> bankrollProductLinks = (Collection<BankrollProductLink>) getPersonBankrollManager().getCards();
		for (BankrollProductLink link : bankrollProductLinks)
		{
			try
			{
				Card card = ((CardLink)link).getCard();
				if (notBlockedCardFilter.accept(card) && notExpiredCardFilter.accept(card) && CardsUtil.hasAvailableLimit(card, newTariff.getConnectionCost()))
				{
					return;
				}
			}
			catch (BusinessException e)
			{
				throw new UserErrorException(messageBuilder.buildNotEnoughMoneyMessage(newTariff.getName()));
			}
		}
		throw new UserErrorException(messageBuilder.buildNotEnoughMoneyMessage(newTariff.getName()));
	}


	@Override
	public String toString()
	{
		return String.format("ТАРИФ[->%s]", name);
	}

	public String getName()
	{
		return name;
	}

	public String getCommandName()
	{
		return "CHANGE_TARIFF";
	}
}
