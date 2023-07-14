package com.rssl.phizic.business.web.widget.strategy;

import com.rssl.phizic.business.resources.external.EditableExternalResourceLink;
import com.rssl.phizic.business.resources.external.AccountLink;
import com.rssl.phizic.business.web.AccountBlockWidget;

/**
 * @author Erkin
 * @ created 22.01.2013
 * @ $Author$
 * @ $Revision$
 */
public class AccountBlockWidgetInitializer extends ProductBlockWidgetInitializerBase<AccountBlockWidget>
{
	protected Class<? extends EditableExternalResourceLink> getProductLinkClass()
	{
		return AccountLink.class;
	}
}
