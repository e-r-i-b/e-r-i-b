package com.rssl.phizic.config.basket;

import com.rssl.phizic.config.Config;
import com.rssl.phizic.config.ConfigurationException;
import com.rssl.phizic.config.PropertyReader;
import org.apache.commons.lang.StringUtils;

import java.util.Arrays;
import java.util.List;

/**
 * @author tisov
 * @ created 29.05.14
 * @ $Author$
 * @ $Revision$
 * ������ ��� ����� "������ � ������"
 */

public class InvoiceConfig extends Config
{

	private static final Character SEPARATOR = ',';
	public static final String DAYS_INVOICE_LIVE_KEY = "com.rssl.iccs.payments.viewTime";
	public static final String NO_INVOICES_TEXT_KEY = "com.rssl.iccs.payments.banner.noInvoicesText";
	public static final String ONLY_AUTO_INVOICES_TEXT_KEY = "com.rssl.iccs.payments.banner.autoInvoicesText";
	public static final String FIRST_INVOICES_LIMIT_KEY =  "com.rssl.iccs.payments.invoicesLimitBefore";
	public static final String DELAYED_INVOICES_INITIAL_LIMIT = "com.rssl.iccs.payments.invoicesLimitBefore.delayed";
	public static final String INVOICES_LIMIT_KEY       =  "com.rssl.iccs.payments.invoicesLimit";
	public static final String DAYS_CREATE_INVOICE_REQUEST_LIVE_KEY = "com.rssl.iccs.payments.createInvoiceRequestsViewTime";
	public static final String EXCEPTED_PROVIDERS_LIST_KEY = "com.rssl.iccs.payments.exceptedProviders";
	public static final String GENERATED_SUBSCRIPTION_LIFE_DAYS = "com.rssl.iccs.payments.generated.subscription.life.days";
	public static final String INVOICES_TIMETOLIFE = "com.rssl.iccs.payments.invoicesTimeToLive";

	private int daysInvoiceLive;               //������ "�����"(��� �����������) �������� � ����
	private int daysCreateInvoiceRequestLive;             //������ "�����" ������ �� �������� �������� �� �������
	private String noInvoicesText;             //����� ��� �������, � ������, ���� � ������������ �� �� ������ �������
	private String onlyAutoInvoicesText;       //����� ��� �������, � ������, ���� � ������������ ���� ������ ������������� ��������� �������
	private int firstInvoicesLimit;            //�������������� ����������� ���������� ������������ ��������(�� ������� ������ "�������� ��������� �����"
	private int delayedInvoicesInitialLimit;   //����������� �� ���������� ������������� ������������ ���������� ��������
	private int invoiceLimit;                  //������������ ����������� ���������� ������������ �������� (����� ������� ������)
	private String exceptedProvidersList;      //��������� ������������� �����������, ����������� �� ����������� � ������� ��������
	private int generatedSubsLifeDays;
	private int invoicesTimeToLive;

	public InvoiceConfig(PropertyReader reader)
	{
		super(reader);
	}

	@Override protected void doRefresh() throws ConfigurationException
	{
		daysInvoiceLive = getIntProperty(DAYS_INVOICE_LIVE_KEY);
		noInvoicesText = getProperty(NO_INVOICES_TEXT_KEY);
		onlyAutoInvoicesText = getProperty(ONLY_AUTO_INVOICES_TEXT_KEY);
		firstInvoicesLimit = getIntProperty(FIRST_INVOICES_LIMIT_KEY);
		invoiceLimit = getIntProperty(INVOICES_LIMIT_KEY);
		daysCreateInvoiceRequestLive = getIntProperty(DAYS_CREATE_INVOICE_REQUEST_LIVE_KEY);
		exceptedProvidersList = getProperty(EXCEPTED_PROVIDERS_LIST_KEY);
		generatedSubsLifeDays = getIntProperty(GENERATED_SUBSCRIPTION_LIFE_DAYS);
		invoicesTimeToLive = getIntProperty(INVOICES_TIMETOLIFE);
		delayedInvoicesInitialLimit = getIntProperty(DELAYED_INVOICES_INITIAL_LIMIT);
	}

	public int getDaysInvoiceLive()
	{
		return daysInvoiceLive;
	}

	public int getGeneratedSubsLifeDays()
	{
		return generatedSubsLifeDays;
	}

	public String getNoInvoicesText()
	{
		return noInvoicesText;
	}

	public String getOnlyAutoInvoicesText()
	{
		return onlyAutoInvoicesText;
	}

	public int getFirstInvoicesLimit()
	{
		return firstInvoicesLimit;
	}

	public int getInvoiceLimit()
	{
		return invoiceLimit;
	}

	public int getDaysCreateInvoiceRequestLive()
	{
		return daysCreateInvoiceRequestLive;
	}

	public List<String> getExceptedProvidersList()
	{
		return Arrays.asList(StringUtils.split(exceptedProvidersList, SEPARATOR));
	}

	public int getInvoicesTimeToLive()
	{
		return invoicesTimeToLive;
	}

	/**
	 * �������� (�� ������� "�������� ���������") ����� ����������� ������ ���������� ��������
	 * @return
	 */
	public int getDelayedInvoicesInitialLimit()
	{
		return delayedInvoicesInitialLimit;
	}
}
