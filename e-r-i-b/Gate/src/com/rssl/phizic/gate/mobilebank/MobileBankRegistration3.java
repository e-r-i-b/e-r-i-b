package com.rssl.phizic.gate.mobilebank;

import com.rssl.phizic.common.types.limits.ChannelType;

import java.util.Calendar;

/**
 * @author osminin
 * @ created 17.05.2013
 * @ $Author$
 * @ $Revision$
 */
public interface MobileBankRegistration3 extends MobileBankRegistration
{   
	/**
	 * @return ���� ��������� �����������
	 */
	Calendar getLastRegistrationDate();

	/**
	 * @return �����, ����� ������� ��� �������� ����
	 */
	ChannelType getChannelType();
}
