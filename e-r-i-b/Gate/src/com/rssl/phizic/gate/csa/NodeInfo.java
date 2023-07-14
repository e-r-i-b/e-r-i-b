package com.rssl.phizic.gate.csa;

/**
 * @author osminin
 * @ created 25.09.13
 * @ $Author$
 * @ $Revision$
 *
 * Информация об узле
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
	private MQInfo smsMQ;                      // очередь сообщений смс-канала
	private MQInfo dictionaryMQ;               // очередь сообщений канала обновлений справочников
	private MQInfo multiNodeDataMQ;            // очередь сообщений для получения ресурсов из нескольких блоков
	private MQInfo mbkRegistrationMQ;          // очередь сообщений для получения связки  MBK
	private MQInfo ermbQueueMQ;                  // очередь сообщений для сообщений ЕРМБ
													// (проверкой IMSI,
													// подтверждения обновления профиля,
													// обновления данных клиента,
													// обновления продуктов клиента,
													// результата списания абонентской платы)
	private String listenerHost;               // хост листенера вэб-сервиса блока

	/**
	 * @return идентификатор блока
	 */
	public Long getId()
	{
		return id;
	}

	/**
	 * задать идентификатор блока
	 * @param id идентификатор
	 */
	public void setId(Long id)
	{
		this.id = id;
	}

	/**
	 * @return название блока
	 */
	public String getName()
	{
		return name;
	}

	/**
	 * задать название блока
	 * @param name название
	 */
	public void setName(String name)
	{
		this.name = name;
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
	 * @return очередь сообщений для получения ресурсов из нескольких блоков
	 */
	public MQInfo getMultiNodeDataMQ()
	{
		return multiNodeDataMQ;
	}

	/**
	 * задать очередь сообщений для получения ресурсов из нескольких блоков
	 * @param multiNodeDataMQ описание очереди
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
