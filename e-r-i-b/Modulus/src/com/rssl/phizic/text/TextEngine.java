package com.rssl.phizic.text;

import com.rssl.phizic.engine.Engine;

/**
 * @author Erkin
 * @ created 28.05.2013
 * @ $Author$
 * @ $Revision$
 */

/**
 * ������ ��������
 */
public interface TextEngine extends Engine
{
	/**
	 * ������ ������������� ����������� ���������
	 * @return ����� ���������� ���������
	 */
	MessageComposer createMessageComposer();
}
