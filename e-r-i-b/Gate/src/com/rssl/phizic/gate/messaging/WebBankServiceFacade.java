package com.rssl.phizic.gate.messaging;

import com.rssl.phizic.gate.cache.MessagesCacheManager;

/**
 * @author Evgrafov
 * @ created 28.08.2006
 * @ $Author: krenev $
 * @ $Revision: 10491 $
 * @deprecated �� ������������ ������ �����. ������ � ������� �������� (RetailJNI, ���)
 * ������ �������������� � ���������� �����. ������� � �������� ������ ����� java-���������.
 */
@Deprecated
public interface WebBankServiceFacade extends OfflineServiceFacade, OnlineServiceFacade
{
	/**
	 * ��������� ��� ��������� ���������
	 * @return MessagesCacheManager
	 */
	MessagesCacheManager getMessagesCacheManager();

}
