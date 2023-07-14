package com.rssl.phizic.business.ermb.sms.messaging;

import com.rssl.phizic.business.ermb.sms.command.Command;
import com.rssl.phizic.module.Module;
import com.rssl.phizic.session.PersonSession;
import com.rssl.phizic.text.MessageComposer;

/**
 * @author Gulov
 * @ created 25.06.2013
 * @ $Author$
 * @ $Revision$
 */

/**
 * ����������� ��������� � �������� ��� ���������� �������
 */
public interface ExecutorRequisites
{
	/**
	 * ��������� ��������������, ������� ������
	 * @return ������
	 */
	PersonSession authenticate();

	/**
	 * ������� �������
	 * @return �������
	 */
	Command createCommand();

	/**
	 * �������� ������
	 * @return ������
	 */
	Module getModule();

	/**
	 * �������� �������
	 * @return �������
	 */
	String getPhone();

	/**
	 * ��������� ������ �� �������� ���.
	 * @param command - �������
	 * @param messageComposer - ���������� ��������� ��� �������
	 */
	void handleInactiveExternalSystemException(Command command, MessageComposer messageComposer);

	/**
	 * ��������, ������� ���������� ��������� ����� ����� ��������� ���������� �������
	 * @param command - �������
	 */
	void doAfterExecute(Command command);
}
