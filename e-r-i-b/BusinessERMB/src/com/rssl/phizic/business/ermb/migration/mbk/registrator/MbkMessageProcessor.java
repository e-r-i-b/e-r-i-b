package com.rssl.phizic.business.ermb.migration.mbk.registrator;

import com.rssl.phizic.gate.ermb.MBKRegistration;
import com.rssl.phizic.business.ermb.migration.mbk.registrator.sender.ProcessResult;
import com.rssl.phizic.common.types.exceptions.IKFLException;

/**
 * ���������� ��������� � ����������� ��� (������)
 * @author Puzikov
 * @ created 09.07.14
 * @ $Author$
 * @ $Revision$
 */

public interface MbkMessageProcessor
{
	/**
	 * ���������� ��������� �� ��� (������)
	 * @param mbkMessage ��������� ���
	 * @return ��������� ��������� ���������
	 */
	public ProcessResult process(MBKRegistration mbkMessage) throws IKFLException;
}
