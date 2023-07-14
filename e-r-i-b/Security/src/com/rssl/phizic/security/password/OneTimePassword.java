package com.rssl.phizic.security.password;

import java.util.Calendar;

/**
 * Created by IntelliJ IDEA.
 * User: Evgrafov
 * Date: 01.09.2005
 * Time: 21:17:33
 *
 * ����������� ������
 */
public class OneTimePassword
{
	private final String hash;
	private final Calendar expireDate;
	private int   wrongAttempts;
	private final String entityType;
	private final Long entityId;

	/**
	 * �����������
	 * @param expireDate ���� ��������� �������� ������
	 * @param hash       ��� ������
	 * @param entityType ��� �������������� ��������
	 * @param entityId   ������������� �������������� ��������
	 */
	public OneTimePassword(Calendar expireDate, String hash, String entityType, Long entityId)
	{
		this.expireDate = expireDate;
		this.hash    = hash;
		this.wrongAttempts = 0;
		this.entityType = entityType;
		this.entityId = entityId;
	}

	/**
	 * @return ���� ��������� �������� ������
	 */
	public Calendar getExpireDate()
	{
		return expireDate;
	}

	/**
	 * @return ��� ������
	 */
	public String getHash()
	{
		return hash;
	}

	/**
	 * @return ���������� ������
	 */
	public int getWrongAttempts()
	{
		return wrongAttempts;
	}

	/**
	 * ��������� ���������� ������
	 */
	public void incWrongAttempts()
	{
		wrongAttempts++;
	}

	/**
	 * @return ��� �������������� ��������
	 */
	public String getEntityType()
	{
		return entityType;
	}

	/**
	 * @return ������������� �������������� ��������
	 */
	public Long getEntityId()
	{
		return entityId;
	}
}
