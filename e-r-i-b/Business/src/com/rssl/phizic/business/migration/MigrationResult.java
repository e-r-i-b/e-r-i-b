package com.rssl.phizic.business.migration;

/**
 * @author akrenev
 * @ created 30.09.2014
 * @ $Author$
 * @ $Revision$
 *
 * ��� ���������� ��������
 */

public enum MigrationResult
{
	MIGRATED(0),
	ERROR(1);

	private final long code;

	private MigrationResult(long code)
	{
		this.code = code;
	}

	/**
	 * �������� ��������� �������� �� ���� ������
	 * @param code ��� ������
	 * @return ��������� ��������
	 */
	public static MigrationResult getFromCode(long code)
	{
		for (MigrationResult result : MigrationResult.values())
		{
			if (code == result.code)
				return result;
		}
		throw new IllegalArgumentException("����������� ��� ������ �������� code = " + code);
	}
}
