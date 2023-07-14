package com.rssl.phizic.messaging;

import com.rssl.phizic.common.types.annotation.ThreadSafe;
import com.rssl.phizic.module.Module;

/**
 * @author Erkin
 * @ created 09.03.2013
 * @ $Author$
 * @ $Revision$
 */

/**
 * ���������� �������� ���������
 */
@ThreadSafe
public interface MessageProcessor<T extends XmlMessage>
{
	/**
	 * ���������� ���������
	 * @param xmlRequest - ������
	 */
	public void processMessage(T xmlRequest);

	/**
	 * @return ������ �� � ���.
	 */
	public boolean writeToLog();

	/**
	 * ���������� ������
	 * @return
	 */
	public Module getModule();
}
