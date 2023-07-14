package com.rssl.auth.csa.back.servises.operations;

import com.rssl.auth.csa.back.servises.Session;

/**
 * @author krenev
 * @ created 17.10.2012
 * @ $Author$
 * @ $Revision$
 */
public class CheckSessionOperation extends SessionContextOperation
{
	public CheckSessionOperation() {}

	public CheckSessionOperation(IdentificationContext identificationContext)
	{
		super(identificationContext);
	}

	/**
	 * ѕроинициализировать операцию
	 * @param sid »дентфикатор сессии
	 */
	public void initialize(String sid) throws Exception
	{
		setSid(sid);
		//сохран€ть в базе не будем - мусор.
	}

	/**
	 * проверить сесиию на активность(пинг)
	 * @return проверенна€
	 */
	public Session execute() throws Exception
	{
		return getSession();
	}
}
