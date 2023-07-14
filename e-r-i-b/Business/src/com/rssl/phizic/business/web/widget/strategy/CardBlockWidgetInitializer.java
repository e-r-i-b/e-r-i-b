package com.rssl.phizic.business.web.widget.strategy;

import com.rssl.phizic.business.resources.external.CardLink;
import com.rssl.phizic.business.resources.external.EditableExternalResourceLink;
import com.rssl.phizic.business.web.CardBlockWidget;

/**
 * @author Erkin
 * @ created 22.01.2013
 * @ $Author$
 * @ $Revision$
 */
public class CardBlockWidgetInitializer extends ProductBlockWidgetInitializerBase<CardBlockWidget>
{
	protected Class<? extends EditableExternalResourceLink> getProductLinkClass()
	{
		return CardLink.class;
	}
}
