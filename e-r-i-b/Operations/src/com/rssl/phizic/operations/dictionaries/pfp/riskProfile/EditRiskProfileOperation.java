package com.rssl.phizic.operations.dictionaries.pfp.riskProfile;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.ResourceNotFoundBusinessException;
import com.rssl.phizic.business.dictionaries.pfp.common.DictionaryProductType;
import com.rssl.phizic.business.dictionaries.pfp.products.types.ProductTypeParameters;
import com.rssl.phizic.business.dictionaries.pfp.products.types.ProductTypeParametersService;
import com.rssl.phizic.business.dictionaries.pfp.riskProfile.ProductType;
import com.rssl.phizic.business.dictionaries.pfp.riskProfile.RiskProfile;
import com.rssl.phizic.business.dictionaries.pfp.riskProfile.RiskProfileService;
import com.rssl.phizic.business.dictionaries.pfp.riskProfile.RiskProfileUtil;
import com.rssl.phizic.common.types.SegmentCodeType;
import com.rssl.phizic.operations.dictionaries.synchronization.EditDictionaryEntityOperationBase;

import java.util.*;

/**
 * @author akrenev
 * @ created 10.02.2012
 * @ $Author$
 * @ $Revision$
 *
 * Операция редактирования риск-профиля
 */

public class EditRiskProfileOperation extends EditDictionaryEntityOperationBase
{
	private static final RiskProfileService riskProfileService = new RiskProfileService();
	private static final ProductTypeParametersService productTypeService = new ProductTypeParametersService();
	private RiskProfile profile;

	/**
	 * инициализация операции
	 * @param id идентификатор риск-профиля
	 * @throws BusinessException
	 */
	public void initialize(Long id) throws BusinessException
	{
		if (id == null)
			profile = riskProfileService.newRiskProfile();
		else
			profile = riskProfileService.getById(id, getInstanceName());

		if(profile == null)
			throw new ResourceNotFoundBusinessException("В системе не найден риск-профиль с id: " + id, RiskProfile.class);
	}

	public RiskProfile getEntity()
	{
		return profile;
	}

	protected void doSave() throws BusinessLogicException, BusinessException
	{
		if (!RiskProfileUtil.checkProductWeightsMap(getEntity().getProductsWeights()))
			throw new BusinessLogicException("Сумма процентов в портфеле должна составлять 100%.");

		if (riskProfileService.isExistCrossingRiskProfiles(profile.getId(), profile.getMinWeight(), profile.getMaxWeight(), profile.getSegment(), getInstanceName()))
			throw new BusinessLogicException("Риск-профиль с таким количеством баллов уже существует в системе. Пожалуйста, укажите другое количество баллов.");

		riskProfileService.addOrUpdate(profile, getInstanceName());
	}

	/**
	 * @return настройки сегментов для типов продуктов
	 * @throws BusinessException
	 */
	public Map<ProductType, List<SegmentCodeType>> getProductTypeSegmentDependence() throws BusinessException
	{
		Map<ProductType, List<SegmentCodeType>> dependence = new HashMap<ProductType, List<SegmentCodeType>>();
		for (ProductType productType : RiskProfileUtil.getProductTypes())
		{
			Set<SegmentCodeType> segments = new HashSet<SegmentCodeType>();
			for (DictionaryProductType dictionaryProductType : productType.getDictionaryProductTypes())
			{
				ProductTypeParameters parameters = productTypeService.findByProductType(dictionaryProductType, getInstanceName());
				if (parameters.isUse())
					segments.addAll(parameters.getTargetGroup());
			}
			dependence.put(productType, new ArrayList<SegmentCodeType>(segments));
		}
		return dependence;
	}
}
