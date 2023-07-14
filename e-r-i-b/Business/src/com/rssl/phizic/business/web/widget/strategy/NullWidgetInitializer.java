package com.rssl.phizic.business.web.widget.strategy;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.web.Widget;

/**
 * @author Erkin
 * @ created 22.01.2013
 * @ $Author$
 * @ $Revision$
 */
public final class NullWidgetInitializer<TWidget extends Widget> implements WidgetInitializer<TWidget>
{
	public void init(TWidget widget) throws BusinessException, BusinessLogicException
	{
	}
}
