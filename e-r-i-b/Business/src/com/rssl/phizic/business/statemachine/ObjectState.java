package com.rssl.phizic.business.statemachine;

import java.util.List;

/**
 * @author Gainanov
 * @ created 05.10.2009
 * @ $Author$
 * @ $Revision$
 */
public class ObjectState
{
	private String state;
	private String stateCondition;
	private List<Handler> stateHandlers;
	private EnabledSource enabledSource;
	private String clientMessage;
	private String description;

	public String getState()
	{
		return state;
	}

	public void setState(String state)
	{
		this.state = state;
	}

	public String getStateCondition()
	{
		return stateCondition;
	}

	public void setStateCondition(String stateCondition)
	{
		this.stateCondition = stateCondition;
	}

	public List<Handler> getStateHandlers()
	{
		return stateHandlers;
	}

	public void setStateHandlers(List<Handler> stateHandlers)
	{
		this.stateHandlers = stateHandlers;
	}

	public EnabledSource getEnabledSource()
	{
		return enabledSource;
	}

	public void setEnabledSource(EnabledSource enabledSource)
	{
		this.enabledSource = enabledSource;
	}

	/**
	 * @return сообщение о событии для клиента
	 */
	public String getClientMessage()
	{
		return clientMessage;
	}

	public void setClientMessage(String clientMessage)
	{
		this.clientMessage = clientMessage;
	}

	/**
	 * Текстовое описание статуса документа
	 * @return
	 */
	public String getDescription()
	{
		return description;
	}

	public void setDescription(String description)
	{
		this.description = description;
	}
}
