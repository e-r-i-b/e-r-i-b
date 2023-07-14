package com.rssl.phizic.gate.owners.client;

import com.rssl.phizic.gate.clients.Client;
import com.rssl.phizic.gate.owners.person.Profile;

/**
 * Расширенный интерфейс клиента
 *
 * @author khudyakov
 * @ created 06.05.14
 * @ $Author$
 * @ $Revision$
 */
public interface ExtendedClient extends Client
{
	/**
	 * Вернуть текущий профиль клиента в ЕСУШ
	 * @return профиль
	 */
	Profile asCurrentPerson() throws Exception;

	/**
	 * Вернуть профиль клиента в ЕСУШ
	 * @return профиль
	 */
	Profile asAbstractPerson() throws Exception;
}
