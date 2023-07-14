package com.rssl.phizic.business.finances;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.SimpleService;
import com.rssl.phizic.business.profile.Profile;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.config.ips.IPSConfig;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizic.dataaccess.hibernate.HibernateExecutor;
import com.rssl.phizic.utils.DateHelper;
import org.apache.commons.collections.CollectionUtils;
import org.hibernate.Query;
import org.hibernate.Session;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.List;

import static com.rssl.phizic.business.finances.CardOperationClaimStatus.*;

/**
 * @author Erkin
 * @ created 26.07.2011
 * @ $Author$
 * @ $Revision$
 */

/**
 * ������ ��� ������ � �������� �� ��������� �������� �� �����
 */
public class CardOperationClaimService
{
	private final SimpleService simpleService = new SimpleService();

	///////////////////////////////////////////////////////////////////////////

	/**
	 * ��������� ��������� � ������
	 * @param claim - ����� ��� ������ ������
	 */
	public void addOrUpdate(CardOperationClaim claim) throws BusinessException
	{
		simpleService.addOrUpdate(claim);
	}

	/**
	 * ����� ��� ����������/��������� ������ ������
	 * @param claims - ������ ������
	 * @throws BusinessException
	 */
	public void addOrUpdateList (List<CardOperationClaim> claims) throws BusinessException
	{
		if (claims.isEmpty()) return;
		simpleService.addOrUpdateList(claims);
	}

	/**
	 * ���������� ������ �� ��������� � �����
	 * @param loginId - ID ������ ���������
	 * @param cardNumbers - ������ ������� ����
	 * @return ������ 
	 * @throws BusinessException
	 */
	public List<CardOperationClaim> findByLoginAndCard(final Long loginId, final List<String> cardNumbers) throws BusinessException
	{
		if (CollectionUtils.isEmpty(cardNumbers))
			return new ArrayList<CardOperationClaim>();

		try
		{
			return HibernateExecutor.getInstance().execute(new HibernateAction<List<CardOperationClaim>>()
			{
				public List<CardOperationClaim> run(Session session) throws Exception
				{
					Query query = session.getNamedQuery("com.rssl.phizic.business.finances.CardOperationClaimService.findByLoginAndCard");
					query.setLong("loginId", loginId);
					query.setParameterList("cardNumbers", cardNumbers);
					return query.list();
				}
			});
		}
		catch (Exception e)
		{
			throw new BusinessException("������ ��������� ������ ������. ", e);
		}
	}

	/**
	 * ���������� ������ ������, �� ������� ���������� ���������� ��������
	 * @return ������ ������, �� ������� ���������� ���������� ��������
	 * @throws BusinessException
	 */
	public List<CardOperationClaim> getClaimsToLoadOperations () throws BusinessException
	{
		final IPSConfig config = ConfigFactory.getConfig(IPSConfig.class);

		final int maxResults = config.getClaimsBatchSize();
		if (maxResults <= 0)
			throw new IllegalArgumentException("�������� maxResults ������ ���� �������������");

		final Calendar now = Calendar.getInstance();
		final Calendar staleLatestTime = DateHelper.addSeconds(now, -config.getClaimExecutionMaxTime());
		try
		{
			return HibernateExecutor.getInstance().execute(new HibernateAction<List<CardOperationClaim>>()
			{
				public List<CardOperationClaim> run(Session session) throws Exception
				{
					Query query = session.getNamedQuery("com.rssl.phizic.business.finances.CardOperationClaimService.getClaimsToLoadOperations");
					query.setCalendar("staleLatestTime", staleLatestTime);
					query.setInteger("executionAttemptMaxNum", config.getClaimExecutionAttemptMaxNum());
					query.setInteger("maxCount", maxResults);
					//noinspection unchecked
					return (List<CardOperationClaim>)query.list();
				}
			});
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 * �������� ������ ��� �����������
	 * ������ ��������� � ��� ����� ������ ����������
	 * @param claimsIds - �������������� ������, ������� ����� ��������
	 */
	public void markClaimsProcessing(final Collection<Long> claimsIds) throws BusinessException
	{
		if (claimsIds.isEmpty())
			return;
		
		final Calendar now = Calendar.getInstance();
		try
		{
			HibernateExecutor.getInstance().execute(new HibernateAction<Void>()
			{
				public Void run(Session session) throws Exception
				{
					Query query = session.getNamedQuery("com.rssl.phizic.business.finances.CardOperationClaimService.markClaimsProcessing");
					query.setParameterList("claimIds", claimsIds);
					// Hibernate �� ����� ��������� ������ enum-� � update-�������� => ���������� String-��
					query.setString("processingStatus", PROCESSING.name());
					query.setCalendar("processingStartTime", now);
					query.executeUpdate();
					return null;
				}
			});
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 * �������� ������ ��������������
	 * @param claim - ������
	 * @return true - ������ ���������� � ������
	 *         false - ������ �� ���������� � ������, ��� ��� ���� �������� � ������� ������� � ����� ������ ���������
	 * @throws BusinessException
	 */
	public boolean markClaimsProcessing(final CardOperationClaim claim) throws BusinessException
	{
		try
		{
			return HibernateExecutor.getInstance().execute(new HibernateAction<Boolean>()
			{
				public Boolean run(Session session) throws Exception
				{
					Query query = session.getNamedQuery("com.rssl.phizic.business.finances.CardOperationClaimService.markClaimProcessing");
					query.setParameter("claimId", claim.getId());
					query.setParameter("processingStatus", PROCESSING.name());
					query.setCalendar("oldProcessingStartTime", claim.getProcessingStartTime());
					query.setCalendar("newProcessingStartTime", Calendar.getInstance());
					query.setParameter("oldExecutionAttemptNum", claim.getExecutionAttemptNum());
					return query.executeUpdate() == 1;
				}
			});
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 * ���������� ������ �������� �������� ��� ������� ���������� �������� ������ ������ ��� �������� ��������� ��������
	 * @param minCountOfUsingFinances - ����������� ���������� ������������� ���
	 * @param minProbabilityOfUsingFinances - ����������� �������� ����������� ������������� ��� ��� � 3 ���
	 * @param maxClientsToUpdateClaims - ������������ ���������� ������� ������������ ��� ��������, �������������� �� ���� ������
	 * @return ������ �������� ��������
	 */
	public List<Profile> getPersonsToUpdateClaims(final int minCountOfUsingFinances, final float minProbabilityOfUsingFinances, final int maxClientsToUpdateClaims) throws BusinessException
	{
		try
		{
			return HibernateExecutor.getInstance().execute(new HibernateAction<List<Profile>>()
			{
				public List<Profile> run(Session session) throws Exception
				{
					Query query = session.getNamedQuery("com.rssl.phizic.business.finances.CardOperationClaimService.getPersonsToUpdateClaims");
					query.setCalendar("currentDate", DateHelper.getCurrentDate());
					query.setInteger("minCountOfUsingFinances", minCountOfUsingFinances);
					query.setFloat("minProbabilityOfUsingFinances", minProbabilityOfUsingFinances);
					query.setInteger("maxResults", maxClientsToUpdateClaims);
					//noinspection unchecked
					return (List<Profile>)query.list();
				}
			});
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}
}
