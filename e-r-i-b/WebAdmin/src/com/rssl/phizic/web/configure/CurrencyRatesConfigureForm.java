package com.rssl.phizic.web.configure;

import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.phizic.business.dictionaries.tariffPlan.TariffPlanConfig;
import com.rssl.phizic.web.common.EditFormBase;

import java.util.List;

/**
 * Форма настройки курсов валют для тарифных планов клиентов
 *
 * @ author: Gololobov
 * @ created: 21.02.14
 * @ $Author$
 * @ $Revision$
 */
public class CurrencyRatesConfigureForm extends EditFormBase
{
	private static List<TariffPlanConfig> tarifPlanConfigList;
	public static final Form EDIT_FORM = createForm();

	private static Form createForm()
	{
		FormBuilder formBuilder = new FormBuilder();
		return formBuilder.build();
	}

	public List<TariffPlanConfig> getTarifPlanConfigList()
	{
		return tarifPlanConfigList;
	}

	public void setTarifPlanConfigList(List<TariffPlanConfig> tarifPlanConfigList)
	{
		this.tarifPlanConfigList = tarifPlanConfigList;
	}
}
