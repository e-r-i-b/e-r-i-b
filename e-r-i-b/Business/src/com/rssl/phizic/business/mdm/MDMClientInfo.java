package com.rssl.phizic.business.mdm;

import com.rssl.phizic.gate.csa.UserInfo;

import java.util.Calendar;

/**
 * �������� ��� ��������� csaProfileId
 * @author komarov
 * @ created 21.07.15
 * @ $Author$
 * @ $Revision$
 */
public class MDMClientInfo
{
	private Long id;
	private Long csaProfileId;
	private String firstName;   // ��� ������������
	private String patrName;    // �������� ������������
	private String surname;     // ������� ������������
	private Calendar birthDate; // ���� �������� ������������
	private String passport;    // ��� ������������
	private String tb;          // �� ������������

	/**
	 * @return ������������� ��� hibernate
	 */
	public Long getId()
	{
		return id;
	}

	/**
	 * @param id ������������� ��� hibernate
	 */
	public void setId(Long id)
	{
		this.id = id;
	}

	/**
	 * @return ��� ������������
	 */
	public String getFirstName()
	{
		return firstName;
	}

	/**
	 * @return �������� ������������
	 */
	public String getPatrName()
	{
		return patrName;
	}

	/**
	 * @return ������� ������������
	 */
	public String getSurname()
	{
		return surname;
	}

	/**
	 * @return ���� �������� ������������
	 */
	public Calendar getBirthDate()
	{
		return birthDate;
	}

	/**
	 * @return ��� ������������
	 */
	public String getPassport()
	{
		return passport;
	}

	/**
	 * @return �� ������������
	 */
	public String getTb()
	{
		return tb;
	}

	/**
	 * @param csaProfileId ������������� ������� � ���
	 */
	public void setCsaProfileId(Long csaProfileId)
	{
		this.csaProfileId = csaProfileId;
	}

	/**
	 * @return ������������� ������� � ���
	 */
	public Long getCsaProfileId()
	{
		return csaProfileId;
	}

	/**
	 * @return UserInfo
	 */
	public UserInfo asUserInfo()
	{
		return new UserInfo(tb.equals("38") ? "99" : tb, firstName, surname, patrName, passport, birthDate);
	}

}
