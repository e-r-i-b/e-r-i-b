package com.rssl.phizic.operations.ermb;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.access.AccessException;
import com.rssl.phizic.business.resources.external.ErmbProductLink;
import com.rssl.phizic.business.resources.external.ExternalResourceLink;
import com.rssl.phizic.business.resources.external.ExternalResourceService;
import com.rssl.phizic.business.xslt.lists.cache.XmlEntityListCacheSingleton;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.context.PersonData;
import com.rssl.phizic.operations.OperationBase;
import com.rssl.phizic.operations.validators.IsOwnLinkValidator;
import com.rssl.phizic.operations.validators.NotOwnLinkException;

/**
 * @author EgorovaA
 * @ created 17.05.2013
 * @ $Author$
 * @ $Revision$
 */
public class EditProductAliasOperation extends OperationBase
{
	private static final ExternalResourceService externalResourceService = new ExternalResourceService();
	private ExternalResourceLink link;

	public void initialize(Long linkId, Class linkClass) throws BusinessException
	{
		link = externalResourceService.findLinkById(linkClass,linkId);
		try
		{
			new IsOwnLinkValidator().validate(link);
		}
		catch(NotOwnLinkException e)
		{
			PersonData data = PersonContext.getPersonDataProvider().getPersonData();
			throw new AccessException("У пользователя с loginId=" + data.getLogin().getId() +
					" нет прав на просмотр " + linkClass.getSimpleName() + " c id=" + linkId);
		}
	}

	/**
	 * Сохранить введенный клиентом смс-алиас
	 * @param alias - алиас
	 * @throws BusinessException
	 * @throws BusinessLogicException
	 */
	public void saveProductSmsAlias(String alias) throws BusinessException, BusinessLogicException
	{
		if (link instanceof ErmbProductLink)
		{
			ErmbProductLink pLink = (ErmbProductLink) link;
			pLink.setErmbSmsAlias(alias);
			externalResourceService.updateLink(pLink);
			XmlEntityListCacheSingleton.getInstance().clearCache(link.getValue(), link.getClass());
		}
		else
			throw new BusinessException("Тип ссылки не поддерживает сохранение sms-идентификатора");
	}

}
