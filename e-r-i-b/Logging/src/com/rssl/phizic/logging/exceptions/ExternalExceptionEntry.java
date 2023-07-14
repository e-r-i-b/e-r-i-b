package com.rssl.phizic.logging.exceptions;


/**
 * @author akrenev
 * @ created 16.04.2013
 * @ $Author$
 * @ $Revision$
 *
 *  ласс, описывающий внешние ошибки
 */

public class ExternalExceptionEntry extends ExceptionEntry
{
	private static final String KIND = "E";

	private String errorCode;
	private String adapterUUID;

	/**
	 * конструктор
	 */
	public ExternalExceptionEntry()
	{}

	/**
	 * конструктор
	 * @param errorCode код ошибки
	 * @param detail описание ошибки
	 * @param operation действие при выполнение которого произошла ошибка
	 * @param adapterUUID внешн€€ система
	 */
	public ExternalExceptionEntry(String errorCode, String detail, String operation, String adapterUUID)
	{
		setHash(adapterUUID.concat(DELIMITER).concat(operation).concat(DELIMITER).concat(errorCode));
		setDetail(detail);
		setOperation(operation);
		this.adapterUUID = adapterUUID;
		this.errorCode = errorCode;
	}

	/**
	 * @return внешн€€ система
	 */
	public String getSystem()
	{
		return adapterUUID;
	}

	/**
	 * @return код ошибки во внешней системе
	 */
	public String getErrorCode()
	{
		return errorCode;
	}

	@Override
	public String getKind()
	{
		return KIND;
	}
}
