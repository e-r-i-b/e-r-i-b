package com.rssl.phizic.business.ermb.profile;

import com.rssl.phizic.business.ermb.ErmbProfileImpl;
import com.rssl.phizic.business.ermb.profile.comparators.sms.ErmbPersonSmsComparator;
import com.rssl.phizic.business.ermb.profile.comparators.sms.ErmbProfileSmsComparator;
import com.rssl.phizic.business.ermb.profile.comparators.sms.ErmbResourcesShowInSmsComparator;
import com.rssl.phizic.business.resources.external.ErmbProductLink;
import com.rssl.phizic.business.sms.ErmbBusinessTextMessageBuilder;
import com.rssl.phizic.common.types.TextMessage;
import com.rssl.phizic.common.types.client.CreationType;
import com.rssl.phizic.common.types.exceptions.IKFLMessagingException;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.messaging.MessagingSingleton;
import com.rssl.phizic.messaging.mail.SmsTransportService;
import com.rssl.phizic.person.Person;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author Erkin
 * @ created 07.08.2014
 * @ $Author$
 * @ $Revision$
 */
public class ErmbProfileChangesNotificator
{
	private final Log log = PhizICLogFactory.getLog(Constants.LOG_MODULE_CORE);

	private final SmsTransportService smsService = MessagingSingleton.getInstance().getErmbSmsTransportService();

	private final ErmbBusinessTextMessageBuilder messageBuilder = new ErmbBusinessTextMessageBuilder();

	///////////////////////////////////////////////////////////////////////////

	/**
	 * Отправка смс при изменении профиля ермб клиента
	 * @param oldProfile новое состояние профиля
	 * @param newProfile старое состояние профиля
	 */
	public void sendOnProfileUpdate(ErmbProfileImpl oldProfile, ErmbProfileImpl newProfile)
	{
		boolean oldProfileActive = oldProfile != null && oldProfile.isServiceStatus() && !oldProfile.isClientBlocked();
		boolean newProfileActive = newProfile != null && newProfile.isServiceStatus() && !newProfile.isClientBlocked();

		boolean profileCreated = (oldProfile == null || !oldProfile.isServiceStatus()) && newProfileActive;
		boolean profileChanged = oldProfile != null && oldProfile.getId() != null && newProfileActive;
		boolean profileDisconnected = oldProfileActive && !newProfileActive;
		boolean isUdbo = newProfile != null && newProfile.getPerson().getCreationType() == CreationType.UDBO;

		if (profileCreated)
		{
			sendSmsWelcome(newProfile.getMainPhoneNumber(), isUdbo);
			sendFastServiceChanged(newProfile.getMainPhoneNumber(), newProfile.getFastServiceAvailable());
		}
		else if (profileChanged)
		{
			String oldMainPhoneNumber = oldProfile.getMainPhoneNumber();
			String newMainPhoneNumber = newProfile.getMainPhoneNumber();
			if (!newMainPhoneNumber.equals(oldMainPhoneNumber))
				sendSmsChangedMainPhone(newMainPhoneNumber, oldMainPhoneNumber);

			ErmbProfileSmsComparator comparator = new ErmbProfileSmsComparator();
			Set<String> changedFields = comparator.compare(oldProfile, newProfile);
			if (CollectionUtils.isNotEmpty(changedFields))
				sendSmsChangedData(newMainPhoneNumber, changedFields);

			if (oldProfile.getFastServiceAvailable() != newProfile.getFastServiceAvailable())
				sendFastServiceChanged(newProfile.getMainPhoneNumber(), newProfile.getFastServiceAvailable());

			boolean oldClientBlocked = oldProfile.isClientBlocked();
			boolean newClientBlocked = newProfile.isClientBlocked();
			if (oldClientBlocked && !newClientBlocked)
				sendSmsWelcome(newProfile.getMainPhoneNumber(), isUdbo);
		}
		else if (profileDisconnected)
		{
			String oldMainPhoneNumber = oldProfile.getMainPhoneNumber();
			String newMainPhoneNumber = newProfile.getMainPhoneNumber();
			if (!oldMainPhoneNumber.equals(newMainPhoneNumber))
				sendSmsChangedMainPhone(newMainPhoneNumber, oldMainPhoneNumber);
		}
	}

	/**
	 * Отправка смс при изменении видимости продуктов клиента
	 * @param profile профиль
	 * @param oldResources старый набор продуктов
	 * @param newResources новый набор продуктов
	 */
	public void sendOnResourcesUpdate(ErmbProfileImpl profile, Map<Class, List<ErmbProductLink>> oldResources, Map<Class, List<ErmbProductLink>> newResources)
	{
		String phoneNumber = profile.getMainPhoneNumber();
		boolean isProfileActive = profile.isServiceStatus() && !profile.isClientBlocked();
		if (isProfileActive && StringUtils.isNotEmpty(phoneNumber))
		{
			ErmbResourcesShowInSmsComparator smsComparator = new ErmbResourcesShowInSmsComparator();
			Set<String> changedFields = smsComparator.compare(oldResources, newResources);
			if (CollectionUtils.isNotEmpty(changedFields))
			{
				sendSmsChangedData(phoneNumber, changedFields);
			}
		}
	}

	/**
	 * Отправка смс при изменении анкеты клиента
	 * @param profile профиль
	 * @param oldPerson старая анкета клиента
	 * @param newPerson новая анкета клиента
	 */
	public void sendOnPersonUpdate(ErmbProfileImpl profile, Person oldPerson, Person newPerson)
	{
		String phoneNumber = profile.getMainPhoneNumber();
		boolean isProfileActive = profile.isServiceStatus() && !profile.isClientBlocked();
		if (isProfileActive && StringUtils.isNotEmpty(phoneNumber))
		{
			ErmbPersonSmsComparator smsComparator = new ErmbPersonSmsComparator();
			Set<String> changedFields = smsComparator.compare(oldPerson, newPerson);
			if (CollectionUtils.isNotEmpty(changedFields))
			{
				ErmbProfileChangesNotificator notificator = new ErmbProfileChangesNotificator();
				notificator.sendSmsChangedData(phoneNumber, changedFields);
			}
		}
	}

	private void sendSmsWelcome(String phoneNumber, boolean isUdbo)
	{
		try
		{
			TextMessage welcomeMessage = messageBuilder.buildSmsWelcome(isUdbo);
			smsService.sendSms(phoneNumber, welcomeMessage.getText(), welcomeMessage.getTextToLog(), welcomeMessage.getPriority());
		}
		catch (IKFLMessagingException e)
		{
			log.error("Не удалось отправить смс о подключении Мобильного Банка", e);
		}
	}

	private void sendSmsChangedMainPhone(String newMainPhoneNumber, String oldMainPhoneNumber)
	{
		try
		{
			if (StringUtils.isNotEmpty(newMainPhoneNumber))
			{
				TextMessage newMessage = messageBuilder.buildSmsChangeMainPhone(newMainPhoneNumber, true);
				smsService.sendSms(newMainPhoneNumber, newMessage.getText(), newMessage.getTextToLog(), newMessage.getPriority());
			}

			if (StringUtils.isNotEmpty(oldMainPhoneNumber))
			{
				TextMessage oldMessage = messageBuilder.buildSmsChangeMainPhone(oldMainPhoneNumber, false);
				smsService.sendSms(oldMainPhoneNumber, oldMessage.getText(), oldMessage.getTextToLog(), oldMessage.getPriority());
			}
		}
		catch (IKFLMessagingException e)
		{
			log.error("Не удалось отправить смс об изменении ермб профиля", e);
		}
	}

	private void sendSmsChangedData(String phoneNumber, Collection<String> changedFields)
	{
		try
		{
			TextMessage message = messageBuilder.buildSmsChangedData(changedFields);
			smsService.sendSms(phoneNumber, message.getText(), message.getTextToLog(), message.getPriority());
		}
		catch (IKFLMessagingException e)
		{
			log.error("Не удалось отправить смс об изменении ермб профиля", e);
		}
	}

	private void sendFastServiceChanged(String phoneNumber, boolean fastServiceAvailable)
	{
		try
		{
			TextMessage servicesMessage = messageBuilder.buildSmsFastServices(fastServiceAvailable);
			smsService.sendSms(phoneNumber, servicesMessage.getText(), servicesMessage.getTextToLog(), servicesMessage.getPriority());
		}
		catch (IKFLMessagingException e)
		{
			log.error("Не удалось отправить смс об изменении ермб профиля", e);
		}
	}
}
