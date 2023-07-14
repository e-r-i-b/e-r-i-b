package com.rssl.phizic.csaadmin.business.login;

import java.util.Calendar;

/**
 * @author akrenev
 * @ created 27.09.13
 * @ $Author$
 * @ $Revision$
 *
 * ���������� ������
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
	 * @return �������������
	 */
	public Long getId()
	{
		return id;
	}

	/**
	 * ������ �������������
	 * @param id �������������
	 */
	public void setId(Long id)
	{
		this.id = id;
	}

	/**
	 * @return ��������������� �����
	 */
	public Login getLogin()
	{
		return login;
	}

	/**
	 * ������ ��������������� �����
	 * @param login ��������������� �����
	 */
	public void setLogin(Login login)
	{
		this.login = login;
	}

	/**
	 * @return ��� ����������
	 */
	public BlockType getReasonType()
	{
		return reasonType;
	}

	/**
	 * ������ ��� ����������
	 * @param reasonType ��� ����������
	 */
	public void setReasonType(BlockType reasonType)
	{
		this.reasonType = reasonType;
	}

	/**
	 * @return ������� ����������
	 */
	public String getReasonDescription()
	{
		return reasonDescription;
	}

	/**
	 * ������ ������� ����������
	 * @param reasonDescription ������� ����������
	 */
	public void setReasonDescription(String reasonDescription)
	{
		this.reasonDescription = reasonDescription;
	}

	/**
	 * @return ���� ����������
	 */
	public Calendar getBlockedFrom()
	{
		return blockedFrom;
	}

	/**
	 * ������ ���� ����������
	 * @param blockedFrom ���� ����������
	 */
	public void setBlockedFrom(Calendar blockedFrom)
	{
		this.blockedFrom = blockedFrom;
	}

	/**
	 * @return ��������� ����������
	 */
	public Calendar getBlockedUntil()
	{
		return blockedUntil;
	}

	/**
	 * ������ ��������� ����������
	 * @param blockedUntil ��������� ����������
	 */
	public void setBlockedUntil(Calendar blockedUntil)
	{
		this.blockedUntil = blockedUntil;
	}

	/**
	 * @return ���������������
	 */
	public Login getEmployee()
	{
		return employee;
	}

	/**
	 * ������ ����������������
	 * @param employee ���������������
	 */
	public void setEmployee(Login employee)
	{
		this.employee = employee;
	}

	/**
	 * @return ��������� � ����������
	 */
	public String getMessage()
	{
		StringBuilder messageBuilder = new StringBuilder(this.getReasonType().getPrefix());
		if(this.getBlockedFrom() != null)
			messageBuilder.append(" c ").append(String.format(DATE_FORMAT, this.getBlockedFrom()));
		if(this.getBlockedUntil() != null)
			messageBuilder.append(" �� ").append(String.format(DATE_FORMAT, this.getBlockedUntil()));
		messageBuilder.append(".");
		return messageBuilder.toString();
	}
}
