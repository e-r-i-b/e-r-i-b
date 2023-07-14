package com.rssl.phizic.business.mail.reassign.history;

import java.util.Calendar;

/**
 * Причина переназначения письма
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
	 * @return идентификатор
	 */
	public Long getId()
	{
		return id;
	}

	/**
	 * @param id идентификатор
	 */
	public void setId(Long id)
	{
		this.id = id;
	}

	/**
	 * @return идентификатор письма
	 */
	public Long getMailId()
	{
		return mailId;
	}

	/**
	 * @param mailId идентификатор письма
	 */
	public void setMailId(Long mailId)
	{
		this.mailId = mailId;
	}

	/**
	 * @return дата переназначения
	 */
	public Calendar getDate()
	{
		return date;
	}

	/**
	 * @param date дата переназначения
	 */
	public void setDate(Calendar date)
	{
		this.date = date;
	}

	/**
	 * @return ФИО сотрудника
	 */
	public String getEmployeeFIO()
	{
		return employeeFIO;
	}

	/**
	 * @param employeeFIO ФИО сотрудника
	 */
	public void setEmployeeFIO(String employeeFIO)
	{
		this.employeeFIO = employeeFIO;
	}

	/**
	 * @return причина переназначения письма
	 */
	public String getReassignReason()
	{
		return reassignReason;
	}

	/**
	 * @param reassignReason причина переназначения письма
	 */
	public void setReassignReason(String reassignReason)
	{
		this.reassignReason = reassignReason;
	}
}
