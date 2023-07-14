package com.rssl.phizic.business.mail.reassign.history;

import java.util.Calendar;

/**
 * ������� �������������� ������
 * @author komarov
 * @ created 15.10.13 
 * @ $Author$
 * @ $Revision$
 */

public class ReassignMailReason
{
	private Long id;
	private Long mailId;
	private Calendar date;
	private String employeeFIO;
	private String reassignReason;

	/**
	 * @return �������������
	 */
	public Long getId()
	{
		return id;
	}

	/**
	 * @param id �������������
	 */
	public void setId(Long id)
	{
		this.id = id;
	}

	/**
	 * @return ������������� ������
	 */
	public Long getMailId()
	{
		return mailId;
	}

	/**
	 * @param mailId ������������� ������
	 */
	public void setMailId(Long mailId)
	{
		this.mailId = mailId;
	}

	/**
	 * @return ���� ��������������
	 */
	public Calendar getDate()
	{
		return date;
	}

	/**
	 * @param date ���� ��������������
	 */
	public void setDate(Calendar date)
	{
		this.date = date;
	}

	/**
	 * @return ��� ����������
	 */
	public String getEmployeeFIO()
	{
		return employeeFIO;
	}

	/**
	 * @param employeeFIO ��� ����������
	 */
	public void setEmployeeFIO(String employeeFIO)
	{
		this.employeeFIO = employeeFIO;
	}

	/**
	 * @return ������� �������������� ������
	 */
	public String getReassignReason()
	{
		return reassignReason;
	}

	/**
	 * @param reassignReason ������� �������������� ������
	 */
	public void setReassignReason(String reassignReason)
	{
		this.reassignReason = reassignReason;
	}
}
