package com.rssl.phizic.utils;

import com.rssl.phizic.config.Config;
import com.rssl.phizic.config.ConfigurationException;
import com.rssl.phizic.config.PropertyReader;

/**
 * ������ ��� ��������� �������� �������.
 *
 * @author bogdanov
 * @ created 27.01.14
 * @ $Author$
 * @ $Revision$
 */

public class ClientConfig extends Config
{
	private static final String NUM_OF_PRODUCTS_ON_MAIN     = "com.rssl.iccs.main.numOfProducts";
	private static final String SHOW_TEMPLATE_CARD = "com.rssl.iccs.showTemplate.card";
	private static final String SHOW_TEMPLATE_ACCOUNT = "com.rssl.iccs.showTemplate.account";
	private static final String SHOW_OPERATIONS_BUTTON = "com.rssl.iccs.show.operations.button";
	private static final String MAX_BANNER_COUNT_KEY = "com.rssl.iccs.main.numOfBanners";
	public static final String SMS_BANKING_OPERATIONS_DEFAULT_COUNT  = "com.rssl.iccs.smsBanking.operations.defaultCount";
	public static final String SMS_BANKING_OPERATIONS_MAX_COUNT   = "com.rssl.iccs.smsBanking.operations.maxCount";
	public static final String NEWS_COUNT                        = "com.rssl.business.simple.newsCount";
	public static final String INFO_PERSON_PAYMENT_LIMIT_KEY = "com.rssl.business.simple.infoPersonPaymentLimit";
	public static final String INFO_PERSON_PAYMENT_LIMIT_MESSAGE = "com.rssl.business.simple.infoPersonPaymentLimit.message";
	public static final String P2P_PROMO_SHOW_TIMES = "com.rssl.iccs.main.p2pPromoShowTimes";
	public static final String P2P_SHOW_NEW_MARK_LIMIT = "com.rssl.iccs.main.p2pShowNewMarkLimit";
	public static final String NEED_STOPLIST_CHECK = "com.rssl.iccs.need.stop.check";
	public static final String URL_PIXEL_METRIC = "com.rssl.iccs.metric.pixel";
	public static final String CLAIMS_LIMIT = "com.rssl.iccs.claims.limit";
	public static final String LOAN_CARD_CLAIM_EXISTENCE_REQUEST = "com.rssl.iccs.loanCardClaim.doExistenceRequest";
	public static final String GUEST_CLAIMS_PERIOD = "com.rssl.guest.entry.claims.show.period";
	public static final String USE_JMS_FOR_LOAN_CONFIG = "com.rssl.phizic.config.use.jms.for.loan.requests";

	private int numOfProducts;
	private boolean showTemplateCard;
	private boolean showTemplateAccount;
	private boolean showOperationsButton;
	private int maxBannerCount;
	private long smsBankingOperationsDefaultCount;
	private long smsBankingOperationsMaxCount;
	private int newsCount;
	private long infoPersonPaymentLimit;
	private String infoPersonPaymentLimitMessage;
	private long p2pPromoShowTimes;
	private long p2pShowNewMarkLimit;
	private Boolean needStopListCheck;
	private String urlPixelMetric;
	private long claimsLimit;               //����������� �� ���������� ������������ � ������ ������ (������� �������� ��������� �����, ������ �� �� � �������� �������)
	private boolean doLoanCardClaimExistenceRequest; // ������ "����� �� ������ ������ � ���� �� ������������� ������ �� ��������� ����� � �������"
	private int guestClaimsPeriod;
    private boolean jmsForLoanAvailable;

	public ClientConfig(PropertyReader reader)
	{
		super(reader);
	}

	@Override
	protected void doRefresh() throws ConfigurationException
	{
		numOfProducts  = getIntProperty(NUM_OF_PRODUCTS_ON_MAIN);
		showTemplateCard = getBoolProperty(SHOW_TEMPLATE_CARD);
		showTemplateAccount = getBoolProperty(SHOW_TEMPLATE_ACCOUNT);
		showOperationsButton = getBoolProperty(SHOW_OPERATIONS_BUTTON);
		maxBannerCount = getIntProperty(MAX_BANNER_COUNT_KEY);
		smsBankingOperationsDefaultCount= getLongProperty(SMS_BANKING_OPERATIONS_DEFAULT_COUNT);
		smsBankingOperationsMaxCount = getLongProperty(SMS_BANKING_OPERATIONS_MAX_COUNT);
		newsCount = getIntProperty(NEWS_COUNT);
		infoPersonPaymentLimit = getLongProperty(INFO_PERSON_PAYMENT_LIMIT_KEY);
		infoPersonPaymentLimitMessage = getProperty(INFO_PERSON_PAYMENT_LIMIT_MESSAGE);
		p2pPromoShowTimes = getLongProperty(P2P_PROMO_SHOW_TIMES);
		p2pShowNewMarkLimit = getLongProperty(P2P_SHOW_NEW_MARK_LIMIT);
		needStopListCheck = getBoolProperty(NEED_STOPLIST_CHECK);
		urlPixelMetric = getProperty(URL_PIXEL_METRIC);
		claimsLimit = getLongProperty(CLAIMS_LIMIT);
		doLoanCardClaimExistenceRequest = getBoolProperty(LOAN_CARD_CLAIM_EXISTENCE_REQUEST);
		guestClaimsPeriod = getIntProperty(GUEST_CLAIMS_PERIOD);
        jmsForLoanAvailable = getBoolProperty(USE_JMS_FOR_LOAN_CONFIG);
	}

	/**
	 * @return ���������� ��������� �� ������� ��� 1-� ����� � �������
	 */
	public int getNumOfProducts()
	{
		return numOfProducts;
	}

	/**
	 * @return ���������� ���������� �� ������� "������� � �������" ��� ���������� ��������/
	 */
	public boolean isShowTemplateForProduct(String productType)
	{
		return SHOW_TEMPLATE_CARD.endsWith(productType) ? showTemplateCard : showTemplateAccount;
	}

	/**
	 * @return ���������� ��� �� ���������� ������ "��������"
	 */
	public boolean isShowOperationsButton()
	{
		return showOperationsButton;
	}

	/**
	 * @return ������������ ���������� ������� �� �������.
	 */
	public int getMaxBannerCount()
	{
		return maxBannerCount;
	}

	public long getSmsBankingOperationsDefaultCount()
	{
		return smsBankingOperationsDefaultCount;
	}

	public long getSmsBankingOperationsMaxCount()
	{
		return smsBankingOperationsMaxCount;
	}

	/**
	 * @return ���������� �������� � ������ ����
	 */
	public int getNewsCount()
	{
		return newsCount;
	}

	/**
	 * @return ����� �� ������ ���������� � ������� � �������� ������ ��� ���� �� ����� �� ������ ��������
	 */
	public long getInfoPersonPaymentLimit()
	{
		return infoPersonPaymentLimit;
	}

	/**
	 * @return �������������� ��������� ��������� � ���������� ���������� ��� ���������� ������ �� ������ ���������� � ������� � �������� ������ ��� ���� �� ����� �� ������ ��������
	 */
	public String getInfoPersonPaymentLimitMessage()
	{
		return infoPersonPaymentLimitMessage;
	}

	/**
	 * @return ���������� ������, � ������� ���������� ����� �� ������������ �����-�����
	 */
	public long getP2PPromoShowTimes()
	{
		return p2pPromoShowTimes;
	}

	/**
	 * @return ������������� �������� ������� �� ����-�����
	 */
	public Boolean getNeedStopListCheck()
	{
		return needStopListCheck;
	}

	/**
	 * @return ����� ���������� ���������� ������, � ��������� ������� ������� P2P-����������� �������������
	 */
	public long getP2pShowNewMarkLimit()
	{
		return p2pShowNewMarkLimit;
	}

	/**
	 * @return Url ��� ������� �������
	 */
	public String getUrlPixelMetric()
	{
		return urlPixelMetric;
	}

	public long getClaimsLimit()
	{
		return claimsLimit;
	}

	public void setClaimsLimit(long guestClaimsLimit)
	{
		this.claimsLimit = guestClaimsLimit;
	}

	/**
	 * ������ "����� �� ������ ������ � ���� �� ������������� ������ �� ��������� ����� � �������"
	 * @return
	 */
	public boolean isDoLoanCardClaimExistenceRequest()
	{
		return doLoanCardClaimExistenceRequest;
	}

	public int getGuestClaimsPeriod()
	{
		return guestClaimsPeriod;
	}

	public void setGuestClaimsPeriod(int guestClaimsPeriod)
	{
		this.guestClaimsPeriod = guestClaimsPeriod;
	}

    public boolean isJmsForLoanEnabled()
    {
        return jmsForLoanAvailable;
	}
}
