package com.rssl.phizic.business.addressBook.reports;

import java.util.Calendar;

/**
 * @author akrenev
 * @ created 11.06.2014
 * @ $Author$
 * @ $Revision$
 *
 * ������� �������� ������ ���� ������ � �������� ������
 */

public abstract class ReportEntityBase
{
	private Long login;
	private String name;
	private String document;
	private Calendar birthday;
	private String tb;

	/**
	 * @return ������������� ������
	 */
	public Long getLogin()
	{
		return login;
	}

	/**
	 * ������ ������������� ������
	 * @param login ������������� ������
	 */
	public void setLogin(Long login)
	{
		this.login = login;
	}

	/**
	 * @return ��� ������������
	 */
	public String getName()
	{
		return name;
	}

	/**
	 * ������ ��� ������������
	 * @param name ��� ������������
	 */
	public void setName(String name)
	{
		this.name = name;
	}

	/**
	 * @return ��� ������������
	 */
	public String getDocument()
	{
		return document;
	}

	/**
	 * ������ ��� ������������
	 * @param document ��� ������������
	 */
	public void setDocument(String document)
	{
		this.document = document;
	}

	/**
	 * @return �� ������������
	 */
	public Calendar getBirthday()
	{
		return birthday;
	}

	/**
	 * ������ �� ������������
	 * @param birthday �� ������������
	 */
	public void setBirthday(Calendar birthday)
	{
		this.birthday = birthday;
	}

	/**
	 * @return �� �������
	 */
	public String getTb()
	{
		return tb;
	}

	/**
	 * ������ �� �������
	 * @param tb ��
	 */
	public void setTb(String tb)
	{
		this.tb = tb;
	}
}
