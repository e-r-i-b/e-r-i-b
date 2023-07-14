package com.rssl.phizic.web.log.detail;

import com.rssl.phizic.logging.operations.GuestLogEntry;
import com.rssl.phizic.logging.operations.LogEntry;
import com.rssl.phizic.web.common.EditFormBase;

/**
 * @author akrenev
 * @ created 01.08.2014
 * @ $Author$
 * @ $Revision$
 *
 * ����� ��������� ���������� �� ��������
 */

public class ViewOperationParametersInfoForm extends EditFormBase
{
	private LogEntry logEntry;
	private GuestLogEntry guestLogEntry;
	private String fullName;
	private String type; //��� ��������: guest - �������� ������, simple - ������� ������

	/**
	 * @return �������� ����
	 */
	@SuppressWarnings("UnusedDeclaration") //jsp
	public LogEntry getLogEntry()
	{
		return logEntry;
	}

	/**
	 * ������ �������� ����
	 * @param logEntry �������� ����
	 */
	public void setLogEntry(LogEntry logEntry)
	{
		this.logEntry = logEntry;
	}

	/**
	 * @return �������� ��������� ����
	 */
	@SuppressWarnings("UnusedDeclaration") //jsp
	public GuestLogEntry getGuestLogEntry()
	{
		return guestLogEntry;
	}

	/**
	 * ������ �������� ��������� ����
	 * @param guestLogEntry �������� ��������� ����
	 */
	public void setGuestLogEntry(GuestLogEntry guestLogEntry)
	{
		this.guestLogEntry = guestLogEntry;
	}

	/**
	 * @return ��� ���������� ��������
	 */
	@SuppressWarnings("UnusedDeclaration") //jsp
	public String getFullName()
	{
		return fullName;
	}

	/**
	 * ������ ��� ���������� ��������
	 * @param fullName ���
	 */
	public void setFullName(String fullName)
	{
		this.fullName = fullName;
	}

	/**
	 * @return ��� ��������: �������� ��� ������� ������
	 */
	public String getType()
	{
		return type;
	}

	/**
	 * @param type - ��� �������� (�������� ��� ������� ������)
	 */
	public void setType(String type)
	{
		this.type = type;
	}
}