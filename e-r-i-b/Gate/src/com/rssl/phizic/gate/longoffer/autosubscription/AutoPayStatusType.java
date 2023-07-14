package com.rssl.phizic.gate.longoffer.autosubscription;

/**
 * @author vagin
 * @ created: 19.01.2012
 * @ $Author$
 * @ $Revision$
 * ��������� �������� �� ����������
 */
public enum AutoPayStatusType
{
	New ("�����", "����������� ����������� �����������: ��������� ��� � ���������� 5-�� ������� ����� �� ����� 900."),
    OnRegistration ("�� �����������", "���������� ������� ������������� �� ���������."),
    Confirmed ("������ �����������", "����������  ������� ��������������� � �����."),
    Active("��������", "���������� ����� � ���������� ��������."),
    WaitForAccept("������� ������������� �������", "����������� ����������� �����������: ��������� ��� � ���������� 5-�� ������� ����� �� ����� 900."),
    ErrorRegistration ("�� ���������������", "�������� � ���������� �����������."),
    Paused ("�������������", "���������� ������������� �� ������ �������. ����������� ������ ������ ��� ����������� ������."),
	WaitingForActivation("�������� ���������", "������� ������������� ��������."),
    Closed ("������", "���������� ���� ������."),
	Empty("", "");  //������ ������������ ��� ���������� ������� � ������ ��������

	private String description;
	private String hint;

	AutoPayStatusType(String description, String hint)
	{
		this.description = description;
		this.hint = hint;
	}

	/**
	 * @return ��������
	 */
	public String getDescription()
	{
		return description;
	}

	/**
	 * @return ���������
	 */
	public String getHint()
	{
		return hint;
	}

	/**
	 * @param type �������������� ������
	 * @return ������ ������, ���� ���������� ������ null, ����� ���������� ������
	 */
	public static AutoPayStatusType getEmptyIfNull(AutoPayStatusType type)
	{
		return type == null ? AutoPayStatusType.Empty : type;
	}
}
