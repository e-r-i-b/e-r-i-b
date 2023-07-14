package com.rssl.phizic.web.log.detail;

import com.rssl.phizic.logging.system.SystemLogEntry;
import com.rssl.phizic.web.common.EditFormBase;

/**
 * @author akrenev
 * @ created 01.08.2014
 * @ $Author$
 * @ $Revision$
 *
 * ����� ��������� ���������� � ��������� ��������
 */

public class ViewSystemLogInfoForm extends EditFormBase
{
	private SystemLogEntry systemLogEntry= null;

	/**
	 * @return �������� ����
	 */
	@SuppressWarnings("UnusedDeclaration") //jsp
	public SystemLogEntry getSystemLogEntry()
	{
		return systemLogEntry;
	}

	/**
	 * ������ �������� ����
	 * @param systemLogEntry �������� ����
	 */
	public void setSystemLogEntry(SystemLogEntry systemLogEntry)
	{
		this.systemLogEntry = systemLogEntry;
	}
}
