package com.rssl.phizic.operations.access.restrictions;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.operations.restrictions.ListCardRestrictionData;
import com.rssl.phizic.business.resources.external.CardLink;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * �������� ��� ������ � ������������� �� �����.
 * @author Roshka
 * @ created 19.04.2006
 * @ $Author$
 * @ $Revision$
 */

public class CardListRestrictionOperation extends RestrictionOperationBase<ListCardRestrictionData>
{

	/**
	 * �������� ������ ���� ���� ������������.
	 * @return
	 * @throws BusinessException
	 */
	public List<CardLink> getAllCardLinks() throws BusinessException, BusinessLogicException
	{
		return externalResourceService.getLinks(getLogin(), CardLink.class);
	}

	/**
	 * ���������� ����������� ��� ������ ����
	 * @param cardLinkIds
	 * @throws BusinessException
	 */
	public void setCardLinks(List<Long> cardLinkIds) throws BusinessException, BusinessLogicException
	{
		Set<Long> idSet = new HashSet<Long>(cardLinkIds);

		List<CardLink> links = getAllCardLinks();
		ArrayList<CardLink> selectedLinks = new ArrayList<CardLink>();

		for (CardLink link : links)
		{
			if (idSet.contains(link.getId()))
				selectedLinks.add(link);
		}
		getRestrictionData().setCardLinks(selectedLinks);
	}

	/**
	 * ��. ������� � base {@link RestrictionOperationBase#createNew(Long, Long, Long)}  
	 */
	protected ListCardRestrictionData createNew(Long loginId, Long serviceId, Long operationId)
	{
		ListCardRestrictionData restrictionData = new ListCardRestrictionData();
		restrictionData.setLoginId(loginId);
		restrictionData.setServiceId(serviceId);
		restrictionData.setOperationId(operationId);
		restrictionData.setCardLinks(new ArrayList<CardLink>());

        return restrictionData;
	}
}