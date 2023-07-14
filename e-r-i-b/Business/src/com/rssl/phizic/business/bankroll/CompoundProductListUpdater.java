package com.rssl.phizic.business.bankroll;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.SimpleService;
import com.rssl.phizic.business.clients.ClientResourcesService;
import com.rssl.phizic.business.clients.SmsAliasCalculator;
import com.rssl.phizic.business.persons.ActivePerson;
import com.rssl.phizic.business.persons.PersonService;
import com.rssl.phizic.business.persons.PersonTiming;
import com.rssl.phizic.business.resources.external.*;
import com.rssl.phizic.common.types.exceptions.InternalErrorException;
import com.rssl.phizic.common.types.exceptions.UserErrorException;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.bankroll.Account;
import com.rssl.phizic.gate.bankroll.Card;
import com.rssl.phizic.gate.cache.CacheService;
import com.rssl.phizic.gate.clients.Client;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.loans.Loan;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.LogModule;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import org.apache.commons.lang.StringUtils;

import java.util.*;

/**
 * @author Erkin
 * @ created 02.04.2013
 * @ $Author$
 * @ $Revision$
 */

/**
 * ������������ �� ���� ����� ���������
 */
class CompoundProductListUpdater implements ProductListUpdater
{
	private static final Log log = PhizICLogFactory.getLog(LogModule.Core);

	private static final ClientResourcesService clientResourcesService = new ClientResourcesService();

	private static final PersonService personService = new PersonService();

	private static final SimpleService simpleService = new SimpleService();

	private static final ExternalResourceService resourceService = new ExternalResourceService();

	private final ActivePerson person;

	/**
	 * ������ "������ �������� ���������������"
	 */
	private boolean updated = false;

	///////////////////////////////////////////////////////////////////////////

	CompoundProductListUpdater(ActivePerson person)
	{
		this.person = person;
	}

	public void updateProductList(boolean forceUpdate)
	{
		if (forceUpdate)
			updated = false;
		if (updated)
			return;

		// 0. ��������� ������������� ������ � ������ �������������� ��� �������
		checkNullableSmsAutoAlias();

		// 1. ������ �������� �������
		PersonTiming timing = loadTimings();

		// 2. ������� change-set
		ProductChangeSet changeSet = new ProductChangeSet(timing, Calendar.getInstance());

		// 3. �������� �������������� �� ���������
		ProductListUpdater accountListUpdater = new AccountListUpdater(changeSet);
		ProductListUpdater cardListUpdater = new CardListUpdater(changeSet);
		ProductListUpdater loanListUpdater = new LoanListUpdater(changeSet);

		// 4. �������� �������������� �������� ���� ��� ��������
		accountListUpdater.updateProductList(forceUpdate);
		cardListUpdater.updateProductList(forceUpdate);
		loanListUpdater.updateProductList(forceUpdate);

		if (changeSet.isEmpty())
		{
			if (log.isDebugEnabled())
				log.debug("������ ��������� ������� USER_ID=" + person.getId() + " �� ��������� � ����������");
		}
		else
		{
			// 5. ��������� ������ ���������
			Class[] changes = changeSet.getChanges();
			if (log.isDebugEnabled())
				log.debug("��������� ������ ��������� ������� USER_ID=" + person.getId() + " : " + Arrays.toString(changes));

			try
			{
				//TODO: (����) ������� ������� �� �� ���� ���������, � ������ �� ������������ �����. �����������: ������� �.
				Client client = person.asClient();
				GateSingleton.getFactory().service(CacheService.class).clearClientProductsCache(client, Card.class, Account.class, Loan.class);
				// TODO: (����) catch InactiveExternalSystemException ����������� ����� �
				clientResourcesService.updateResources(person, false, changes);
			}
			catch (GateLogicException e)
			{
				throw new UserErrorException(e);
			}
			catch (GateException e)
			{
				throw new InternalErrorException(e);
			}
			catch (BusinessException e)
			{
				throw new InternalErrorException(e);
			}


			// 6. ��������� �������� �������
			saveTimings(timing);

			if (log.isDebugEnabled())
				log.debug("������ ��������� ������� USER_ID=" + person.getId() + " ������� ���������");
		}

		updated = true;
	}

	private void checkNullableSmsAutoAlias()
	{
		if (!doesNullableSmsAutoAliasExists(person.getLogin().getId()))
			return;
		Map<Class<? extends ExternalResourceLink>,
			List<? extends ExternalResourceLink>> map = ClientResourcesService.initLinks(person,
				new Class[]{Card.class, Account.class, Loan.class});
		Collection<Object> links = new ArrayList<Object>();
		for (List<? extends ExternalResourceLink> list : map.values())
			for (Object link : list)
				links.add(link);

		Map<Object, String> smsAutoAliases;
		try
		{
			smsAutoAliases = SmsAliasCalculator.LINK.computeSmsAutoAliases(links);
		}
		catch (Exception e)
		{
			StringBuilder builder = new StringBuilder();
			builder.append("���������� ��������� ���-������ ��� ���������, �.�. ���������� ��������, ��������� 7 ���� ������� ������� ���������: ");
			for (Object product : links)
			{
				if (product instanceof Account)
					builder.append("���� ").append(((Account)product).getNumber()).append("; ");

				if (product instanceof Card)
					builder.append("����� ").append(((Card)product).getNumber()).append("; ");

				if (product instanceof Loan)
					builder.append("������ ").append(((Loan)product).getAccountNumber()).append("; ");
			}
			log.error(builder.toString(), e);
			smsAutoAliases = Collections.emptyMap();
		}

		for (Object link : links)
		{
			ErmbProductLink ermbProductLink = (ErmbProductLink) link;
			String smsAutoAlias = smsAutoAliases.get(ermbProductLink);
			if (!StringUtils.equals(ermbProductLink.getAutoSmsAlias(), smsAutoAlias))
			{
				ermbProductLink.setAutoSmsAlias(smsAutoAlias);
				updateLink(ermbProductLink);
			}
		}
	}

	private boolean doesNullableSmsAutoAliasExists(Long loginId)
	{
		try
		{
			return personService.existsNullableSmsAutoAlias(loginId);
		}
		catch (BusinessException e)
		{
			throw new InternalErrorException(e);
		}
	}

	private void updateLink(ErmbProductLink ermbProductLink)
	{
		try
		{
			resourceService.updateLink(ermbProductLink);
		}
		catch (BusinessException e)
		{
			throw new InternalErrorException(e);
		}
	}

	private PersonTiming loadTimings()
	{
		try
		{
			PersonTiming timings = personService.getPersonTimings(person.getId());

			if (timings == null) {
				timings = new PersonTiming();
				timings.setPersonId(person.getId());
			}

			return timings;
		}
		catch (BusinessException e)
		{
			throw new InternalErrorException(e);
		}
	}

	private void saveTimings(PersonTiming timings)
	{
		try
		{
			simpleService.addOrUpdate(timings);
		}
		catch (BusinessException e)
		{
			throw new InternalErrorException(e);
		}
	}
}
