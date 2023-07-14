package com.rssl.phizic.csaadmin.business.login;

import com.rssl.phizic.csaadmin.business.access.AccessScheme;
import com.rssl.phizic.security.config.SecurityFactory;
import com.rssl.phizic.security.crypto.CryptoService;

import java.util.Calendar;

/**
 * @author akrenev
 * @ created 27.09.13
 * @ $Author$
 * @ $Revision$
 *
 * �����
 */

public class Login
{
	private Long id;
	private String name;
	private String password;
	private AccessScheme accessScheme;
	private Long nodeId;
	private Calendar lastUpdateDate;
	private boolean deleted;
	private long wrongLoginAttempts;
	private Calendar passwordExpireDate;
	private Calendar lastLogonDate;

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
	 * �����
	 * @return �����
	 */
	public String getName()
	{
		return name;
	}

	/**
	 * ������ �����
	 * @param name �����
	 */
	public void setName(String name)
	{
		this.name = name;
	}

	/**
	 * @return ������
	 */
	public String getPassword()
	{
		return password;
	}

	/**
	 * ������ ����� ������
	 * @param password ������
	 */
	public void setNewPassword(String password)
	{
		CryptoService cryptoService = SecurityFactory.cryptoService();
		this.password = cryptoService.hash(password);
	}


	/**
	 * @return ����� ����
	 */
	public AccessScheme getAccessScheme()
	{
		return accessScheme;
	}

	/**
	 * ������ ����� ����
	 * @param accessScheme ����� ����
	 */
	public void setAccessScheme(AccessScheme accessScheme)
	{
		this.accessScheme = accessScheme;
	}

	/**
	 * @return ������������� �����
	 */
	public Long getNodeId()
	{
		return nodeId;
	}

	/**
	 * ������ ������������� �����
	 * @param nodeId ������������� �����
	 */
	public void setNodeId(Long nodeId)
	{
		this.nodeId = nodeId;
	}

	/**
	 * @return ���� ���������� ����������
	 */
	public Calendar getLastUpdateDate()
	{
		return lastUpdateDate;
	}

	/**
	 * ���������� ���� ���������� ����������
	 * @param lastUpdateDate - ����
	 */
	public void setLastUpdateDate(Calendar lastUpdateDate)
	{
		this.lastUpdateDate = lastUpdateDate;
	}

	/**
	 * @return ������� ����������� ������
	 */
	public boolean isDeleted()
	{
		return deleted;
	}

	/**
	 * ������ ������� ����������� ������
	 * @param deleted ������� ����������� ������
	 */
	public void setDeleted(boolean deleted)
	{
		this.deleted = deleted;
	}

	/**
	 * @return ���������� �������� ������� �����
	 */
	public long getWrongLoginAttempts()
	{
		return wrongLoginAttempts;
	}

	/**
	 * @param wrongLoginAttempts - ���������� �������� ������� �����
	 */
	public void setWrongLoginAttempts(long wrongLoginAttempts)
	{
		this.wrongLoginAttempts = wrongLoginAttempts;
	}

	/**
	 * ����������� ���������� �������� ������� ����� �� 1
	 */
	public void incWrongLoginAttempts()
	{
		this.wrongLoginAttempts++;
	}

	/**
	 * @return ���� ����������� ������
	 */
	public Calendar getPasswordExpireDate()
	{
		return passwordExpireDate;
	}

	/**
	 * @param passwordExpireDate - ���� ����������� ������
	 */
	public void setPasswordExpireDate(Calendar passwordExpireDate)
	{
		this.passwordExpireDate = passwordExpireDate;
	}

	/**
	 * @return ���������� �� ������� ������ (true - ����������)
	 */
	public boolean needChangePassword()
	{
		return !Calendar.getInstance().before(passwordExpireDate);
	}

	/**
	 * ��������� ���� ���������� ����� �� ������
	 * @param lastLogonDate ���� ���������� �����
	 */
	public void setLastLogonDate(Calendar lastLogonDate)
	{
		this.lastLogonDate = lastLogonDate;
	}

	/**
	 * @return ���� ���������� �����
	 */
	public Calendar getLastLogonDate()
	{
		return lastLogonDate;
	}
}
