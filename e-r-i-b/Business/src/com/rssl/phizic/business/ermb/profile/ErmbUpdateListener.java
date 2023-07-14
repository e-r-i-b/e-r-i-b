package com.rssl.phizic.business.ermb.profile;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.ermb.ErmbClientTariffChangeStatisticService;
import com.rssl.phizic.business.ermb.ErmbOfficialInfoNotificationSender;
import com.rssl.phizic.business.ermb.ErmbProfileBusinessService;
import com.rssl.phizic.business.ermb.ErmbProfileImpl;
import com.rssl.phizic.business.ermb.profile.comparators.*;
import com.rssl.phizic.business.ermb.profile.comparators.mss.ErmbPersonUpdateComparator;
import com.rssl.phizic.business.ermb.profile.comparators.mss.ErmbProfileUpdateComparator;
import com.rssl.phizic.business.ermb.profile.comparators.mss.ErmbResoursesParamsComparator;
import com.rssl.phizic.business.ermb.profile.comparators.mss.ErmbResoursesSetComparator;
import com.rssl.phizic.business.ermb.profile.events.ErmbPersonEvent;
import com.rssl.phizic.business.ermb.profile.events.ErmbProfileEvent;
import com.rssl.phizic.business.ermb.profile.events.ErmbResourseParamsEvent;
import com.rssl.phizic.business.ermb.profile.events.ErmbResourseSetEvent;
import com.rssl.phizic.business.resources.external.ErmbProductLink;
import com.rssl.phizic.person.Person;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author EgorovaA
 * @ created 08.07.2013
 * @ $Author$
 * @ $Revision$
 *
 * Наблюдатель, увеличивающий при необходимости версию профиля, и вызывающий отправку уведомления об изменении профиля 
 */
public class ErmbUpdateListener implements ErmbProfileListener, ErmbResourseListener, ErmbResourseSetListener, ErmbPersonListener
{
	private static final ErmbUpdateListener listener = new ErmbUpdateListener();
	private static final Long  START_VERSION = 1L;
	private static final ErmbProfileBusinessService profileService = new ErmbProfileBusinessService();
	private final ErmbClientTariffChangeStatisticService tariffChangeStatisticService = new ErmbClientTariffChangeStatisticService();

	private ErmbUpdateListener()
	{
	}

	public static ErmbUpdateListener getListener()
	{
		return listener;
	}

	/**
	 * Метод, вызываемый перед сохранением ЕРМБ профиля клиента. Если профиль был изменен, необходимо поднять
	 * версию профиля. Проверяем не все поля, а только те, что важны для ЕРМБ
	 */
	public void beforeProfileUpdate(ErmbProfileEvent event)
	{
		ErmbProfileImpl oldProfile = event.getOldProfile();
		ErmbProfileImpl newProfile = event.getNewProfile();
		ErmbProfileUpdateComparator comparator = new ErmbProfileUpdateComparator();
		if (comparator.compare(oldProfile, newProfile) != 0)
		{
			Long version = newProfile.getProfileVersion();
			newProfile.setProfileVersion(version != null ? ++version : START_VERSION);
		}
	}

	/**
	 * Метод, вызываемый после сохранения профиля ЕРМБ. Если версия профиля не совпадает с подтвержденной,
     * и если статус профиля не NOT_CONNECTED (это нужно для корректно работы шлюза АС ФСБ)
	 * нужно отправить уведомление ЕРМБ об изменении профиля
	 * Отправляет смс об изменении данных клиента
	 * Сохраняет в историческую таблицу данные об изменении тарифа
	 * @param event - измененные данные
	 */
	public void afterProfileUpdate(ErmbProfileEvent event) throws BusinessException
	{
		//оповещение сос
		sendNotification(event.getNewProfile());

		//оповещение по смс
		if (event.isSendSms())
		{
			ErmbProfileChangesNotificator notificator = new ErmbProfileChangesNotificator();
			notificator.sendOnProfileUpdate(event.getOldProfile(), event.getNewProfile());
		}

		ErmbTariffComparator ermbTariffComparator = new ErmbTariffComparator();
		if (event.getOldProfile() == null || ermbTariffComparator.compare(event.getOldProfile(), event.getNewProfile()) != 0)
			tariffChangeStatisticService.addTariffChangedRecord(event.getNewProfile());
	}

	/**
	 * Метод, вызывающий отправку уведомления ЕРМБ
	 * @param profile - измененный профиль
	 */
	private void sendNotification(ErmbProfileImpl profile)
	{
		List<ErmbProfileImpl> profileList = new ArrayList<ErmbProfileImpl>();
		profileList.add(profile);
		ErmbOfficialInfoNotificationSender sender = new ErmbOfficialInfoNotificationSender();
		sender.sendNotification(profileList);
	}

	/**
	 * Метод, вызываемый при изменении настроек видимости продуктов в СМС-канале
	 * Отправляет смс об изменении данных клиента
	 * @param event - данные для сравнения
	 * @throws BusinessException
	 */
	public void onResoursesUpdate(ErmbResourseParamsEvent event) throws BusinessException
	{
		Map<Class,List<ErmbProductLink>> oldResources = event.getOldResourses();
		Map<Class, List<ErmbProductLink>> newResources = event.getNewResourses();

		ErmbProfileImpl profile = event.getProfile();
		if (profile == null)
			return;

		//оповещение сос
		ErmbResoursesParamsComparator comparator = new ErmbResoursesParamsComparator();
		if (comparator.compare(oldResources, newResources) != 0)
		{
			increaseProfileVersion(profile);
		}

		//оповещение по смс
		ErmbProfileChangesNotificator notificator = new ErmbProfileChangesNotificator();
		notificator.sendOnResourcesUpdate(profile, oldResources, newResources);
	}

	/**
	 * Метод, вызываемый при перезагрузке продуктов (как правило, при входе клиента)
	 * @param event - данные для сравнения
	 * @throws BusinessException
	 */
	public void onResoursesReload(ErmbResourseSetEvent event) throws BusinessException
	{
		ErmbResoursesSetComparator comparator = new ErmbResoursesSetComparator();
		if (comparator.compare(event.getOldResourses(), event.getNewResourses()) != 0)
		{
			ErmbProfileImpl profile = event.getProfile();
			if (profile != null)
				increaseProfileVersion(profile);
		}
	}

	/**
	 * Увеличить версию ЕРМБ-профиля, сохранить его и оправить уведомление ЕРМБ об изменении профиля
	 * @param profile - изменяемый профиль
	 * @throws BusinessException
	 */
	private void increaseProfileVersion(ErmbProfileImpl profile) throws BusinessException
	{
		// Увеличиваем версию профиля
		Long version = profile.getProfileVersion();
		profile.setProfileVersion(version != null ? ++version : START_VERSION);

		// Сохраняем профиль
		profileService.addOrUpdate(profile);

		// Отправляем уведомление
		if (!profile.getProfileVersion().equals(profile.getConfirmProfileVersion()))
			sendNotification(profile);
	}

	/**
	 * Метод, вызываемый перед сохранением клиента. Если данные клиента был изменены, необходимо поднять
	 * версию профиля. Проверяем не все поля, а только те, что важны для ЕРМБ
	 * Отправляет смс об изменении данных клиента
	 * @param event - данные для сравнения
	 * @throws BusinessException
	 */
	public void onPersonUpdate(ErmbPersonEvent event) throws BusinessException
	{
		Person oldPerson = event.getOldPerson();
		Person newPerson = event.getNewPerson();
		ErmbProfileImpl profile = event.getProfile();
		if (profile == null)
			return;

		//оповещение сос
		ErmbPersonUpdateComparator comparator = new ErmbPersonUpdateComparator();
		if (comparator.changed(oldPerson, newPerson))
		{
			increaseProfileVersion(profile);
		}

		//оповещение по смс
		ErmbProfileChangesNotificator notificator = new ErmbProfileChangesNotificator();
		notificator.sendOnPersonUpdate(profile, oldPerson, newPerson);
	}
}
