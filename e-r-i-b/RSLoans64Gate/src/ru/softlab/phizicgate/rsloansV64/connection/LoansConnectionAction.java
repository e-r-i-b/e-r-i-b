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
	 *  Метод для запуска запроса к БД.
	 *  connection - не закрывать!
	 * @param connection коннект к базе
	 * @return результат действия
	 */
	R run(Connection connection) throws Exception;
}
