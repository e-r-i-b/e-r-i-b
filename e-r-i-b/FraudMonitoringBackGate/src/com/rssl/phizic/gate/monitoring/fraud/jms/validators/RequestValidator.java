package com.rssl.phizic.gate.monitoring.fraud.jms.validators;

/**
 * ��������� ������� �� �� ��
 *
 * @author khudyakov
 * @ created 13.06.15
 * @ $Author$
 * @ $Revision$
 */
public interface RequestValidator
{
	/**
	 * @return true - �������� ������
	 */
	boolean validate();

	/**
	 * @return ��������� �� ������
	 */
	String getMessage();
}
