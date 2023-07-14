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
	 * ��������� ����� ������ ��������� � ������ ��������� ��� ��������������� �������
	 * @param stateObject ������
	 * @return MachineState ��� null ���� ��� ��������� � SM ��������������� �������,
	 * � ������ ��������� � ������� ��������� ������������ inital-state.
	 */
	public MachineState getObjectMachineState(StateObject stateObject)
	{
		if (stateObject == null)
		{
			throw new IllegalArgumentException("�������� stateObject �� ����� ���� null");
		}
		State objectState = stateObject.getState();
		if (objectState == null)
		{
			return initalState;
		}

		return getMachineStateByObjectStateCode(objectState.getCode());
	}

	/**
	 * ���������� ��������� ������ ���������
	 * @param objectStateCode ��������� �������
	 * @return ��������� ������
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
	 * @return ����� �� ��������� ���� � �����, � ������� ����������� ��������
	 */
	public boolean isSaveNodeInfo()
	{
		return saveNodeInfo;
	}

	/**
	 * ������ ������� ������������� ��������� ���� � �����, � ������� ����������� ��������
	 * @param saveNodeInfo �������
	 */
	public void setSaveNodeInfo(boolean saveNodeInfo)
	{
		this.saveNodeInfo = saveNodeInfo;
	}
}
