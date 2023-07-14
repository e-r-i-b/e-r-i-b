package com.rssl.phizicgate.sbrf.ws.listener;

import com.rssl.common.forms.doc.DocumentCommand;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;

/**
 * Created by IntelliJ IDEA.
 * User: Omeliyanchuk
 * Date: 09.01.2007
 * Time: 12:39:53
 * To change this template use File | Settings | File Templates.
 */

/**
 * ���������� ����������� ���������
 */
public interface OfflineRequestHandler
{
	/**
	 * ���������� ���������
	 * @param messageInfoContainer ���������� � ���������
	 * @param object ������ ��������� � ����������
	 * @return ��������� ���������, true- ��������� ������ ����������
	 * @throws GateException
	 * @throws GateLogicException
	 */
	public boolean handleMessage(InternalMessageInfoContainer messageInfoContainer, Object object) throws GateException, GateLogicException;

	/**
	 * @return ���������� ��������� � ������� ��������� ��������� ��������
	 */
	public DocumentCommand getUpdateCommand(InternalMessageInfoContainer messageInfoContainer);
}
