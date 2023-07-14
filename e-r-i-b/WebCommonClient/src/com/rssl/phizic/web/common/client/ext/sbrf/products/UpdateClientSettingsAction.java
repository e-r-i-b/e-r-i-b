package com.rssl.phizic.web.common.client.ext.sbrf.products;

import com.rssl.phizic.auth.Login;
import com.rssl.phizic.auth.modes.AthenticationCompleteAction;
import com.rssl.phizic.auth.modes.AuthenticationContext;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.menulinks.MenuLink;
import com.rssl.phizic.business.menulinks.MenuLinkInfo;
import com.rssl.phizic.business.messaging.info.NotificationChannel;
import com.rssl.phizic.business.messaging.info.UserNotificationType;
import com.rssl.phizic.business.pfr.PFRLink;
import com.rssl.phizic.business.pfr.PFRLinkService;
import com.rssl.phizic.business.profileSynchronization.PersonSettingsManager;
import com.rssl.phizic.business.profileSynchronization.products.ResourceInfo;
import com.rssl.phizic.business.resources.external.*;
import com.rssl.phizic.business.resources.external.security.SecurityAccountLink;
import com.rssl.phizic.common.types.csa.ProfileType;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.operations.userprofile.ListMenuLinksOperation;
import com.rssl.phizic.operations.userprofile.SetupNotificationOperation;
import com.rssl.phizic.security.SecurityLogicException;
import org.apache.commons.collections.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * @author komarov
 * @ created 29.05.2014
 * @ $Author$
 * @ $Revision$
 *
 * ќбновление настроек клиента из централизованного хранилища
 */
public class UpdateClientSettingsAction implements AthenticationCompleteAction
{
	private static final Log log = PhizICLogFactory.getLog(Constants.LOG_MODULE_CORE);

	private static final ExternalResourceService externalService = new ExternalResourceService();
	private static final PFRLinkService pfrLinkService = new PFRLinkService();

	public void execute(AuthenticationContext context) throws SecurityException, SecurityLogicException
	{
		if( context.getProfileType() != ProfileType.TEMPORARY )
			return;

		updatePersonalMenu();
		updateResourceInfo();
		updateNotificationSettings();
    }

	private void updatePersonalMenu()
	{
		//noinspection unchecked
		List<MenuLink> links = (List<MenuLink>)PersonSettingsManager.getPersonData(PersonSettingsManager.MENU_LINKS_DATA_KEY);
		if(CollectionUtils.isEmpty(links))
			return;

		try
		{
			ListMenuLinksOperation operation = new ListMenuLinksOperation();
			operation.initialize(true);
			List<MenuLinkInfo> infos = operation.getMenuLinksInfo();

			List<Integer> sortedIds = new ArrayList<Integer>();
			List<String> selectedIsd = new ArrayList<String>();

			for(MenuLink link : links)
			{
				for(MenuLinkInfo info: infos)
				{
					if(link.getLinkId() == info.getLink().getLinkId())
					{
						Long infoLinkId = info.getLink().getId();
						sortedIds.add(infoLinkId.intValue());
						if(link.isUse())
							selectedIsd.add(infoLinkId.toString());
					}
				}
			}
			operation.save(selectedIsd.toArray(new String[selectedIsd.size()]), toIntArray(sortedIds));
		}
		catch(BusinessException be)
		{
			log.error(be);
		}
	}

	/**
	 * ¬осстановление настроек продуктов клиента
	 */
	private void updateResourceInfo()
	{
		try
		{
			updateCardLinks();
			updateAccountLinks();
			updateLoanLinks();
			updateDepoAccountLinks();
			updateIMALinks();
			updateSecurityAccountLinks();
			updatePFRLink();
		}
		catch (BusinessLogicException ex)
		{
			log.error(ex);
		}
		catch (BusinessException ex)
		{
			log.error(ex);
		}
	}

	private void updateCardLinks() throws BusinessLogicException, BusinessException
	{
		//noinspection unchecked
		List<ResourceInfo> cardsInfo = (List<ResourceInfo>)PersonSettingsManager.getPersonData(PersonSettingsManager.CARDS_INFO_KEY);
		if(CollectionUtils.isNotEmpty(cardsInfo))
		{
			Login login = PersonContext.getPersonDataProvider().getPersonData().getPerson().getLogin();
			List<CardLink> cards = externalService.getLinks(login.getId(), CardLink.class);
			for(CardLink card : cards)
			{
				for(ResourceInfo info : cardsInfo)
				{
					if (info.getNumber().equals(card.getNumber()))
					{
						card.setName(info.getName());
						card.setShowInSystem(info.getShowInSystem());
						card.setShowInMobile(info.getShowInMobile());
						card.setShowInSocial(info.getShowInSocial());
						card.setShowInATM(info.getShowInATM());
						card.setShowInSms(info.getShowInSms());
						card.setShowInMain(info.getShowInMain());
						card.setPositionNumber(info.getPositionNumber());
						externalService.updateLink(card);
						break;
					}
				}
			}
		}
	}

	private void updateAccountLinks() throws BusinessLogicException, BusinessException
	{
		//noinspection unchecked
		List<ResourceInfo> accountsInfo = (List<ResourceInfo>)PersonSettingsManager.getPersonData(PersonSettingsManager.ACCOUNT_INFO_KEY);
		if(CollectionUtils.isNotEmpty(accountsInfo))
		{
			Login login = PersonContext.getPersonDataProvider().getPersonData().getPerson().getLogin();
			List<AccountLink> accounts = externalService.getLinks(login.getId(), AccountLink.class);
			for(AccountLink account : accounts)
			{
				for(ResourceInfo info : accountsInfo)
				{
					if (info.getNumber().equals(account.getNumber()))
					{
						account.setName(info.getName());
						account.setShowInSystem(info.getShowInSystem());
						account.setShowInMobile(info.getShowInMobile());
						account.setShowInSocial(info.getShowInSocial());
						account.setShowInATM(info.getShowInATM());
						account.setShowInSms(info.getShowInSms());
						account.setShowInMain(info.getShowInMain());
						account.setPositionNumber(info.getPositionNumber());
						externalService.updateLink(account);
						break;
					}
				}
			}
		}
	}

	private void updateLoanLinks() throws BusinessLogicException, BusinessException
	{
		//noinspection unchecked
		List<ResourceInfo> accountsInfo = (List<ResourceInfo>)PersonSettingsManager.getPersonData(PersonSettingsManager.LOAN_INFO_KEY);
		if(CollectionUtils.isNotEmpty(accountsInfo))
		{
			Login login = PersonContext.getPersonDataProvider().getPersonData().getPerson().getLogin();
			List<LoanLink> loans = externalService.getLinks(login.getId(), LoanLink.class);
			for(LoanLink loan : loans)
			{
				for(ResourceInfo info : accountsInfo)
				{
					if (info.getNumber().equals(loan.getNumber()))
					{
						loan.setName(info.getName());
						loan.setShowInSystem(info.getShowInSystem());
						loan.setShowInMobile(info.getShowInMobile());
						loan.setShowInSocial(info.getShowInSocial());
						loan.setShowInATM(info.getShowInATM());
						loan.setShowInSms(info.getShowInSms());
						loan.setShowInMain(info.getShowInMain());
						loan.setPositionNumber(info.getPositionNumber());
						externalService.updateLink(loan);
						break;
					}
				}
			}
		}
	}

	private void updateDepoAccountLinks() throws BusinessLogicException, BusinessException
	{
		//noinspection unchecked
		List<ResourceInfo> accountsInfo = (List<ResourceInfo>)PersonSettingsManager.getPersonData(PersonSettingsManager.DEPO_ACCOUNT_INFO_KEY);
		if(CollectionUtils.isNotEmpty(accountsInfo))
		{
			Login login = PersonContext.getPersonDataProvider().getPersonData().getPerson().getLogin();
			List<DepoAccountLink> depoAccounts = externalService.getLinks(login.getId(), DepoAccountLink.class);
			for(DepoAccountLink depoAccount : depoAccounts)
			{
				for(ResourceInfo info : accountsInfo)
				{
					if (info.getNumber().equals(depoAccount.getAccountNumber()))
					{
						depoAccount.setName(info.getName());
						depoAccount.setShowInSystem(info.getShowInSystem());
						depoAccount.setShowInMain(info.getShowInMain());
						depoAccount.setPositionNumber(info.getPositionNumber());
						externalService.updateLink(depoAccount);
						break;
					}
				}
			}
		}
	}

	private void updateIMALinks() throws BusinessLogicException, BusinessException
	{
		//noinspection unchecked
		List<ResourceInfo> imaInfo = (List<ResourceInfo>)PersonSettingsManager.getPersonData(PersonSettingsManager.IMACCOUNT_INFO_KEY);
		if(CollectionUtils.isNotEmpty(imaInfo))
		{
			Login login = PersonContext.getPersonDataProvider().getPersonData().getPerson().getLogin();
			List<IMAccountLink> imAccounts = externalService.getLinks(login.getId(), IMAccountLink.class);
			for(IMAccountLink ima : imAccounts)
			{
				for(ResourceInfo info : imaInfo)
				{
					if (info.getNumber().equals(ima.getNumber()))
					{
						ima.setName(info.getName());
						ima.setShowInSystem(info.getShowInSystem());
						ima.setShowInMobile(info.getShowInMobile());
						ima.setShowInSocial(info.getShowInSocial());
						ima.setShowInATM(info.getShowInATM());
						ima.setShowInMain(info.getShowInMain());
						ima.setPositionNumber(info.getPositionNumber());
						externalService.updateLink(ima);
						break;
					}
				}
			}
		}
	}

	private void updateSecurityAccountLinks() throws BusinessLogicException, BusinessException
	{
		//noinspection unchecked
		List<ResourceInfo> securityAccountsInfo = (List<ResourceInfo>)PersonSettingsManager.getPersonData(PersonSettingsManager.SECURITY_ACCOUNT_INFO_KEY);
		if(CollectionUtils.isNotEmpty(securityAccountsInfo))
		{
			Login login = PersonContext.getPersonDataProvider().getPersonData().getPerson().getLogin();
			List<SecurityAccountLink> securityAccounts = externalService.getLinks(login.getId(), SecurityAccountLink.class);
			for(SecurityAccountLink securityAccount : securityAccounts)
			{
				for(ResourceInfo info : securityAccountsInfo)
				{
					if (info.getNumber().equals(securityAccount.getNumber()))
					{
						securityAccount.setName(info.getName());
						securityAccount.setShowInSystem(info.getShowInSystem());
						externalService.updateLink(securityAccount);
						break;
					}
				}
			}
		}
	}

	private void updatePFRLink() throws BusinessException
	{
		//noinspection unchecked
		List<ResourceInfo> pfrInfoAsList = (List<ResourceInfo>)PersonSettingsManager.getPersonData(PersonSettingsManager.PFR_LINK_INFO_KEY);
		if(CollectionUtils.isNotEmpty(pfrInfoAsList))
		{
			Login login = PersonContext.getPersonDataProvider().getPersonData().getPerson().getLogin();
			PFRLink pfrLink = pfrLinkService.findByLoginId(login.getId());
			ResourceInfo pfrInfo = pfrInfoAsList.get(0);

			pfrLink.setShowInSystem(pfrInfo.getShowInSystem());
			pfrLink.setShowInMain(pfrInfo.getShowInMain());
			pfrLinkService.addOrUpdate(pfrLink);
		}
	}

	private int[] toIntArray(List<Integer> list)
	{
		int[] array = new int[list.size()];
		for(int i = 0; i < array.length; i++)
		{
			array[i] = list.get(i);
		}
		return array;
	}

	/**
	 * ¬осстановление настроек оповещений
	 */
	private void updateNotificationSettings()
	{
		try
		{
			SetupNotificationOperation operation = new SetupNotificationOperation();
			operation.initialize(PersonContext.getPersonDataProvider().getPersonData().getPerson(), UserNotificationType.values());
			for (UserNotificationType type : UserNotificationType.values())
			{
				NotificationChannel newChannel = getNewChannel(type);
				if (newChannel == null)
					continue;

				operation.setChannel(type, newChannel.toString());
			}
			operation.saveNotification();
		}
		catch(BusinessException ex)
		{
			log.error(ex);
		}
		catch (BusinessLogicException ex)
		{
			log.error(ex);
		}
	}

	private NotificationChannel getNewChannel(UserNotificationType type)
	{
		switch (type)
		{
			case loginNotification: return getChannel(PersonSettingsManager.LOGIN_NOTIFICATION_KEY);
			case mailNotification: return getChannel(PersonSettingsManager.MAIL_NOTIFICATION_KEY);
			case newsNotification: return getChannel(PersonSettingsManager.NEWS_NOTIFICATION_KEY);
			case operationNotification: return getChannel(PersonSettingsManager.OPERATION_NOTIFICATION_KEY);
		}
		return null;
	}

	private NotificationChannel getChannel(String key)
	{
		return (NotificationChannel)PersonSettingsManager.getPersonData(key);
	}
}
