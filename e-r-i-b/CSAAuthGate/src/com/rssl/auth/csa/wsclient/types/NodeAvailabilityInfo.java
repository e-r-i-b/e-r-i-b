package com.rssl.auth.csa.wsclient.types;

/**
 * @author akrenev
 * @ created 17.09.2014
 * @ $Author$
 * @ $Revision$
 *
 * Информация о состоянии блока
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
}
