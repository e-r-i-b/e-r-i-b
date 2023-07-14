package com.rssl.phizic.operations.deposits;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.config.DbPropertyService;
import com.rssl.phizic.config.PropertyCategory;
import com.rssl.phizic.config.promoCodesDeposit.PromoCodesDepositConfig;
import com.rssl.phizic.operations.EditEntityOperation;
import com.rssl.phizic.operations.OperationBase;

/**
 * Операция просмотра/редактирования настроек промо - кодов для открытия вкладов
 *
 * @ author: Gololobov
 * @ created: 12.12.14
 * @ $Author$
 * @ $Revision$
 */
public class PromoCodeDepositOperation extends OperationBase implements EditEntityOperation
{
	private PromoCodesDepositConfig promoCodesDepositConfig;

	public void initialize() throws BusinessException
	{
		promoCodesDepositConfig = ConfigFactory.getConfig(PromoCodesDepositConfig.class);
	}

	public void save() throws BusinessException, BusinessLogicException
	{
		if (promoCodesDepositConfig != null)
			DbPropertyService.updatePropertiesWithLikeKeys(PromoCodesDepositConfig.PROMOCODES_PROPERTIES_PREFIX,
					promoCodesDepositConfig.getPromoCodeSettingsForSaveMap(), PropertyCategory.Phizic,
					DbPropertyService.getDbInstance(PropertyCategory.Phizic.getValue()));
	}

	public Object getEntity() throws BusinessException, BusinessLogicException
	{
		return promoCodesDepositConfig;
	}
}
