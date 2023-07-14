package com.rssl.auth.csa.back.servises.nodes;

import com.rssl.auth.csa.back.servises.ActiveRecord;
import com.rssl.phizic.config.ConfigurationException;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import org.hibernate.LockMode;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;

import java.util.List;

/**
 * @author krenev
 * @ created 21.08.2013
 * @ $Author$
 * @ $Revision$
 * ��������� ������������� �����
 */

public class Node extends ActiveRecord
{
	private Long id;
	private String name;
	private String hostname;
	private String listenerHostname;
	private boolean newUsersAllowed;
	private boolean existingUsersAllowed;
	private boolean temporaryUsersAllowed;
	private boolean usersTransferAllowed;
	private boolean adminAvailable;
	private boolean guestAvailable;
	private String smsQueueName;
	private String smsFactoryName;
	private String ermbQueueName;
	private String ermbFactoryName;
	private String dictionaryQueueName;
	private String dictionaryFactoryName;
	private String multiNodeDataQueueName;
	private String multiNodeDataFactoryName;
	private String mbkRegistrationQueueName;
	private String mbkRegistrationFactoryName;

	/**
	 * @return ������������� �����
	 */
	public Long getId()
	{
		return id;
	}

	/**
	 * ���������� ������������� �����
	 * @param id ������������� �����
	 */
	public void setId(Long id)
	{
		this.id = id;
	}

	/**
	 * @return ��� �����
	 */
	public String getName()
	{
		return name;
	}

	/**
	 * ���������� ��� �����
	 * @param name ��� �����
	 */
	public void setName(String name)
	{
		this.name = name;
	}

	/**
	 *
	 * @return ��� �����, �� ������� ��������� ����������� ������� �����
	 */
	public String getHostname()
	{
		return hostname;
	}

	/**
	 * ���������� ��� �����
	 * @param hostname ��� �����
	 */
	public void setHostname(String hostname)
	{
		this.hostname = hostname;
	}

	/**
	 * @return ��� ����� ��������� �����
	 */
	public String getListenerHostname()
	{
		return listenerHostname;
	}

	/**
	 * ������ ��� ����� ��������� �����
	 * @param listenerHostname ��� �����
	 */
	public void setListenerHostname(String listenerHostname)
	{
		this.listenerHostname = listenerHostname;
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
	/**
	 * @return ������������ ������� ���-������ �����
	 */
	public String getSmsQueueName()
	{
		return smsQueueName;
	}

	/**
	 * @param smsQueueName ������������ ������� ���-������ �����
	 */
	public void setSmsQueueName(String smsQueueName)
	{
		this.smsQueueName = smsQueueName;
	}

	/**
	 * @return ������������ ������� ���-������ �����
	 */
	public String getSmsFactoryName()
	{
		return smsFactoryName;
	}

	/**
	 * @param smsFactoryName ������������ ������� ���-������ �����
	 */
	public void setSmsFactoryName(String smsFactoryName)
	{
		this.smsFactoryName = smsFactoryName;
	}

	/**
	 * @return ������������ ������� ��� ����-��������
	 */
	public String getErmbQueueName()
	{
		return ermbQueueName;
	}

	/**
	 * @param ermbQueueName - ������������ ������� ��� ����-��������
	 */
	public void setErmbQueueName(String ermbQueueName)
	{
		this.ermbQueueName = ermbQueueName;
	}

	/**
	 * @return ������������ ������� ��� ����-��������
	 */
	public String getErmbFactoryName()
	{
		return ermbFactoryName;
	}

	/**
	 * @param ermbFactoryName - ������������ ������� ��� ����-��������
	 */
	public void setErmbFactoryName(String ermbFactoryName)
	{
		this.ermbFactoryName = ermbFactoryName;
	}

	/**
	 * @return ������������ ������� ������ ���������� ������������
	 */
	public String getDictionaryQueueName()
	{
		return dictionaryQueueName;
	}

	/**
	 * ���������� ������������ ������� ������ ���������� ������������
	 * @param dictionaryQueueName - ������������ ������� ������ ���������� ������������
	 */
	public void setDictionaryQueueName(String dictionaryQueueName)
	{
		this.dictionaryQueueName = dictionaryQueueName;
	}

	/**
	 * @return ������������ ������� ������ ���������� ������������
	 */
	public String getDictionaryFactoryName()
	{
		return dictionaryFactoryName;
	}

	/**
	 * ���������� ������������ ������� ������ ���������� ������������
	 * @param dictionaryFactoryName - ������������ ������� ������ ���������� ������������
	 */
	public void setDictionaryFactoryName(String dictionaryFactoryName)
	{
		this.dictionaryFactoryName = dictionaryFactoryName;
	}

	/**
	 * �������� ���� �� ������
	 * @param nodeId ����� �����
	 * @return ����
	 * @throws Exception
	 */
	public static Node findById(Long nodeId) throws Exception
	{
		return findById(Node.class, nodeId, LockMode.NONE, getInstanceName());
	}

	/**
	 * @return ������������� ����
	 * @throws ConfigurationException � ������ ��������� �������������� �����.
	 */
	public static Node getFilling() throws Exception
	{
		Node node = getHibernateExecutor().execute(new HibernateAction<Node>()
		{
			public Node run(Session session) throws Exception
			{
				return (Node) session.getNamedQuery("com.rssl.auth.csa.back.servises.nodes.Node.getFilling")
						.setMaxResults(1) //��� ���������� ����� �� ���������
						.uniqueResult();
			}
		});
		if (node == null)
		{
			throw new ConfigurationException("�� ����� ����, ����������� �� ������������ ����� ��������. ���� ����� �������� ����������.");
		}
		return node;
	}

	/**
	 * @return ������������� ����
	 * @throws ConfigurationException � ������ ��������� �������������� �����.
	 */
	public static Node getFillingGuest() throws Exception
	{
		Node node = getHibernateExecutor().execute(new HibernateAction<Node>()
		{
			public Node run(Session session) throws Exception
			{
				return (Node) session.getNamedQuery("com.rssl.auth.csa.back.servises.nodes.Node.getFillingGuest")
						.setMaxResults(1)
						.uniqueResult();
			}
		});
		if (node == null)
		{
			throw new ConfigurationException("�� ����� ����, ����������� �� ������������ ������. ���� ������ �������� ����������.");
		}
		return node;
	}

	/**
	 * @return ������ ������, ����������� �� ������������ ��������� ��������.
	 */
	public static List<Node> getTemporaryNodes() throws Exception
	{
		return getHibernateExecutor().execute(new HibernateAction<List<Node>>()
		{
			public List<Node> run(Session session) throws Exception
			{
				return (List<Node>) session.getNamedQuery("com.rssl.auth.csa.back.servises.nodes.Node.getTemporary")
						.list();
			}
		});
	}

	/**
	 * @return ������ ���� ������
	 */
	public static List<Node> getAll() throws Exception
	{
		return databaseService.find(DetachedCriteria.forClass(Node.class), null, getInstanceName());
	}

	/**
	 * @return ������������ ������� ��� ������ � ��������� �� ���������� ������
	 */
	public String getMultiNodeDataQueueName()
	{
		return multiNodeDataQueueName;
	}

	/**
	 * @param multiNodeDataQueueName - ������������ ������� ��� ������ � ��������� �� ���������� ������
	 */
	public void setMultiNodeDataQueueName(String multiNodeDataQueueName)
	{
		this.multiNodeDataQueueName = multiNodeDataQueueName;
	}

	/**
	 * @return ������������ ������� ��� ������ � ��������� �� ���������� ������
	 */
	public String getMultiNodeDataFactoryName()
	{
		return multiNodeDataFactoryName;
	}

	/**
	 * @param multiNodeDataFactoryName - ������������ ������� ��� ������ � ��������� �� ���������� ������
	 */
	public void setMultiNodeDataFactoryName(String multiNodeDataFactoryName)
	{
		this.multiNodeDataFactoryName = multiNodeDataFactoryName;
	}

	/**
	 * @return  ������������ �������  ��� ��������� ����������� ���
	 */
	public String getMbkRegistrationQueueName()
	{
		return mbkRegistrationQueueName;
	}

	public void setMbkRegistrationQueueName(String mbkRegistrationQueueName)
	{
		this.mbkRegistrationQueueName = mbkRegistrationQueueName;
	}

	/**
	 * @return  ������������ �������  ��� ��������� ����������� ���
	 */
	public String getMbkRegistrationFactoryName()
	{
		return mbkRegistrationFactoryName;
	}

	public void setMbkRegistrationFactoryName(String mbkRegistrationFactoryName)
	{
		this.mbkRegistrationFactoryName = mbkRegistrationFactoryName;
	}
}
