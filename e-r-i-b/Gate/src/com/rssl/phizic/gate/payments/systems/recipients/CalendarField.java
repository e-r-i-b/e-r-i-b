package com.rssl.phizic.gate.payments.systems.recipients;

/**
 * @author Gainanov
 * @ created 18.08.2008
 * @ $Author$
 * @ $Revision$
 */
public interface CalendarField extends Field
{
	/**
	 * �������� ������ ������ (�����������, �������� �� �����)
	 * @return ������ ������
	 */
	public CalendarFieldPeriod getPeriod();
}
