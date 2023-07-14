package com.rssl.phizicgate.mdm.business.profiles;

import java.util.Calendar;
import java.util.Set;

/**
 * @author akrenev
 * @ created 10.07.2015
 * @ $Author$
 * @ $Revision$
 *
 * ������� ���
 */

public class Profile
{
	private Long id;
	private String mdmId;
	private Set<Long> profileIds;
	private String lastName;
	private String firstName;
	private String middleName;
	private Gender gender;
	private Calendar birthday;
	private String birthPlace;
	private Set<Document> documents;
	private String taxId;
	private boolean resident;
	private boolean employee;
	private boolean shareholder;
	private boolean insider;
	private String citizenship;
	private boolean literacy;

	/**
	 * @return �������������
	 */
	public Long getId()
	{
		return id;
	}

	/**
	 * ������ �������������
	 * @param id �������������
	 */
	public void setId(Long id)
	{
		this.id = id;
	}

	/**
	 * @return ������������� ���
	 */
	public String getMdmId()
	{
		return mdmId;
	}

	/**
	 * ������ ������������� ���
	 * @param mdmId �������������
	 */
	public void setMdmId(String mdmId)
	{
		this.mdmId = mdmId;
	}

	/**
	 * @return �������������� ��������
	 */
	public Set<Long> getProfileIds()
	{
		//noinspection ReturnOfCollectionOrArrayField
		return profileIds;
	}

	/**
	 * ������ �������������� ��������
	 * @param profileIds ��������������
	 */
	public void setProfileIds(Set<Long> profileIds)
	{
		//noinspection AssignmentToCollectionOrArrayFieldFromParameter
		this.profileIds = profileIds;
	}

	/**
	 * @return �������
	 */
	public String getLastName()
	{
		return lastName;
	}

	/**
	 * ������ �������
	 * @param lastName �������
	 */
	public void setLastName(String lastName)
	{
		this.lastName = lastName;
	}

	/**
	 * @return ���
	 */
	public String getFirstName()
	{
		return firstName;
	}

	/**
	 * ������ ���
	 * @param firstName ���
	 */
	public void setFirstName(String firstName)
	{
		this.firstName = firstName;
	}

	/**
	 * @return ��������
	 */
	public String getMiddleName()
	{
		return middleName;
	}

	/**
	 * ������ ��������
	 * @param middleName ��������
	 */
	public void setMiddleName(String middleName)
	{
		this.middleName = middleName;
	}

	/**
	 * @return ���
	 */
	public Gender getGender()
	{
		return gender;
	}

	/**
	 * ������ ���
	 * @param gender ���
	 */
	public void setGender(Gender gender)
	{
		this.gender = gender;
	}

	/**
	 * @return ���� ��������
	 */
	public Calendar getBirthday()
	{
		return birthday;
	}

	/**
	 * ������ ���� ��������
	 * @param birthday ���� ��������
	 */
	public void setBirthday(Calendar birthday)
	{
		this.birthday = birthday;
	}

	/**
	 * @return ����� ��������
	 */
	public String getBirthPlace()
	{
		return birthPlace;
	}

	/**
	 * ������ ����� ��������
	 * @param birthPlace ����� ��������
	 */
	public void setBirthPlace(String birthPlace)
	{
		this.birthPlace = birthPlace;
	}

	/**
	 * @return ��������� �������
	 */
	public Set<Document> getDocuments()
	{
		//noinspection ReturnOfCollectionOrArrayField
		return documents;
	}

	/**
	 * ������ ��������� ���������� �������
	 * @param documents ���������
	 */
	public void setDocuments(Set<Document> documents)
	{
		//noinspection AssignmentToCollectionOrArrayFieldFromParameter
		this.documents = documents;
	}

	/**
	 * @return ���
	 */
	public String getTaxId()
	{
		return taxId;
	}

	/**
	 * ������ ���
	 * @param taxId ���
	 */
	public void setTaxId(String taxId)
	{
		this.taxId = taxId;
	}

	/**
	 * @return ������� ������������
	 */
	public boolean isResident()
	{
		return resident;
	}

	/**
	 * ������ ������� ������������
	 * @param resident ������� ������������
	 */
	public void setResident(boolean resident)
	{
		this.resident = resident;
	}

	/**
	 * @return ������� ����������
	 */
	public boolean isEmployee()
	{
		return employee;
	}

	/**
	 * ������ ������� ����������
	 * @param employee ������� ����������
	 */
	public void setEmployee(boolean employee)
	{
		this.employee = employee;
	}

	/**
	 * @return ������� ���������
	 */
	public boolean isShareholder()
	{
		return shareholder;
	}

	/**
	 * ������ ������� ���������
	 * @param shareholder ������� ���������
	 */
	public void setShareholder(boolean shareholder)
	{
		this.shareholder = shareholder;
	}

	/**
	 * @return ������� ���������
	 */
	public boolean isInsider()
	{
		return insider;
	}

	/**
	 * ������ ������� ���������
	 * @param insider ������� ���������
	 */
	public void setInsider(boolean insider)
	{
		this.insider = insider;
	}

	/**
	 * @return �����������
	 */
	public String getCitizenship()
	{
		return citizenship;
	}

	/**
	 * ������ �����������
	 * @param citizenship �����������
	 */
	public void setCitizenship(String citizenship)
	{
		this.citizenship = citizenship;
	}

	/**
	 * @return �����������
	 */
	public boolean isLiteracy()
	{
		return literacy;
	}

	/**
	 * ������ �����������
	 * @param literacy �����������
	 */
	public void setLiteracy(boolean literacy)
	{
		this.literacy = literacy;
	}
}
