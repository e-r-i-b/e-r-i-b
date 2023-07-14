package com.rssl.phizic.business.web.widget.strategy;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.web.WidgetDefinition;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.context.PersonData;
import com.rssl.phizic.security.PermissionUtil;
import org.apache.commons.collections.CollectionUtils;

/**
 * @author Dorzhinov
 * @ created 14.01.2013
 * @ $Author$
 * @ $Revision$
 */
public class CardWidgetAccessor extends GenericWidgetAccessor
{
	@Override
	protected boolean impliesMainPermission(WidgetDefinition widgetDefinition)
	{
		return PermissionUtil.impliesServiceRigid("AccountAndCardInfo");
	}

	@Override
	protected boolean checkData(WidgetDefinition widgetDefinition) throws BusinessException, BusinessLogicException
	{
		PersonData personData = PersonContext.getPersonDataProvider().getPersonData();
		return CollectionUtils.isNotEmpty(personData.getCards());
	}
}
