package com.rssl.phizic.operations.news;

import com.rssl.phizic.business.dictionaries.synchronization.MultiBlockModeDictionaryHelper;
import com.rssl.phizic.business.news.NewsState;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.Constants;
import com.rssl.phizic.business.news.*;
import com.rssl.phizic.business.payments.job.JobRefreshConfig;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.context.EmployeeContext;
import com.rssl.phizic.operations.EditEntityOperation;
import com.rssl.phizic.operations.OperationBase;
import com.rssl.phizic.utils.StringHelper;
import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * �������� �������� email-�������� � ��������\�������� �����
 * @author gladishev
 * @ created 01.11.13
 * @ $Author$
 * @ $Revision$
 */
public class EditNewsDistributionsOperation extends OperationBase implements EditEntityOperation
{
	private static final NewsService newsService = new NewsService();
	private News news;
	private NewsDistributionType newsDistributionType;
	private int mailCount;
	private long timeout;

	/**
	 * ������������� ��������
	 * @param newsId - ������������� �������
	 * @param mainPage - true - ������� �������, false - ������� �������� �����
	 * @param mailCount - ���������� ������ � �����
	 * @param timeout - ������� ����� ���������� �����
	 */
	public void initialize(Long newsId, boolean mainPage, int mailCount, long timeout) throws BusinessException, BusinessLogicException
	{
		String instanceName = mainPage ? getInstanceName() : Constants.DB_CSA;
		news = newsService.findById(newsId, instanceName);
		newsDistributionType = mainPage ?  NewsDistributionType.MAIN_PAGE : NewsDistributionType.LOGIN_PAGE;

		if (news == null)
			throw new BusinessLogicException("������� � id = " + newsId + " �� �������");

		if (NewsState.NOT_PUBLISHED == news.getState())
			throw new BusinessLogicException("�������� ����������� �� �������� � ������� 'C���� � ����������' ���������.");

		NewsDistribution distribution = newsService.findDistributionByNewsId(newsId);

		if (distribution != null)
			throw new BusinessLogicException("��������� �������� �� ������ ������� ���������.");

		if (org.apache.commons.collections.CollectionUtils.isNotEmpty(newsService.findActiveDistributions()))
			throw new BusinessLogicException("������ ��� �������� ��������: ������� �������� ��������.");

		this.mailCount = mailCount;
		this.timeout = timeout;
	}

	public void save() throws BusinessException, BusinessLogicException
	{
		String newsDistributionPeriod = ConfigFactory.getConfig(JobRefreshConfig.class).getNewsDistributionPeriod();

		if (StringHelper.isEmpty(newsDistributionPeriod))
			throw new BusinessLogicException("������ �� ����� �������� ����� ��� ��������: �� ����� ������ �������� (job.news.distribution.period)");

		NewsDistribution distribution = new NewsDistribution();
		distribution.setLogin(EmployeeContext.getEmployeeDataProvider().getEmployeeData().getEmployee().getLogin());
		distribution.setDate(Calendar.getInstance());
		distribution.setType(newsDistributionType);
		distribution.setNewsId(news.getId());
		distribution.setState(NewsDistributionsState.PROCESSED);
		distribution.setMailCount(mailCount);
		distribution.setTimeout(timeout);
		distribution.setTitle(news.getTitle());
		distribution.setText(news.getText());

		List<String> terbanks = new ArrayList<String>(news.getDepartments());

		distribution.setTerbanks(StringUtils.join(terbanks, ','));

		NewsDistribution newsDistribution = newsService.addOrUpdateNewsDistribution(distribution);

		try
		{
			createJob(newsDistributionPeriod, newsDistribution);
		}
		catch (BusinessException e)
		{
			newsService.removeNewsDistribution(newsDistribution);
			throw e;
		}
	}

	private void createJob(String newsDistributionPeriod, NewsDistribution newsDistribution) throws BusinessException
	{
		StringBuilder cronExpression = new StringBuilder();
		cronExpression.append("0 0/");
		cronExpression.append(timeout);
		cronExpression.append(" ");
		cronExpression.append(newsDistributionPeriod);
		cronExpression.append(" * * ?");

		newsService.createNewDistributionJob(cronExpression.toString(), newsDistribution.getId());
	}

	public Object getEntity() throws BusinessException, BusinessLogicException
	{
		return null;
	}

	@Override
	protected String getInstanceName()
	{
		return MultiBlockModeDictionaryHelper.getDBInstanceName();
	}
}
