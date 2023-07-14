package com.rssl.phizic.logging;

/**
 * �������� ��� ���������� ������� � �����������
 * @author gladishev
 * @ created 20.05.2013
 * @ $Author$
 * @ $Revision$
 */
public interface LoggingAccessor
{
	/**
	 * @param parameters - ���������
	 * @return ��������� �� ����������� � ������� ������
	 */
	boolean needNormalModeLogging(Object... parameters);

	/**
	 * @param parameters - ���������
	 * @return ��������� �� ����������� � ����������� ������
	 */
	boolean needExtendedModeLogging(Object... parameters);
}
