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
 * ���������� �������
 */
@Immutable
public class Phone
{
	@SuppressWarnings("PublicInnerClass")
	public enum Type
	{
		/**
		 * ���������
		 */
		MOBILE("1"),

		/**
		 * �������� �� ������ ����������
		 */
		RESIDENCE("2"),

		/**
		 * �������� �� ������ �����������
		 */
		REGISTRATION("3"),

		/**
		 * �������� �� ������ �����������
		 */
		WORK("4"),

		;

		private final String code;

		private Type(String code)
		{
			this.code = code;
		}

		/**
		 * @return �������� � ��������� Transact SM (never null nor empty)
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
	 * @param type - ��� ����������� �������� (never null)
	 * @param country - ��� ������ (never null)
	 * @param prefix - ������� (�����, never null)
	 * @param number - ����� (�����, never null)
	 */
	public Phone(Type type, String country, String prefix, String number)
	{
		if (type == null)
		    throw new IllegalArgumentException("�� ������ ��� ��������");
		if (StringHelper.isEmpty(country))
		    throw new IllegalArgumentException("�� ������ ��� ������");
		if (!StringHelper.isNumericString(prefix))
		    throw new IllegalArgumentException("�� ����� ������ �������: " + prefix);
		if (!StringHelper.isNumericString(number))
		    throw new IllegalArgumentException("�� ����� ������ �����: " + number);

		this.type = type;
		this.country = country;
		this.prefix = prefix;
		this.number = number;
	}

	/**
	 * @return ��� ����������� �������� (never null)
	 */
	public Type getType()
	{
		return type;
	}

	/**
	 * @return ��� ������ (never null)
	 */
	public String getCountry()
	{
		return country;
	}

	/**
	 * @return ������� (never null)
	 */
	public String getPrefix()
	{
		return prefix;
	}

	/**
	 * @return ����� (never null)
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
