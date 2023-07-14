package com.rssl.phizic.common.types.limits;

/**
 * ����� ����� �������
 *
 * @author khudyakov
 * @ created 25.09.2012
 * @ $Author$
 * @ $Revision$
 */
public enum ChannelType
{
	MOBILE_API("��������� ����������"),                 //��������� ����������
	INTERNET_CLIENT("�������� ������"),                 //�������� ������
	VSP("���"),                                         //���
	CALL_CENTR("��"),                                   //��
	SELF_SERVICE_DEVICE("���������� ����������������"), //���������� ����������������
	ERMB_SMS("���-����� ���������� �����"),             //����
    SOCIAL_API("���������� ����������"),                //���������� ����������
    ALL("��� ������");                                  //��� ������

	private String description;

	ChannelType(String description)
	{
		this.description = description;
	}

	public String getDescription()
	{
		return description;
	}
}
