package com.rssl.phizic.business.ermb.sms.command;

import com.rssl.phizic.common.types.annotation.MandatoryParameter;
import com.rssl.phizic.common.types.annotation.NonThreadSafe;
import com.rssl.phizic.person.PersonTask;
import com.rssl.phizic.task.ExecutableTask;

/**
 * @author Erkin
 * @ created 05.12.2012
 * @ $Author$
 * @ $Revision$
 */

/**
 * ���-�������
 */
@NonThreadSafe
public interface Command extends PersonTask, ExecutableTask
{
	/**
	 * ������� �������� �������
	 * @return �������� �������
	 */
	String getCommandName();

	/**
	 * ���������� ������� �����������
	 * @param phoneTransmitter
	 */
	@MandatoryParameter
	void setPhoneTransmitter(String phoneTransmitter);
}
