package com.rssl.phizic.esb.ejb.mock;

import javax.jms.Message;

/**
 * @author akrenev
 * @ created 27.05.2015
 * @ $Author$
 * @ $Revision$
 *
 * ����������� ������ ���������� � ����
 */

public interface ESBMockMessageParser<M>
{
	/**
	 * ���������� ���������
	 * @param textMessage ����� ���������
	 * @param source �������� ���������
	 * @return ������������ ���������
	 */
	M parseMessage(com.rssl.phizic.common.types.TextMessage textMessage, Message source);
}
