package com.rssl.phizic.csaadmin.business.authtoken;

import com.rssl.phizic.csaadmin.business.session.Session;
import com.rssl.phizic.utils.MapUtil;
import org.apache.commons.collections.MapUtils;

import java.util.Calendar;
import java.util.Map;

/**
 * @author mihaylov
 * @ created 27.10.13
 * @ $Author$
 * @ $Revision$
 * Токен аутентификации сотрудника
 */
public class AuthenticationToken
{
	private static final String PARAMETERS_ENTRY_DELIMETER = "&";
	private static final String PAREMETERS_VALUE_DELIMETER = "=";

	private String id; //идентификатор
	private Session session; //сессия сотрудника
	private AuthenticationTokenState state; //статус токена
	private Calendar creationDate; //дата создания токена
	private String action; //экшен перехода в блоке
	private String parameters; //параметры перехода в блоке

	/**
	 * Пустой конструктор, для хибернета
	 */
	public AuthenticationToken()
	{
	}

	/**
	 * Конструктор по сессии сотрудника
	 * @param session - сессия сотрудника
	 */
	public AuthenticationToken(Session session, String action, Map<String,String> parameters)
	{
		this.session = session;
		this.state = AuthenticationTokenState.ACTIVE;
		this.creationDate = Calendar.getInstance();
		this.action = action;
		if(MapUtils.isNotEmpty(parameters))
		{
			this.parameters = MapUtil.format(parameters, PAREMETERS_VALUE_DELIMETER, PARAMETERS_ENTRY_DELIMETER);
		}
	}

	/**
	 * @return идентификатор токена
	 */
	public String getId()
	{
		return id;
	}

	/**
	 * Установить идентификатор токена
	 * @param id - идентификатор
	 */
	public void setId(String id)
	{
		this.id = id;
	}

	/**
	 * @return сессия сотрудника
	 */
	public Session getSession()
	{
		return session;
	}

	/**
	 * Установить сессию сотрудника
	 * @param session - сессия
	 */
	public void setSession(Session session)
	{
		this.session = session;
	}

	/**
	 * @return статус токена
	 */
	public AuthenticationTokenState getState()
	{
		return state;
	}

	/**
	 * Установить статус токена
	 * @param state - статус
	 */
	public void setState(AuthenticationTokenState state)
	{
		this.state = state;
	}

	/**
	 * @return дата создания токена
	 */
	public Calendar getCreationDate()
	{
		return creationDate;
	}

	/**
	 * Установить дату создания токена
	 * @param creationDate - дата создания токена
	 */
	public void setCreationDate(Calendar creationDate)
	{
		this.creationDate = creationDate;
	}

	/**
	 * @return экшен перехода в блоке
	 */
	public String getAction()
	{
		return action;
	}

	/**
	 * Установить экшен перехода в блоке
	 * @param action - экшен
	 */
	public void setAction(String action)
	{
		this.action = action;
	}

	/**
	 * @return параметры экшена перехода в блоке
	 */
	public Map<String,String> getParameters()
	{
		return MapUtil.parse(parameters, PAREMETERS_VALUE_DELIMETER, PARAMETERS_ENTRY_DELIMETER);
	}

	/**
	 * Установить параметры экшена
	 * @param parameters - параметры
	 */
	public void setParameters(Map<String,String> parameters)
	{
		if(MapUtils.isNotEmpty(parameters))
		{
			this.parameters = MapUtil.format(parameters, PAREMETERS_VALUE_DELIMETER, PARAMETERS_ENTRY_DELIMETER);
		}
	}
}
