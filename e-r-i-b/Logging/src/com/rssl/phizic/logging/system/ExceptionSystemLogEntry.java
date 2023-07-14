package com.rssl.phizic.logging.system;

import com.rssl.phizic.logging.system.guest.GuestSystemLogEntry;

/**
 * @author osminin
 * @ created 17.03.15
 * @ $Author$
 * @ $Revision$
 *
 * Системного лог для передачи информации об ошибке для маппинга
 */
public class ExceptionSystemLogEntry extends GuestSystemLogEntry
{
	private String hash;
	private String operation;
	private String system;
	private String errorCode;
	private String detail;
	private String kind;

	/**
	 * ctor
	 */
	public ExceptionSystemLogEntry()
	{
	}

	/**
	 * ctor
	 * @param entry запись в журнал
	 */
	public ExceptionSystemLogEntry(GuestSystemLogEntry entry)
	{
		super(entry);
	}

	/**
	 * ctor
	 * @param entry запись в журнал
	 */
	public ExceptionSystemLogEntry(SystemLogEntry entry)
	{
		super(entry);
	}

	/**
	 * @return хэш
	 */
	public String getHash()
	{
		return hash;
	}

	/**
	 * @param hash хэш
	 */
	public void setHash(String hash)
	{
		this.hash = hash;
	}

	/**
	 * @return описание действия
	 */
	public String getOperation()
	{
		return operation;
	}

	/**
	 * @param operation описание действия
	 */
	public void setOperation(String operation)
	{
		this.operation = operation;
	}

	/**
	 * @return система
	 */
	public String getSystem()
	{
		return system;
	}

	/**
	 * @param system система
	 */
	public void setSystem(String system)
	{
		this.system = system;
	}

	/**
	 * @return код ошибки
	 */
	public String getErrorCode()
	{
		return errorCode;
	}

	/**
	 * @param errorCode код ошибки
	 */
	public void setErrorCode(String errorCode)
	{
		this.errorCode = errorCode;
	}

	/**
	 * @return детали ошибки
	 */
	public String getDetail()
	{
		return detail;
	}

	/**
	 * @param detail детали ошибки
	 */
	public void setDetail(String detail)
	{
		this.detail = detail;
	}

	/**
	 * @return тип ошибки
	 */
	public String getKind()
	{
		return kind;
	}

	/**
	 * @param kind тип ошибки
	 */
	public void setKind(String kind)
	{
		this.kind = kind;
	}
}
