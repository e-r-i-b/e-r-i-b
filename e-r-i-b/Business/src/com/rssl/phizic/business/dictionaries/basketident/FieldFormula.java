package com.rssl.phizic.business.dictionaries.basketident;

import java.util.List;

/**
 * @author osminin
 * @ created 02.07.15
 * @ $Author$
 * @ $Revision$
 *
 * Сущность - Связь иднетификатора и полей поставщика услуг.
 * Поле поставщика услуг описывается как определенный набор (формула) из данных и атрибутов идентификатора.
 */
public class FieldFormula
{
	private Long id;
	private Long identifierId;
	private Long providerId;
	private String fieldExternalId;
	private List<FieldFormulaAttribute> attributes;

	/**
	 * @return идентификатор формулы
	 */
	public Long getId()
	{
		return id;
	}

	/**
	 * @param id идентификатор формулы
	 */
	public void setId(Long id)
	{
		this.id = id;
	}

	/**
	 * @return идентификатор документа(идентификатора профиля)
	 */
	public Long getIdentifierId()
	{
		return identifierId;
	}

	/**
	 * @param identifierId идентификатор документа(идентификатора профиля)
	 */
	public void setIdentifierId(Long identifierId)
	{
		this.identifierId = identifierId;
	}

	/**
	 * @return идентфикатор поставщика услуг
	 */
	public Long getProviderId()
	{
		return providerId;
	}

	/**
	 * @param providerId идентфикатор поставщика услуг
	 */
	public void setProviderId(Long providerId)
	{
		this.providerId = providerId;
	}

	/**
	 * @return внешний идентификатор поля поставщика услуг
	 */
	public String getFieldExternalId()
	{
		return fieldExternalId;
	}

	/**
	 * @param fieldExternalId внешний идентификатор поля поставщика услуг
	 */
	public void setFieldExternalId(String fieldExternalId)
	{
		this.fieldExternalId = fieldExternalId;
	}

	/**
	 * @return список слагаемых
	 */
	public List<FieldFormulaAttribute> getAttributes()
	{
		return attributes;
	}

	/**
	 * @param attributes список слагаемых
	 */
	public void setAttributes(List<FieldFormulaAttribute> attributes)
	{
		this.attributes = attributes;
	}
}
