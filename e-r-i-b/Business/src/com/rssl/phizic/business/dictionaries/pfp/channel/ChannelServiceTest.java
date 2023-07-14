package com.rssl.phizic.business.dictionaries.pfp.channel;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.SimpleService;
import com.rssl.phizic.test.BusinessTestCaseBase;

/**
 * Тест сервиса каналов
 * @author komarov
 * @ created 12.08.13 
 * @ $Author$
 * @ $Revision$
 */

public class ChannelServiceTest extends BusinessTestCaseBase
{
	private static final ChannelService service = new ChannelService();
	private static final SimpleService simpleService = new SimpleService();

	/**
	 * тест сервиса
	 * @throws BusinessException
	 */
	public void testRiskService() throws BusinessException, BusinessLogicException
	{
		Channel testEntity = new Channel();
		testEntity.setName("testEntity");
		Long testEntityId = service.addOrUpdate(testEntity, null).getId();
		Channel savedEntity = service.getChannelById(testEntityId, null);
		assertNotNull(savedEntity);
		simpleService.remove(savedEntity, null);
		assertNull(service.getChannelById(testEntityId, null));
	}


}
