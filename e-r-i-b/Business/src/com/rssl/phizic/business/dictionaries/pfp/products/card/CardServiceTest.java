package com.rssl.phizic.business.dictionaries.pfp.products.card;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.test.BusinessTestCaseBase;

import java.math.BigDecimal;

/**
 * @author akrenev
 * @ created 08.07.2013
 * @ $Author$
 * @ $Revision$
 * 
 * тест работы сервиса
 */

public class CardServiceTest extends BusinessTestCaseBase
{
	private static final CardService service = new CardService();

	/**
	 * тест работы сервиса
	 * @throws BusinessException
	 */
	public void testService() throws Exception
	{
		Card testEntity = new Card();
		testEntity.setName("testEntity");
		testEntity.setProgrammType(CardProgrammType.aeroflot);
		testEntity.setInputs(new BigDecimal(100));
		testEntity.setBonus(new BigDecimal(100));
		testEntity.setClause("testClause");
		testEntity.setCardIconId(1L);
		testEntity.setProgrammIconId(2L);
		testEntity.setDescription("testEntityDescription");
		testEntity.setRecommendation("testEntityRecommendation");
		CardDiagramParameters diagramParameters = new CardDiagramParameters();
		diagramParameters.setUseImage(false);
		diagramParameters.setColor("#000000");
		diagramParameters.setUseNet(true);
		testEntity.setDiagramParameters(diagramParameters);
		Card savedEntity = service.addOrUpdate(testEntity, null);
		Long id = savedEntity.getId();
		assertNotNull(service.findById(id, null));
		service.remove(savedEntity, null);
		assertNull(service.findById(id, null));
	}
}
