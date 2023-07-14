package ru.softlab.phizicgate.rsloansV64.connection;

import java.sql.Connection;

/**
 * @author Omeliyanchuk
 * @ created 07.12.2007
 * @ $Author$
 * @ $Revision$
 */

public interface LoansConnectionAction<R>
{
	/**
	 *  ����� ��� ������� ������� � ��.
	 *  connection - �� ���������!
	 * @param connection ������� � ����
	 * @return ��������� ��������
	 */
	R run(Connection connection) throws Exception;
}
