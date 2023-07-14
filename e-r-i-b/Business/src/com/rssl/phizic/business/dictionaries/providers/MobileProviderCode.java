package com.rssl.phizic.business.dictionaries.providers;

/**
 * �������� ��������� �����, �� �������� ���� ����������� �������� ������� �������
 * @author miklyaev
 * @ created 21.01.14
 * @ $Author$
 * @ $Revision$
 */

public class MobileProviderCode
{
	protected Long id;
	private String code;
	private String name;
	private String externalId;

	/**
	 * @return ������������� ��������� � �������
	 */
	public Long getId()
	{
		return id;
	}

	/**
	 * ������ ������������� ���������
	 * @param id - �������������
	 */
	public void setId(Long id)
	{
		this.id = id;
	}

	/**
	 * @return ��� ��������� ��������� �����
	 */
	public String getCode()
	{
		return code;
	}

	/**
	 * ������ ��� ��������� ��������� �����
	 * @param code - ���
	 */
	public void setCode(String code)
	{
		this.code = code;
	}

	/**
	 * @return �������� ��������� ��������� �����
	 */
	public String getName()
	{
		return name;
	}

	/**
	 * ������ �������� ��������� ��������� �����
	 * @param name - ��������
	 */
	public void setName(String name)
	{
		this.name = name;
	}

	/**
	 * @return ������� ������������� ��������� ��������� �����
	 */
	public String getExternalId()
	{
		return externalId;
	}

	/**
	 * ������ ������� ������������� ��������� ��������� �����
	 * @param externalId - ������� �������������
	 */
	public void setExternalId(String externalId)
	{
		this.externalId = externalId;
	}
}
