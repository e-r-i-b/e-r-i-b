package com.rssl.phizic.business.dictionaries.pfp.risk;

import com.rssl.phizic.test.BusinessTestCaseBase;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;

/**
 * @author akrenev
 * @ created 15.07.2013
 * @ $Author$
 * @ $Revision$
 *
 * Тест сервиса рисков
 */

public class RiskServiceTest extends BusinessTestCaseBase
{
	private static final RiskService service = new RiskService();

	/**
	 * тест сервиса
	 * @throws BusinessException
	 * @throws BusinessLogicException
	 */
	public void testRiskService() throws BusinessException, BusinessLogicException
	{
		Risk testEntity = new Risk();
		testEntity.setName("testEntity");
		Long testEntityId = service.addOrUpdate(testEntity, null).getId();
		Risk savedEntity = service.getById(testEntityId, null);
		assertNotNull(savedEntity);
		service.remove(savedEntity, null);
		assertNull(service.getById(testEntityId, null));
	}
}
