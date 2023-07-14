package com.rssl.phizic.config.reports;

import com.rssl.phizic.config.Config;
import com.rssl.phizic.config.ConfigurationException;
import com.rssl.phizic.config.PropertyReader;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.LogModule;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.utils.DateHelper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * ������ ��� ������ � ����������� ��� ������� �� ������ ���������
 * @author gladishev
 * @ created 24.02.2015
 * @ $Author$
 * @ $Revision$
 */

public class BusinessReportConfig extends Config
{
	private static final Log log = PhizICLogFactory.getLog(LogModule.Core);

	public static final String REPORTS_TRANSFER_ON_KEY = "reports.business.transfer.on";
	public static final String REPORTS_PAYMENT_ON_KEY = "reports.business.payment.on";
	public static final String REPORTS_OPEN_ACCOUNT_ON_KEY = "reports.business.openaccount.on";

	public static final String REPORTS_DEVIATION_ALL_KEY = "reports.business.allreports.deviation";
	public static final String REPORTS_DEVIATION_TRANSFER_KEY = "reports.business.transfer.deviation";
	public static final String REPORTS_DEVIATION_PAYMENT_KEY = "reports.business.payment.deviation";
	public static final String REPORTS_DEVIATION_OPEN_ACCOUNT_KEY = "reports.business.openaccount.deviation";

	public static final String REPORTS_BLOCK_KEY = "reports.business.block";
	public static final String REPORTS_CHANNEL_KEY = "reports.business.channel";

	public static final String MAIL_RECEIVERS_KEY = "reports.business.mail.receivers";
	public static final String MAIL_THEME_KEY = "reports.business.mail.theme";
	public static final String MAIL_TEXT_KEY = "reports.business.mail.text";
	public static final String MAIL_TIME_KEY = "reports.business.mail.time";

	public static final String AGGREGATE_ON_KEY = "reports.business.aggregate.on";
	public static final String SEND_ON_KEY = "reports.business.send.on";

	public static final String FROM_PERIOD = "reports.business.period.from";
	public static final String TO_PERIOD = "reports.business.period.to";

	private boolean transferOn;
	private boolean paymentOn;
	private boolean accountOpenOn;

	private int deviationAll;
	private int deviationTransfer;
	private int deviationPayment;
	private int deviationOpenAccount;

	private int blockNumber;
	private String channel;

	private String mailReceivers;
	private String mailTheme;
	private String mailText;

	private boolean aggregateOn;
	private boolean sendOn;

	private Calendar timeForSendMail;

	/**
	 * ctor
	 * @param reader �����.
	 */
	public BusinessReportConfig(PropertyReader reader)
	{
		super(reader);
	}

	@Override
	protected void doRefresh() throws ConfigurationException
	{
		this.transferOn = getBoolProperty(REPORTS_TRANSFER_ON_KEY);
		this.paymentOn = getBoolProperty(REPORTS_PAYMENT_ON_KEY);
		this.accountOpenOn = getBoolProperty(REPORTS_OPEN_ACCOUNT_ON_KEY);

		this.deviationAll = getIntProperty(REPORTS_DEVIATION_ALL_KEY);
		this.deviationTransfer = getIntProperty(REPORTS_DEVIATION_TRANSFER_KEY);
		this.deviationPayment = getIntProperty(REPORTS_DEVIATION_PAYMENT_KEY);
		this.deviationOpenAccount = getIntProperty(REPORTS_DEVIATION_OPEN_ACCOUNT_KEY);

		this.blockNumber = getProperty(REPORTS_BLOCK_KEY).equals("all") ? -1 : getIntProperty(REPORTS_BLOCK_KEY);
		try
		{
			this.channel = getProperty(REPORTS_CHANNEL_KEY);
		}
		catch (IllegalArgumentException e)
		{
			log.error(e);
			this.channel = "all";
		}

		this.mailReceivers = getProperty(MAIL_RECEIVERS_KEY);
		this.mailTheme = getProperty(MAIL_THEME_KEY);
		this.mailText = getProperty(MAIL_TEXT_KEY);

		this.aggregateOn = getBoolProperty(AGGREGATE_ON_KEY);
		this.sendOn = getBoolProperty(SEND_ON_KEY);
		try
		{
			this.timeForSendMail = DateHelper.toCalendar(new SimpleDateFormat("HH:mm").parse(getProperty(MAIL_TIME_KEY, "00:00")));
		}
		catch (ParseException e)
		{
			throw new ConfigurationException("�������� ������ ������� ��� ���������: " + MAIL_TIME_KEY, e);
		}
	}

	/**
	 * @return ������������� ���������� ������ �� ���������
	 */
	public boolean isTransferOn()
	{
		return transferOn;
	}

	/**
	 * @return ������������� ���������� ������ �� ��������
	 */
	public boolean isPaymentOn()
	{
		return paymentOn;
	}

	/**
	 * @return ������������� ���������� ������ �� �������� �������
	 */
	public boolean isAccountOpenOn()
	{
		return accountOpenOn;
	}

	/**
	 * @return ���������� �� ����� (��� ���� �������)
	 */
	public int getDeviationAll()
	{
		return deviationAll;
	}

	/**
	 * @return ���������� �� ����� ��� ������� �� ���������
	 */
	public int getDeviationTransfer()
	{
		return deviationTransfer;
	}

	/**
	 * @return ���������� �� ����� ��� ������� �� ���������
	 */
	public int getDeviationPayment()
	{
		return deviationPayment;
	}

	/**
	 * @return ���������� �� ����� ��� ������� �� ������� �������� �������
	 */
	public int getDeviationOpenAccount()
	{
		return deviationOpenAccount;
	}

	/**
	 * ���������� ����� ����� ��� �������� ������� �����
	 * @return - "-1" - ��� �����, 1... - �����(id) �����
	 */
	public int getBlockNumber()
	{
		return blockNumber;
	}

	/**
	 * ���������� ��� ������ �������� ���������, ��� �������� ������� �����
	 * @return "-1" - ��� ������, 0...5 - ����� (CreationType#valueOf)
	 */
	public String getChannel()
	{
		return channel;
	}

	/**
	 * @return ���������� ������
	 */
	public String getMailReceivers()
	{
		return mailReceivers;
	}

	/**
	 * @return ���� ������
	 */
	public String getMailTheme()
	{
		return mailTheme;
	}

	/**
	 * @return ����� ������
	 */
	public String getMailText()
	{
		return mailText;
	}

	/**
	 * @return ������������� ������������ ������
	 */
	public boolean isAggregateOn()
	{
		return aggregateOn;
	}

	/**
	 * @return ������������� �������� �������� �������
	 */
	public boolean isSendOn()
	{
		return sendOn;
	}

	/**
	 * @param time �����.
	 * @return ���������� �� ��������� � ����������� ������.
	 */
	public boolean isNeedSendMail(Calendar time)
	{
		return timeForSendMail.get(Calendar.HOUR_OF_DAY) == time.get(Calendar.HOUR_OF_DAY) &&
				timeForSendMail.get(Calendar.MINUTE) == time.get(Calendar.MINUTE);
	}
}
