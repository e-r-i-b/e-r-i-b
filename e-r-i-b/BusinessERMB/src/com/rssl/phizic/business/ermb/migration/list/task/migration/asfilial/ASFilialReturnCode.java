package com.rssl.phizic.business.ermb.migration.list.task.migration.asfilial;

/**
 * User: moshenko
 * Date: 27.03.2013
 * Time: 20:44:48
 * ���� ���������
 */
public enum ASFilialReturnCode
{
	BUSINES_ERROR(-666L),               //������ ������(�����)
	MB_NOT_CONNECT(-40L),               //-40 - ������ �� ����������(3.4. ��������� CHECK_MB ��������� ���������� � ����������� ������ )
	MB_HAVE_THIRD_PARTIES_ACCOUNTS(-6L),//� ������� � ��� ���������������� �������� ����� � �������������� � ����� ������ �� ��� ������� ���
	CONFIRM_HOLDER_ERR(-5L),            //��� ������ %s ��� ������ �� ������ ��� ������������� ��������� ������.
	FORMAT_ERROR(-4L),                  //������ �������"
	DEPARTMENT_NOT_FOUND(-3L),          //������������� �� �������
	PROFILE_NOT_FOUND(-2L),             //������� �� ������
	DUPLICATION_PHONE_ERR(-1L),         // ����� �������� �����: ���� ��� ��������� ��������� ��������� ���������������� ��  ������ ���
	OK(0L),                             //��
	TECHNICAL_ERROR(1L);                //����������� ������(�����)

	private Long returnCode;

	ASFilialReturnCode(Long returnCode)
	{
		this.returnCode = returnCode;
	}

	public Long toValue()
	{
		return returnCode;
	}

	public Long getValue()
	{
		return returnCode;
	}
}
