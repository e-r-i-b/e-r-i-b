package com.rssl.phizic.business.documents.payments.source;

import com.rssl.common.forms.DocumentException;
import com.rssl.common.forms.DocumentLogicException;
import com.rssl.phizic.ApplicationAutoRefreshConfig;
import com.rssl.phizic.auth.modes.AuthenticationContext;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.documents.payments.BusinessDocument;
import com.rssl.phizic.common.types.csa.ProfileType;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.context.PersonContext;

/**
 * @author Mescheryakova
 * @ created 19.11.2009
 * @ $Author$
 * @ $Revision$
 */

public class HelperSource
{
	public static BusinessDocument copyPersonDocument(BusinessDocument template, Class<? extends BusinessDocument> childClass) throws BusinessLogicException, BusinessException
	{
		try
		{
			BusinessDocument document = template.createCopy(childClass);
			document.setTemplateId(template.getId());
			// ѕри повторении платежа нужно проинформировать клиента о дате его подтверждени€.
			document.setOperationDate(template.getOperationDate());

			ApplicationAutoRefreshConfig config = ConfigFactory.getConfig(ApplicationAutoRefreshConfig.class);
			ProfileType type = AuthenticationContext.getContext().getProfileType();
			if (type == ProfileType.TEMPORARY)
				document.setTemporaryNodeId(config.getNodeNumber());
			return updateOwner(document);
		}
		catch (DocumentLogicException e)
		{
			throw new BusinessLogicException(e);
		}
		catch (DocumentException e)
		{
			throw new BusinessException(e);
		}
	}

	public static BusinessDocument copyPersonDocument(BusinessDocument template) throws BusinessLogicException, BusinessException
	{
		return copyPersonDocument(template, null);
	}

	public static BusinessDocument updateOwner(BusinessDocument document) throws BusinessException
	{
		if (PersonContext.isAvailable())
		{
			document.setOwner(PersonContext.getPersonDataProvider().getPersonData().createDocumentOwner());
		}
		return document;
	}
}
