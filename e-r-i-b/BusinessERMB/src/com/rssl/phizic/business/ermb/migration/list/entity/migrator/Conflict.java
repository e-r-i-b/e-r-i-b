package com.rssl.phizic.business.ermb.migration.list.entity.migrator;

import java.util.HashSet;
import java.util.Set;

import static com.rssl.phizic.business.ermb.migration.list.entity.migrator.ConflictStatus.UNRESOLVED;

/**
 * Конфликт по телефону в контексте списковой миграции
 * @author Puzikov
 * @ created 31.01.14
 * @ $Author$
 * @ $Revision$
 */

public class Conflict
{
	private String phone;                           //номер телефона, по которому несколько клиентов
	private ConflictStatus status = UNRESOLVED;     //статус
	private Long ownerId;                           //клиент, в чью пользу разрешен конфликт
	private Set<ConflictedClient> clients = new HashSet<ConflictedClient>();          //набор конфликтующих клиентов
	private boolean manually;                       //разрешфется ли конфликт вручную
	private String employeeInfo;                    //сотрудник, разрешивший конфликт вручную

	public String getPhone()
	{
		return phone;
	}

	public void setPhone(String phone)
	{
		this.phone = phone;
	}

	public ConflictStatus getStatus()
	{
		return status;
	}

	public void setStatus(ConflictStatus status)
	{
		this.status = status;
	}

	public Long getOwnerId()
	{
		return ownerId;
	}

	public void setOwnerId(Long ownerId)
	{
		this.ownerId = ownerId;
	}

	public Set<ConflictedClient> getClients()
	{
		return clients;
	}

	public void setClients(Set<ConflictedClient> clients)
	{
		this.clients = clients;
	}

	public boolean isManually()
	{
		return manually;
	}

	public void setManually(boolean manually)
	{
		this.manually = manually;
	}

	public String getEmployeeInfo()
	{
		return employeeInfo;
	}

	public void setEmployeeInfo(String employeeInfo)
	{
		this.employeeInfo = employeeInfo;
	}

	public MigrationClient getOwner()
	{
		if (ownerId == null)
		{
			return null;
		}
		else
		{
			for (ConflictedClient client : clients)
			{
				if (ownerId.equals(client.getId()))
					return client;
			}

			throw new IllegalStateException("Неконсистентный конфликт: по id владельца не найден владелец.");
		}

	}
}
