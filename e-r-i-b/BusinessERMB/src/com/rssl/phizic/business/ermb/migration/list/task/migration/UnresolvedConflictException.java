package com.rssl.phizic.business.ermb.migration.list.task.migration;

/**
 * ���������� ��� ������������� �������� ��-�� ������������� ����������
 * @author Puzikov
 * @ created 03.03.14
 * @ $Author$
 * @ $Revision$
 */

class UnresolvedConflictException extends MigrationLogicException
{
	/**
	 * ctor
	 * @param s message
	 */
	UnresolvedConflictException(String s)
	{
		super(s);
	}
}
