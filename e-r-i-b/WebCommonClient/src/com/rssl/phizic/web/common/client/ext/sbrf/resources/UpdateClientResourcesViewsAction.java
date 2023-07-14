package com.rssl.phizic.web.common.client.ext.sbrf.resources;

import com.rssl.phizic.auth.modes.AthenticationCompleteAction;
import com.rssl.phizic.auth.modes.AuthenticationContext;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.operations.OperationFactory;
import com.rssl.phizic.business.operations.OperationFactoryImpl;
import com.rssl.phizic.business.operations.restrictions.RestrictionProviderImpl;
import com.rssl.phizic.business.resources.external.*;
import com.rssl.phizic.business.resources.external.comparator.AccountsComparator;
import com.rssl.phizic.business.resources.external.comparator.CardLinkComparator;
import com.rssl.phizic.business.resources.external.comparator.IMAComparator;
import com.rssl.phizic.business.resources.external.comparator.LoansComparator;
import com.rssl.phizic.common.types.exceptions.InactiveExternalSystemException;
import com.rssl.phizic.common.types.transmiters.Pair;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.gate.bankroll.AccountAbstract;
import com.rssl.phizic.gate.bankroll.CardAbstract;
import com.rssl.phizic.gate.ima.IMAccountAbstract;
import com.rssl.phizic.gate.loans.ScheduleAbstract;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.operations.card.GetCardAbstractOperation;
import com.rssl.phizic.operations.ext.sbrf.account.GetAccountAbstractExtendedOperation;
import com.rssl.phizic.operations.ima.GetIMAccountAbstractOperation;
import com.rssl.phizic.operations.loans.loan.GetLoanAbstractOperation;
import com.rssl.phizic.person.Person;
import com.rssl.phizic.security.PermissionUtil;
import com.rssl.phizic.utils.ClientConfig;
import org.apache.commons.collections.CollectionUtils;
import com.rssl.phizic.common.types.client.CreationType;

import java.util.*;

/**
 * @author kuzmin
 * @ created 09.02.2011
 * @ $Author$
 * @ $Revision$
 */
public class UpdateClientResourcesViewsAction implements AthenticationCompleteAction
{
	protected static final Long MAX_COUNT_OF_TRANSACTIONS = 3L;
	private static final Log log = PhizICLogFactory.getLog(Constants.LOG_MODULE_CORE);
	private transient OperationFactory operationFactory = new OperationFactoryImpl(new RestrictionProviderImpl());
	private static ExternalResourceService resourceService = new ExternalResourceService();

	public void execute(AuthenticationContext context) throws SecurityException
	{
		Person person = PersonContext.getPersonDataProvider().getPersonData().getPerson();
		if(person.getLogin().isFirstLogin())
		{
			if (PermissionUtil.impliesOperation("GetCardAbstractOperation", "Abstract"))
			{
				updateCardLink(context);
			}

			if (!(person.getCreationType() == CreationType.CARD))
			{
				if (PermissionUtil.impliesOperation("GetAccountAbstractExtendedOperation", "Abstract"))
				{
					updateAccountLink(context);
				}
				if (PermissionUtil.impliesService("LoanInfo") && PermissionUtil.impliesService("ReceiveLoansOnLogin"))
				{
					updateLoanLink(context);
				}
				if (PermissionUtil.impliesService("IMAccountInfoService"))
				{
					updateIMAccountLink(context);
				}
			}
		}
	}
	
	private void updateCardLink(AuthenticationContext context)
	{
		try
		{
			List<CardLink> cardLinks = PersonContext.getPersonDataProvider().getPersonData().getCards();

			if (!CollectionUtils.isEmpty(cardLinks) && cardLinks.size() <= ConfigFactory.getConfig(ClientConfig.class).getNumOfProducts())
				return;

			GetCardAbstractOperation operation = operationFactory.create(GetCardAbstractOperation.class);
			operation.initialize(cardLinks);

			Map<CardLink, CardAbstract> cardAbstract = operation.getCardAbstract(MAX_COUNT_OF_TRANSACTIONS);
			List<Pair<CardLink,CardAbstract>> cardList = new ArrayList<Pair<CardLink,CardAbstract>>();

			for(CardLink link: cardLinks)
			{
				cardList.add(new Pair(link,cardAbstract.get(link)));
			}

			Collections.sort(cardList, new CardLinkComparator());

			for (int i=ConfigFactory.getConfig(ClientConfig.class).getNumOfProducts(); i < cardList.size(); i++)
			{
				CardLink link = cardList.get(i).getFirst();
				link.setShowInMain(false);
				resourceService.updateLink(link);
			}
		}
		catch (BusinessException e)
		{
			log.error("Не удалось обновить информацию по картам клиена", e);
		}
		catch (BusinessLogicException e)
		{
			log.error("Не удалось обновить информацию по картам клиена", e);
		}
		catch (InactiveExternalSystemException e)
		{
			context.putInactiveESMessage(e.getMessage());
			log.error(e.getMessage(), e);
		}
	}

	private void updateAccountLink(AuthenticationContext context)
	{
		try
		{
			List<AccountLink> accountLinks = PersonContext.getPersonDataProvider().getPersonData().getAccounts();

			if (!CollectionUtils.isEmpty(accountLinks) && accountLinks.size() <= ConfigFactory.getConfig(ClientConfig.class).getNumOfProducts())
				return;

			GetAccountAbstractExtendedOperation operation = operationFactory.create(GetAccountAbstractExtendedOperation.class);
			operation.initialize(accountLinks);

			Map<AccountLink,AccountAbstract> accountAbstract = operation.getAccountAbstract(MAX_COUNT_OF_TRANSACTIONS);
			List<Pair<AccountLink,AccountAbstract>> accountList = new ArrayList<Pair<AccountLink,AccountAbstract>>();

			for (AccountLink accountLink: accountLinks)
			{
				accountList.add(new Pair(accountLink, accountAbstract.get(accountLink)));
			}

			Collections.sort(accountList, new AccountsComparator());

			for (int i=ConfigFactory.getConfig(ClientConfig.class).getNumOfProducts(); i < accountList.size(); i++)
			{
				AccountLink link = accountList.get(i).getFirst();
				link.setShowInMain(false);
				resourceService.updateLink(link);
			}
		}
		catch (BusinessException e)
		{
			log.error("Не удалось обновить информацию по счетам клиена", e);
		}
		catch (InactiveExternalSystemException e)
		{
			context.putInactiveESMessage(e.getMessage());
			log.error(e.getMessage(), e);
		}
		catch (BusinessLogicException e)
		{
			log.error("Не удалось обновить информацию по счетам клиена", e);
		}
	}

	private void updateLoanLink(AuthenticationContext context)
	{
		try
		{
			List<LoanLink> loanLinks = PersonContext.getPersonDataProvider().getPersonData().getLoans();

			if (!CollectionUtils.isEmpty(loanLinks) && loanLinks.size() <= ConfigFactory.getConfig(ClientConfig.class).getNumOfProducts())
				return;

			Map<LoanLink, ScheduleAbstract> scheduleAbstract = new HashMap<LoanLink, ScheduleAbstract>();
			if (operationFactory.checkAccess(GetLoanAbstractOperation.class)){
				GetLoanAbstractOperation abstractOperation = operationFactory.create(GetLoanAbstractOperation.class);
				abstractOperation.initialize(loanLinks);
				scheduleAbstract = abstractOperation.getScheduleAbstract(-MAX_COUNT_OF_TRANSACTIONS,MAX_COUNT_OF_TRANSACTIONS, true).getFirst();
			}
			
			List<Pair<LoanLink,ScheduleAbstract>> loanList = new ArrayList<Pair<LoanLink,ScheduleAbstract>>();
			for(LoanLink link: loanLinks)
			{
				loanList.add(new Pair(link,scheduleAbstract.get(link)));
			}

			Collections.sort(loanList, new LoansComparator());

			for (int i=ConfigFactory.getConfig(ClientConfig.class).getNumOfProducts(); i < loanList.size(); i++)
			{
				LoanLink link = loanList.get(i).getFirst();
				link.setShowInMain(false);
				resourceService.updateLink(link);
			}
		}
		catch (BusinessException e)
		{
			log.error("Не удалось обновить информацию по кредитам клиента", e);
		}
		catch (BusinessLogicException e)
		{
			log.error("Не удалось обновить информацию по кредитам клиента", e);
		}
		catch (InactiveExternalSystemException e)
		{
			context.putInactiveESMessage(e.getMessage());
			log.error(e.getMessage(), e);
		}
	}

	private void updateIMAccountLink(AuthenticationContext context)
	{
		try
		{
			List<IMAccountLink> imAccountLinks = PersonContext.getPersonDataProvider().getPersonData().getIMAccountLinks();

			if (!CollectionUtils.isEmpty(imAccountLinks) && imAccountLinks.size() <= ConfigFactory.getConfig(ClientConfig.class).getNumOfProducts())
				return;

			GetIMAccountAbstractOperation abstractOperation = operationFactory.create("GetIMAccountShortAbstractOperation");
			abstractOperation.initialize(imAccountLinks, false);
			Map<IMAccountLink, IMAccountAbstract> imAccountAbstract = abstractOperation.getIMAccountAbstractMap(MAX_COUNT_OF_TRANSACTIONS);

			List<Pair<IMAccountLink,IMAccountAbstract>> IMAList = new ArrayList<Pair<IMAccountLink,IMAccountAbstract>>();
			for(IMAccountLink link: imAccountLinks)
			{
				IMAList.add(new Pair(link, imAccountAbstract.get(link)));
			}

			Collections.sort(IMAList, new IMAComparator());

			for (int i=ConfigFactory.getConfig(ClientConfig.class).getNumOfProducts(); i < IMAList.size(); i++)
			{
				IMAccountLink link = IMAList.get(i).getFirst();
				link.setShowInMain(false);
				resourceService.updateLink(link);
			}
		}
		catch (BusinessException e)
		{
			log.error("Не удалось обновить информацию по ОМС клиента", e);
		}
		catch (BusinessLogicException e)
		{
			log.error("Не удалось обновить информацию по ОМС клиента", e);
		}
		catch (InactiveExternalSystemException e)
		{
			context.putInactiveESMessage(e.getMessage());
			log.error(e.getMessage(), e);
		}
	}
}
