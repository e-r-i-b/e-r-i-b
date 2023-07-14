package com.rssl.phizic.business.ermb;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.common.types.Period;
import com.rssl.phizic.gate.mobilebank.ClientTariffInfo;
import com.rssl.phizic.utils.DateHelper;

import java.util.Calendar;

/**
 * Обновление информации о дате списания платы за услугу Мобильный Банк
 * @author Puzikov
 * @ created 02.06.14
 * @ $Author$
 * @ $Revision$
 */

public class ErmbChargeDateUpdater
{
	/**
	 * Инициализировать информацию о первом списании абонентской платы
	 * (подключение профиля или смена тарифа)
	 * @param profile инициализируемый профиль
	 */
	public void initChargeDate(ErmbProfileImpl profile) throws BusinessException
	{
		Calendar currentDate = DateHelper.getCurrentDate();
		profile.setChargeNextDate(currentDate);
		profile.setChargeDayOfMonth(currentDate.get(Calendar.DAY_OF_MONTH));
	}

	/**
	 * Обновить информацию о списании абонентской платы
	 * Платеж сдвигается на следующую дату платежа
	 * Обновление происходит при обработке сообщения WAY о списании или апдейте бесплатного тарифа
	 * @param profile обновляемый профиль
	 * @return период за который произошло списание
	 */
	public Period shiftChargeDate(ErmbProfileImpl profile) throws BusinessException
	{
		return shiftChargeDate(profile, profile.getChargeNextDate(), profile.getChargeDayOfMonth());
	}

	/**
	 * Установить состояние по списанию абонентской платы согласно МБК
	 * Если дата не вернулась или вернулась уже прошедшая, то устанавливается сегодняшним числом
	 * @param profile обновляемый профиль
	 * @param mbkInfo информация из МБК по коммиту миграции
	 */
	public void setChargeDateFromMbk(ErmbProfileImpl profile, ClientTariffInfo mbkInfo) throws BusinessException
	{
		Calendar mbkNextPaidPeriod = mbkInfo.getNextPaidPeriod();
		int chargeDayOfMonth = mbkInfo.getPayDate();

		if (mbkNextPaidPeriod == null)
		{
			initChargeDate(profile);
		}
		else
		{
			Calendar chargeNextDate = (Calendar) mbkNextPaidPeriod.clone();
			DateHelper.setDayOfMonth(chargeNextDate, chargeDayOfMonth);

			if (chargeNextDate.before(DateHelper.getCurrentDate()))
			{
				initChargeDate(profile);
			}
			else
			{
				profile.setChargeNextDate(chargeNextDate);
				profile.setChargeDayOfMonth(chargeDayOfMonth);
			}
		}
	}

	/**
	 * Обновить информацию о списании абонентской платы при разблокировке профиля по неоплате
	 * Дата платежа устанавливается текущим днем (днем разблокировки)
	 * @param profile обновляемый профиль
	 * @return период за который произошло списание
	 */
	public Period resetChargeDate(ErmbProfileImpl profile) throws BusinessException
	{
		Calendar currentDate = DateHelper.getCurrentDate();
		int currentDay = currentDate.get(Calendar.DAY_OF_MONTH);
		return shiftChargeDate(profile, currentDate, currentDay);
	}

	private Period shiftChargeDate(ErmbProfileImpl profile, Calendar chargeDate, Integer chargeDayOfMonth) throws BusinessException
	{
		ErmbTariff tariff = profile.getTarif();
		int chargePeriod = tariff.getChargePeriod();

		Calendar nextChargeDate = DateHelper.addMonths(chargeDate, chargePeriod);
		DateHelper.setDayOfMonth(nextChargeDate, chargeDayOfMonth);

		profile.setChargeNextDate(nextChargeDate);
		profile.setChargeDayOfMonth(chargeDayOfMonth);

		return new Period(chargeDate, nextChargeDate);
	}
}
