package com.rssl.phizic.business.ermb.disconnector;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.ermb.*;
import com.rssl.phizic.business.ermb.migration.mbk.ERMBPhoneService;
import com.rssl.phizic.business.ermb.profile.ErmbProfileListener;
import com.rssl.phizic.business.ermb.profile.ErmbUpdateListener;
import com.rssl.phizic.business.ermb.profile.events.ErmbProfileEvent;
import com.rssl.phizic.business.ermb.sms.messaging.SmsSenderUtils;
import com.rssl.phizic.business.sms.ErmbBusinessTextMessageBuilder;
import com.rssl.phizic.common.types.TextMessage;
import com.rssl.phizic.common.types.exceptions.IKFLMessagingException;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizic.gate.mobilebank.PhoneDisconnectionReason;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.LogModule;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.person.ErmbProfile;
import org.apache.commons.collections.CollectionUtils;
import org.hibernate.Session;

import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * @author Gulov
 * @ created 10.09.13
 * @ $Author$
 * @ $Revision$
 */

/**
 * Отключает телефон от клиента.
 * Блокирует профиль, если отключенный телефон один.
 * Если отключаемый номер - основной, то в качестве основного выбирается любой из оставшихся.
 */
public class DisconnectorPhone implements ErmbPhoneDisconnector
{
	private static final ErmbClientPhoneService phoneService = new ErmbClientPhoneService();
	private static final ErmbProfileBusinessService profileService = new ErmbProfileBusinessService();
	private ErmbProfileListener updateListener = ErmbUpdateListener.getListener();
	private final ERMBPhoneService ermbPhoneService = new ERMBPhoneService();
	private final ErmbBusinessTextMessageBuilder messageBuilder = new ErmbBusinessTextMessageBuilder();
	private final OSSConfig config = ConfigFactory.getConfig(OSSConfig.class);

	private final Log log = PhizICLogFactory.getLog(LogModule.Core);

	/**
	 * Собственно действие
	 * @param phoneNumber номер телефона
	 * @param reason причина отключения
	 */
	public void disconnect(String phoneNumber, PhoneDisconnectionReason reason) throws BusinessException, BusinessLogicException
	{
		ErmbClientPhone clientPhone = phoneService.findPhoneByNumber(phoneNumber);
		disconnect(clientPhone, reason);
	}

	private void disconnect(ErmbClientPhone clientPhone, PhoneDisconnectionReason reason) throws BusinessException, BusinessLogicException
	{
		if (clientPhone == null)
			return;
		ErmbProfileImpl profile = clientPhone.getProfile();
		ErmbProfileEvent event = new ErmbProfileEvent(ErmbHelper.copyProfile(profile));
		if (!profile.isServiceStatus() || profile.isClientBlocked() || profile.isPaymentBlocked())
			return;

		if (profile.getPhones().size() == 1) // один телефон, блокируем
		{
			disconnectSinglePhone(profile, clientPhone, event);
		}
		else if (clientPhone.isMain()) // основной телефон
		{
			disconnectMainPhone(profile, clientPhone, event);
		}
		else
		{
			disconnectAnyPhone(profile, clientPhone, event);
		}

		if (config.getNotifyingReasonCodes().contains(reason.code))
			notifyAllPhones(profile, clientPhone.toString(), reason);
	}

	private void disconnectSinglePhone(final ErmbProfileImpl profile, final ErmbClientPhone phone, ErmbProfileEvent event) throws BusinessException, BusinessLogicException
	{
		ErmbProfileImpl oldProfile = ErmbHelper.copyProfile(profile);

		removePhone(profile, phone);
		profile.setServiceStatus(false);
		// Не выставляем клиентскую блокировку согласно
		// BUG075813: ЕРМБ : АРМ Сотрудника : Блокировка услуги при удалении номера
		// ErmbHelper.blockProfile(profile, ErmbHelper.NO_PHONE_BLOCK_DESCRIPTION);
		event.setNewProfile(profile);
		updateListener.beforeProfileUpdate(event);
		ErmbHelper.saveErmbDataWithCsaUpdate(profile, oldProfile, new HibernateAction<Void>()
		{
			public Void run(Session session) throws Exception
			{
				phoneService.remove(phone);
				profileService.addOrUpdate(profile);
				ermbPhoneService.saveOrUpdateERMBPhones(Collections.<String>emptyList(), Collections.singletonList(phone.getNumber()));
				return null;
			}
		});
		updateListener.afterProfileUpdate(event);
	}

	private void disconnectMainPhone(final ErmbProfileImpl profile, final ErmbClientPhone phone, ErmbProfileEvent event) throws BusinessException, BusinessLogicException
	{
		ErmbProfileImpl oldProfile = ErmbHelper.copyProfile(profile);

		removePhone(profile, phone);
		Set<ErmbClientPhone> phones = profile.getPhones();
		Iterator<ErmbClientPhone> iterator = phones.iterator();
		final ErmbClientPhone mainPhone = iterator.next();
		mainPhone.setMain(true);
		event.setNewProfile(profile);
		updateListener.beforeProfileUpdate(event);
		ErmbHelper.saveErmbDataWithCsaUpdate(profile, oldProfile, new HibernateAction<Void>()
		{
			public Void run(Session session) throws Exception
			{
				phoneService.remove(phone);
				phoneService.update(mainPhone);
				ermbPhoneService.saveOrUpdateERMBPhones(Collections.<String>emptyList(), Collections.singletonList(phone.getNumber()));
				return null;
			}
		});
		updateListener.afterProfileUpdate(event);
	}

	private void disconnectAnyPhone(ErmbProfileImpl profile, final ErmbClientPhone phone, ErmbProfileEvent event) throws BusinessException, BusinessLogicException
	{
		ErmbProfileImpl oldProfile = ErmbHelper.copyProfile(profile);

		removePhone(profile, phone);
		event.setNewProfile(profile);
		updateListener.beforeProfileUpdate(event);
		ErmbHelper.saveErmbDataWithCsaUpdate(profile, oldProfile, new HibernateAction<Void>()
		{
			public Void run(Session session) throws Exception
			{
				phoneService.remove(phone);
				ermbPhoneService.saveOrUpdateERMBPhones(Collections.<String>emptyList(), Collections.singletonList(phone.getNumber()));
				return null;
			}
		});
		updateListener.afterProfileUpdate(event);
	}

	private void notifyAllPhones(ErmbProfile profile, String disconnectedNumber, PhoneDisconnectionReason reason) throws BusinessException
	{
		profile = profileService.findByPersonId(profile.getId());
		Set<String> numbers = profile.getPhoneNumbers();
		if (CollectionUtils.isEmpty(numbers))
			return;

		try
		{
			TextMessage message = messageBuilder.buildDisconnectPhoneBroadcastMessage(disconnectedNumber, profile.getMainPhoneNumber(), reason);
			for (String number : numbers)
				SmsSenderUtils.notifyClientMessage(number, message);
		}
		catch (IKFLMessagingException e)
		{
			log.error("Не удалось сделать рассылку об отключении услуги ЕРМБ для телефона", e);
		}
	}

	private void removePhone(ErmbProfileImpl profile, ErmbClientPhone removed)
	{
		Set<ErmbClientPhone> phones = new HashSet<ErmbClientPhone>();
		for (ErmbClientPhone phone : profile.getPhones())
			if (!phone.getNumber().equals(removed.getNumber()))
				phones.add(phone);
		profile.setPhones(phones);
	}
}
