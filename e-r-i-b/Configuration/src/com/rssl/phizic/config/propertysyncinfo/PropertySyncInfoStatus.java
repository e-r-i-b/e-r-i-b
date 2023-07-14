package com.rssl.phizic.config.propertysyncinfo;

/**
 * Created with IntelliJ IDEA.
 * User: tisov
 * Date: 20.10.14
 * Time: 18:24
 * ������ �������� ������������� ��������
 */
public enum PropertySyncInfoStatus
{

	OK("����������������"),
	ERROR("������"),
	PROCESSING("� ���������");

	private String description;

	private PropertySyncInfoStatus(String description)
	{
		this.description = description;
	}

	public String getDescription()
	{
		return description;
	}
}
