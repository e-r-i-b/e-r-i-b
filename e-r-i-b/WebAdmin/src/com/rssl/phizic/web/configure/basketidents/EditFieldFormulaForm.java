package com.rssl.phizic.web.configure.basketidents;

import com.rssl.phizic.business.dictionaries.basketident.AttributeForBasketIdentType;
import com.rssl.phizic.business.dictionaries.basketident.FieldFormula;
import com.rssl.phizic.business.dictionaries.providers.ServiceProviderShort;
import com.rssl.phizic.business.fields.FieldDescriptionShortcut;
import com.rssl.phizic.web.common.EditFormBase;

import java.util.List;
import java.util.Set;

/**
 * @author osminin
 * @ created 03.07.15
 * @ $Author$
 * @ $Revision$
 *
 * Форма для работы со связками документов и ПУ
 */
public class EditFieldFormulaForm extends EditFormBase
{
	private String fieldIds;
	private String newFieldIds;
	private Long identId;
	private String externalId;

	private List<FieldDescriptionShortcut> fieldDescriptions;
	private Set<AttributeForBasketIdentType> attributes;
	private List<FieldFormula> formulas;
	private List<ServiceProviderShort> serviceProviders;

	/**
	 * @return идентфикаторы полей
	 */
	public String getFieldIds()
	{
		return fieldIds;
	}

	/**
	 * @param fieldIds идентфикаторы полей
	 */
	public void setFieldIds(String fieldIds)
	{
		this.fieldIds = fieldIds;
	}

	/**
	 * @return идентфикаторы новых полей
	 */
	public String getNewFieldIds()
	{
		return newFieldIds;
	}

	/**
	 * @param newFieldIds идентфикаторы новых полей
	 */
	public void setNewFieldIds(String newFieldIds)
	{
		this.newFieldIds = newFieldIds;
	}

	/**
	 * @return идентификатор документа привязки
	 */
	public Long getIdentId()
	{
		return identId;
	}

	/**
	 * @param identId идентификатор документа привязки
	 */
	public void setIdentId(Long identId)
	{
		this.identId = identId;
	}

	/**
	 * @return набор описаний полей ПУ
	 */
	public List<FieldDescriptionShortcut> getFieldDescriptions()
	{
		return fieldDescriptions;
	}

	/**
	 * @param fieldDescriptions набор описаний полей ПУ
	 */
	public void setFieldDescriptions(List<FieldDescriptionShortcut> fieldDescriptions)
	{
		this.fieldDescriptions = fieldDescriptions;
	}

	/**
	 * @return набор атрибутов идентификатора профиля
	 */
	public Set<AttributeForBasketIdentType> getAttributes()
	{
		return attributes;
	}

	/**
	 * @param attributes набор атрибутов идентификатора профиля
	 */
	public void setAttributes(Set<AttributeForBasketIdentType> attributes)
	{
		this.attributes = attributes;
	}

	/**
	 * @return Список формул
	 */
	public List<FieldFormula> getFormulas()
	{
		return formulas;
	}

	/**
	 * @param formulas Список формул
	 */
	public void setFormulas(List<FieldFormula> formulas)
	{
		this.formulas = formulas;
	}

	/**
	 * @return внешний идентифкатор поставщика услуг
	 */
	public String getExternalId()
	{
		return externalId;
	}

	/**
	 * @param externalId внешний идентифкатор поставщика услуг
	 */
	public void setExternalId(String externalId)
	{
		this.externalId = externalId;
	}

	/**
	 * @return одиночный список поставщиков услуг
	 */
	public List<ServiceProviderShort> getServiceProviders()
	{
		return serviceProviders;
	}

	/**
	 * @param serviceProviders одиночный список поставщиков услуг
	 */
	public void setServiceProviders(List<ServiceProviderShort> serviceProviders)
	{
		this.serviceProviders = serviceProviders;
	}
}
