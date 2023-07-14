package com.rssl.phizic.business.util;

import com.rssl.phizic.business.ermb.MobileBankManager;
import com.rssl.phizic.business.ermb.OurPhoneSearcher;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.utils.PhoneNumber;
import com.rssl.phizic.utils.PhoneNumberFormat;

/**
 * @author komarov
 * @ created 30.11.2012
 * @ $Author$
 * @ $Revision$
 */

public class MaskPhoneNumberUtil
{
	private static final Log log = PhizICLogFactory.getLog(Constants.LOG_MODULE_CORE);
	public static final OurPhoneSearcher<Boolean> OUR_PHONE_SEARCHER = new OurPhoneSearcher<Boolean>()
	{
		public boolean isEquals(String phone, String ourPhone)
		{
			try
			{
				return PhoneNumber.fromString(ourPhone).equals(PhoneNumber.fromString(phone));
			}
			catch (Exception e)
			{
				log.warn(e);
				return false;
			}
		}

		public Boolean returnOnFail()
		{
			return false;
		}

		public Boolean returnIfOur(String ourPhone)
		{
			return true;
		}
	};

	/**
	 * ¬озвращает маскированный number, если number наш номер телефона.
	 * @param number номер
	 * @return маскированный номер
	 */
	public static String getCutPhoneIfOur(String number)
	{
		try
		{
			if(MobileBankManager.checkOurPhone(number, OUR_PHONE_SEARCHER))
				return getCutPhone(number);
			return number;
		}
		catch (Exception e)
		{
			log.error(e.getMessage(), e);
			return number;
		}
	}

	/**
	 * ¬озвращает маскированный number, исход€ из того что формат MOBILE_INTERANTIONAL
	 * @param number номер
	 * @return  маскированный номер
	 */
	public static String getCutPohoneForIntFormat(String number)
	{
		return getCutPhone(PhoneNumberFormat.MOBILE_INTERANTIONAL,number);
	}

	/**
	 * ¬озвращает маскированный number
	 * @param number номер
	 * @return маскированный номер
	 */
	public static String getCutPhone(String number)
	{
		return getCutPhone(PhoneNumberFormat.SIMPLE_NUMBER,number);
	}

	/**
	 * ¬озвращает маскированный number дл€ адресной книги.
	 * @param number номер
	 * @return маскированный номер
	 */
	public static String getCutPhoneForAddressBook(String number)
	{
		return getCutPhone(PhoneNumberFormat.ADDRESS_BOOK, number.trim());
	}

	private static String getCutPhone(PhoneNumberFormat phoneformat, String number)
	{
		PhoneNumber phoneNumber = PhoneNumber.fromString(number);
		return phoneformat.format(phoneNumber.hideAbonent());
	}

	public static boolean isOurPhone(String number)
	{
		try
		{
			return MobileBankManager.checkOurPhone(number, MaskPhoneNumberUtil.OUR_PHONE_SEARCHER);
		}
		catch (Exception e)
		{
			log.warn(e);
			return false;
		}
	}

	/**
	 * ¬осстанавливат замаскированный номер клиента
 	 * @param phoneNumber замаскированный номер
	 * @return размаскированный номер
	 */
	public static String unmaskPhone(final String phoneNumber)
	{
		OurPhoneSearcher<String> searcher = new OurPhoneSearcher<String>()
		{
			public String returnOnFail() {
				return phoneNumber;
			}

			public String returnIfOur(String ourPhone) {
				return ourPhone;
			}
		};

		try
		{
			String unmaskPhone = MobileBankManager.checkOurPhone(phoneNumber, searcher);
			return PhoneNumberFormat.SIMPLE_NUMBER.translate(unmaskPhone);
		}
		catch (Exception e)
		{
			log.error(e.getMessage(), e);
			return phoneNumber;
		}
	}

	/**
	 * ћаскирует телефон(первые 3 цифры).
	 * @param number номер телефона в виде строки
	 * @return строка с маскированным номером
	 */
	public static String maskPhone(String number)
	{
		try
		{
			PhoneNumber phoneNumber = PhoneNumber.fromString(number);
			return PhoneNumberFormat.SIMPLE_NUMBER.format(phoneNumber.hideAbonent());
		}
		catch (Exception ignored)
		{
			return number;
		}
	}
}
