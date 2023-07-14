package com.rssl.phizic.business.ermb.migration.list.entity.migrator;

import com.rssl.phizic.common.types.UUID;

import java.util.Calendar;

/**
 * Информация о миграции клиента
 * @author Puzikov
 * @ created 26.02.14
 * @ $Author$
 * @ $Revision$
 */

@SuppressWarnings("JavaDoc")
public class MigrationInfo
{
	private Long id;
	private Client client;
	private Long mbkMigrationId;
	private UUID mbvMigrationId;
	private Calendar migrationDate;
	private boolean rollbackError;

	public Calendar getMigrationDate()
	{
		return migrationDate;
	}

	public void setMigrationDate(Calendar migrationDate)
	{
		this.migrationDate = migrationDate;
	}

	public Long getMbkMigrationId()
	{
		return mbkMigrationId;
	}

	public void setMbkMigrationId(Long mbkMigrationId)
	{
		this.mbkMigrationId = mbkMigrationId;
	}

	public UUID getMbvMigrationId()
	{
		return mbvMigrationId;
	}

	public void setMbvMigrationId(UUID mbvMigrationId)
	{
		this.mbvMigrationId = mbvMigrationId;
	}

	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public boolean isRollbackError()
	{
		return rollbackError;
	}

	public void setRollbackError(boolean rollbackError)
	{
		this.rollbackError = rollbackError;
	}

	public Client getClient()
	{
		return client;
	}

	public void setClient(Client client)
	{
		this.client = client;
	}
}
