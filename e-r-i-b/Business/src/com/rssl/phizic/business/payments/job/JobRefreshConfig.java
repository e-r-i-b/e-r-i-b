package com.rssl.phizic.business.payments.job;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.dictionaries.providers.ServiceProviderBase;
import com.rssl.phizic.business.dictionaries.providers.ServiceProviderService;
import com.rssl.phizic.config.*;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizicgate.manager.config.AdaptersConfig;
import org.apache.commons.lang.ArrayUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author lukina
 * @ created 24.12.2012
 * @ $Author$
 * @ $Revision$
 */

public class JobRefreshConfig extends Config
{
	public static final String PAYMENT_JOB_SELECT_ORDER = "send.delayed.payment.job.select.order";
	public static final String MAX_JOBS_COUNT = "max.jobs.count";
	public static final String MAX_ROWS_FROM_FETCH = "max.rows.for.job";
	public static final String NUM_REQUESTS_OPERATION_PREFIX = "restriction.number.requests.state.operation.";
	public static final String EXPIRED_DOCUMENT_HOUR_PREFIX = "document.confirmdate.expire.hour.";
	public static final String RESEND_ESB_PAYMENT_JOB = "ResendESBPaymentJob";
	public static final String DEPOCOD_PAYMENTS_EXEC_JOB = "DepoCodPaymentsExecutionJob";
	public static final String CHECK_PAYMENTS_EXEC_JOB = "CheckPaymentExecutionJob";
	public static final String SEND_DELAYED_PAYMENTS_JOB_NAME = "SendDelayedPaymentsJob";
	public static final String SEND_OFFLINE_DELAYED_PAYMENTS_JOB_NAME = "SendOfflineDelayedPaymentsJob";
	public static final String NUMBER_OF_RESEND_ESB_PAYMENT = "resend.payment.job.max.payment.try";
	//������������ ������ ������ �������� �������� �� ���
	public static final String REPEAT_SEND_REQUEST_DOCUMENT_TIMEOUT = "com.rssl.iccs.phizic.payments.job.repeatSendRequestDocumentTimeout";
	//������������ ������� ��������� ��������� �������� �����
	public static final String CATALOG_AGGREGATION_INTERVAL = "com.rssl.iccs.phizic.catalog.aggreagation.interval";

	private static final String PROVIDER_NUMBERS_EXCLUDE_SETTING = "job.provider.number.repeat.send.document";
	private static final String DOCUMENT_UPDATE_WAITING_TIME = "job.document.waiting.time";
	private static final String PAYMENT_NOTIFICATIONS_ACTUAL_PERIOD = "job.notifications.payment.period.actual";
	private static final String NEWS_DISTRIBUTION_PERIOD_PROPERTY = "job.news.distribution.period";
	private static final String ERROR_MSG_REPEAT_SEND_REQUEST_DOCUMENT_TIMEOUT = "������ ��� ��������� �������� ������������ ������ �������� �������� �� ���";
	private static final String TIMESTAMP_FORMAT_HH24MMSS = "HH:mm:ss";
	private static final String MAX_LOAD_CARD_OPERATIONS_JOBS_COUNT = "max.load.card.operations.jobs.count";
	private static final String LOAD_CARD_OPERATIONS_JOB_STATEFUL = "load.card.operations.job.stateful";

	private static final Log log = PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_SCHEDULER);

	/**
	 * ��� ���������� ������� �������� ��� ��������� ���������.
	 */
	private String paymentSortOrderResendESBPayment;

	/**
	 * ������������ ���������� ������������ ���������� ������.
	 */
	private Integer maxJobcCountResendESBPayment;

	/**
	 * ������������ ���������� ������� ��� ��������� ������.
	 */
	private Integer maxRowsFromFetchResendESBPayment;

	/**
	 * ��� ���������� ������� �������� ��� ��������� ���������.
	 */
	private String paymentSortOrder;

	/**
	 * ������������ ���������� ������������ ���������� ������.
	 */
	private Integer maxJobcCount;

	/**
	 * ������������ ���������� ������� ��� ��������� ������.
	 */
	private Integer maxRowsFromFetch;
	/**
	 * ����������� ���������� �������� ������� �������� IQWave.
	 */
	private Map<String, Integer> restrictionNumberRequests = new HashMap<String, Integer>(2);

	/**
	 * ������������ ������ ������ �������� �������� �� ���.
	 */
	private Calendar repeatSendRequestDocumentTimeout;

	/**
	 * ����� �����, ����� ������� �������� ��������� ���������� � �� ���� �� ���������� ���������.
	 */
	private Map<String, Integer> numberOfHourAfterDocumentIsExpired = new HashMap<String, Integer>(2);

	/**
	 * ������������ ���������� ������� ������ ����������� �������.
	 */
	private Integer numberOfResendPayment;

	private Integer maxLoadCardOperationsJobsCount;

	private Boolean loadCardOperationsJobStateful;

	public JobRefreshConfig(PropertyReader reader)
	{
		super(reader);
	}


	/**
	 * ������ ����������� iqw, ��������� � ����� ������� ����� ��������� �� ����� �� ��������� �������
	 */
	private List<Long> providerNumbersExclude;

	/**
	 * ����� ��������, ����� �������� �������� �������� ������ ��������� (� ��������)
	 */
	private Integer documentUpdateWaitingTime;

	/**
	 * ������ ��������� ��������
	 */
	private String newsDistributionPeriod;

	/**
	 * ������ ������������ ���������� �� ���������� �������� (� ������)
	 */
	private Integer paymentNotificationsActualPeriod;

	public String getPaymentSortOrder()
	{
		return paymentSortOrder;
	}

	public Integer getMaxJobcCount()
	{
		return maxJobcCount;
	}

	public Integer getMaxRowsFromFetch()
	{
		return maxRowsFromFetch;
	}

	public String getPaymentSortOrder(String jobName)
	{
		if (jobName.contains(RESEND_ESB_PAYMENT_JOB))
			return paymentSortOrderResendESBPayment;

		return paymentSortOrder;
	}

	/**
	 * �������� ������������ ���������� ������������ ���������� ������ �� 1 �������� ��
	 * @param jobName ��� �����.
	 * @return ����������
	 */
	public Integer getMaxJobsCount(String jobName)
	{
		if (jobName.contains(RESEND_ESB_PAYMENT_JOB))
			return maxJobcCountResendESBPayment;

		return maxJobcCount;
	}

	public Integer getMaxRowsFromFetch(String jobName)
	{
		if (jobName.contains(RESEND_ESB_PAYMENT_JOB))
			return maxRowsFromFetchResendESBPayment;
		
		return maxRowsFromFetch;
	}

	public Integer getRestrictionNumberRequests(String jobName)
	{
		return restrictionNumberRequests.get(jobName);
	}

	public Calendar getRepeatSendRequestDocumentTimeout()
	{
		return repeatSendRequestDocumentTimeout;
	}

	public Integer getNumberOfHourAfterDocumentIsExpired(String jobName)
	{
		return numberOfHourAfterDocumentIsExpired.get(jobName);
	}

	public Integer getNumberOfResendPayment()
	{
		return numberOfResendPayment;
	}

	/**
	 * @return ������ ����������� iqw, ��������� � ����� ������� ����� ��������� �� ����� �� ��������� �������
	 */
	public List<Long> getProviderNumbersExclude()
	{
		return providerNumbersExclude;
	}

	/**
	 * @return ����� ��������, ����� �������� �������� �������� ������ ��������� (� ��������)
	 */
	public Integer getDocumentUpdateWaitingTime()
	{
		return documentUpdateWaitingTime;
	}

	/**
	 * @return ������ ��������� ��������
	 */
	public String getNewsDistributionPeriod()
	{
		return newsDistributionPeriod;
	}

	/**
	 * @return ������ ������������ ���������� �� ���������� �������� (� ������)
	 */
	public Integer getPaymentNotificationsActualPeriod()
	{
		return paymentNotificationsActualPeriod;
	}

	/**
	 * @return ���������� ������������ ���������� ������ ��������� ��������� ��������
	 */
	public Integer getMaxLoadCardOperationsJobsCount()
	{
		return maxLoadCardOperationsJobsCount;
	}

	/**
	 * @return ����� ������ ����� ��������� ��������� ��������.
	 * true - stateful
	 */
	public Boolean getLoadCardOperationsJobStateful()
	{
		return loadCardOperationsJobStateful;
	}

	private void addNumberOfHourAfterDocumentIsExpired(String jobName)
	{
		numberOfHourAfterDocumentIsExpired.put(jobName, getIntProperty(EXPIRED_DOCUMENT_HOUR_PREFIX + jobName));
	}

	protected void doRefresh()
	{
		paymentSortOrder = getProperty(PAYMENT_JOB_SELECT_ORDER);
		maxJobcCount = getIntProperty(MAX_JOBS_COUNT);
		maxRowsFromFetch = getIntProperty(MAX_ROWS_FROM_FETCH);
		restrictionNumberRequests.put(CHECK_PAYMENTS_EXEC_JOB, getIntProperty(NUM_REQUESTS_OPERATION_PREFIX + CHECK_PAYMENTS_EXEC_JOB));
		restrictionNumberRequests.put(DEPOCOD_PAYMENTS_EXEC_JOB, getIntProperty(NUM_REQUESTS_OPERATION_PREFIX + DEPOCOD_PAYMENTS_EXEC_JOB));
		addNumberOfHourAfterDocumentIsExpired(RESEND_ESB_PAYMENT_JOB);
		addNumberOfHourAfterDocumentIsExpired(DEPOCOD_PAYMENTS_EXEC_JOB);
		addNumberOfHourAfterDocumentIsExpired(SEND_DELAYED_PAYMENTS_JOB_NAME);
		addNumberOfHourAfterDocumentIsExpired(SEND_OFFLINE_DELAYED_PAYMENTS_JOB_NAME);
		paymentSortOrderResendESBPayment = getProperty(PAYMENT_JOB_SELECT_ORDER + "." + RESEND_ESB_PAYMENT_JOB);
		maxJobcCountResendESBPayment = getIntProperty(MAX_JOBS_COUNT + "." + RESEND_ESB_PAYMENT_JOB);
		maxRowsFromFetchResendESBPayment = getIntProperty(MAX_ROWS_FROM_FETCH + "." + RESEND_ESB_PAYMENT_JOB);
		numberOfResendPayment = getIntProperty(NUMBER_OF_RESEND_ESB_PAYMENT);
		providerNumbersExclude = getExcludingProviderIds();
		documentUpdateWaitingTime = getIntProperty(DOCUMENT_UPDATE_WAITING_TIME);

		String repeatSendRequestDocumentString = getProperty(REPEAT_SEND_REQUEST_DOCUMENT_TIMEOUT);
		if (StringHelper.isNotEmpty(repeatSendRequestDocumentString))
		{
			SimpleDateFormat sdf = new SimpleDateFormat(TIMESTAMP_FORMAT_HH24MMSS);
			try
			{
				Calendar clnd = Calendar.getInstance();
				clnd.setTime(sdf.parse(repeatSendRequestDocumentString));
				repeatSendRequestDocumentTimeout = clnd;
			}
			catch (ParseException e)
			{
				throw new ConfigurationException(ERROR_MSG_REPEAT_SEND_REQUEST_DOCUMENT_TIMEOUT,e);
			}
		}

		newsDistributionPeriod = getProperty(NEWS_DISTRIBUTION_PERIOD_PROPERTY);
		paymentNotificationsActualPeriod = getIntProperty(PAYMENT_NOTIFICATIONS_ACTUAL_PERIOD);
		maxLoadCardOperationsJobsCount = getIntProperty(MAX_LOAD_CARD_OPERATIONS_JOBS_COUNT);
		loadCardOperationsJobStateful = getBoolProperty(LOAD_CARD_OPERATIONS_JOB_STATEFUL);
	}

	private List<Long> getExcludingProviderIds()
	{
		String strProviderNumbers = getProperty(PROVIDER_NUMBERS_EXCLUDE_SETTING);
		if(StringHelper.isEmpty(strProviderNumbers))
			return Collections.emptyList();

		AdaptersConfig adaptersConfig = ConfigFactory.getConfig(AdaptersConfig.class);
		String uuid = adaptersConfig.getCardTransfersAdapter().getUUID();

		String[] providerNumbers = strProviderNumbers.split(",");
		if(ArrayUtils.isEmpty(providerNumbers))
			return Collections.emptyList();

		List<Long> result = new ArrayList<Long>();
		ServiceProviderService providerService = new ServiceProviderService();
		for(String provideNumber : providerNumbers)
		{
			try
			{
				String number = provideNumber.trim();
				// ����� ���������� � ��� ������ � ������� ��� iqw ���������
				ServiceProviderBase provider = providerService.find(number, number, uuid);
				if(provider == null)
				{
					log.warn("���������� � code=" + provideNumber + " codeService=" + provideNumber + " � �������� " + uuid + "�� ����������");
					continue;
				}

				result.add(provider.getId());
			}
			catch (BusinessException e)
			{
				log.error(e.getMessage(), e);
				return Collections.emptyList();
			}
		}

		return result;
	}
}
