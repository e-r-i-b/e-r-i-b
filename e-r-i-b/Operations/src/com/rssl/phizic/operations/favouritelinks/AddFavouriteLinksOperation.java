package com.rssl.phizic.operations.favouritelinks;

import com.rssl.phizic.auth.AuthModule;
import com.rssl.phizic.auth.CommonLogin;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.favouritelinks.FavouriteLink;
import com.rssl.phizic.business.favouritelinks.FavouriteLinkManager;
import com.rssl.phizic.common.types.FavouriteTypeLink;
import com.rssl.phizic.operations.EditEntityOperation;
import com.rssl.phizic.operations.OperationBase;

/**
 * @author mihaylov
 * @ created 28.04.2010
 * @ $Author$
 * @ $Revision$
 */

public class AddFavouriteLinksOperation extends OperationBase implements EditEntityOperation
{
	private static final FavouriteLinkManager favouriteLinkManager = new FavouriteLinkManager();

	private CommonLogin login;
	private FavouriteLink favouriteLink;

	public void initializeNew(String name, String link, String pattern, FavouriteTypeLink typeLink, String onclick) throws BusinessException
	{
		login = AuthModule.getAuthModule().getPrincipal().getLogin();
		favouriteLink = new FavouriteLink();
		favouriteLink.setName(name);
		favouriteLink.setLink(link);
		favouriteLink.setLoginId(login.getId());
		favouriteLink.setOrderInd(favouriteLinkManager.maxLinkInd(login.getId())+1);
		favouriteLink.setPattern(pattern);
		favouriteLink.setTypeLink(typeLink);
		favouriteLink.setOnclickFunction(onclick);
	}

	public FavouriteLink getEntity()
	{
		return favouriteLink;
	}

	public void save() throws BusinessException, BusinessLogicException
	{
		favouriteLinkManager.add(favouriteLink);
	}
}
