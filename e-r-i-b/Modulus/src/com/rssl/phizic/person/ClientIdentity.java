package com.rssl.phizic.person;

import com.rssl.phizic.common.types.annotation.Immutable;
import com.rssl.phizic.utils.StringHelper;
import org.apache.commons.lang.StringUtils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author Erkin
 * @ created 01.08.2014
 * @ $Author$
 * @ $Revision$
 */

/**
 * ���+���+��(+��)
 * ����������������� ������ �������
 */
@Immutable
@SuppressWarnings("PublicField")
public final class ClientIdentity
{
	/**
	 * ������� ������� � ������� �������� � ��� �������� (������� ������)
	 */
	public final String lastName;

	/**
	 * ��� ������� � ������� �������� � ��� �������� (������� ������)
	 */
	public final String firstName;

	/**
	 * �������� ������� � ������� �������� � ��� �������� (����� ���� �� �������)
	 */
	public final String middleName;

	/**
	 * ��������, �������������� �������� �������, � ������� �������� � ��� �������� (������ ������)
	 */
	public final String passport;

	/**
	 * ���� �������� ������� (������� ������)
	 */
	public final Date birthDay;

	/**
	 * ����� �������� ��� ���������� ����� (����� ���� �� ������)
	 */
	public final String tb;

	/**
	 * ����������� � ��������� ���� �����
	 * @param lastName - ������� (������ ���� �������)
	 * @param firstName - ��� (������ ���� �������)
	 * @param middleName - �������� (����� ���� �� �������)
	 * @param passport - ��� (������ ���� ������)
	 * @param birthDay - ���� �������� (������ ���� �������)
	 * @param tb - ����� �������� (����� ���� �� ������)
	 */
	public ClientIdentity(String lastName, String firstName, String middleName, String passport, Date birthDay, String tb)
	{
		if (StringUtils.isBlank(lastName))
			throw new IllegalArgumentException("�� ������� �������");
		if (StringUtils.isBlank(firstName))
			throw new IllegalArgumentException("�� ������� ���");
		if (StringUtils.isBlank(passport))
			throw new IllegalArgumentException("�� ������ ���");
		if (birthDay == null)
			throw new IllegalArgumentException("�� ������� ���� ��������");

		this.lastName = lastName.replace(" ", "").toUpperCase();
		this.firstName = firstName.replace(" ", "").toUpperCase();
		this.middleName = StringHelper.isNotEmpty(middleName) ? middleName.replace(" ", "").toUpperCase() : null;

		this.passport = passport.replace(" ", "").toUpperCase();

		// noinspection deprecation
		this.birthDay = new Date(birthDay.getYear(), birthDay.getMonth(), birthDay.getDate());

		this.tb = StringHelper.isNotEmpty(tb) ? StringHelper.removeLeadingZeros(tb) : null;
	}

	/**
	 * ����������� � ��������� ���� ����� ����� ��
	 * @param lastName - ������� (������ ���� �������)
	 * @param firstName - ��� (������ ���� �������)
	 * @param middleName - �������� (����� ���� �� �������)
	 * @param passport - ��� (������ ���� ������)
	 * @param birthDay - ���� �������� (������ ���� �������)
	 */
	public ClientIdentity(String lastName, String firstName, String middleName, String passport, Date birthDay)
	{
		this(lastName, firstName, middleName, passport, birthDay, null);
	}

	/**
	 * @return ���
	 */
	public String getFullName()
	{
		if (middleName != null)
			return lastName + firstName + middleName;
		else return lastName + firstName;
	}

	@Override
	public int hashCode()
	{
		int result = lastName.hashCode();
		result = 31 * result + firstName.hashCode();
		result = 31 * result + (middleName != null ? middleName.hashCode() : 0);
		return result;
	}

	@Override
	public boolean equals(Object o)
	{
		if (this == o)
			return true;
		if (o == null)
			return false;
		if (getClass() != o.getClass())
			return false;

		ClientIdentity that = (ClientIdentity) o;
		String thisName = this.lastName + this.firstName + StringHelper.getEmptyIfNull(this.middleName);
		String thatName = that.lastName + that.firstName + StringHelper.getEmptyIfNull(that.middleName);

		boolean rc = thisName.equals(thatName);
		rc = rc && birthDay.equals(that.birthDay);
		rc = rc && passport.equals(that.passport);
		rc = rc && StringHelper.equalsNullIgnore(tb, that.tb);
		return rc;
	}

	@Override
	public String toString()
	{
		SimpleDateFormat dateFormat = new SimpleDateFormat("ddMMyy");

		String nameString = lastName.charAt(0) + ". " + firstName + " " + (middleName != null ? middleName : "???");
		String passportString = passport;
		String birthDayString = dateFormat.format(birthDay);
		String tbString = (tb != null) ? tb : "??";
		return nameString + "|" + passportString + "|" + birthDayString + "|" + tbString;
	}
}
