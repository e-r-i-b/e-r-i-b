package com.rssl.phizic.gate.monitoring.fraud.jms.processors;

/**
 * ��������� ������������ ������� �� �� ��
 *
 * @author khudyakov
 * @ created 13.06.15
 * @ $Author$
 * @ $Revision$
 */
public interface RequestProcessor
{
	/**
	 * @return true - ����� ����������
	 */
	boolean isEnabled();

	/**
	 * ��������� �������� ��� ��������
	 */
	void process() throws Exception;

	/**
	 * �������� ���������
	 */
	void rollback();
}
