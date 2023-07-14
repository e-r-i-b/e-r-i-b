package com.rssl.phizic.operations.account;

import com.rssl.phizic.auth.AuthModule;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.access.AccessException;
import com.rssl.phizic.business.ext.sbrf.dictionaries.ShowOperationsSettings;
import com.rssl.phizic.business.favouritelinks.FavouriteLink;
import com.rssl.phizic.business.favouritelinks.FavouriteLinkManager;
import com.rssl.phizic.business.persons.ActivePerson;
import com.rssl.phizic.business.profileSynchronization.products.ResourceInfoSynchronizationHelper;
import com.rssl.phizic.business.resources.external.*;
import com.rssl.phizic.business.xslt.lists.cache.XmlEntityListCacheSingleton;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.gate.bankroll.Account;
import com.rssl.phizic.gate.bankroll.Card;
import com.rssl.phizic.gate.ima.IMAccount;
import com.rssl.phizic.gate.loans.Loan;
import com.rssl.phizic.operations.OperationBase;
import com.rssl.phizic.operations.validators.IsOwnLinkValidator;
import com.rssl.phizic.operations.validators.NotOwnLinkException;
import com.rssl.phizic.utils.StringHelper;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author mihaylov
 * @ created 19.05.2010
 * @ $Author$
 * @ $Revision$
 */

public class EditExternalLinkOperation extends OperationBase
{
	private static final ExternalResourceService externalResourceService = new ExternalResourceService();
	private static final FavouriteLinkManager favouriteLinkManager = new FavouriteLinkManager();
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
			throw new AccessException("У пользователя с loginId=" + AuthModule.getAuthModule().getPrincipal().getLogin().getId() +
					" нет прав на просмотр " + linkClass.getSimpleName() + " c id=" + linkId);
		}
	}

	public ExternalResourceLink getLink()
	{
		return link;
	}

	/**	 	 	 
	 * @throws BusinessException
	 */
	public void changeShowInMain() throws BusinessException
	{
		if(link instanceof EditableExternalResourceLink)
		{
			EditableExternalResourceLink editableLink = (EditableExternalResourceLink)link;
			editableLink.setShowInMain(!editableLink.getShowInMain());
			externalResourceService.updateLink(editableLink);
			ResourceInfoSynchronizationHelper.updateResourceInfo(editableLink);
		}
		else
			throw new BusinessException("Тип ссылки не поддерживает отображение на главной странице");
	}

	/**
	 * Изменение состояния плашек (свернуть/развернуть)
	 * @throws BusinessException
	 */
	public void changeShowOperations() throws BusinessException
	{
		if(link instanceof EditableExternalResourceLink)
		{
			EditableExternalResourceLink editableLink = (EditableExternalResourceLink)link;

			if (ShowOperationsSettings.isDataBaseSetting())
			{
				editableLink.setShowOperations(!editableLink.getShowOperations());

				externalResourceService.updateLink(editableLink);
			}
			else
			{
				ActivePerson person = PersonContext.getPersonDataProvider().getPersonData().getPerson();
				Set<String>  showOperations = new HashSet<String>(person.getShowOperations());
				String code = editableLink.getCode();

				if (!showOperations.remove(code))
					showOperations.add(code);

				person.setShowOperations(showOperations);
			}
		}
		else
			throw new BusinessException("Тип ссылки не поддерживает сохранение состояния плашек");
	}

	/**
	 * @param name имя ссылки
	 * @throws BusinessException
	 */
	public void saveLinkName(String name) throws BusinessException, BusinessLogicException
	{
		if(link instanceof EditableExternalResourceLink)
		{
			name = StringHelper.replaceSomeSymbols(name, (char)0x00000000, (char)0x00000020, ' ');
			EditableExternalResourceLink editableLink = (EditableExternalResourceLink)link;
			editableLink.setName(name);
			externalResourceService.updateLink(editableLink);
			ResourceInfoSynchronizationHelper.updateResourceInfo(editableLink);
			if (link instanceof AccountLink)
				XmlEntityListCacheSingleton.getInstance().clearCache(link.getValue(), Account.class);
			else if (link instanceof CardLink)
				XmlEntityListCacheSingleton.getInstance().clearCache(link.getValue(), Card.class);
			else if (link instanceof LoanLink)
				XmlEntityListCacheSingleton.getInstance().clearCache(link.getValue(), Loan.class);
			else if (link instanceof IMAccountLink)
				XmlEntityListCacheSingleton.getInstance().clearCache(link.getValue(), IMAccount.class);

		    changeNameFavouriteLink(name);
		}
		else
			throw new BusinessException("Тип ссылки не поддерживает сохранение псевдонима");
	}

	private String getLinkNumber()
	{
		if (link instanceof DepoAccountLink)
			return ((DepoAccountLink) link).getAccountNumber();
		
		return link.getNumber();
	}

	private void changeNameFavouriteLink(String name) throws BusinessException, BusinessLogicException
	{
		// получить все ссылки из личного меню для данного пользователя
		List<FavouriteLink> favouriteLinks =  favouriteLinkManager.findByUserIdPattern(AuthModule.getAuthModule().getPrincipal().getLogin().getId(), link.getPatternForFavouriteLink());
		if (favouriteLinks == null)
			return;

		// для каждой ссылки, имеющей шаблон, совпадающий с заданым, делаем подмену значения
		for (FavouriteLink favouriteLink : favouriteLinks)
		{
			String newPattern = favouriteLink.getPattern().replace(link.getPatternForFavouriteLink(), name == null? getLinkNumber(): name);
			favouriteLink.setName(newPattern);

			favouriteLinkManager.update(favouriteLink);
		}
	}
}

