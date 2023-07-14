package com.rssl.phizic.business.news;

import com.rssl.auth.csa.wsclient.events.ClearCacheAuthNewsEvent;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.SimpleJobService;
import com.rssl.phizic.business.SimpleService;
import com.rssl.phizic.business.dictionaries.synchronization.log.ChangeType;
import com.rssl.phizic.business.dictionaries.synchronization.log.DictionaryRecordChangeInfoService;
import com.rssl.phizic.business.payments.job.NewsDistributionJob;
import com.rssl.phizic.dataaccess.DataAccessException;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizic.dataaccess.hibernate.HibernateExecutor;
import com.rssl.phizic.dataaccess.query.ExecutorQuery;
import com.rssl.phizic.events.EventSender;
import org.apache.commons.collections.CollectionUtils;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Expression;
import org.quartz.JobDataMap;

import java.util.*;

/**
 * @author lukina
 * @ created 24.08.2010
 * @ $Author$
 * @ $Revision$
 */

public class NewsService
{
	private static final SimpleService simpleService = new SimpleService();
	private static final DictionaryRecordChangeInfoService dictionaryRecordChangeInfoService = new DictionaryRecordChangeInfoService();
	private static final String MAIN_PAGE_TYPE = "MAIN_PAGE";

	private final SimpleJobService jobService = new SimpleJobService();

	/**
	 * ����� ������� �� ��������������
	 * @param id - ������������
	 * @param instanceName - ������� ��
	 * @return �������
	 */
	public News findById(Long id, String instanceName) throws BusinessException
	{
		return simpleService.findById(News.class, id, instanceName);
	}

	/**
	 * ��������� �������
	 * @param news �������
	 * @param instanceName ������� ��
	 * @return ����������� �������
	 * @throws BusinessException
	 */
	public News addOrUpdate(final News news, final String instanceName) throws BusinessException
	{
		try
		{
			return HibernateExecutor.getInstance(instanceName).execute(new HibernateAction<News>()
			{
				public News run(Session session) throws Exception
				{
					News savedNews = simpleService.addOrUpdate(news, instanceName);
					addToLog(savedNews, ChangeType.update);
					return savedNews;
				}
			});
		}
		catch (BusinessException e)
		{
			throw e;
		}
		catch (Exception e)
		{
			throw new BusinessException("������ ���������� �������.", e);
		}
	}

	/**
	 * �������� �������
	 * @param news �������
	 * @param instanceName ��� �������� ��
	 * @throws BusinessException
	 */
	public void remove(final News news, final String instanceName) throws BusinessException
	{
		try
		{
			HibernateExecutor.getInstance(instanceName).execute(new HibernateAction<Void>()
			{
				public Void run(Session session) throws Exception
				{
					simpleService.remove(news, instanceName);
					addToLog(news, ChangeType.delete);
					return null;
				}
			});
		}
		catch (BusinessException e)
		{
			throw e;
		}
		catch (Exception e)
		{
			throw new BusinessException("������ ���������� �������.", e);
		}
	}

	/**
	 * �������� ��������� JMS � ����� �� ������� ���� ��������
	 * @throws BusinessException
	 */
	public void sendClearCacheAuthNewsEvent() throws BusinessException
	{
		try
		{
			EventSender.getInstance().sendEvent(new ClearCacheAuthNewsEvent());
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 * @param departmentTB ������� ��� �������� ������ ������� 
	 * @param maxResult ������������ ���������� �������� � �������
	 * @return ������ �������� ��� ��������
	 * @throws BusinessException
	 */
	public List<News> getNewsListForMainPage(final String departmentTB, final int maxResult) throws BusinessException
	{
		try
		{
			ExecutorQuery query = new ExecutorQuery(HibernateExecutor.getInstance(), "com.rssl.phizic.operations.news.ListNewsOperation.clientList");
			query.setParameter("fromDate", null);
			query.setParameter("toDate", null);
			query.setParameter("search", null);
			query.setParameter("like_search", null);
			query.setParameter("important", null);
			query.setParameter("departmentTB", departmentTB);
			query.setParameter("type", MAIN_PAGE_TYPE);
			query.setMaxResults(maxResult);
			query.setMaxResults(maxResult);
			return query.executeList();
		}
		catch (DataAccessException e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 * ����� �������� ��� ������� �� �������������� �������
	 * @param newsId - ������������� �������
	 * @return ��������
	 */
	public NewsDistribution findDistributionByNewsId(Long newsId) throws BusinessException
	{
		DetachedCriteria criteria = DetachedCriteria.forClass(NewsDistribution.class);
        criteria.add(Expression.eq("newsId", newsId));
		return simpleService.findSingle(criteria);
	}

	/**
	 * ����� �������� �� �� ��������������
	 * @param id - ������������� ��������
	 * @return ��������
	 */
	public NewsDistribution findDistributionById(Long id) throws BusinessException
	{
		return simpleService.findById(NewsDistribution.class, id);
	}

	/**
	 * ��������/�������� ������ � ��������
	 * @param newsDistribution - ��������
	 * @return ����������� ��������
	 */
	public NewsDistribution addOrUpdateNewsDistribution(NewsDistribution newsDistribution) throws BusinessException
	{
		return simpleService.addOrUpdate(newsDistribution);
	}

	/**
	 * ������� ������ � ��������
	 * @param newsDistribution - ��������
	 */
	public void removeNewsDistribution(NewsDistribution newsDistribution) throws BusinessException
	{
		simpleService.remove(newsDistribution);
	}

	/**
	 * ������� ���� ��� ��������� ��������
	 * @param cronExpression - cron-��������� ������ �����
	 * @param distributionId - id ��������
	 */
	public void createNewDistributionJob(String cronExpression, Long distributionId) throws BusinessException
	{
		try
		{
			Map<String, Long> data = new HashMap<String, Long>();
			data.put(NewsDistributionJob.PROCESSED_DISTRIBUTION_ID_KEY, distributionId);
			jobService.addNewCronJob(NewsDistributionJob.class.getSimpleName(), cronExpression, NewsDistributionJob.class.getName(), null, new JobDataMap(data));
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 * ����� �������� ��� ��������
	 * @param newsIds - �������������� ��������
	 * @param type - ��� �������
	 * @return - ���<id ������� ������� ����������� ��������, ��������>
	 */
	public Map<Long, NewsDistribution> findNewsDistributions(final NewsDistributionType type, final List<Long> newsIds) throws BusinessException
	{
		try
		{
			List<NewsDistribution> distributions = HibernateExecutor.getInstance().execute(new HibernateAction<List<NewsDistribution>>()
			{
				public List<NewsDistribution> run(Session session) throws Exception
				{
					Query query = session.getNamedQuery("com.rssl.phizic.business.news.findNewsDistributions");
					query.setParameter("distrType", type);
					query.setParameterList("newsIds", newsIds);
					return query.list();
				}
			});

			if (CollectionUtils.isEmpty(distributions))
				return Collections.EMPTY_MAP;

			Map<Long, NewsDistribution> result = new HashMap<Long, NewsDistribution>();
			for (NewsDistribution distribution : distributions)
				result.put(distribution.getNewsId(), distribution);

			return result;
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}
	}

	/**
	 * ����� �������� ��������
	 * @return ������ �������� ��������
	 */
	public List<NewsDistribution> findActiveDistributions() throws BusinessException
	{
		DetachedCriteria criteria = DetachedCriteria.forClass(NewsDistribution.class);
		criteria.add(Expression.eq("state", NewsDistributionsState.PROCESSED));
		return simpleService.find(criteria);
	}

	private void addToLog(News savedNews, ChangeType changeType) throws BusinessException
	{
		if (NewsType.LOGIN_PAGE != savedNews.getType())
			dictionaryRecordChangeInfoService.addChangesToLog(savedNews, changeType);
	}
}
