package com.rssl.phizic.web.common.socialApi.contacts;

import com.rssl.phizic.web.common.client.contacts.ShowContactsMessageAction;

import java.util.HashMap;
import java.util.Map;

/**
 * @author lepihina
 * @ created 11.06.14
 * $Author$
 * $Revision$
 * Отображение клиенту информирующего сообщения об услуге адресной книги
 */
public class ShowContactsMessageMobileAction extends ShowContactsMessageAction
{
	protected Map<String, String> getKeyMethodMap()
	{
		Map<String, String> map = new HashMap<String, String>();
		map.put("next", "next");
		return map;
	}
}
