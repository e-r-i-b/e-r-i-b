package com.rssl.phizic.web.client.component;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.operations.widget.WeatherWidgetOperation;
import com.rssl.phizic.operations.widget.WidgetOperation;
import com.rssl.phizic.web.component.WidgetActionBase;
import com.rssl.phizic.web.component.WidgetForm;

/**
 * @author Erkin
 * @ created 18.06.2012
 * @ $Author$
 * @ $Revision$
 */

/**
 * Виджет "Погода"
 */
public class WeatherWidgetAction extends WidgetActionBase
{
	@Override
	protected WidgetOperation createWidgetOperation() throws BusinessException
	{
		return createOperation(WeatherWidgetOperation.class);
	}

	@Override
	protected void updateForm(WidgetForm form, WidgetOperation operation) throws BusinessException, BusinessLogicException
	{
		super.updateForm(form, operation);

		WeatherWidgetOperation weatherWidgetOperation = (WeatherWidgetOperation) operation;
		WeatherWidgetForm frm = (WeatherWidgetForm) form;
		frm.setWeatherServiceKey(weatherWidgetOperation.getWeatherServiceKey());
	}
}
