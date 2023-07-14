package com.rssl.phizic.operations.favouritelinks;

import com.rssl.phizic.auth.AuthModule;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.favouritelinks.FavouriteLinkManager;
import com.rssl.phizic.business.operations.Transactional;
import com.rssl.phizic.operations.OperationBase;
import com.rssl.phizic.operations.RemoveEntityOperation;

/**
 * @author mihaylov
 * @ created 03.05.2010
 * @ $Author$
 * @ $Revision$
 */

public class RemoveFavouriteLinksOperation extends OperationBase implements RemoveEntityOperation
{
	private Long linkId;
	private static final FavouriteLinkManager favouriteLinkManager = new FavouriteLinkManager();

	public void initialize(Long linkId) throws BusinessException, BusinessLogicException
	{
		this.linkId = linkId;
		if (linkId == null)
			throw new BusinessException("linkId не передан");
	}

	public Object getEntity()
	{
		return linkId;
	}

	@Transactional
	public void remove() throws BusinessException, BusinessLogicException
	{
		favouriteLinkManager.removeById(linkId, AuthModule.getAuthModule().getPrincipal().getLogin().getId());
	}

}
