package com.rssl.phizic.gate.loanclaim.type;

import com.rssl.phizic.common.types.annotation.Immutable;
import com.rssl.phizic.utils.StringHelper;

/**
 * @author Erkin
 * @ created 21.01.2014
 * @ $Author$
 * @ $Revision$
 */

/**
 * Контактный телефон
 */
@Immutable
public class Phone
{
	@SuppressWarnings("PublicInnerClass")
	public enum Type
	{
		/**
		 * Мобильный
		 */
		MOBILE("1"),

		/**
		 * Домашний по адресу проживания
		 */
		RESIDENCE("2"),

		/**
		 * Домашний по адресу регистрации
		 */
		REGISTRATION("3"),

		/**
		 * Домашний по адресу регистрации
		 */
		WORK("4"),

		;

		private final String code;

		private Type(String code)
		{
			this.code = code;
		}

		/**
		 * @return значение в кодировке Transact SM (never null nor empty)
		 */
		public String getCode()
		{
			return code;
		}
	}

	///////////////////////////////////////////////////////////////////////////

	private final Type type;

	private final String country;

	private final String prefix;

	private final String number;

	///////////////////////////////////////////////////////////////////////////

	/**
	 * ctor
	 * @param type - тип контактного телефона (never null)
	 * @param country - код страны (never null)
	 * @param prefix - префикс (цифры, never null)
	 * @param number - номер (цифры, never null)
	 */
	public Phone(Type type, String country, String prefix, String number)
	{
		if (type == null)
		    throw new IllegalArgumentException("Не указан тип телефона");
		if (StringHelper.isEmpty(country))
		    throw new IllegalArgumentException("Не указан код страны");
		if (!StringHelper.isNumericString(prefix))
		    throw new IllegalArgumentException("Не верно указан префикс: " + prefix);
		if (!StringHelper.isNumericString(number))
		    throw new IllegalArgumentException("Не верно указан номер: " + number);

		this.type = type;
		this.country = country;
		this.prefix = prefix;
		this.number = number;
	}

	/**
	 * @return тип контактного телефона (never null)
	 */
	public Type getType()
	{
		return type;
	}

	/**
	 * @return код страны (never null)
	 */
	public String getCountry()
	{
		return country;
	}

	/**
	 * @return префикс (never null)
	 */
	public String getPrefix()
	{
		return prefix;
	}

	/**
	 * @return номер (never null)
	 */
	public String getNumber()
	{
		return number;
	}

	@Override
	public int hashCode()
	{
		int result = prefix.hashCode();
		result = 31 * result + number.hashCode();
		return result;
	}

	@Override
	public boolean equals(Object o)
	{
		if (this == o)
			return true;

		if (o == null || getClass() != o.getClass())
			return false;

		Phone other = (Phone) o;

		return type == other.type
				&& country.equals(other.country)
				&& prefix.equals(other.prefix)
				&& number.equals(other.number);
	}
}
