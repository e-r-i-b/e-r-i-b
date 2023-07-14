package com.rssl.phizic.operations.config;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.dictionaries.tariffPlan.TarifPlanConfigService;
import com.rssl.phizic.business.dictionaries.tariffPlan.TariffPlanConfig;
import com.rssl.phizic.operations.EditEntityOperation;
import com.rssl.phizic.operations.OperationBase;

import java.util.List;

/**
 * Операция просмотра/редактирования настроек курсов валют для тарифных планов
 *
 * @ author: Gololobov
 * @ created: 21.02.14
 * @ $Author$
 * @ $Revision$
 */
public class CurrencyRateConfigureOperation extends OperationBase implements EditEntityOperation
{
	//Список настроек тарифных планов курсов валют
	private List<TariffPlanConfig> tarifPlanConfigsList;
	private TarifPlanConfigService service = new TarifPlanConfigService();

	public void initialize() throws BusinessException
	{
		tarifPlanConfigsList = service.getTarifPlanConfigsList();
	}

	public void save() throws BusinessException, BusinessLogicException
	{
		service.addOrUpdateList(tarifPlanConfigsList);
	}

	public Object getEntity() throws BusinessException, BusinessLogicException
	{
		return tarifPlanConfigsList;
	}
}
