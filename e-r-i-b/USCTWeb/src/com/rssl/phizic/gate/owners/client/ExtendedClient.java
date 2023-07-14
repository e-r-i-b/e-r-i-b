package com.rssl.phizic.gate.owners.client;

import com.rssl.phizic.gate.clients.Client;
import com.rssl.phizic.gate.owners.person.Profile;

/**
 * ����������� ��������� �������
 *
 * @author khudyakov
 * @ created 06.05.14
 * @ $Author$
 * @ $Revision$
 */
public interface ExtendedClient extends Client
{
	/**
	 * ������� ������� ������� ������� � ����
	 * @return �������
	 */
	Profile asCurrentPerson() throws Exception;

	/**
	 * ������� ������� ������� � ����
	 * @return �������
	 */
	Profile asAbstractPerson() throws Exception;
}
