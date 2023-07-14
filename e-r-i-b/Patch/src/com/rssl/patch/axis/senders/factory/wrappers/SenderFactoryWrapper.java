package com.rssl.patch.axis.senders.factory.wrappers;

import com.rssl.patch.axis.senders.Connection;
import org.apache.axis.components.net.SocketFactory;

/**
 * ��������� ����������� �������
 *
 * @author khudyakov
 * @ created 08.06.15
 * @ $Author$
 * @ $Revision$
 */
public interface SenderFactoryWrapper
{
	/**
	 * @param connection connection
	 * @return �������
	 */
	SocketFactory getFactory(Connection connection);
}
