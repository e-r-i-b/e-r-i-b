package com.rssl.phizic.gate.mail;

import com.rssl.phizic.gate.multinodes.MultiNodeEntityBase;

import java.util.Calendar;

/**
 * @author mihaylov
 * @ created 29.05.14
 * @ $Author$
 * @ $Revision$
 *
 * ������� �������� ��� ������� �����.
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
	 * @return ������������� ������ � �����
	 */
	public Long getId()
	{
		return id;
	}

	/**
	 * ���������� ������������� ������ � �����
	 * @param id ������������� ������ � �����
	 */
	public void setId(Long id)
	{
		this.id = id;
	}

	/**
	 * @return ������������� ����� ������� � ������� ��������� ������
	 */
	public Long getNodeId()
	{
		return nodeId;
	}

	/**
	 * ���������� ������������� ����� ������� � ������� ��������� ������
	 * @param nodeId ������������� ����� ������� � ������� ��������� ������
	 */
	public void setNodeId(Long nodeId)
	{
		this.nodeId = nodeId;
	}

	/**
	 * @return ����� ������
	 */
	public Long getNumber()
	{
		return number;
	}

	/**
	 * ���������� ����� ������
	 * @param number ����� ������
	 */
	public void setNumber(Long number)
	{
		this.number = number;
	}

	/**
	 * @return ���� ������
	 */
	public String getSubject()
	{
		return subject;
	}

	/**
	 * ���������� ���� ������
	 * @param subject - ���� ������
	 */
	public void setSubject(String subject)
	{
		this.subject = subject;
	}

	/**
	 * @return ������ ������
	 */
	public String getState()
	{
		return state;
	}

	/**
	 * ���������� ������ ������
	 * @param state - ������ ������
	 */
	public void setState(String state)
	{
		this.state = state;
	}

	/**
	 * @return �������� ������� ������
	 */
	public String getStateDescription()
	{
		return stateDescription;
	}

	/**
	 * ���������� �������� ������� ������
	 * @param stateDescription - �������� ������� ������
	 */
	public void setStateDescription(String stateDescription)
	{
		this.stateDescription = stateDescription;
	}

	/**
	 * @return ��� ������
	 */
	public String getType()
	{
		return type;
	}

	/**
	 * ���������� ��� ������
	 * @param type - ��� ������
	 */
	public void setType(String type)
	{
		this.type = type;
	}

	/**
	 * @return �������� ���� ������
	 */
	public String getTypeDescription()
	{
		return typeDescription;
	}

	/**
	 * ���������� �������� ���� ������
	 * @param typeDescription - �������� ���� ������
	 */
	public void setTypeDescription(String typeDescription)
	{
		this.typeDescription = typeDescription;
	}

	/**
	 * @return ���� �������� ������
	 */
	public Calendar getCreationDate()
	{
		return creationDate;
	}

	/**
	 * ���������� ���� �������� ������
	 * @param creationDate - ���� �������� ������
	 */
	public void setCreationDate(Calendar creationDate)
	{
		this.creationDate = creationDate;
	}

	/**
	 * @return ����� ������ �� ������
	 */
	public String getResponseMethod()
	{
		return responseMethod;
	}

	/**
	 * ���������� ����� ������ �� ������
	 * @param responseMethod - ����� ������ �� ������
	 */
	public void setResponseMethod(String responseMethod)
	{
		this.responseMethod = responseMethod;
	}

	/**
	 * @return ���� ������
	 */
	public String getTheme()
	{
		return theme;
	}

	/**
	 * ���������� ���� ������
	 * @param theme - ���� ������
	 */
	public void setTheme(String theme)
	{
		this.theme = theme;
	}

	/**
	 * @return �������
	 */
	public String getTb()
	{
		return tb;
	}

	/**
	 * ���������� �������
	 * @param tb - �������
	 */
	public void setTb(String tb)
	{
		this.tb = tb;
	}

	/**
	 * @return �������� ��
	 */
	public String getArea()
	{
		return area;
	}

	/**
	 * ���������� �������� ��
	 * @param area - �������� ��
	 */
	public void setArea(String area)
	{
		this.area = area;
	}

	/**
	 * @return ��� ���������� �������������� �� ������
	 */
	public String getEmployeeFIO()
	{
		return employeeFIO;
	}

	/**
	 * ���������� ��� ���������� �������������� �� ������
	 * @param employeeFIO - ��� ���������� �������������� �� ������
	 */
	public void setEmployeeFIO(String employeeFIO)
	{
		this.employeeFIO = employeeFIO;
	}

	/**
	 * @return ������������� ���������� �������������� �� ������
	 */
	public String getEmployeeUserId()
	{
		return employeeUserId;
	}

	/**
	 * ���������� ������������� ���������� �������������� �� ������
	 * @param employeeUserId - ������������� ���������� �������������� �� ������
	 */
	public void setEmployeeUserId(String employeeUserId)
	{
		this.employeeUserId = employeeUserId;
	}
}
