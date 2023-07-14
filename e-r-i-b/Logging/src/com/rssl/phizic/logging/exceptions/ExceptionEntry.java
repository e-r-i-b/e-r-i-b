package com.rssl.phizic.logging.exceptions;

/**
 * «апись из справочника классификации ошибок, определ€ет уникальную ошибку.
 * @author mihaylov
 * @ created 11.04.2013
 * @ $Author$
 * @ $Revision$
 */
public abstract class ExceptionEntry
{
	public static final String DELIMITER = "|";

	private Long id;
	private String hash;
	private String operation;
	private String detail;

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
	 * @return хеш ошибки, дл€ сопоставлени€ с другими экземпл€рами ошибок
	 */
	public String getHash()
	{
		return hash;
	}

	/**
	 * @param hash хеш ошибки, дл€ сопоставлени€ с другими экземпл€рами ошибок
	 */
	public void setHash(String hash)
	{
		this.hash = hash;
	}

	/**
	 * @return действие при выполнение которого произошла ошибка
	 */
	public String getOperation()
	{
		return operation;
	}

	/**
	 * @param operation действие при выполнение которого произошла ошибка
	 */
	public void setOperation(String operation)
	{
		this.operation = operation;
	}

	/**
 	 * @return описание ошибки(стек трейс или сообщение из внешней системы)
	 */
	public String getDetail()
	{
		return detail;
	}

	/**
	 * @param detail описание ошибки(стек трейс или сообщение из внешней системы)
	 */
	public void setDetail(String detail)
	{
		this.detail = detail;
	}

	/**
	 * @return тип ошибки
	 */
	public abstract String getKind();
}
