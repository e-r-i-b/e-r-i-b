package com.rssl.phizic.business.ermb.sms.command;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.documents.ResourceType;
import com.rssl.phizic.business.ermb.ErmbProfileBusinessService;
import com.rssl.phizic.business.ermb.ErmbProfileImpl;
import com.rssl.phizic.business.ermb.sms.messaging.MessageBuilder;
import com.rssl.phizic.business.resources.external.CardLink;
import com.rssl.phizic.business.resources.external.ExternalResourceLink;
import com.rssl.phizic.common.types.TextMessage;
import com.rssl.phizic.common.types.exceptions.InternalErrorException;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.gate.config.ESBEribConfig;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.utils.ExternalSystemHelper;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.LogModule;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.messaging.PersonSmsMessanger;
import com.rssl.phizic.task.PersonTaskBase;
import com.rssl.phizicgate.manager.ext.sbrf.ESBHelper;
import com.rssl.phizicgate.manager.routing.Adapter;
import com.rssl.phizicgate.manager.routing.AdapterService;

import java.util.Collection;

/**
 * @author Erkin
 * @ created 13.03.2013
 * @ $Author$
 * @ $Revision$
 */

/**
 * Базовый класс СМС-команды
 */
abstract class CommandBase extends PersonTaskBase implements Command
{
	protected static final Log log = PhizICLogFactory.getLog(LogModule.Core);

	protected static final MessageBuilder messageBuilder = new MessageBuilder();

	protected static final ErmbProfileBusinessService profileService = new ErmbProfileBusinessService();

	private static final AdapterService adapterService = new AdapterService();

	private transient String phoneTransmitter;

	private static final int autoSmsAliasLengthDefaultValue = 4;

	///////////////////////////////////////////////////////////////////////////

	public final void execute()
	{
		if (needResetIncompletePayment())
			resetIncompletePayment();
		doExecute();
	}

	protected ErmbProfileImpl getErmbProfile()
	{
		return (ErmbProfileImpl) getPerson().getErmbProfile();
	}

	protected void sendMessage(TextMessage message)
	{
		PersonSmsMessanger messanger = getPersonSmsMessanger();
		messanger.sendSms(message);
	}

	protected abstract void doExecute();

	protected boolean needResetIncompletePayment()
	{
		//Прервать оформление незаконченного платежа в адрес биллингового поставщика можно отправив любую команду, кроме запрошенной банком.
		return true;
	}

	private void resetIncompletePayment()
	{
		ErmbProfileImpl profile = getErmbProfile();
		if (profile.getIncompleteSmsPayment() == null)
			return;

		try
		{
			profileService.updateIncompletePayment(profile.getId(), null);
			profile.setIncompleteSmsPayment(null);
		}
		catch (BusinessException e)
		{
			log.error("Не удалось сбросить значение недовведенного платежа смс канала", e);
		}
	}

	public void setPhoneTransmitter(String phoneTransmitter)
	{
		this.phoneTransmitter = phoneTransmitter;
	}

	public String getPhoneTransmitter()
	{
		return phoneTransmitter;
	}

	protected int getCardSmsAutoAliasLength(Collection<CardLink> cardLinks)
	{
		if (!cardLinks.isEmpty())
		{
			CardLink cardLink = cardLinks.iterator().next();
			return cardLink.getAutoSmsAlias().length();
		}
		return autoSmsAliasLengthDefaultValue;
	}

	protected int getCardSmsAutoAliasLength()
	{
		Collection<CardLink> cardLinks = (Collection<CardLink>) getPersonBankrollManager().getCards();
		if (!cardLinks.isEmpty())
		{
			CardLink cardLink = cardLinks.iterator().next();
			return cardLink.getAutoSmsAlias().length();
		}
		return autoSmsAliasLengthDefaultValue;
	}

	//проверка на активность внешней системы для запросов по продукту
	//нельзя использовать в платежных командах, т.к. платеж не попадет в отложенную очередь
	protected void checkAndThrowExternalSystem(ExternalResourceLink link)
	{
		try
		{
			//Проверяем  есть ли перерыв на  всю шину.
			Adapter esbAdapter = adapterService.getAdapterByUUID(ExternalSystemHelper.getESBSystemCode());
			ExternalSystemHelper.check(esbAdapter);

			String systemId;
			if (link.getResourceType() == ResourceType.CARD)
				systemId = ConfigFactory.getConfig(ESBEribConfig.class).getEsbERIBCardSystemId99Way();
			else
				systemId = ESBHelper.parseSystemId(link.getExternalId());

			ExternalSystemHelper.check(systemId);
		}
		catch (GateException e)
		{
			throw new InternalErrorException(e);
		}
	}
}
