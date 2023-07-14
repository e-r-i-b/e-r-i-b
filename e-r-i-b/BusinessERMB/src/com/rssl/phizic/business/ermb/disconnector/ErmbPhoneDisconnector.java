package com.rssl.phizic.business.ermb.disconnector;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.gate.mobilebank.PhoneDisconnectionReason;

/**
 * ��������� �������� �� ����
 * @author Puzikov
 * @ created 13.04.15
 * @ $Author$
 * @ $Revision$
 */

public interface ErmbPhoneDisconnector
{
	/**
	 * ��������� ������� �� ����
	 * @param phoneNumber ����� ��������
	 * @param reason ������� ����������
	 */
	void disconnect(String phoneNumber, PhoneDisconnectionReason reason) throws BusinessException, BusinessLogicException;
}
