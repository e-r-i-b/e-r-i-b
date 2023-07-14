package com.rssl.phizic.gate.utils;

/**
 * ��������� ������� �������
 *
 * @author khudyakov
 * @ created 12.03.2012
 * @ $Author$
 * @ $Revision$
 */
public interface ExternalSystem
{
	/**
	 * @return ��������
	 */
	String getName();

	/**
	 * @return ������������� ������� �������(� ������ ������ ���������� uuid ��������, � ������ ���� systemId)
	 */
	String getUUID();

}
