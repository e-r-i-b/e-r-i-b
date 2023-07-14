package com.rssl.phizic.business.ermb.auxiliary.smslog;

/**
 * @author Gulov
 * @ created 16.05.2013
 * @ $Author$
 * @ $Revision$
 */

/**
 * Поля фильтра Журнала ЕРМБ.
 */
public enum FilterField
{
	FIO("fio"),
	DOCUMENT_TYPE("documentType"),
	DOCUMENT_NUMBER("documentNumber"),
	DOCUMENT_SERIES("documentSeries"),
	BIRTHDAY("birthDay"),
	PHONE("phone"),
	FROM_DATE("fromDate"),
	FROM_TIME("fromTime"),
	TO_DATE("toDate"),
	TO_TIME("toTime"),
	SESSION_ID("sessionId"),
	PAGINATION_SIZE("$$pagination_size0"),
	PAGINATION_OFFSET("$$pagination_offset0"),
	TB("tb");

	private String value;

	private FilterField(String value)
	{
		this.value = value;
	}

	public String value()
	{
		return value;
	}

	public static FilterField fromValue(String value)
	{
		for (FilterField field : values())
		{
			if (field.value.equals(value))
				return field;
		}
		throw new IllegalArgumentException("Неизвестное поле [" + value + "]");
	}
}
