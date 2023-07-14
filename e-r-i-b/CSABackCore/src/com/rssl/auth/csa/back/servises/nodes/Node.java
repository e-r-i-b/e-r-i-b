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
 * Объектное представление блока
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
	 * @return идентификатор блока
	 */
	public Long getId()
	{
		return id;
	}

	/**
	 * Установить идентификатор блока
	 * @param id идентификатор блока
	 */
	public void setId(Long id)
	{
		this.id = id;
	}

	/**
	 * @return имя блока
	 */
	public String getName()
	{
		return name;
	}

	/**
	 * Установить имя блока
	 * @param name имя блока
	 */
	public void setName(String name)
	{
		this.name = name;
	}

	/**
	 *
	 * @return имя хоста, на который настроены фронтальные цепочки блока
	 */
	public String getHostname()
	{
		return hostname;
	}

	/**
	 * Установить имя хоста
	 * @param hostname имя хоста
	 */
	public void setHostname(String hostname)
	{
		this.hostname = hostname;
	}

	/**
	 * @return имя хоста листенера блока
	 */
	public String getListenerHostname()
	{
		return listenerHostname;
	}

	/**
	 * задать имя хоста листенера блока
	 * @param listenerHostname имя хоста
	 */
	public void setListenerHostname(String listenerHostname)
	{
		this.listenerHostname = listenerHostname;
	}

	/**
	 * @return разрешена ли регистрация новых клиентов в блоке
	 */
	public boolean isNewUsersAllowed()
	{
		return newUsersAllowed;
	}

	/**
	 * @param newUsersAllowed разрешена ли регистрация новых клиентов в блоке
	 */
	public void setNewUsersAllowed(boolean newUsersAllowed)
	{
		this.newUsersAllowed = newUsersAllowed;
	}

	/**
	 * @return разрешено ли обслуживание существующих клиентов. запрет на обслуживание обозначает недоступность блока для существующих клиентов
	 */
	public boolean isExistingUsersAllowed()
	{
		return existingUsersAllowed;
	}

	/**
	 *
	 * @param existingUsersAllowed разрешено ли обслуживание существующих клиентов
	 */
	public void setExistingUsersAllowed(boolean existingUsersAllowed)
	{
		this.existingUsersAllowed = existingUsersAllowed;
	}

	/**
	 * @return разрешено ли обслуживание временных клиентов(клиентов, которые обслуживаются в другом блоке)
	 */
	public boolean isTemporaryUsersAllowed()
	{
		return temporaryUsersAllowed;
	}

	/**
	 * @param temporaryUsersAllowed разрешено ли обслуживание временных клиентов
	 */
	public void setTemporaryUsersAllowed(boolean temporaryUsersAllowed)
	{
		this.temporaryUsersAllowed = temporaryUsersAllowed;
	}

	/**
	 * @return разрешено ли пользователям блока переходить в другие блоки в случае недоступности данного.
	 * Флаг используется только при выключенном значении existingUsersAllowed
	 */
	public boolean isUsersTransferAllowed()
	{
		return usersTransferAllowed;
	}

	/**
	 * @param usersTransferAllowed разрешено ли пользователям блока переходить в другие блоки в случае недоступности данного
	 */
	public void setUsersTransferAllowed(boolean usersTransferAllowed)
	{
		this.usersTransferAllowed = usersTransferAllowed;
	}

	/**
	 * @return доступен ли блок сотруднику
	 */
	public boolean isAdminAvailable()
	{
		return adminAvailable;
	}

	/**
	 * установить флаг доступности блока сотруднику
	 * @param adminAvailable флаг
	 */
	public void setAdminAvailable(boolean adminAvailable)
	{
		this.adminAvailable = adminAvailable;
	}

	/**
	 * @return является ли блок гостевым
	 */
	public boolean isGuestAvailable()
	{
		return guestAvailable;
	}

	/**
	 * установить признак гостевого входа
	 * @param guestAvailable признак
	 */
	public void setGuestAvailable(boolean guestAvailable)
	{
		this.guestAvailable = guestAvailable;
	}
	/**
	 * @return наименование очереди смс-канала блока
	 */
	public String getSmsQueueName()
	{
		return smsQueueName;
	}

	/**
	 * @param smsQueueName наименование очереди смс-канала блока
	 */
	public void setSmsQueueName(String smsQueueName)
	{
		this.smsQueueName = smsQueueName;
	}

	/**
	 * @return наименование фабрики смс-канала блока
	 */
	public String getSmsFactoryName()
	{
		return smsFactoryName;
	}

	/**
	 * @param smsFactoryName наименование фабрики смс-канала блока
	 */
	public void setSmsFactoryName(String smsFactoryName)
	{
		this.smsFactoryName = smsFactoryName;
	}

	/**
	 * @return наименование очереди для ермб-запросов
	 */
	public String getErmbQueueName()
	{
		return ermbQueueName;
	}

	/**
	 * @param ermbQueueName - наименование очереди для ермб-запросов
	 */
	public void setErmbQueueName(String ermbQueueName)
	{
		this.ermbQueueName = ermbQueueName;
	}

	/**
	 * @return наименование очереди для ермб-запросов
	 */
	public String getErmbFactoryName()
	{
		return ermbFactoryName;
	}

	/**
	 * @param ermbFactoryName - наименование очереди для ермб-запросов
	 */
	public void setErmbFactoryName(String ermbFactoryName)
	{
		this.ermbFactoryName = ermbFactoryName;
	}

	/**
	 * @return наименование очереди канала обновления справочников
	 */
	public String getDictionaryQueueName()
	{
		return dictionaryQueueName;
	}

	/**
	 * Установить наименование очереди канала обновления справочников
	 * @param dictionaryQueueName - наименование очереди канала обновления справочников
	 */
	public void setDictionaryQueueName(String dictionaryQueueName)
	{
		this.dictionaryQueueName = dictionaryQueueName;
	}

	/**
	 * @return наименование фабрики канала обновления справочников
	 */
	public String getDictionaryFactoryName()
	{
		return dictionaryFactoryName;
	}

	/**
	 * Установить наименование фабрики канала обновления справочников
	 * @param dictionaryFactoryName - наименование фабрики канала обновления справочников
	 */
	public void setDictionaryFactoryName(String dictionaryFactoryName)
	{
		this.dictionaryFactoryName = dictionaryFactoryName;
	}

	/**
	 * Получить блок по номеру
	 * @param nodeId номер блока
	 * @return блок
	 * @throws Exception
	 */
	public static Node findById(Long nodeId) throws Exception
	{
		return findById(Node.class, nodeId, LockMode.NONE, getInstanceName());
	}

	/**
	 * @return наполняющийся блок
	 * @throws ConfigurationException в случае отсутсвия наполняющегося блока.
	 */
	public static Node getFilling() throws Exception
	{
		Node node = getHibernateExecutor().execute(new HibernateAction<Node>()
		{
			public Node run(Session session) throws Exception
			{
				return (Node) session.getNamedQuery("com.rssl.auth.csa.back.servises.nodes.Node.getFilling")
						.setMaxResults(1) //нас интересует любой из доступных
						.uniqueResult();
			}
		});
		if (node == null)
		{
			throw new ConfigurationException("Не задан блок, принимающий на обслуживание новых клиентов. Вход новых клиентов невозможен.");
		}
		return node;
	}

	/**
	 * @return наполняющийся блок
	 * @throws ConfigurationException в случае отсутсвия наполняющегося блока.
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
			throw new ConfigurationException("Не задан блок, принимающий на обслуживание гостей. Вход гостей клиентов невозможен.");
		}
		return node;
	}

	/**
	 * @return Список блоков, принимающих на обслуживание временных клиентов.
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
	 * @return Список всех блоков
	 */
	public static List<Node> getAll() throws Exception
	{
		return databaseService.find(DetachedCriteria.forClass(Node.class), null, getInstanceName());
	}

	/**
	 * @return наименование очереди для работы с ресурсами из нескольких блоков
	 */
	public String getMultiNodeDataQueueName()
	{
		return multiNodeDataQueueName;
	}

	/**
	 * @param multiNodeDataQueueName - наименование очереди для работы с ресурсами из нескольких блоков
	 */
	public void setMultiNodeDataQueueName(String multiNodeDataQueueName)
	{
		this.multiNodeDataQueueName = multiNodeDataQueueName;
	}

	/**
	 * @return наименование фабрики для работы с ресурсами из нескольких блоков
	 */
	public String getMultiNodeDataFactoryName()
	{
		return multiNodeDataFactoryName;
	}

	/**
	 * @param multiNodeDataFactoryName - наименование фабрики для работы с ресурсами из нескольких блоков
	 */
	public void setMultiNodeDataFactoryName(String multiNodeDataFactoryName)
	{
		this.multiNodeDataFactoryName = multiNodeDataFactoryName;
	}

	/**
	 * @return  наименование очереди  для получения регистраций МБК
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
	 * @return  наименование фабрики  для получения регистраций МБК
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
