package com.rssl.phizic.logging.confirm;

/**
 * ������ ������� �� ���� ������
 * @author lukina
 * @ created 22.02.2012
 * @ $Author$
 * @ $Revision$
 */

public enum ConfirmState
{
	//��������� ������ ��������(���������� ����������).
	SUCCESSFUL("�������"),
	TIMOUT("��������� ��������"),
	SYSTEM_ERROR("�� ������� ��������� SMS-������"),
	CARD_ERROR("��� ��������� ������� �������"),
	CAP_ERROR("�� ������� ������������ ������������� CAP �������"),
	CLIENT_ERROR("������������ ���� ������"),
	NEW_PASSW("������ �� ������"),


	INIT_SUCCESS("�������", "�������� ���� ������������� �� ���"),
	INIT_FAILED("�� ������� ��������� SMS-������", "�������� ���� ������������� �� ���"),

	CONF_SUCCESS("�������", "���� ���-���� �������������"),
	CONF_FAILED("���������", "���� ���-���� �������������"),
	CONF_TIMEOUT("��������� ��������", "���� ���-���� �������������")
	;

	private String description;
	private String operationName;

	ConfirmState(String description)
	{
		this.description = description;
	}

	ConfirmState(String description, String operationName)
	{
		this(description);
		this.operationName = operationName;
	}

	/**
	 * @return ��������
	 */
	public String getDescription()
	{
		return description;
	}

	/**
	 * @return �������� ��������
	 */
	public String getOperationName()
	{
		return operationName;
	}
}
