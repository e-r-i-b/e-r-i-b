package com.rssl.phizic.operations.config.writers;

import java.util.Map;

/**
 * �������� �������� � ��
 *
 * @author khudyakov
 * @ created 15.07.15
 * @ $Author$
 * @ $Revision$
 */
public interface PropertiesWriter
{
	/**
	 * ��������� ���������
	 * @param properties ���������
	 */
	void write(Map<String, String> properties);
}
