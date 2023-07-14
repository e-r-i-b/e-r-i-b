package ru.softlab.phizicgate.rsloansV64.connection;

import com.rssl.phizic.gate.exceptions.GateException;

import java.sql.Connection;
import java.sql.SQLException;
import javax.sql.DataSource;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

/**
 * @author Omeliyanchuk
 * @ created 07.12.2007
 * @ $Author$
 * @ $Revision$
 */

public class LoansConnectionFactory
{
	private static DataSource source = null;
	private static final String JDBC_RSLOANS6 = "jdbc/RSLoans6";

	private static void initialize() throws GateException
	{
		try
		{
			Context initCtx=new InitialContext();
			source =(DataSource)initCtx.lookup(JDBC_RSLOANS6);
		}
		catch(NamingException ex)
		{
			throw new GateException("Не удается найти соединение с RS-Loans 6.4",ex);
		}
	}

	/**
	 * получить соединение с БД RS Loans
	 * @return содениение с БД Loans
	 * @throws GateException проблема при поиске Data Source или при получении конекта.
	 */
	public static Connection getConnection() throws GateException
	{
		if(source == null)
			initialize();
		try
		{
			//todo предполагаем, что data source с пулингом, надо бы сделать проверку на это
			//считаем, что data source может работать multithread
			return source.getConnection();
		}		
		catch(SQLException ex)
		{
			throw new GateException("Не удается установить соединение с RS-Loans 6.4",ex);
		}
	}

}
