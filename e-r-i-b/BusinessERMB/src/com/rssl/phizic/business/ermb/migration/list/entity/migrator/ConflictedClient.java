package com.rssl.phizic.business.ermb.migration.list.entity.migrator;

/**
 * Конфликтующий по телефону клиент.
 * @author Puzikov
 * @ created 31.01.14
 * @ $Author$
 * @ $Revision$
 */

public class ConflictedClient extends MigrationClient
{
	private boolean isSmsActive;
	private boolean cardActivity;

	public boolean isSmsActive()
	{
		return isSmsActive;
	}

	public void setSmsActive(boolean smsActive)
	{
		isSmsActive = smsActive;
	}

	public boolean isCardActivity()
	{
		return cardActivity;
	}

	public void setCardActivity(boolean cardActivity)
	{
		this.cardActivity = cardActivity;
	}
}
