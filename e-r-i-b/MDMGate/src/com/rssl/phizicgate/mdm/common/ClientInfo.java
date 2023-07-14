package com.rssl.phizicgate.mdm.common;

import com.rssl.phizicgate.mdm.business.profiles.Gender;

import java.util.Calendar;
import java.util.Set;

/**
 * @author akrenev
 * @ created 14.07.2015
 * @ $Author$
 * @ $Revision$
 *
 * Информация по клиенту из мдм
 */

public class ClientInfo
{
	private String mdmId;
	private Long innerId;
	private String lastName;
	private String firstName;
	private String middleName;
	private Gender gender;
	private Calendar birthday;
	private String birthPlace;
	private Set<DocumentInfo> documents;
	private String taxId;
	private boolean resident;
	private boolean employee;
	private boolean shareholder;
	private boolean insider;
	private String citizenship;
	private boolean literacy;

	/**
	 * @return идентификатор мдм
	 */
	public String getMdmId()
	{
		return mdmId;
	}

	/**
	 * задать идентификатор мдм
	 * @param mdmId идентификатор
	 */
	public void setMdmId(String mdmId)
	{
		this.mdmId = mdmId;
	}

	/**
	 * @return внутренний идентификатор
	 */
	public Long getInnerId()
	{
		return innerId;
	}

	/**
	 * задать внутренний идентификатор
	 * @param innerId идентификатор
	 */
	public void setInnerId(Long innerId)
	{
		this.innerId = innerId;
	}

	/**
	 * @return фамилия
	 */
	public String getLastName()
	{
		return lastName;
	}

	/**
	 * задать фамилию
	 * @param lastName фамилия
	 */
	public void setLastName(String lastName)
	{
		this.lastName = lastName;
	}

	/**
	 * @return имя
	 */
	public String getFirstName()
	{
		return firstName;
	}

	/**
	 * задать имя
	 * @param firstName имя
	 */
	public void setFirstName(String firstName)
	{
		this.firstName = firstName;
	}

	/**
	 * @return отчество
	 */
	public String getMiddleName()
	{
		return middleName;
	}

	/**
	 * задать отчество
	 * @param middleName отчество
	 */
	public void setMiddleName(String middleName)
	{
		this.middleName = middleName;
	}

	/**
	 * @return пол
	 */
	public Gender getGender()
	{
		return gender;
	}

	/**
	 * задать пол
	 * @param gender пол
	 */
	public void setGender(Gender gender)
	{
		this.gender = gender;
	}

	/**
	 * @return день рождения
	 */
	public Calendar getBirthday()
	{
		return birthday;
	}

	/**
	 * задать день рождения
	 * @param birthday день рождения
	 */
	public void setBirthday(Calendar birthday)
	{
		this.birthday = birthday;
	}

	/**
	 * @return место рождения
	 */
	public String getBirthPlace()
	{
		return birthPlace;
	}

	/**
	 * задать место рождения
	 * @param birthPlace место рождения
	 */
	public void setBirthPlace(String birthPlace)
	{
		this.birthPlace = birthPlace;
	}

	/**
	 * @return документы клиента
	 */
	public Set<DocumentInfo> getDocuments()
	{
		//noinspection ReturnOfCollectionOrArrayField
		return documents;
	}

	/**
	 * задать множество документов клиента
	 * @param documents документы
	 */
	public void setDocuments(Set<DocumentInfo> documents)
	{
		//noinspection AssignmentToCollectionOrArrayFieldFromParameter
		this.documents = documents;
	}

	/**
	 * @return инн
	 */
	public String getTaxId()
	{
		return taxId;
	}

	/**
	 * задать инн
	 * @param taxId инн
	 */
	public void setTaxId(String taxId)
	{
		this.taxId = taxId;
	}

	/**
	 * @return признак резидентства
	 */
	public boolean isResident()
	{
		return resident;
	}

	/**
	 * задать признак резидентства
	 * @param resident признак резидентства
	 */
	public void setResident(boolean resident)
	{
		this.resident = resident;
	}

	/**
	 * @return признак сотрудника
	 */
	public boolean isEmployee()
	{
		return employee;
	}

	/**
	 * задать признак сотрудника
	 * @param employee признак сотрудника
	 */
	public void setEmployee(boolean employee)
	{
		this.employee = employee;
	}

	/**
	 * @return признак акционера
	 */
	public boolean isShareholder()
	{
		return shareholder;
	}

	/**
	 * задать признак акционера
	 * @param shareholder признак акционера
	 */
	public void setShareholder(boolean shareholder)
	{
		this.shareholder = shareholder;
	}

	/**
	 * @return признак инсайдера
	 */
	public boolean isInsider()
	{
		return insider;
	}

	/**
	 * задать признак инсайдера
	 * @param insider признак инсайдера
	 */
	public void setInsider(boolean insider)
	{
		this.insider = insider;
	}

	/**
	 * @return гражданство
	 */
	public String getCitizenship()
	{
		return citizenship;
	}

	/**
	 * задать гражданство
	 * @param citizenship гражданство
	 */
	public void setCitizenship(String citizenship)
	{
		this.citizenship = citizenship;
	}

	/**
	 * @return грамотность
	 */
	public boolean isLiteracy()
	{
		return literacy;
	}

	/**
	 * задать грамотность
	 * @param literacy грамотность
	 */
	public void setLiteracy(boolean literacy)
	{
		this.literacy = literacy;
	}
}
