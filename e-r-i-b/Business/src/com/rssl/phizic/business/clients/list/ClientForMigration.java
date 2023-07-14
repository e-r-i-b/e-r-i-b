package com.rssl.phizic.business.clients.list;

import com.rssl.phizic.common.types.client.CreationType;
import com.rssl.phizic.utils.StringHelper;

import java.util.Calendar;

/**
 * @author akrenev
 * @ created 26.09.2014
 * @ $Author$
 * @ $Revision$
 *
 * Клиент для миграции
 */

public class ClientForMigration
{
	private Long id;
	private String surname;
	private String firstname;
	private String patronymic;
	private String document;
	private String department;
	private Calendar birthday;
	private CreationType agreementType;
	private String agreementNumber;

	/**
	 * @return идентификатор
	 */
	public Long getId()
	{
		return id;
	}

	/**
	 * задать идентификатор
	 * @param id идентификатор
	 */
	public void setId(Long id)
	{
		this.id = id;
	}

	/**
	 * @return фамилия
	 */
	public String getSurname()
	{
		return surname;
	}

	/**
	 * задать фамилию
	 * @param surname фамилия
	 */
	public void setSurname(String surname)
	{
		this.surname = surname;
	}

	/**
	 * @return имя
	 */
	public String getFirstname()
	{
		return firstname;
	}

	/**
	 * задать имя
	 * @param firstname имя
	 */
	public void setFirstname(String firstname)
	{
		this.firstname = firstname;
	}

	/**
	 * @return отчество
	 */
	public String getPatronymic()
	{
		return patronymic;
	}

	/**
	 * задать отчество
	 * @param patronymic отчество
	 */
	public void setPatronymic(String patronymic)
	{
		this.patronymic = patronymic;
	}

	/**
	 * @return полное имя клиента
	 */
	public String getFullName()
	{
		String fullName = StringHelper.getEmptyIfNull(getSurname());

		if (StringHelper.isNotEmpty(fullName) && StringHelper.isNotEmpty(getFirstname()))
			fullName += " ";
		fullName += StringHelper.getEmptyIfNull(getFirstname());

		if (StringHelper.isNotEmpty(fullName) && StringHelper.isNotEmpty(getPatronymic()))
			fullName += " ";
		fullName += StringHelper.getEmptyIfNull(getPatronymic());

		return fullName;
	}

	/**
	 * @return дул
	 */
	public String getDocument()
	{
		return document;
	}

	/**
	 * задать дул
	 * @param document дул
	 */
	public void setDocument(String document)
	{
		this.document = document;
	}

	/**
	 * @return тб
	 */
	public String getDepartment()
	{
		return department;
	}

	/**
	 * задать тб
	 * @param department тб
	 */
	public void setDepartment(String department)
	{
		this.department = department;
	}

	/**
	 * @return дата рождения
	 */
	public Calendar getBirthday()
	{
		return birthday;
	}

	/**
	 * задать дату рождения
	 * @param birthday дата
	 */
	public void setBirthday(Calendar birthday)
	{
		this.birthday = birthday;
	}

	/**
	 * @return тип договора
	 */
	public CreationType getAgreementType()
	{
		return agreementType;
	}

	/**
	 * задать тип договора
	 * @param agreementType тип
	 */
	public void setAgreementType(CreationType agreementType)
	{
		this.agreementType = agreementType;
	}

	/**
	 * @return номер договора
	 */
	public String getAgreementNumber()
	{
		return agreementNumber;
	}

	/**
	 * задать номер договора
	 * @param agreementNumber номер
	 */
	public void setAgreementNumber(String agreementNumber)
	{
		this.agreementNumber = agreementNumber;
	}
}
