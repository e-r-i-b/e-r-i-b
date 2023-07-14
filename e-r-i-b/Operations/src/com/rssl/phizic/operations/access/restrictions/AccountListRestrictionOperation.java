package com.rssl.phizic.operations.access.restrictions;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.operations.restrictions.ListAccountRestrictionData;
import com.rssl.phizic.business.resources.external.AccountLink;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * ќпераци€ дл€ работы с ограничени€ми на счета.
 * @author Roshka
 * @ created 19.04.2006
 * @ $Author$
 * @ $Revision$
 */
public class AccountListRestrictionOperation extends RestrictionOperationBase<ListAccountRestrictionData>
{
	/**
	 * ѕолучить список ссылок на счета
	 * @return
	 * @throws BusinessException
	 */
	public List<AccountLink> getAllAccountLinks() throws BusinessException, BusinessLogicException
	{
		return externalResourceService.getLinks(getLogin(), AccountLink.class);
	}

	/**
	 * ”становить ограничени€ дл€ счетов
	 * @param accountLinkIds список id ссылок на счета
	 * @throws BusinessException
	 */
	public void setAccountLinks(List<Long> accountLinkIds) throws BusinessException, BusinessLogicException
	{
		Set<Long> idSet = new HashSet<Long>(accountLinkIds);

		List<AccountLink> links = getAllAccountLinks();
		List<AccountLink> accountLinks = new ArrayList<AccountLink>();

		for (AccountLink accountLink : links)
		{
			if(idSet.contains(accountLink.getId()))
				accountLinks.add(accountLink);
		}

		getRestrictionData().setAccountLinks(accountLinks);
	}

	/**
	 * см. коммент в base {@link RestrictionOperationBase#createNew(Long, Long, Long)}
	 */
	protected ListAccountRestrictionData createNew(Long loginId, Long serviceId, Long operationId)
	{
		ListAccountRestrictionData restrictionData = new ListAccountRestrictionData();
		restrictionData.setLoginId(loginId);
		restrictionData.setServiceId(serviceId);
		restrictionData.setOperationId(operationId);
		restrictionData.setAccountLinks(new ArrayList<AccountLink>());
		return restrictionData;
	}
}