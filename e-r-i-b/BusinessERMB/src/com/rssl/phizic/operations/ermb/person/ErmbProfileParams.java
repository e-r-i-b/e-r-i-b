package com.rssl.phizic.operations.ermb.person;

import com.rssl.phizic.common.types.DaysOfWeek;

import java.io.Serializable;
import java.sql.Time;
import java.util.HashSet;
import java.util.Set;

/**
 * Для проверки измененных полей при создании/редактировании профиля ЕРМБ.
 @author: EgorovaA
 @ created: 13.12.2012
 @ $Author$
 @ $Revision$
 */
public class ErmbProfileParams implements Serializable
{
	/**
	 * Номера телефонов, привязанных к ЕРМБ-профилю
	 */
	private Set<String> phones = new HashSet<String>();
	/**
	 *  Признак быстрых сервисов
	 */
	private boolean fastServiceAvailable;
	/**
	 * Признак отправки уведомлений по операциям причисления процентов по счетам
	 */
	private boolean depositsTransfer;
	/**
	 * Отсылать уведомления по новым продуктам
	 */
	private boolean newProductNtf;
	/**
	 * Приоритетный продукт для списания абонентской платы(кард линк)
	 */
	private String mainCardId;
	/**
	 * Выбранный тариф
	 */
	private int selectedTarifId;
	/**
	 * Дни отправки уведомлений
	 */
	private DaysOfWeek ntfDays;
	/**
	 * Время начала периода отправки уведомлений
	 */
	private Time notificationStartTime;
	/**
	 * Время окончания периода отправки уведомлений
	 */
	private Time notificationEndTime;
	/**
	 * Часовой пояс
	 */
	private long timeZone;
	/**
	 * Номер основного телефона
	 */
	private String mainPhoneNumber;

	//////////////////////////////////////////////////////////////////////////////////////////////////////////

	public Set<String> getPhones()
	{
		return phones;
	}

	public void setPhones(Set<String> phones)
	{
		this.phones = phones;
	}

	public boolean getFastServiceAvailable()
	{
		return fastServiceAvailable;
	}

	public void setFastServiceAvailable(boolean fastServiceAvailable)
	{
		this.fastServiceAvailable = fastServiceAvailable;
	}

	public boolean getDepositsTransfer()
	{
		return depositsTransfer;
	}

	public void setDepositsTransfer(boolean depositsTransfer)
	{
		this.depositsTransfer = depositsTransfer;
	}

	public String getMainCardId()
	{
		return mainCardId;
	}

	public void setMainCardId(String mainCardId)
	{
		this.mainCardId = mainCardId;
	}

	public int getSelectedTarifId()
	{
		return selectedTarifId;
	}

	public void setSelectedTarifId(int selectedTarifId)
	{
		this.selectedTarifId = selectedTarifId;
	}

	public boolean getNewProductNtf()
	{
		return newProductNtf;
	}

	public void setNewProductNtf(boolean newProductNtf)
	{
		this.newProductNtf = newProductNtf;
	}

	public DaysOfWeek getNtfDays()
	{
		return ntfDays;
	}

	public void setNtfDays(DaysOfWeek ntfDays)
	{
		this.ntfDays = ntfDays;
	}

	public Time getNotificationStartTime()
	{
		return notificationStartTime;
	}

	public void setNotificationStartTime(Time notificationStartTime)
	{
		this.notificationStartTime = notificationStartTime;
	}

	public Time getNotificationEndTime()
	{
		return notificationEndTime;
	}

	public void setNotificationEndTime(Time notificationEndTime)
	{
		this.notificationEndTime = notificationEndTime;
	}

	public long getTimeZone()
	{
		return timeZone;
	}

	public void setTimeZone(long timeZone)
	{
		this.timeZone = timeZone;
	}

	public String getMainPhoneNumber()
	{
		return mainPhoneNumber;
	}

	public void setMainPhoneNumber(String mainPhoneNumber)
	{
		this.mainPhoneNumber = mainPhoneNumber;
	}

}
