package com.rssl.phizic.business.web.widget.strategy;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.resources.external.EditableExternalResourceLink;
import com.rssl.phizic.business.resources.external.ExternalResourceService;
import com.rssl.phizic.business.web.ProductWidgetBase;
import com.rssl.phizic.business.web.WidgetObjectVisibility;
import com.rssl.phizic.context.PersonData;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.auth.Login;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author Erkin
 * @ created 22.01.2013
 * @ $Author$
 * @ $Revision$
 */
public abstract class ProductBlockWidgetInitializerBase<BlockWidget extends ProductWidgetBase> implements WidgetInitializer<BlockWidget>
{
	private static final ExternalResourceService resourceService = new ExternalResourceService();

	///////////////////////////////////////////////////////////////////////////

	protected abstract Class<? extends EditableExternalResourceLink> getProductLinkClass();

	public void init(BlockWidget widget) throws BusinessException, BusinessLogicException
	{
		PersonData personData = PersonContext.getPersonDataProvider().getPersonData();
		Login login = personData.getPerson().getLogin();

		Collection<? extends EditableExternalResourceLink> allLinks = resourceService.getInSystemLinks(login, getProductLinkClass());

		List<WidgetObjectVisibility> productsVisibility = new ArrayList<WidgetObjectVisibility>(allLinks.size());
		for (EditableExternalResourceLink link : allLinks)
			productsVisibility.add(new WidgetObjectVisibility(link.getId(), link.getShowInMain()));

		widget.setProductsVisibility(productsVisibility);
	}
}
