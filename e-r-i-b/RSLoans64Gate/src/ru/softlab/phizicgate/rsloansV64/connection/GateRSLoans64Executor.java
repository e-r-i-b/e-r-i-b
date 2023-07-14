package ru.softlab.phizicgate.rsloansV64.connection;

import java.sql.Connection;

/**
 * @author Omeliyanchuk
 * @ created 07.12.2007
 * @ $Author$
 * @ $Revision$
 */

public final class GateRSLoans64Executor
{
	private boolean readOnly;

	/**
	 * Создание
	 * @param readOnly необходим ли коммит транзакции? (readOnly==true - коммит не нужен.)
	 */
	private GateRSLoans64Executor(boolean readOnly)
	{
		this.readOnly = readOnly;
	}

	public static GateRSLoans64Executor getInstance(boolean readOnly)
	{
		return new GateRSLoans64Executor(readOnly);
	}

    public <T> T execute(LoansConnectionAction<T> action) throws Exception
    {
	    boolean exception = false;
	    Connection conection = LoansConnectionFactory.getConnection();
	    try
	    {
			return action.run(conection);
	    }
	    catch(Exception ex)
	    {
		    exception = true;
		    throw ex;
	    }
	    finally
	    {
		    if(!readOnly && !exception)
				conection.commit();

			conection.close();
	    }
    }
}
