package com.rssl.phizic.business.pfp.handlers;

import com.rssl.common.forms.DocumentException;
import com.rssl.common.forms.DocumentLogicException;
import com.rssl.common.forms.doc.StateMachineEvent;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.dictionaries.pfp.common.DictionaryProductType;
import com.rssl.phizic.business.dictionaries.pfp.products.types.ProductTypeParameters;
import com.rssl.phizic.business.dictionaries.pfp.products.types.ProductTypeParametersService;
import com.rssl.phizic.business.dictionaries.pfp.riskProfile.ProductType;
import com.rssl.phizic.business.dictionaries.pfp.riskProfile.RiskProfile;
import com.rssl.phizic.business.pfp.PersonalFinanceProfile;
import com.rssl.phizic.business.pfp.risk.profile.PersonRiskProfile;
import com.rssl.phizic.common.types.SegmentCodeType;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;

import java.util.List;
import java.util.Map;

/**
 * Устанавливает риск профиль клиента
 * @author komarov
 * @ created 26.06.2013 
 * @ $Author$
 * @ $Revision$
 */

public class CreatePersonRiskProfileHandler extends PersonalFinanceProfileHandlerBase
{
	private static final ProductTypeParametersService productTypeParametersService = new ProductTypeParametersService();

	protected void process(PersonalFinanceProfile profile, StateMachineEvent stateMachineEvent) throws DocumentException, DocumentLogicException
	{
		PersonRiskProfile personRiskProfile = profile.getPersonRiskProfile();
		Map<ProductType, Long> personProductsWeights = personRiskProfile.getProductsWeights();
		if(MapUtils.isEmpty(personProductsWeights))
		{
			RiskProfile defaultRiskProfile = profile.getDefaultRiskProfile();
			personProductsWeights.putAll(defaultRiskProfile.getProductsWeights());
		}
		List<DictionaryProductType> availableProducts = personRiskProfile.getAvailableProducts();
		if (CollectionUtils.isNotEmpty(availableProducts))
			return;

		try
		{
			SegmentCodeType segment = profile.getDefaultRiskProfile().getSegment();
			for (DictionaryProductType dictionaryProductType : DictionaryProductType.values())
			{
				ProductTypeParameters parameters = productTypeParametersService.findByProductType(dictionaryProductType);
				if (parameters != null && parameters.isUse() && parameters.getTargetGroup().contains(segment))
					availableProducts.add(dictionaryProductType);
			}
		}
		catch (BusinessException e)
		{
			throw new DocumentException(e);
		}
	}
}
