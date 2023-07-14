package com.rssl.phizic.gate.csa;

/**
 * @author osminin
 * @ created 25.09.13
 * @ $Author$
 * @ $Revision$
 *
 * ���������� �� ����
 */
public class NodeInfo
{
	private Long id;
	private String name;
	private boolean newUsersAllowed;
	private boolean existingUsersAllowed;
	private boolean temporaryUsersAllowed;
	private boolean usersTransferAllowed;
	private boolean adminAvailable;
	private boolean guestAvailable;
	private String hostname;
	private MQInfo smsMQ;                      // ������� ��������� ���-������
	private MQInfo dictionaryMQ;               // ������� ��������� ������ ���������� ������������
	private MQInfo multiNodeDataMQ;            // ������� ��������� ��� ��������� �������� �� ���������� ������
	private MQInfo mbkRegistrationMQ;          // ������� ��������� ��� ��������� ������  MBK
	private MQInfo ermbQueueMQ;                  // ������� ��������� ��� ��������� ����
													// (��������� IMSI,
													// ������������� ���������� �������,
													// ���������� ������ �������,
													// ���������� ��������� �������,
													// ���������� �������� ����������� �����)
	private String listenerHost;               // ���� ��������� ���-������� �����

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

	/**
	 * @return �������� �� ���� ����������
	 */
	public boolean isAdminAvailable()
	{
		return adminAvailable;
	}

	/**
	 * ���������� ���� ����������� ����� ����������
	 * @param adminAvailable ����
	 */
	public void setAdminAvailable(boolean adminAvailable)
	{
		this.adminAvailable = adminAvailable;
	}

	/**
	 * @return �������� �� ���� ��������
	 */
	public boolean isGuestAvailable()
	{
		return guestAvailable;
	}

	/**
	 * ���������� ������� ��������� �����
	 * @param guestAvailable �������
	 */
	public void setGuestAvailable(boolean guestAvailable)
	{
		this.guestAvailable = guestAvailable;
	}

	public String getHostname()
	{
		return hostname;
	}

	public void setHostname(String hostname)
	{
		this.hostname = hostname;
	}

	public MQInfo getSmsMQ()
	{
		return smsMQ;
	}

	public void setSmsMQ(MQInfo smsMQ)
	{
		this.smsMQ = smsMQ;
	}

	public MQInfo getDictionaryMQ()
	{
		return dictionaryMQ;
	}

	public void setDictionaryMQ(MQInfo dictionaryMQ)
	{
		this.dictionaryMQ = dictionaryMQ;
	}

	/**
	 * @return ������� ��������� ��� ��������� �������� �� ���������� ������
	 */
	public MQInfo getMultiNodeDataMQ()
	{
		return multiNodeDataMQ;
	}

	/**
	 * ������ ������� ��������� ��� ��������� �������� �� ���������� ������
	 * @param multiNodeDataMQ �������� �������
	 */
	public void setMultiNodeDataMQ(MQInfo multiNodeDataMQ)
	{
		this.multiNodeDataMQ = multiNodeDataMQ;
	}

	public MQInfo getMbkRegistrationMQ()
	{
		return mbkRegistrationMQ;
	}

	public void setMbkRegistrationMQ(MQInfo mbkRegistrationMQ)
	{
		this.mbkRegistrationMQ = mbkRegistrationMQ;
	}

	public String getListenerHost()
	{
		return listenerHost;
	}

	public void setListenerHost(String listenerHost)
	{
		this.listenerHost = listenerHost;
	}

	public MQInfo getErmbQueueMQ()
	{
		return ermbQueueMQ;
	}

	public void setErmbQueueMQ(MQInfo ermbQueueMQ)
	{
		this.ermbQueueMQ = ermbQueueMQ;
	}
}
