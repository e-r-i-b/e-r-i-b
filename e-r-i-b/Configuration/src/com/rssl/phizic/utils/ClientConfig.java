package com.rssl.phizic.utils;

import com.rssl.phizic.config.Config;
import com.rssl.phizic.config.ConfigurationException;
import com.rssl.phizic.config.PropertyReader;

/**
 *  онфиг дл€ изменени€ настроек клиента.
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
	private long claimsLimit;               //ќграничение на количество отображаемых в списке за€вок (главна€ страница гостевого входа, за€вка на    у обычного клиента)
	private boolean doLoanCardClaimExistenceRequest; // ‘лажок "можно ли делать запрос в базу на существование за€вок на кредитную карту у клиента"
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
	 * @return количество продуктов на главной при 1-м входе в систему
	 */
	public int getNumOfProducts()
	{
		return numOfProducts;
	}

	/**
	 * @return ќпредел€ем показывать ли вкладку "Ўаблоны и платежи" дл€ указанного продукта/
	 */
	public boolean isShowTemplateForProduct(String productType)
	{
		return SHOW_TEMPLATE_CARD.endsWith(productType) ? showTemplateCard : showTemplateAccount;
	}

	/**
	 * @return отображать или не отображать кнопку "ќперации"
	 */
	public boolean isShowOperationsButton()
	{
		return showOperationsButton;
	}

	/**
	 * @return максимальное количество банеров на главной.
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
	 * @return  оличество новостей в правом меню
	 */
	public int getNewsCount()
	{
		return newsCount;
	}

	/**
	 * @return Ћимит на запрос информации о клиенте в операции оплата физ лицу на карту по номеру телефона
	 */
	public long getInfoPersonPaymentLimit()
	{
		return infoPersonPaymentLimit;
	}

	/**
	 * @return »нформационное сообщение выводимое в клиентском приложении при превышении лимита на запрос информации о клиенте в операции оплата физ лицу на карту по номеру телефона
	 */
	public String getInfoPersonPaymentLimitMessage()
	{
		return infoPersonPaymentLimitMessage;
	}

	/**
	 * @return  оличество сессий, в которые показываем промо по автоплатежам карта-карта
	 */
	public long getP2PPromoShowTimes()
	{
		return p2pPromoShowTimes;
	}

	/**
	 * @return Ќеобходимость проверки клиента по стоп-листу
	 */
	public Boolean getNeedStopListCheck()
	{
		return needStopListCheck;
	}

	/**
	 * @return Ћимит количества клиентских сессий, в контексте которых считаем P2P-автоплатежи нововведением
	 */
	public long getP2pShowNewMarkLimit()
	{
		return p2pShowNewMarkLimit;
	}

	/**
	 * @return Url дл€ метрики пиксель
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
	 * ‘лажок "можно ли делать запрос в базу на существование за€вок на кредитную карту у клиента"
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
