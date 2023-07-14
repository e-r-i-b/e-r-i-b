package com.rssl.phizic.csaadmin.business.login;

import java.util.Calendar;

/**
 * @author akrenev
 * @ created 27.09.13
 * @ $Author$
 * @ $Revision$
 *
 * Блокировка логина
 */

public class LoginBlock
{
	private static final String DATE_FORMAT = "%1$td.%1$tm.%1$tY %1$tH:%1$tM:%1$tS";

	private Long id;
	private Login login;
	private BlockType reasonType;
	private String reasonDescription;
	private Calendar blockedFrom;
	private Calendar blockedUntil;
	private Login employee;

	/**
	 * @return идентификатор
	 */
	public Long getId()
	{
		return id;
	}

	/**
	 * задать идентификатор
	 * @param id идентификатор
	 */
	public void setId(Long id)
	{
		this.id = id;
	}

	/**
	 * @return заблокированный логин
	 */
	public Login getLogin()
	{
		return login;
	}

	/**
	 * задать заблокированный логин
	 * @param login заблокированный логин
	 */
	public void setLogin(Login login)
	{
		this.login = login;
	}

	/**
	 * @return тип блокировки
	 */
	public BlockType getReasonType()
	{
		return reasonType;
	}

	/**
	 * задать тип блокировки
	 * @param reasonType тип блокировки
	 */
	public void setReasonType(BlockType reasonType)
	{
		this.reasonType = reasonType;
	}

	/**
	 * @return причина блокировки
	 */
	public String getReasonDescription()
	{
		return reasonDescription;
	}

	/**
	 * задать причину блокировки
	 * @param reasonDescription причина блокировки
	 */
	public void setReasonDescription(String reasonDescription)
	{
		this.reasonDescription = reasonDescription;
	}

	/**
	 * @return дата блокировки
	 */
	public Calendar getBlockedFrom()
	{
		return blockedFrom;
	}

	/**
	 * задать дата блокировки
	 * @param blockedFrom дата блокировки
	 */
	public void setBlockedFrom(Calendar blockedFrom)
	{
		this.blockedFrom = blockedFrom;
	}

	/**
	 * @return окончание блокировки
	 */
	public Calendar getBlockedUntil()
	{
		return blockedUntil;
	}

	/**
	 * задать окончание блокировки
	 * @param blockedUntil окончание блокировки
	 */
	public void setBlockedUntil(Calendar blockedUntil)
	{
		this.blockedUntil = blockedUntil;
	}

	/**
	 * @return заблокировавший
	 */
	public Login getEmployee()
	{
		return employee;
	}

	/**
	 * задать заблокировавшего
	 * @param employee заблокировавший
	 */
	public void setEmployee(Login employee)
	{
		this.employee = employee;
	}

	/**
	 * @return сообщение о блокировке
	 */
	public String getMessage()
	{
		StringBuilder messageBuilder = new StringBuilder(this.getReasonType().getPrefix());
		if(this.getBlockedFrom() != null)
			messageBuilder.append(" c ").append(String.format(DATE_FORMAT, this.getBlockedFrom()));
		if(this.getBlockedUntil() != null)
			messageBuilder.append(" до ").append(String.format(DATE_FORMAT, this.getBlockedUntil()));
		messageBuilder.append(".");
		return messageBuilder.toString();
	}
}
