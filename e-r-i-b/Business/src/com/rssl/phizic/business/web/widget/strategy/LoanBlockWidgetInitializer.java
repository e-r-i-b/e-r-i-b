package com.rssl.phizic.business.web.widget.strategy;

import com.rssl.phizic.business.resources.external.EditableExternalResourceLink;
import com.rssl.phizic.business.resources.external.LoanLink;
import com.rssl.phizic.business.web.LoanBlockWidget;

/**
 * @author Erkin
 * @ created 22.01.2013
 * @ $Author$
 * @ $Revision$
 */
public class LoanBlockWidgetInitializer extends ProductBlockWidgetInitializerBase<LoanBlockWidget>
{
	protected Class<? extends EditableExternalResourceLink> getProductLinkClass()
	{
		return LoanLink.class;
	}
}
