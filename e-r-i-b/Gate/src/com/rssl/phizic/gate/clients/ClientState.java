package com.rssl.phizic.gate.clients;

import java.util.HashMap;
import java.util.Map;
import java.io.Serializable;

/**
 * @author Omeliyanchuk
 * @ created 07.06.2008
 * @ $Author$
 * @ $Revision$
 */

public class ClientState implements Serializable
{
	/** мап хранения всех состояний клиента по кодам */
	private static final Map<String, ClientState> statesByCode = new HashMap<String, ClientState>();

	/** Удачно расторгнут */
	public static final ClientState SUCCESS    = new ClientState(ClientStateCategory.agreement_dissolve,      "S");

	/** Ошибка при расторжении */
	public static final ClientState ERROR = new ClientState(ClientStateCategory.error_dissolve, "E");

	/** Ошибка при расторжении */
	public static final ClientState SYSTEM_ERROR = new ClientState(ClientStateCategory.system_error_dissolve, "E");

	/**Активный клиент, может работать в системе*/
	public static final ClientState ACTIVE    = new ClientState(ClientStateCategory.active, "A");

	/**удаленный*/
	public static final ClientState DELETED    = new ClientState(ClientStateCategory.cancellation, "D");

	/**Клиент в процессе регистрации*/
	public static final ClientState TEMPLATE    = new ClientState(ClientStateCategory.creation, "T");

	/**На расторжении*/
	public static final ClientState WAIT_CANCELATION    = new ClientState(ClientStateCategory.cancellation, "W");

	/**Клиент в процессе регистрации*/
	public static final ClientState SIGN_AGREEMENT    = new ClientState(ClientStateCategory.creation, "S");

	//категория состояния клиента
	private ClientStateCategory category;
	//уточняющий код
	private String code;
	//описание
	private String description;

	// только для вэб-сервиса
	@Deprecated
	public ClientState() {}

	/**
	 * ctor
	 * @param category категория состояния
	 * @param code уточняющий код
	 */
	public ClientState(ClientStateCategory category, String code)
	{
		this.category = category;
		this.code = code;
		statesByCode.put(code, this);
	}

	public ClientState(ClientState state, String description)
	{
		this.category = state.category;
		this.code = state.code;
		this.description = description;
		statesByCode.put(code, this);
	}

	/**
	 * ctor
	 * @param category категория состояния
	 * @param code уточняющий код
	 * @param description описание
	 */
	public ClientState(ClientStateCategory category, String code, String description)
	{
		this.category = category;
		this.code = code;
		this.description = description;
		statesByCode.put(code, this);
	}

	/**
	 * @return категоря статуса
	 */
	public ClientStateCategory getCategory()
	{
		return category;
	}

	@Deprecated //todo только для вебсервисов
	public void setCategory(String category)
	{

		this.category = ClientStateCategory.valueOf(category);
	}
	
	@Deprecated //todo только для вебсервисов
	public void setCategory(ClientStateCategory category)
	{

		this.category = category;
	}

	/**
	 * @return код состояния (каждая категория сотояния может уточнятся кодом)
	 */
	public String getCode()
	{
		return code;
	}

	@Deprecated //todo только для вебсервисов
	public void setCode(String code)
	{
		this.code = code;
	}

	public String getDescription()
	{
		return description;
	}

	public void setDescription(String description)
	{
		this.description = description;
	}

	/**
	 * Получить состояние клиента по коду состояния
	 * @param code - код состояния
	 * @return состояние клиента
	 */
	public static ClientState getStateByCode(String code)
	{
		return statesByCode.get(code);
	}


	public boolean equals(Object o)
	{
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		ClientState state = (ClientState) o;

		if (category != state.category) return false;
		if (!code.equals(state.code)) return false;

		return true;
	}

	public int hashCode()
	{
		int	result = category.hashCode();
		result = 31 * result + code.hashCode();
		return result;
	}
}
