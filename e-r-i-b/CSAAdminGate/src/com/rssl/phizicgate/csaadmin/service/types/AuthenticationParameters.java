package com.rssl.phizicgate.csaadmin.service.types;

import java.util.Calendar;
import java.util.Map;

/**
 * @author mihaylov
 * @ created 21.10.13
 * @ $Author$
 * @ $Revision$
 *
 * Параметры аутентификации в ЦСА Админ
 */
public class AuthenticationParameters
{
	private String loginId;//идентификатор логина пользователя в ЦСА Админ
	private Calendar lastEmployeeUpdateDate;//дата последнего обновления информации по сотруднику
	private String sessionId; //идентификатор сессии в ЦСА Админ
	private String action; //экшен перехода
	private Map<String,String> parameters;//параметры в экшене перехода

	/**
	 * @return идентификатор логина пользователя
	 */
	public String getLoginId()
	{
		return loginId;
	}

	/**
	 * Установить идентификатор логина пользователя
	 * @param loginId - идентификатор логина пользователя
	 */
	public void setLoginId(String loginId)
	{
		this.loginId = loginId;
	}

	/**
	 * @return дата последнего обновления информации по сотруднику
	 */
	public Calendar getLastEmployeeUpdateDate()
	{
		return lastEmployeeUpdateDate;
	}

	/**
	 * Установить дату последнего обновления информации
	 * @param lastEmployeeUpdateDate дата последнего обновления информации по сотруднику
	 */
	public void setLastEmployeeUpdateDate(Calendar lastEmployeeUpdateDate)
	{
		this.lastEmployeeUpdateDate = lastEmployeeUpdateDate;
	}

	/**
	 * @return идентификатор сессии в Цса Админ
	 */
	public String getSessionId()
	{
		return sessionId;
	}

	/**
	 * Установить идентификатор сессии в ЦСА Админ
	 * @param sessionId - идентификатор сессии
	 */
	public void setSessionId(String sessionId)
	{
		this.sessionId = sessionId;
	}

	/**
	 * @return экшен перехода
	 */
	public String getAction()
	{
		return action;
	}

	/**
	 * Установить экшен перехода
	 * @param action - экшен
	 */
	public void setAction(String action)
	{
		this.action = action;
	}

	/**
	 * @return параметры экшена перехода
	 */
	public Map<String, String> getParameters()
	{
		return parameters;
	}

	/**
	 * Установить параметры экшена перехода
	 * @param parameters - параметры
	 */
	public void setParameters(Map<String, String> parameters)
	{
		this.parameters = parameters;
	}
}
