package com.rssl.phizic.scheduler.jobs;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.finances.CardOperationClaim;
import com.rssl.phizic.business.finances.CardOperationClaimService;
import com.rssl.phizic.business.finances.CardOperationClaimStatus;
import com.rssl.phizic.business.profile.Profile;
import com.rssl.phizic.business.profile.ProfileService;
import com.rssl.phizic.business.resources.external.CardLink;
import com.rssl.phizic.business.resources.external.ExternalResourceService;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.config.ips.IPSConfig;
import com.rssl.phizic.job.BaseJob;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.LogModule;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.utils.DateHelper;
import org.apache.commons.collections.CollectionUtils;
import org.quartz.*;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Джоб актуализирующий заявки на загрузку карточных операций для клиентов, которые активно пользуются АЛФ
 * @author lepihina
 * @ created 23.12.13
 * @ $Author$
 * @ $Revision$
 */
public class UpdateCardOperationClaimsJob extends BaseJob implements StatefulJob, InterruptableJob
{
	private static final Log log = PhizICLogFactory.getLog(LogModule.Scheduler);

	private static final CardOperationClaimService cardOperationClaimService = new CardOperationClaimService();
	private static final ProfileService profileService = new ProfileService();
	private static final ExternalResourceService resourceService = new ExternalResourceService();

	private volatile boolean isInterrupt = false;
	private Calendar thresholdUpdate;

	/**
	 * @throws JobExecutionException
	 */
	public UpdateCardOperationClaimsJob() throws JobExecutionException
	{
	}

	@Override
	protected void executeJob(JobExecutionContext context) throws JobExecutionException
	{
		try
		{
			thresholdUpdate = Calendar.getInstance();
			thresholdUpdate.add(Calendar.MINUTE, -1 * ConfigFactory.getConfig(IPSConfig.class).getClaimAutoUpdateFrequency());
			while (!isInterrupt)
			{
				List<Profile> profiles = getPersonsList();
				if (profiles.isEmpty() || isInterrupt)
					break;

				for(Profile profile : profiles)
				{
					updateStatistics(profile);
					updateClaims(profile.getLoginId());
				}
			}
		}
		catch (Exception e)
		{
			log.error("Ошибка при обновлении статуса АЛФ заявок",e);
		}
	}

	/**
	 * Обновление заявок на загрузку операций
	 */
	private void updateClaims(Long loginId)
	{
		try
		{
			List<String> cardNumbers = getPersonCardNumbers(loginId);
			if (CollectionUtils.isEmpty(cardNumbers))
				return;
			List<CardOperationClaim> claims = cardOperationClaimService.findByLoginAndCard(loginId, cardNumbers);
			List<CardOperationClaim> claimsToAddOrUpdate = new ArrayList<CardOperationClaim>(cardNumbers.size());
			List<String> newCards = new ArrayList<String>(cardNumbers);

			// обновляем заявки
			for (CardOperationClaim claim: claims)
			{
				if ((claim.getStatus() == CardOperationClaimStatus.EXECUTED || claim.getStatus() == CardOperationClaimStatus.FAILED) && claim.getExecutingEndTime().compareTo(thresholdUpdate) < 0)
				{
					claim.setStatus(CardOperationClaimStatus.AUTO_WAITING);
					claim.setWaitingStartTime(Calendar.getInstance());
					claim.setExecutionAttemptNum(0);
					claimsToAddOrUpdate.add(claim);
				}
				newCards.remove(claim.getCardNumber());
			}

			// создаем новые заявки
			for (String cardNumber: newCards)
			{
				CardOperationClaim newClaim = new CardOperationClaim();
				newClaim.setOwnerId(loginId);
				newClaim.setStatus(CardOperationClaimStatus.AUTO_WAITING);
				newClaim.setCardNumber(cardNumber);
				newClaim.setWaitingStartTime(Calendar.getInstance());
				claimsToAddOrUpdate.add(newClaim);
			}

			cardOperationClaimService.addOrUpdateList(claimsToAddOrUpdate);
		}
		catch (Exception e)
		{
			log.error("Ошибка при обновлении статуса АЛФ заявок для логина " + loginId,e);
		}
	}

	private List<Profile> getPersonsList() throws BusinessException
	{
		IPSConfig ipsConfig = ConfigFactory.getConfig(IPSConfig.class);
		return cardOperationClaimService.getPersonsToUpdateClaims(ipsConfig.getMinCountOfUsingFinances(), ipsConfig.getMinProbabilityOfUsingFinances(), ipsConfig.getMaxClientsToUpdateClaims());
	}

	private List<String> getPersonCardNumbers(Long loginId) throws BusinessLogicException, BusinessException
	{
		List<CardLink> cardLinks = resourceService.getLinks(loginId, CardLink.class);
		List<String> cardNumbers = new ArrayList<String>(cardLinks.size());
		for(CardLink cardLink : cardLinks)
			cardNumbers.add(cardLink.getNumber());

		return cardNumbers;
	}

	private void updateStatistics(Profile profile) throws BusinessException
	{
		long diff = DateHelper.daysDiff(profile.getLastUsingFinancesDate(), DateHelper.getCurrentDate());
		if(diff > ConfigFactory.getConfig(IPSConfig.class).getMaxPauseInUsingFinances())
		{
			profile.setUsingFinancesCount(0);
			profile.setUsingFinancesEveryThreeDaysCount(0);
		}

		profile.setLastUpdateCardOperationClaimsDate(DateHelper.getCurrentDate());
		profileService.update(profile);
	}

	public void interrupt() throws UnableToInterruptJobException
	{
		isInterrupt = true;
	}
}
