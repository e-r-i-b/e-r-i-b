package com.rssl.phizic.gate.csa;

/**
 * @author akrenev
 * @ created 13.02.2014
 * @ $Author$
 * @ $Revision$
 *
 * Профиль клиента ЦСА с полной информацией об узлах в которых работал клиент
 */

public class ProfileWithFullNodeInfo extends Profile
{
	private Long waitMigrationNodeId;

	/**
	 * @return идентификатор узла ожидающего миграции
	 */
	public Long getWaitMigrationNodeId()
	{
		return waitMigrationNodeId;
	}

	/**
	 * задать идентификатор узла ожидающего миграции
	 * @param waitMigrationNodeId идентификатор узла
	 */
	public void setWaitMigrationNodeId(Long waitMigrationNodeId)
	{
		this.waitMigrationNodeId = waitMigrationNodeId;
	}
}
