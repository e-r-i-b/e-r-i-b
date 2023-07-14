package com.rssl.phizic.gate.csa;

import com.rssl.phizic.gate.clients.Client;
import com.rssl.phizic.utils.StringHelper;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Информация о пользователе, которая приходит из CSABack
 * @author niculichev
 * @ created 15.11.2012
 * @ $Author$
 * @ $Revision$
 */
public class UserInfo implements Serializable
{
	private String firstname;   // Имя пользователя
	private String patrname;    // Отчество пользователя
	private String surname;     // Фамилия пользователя
	private Calendar birthdate; // Дата рождения пользователя
	private String passport;    // ДУЛ пользователя
	private String cbCode;      // cbCode пользователя
	private String tb;          // ТБ пользователя

	/**
	 * ctor
	 */
	public UserInfo()
	{}

	/**
	 * ctor
	 * @param cbCode ТБ пользователя
	 * @param firstname Имя пользователя
	 * @param surname Фамилия пользователя
	 * @param patrname Отчество пользователя
	 * @param passport ДУЛ пользователя
	 * @param birthdate Дата рождения пользователя
	 */
	public UserInfo(String cbCode, String firstname, String surname, String patrname, String passport, Calendar birthdate)
	{
		this.birthdate = birthdate;
		this.cbCode = cbCode;
		this.firstname = firstname;
		this.surname = surname;
		this.patrname = patrname;
		this.passport = passport;
	}

	/**
	 * ctor по клиенту
	 * @param client - клиент (cannot be null)
	 * @param passport - ДУЛ (cannot be null nor empty)
	 * @param cbCode cbCode клиента (cannot be null nor empty)
	 * @param tb - ТБ клиента (cannot be null nor empty)
	 */
	public UserInfo(Client client, String passport, String cbCode, String tb)
	{
		if (StringHelper.isEmpty(passport))
			throw new IllegalArgumentException("Не указан ДУЛ");

		if (StringHelper.isEmpty(tb))
			throw new IllegalArgumentException("Не указан tb");

		if (StringHelper.isEmpty(cbCode))
			throw new IllegalArgumentException("Не указан cbCode");

		this.firstname = client.getFirstName();
		this.surname = client.getSurName();
		this.patrname = client.getPatrName();
		this.birthdate = client.getBirthDay();
		this.passport = passport;
		this.cbCode = cbCode;
		this.tb = tb;
	}

	public String getFirstname()
	{
		return firstname;
	}

	public void setFirstname(String firstname)
	{
		this.firstname = firstname;
	}

	public String getPatrname()
	{
		return patrname;
	}

	public void setPatrname(String patrname)
	{
		this.patrname = patrname;
	}

	public String getSurname()
	{
		return surname;
	}

	public void setSurname(String surname)
	{
		this.surname = surname;
	}

	public Calendar getBirthdate()
	{
		return birthdate;
	}

	public void setBirthdate(Calendar birthdate)
	{
		this.birthdate = birthdate;
	}

	public String getPassport()
	{
		return passport;
	}

	public void setPassport(String passport)
	{
		this.passport = passport;
	}

	public String getCbCode()
	{
		return cbCode;
	}

	public void setCbCode(String cbCode)
	{
		this.cbCode = cbCode;
	}

	public String getTb()
	{
		return tb;
	}

	public void setTb(String tb)
	{
		this.tb = tb;
	}

	@Override
	public String toString()
	{
		String name = StringHelper.getEmptyIfNull(firstname) + "|" +
				StringHelper.getEmptyIfNull(patrname) + "|" +
				StringHelper.getEmptyIfNull(StringUtils.substring(firstname, 0, 1));

		String birthdateString = null;
		if (birthdate != null)
		{
			SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
			birthdateString = dateFormat.format(birthdate.getTime());
		}

		return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
				.append("name", name)
				.append("birthdate", birthdateString)
				.append("passport", passport)
				.append("cbCode", cbCode)
				.append("tb", tb)
				.toString();
	}
}
