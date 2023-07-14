package com.rssl.phizic.business.ips;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.documents.ResourceType;
import com.rssl.phizic.business.finances.CardOperationClaim;
import com.rssl.phizic.business.finances.CardOperationClaimService;
import com.rssl.phizic.business.finances.CardOperationClaimStatus;
import com.rssl.phizic.business.payments.job.JobRefreshConfig;
import com.rssl.phizic.business.persons.ActivePerson;
import com.rssl.phizic.business.persons.PersonService;
import com.rssl.phizic.business.resources.external.CardLink;
import com.rssl.phizic.business.resources.external.ExternalResourceService;
import com.rssl.phizic.common.types.exceptions.IKFLException;
import com.rssl.phizic.common.types.transmiters.GroupResult;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.config.ips.IPSConfig;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.bankroll.BankrollService;
import com.rssl.phizic.gate.bankroll.Card;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.ips.IPSCardOperationClaim;
import com.rssl.phizic.gate.ips.IPSExecutiveService;
import com.rssl.phizic.gate.utils.ExternalSystemHelper;
import com.rssl.phizic.job.BaseJob;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.LogModule;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.service.StartupServiceDictionary;
import com.rssl.phizic.utils.DateHelper;
import com.rssl.phizic.utils.EntityUtils;
import com.rssl.phizicgate.manager.routing.Adapter;
import com.rssl.phizicgate.manager.routing.AdapterService;
import org.apache.commons.lang.StringUtils;
import org.quartz.*;

import java.util.*;
import java.util.Calendar;

/**
 * @author Erkin
 * @ created 04.08.2011
 * @ $Author$
 * @ $Revision$
 */

/**
 * ���� ��� ��������� ��������� �������� �� ���� ���
 */
public class LoadCardOperationsJob extends BaseJob implements StatefulJob, InterruptableJob
{
	private static final Log log = PhizICLogFactory.getLog(LogModule.Scheduler);

	private static boolean initialized = false;
	private volatile boolean isInterrupt = false;
	
	private static final CardOperationClaimService claimService = new CardOperationClaimService();

	private static final ExternalResourceService resourceService = new ExternalResourceService();

	private static final AdapterService adapterService = new AdapterService();

	private static final PersonService personService = new PersonService();

	///////////////////////////////////////////////////////////////////////////

	@Override protected void executeJob(JobExecutionContext context) throws JobExecutionException
	{
		JobRefreshConfig config = ConfigFactory.getConfig(JobRefreshConfig.class);
		if(!config.getLoadCardOperationsJobStateful())
			return;

		initialize();
		try
		{
			startJob();
		}
		catch (RuntimeException e)
		{
			log.error(e.getMessage(), e);
		}
	}

	private synchronized void initialize()
	{
		if (initialized)
			return;

		runStartupServices();

		initialized = true;
	}

	private void runStartupServices()
	{
		StartupServiceDictionary serviceStarter = new StartupServiceDictionary();
		serviceStarter.startMBeans();
	}

	private void startJob()
	{
		while (!isInterrupt)
		{
			try
			{
				//0. ��������� ���������� �� ��������������� �������
				Adapter ipsAdapter =  adapterService.getAdapterByUUID(ExternalSystemHelper.getIpsSystemCode());
				if(ipsAdapter != null && !ExternalSystemHelper.isActive(ipsAdapter))
					break;

				// 1. �������� ��������� ������� ������, ������� ����� ���������
				List<CardOperationClaim> claims = getClaims();
				if (claims.isEmpty() || isInterrupt)
					// ������ �� ��������� ������ ��� ��� ����� �����������
					break;

				log.info("������� ������ �� ��������� ��������� �������� => ������� ��������� �������� ��������� ��������");

				// 2. ��������� ������ ������ �� "�����������"
				markClaimsProcessing(claims);
				log.info("� ��������� ������� ������: " + StringUtils.join(claims, ", "));

				// 3. ������������ ������ � �������� �������������
				List<IPSCardOperationClaim> gateClaims = convertClaimsToGate(claims);
				if (gateClaims.isEmpty())
					continue;

				// 4. ������� ������ � ���� ���
				log.info("������� ������ � ����: " + StringUtils.join(gateClaims, ", "));
				executeGateClaims(gateClaims);
			}
			catch(GateException ge)
			{
				log.error(ge);
				break;
			}
			catch (BusinessException e)
			{
				log.error(e.getMessage(), e);
				break;
			}
		}
	}

	/**
	 * ��������� ������ ��� ����������
	 * @return ������ ������, ����������� � ����������
	 */
	private List<CardOperationClaim> getClaims()
	{
		List<CardOperationClaim> claims = Collections.emptyList();
		try
		{
			claims	= claimService.getClaimsToLoadOperations();
		}
		catch (BusinessException e)
		{
			log.error("���� �� ��������� ������ �������� ������", e);
		}

		return claims;
	}

	private List<IPSCardOperationClaim> convertClaimsToGate(List<CardOperationClaim> claims)
	{
		IPSConfig config = ConfigFactory.getConfig(IPSConfig.class);
		// ������� �� ������ ���
		final Calendar today = DateHelper.getCurrentDate();
		final Calendar defaultStartDate = DateHelper.addDays(today, -config.getCardOperationMaxTime());

		// 1. �������� ��� "����� -> �����"
		Map<String, Card> cardsByNumber = getClaimCards(claims);

		// 2. ������������ ������ � �������� �������������
		List<IPSCardOperationClaim> gateClaims = new ArrayList<IPSCardOperationClaim>(cardsByNumber.size());
		for (CardOperationClaim claim : claims)
		{
			ActivePerson person;
			try
			{
				person = personService.findByLoginId(claim.getOwnerId());
			}
			catch (BusinessException e)
			{
				log.error(e.getMessage(), e);
				failClaim(claim, e.getMessage());
				continue;
			}

			Card card = cardsByNumber.get(claim.getCardNumber());
			if (card == null) {
				log.error("�� ������� ����� ��� ������ " + claim);
				failClaim(claim, "�� ������� ����� ��� ������");
				continue;
			}

			Calendar startDate = claim.getLastOperationDate();
			if (startDate == null)
				startDate = defaultStartDate;

			try
			{
				IPSCardOperationClaimImpl gateClaim = new IPSCardOperationClaimImpl();
				gateClaim.setClient(person.asClient());
				gateClaim.setCard(card);
				gateClaim.setStartDate(startDate);

				gateClaims.add(gateClaim);
			}
			catch (BusinessException e)
			{
				log.error(e.getMessage(), e);
				failClaim(claim, e.getMessage());
			}
		}
		return gateClaims;
	}

	private Map<String, Card> getClaimCards(Collection<CardOperationClaim> claims)
	{
		BankrollService bankrollService = GateSingleton.getFactory().service(BankrollService.class);

		// 1. �������� (�������) �������������� ����
		Set<String> cardIdsSet = new HashSet<String>(claims.size());
		// ��� "id_����� -> ������" ��� ����������� ������ ��������� ����
		Map<String, CardOperationClaim> claimsByCardId = new HashMap<String, CardOperationClaim>(claims.size());
		for (CardOperationClaim claim : claims)
		{
			CardLink cardLink;
			try
			{
				cardLink = resourceService.findLinkByNumber(claim.getOwnerId(), ResourceType.CARD, claim.getCardNumber());
				if (cardLink == null) {
					failClaim(claim, "����-���� �� ������");
					continue;
				}

			}
			catch (BusinessException e)
			{
				log.error("���� ��� ��������� ����-�����. " + claim, e);
				failClaim(claim, "���� ��� ��������� ����-�����");
				continue;
			}

			String cardId = cardLink.getExternalId();
			cardIdsSet.add(cardId);
			claimsByCardId.put(cardId, claim);
		}

		// 2. �������� �����
		String[] cardIdsArray = cardIdsSet.toArray(new String[cardIdsSet.size()]);
		GroupResult<String, Card> cardsResult = bankrollService.getCard(cardIdsArray);

		// 3. �������� �������� ��������� ����
		Map<String, IKFLException> cardsExceptions = cardsResult.getExceptions();
		for (Map.Entry<String, IKFLException> exceptionEntry : cardsExceptions.entrySet())
		{
			String cardId = exceptionEntry.getKey();
			IKFLException exception = exceptionEntry.getValue();
			CardOperationClaim claim = claimsByCardId.get(cardId);
			log.error("�� ������� �������� ����� ��� " + claim, exception);
		}

		// 4. ���������� ��� ����
		Collection<Card> cards = cardsResult.getResults().values();
		Map<String, Card> cardsByNumber = new HashMap<String, Card>(cards.size());
		for (Card card : cards)
			cardsByNumber.put(card.getNumber(), card);

		return cardsByNumber;
	}

	private void markClaimsProcessing(List<CardOperationClaim> claims) throws BusinessException
	{
		claimService.markClaimsProcessing(EntityUtils.collectEntityIds(claims));
	}

	private void executeGateClaims(List<IPSCardOperationClaim> claims) throws BusinessException
	{
		IPSExecutiveService ipsService = GateSingleton.getFactory().service(IPSExecutiveService.class);
		try
		{
			ipsService.executeCardOperationClaims(claims);
		}
		catch (GateException e)
		{
			throw new BusinessException("���� �� ���������� ������", e);
		}
	}

	///////////////////////////////////////////////////////////////////////////

	private void failClaim(CardOperationClaim claim, String error)
	{
		try
		{
			claim.setStatus(CardOperationClaimStatus.FAILED);
			claim.setLastError(error);
			claim.setExecutingEndTime(Calendar.getInstance());
			claimService.addOrUpdate(claim);
		}
		catch (BusinessException e)
		{
			log.error("�� ������� ����������� ������", e);
		}
	}

	public void interrupt() throws UnableToInterruptJobException
	{
		isInterrupt = true;
	}
}
