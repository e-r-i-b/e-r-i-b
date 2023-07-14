package com.rssl.phizic.business.dictionaries.pfp.riskProfile;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.test.BusinessTestCaseBase;

import java.util.Map;
import java.util.Random;

/**
 * @author akrenev
 * @ created 10.02.2012
 * @ $Author$
 * @ $Revision$
 */
public class RiskProfileServiceTest extends BusinessTestCaseBase
{
	private static final RiskProfileService profileService = new RiskProfileService();
	private static final Random random = new Random();

	private void updateProductWeightsMap(Map<ProductType, Long> map, int sum) throws BusinessException
	{
		int currentBalance = sum;
		ProductType lastProductType = null;
		for (Map.Entry<ProductType, Long> entry: map.entrySet())
		{
			int weight = random.nextInt(currentBalance);
			entry.setValue(Long.valueOf(weight));
			currentBalance -= weight;
			lastProductType = entry.getKey();
		}
		map.put(lastProductType, Long.valueOf(map.get(lastProductType).longValue() + currentBalance));
	}

	public void testQuestionService() throws BusinessException, BusinessLogicException
	{
		RiskProfile profile = profileService.newRiskProfile();
		profile.setName("name_test");
		profile.setDescription("description_test");
		profile.setMaxWeight(Long.valueOf(20));
		profile.setMinWeight(Long.valueOf(0));
		updateProductWeightsMap(profile.getProductsWeights(),100);
		assertTrue(RiskProfileUtil.checkProductWeightsMap(profile.getProductsWeights()));
		profileService.addOrUpdate(profile, null);

		Long id = profile.getId();
		RiskProfile existProfile = profileService.getById(id, null);
		assertNotNull(existProfile);
	}
}
