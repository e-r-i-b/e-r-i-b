package com.rssl.phizic.business.web.widget.strategy;

import com.rssl.phizic.business.resources.external.EditableExternalResourceLink;
import com.rssl.phizic.business.resources.external.IMAccountLink;
import com.rssl.phizic.business.web.IMAccountBlockWidget;

/**
 * @author Erkin
 * @ created 22.01.2013
 * @ $Author$
 * @ $Revision$
 */
public class IMAccountBlockWidgetInitializer extends ProductBlockWidgetInitializerBase<IMAccountBlockWidget>
{
	protected Class<? extends EditableExternalResourceLink> getProductLinkClass()
	{
		return IMAccountLink.class;
	}
}
