package com.rssl.phizic.gate.mail;

import com.rssl.phizic.gate.multinodes.MultiNodeEntityBase;

import java.util.Calendar;

/**
 * @author mihaylov
 * @ created 29.05.14
 * @ $Author$
 * @ $Revision$
 *
 * Базовая сущность для списков писем.
 */
public class MailListEntityBase extends MultiNodeEntityBase
{
	private Long id;
	private Long nodeId;
	private Long number;
	private String subject;
	private String state;
	private String stateDescription;
	private String type;
	private String typeDescription;
	private Calendar creationDate;
	private String responseMethod;
	private String theme;
	private String tb;
	private String area;
	private String employeeFIO;
	private String employeeUserId;

	/**
	 * @return идентификатор письма в блоке
	 */
	public Long getId()
	{
		return id;
	}

	/**
	 * Установить идентификатор письма в блоке
	 * @param id идентификатор письма в блоке
	 */
	public void setId(Long id)
	{
		this.id = id;
	}

	/**
	 * @return идентификатор блока системы в котором находится письмо
	 */
	public Long getNodeId()
	{
		return nodeId;
	}

	/**
	 * Установить идентификатор блока системы в котором находится письмо
	 * @param nodeId идентификатор блока системы в котором находится письмо
	 */
	public void setNodeId(Long nodeId)
	{
		this.nodeId = nodeId;
	}

	/**
	 * @return номер письма
	 */
	public Long getNumber()
	{
		return number;
	}

	/**
	 * Установить номер письма
	 * @param number номер письма
	 */
	public void setNumber(Long number)
	{
		this.number = number;
	}

	/**
	 * @return тема письма
	 */
	public String getSubject()
	{
		return subject;
	}

	/**
	 * Установить тему письма
	 * @param subject - тема письма
	 */
	public void setSubject(String subject)
	{
		this.subject = subject;
	}

	/**
	 * @return статус письма
	 */
	public String getState()
	{
		return state;
	}

	/**
	 * Установить статус письма
	 * @param state - статус письма
	 */
	public void setState(String state)
	{
		this.state = state;
	}

	/**
	 * @return описание статуса письма
	 */
	public String getStateDescription()
	{
		return stateDescription;
	}

	/**
	 * Установить описание статуса письма
	 * @param stateDescription - описание статуса письма
	 */
	public void setStateDescription(String stateDescription)
	{
		this.stateDescription = stateDescription;
	}

	/**
	 * @return Тип письма
	 */
	public String getType()
	{
		return type;
	}

	/**
	 * Установить тип письма
	 * @param type - тип письма
	 */
	public void setType(String type)
	{
		this.type = type;
	}

	/**
	 * @return описание типа письма
	 */
	public String getTypeDescription()
	{
		return typeDescription;
	}

	/**
	 * Установить описание типа письма
	 * @param typeDescription - описание типа письма
	 */
	public void setTypeDescription(String typeDescription)
	{
		this.typeDescription = typeDescription;
	}

	/**
	 * @return дата создания письма
	 */
	public Calendar getCreationDate()
	{
		return creationDate;
	}

	/**
	 * Установить дату создания письма
	 * @param creationDate - дата создания письма
	 */
	public void setCreationDate(Calendar creationDate)
	{
		this.creationDate = creationDate;
	}

	/**
	 * @return метод ответа на письмо
	 */
	public String getResponseMethod()
	{
		return responseMethod;
	}

	/**
	 * Установить метод ответа на письмо
	 * @param responseMethod - метод ответа на письмо
	 */
	public void setResponseMethod(String responseMethod)
	{
		this.responseMethod = responseMethod;
	}

	/**
	 * @return тема письма
	 */
	public String getTheme()
	{
		return theme;
	}

	/**
	 * Установить тему письма
	 * @param theme - тема письма
	 */
	public void setTheme(String theme)
	{
		this.theme = theme;
	}

	/**
	 * @return тербанк
	 */
	public String getTb()
	{
		return tb;
	}

	/**
	 * Установить тербанк
	 * @param tb - тербанк
	 */
	public void setTb(String tb)
	{
		this.tb = tb;
	}

	/**
	 * @return площадка КЦ
	 */
	public String getArea()
	{
		return area;
	}

	/**
	 * Установить площадку КЦ
	 * @param area - площадка КЦ
	 */
	public void setArea(String area)
	{
		this.area = area;
	}

	/**
	 * @return ФИО сотрудника ответственного за письмо
	 */
	public String getEmployeeFIO()
	{
		return employeeFIO;
	}

	/**
	 * Установить ФИО сотрудника ответственного за письмо
	 * @param employeeFIO - ФИО сотрудника ответственного за письмо
	 */
	public void setEmployeeFIO(String employeeFIO)
	{
		this.employeeFIO = employeeFIO;
	}

	/**
	 * @return идентификатор сотрудника ответственного за письмо
	 */
	public String getEmployeeUserId()
	{
		return employeeUserId;
	}

	/**
	 * Установить идентификатор сотрудника ответственного за письмо
	 * @param employeeUserId - идентификатор сотрудника ответственного за письмо
	 */
	public void setEmployeeUserId(String employeeUserId)
	{
		this.employeeUserId = employeeUserId;
	}
}
