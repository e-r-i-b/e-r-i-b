package com.rssl.phizic.config;

/**
 * ������ ��� �������� �������� ������� ���������.
 *
 * @author bogdanov
 * @ created 13.06.2013
 * @ $Author$
 * @ $Revision$
 */

public class ShopListenerConfig extends Config
{
	private static final String NEW_INTERNER_ORDER_SMS = "com.rssl.phizic.payments.new_internet_order";
	private static final String NEW_AIRLINE_ORDER_SMS = "com.rssl.phizic.payments.new_airline_order";
	private static final String INTERNET_OFFER_SMS = "com.rssl.phizic.payments.internetOrder.smsOfferMessage";
	private static final String AIRLINE_OFFER_SMS = "com.rssl.phizic.payments.airline.smsOfferMessage";
	private static final String FACILITATOR_LIMIT = "com.rssl.phizic.shop.facilitatorLimit";
	private static final String NOTIFICATION_DOC_LIMIT = "com.rssl.phizic.shop.notification.documentLimit";
	private static final String SHOP_ESB_URL = "com.rssl.phizic.shop.esb.url";

	private String newInternetOrderSms;
	private String newAirlineOrderSms;
	private String internetOfferSms;
	private String airlineOfferSms;
	private int facilitatorProvidersLimit;
	private int notificationDocumentLimit;
	private String shopEsbUrl;

	public ShopListenerConfig(PropertyReader reader)
	{
		super(reader);
	}

	/**
	 * @return ����� ��� ��� ������ �������� ������.
	 */
	public String getNewInternetOrderSms()
	{
		return newInternetOrderSms;
	}

	/**
	 * @return ����� ��� ��� ������ ����������.
	 */
	public String getNewAirlineOrderSms()
	{
		return newAirlineOrderSms;
	}

	/**
	 * @return ����� ��� ���������� ������� � ����� ��������-������.
	 */
	public String getInternetOfferSms()
	{
		return internetOfferSms;
	}

	/**
	 * @return ����� ��� ���������� ������� � ����� ����� ���������.
	 */
	public String getAirlineOfferSms()
	{
		return airlineOfferSms;
	}

	/**
	 * @return ����� ������������ �������� ���
	 */
	public int getFacilitatorProvidersLimit()
	{
		return facilitatorProvidersLimit;
	}

	/**
	 * @return ����� ���������� ������������ ���������� � ����� ��������� �� ��������� ������� ������/������/�������� ������
	 */
	public int getNotificationDocumentLimit()
	{
		return notificationDocumentLimit;
	}

	/**
	 * ��� ������� ���-�������
	 * ������������ ��� �������� ��������� ����������� ����� ����.
	 * �������� ����� ������ ��� �������������� ������
	 * ��� ����������� � ���������� ���������� "������ ����� ���".
	 * @return
	 */
	public String getShopEsbUrl()
	{
		return shopEsbUrl;
	}

	protected void doRefresh() throws ConfigurationException
	{
		newInternetOrderSms = getProperty(NEW_INTERNER_ORDER_SMS);
		newAirlineOrderSms = getProperty(NEW_AIRLINE_ORDER_SMS);
		internetOfferSms = getProperty(INTERNET_OFFER_SMS);
		airlineOfferSms = getProperty(AIRLINE_OFFER_SMS);
		facilitatorProvidersLimit = getIntProperty(FACILITATOR_LIMIT);
		notificationDocumentLimit = getIntProperty(NOTIFICATION_DOC_LIMIT);
		shopEsbUrl = getProperty(SHOP_ESB_URL);
	}
}
