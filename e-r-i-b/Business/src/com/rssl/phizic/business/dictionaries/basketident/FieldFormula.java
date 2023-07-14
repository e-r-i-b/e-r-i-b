package com.rssl.phizic.business.dictionaries.basketident;

import java.util.List;

/**
 * @author osminin
 * @ created 02.07.15
 * @ $Author$
 * @ $Revision$
 *
 * �������� - ����� �������������� � ����� ���������� �����.
 * ���� ���������� ����� ����������� ��� ������������ ����� (�������) �� ������ � ��������� ��������������.
 */
public class FieldFormula
{
	private Long id;
	private Long identifierId;
	private Long providerId;
	private String fieldExternalId;
	private List<FieldFormulaAttribute> attributes;

	/**
	 * @return ������������� �������
	 */
	public Long getId()
	{
		return id;
	}

	/**
	 * @param id ������������� �������
	 */
	public void setId(Long id)
	{
		this.id = id;
	}

	/**
	 * @return ������������� ���������(�������������� �������)
	 */
	public Long getIdentifierId()
	{
		return identifierId;
	}

	/**
	 * @param identifierId ������������� ���������(�������������� �������)
	 */
	public void setIdentifierId(Long identifierId)
	{
		this.identifierId = identifierId;
	}

	/**
	 * @return ������������ ���������� �����
	 */
	public Long getProviderId()
	{
		return providerId;
	}

	/**
	 * @param providerId ������������ ���������� �����
	 */
	public void setProviderId(Long providerId)
	{
		this.providerId = providerId;
	}

	/**
	 * @return ������� ������������� ���� ���������� �����
	 */
	public String getFieldExternalId()
	{
		return fieldExternalId;
	}

	/**
	 * @param fieldExternalId ������� ������������� ���� ���������� �����
	 */
	public void setFieldExternalId(String fieldExternalId)
	{
		this.fieldExternalId = fieldExternalId;
	}

	/**
	 * @return ������ ���������
	 */
	public List<FieldFormulaAttribute> getAttributes()
	{
		return attributes;
	}

	/**
	 * @param attributes ������ ���������
	 */
	public void setAttributes(List<FieldFormulaAttribute> attributes)
	{
		this.attributes = attributes;
	}
}
