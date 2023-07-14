package com.rssl.phizic.logging.system;

/**
 * @author Omeliyanchuk
 * @ created 16.02.2011
 * @ $Author$
 * @ $Revision$
 */

/**
 * ������ ����������� �������
 * ��� ��������� ���������� �������� ��������� ������
 */
public enum LogModule
{
	//������� �����������
	Cache("Cache"),
	//���������� ����������
	Scheduler("Scheduler"),
	//����
	Gate("Gate"),
	//����
	Core("Core"),
	//���
	Web("Web");

	private String value;

	LogModule(String value)
	{
		this.value = value;
	}

	public String toValue()
	{
		return value;
	}

	public String toString()
	{
		return value;
	}


	public static LogModule fromValue(String value)
	{
		if (value.equals(Cache.value))
			return Cache;
		if (value.equals(Scheduler.value))
			return Scheduler;
		if (value.equals(Gate.value))
			return Gate;
		if (value.equals(Core.value))
			return Core;
		if (value.equals(Web.value))
			return Web;

		throw new IllegalArgumentException("����������� ��� ������ [" + value + "]");
	}
}
