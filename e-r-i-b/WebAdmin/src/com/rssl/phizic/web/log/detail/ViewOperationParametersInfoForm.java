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
 * Форма просмотра информации об операции
 */

public class ViewOperationParametersInfoForm extends EditFormBase
{
	private LogEntry logEntry;
	private GuestLogEntry guestLogEntry;
	private String fullName;
	private String type; //тип сущности: guest - гостевой журнал, simple - обычный журнал

	/**
	 * @return сущность лога
	 */
	@SuppressWarnings("UnusedDeclaration") //jsp
	public LogEntry getLogEntry()
	{
		return logEntry;
	}

	/**
	 * задать сущность лога
	 * @param logEntry сущность лога
	 */
	public void setLogEntry(LogEntry logEntry)
	{
		this.logEntry = logEntry;
	}

	/**
	 * @return сущность гостевого лога
	 */
	@SuppressWarnings("UnusedDeclaration") //jsp
	public GuestLogEntry getGuestLogEntry()
	{
		return guestLogEntry;
	}

	/**
	 * задать сущность гостевого лога
	 * @param guestLogEntry сущность гостевого лога
	 */
	public void setGuestLogEntry(GuestLogEntry guestLogEntry)
	{
		this.guestLogEntry = guestLogEntry;
	}

	/**
	 * @return ФИО инициатора действия
	 */
	@SuppressWarnings("UnusedDeclaration") //jsp
	public String getFullName()
	{
		return fullName;
	}

	/**
	 * задать ФИО инициатора действия
	 * @param fullName ФИО
	 */
	public void setFullName(String fullName)
	{
		this.fullName = fullName;
	}

	/**
	 * @return тип сущности: гостевой или обычный журнал
	 */
	public String getType()
	{
		return type;
	}

	/**
	 * @param type - тип сущности (гостевой или обычный жуннал)
	 */
	public void setType(String type)
	{
		this.type = type;
	}
}