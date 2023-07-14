package com.rssl.phizic.web.log.detail;

import com.rssl.phizic.logging.system.SystemLogEntry;
import com.rssl.phizic.web.common.EditFormBase;

/**
 * @author akrenev
 * @ created 01.08.2014
 * @ $Author$
 * @ $Revision$
 *
 * Форма просмотра информации о системном действии
 */

public class ViewSystemLogInfoForm extends EditFormBase
{
	private SystemLogEntry systemLogEntry= null;

	/**
	 * @return сущность лога
	 */
	@SuppressWarnings("UnusedDeclaration") //jsp
	public SystemLogEntry getSystemLogEntry()
	{
		return systemLogEntry;
	}

	/**
	 * задать сущность лога
	 * @param systemLogEntry сущность лога
	 */
	public void setSystemLogEntry(SystemLogEntry systemLogEntry)
	{
		this.systemLogEntry = systemLogEntry;
	}
}
