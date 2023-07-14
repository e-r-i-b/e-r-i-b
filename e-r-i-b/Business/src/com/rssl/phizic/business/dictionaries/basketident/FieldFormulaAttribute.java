package com.rssl.phizic.business.dictionaries.basketident;

/**
 * @author osminin
 * @ created 02.07.15
 * @ $Author$
 * @ $Revision$
 *
 * Сущность - слагаемое формулы для поля постащика услуг в связке с идентификатором профиля
 */
public class FieldFormulaAttribute
{
	private Long id;
	private Long fieldFormulaId;
	private Long serial;
	private String value;
	private String systemId;
	private boolean last;

	/**
	 * @return идентификатор формулы
	 */
	public Long getFieldFormulaId()
	{
		return fieldFormulaId;
	}

	/**
	 * @param fieldFormulaId идентификатор формулы
	 */
	public void setFieldFormulaId(Long fieldFormulaId)
	{
		this.fieldFormulaId = fieldFormulaId;
	}

	/**
	 * @return положение в формуле
	 */
	public Long getSerial()
	{
		return serial;
	}

	/**
	 * @param serial положение в формуле
	 */
	public void setSerial(Long serial)
	{
		this.serial = serial;
	}

	/**
	 * @return значение поля
	 */
	public String getValue()
	{
		return value;
	}

	/**
	 * @param value значение поля
	 */
	public void setValue(String value)
	{
		this.value = value;
	}

	/**
	 * @return код атрибута в идентификаторе профиля
	 */
	public String getSystemId()
	{
		return systemId;
	}

	/**
	 * @param systemId код атрибута в идентификаторе профиля
	 */
	public void setSystemId(String systemId)
	{
		this.systemId = systemId;
	}

	/**
	 * @return идентификатор сущности
	 */
	public Long getId()
	{
		return id;
	}

	/**
	 * @param id идентификатор сущности
	 */
	public void setId(Long id)
	{
		this.id = id;
	}

	/**
	 * @return является ли значение последним в формуле (не привязано к атрибуту документа)
	 */
	public boolean isLast()
	{
		return last;
	}

	/**
	 * @param last является ли значение последним в формуле (не привязано к атрибуту документа)
	 */
	public void setLast(boolean last)
	{
		this.last = last;
	}
}
