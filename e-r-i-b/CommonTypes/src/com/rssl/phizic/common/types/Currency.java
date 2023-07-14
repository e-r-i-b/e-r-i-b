package com.rssl.phizic.common.types;

import java.io.Serializable;

/**
 * @author Kosyakov
 * @ created
 * @ $Author: Omeliyanchuk $
 * @ $Revision: 2051 $
 */
public interface Currency extends Serializable
{
	/**
	 * @return �������� ��� ������ ISO (numeric)
	 */
	String getNumber ();

	/**
	 * @return ��������� ��� ������ ISO (alphabetical)
	 */
	String getCode ();

	/**
	 * @return ������������
	 */
	String getName ();

	/**
	 *
	 * @return ������������� ������
	 */
	String getExternalId();

	/**
	 * ��������� �����, ��� ������ ���������.
	 * @param c
	 * @return true - �����, false - �� �����
	 */
	boolean compare(Currency c);
}
