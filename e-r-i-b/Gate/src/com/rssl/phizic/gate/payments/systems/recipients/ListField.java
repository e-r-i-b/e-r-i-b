package com.rssl.phizic.gate.payments.systems.recipients;

import java.util.List;

/**
 * ���� c ������������� ���������� ���������� ��������.
 * getType ���������� ������ ���������� list ���� set.
 * @author Gainanov
 * @ created 25.07.2008
 * @ $Author$
 * @ $Revision$
 * TODO rename
 */
public interface ListField extends Field
{
	/**
	 * ��������, ������� ������ ���� ������������ � ������.
	 *
	 * @return ������ �������� ��� ������.
	 */
	List<ListValue> getValues();
}
