package com.rssl.phizic.business.ima;

/**
 * @author akrenev
 * @ created 14.01.2014
 * @ $Author$
 * @ $Revision$
 *
 * ��������� ���������� �� ���
 */

public class IMAProductPart
{
	private Long id; // id � ����
	private Long type; // ��� ����� �� �����������
	private Long subType; // ������ ����� �� �����������
	private String name; // �������� ����� ��� ("������", "�������", "��������", "�������")

	/**
	 * @return �������������
	 */
	public Long getId()
	{
		return id;
	}

	/**
	 * ������ �������������
	 * @param id �������������
	 */
	public void setId(Long id)
	{
		this.id = id;
	}

	/**
	 * @return ��� ����� �� �����������
	 */
	public Long getType()
	{
		return type;
	}

	/**
	 * ������ ��� ����� �� �����������
	 * @param type ��� ����� �� �����������
	 */
	public void setType(Long type)
	{
		this.type = type;
	}

	/**
	 * @return ������ ����� �� �����������
	 */
	public Long getSubType()
	{
		return subType;
	}

	/**
	 * ������ ������ ����� �� �����������
	 * @param subType ������ ����� �� �����������
	 */
	public void setSubType(Long subType)
	{
		this.subType = subType;
	}

	/**
	 * @return �������� ����� ��� ("������", "�������", "��������", "�������")
	 */
	public String getName()
	{
		return name;
	}

	/**
	 * ������ �������� ����� ��� ("������", "�������", "��������", "�������")
	 * @param name �������� ����� ��� ("������", "�������", "��������", "�������")
	 */
	public void setName(String name)
	{
		this.name = name;
	}
}
