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
 * ����� ��� ������ �� �������� ���������� � ��
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
	 * @return ������������� �����
	 */
	public String getFieldIds()
	{
		return fieldIds;
	}

	/**
	 * @param fieldIds ������������� �����
	 */
	public void setFieldIds(String fieldIds)
	{
		this.fieldIds = fieldIds;
	}

	/**
	 * @return ������������� ����� �����
	 */
	public String getNewFieldIds()
	{
		return newFieldIds;
	}

	/**
	 * @param newFieldIds ������������� ����� �����
	 */
	public void setNewFieldIds(String newFieldIds)
	{
		this.newFieldIds = newFieldIds;
	}

	/**
	 * @return ������������� ��������� ��������
	 */
	public Long getIdentId()
	{
		return identId;
	}

	/**
	 * @param identId ������������� ��������� ��������
	 */
	public void setIdentId(Long identId)
	{
		this.identId = identId;
	}

	/**
	 * @return ����� �������� ����� ��
	 */
	public List<FieldDescriptionShortcut> getFieldDescriptions()
	{
		return fieldDescriptions;
	}

	/**
	 * @param fieldDescriptions ����� �������� ����� ��
	 */
	public void setFieldDescriptions(List<FieldDescriptionShortcut> fieldDescriptions)
	{
		this.fieldDescriptions = fieldDescriptions;
	}

	/**
	 * @return ����� ��������� �������������� �������
	 */
	public Set<AttributeForBasketIdentType> getAttributes()
	{
		return attributes;
	}

	/**
	 * @param attributes ����� ��������� �������������� �������
	 */
	public void setAttributes(Set<AttributeForBasketIdentType> attributes)
	{
		this.attributes = attributes;
	}

	/**
	 * @return ������ ������
	 */
	public List<FieldFormula> getFormulas()
	{
		return formulas;
	}

	/**
	 * @param formulas ������ ������
	 */
	public void setFormulas(List<FieldFormula> formulas)
	{
		this.formulas = formulas;
	}

	/**
	 * @return ������� ������������ ���������� �����
	 */
	public String getExternalId()
	{
		return externalId;
	}

	/**
	 * @param externalId ������� ������������ ���������� �����
	 */
	public void setExternalId(String externalId)
	{
		this.externalId = externalId;
	}

	/**
	 * @return ��������� ������ ����������� �����
	 */
	public List<ServiceProviderShort> getServiceProviders()
	{
		return serviceProviders;
	}

	/**
	 * @param serviceProviders ��������� ������ ����������� �����
	 */
	public void setServiceProviders(List<ServiceProviderShort> serviceProviders)
	{
		this.serviceProviders = serviceProviders;
	}
}
