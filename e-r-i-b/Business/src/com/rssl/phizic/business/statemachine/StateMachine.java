package com.rssl.phizic.business.statemachine;

import com.rssl.common.forms.state.StateObject;
import com.rssl.phizic.common.types.documents.State;

import java.util.List;
import java.util.Map;

/**
 * @author hudyakov
 * @ created 26.12.2008
 * @ $Author$
 * @ $Revision$
 */
public class StateMachine
{
	private String name;
	private String description;
	private MachineState initalState;
	private Map<String, MachineState> states;  //id of state, state
	private Map<String, List<ObjectState>> sequencesNextStates;
	private Map<String, List<Handler>> sequencesHandlers;
	private Map<String, Object> parameters;
	private boolean saveNodeInfo;

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

	public MachineState getInitalState()
	{
		return initalState;
	}

	public void setInitalState(MachineState initalState)
	{
		this.initalState = initalState;
	}

	public Map<String, MachineState> getStates()
	{
		return states;
	}

	public void setStates(Map<String, MachineState> states)
	{
		this.states = states;
	}

	/**
	 * Утилитный метод поиска состояния в машине состояний для соответсвующего объекту
	 * @param stateObject объект
	 * @return MachineState или null если нет состояния в SM соттветсвующего объекту,
	 * в случае отсутсвия у объекта состояния возвразается inital-state.
	 */
	public MachineState getObjectMachineState(StateObject stateObject)
	{
		if (stateObject == null)
		{
			throw new IllegalArgumentException("Параметр stateObject не может быть null");
		}
		State objectState = stateObject.getState();
		if (objectState == null)
		{
			return initalState;
		}

		return getMachineStateByObjectStateCode(objectState.getCode());
	}

	/**
	 * Возвращает состояние машины состояний
	 * @param objectStateCode состояние объекта
	 * @return состояние машины
	 */
	public MachineState getMachineStateByObjectStateCode(String objectStateCode)
	{
		return states.get(objectStateCode);
	}

	public Map<String, List<Handler>> getSequencesHandlers()
	{
		return sequencesHandlers;
	}

	public void setSequencesHandlers(Map<String, List<Handler>> sequencesHandlers)
	{
		this.sequencesHandlers = sequencesHandlers;
	}

	public Map<String, List<ObjectState>> getSequencesNextStates()
	{
		return sequencesNextStates;
	}

	public void setSequencesNextStates(Map<String, List<ObjectState>> sequencesNextStates)
	{
		this.sequencesNextStates = sequencesNextStates;
	}

	/**
	 * @return нужно ли сохранять инфу о блоке, в котором совершались действия
	 */
	public boolean isSaveNodeInfo()
	{
		return saveNodeInfo;
	}

	/**
	 * задать признак необходимости сохранять инфу о блоке, в котором совершались действия
	 * @param saveNodeInfo признак
	 */
	public void setSaveNodeInfo(boolean saveNodeInfo)
	{
		this.saveNodeInfo = saveNodeInfo;
	}
}
