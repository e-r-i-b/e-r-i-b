package com.rssl.phizic.business.statemachine;

import java.util.List;

/**
 * @author hudyakov
 * @ created 26.12.2008
 * @ $Author$
 * @ $Revision$
 */
public class Event
{
	private String name;
	private String description;
	private String type;
	private List<ObjectState> nextStates;
	private ObjectState defaultState;
	private List<Handler> handlers;
	private boolean lock;      // выполнять event c блокировкой?
	private List<ObjectState> postNextStates;

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getDescription()
	{
		return description;
	}

	public void setDescription(String description)
	{
		this.description = description;
	}

	public String getType()
	{
		return type;
	}

	public void setType(String type)
	{
		this.type = type;
	}

	public List<Handler> getHandlers()
	{
		return handlers;
	}

	public void setHandlers(List<Handler> handlers)
	{
		this.handlers = handlers;
	}

	public List<ObjectState> getNextStates()
	{
		return nextStates;
	}

	public void setNextStates(List<ObjectState> nextStates)
	{
		this.nextStates = nextStates;
	}

	public ObjectState getDefaultState()
	{
		return defaultState;
	}

	public void setDefaultState(ObjectState defaultState)
	{
		this.defaultState = defaultState;
	}

	public boolean isLock()
	{
		return lock;
	}

	public void setLock(boolean lock)
	{
		this.lock = lock;
	}

	public List<ObjectState> getPostNextStates()
	{
		return postNextStates;
	}

	public void setPostNextStates(List<ObjectState> postNextStates)
	{
		this.postNextStates = postNextStates;
	}
}