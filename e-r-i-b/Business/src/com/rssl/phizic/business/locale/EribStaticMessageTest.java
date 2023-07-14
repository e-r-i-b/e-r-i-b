package com.rssl.phizic.business.locale;

import com.rssl.phizic.locale.entities.ERIBStaticMessage;
import com.rssl.phizic.locale.services.EribStaticMessageService;
import com.rssl.phizic.test.BusinessTestCaseBase;
import com.rssl.phizic.utils.RandomHelper;

/**
 * @author koptyaev
 * @ created 11.09.2014
 * @ $Author$
 * @ $Revision$
 */
public class EribStaticMessageTest extends BusinessTestCaseBase
{
	private static final EribStaticMessageService service = new EribStaticMessageService();


	private ERIBStaticMessage generateMessage()
	{
		ERIBStaticMessage message = new ERIBStaticMessage();
		message.setBundle(RandomHelper.rand(10));
		message.setKey(RandomHelper.rand(10));
		message.setLocaleId("RU");
		message.setMessage(RandomHelper.rand(100));
		return message;
	}

	/**
	 * Тест
	 * @throws Exception
	 */
	public void test() throws Exception
	{
		ERIBStaticMessage message = service.add(generateMessage());
		assertTrue("Сохранение не произошло", message!=null);
		assertTrue(message.getId()!=null);
		service.delete(message);

	}

}
