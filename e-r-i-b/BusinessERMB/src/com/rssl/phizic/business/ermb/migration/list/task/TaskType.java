package com.rssl.phizic.business.ermb.migration.list.task;

import com.rssl.phizic.dataaccess.common.counters.Counter;
import com.rssl.phizic.dataaccess.common.counters.Counters;

/**
 * ��� ������ ��������� ��������
 * @author Puzikov
 * @ created 16.12.13
 * @ $Author$
 * @ $Revision$
 */

public enum TaskType
{
	LOAD_CLIENTS("Load", "�������� ������ ��������", Counters.ERMB_MIGRATION_LOAD_LOG_NUMBER),
	START_MIGRATION("Migration", "������ ��������", Counters.ERMB_MIGRATION_MIGRATE_LOG_NUMBER),
	ROLLBACK_MIGRATION("Revert", "����� ��������", Counters.ERMB_MIGRATION_REVERT_LOG_NUMBER),
	SMS_BROADCAST("Sms", "�������� ���", null);

	private final String value;
	private final String description;
	private final Counter counter;

	private TaskType(String value, String description, Counter counter)
	{
		this.value = value;
		this.description = description;
		this.counter = counter;
	}

	public String getValue()
	{
		return value;
	}

	public String getDescription()
	{
		return description;
	}

	public Counter getCounter()
	{
		return counter;
	}
}
