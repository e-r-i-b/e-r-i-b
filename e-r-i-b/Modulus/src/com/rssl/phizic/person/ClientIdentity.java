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
 * ФИО+ДУЛ+ДР(+ТБ)
 * Идентификационные данные клиента
 */
@Immutable
@SuppressWarnings("PublicField")
public final class ClientIdentity
{
	/**
	 * Фамилия клиента в верхнем регистре и без пробелов (указана всегда)
	 */
	public final String lastName;

	/**
	 * Имя клиента в верхнем регистре и без пробелов (указано всегда)
	 */
	public final String firstName;

	/**
	 * Отчество клиента в верхнем регистре и без пробелов (может быть не указано)
	 */
	public final String middleName;

	/**
	 * Документ, удостоверяющий личность клиента, в верхнем регистре и без пробелов (указан всегда)
	 */
	public final String passport;

	/**
	 * Дата рождения клиента (указана всегда)
	 */
	public final Date birthDay;

	/**
	 * Номер тербанка без лидирующих нулей (может быть не указан)
	 */
	public final String tb;

	/**
	 * Конструктор с указанием всех полей
	 * @param lastName - фамилия (должна быть указана)
	 * @param firstName - имя (должно быть указано)
	 * @param middleName - отчество (может быть не указано)
	 * @param passport - ДУЛ (должен быть указан)
	 * @param birthDay - дата рождения (должна быть указана)
	 * @param tb - номер тербанка (может быть не указан)
	 */
	public ClientIdentity(String lastName, String firstName, String middleName, String passport, Date birthDay, String tb)
	{
		if (StringUtils.isBlank(lastName))
			throw new IllegalArgumentException("Не указана фамилия");
		if (StringUtils.isBlank(firstName))
			throw new IllegalArgumentException("Не указано имя");
		if (StringUtils.isBlank(passport))
			throw new IllegalArgumentException("Не указан ДУЛ");
		if (birthDay == null)
			throw new IllegalArgumentException("Не указана дата рождения");

		this.lastName = lastName.replace(" ", "").toUpperCase();
		this.firstName = firstName.replace(" ", "").toUpperCase();
		this.middleName = StringHelper.isNotEmpty(middleName) ? middleName.replace(" ", "").toUpperCase() : null;

		this.passport = passport.replace(" ", "").toUpperCase();

		// noinspection deprecation
		this.birthDay = new Date(birthDay.getYear(), birthDay.getMonth(), birthDay.getDate());

		this.tb = StringHelper.isNotEmpty(tb) ? StringHelper.removeLeadingZeros(tb) : null;
	}

	/**
	 * Конструктор с указанием всех полей кроме ТБ
	 * @param lastName - фамилия (должна быть указана)
	 * @param firstName - имя (должно быть указано)
	 * @param middleName - отчество (может быть не указано)
	 * @param passport - ДУЛ (должен быть указан)
	 * @param birthDay - дата рождения (должна быть указана)
	 */
	public ClientIdentity(String lastName, String firstName, String middleName, String passport, Date birthDay)
	{
		this(lastName, firstName, middleName, passport, birthDay, null);
	}

	/**
	 * @return ФИО
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
