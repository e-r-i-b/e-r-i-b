package com.rssl.phizic.config.loyalty;

import com.rssl.phizic.utils.CihperHelper;
import com.rssl.phizic.utils.PhoneNumber;
import com.rssl.phizic.utils.PhoneNumberFormat;
import com.rssl.phizic.utils.StringHelper;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

/**
 * @author gladishev
 * @ created 14.08.2012
 * @ $Author$
 * @ $Revision$
 */
public class LoyaltyHelper
{
	private static final String SHA1_ERROR = "Лояльность: Ошибка при попытки получить цифровой отпечаток строки(SHA1)";
	private static final Log log = LogFactory.getLog(LoyaltyHelper.class);

	/**
	 * Вычисляет по алгоритму хэш от номера карты клиента
	 * @param cardNumber - номер карты
	 * @return хэш
	 */
	public static String generateHash(String cardNumber)
	{
		if (StringHelper.isEmpty(cardNumber))
			return null;

		CihperHelper cihperHelper = new CihperHelper();
		//получение SHA1 отпечатка
		String sha1String = null;
		try
		{
			sha1String = cihperHelper.SHA1(cardNumber);
		}
		catch (NoSuchAlgorithmException e)
		{
			log.error(SHA1_ERROR,e);
			return null;
		}
		catch (UnsupportedEncodingException e)
		{
			log.error(SHA1_ERROR,e);
			return null;
		}
		return sha1String;
	}

	/**
	 * Форматирование номера телефона для формы регистрации в ПЛ
	 * @param phone - номер телефона
	 * @return форматированный номер телефона
	 */
	public static String formatPhone(String phone)
	{
		PhoneNumber number = PhoneNumber.fromString(phone);
		return "+7" + " (" + number.operator() + ") <span class='loyaltyPhone'>···</span> " + number.abonent().substring(3,5) + " " + number.abonent().substring(5,7);
	}
}
