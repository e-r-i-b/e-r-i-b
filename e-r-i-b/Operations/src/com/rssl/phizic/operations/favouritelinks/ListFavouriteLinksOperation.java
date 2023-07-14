package com.rssl.phizic.operations.favouritelinks;

import com.rssl.phizic.auth.AuthModule;
import com.rssl.phizic.auth.CommonLogin;
import com.rssl.phizic.auth.UserPrincipal;
import com.rssl.phizic.auth.modes.AccessType;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.favouritelinks.FavouriteLink;
import com.rssl.phizic.business.favouritelinks.FavouriteLinkManager;
import com.rssl.phizic.operations.ListEntitiesOperation;
import com.rssl.phizic.operations.OperationBase;
import com.rssl.phizic.utils.StringHelper;

import java.util.*;

/**
 * ќпераци€ дл€ работы со списком избранных ссылок
 * @author mihaylov
 * @ created 28.04.2010
 * @ $Author$
 * @ $Revision$
 */

public class ListFavouriteLinksOperation extends OperationBase implements ListEntitiesOperation
{
	private FavouriteLinkManager favouriteLinkManager = new FavouriteLinkManager();
	private List<FavouriteLink>  links;

	public void initialize() throws BusinessException
	{
		links = favouriteLinkManager.findByUserId(AuthModule.getAuthModule().getPrincipal().getLogin().getId());
	}

	/**
	 * —охранить пор€док отображени€ списка избранного
	 * @param sortLinks - пор€док отображени€
	 * @throws BusinessLogicException
	 * @throws BusinessException
	 */
	public void save(Long[] sortLinks) throws BusinessLogicException, BusinessException
	{
		int i = 1;
		for (Long id : sortLinks)
		{
			FavouriteLink temp = findLink(id);
			if (temp.getOrderInd() != i)
			{
				temp.setOrderInd(i);
				favouriteLinkManager.update(temp);
			}
			i++;
		}
	}

	/**
	 * —охранить ссылку избранного
	 * @param linkId - id ссылки
	 * @param nameFavourieLink - название ссылки
	 * @throws BusinessLogicException
	 * @throws BusinessException
	 */
	public void save(Long linkId, String nameFavourieLink) throws BusinessLogicException, BusinessException
	{
		FavouriteLink temp = findLink(linkId);
		if (!StringHelper.isEmpty(nameFavourieLink) && !nameFavourieLink.equals(temp.getName()))
		{
			temp.setName(nameFavourieLink);
			favouriteLinkManager.update(temp);
		}
	}

	/**
	 * —писок ссылок
	 * @return список ссылок избранного
	 */
	public Collection<FavouriteLink> getEntity()
	{
		return links;
	}


	private FavouriteLink findLink(long linkId) throws BusinessLogicException
	{
		for(FavouriteLink link:links)
		{
			if(link.getId().equals(linkId))
				return link;
		}
		throw new BusinessLogicException("—сылка с id=" + linkId + " не найдена.");
	}

	/**
	 * ѕолучение заданного кол-ва используемых ссылок клиента
	 * @return
	 * @throws BusinessException
	 */
	public List<FavouriteLink> getUsedFavouriteLinks() throws BusinessException
	{
		AuthModule authModule = AuthModule.getAuthModule();
		if (authModule != null) {
			UserPrincipal principal = authModule.getPrincipal();
			if (principal != null && !AccessType.guest.equals(principal.getAccessType()))
			{
				CommonLogin login = principal.getLogin();
				if (login != null)
					return favouriteLinkManager.findByUserId(login.getId());
			}
		}
		return Collections.emptyList();
	}
}
