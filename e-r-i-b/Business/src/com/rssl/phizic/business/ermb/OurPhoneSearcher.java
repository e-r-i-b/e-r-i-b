package com.rssl.phizic.business.ermb;

import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.utils.PhoneNumber;
import com.rssl.phizic.utils.PhoneNumberFormat;
import org.apache.commons.lang.StringUtils;

import java.util.Collection;

/**
 * @author Dorzhinov
 * @ created 13.08.13
 * @ $Author$
 * @ $Revision$
 */

/**
 * Утилитный класс для поиска своего номера телефона
 * @param <T>    Тип возвращаемого значения
 */
public abstract class OurPhoneSearcher<T>
{
	private static final Log log = PhizICLogFactory.getLog(Constants.LOG_MODULE_CORE);

	/**
	 * Проверяет на равенство с одним из "наших" номеров телефонов
	 *
	 * @param testingPhone    проверяемый телефон
	 * @param ourPhone    "наш" телефон
	 * @return true - если проверяемый телефон - "наш"
	 * Важно! Метод не должен выбрасывать исключений
	 */
	public boolean isEquals(String testingPhone, String ourPhone)
	{
		// A. Повезло: testingPhone не маскирован и совпадает с ourPhone по формату
		if (StringUtils.equals(testingPhone, ourPhone))
			return true;

		// Попробуем получить номер телефон (если testingPhone не маскирован)
		PhoneNumber testingPhoneNumber;
		try
		{
			testingPhoneNumber = PhoneNumber.fromString(testingPhone);
		}
		catch (NumberFormatException ignored)
		{
			testingPhoneNumber = null;
		}

		try
		{
			// B. Сраниваем немаскированные телефоны
			PhoneNumber ourPhoneNumber = PhoneNumber.fromString(ourPhone);
			if (testingPhoneNumber != null && ourPhoneNumber.equals(testingPhoneNumber))
				return true;

			// C. Не повезло: testingPhone пришёл в маскированном виде, придётся гадать на форматах.
			// Т.к. не известно, в каком формате пришёл маскированный телефон, приходится перебирать все форматы
			for (PhoneNumberFormat phoneFormat : PhoneNumberFormat.values())
			{
				String ourHiddenPhone = phoneFormat.formatAsHidden(ourPhoneNumber);
				if (ourHiddenPhone.equals(testingPhone))
					return true;
			}

			return false;
		}
		catch (Exception e)
		{
			log.error(e.getMessage(), e);
			return false;
		}
	}

	/**
	 * Ищет "наш" номер телефона
	 *
	 * @param phone    проверяемый телефон
	 * @param ourPhones    "наши" телефоны
	 * @return найденный "наш" телефон или null
	 * Важно! Метод не должен выбрасывать исключений
	 */
	public String selectOurPhone(String phone, Collection<String> ourPhones)
	{
		for (String ourPhone : ourPhones)
		{
			if (isEquals(phone, ourPhone))
				return ourPhone;
		}

		return null;
	}

	/**
	 * Выполняется если проверяемый телефон не был найден среди "своих" или возникла ошибка
	 * @return T
	 */
	public abstract T returnOnFail();

	/**
	 * Выполняется если проверяемый телефон - "наш"
	 * @param ourPhone    "наш" телефон
	 * @return T
	 */
	public abstract T returnIfOur(String ourPhone);
}
