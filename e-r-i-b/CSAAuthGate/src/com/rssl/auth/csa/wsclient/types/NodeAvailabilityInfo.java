package com.rssl.auth.csa.wsclient.types;

/**
 * @author akrenev
 * @ created 17.09.2014
 * @ $Author$
 * @ $Revision$
 *
 * ���������� � ��������� �����
 */

public class NodeAvailabilityInfo
{
	private Long id;
	private String name;
	private boolean newUsersAllowed;
	private boolean existingUsersAllowed;
	private boolean temporaryUsersAllowed;
	private boolean usersTransferAllowed;

	/**
	 * @return ������������� �����
	 */
	public Long getId()
	{
		return id;
	}

	/**
	 * ������ ������������� �����
	 * @param id �������������
	 */
	public void setId(Long id)
	{
		this.id = id;
	}

	/**
	 * @return �������� �����
	 */
	public String getName()
	{
		return name;
	}

	/**
	 * ������ �������� �����
	 * @param name ��������
	 */
	public void setName(String name)
	{
		this.name = name;
	}

	/**
	 * @return ��������� �� ����������� ����� �������� � �����
	 */
	public boolean isNewUsersAllowed()
	{
		return newUsersAllowed;
	}

	/**
	 * @param newUsersAllowed ��������� �� ����������� ����� �������� � �����
	 */
	public void setNewUsersAllowed(boolean newUsersAllowed)
	{
		this.newUsersAllowed = newUsersAllowed;
	}

	/**
	 * @return ��������� �� ������������ ������������ ��������. ������ �� ������������ ���������� ������������� ����� ��� ������������ ��������
	 */
	public boolean isExistingUsersAllowed()
	{
		return existingUsersAllowed;
	}

	/**
	 *
	 * @param existingUsersAllowed ��������� �� ������������ ������������ ��������
	 */
	public void setExistingUsersAllowed(boolean existingUsersAllowed)
	{
		this.existingUsersAllowed = existingUsersAllowed;
	}

	/**
	 * @return ��������� �� ������������ ��������� ��������(��������, ������� ������������� � ������ �����)
	 */
	public boolean isTemporaryUsersAllowed()
	{
		return temporaryUsersAllowed;
	}

	/**
	 * @param temporaryUsersAllowed ��������� �� ������������ ��������� ��������
	 */
	public void setTemporaryUsersAllowed(boolean temporaryUsersAllowed)
	{
		this.temporaryUsersAllowed = temporaryUsersAllowed;
	}

	/**
	 * @return ��������� �� ������������� ����� ���������� � ������ ����� � ������ ������������� �������.
	 * ���� ������������ ������ ��� ����������� �������� existingUsersAllowed
	 */
	public boolean isUsersTransferAllowed()
	{
		return usersTransferAllowed;
	}

	/**
	 * @param usersTransferAllowed ��������� �� ������������� ����� ���������� � ������ ����� � ������ ������������� �������
	 */
	public void setUsersTransferAllowed(boolean usersTransferAllowed)
	{
		this.usersTransferAllowed = usersTransferAllowed;
	}
}
