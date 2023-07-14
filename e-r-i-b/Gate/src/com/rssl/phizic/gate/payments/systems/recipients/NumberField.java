package com.rssl.phizic.gate.payments.systems.recipients;

/**
 * ���� ��� ����� �������� ��������
 * ���������� ������ ���������� ��� number
 * @author krenev
 * @ created 15.11.2010
 * @ $Author$
 * @ $Revision$
 */
public interface NumberField extends Field
{
	/**
	 * �������� ��������� ��������
	 * (���������� ������ ����� �������)
	 * @return ��������.
	 */
	Integer getNumberPrecision();
}
