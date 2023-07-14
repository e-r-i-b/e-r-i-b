package com.rssl.phizic.utils;

import com.rssl.phizic.common.types.annotation.Immutable;
import org.apache.commons.lang.StringUtils;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * ����� ��������
 * ������� ��:
 *  - ���� ������
 *  - ���� ���������
 *  - ���� ��������
 * @author Erkin
 * @ created 26.04.2010
 * @ $Author$
 * @ $Revision$
 */
@Immutable
public final class PhoneNumber implements Serializable
{
	private static final int ABONENT_PART_LENGTH = 3;

	private String country;

	private String operator;

	private String abonent;

	///////////////////////////////////////////////////////////////////////////

	//��� ���-�������
	public PhoneNumber()
	{
	}

	/**
	 * ������ ������, ���������� ���������� �����
	 * @param numberAsString ������
	 * @return ����� ��������
	 * @throws NumberFormatException
	 */
	public static PhoneNumber fromString(String numberAsString) throws NumberFormatException
	{
		return PhoneNumberFormat.internalParse(numberAsString, PhoneNumberFormat.values());
	}

	/**
	 * ������ �����, ���������� ���������� ������
	 * @param numberStrings ������
	 * @return ������ ���������
	 * @throws NumberFormatException
	 */
	public static Set<PhoneNumber> fromString(Collection<String> numberStrings) throws NumberFormatException
	{
		Set<PhoneNumber> numbers = new HashSet<PhoneNumber>(numberStrings.size());
		for (String numberString : numberStrings)
			numbers.add(PhoneNumberFormat.internalParse(numberString, PhoneNumberFormat.values()));
		return numbers;
	}

	PhoneNumber(String country, String operator, String abonent)
	{
		this.country = StringUtils.trimToNull(country);
		this.operator = StringUtils.trimToNull(operator);
		this.abonent = StringUtils.trimToNull(abonent);

		if (this.country == null)
			throw new IllegalArgumentException("Argument 'country' cannot be null");
		if (this.operator == null)
			throw new IllegalArgumentException("Argument 'operator' cannot be null");
		if (this.abonent == null)
			throw new IllegalArgumentException("Argument 'abonent' cannot be null");
	}

	/**
	 * @return ��� ������
	 */
	public String country()
	{
		return country;
	}

	/**
	 * @return ��� ���������
	 */
	public String operator()
	{
		return operator;
	}

	/**
	 * @return ��� ��������
	 */
	public String abonent()
	{
		return abonent;
	}

	public PhoneNumber hideAbonent()
	{
		return new PhoneNumber(country, operator, StringUtils.repeat(MaskHelper.getActualMaskSymbol(), ABONENT_PART_LENGTH) + getAbonentVisiblePart());
	}

	/**
	 * ������������ ����� ������ ��������
	 * ������������ ��� ����������� ������ � ������������� ����
	 * @return ����� ������ ��������
	 */
	public String getAbonentVisiblePart()
	{
		return abonent.substring(ABONENT_PART_LENGTH, abonent.length());
	}

	public int hashCode()
	{
		int result = country.hashCode();
		result = 31 * result + operator.hashCode();
		return result;
	}

	public boolean equals(Object o)
	{
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;

		PhoneNumber that = (PhoneNumber) o;

		if (!abonent.equals(that.abonent))
			return false;
		if (!country.equals(that.country))
			return false;
		if (!operator.equals(that.operator))
			return false;

		return true;
	}

	@Override
	public String toString()
	{
		return country + '|' + operator + '|' + abonent;
	}
}
