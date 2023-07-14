package com.rssl.phizic.common.types.documents.templates;

/**
 * �������� �������� ���������� �������� ����������
 *
 * @author khudyakov
 * @ created 20.05.14
 * @ $Author$
 * @ $Revision$
 */
public enum ActivityCode
{
	REMOVED("������ ��������"),                 //������ ��������
	ACTIVE("��������");                         //��������


	private String description;

	ActivityCode(String description)
	{
		this.description = description;
	}

	/**
	 * @return ��������
	 */
	public String getDescription()
	{
		return description;
	}
}
