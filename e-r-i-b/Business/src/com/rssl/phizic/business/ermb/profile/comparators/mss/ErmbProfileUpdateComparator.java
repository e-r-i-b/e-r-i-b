package com.rssl.phizic.business.ermb.profile.comparators.mss;

import com.rssl.phizic.business.ermb.ErmbProfileImpl;
import com.rssl.phizic.common.types.Day;
import com.rssl.phizic.utils.DateHelper;
import com.rssl.phizic.utils.StringHelper;

import java.util.HashSet;
import java.util.Set;

/**
 * @author EgorovaA
 * @ created 08.07.2013
 * @ $Author$
 * @ $Revision$
 *
 * Компаратор, проверяющий, изменились ли важные для СОС поля ЕРМБ-профиля
 */
public class ErmbProfileUpdateComparator
{
	public int compare(ErmbProfileImpl oldProfile, ErmbProfileImpl newProfile)
	{
		if (oldProfile == null ^ newProfile == null)
			return -1;

		// id клиента
		if (!oldProfile.getPerson().getId().equals(newProfile.getPerson().getId()))
			return -1;
		// состояние услуги
		if (oldProfile.isServiceStatus() != newProfile.isServiceStatus())
			return -1;
		// Клиентская блокировака
		if (oldProfile.isClientBlocked() != newProfile.isClientBlocked())
			return -1;
		//Блокировака по не оплате
		if (oldProfile.isPaymentBlocked() != newProfile.isPaymentBlocked())
			return -1;
		// дата окончания предоставления услуги
		if (DateHelper.nullSafeCompare(oldProfile.getEndServiceDate(), newProfile.getEndServiceDate()) != 0)
			return -1;
		// главный телефон
		if (!StringHelper.equals(oldProfile.getMainPhoneNumber(), newProfile.getMainPhoneNumber()))
			return -1;
		// список телефонов
		if (!oldProfile.getPhoneNumbers().equals(newProfile.getPhoneNumbers()))
			return -1;
		// дни оповещений
		Set<Day> oldDays = oldProfile.getDaysOfWeek() != null ? oldProfile.getDaysOfWeek().getDays() : new HashSet<Day>();
		Set<Day> newDays = newProfile.getDaysOfWeek() != null ? newProfile.getDaysOfWeek().getDays() : new HashSet<Day>();
		if (!oldDays.equals(newDays))
			return -1;
		// время начала периода оповещений
		if (!oldProfile.getNotificationStartTime().equals(newProfile.getNotificationStartTime()))
			return -1;
		// время окончания периода оповещений
		if (!oldProfile.getNotificationEndTime().equals(newProfile.getNotificationEndTime()))
			return -1;
		// часовой пояс
		if (oldProfile.getTimeZone() != newProfile.getTimeZone())
			return -1;
		// признак отправки уведомлений по новым и не отраженным продуктам
		if (oldProfile.getNewProductNotification() != newProfile.getNewProductNotification())
			return -1;
		// признак включения рекламных рассылок
		if (oldProfile.isSuppressAdv() != newProfile.isSuppressAdv())
			return -1;
		// признак включения уведмлений по процентам по счетам
		if (oldProfile.getDepositsTransfer() != newProfile.getDepositsTransfer())
			return -1;
		//анкета клиента
		ErmbPersonUpdateComparator personComparator = new ErmbPersonUpdateComparator();
		if (personComparator.changed(oldProfile.getPerson(), newProfile.getPerson()))
			return -1;

		return 0;
	}
}
