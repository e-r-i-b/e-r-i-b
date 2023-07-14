package com.rssl.phizic.gate.longoffer.autopayment;

/**
 * @author osminin
 * @ created 28.01.2011
 * @ $Author$
 * @ $Revision$
 *
 * ��������� �����������
 */
public enum AutoPaymentStatus
{
	NEW(0L, "���������� ������� ������������� �� ���������."),        //����� = ������ (���������������, � ���� ������������, �� �� ��������������� � �������)
	ACTIVE(1L, "���������� ����� � ���������� ��������."),     //�������� = �������� (��������������� � ���� ������������, � � �������)
	UPDATING(2L, "����������� ��������� �����������."),   //����������� = �������������� (����������� � �������)
	BLOCKED(3L, ""),    //������������ = ������������� - ����������(�����������)
	DELETED(4L, ""),    //������ = ������� (���������� � ���� ������������ � ��������� �������) - ����������(�����������)
	NO_CREATE(5L, "");  //�� ���������� = ������� - ���������� (�����������)

	private Long value;
	private String hint;

	AutoPaymentStatus(Long value, String hint)
	{
		this.value = value;
		this.hint = hint;
	}

	/**
	 * @return ��������
	 */
	public Long getValue()
	{
		return value;
	}

	/**
	 * @return ���������
	 */
	public String getHint()
	{
		return hint;
	}

	public static AutoPaymentStatus fromValue(Long state)
	{
		if (state.equals(0L))
			return NEW;
		if (state.equals(1L))
			return ACTIVE;
		if (state.equals(2L))
			return UPDATING;
		if (state.equals(3L))
			return BLOCKED;
		if (state.equals(4L))
			return DELETED;
		if (state.equals(5L))
			return NO_CREATE;
		throw new IllegalArgumentException("����������� �������� ��� AutoPaymentStatus " + state);
	}
}
