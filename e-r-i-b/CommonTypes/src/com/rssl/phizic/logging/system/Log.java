package com.rssl.phizic.logging.system;

/**
 * @author eMakarov
 * @ created 16.03.2009
 * @ $Author$
 * @ $Revision$
 */
@SuppressWarnings({"ClassNameSameAsAncestorName"})
public interface Log extends org.apache.commons.logging.Log
{
	LogLevel resolveLogLevelName(String firstchar);

	/**
	 * ��������� ������ ��� �������
	 * @param message ���������
	 * @return ������
	 */
	Object createEntry(Object message);

	/**
	 * ��������� ������ ��� �������
	 * @param message ���������
	 * @param t ����������
	 * @return ������
	 */
	Object createEntry(Object message, Throwable t);
}
