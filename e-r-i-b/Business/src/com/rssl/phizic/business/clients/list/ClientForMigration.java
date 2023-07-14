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
 * ������ ��� ��������
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
	 * @return �������
	 */
	public String getSurname()
	{
		return surname;
	}

	/**
	 * ������ �������
	 * @param surname �������
	 */
	public void setSurname(String surname)
	{
		this.surname = surname;
	}

	/**
	 * @return ���
	 */
	public String getFirstname()
	{
		return firstname;
	}

	/**
	 * ������ ���
	 * @param firstname ���
	 */
	public void setFirstname(String firstname)
	{
		this.firstname = firstname;
	}

	/**
	 * @return ��������
	 */
	public String getPatronymic()
	{
		return patronymic;
	}

	/**
	 * ������ ��������
	 * @param patronymic ��������
	 */
	public void setPatronymic(String patronymic)
	{
		this.patronymic = patronymic;
	}

	/**
	 * @return ������ ��� �������
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
	 * @return ���
	 */
	public String getDocument()
	{
		return document;
	}

	/**
	 * ������ ���
	 * @param document ���
	 */
	public void setDocument(String document)
	{
		this.document = document;
	}

	/**
	 * @return ��
	 */
	public String getDepartment()
	{
		return department;
	}

	/**
	 * ������ ��
	 * @param department ��
	 */
	public void setDepartment(String department)
	{
		this.department = department;
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
	 * @param birthday ����
	 */
	public void setBirthday(Calendar birthday)
	{
		this.birthday = birthday;
	}

	/**
	 * @return ��� ��������
	 */
	public CreationType getAgreementType()
	{
		return agreementType;
	}

	/**
	 * ������ ��� ��������
	 * @param agreementType ���
	 */
	public void setAgreementType(CreationType agreementType)
	{
		this.agreementType = agreementType;
	}

	/**
	 * @return ����� ��������
	 */
	public String getAgreementNumber()
	{
		return agreementNumber;
	}

	/**
	 * ������ ����� ��������
	 * @param agreementNumber �����
	 */
	public void setAgreementNumber(String agreementNumber)
	{
		this.agreementNumber = agreementNumber;
	}
}
