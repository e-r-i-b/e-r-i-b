package com.rssl.phizic.business.statemachine;

import com.rssl.common.forms.state.ObjectEvent;
import com.rssl.phizic.business.statemachine.forms.Form;
import com.rssl.phizic.common.types.documents.StateCode;

import java.util.ArrayList;
import java.util.List;

/**
 * Состояние машины состоянй
 *
 * @author khudyakov
 * @ created 26.12.2008
 * @ $Author$
 * @ $Revision$
 */
public class MachineState
{
	private static final List<String> VIEW_STATES = new ArrayList<String>();
	static
	{
		VIEW_STATES.add(StateCode.EXECUTED.name());
		VIEW_STATES.add(StateCode.REFUSED.name());
		VIEW_STATES.add(StateCode.ERROR.name());
		VIEW_STATES.add(StateCode.UNKNOW.name());
		VIEW_STATES.add(StateCode.SENT.name());
		VIEW_STATES.add(StateCode.OFFLINE_DELAYED.name());
		VIEW_STATES.add(StateCode.PARTLY_EXECUTED.name());
		VIEW_STATES.add(StateCode.BILLING_CONFIRM_TIMEOUT.name());
		VIEW_STATES.add(StateCode.BILLING_GATE_CONFIRM_TIMEOUT.name());
		VIEW_STATES.add(StateCode.ABS_RECALL_TIMEOUT.name());
		VIEW_STATES.add(StateCode.ABS_GATE_RECALL_TIMEOUT.name());
		VIEW_STATES.add(StateCode.RECALLED.name());
		VIEW_STATES.add(StateCode.WAIT_CONFIRM.name());
		VIEW_STATES.add(StateCode.DISPATCHED.name());
		VIEW_STATES.add(StateCode.DELAYED_DISPATCH.name());
	}

	private String id;                                  //наименование состояния
	private String description;                         //краткое описание состояния
	private String clientForm;                          //форма отображения по умолчанию в модуле клинта
	private String employeeForm;                        //форма отображения по умолчанию в модуле сотрудника банка
	private List<Form> forms = new ArrayList<Form>();   //список форм, если пуст - отображается форма по умолчанию
	private String systemResolver;                      //
	private List<Handler> initHandlers;                 //хендлеры по умолчанию
	private List<Event> events;                         //переходы из данного состояния

	public String getId()
	{
		return id;
	}

	void setId(String id)
	{
		this.id = id;
	}

	public String getDescription()
	{
		return description;
	}

	void setDescription(String description)
	{
		this.description = description;
	}

	public String getClientForm()
	{
		return clientForm;
	}

	void setClientForm(String clientForm)
	{
		this.clientForm = clientForm;
	}

	public String getEmployeeForm()
	{
		return employeeForm;
	}

	void setEmployeeForm(String employeeForm)
	{
		this.employeeForm = employeeForm;
	}

	public List<Form> getForms()
	{
		return forms;
	}

	public void setForms(List<Form> forms)
	{
		this.forms = forms;
	}

	public List<Handler> getInitHandlers()
	{
		return initHandlers;
	}

	void setInitHandlers(List<Handler> handlers)
	{
		this.initHandlers = handlers;
	}

	public List<Event> getEvents()
	{
		return events;
	}

	void setEvents(List<Event> events)
	{
		this.events = events;
	}

	public String getSystemResolver()
	{
		return systemResolver;
	}

	public void setSystemResolver(String systemResolver)
	{
		this.systemResolver = systemResolver;
	}

	/**
	 * Утилитный метод проверки допустимости события
	 * @param checkedEvent проверяемоe событиe
	 * @return да/нет.
	 */
	public boolean isEventAcceptable(ObjectEvent checkedEvent)
	{
		if (checkedEvent == null)
		{
			return false;
		}
		for (Event event : getEvents())
		{
			if (event.getType().equals(checkedEvent.getType()) && event.getName().equals(checkedEvent.getEvent().name()))
			{
				return true;
			}
		}
		return false;
	}

	/**
	 * @return форма просмотра документа?
	 */
	public boolean isViewState()
	{
		return VIEW_STATES.contains(id);
	}
}
