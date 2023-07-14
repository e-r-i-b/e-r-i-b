package com.rssl.phizic.web.auth.payOrder;

import com.rssl.phizic.config.CSAFrontConfig;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.LogModule;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.utils.store.StoreManager;

import static com.rssl.phizic.einvoicing.EInvoicingConstants.*;

/**
 * @ author: Vagin
 * @ created: 08.02.2013
 * @ $Author
 * @ $Revision
 * ������ ��� ������ � ������� ������� ��� ����� ��� ������ � ������� ������.
 */
public class PayOrderHelper
{
	private static final Log log = PhizICLogFactory.getLog(LogModule.Web);

	public static final String PAY_ORDER_BACK_URL = "payOrderBackURL";

	/**
	 * ���������� ����� ������ � ������� ������
	 * @return ���, ��������-������� ��� ���
	 */
	public static String getPayOrderMode()
	{
		try
		{
			if (StringHelper.isNotEmpty((String) StoreManager.getCurrentStore().restore(WEBSHOP_REQ_ID)))
				return WEBSHOP_REQ_ID;

			if (StringHelper.isNotEmpty((String) StoreManager.getCurrentStore().restore(FNS_PAY_INFO)))
				return FNS_PAY_INFO;

			if (StringHelper.isNotEmpty((String) StoreManager.getCurrentStore().restore(UEC_PAY_INFO)))
				return UEC_PAY_INFO;

			return null;
		}
		catch (Exception e)
		{
			log.error("���� ��� ����������� ������ ������ � ������� ������", e);
			return null;
		}
	}

	/**
	 * @return true, ���� ������� ����� ������������ - ������ ��������� ��������� � ����� ���
	 */
	public static boolean isUECPaymentSession()
	{
		return UEC_PAY_INFO.equals(getPayOrderMode());
	}

	/**
	 * @return true, ���� ������� ����� ������������ - ������ ��������� ��������� � ����� ���
	 */
	public static boolean isWebShopPaymentSession()
	{
		return WEBSHOP_REQ_ID.equals(getPayOrderMode());
	}

	/**
	 * �������� �� ���� ��� ������ � ������� ������.
	 * @return true, ���� ������ ����� ��� ������ � ������� ������
	 */
	public static boolean isPayOrder()
	{
		return getPayOrderBackURL() != null;
	}

	/**
	 *  �������� �� ���� ����������.
	 * @return false, ���� ������ ����� ��� ������ � ������� ������ �� � ���������������� �����
	 */
	public static boolean isCorrectEnter()
	{
		if (getPayOrderBackURL() == null)
			return true;
		if (getPayOrderMode() != null)
			return true;
		return false;
	}	

	/**
	 * ��� ��������, � ������ ���� ����� ���������� � ������� ������.
	 * @return URL
	 */
	public static String getPayOrderBackURL()
	{
		return StringHelper.getNullIfEmpty((String) StoreManager.getCurrentStore().restore(PAY_ORDER_BACK_URL));
	}

	/**
	 * ������� �������� ������ �� ������ � ������� ������
	 * � ������ �������� �������������� � �������� � ���������� �����
	 */
	public static void clearPayOrderSessionData()
	{
		StoreManager.getCurrentStore().remove(UEC_PAY_INFO);
		StoreManager.getCurrentStore().remove(FNS_PAY_INFO);
		StoreManager.getCurrentStore().remove(WEBSHOP_REQ_ID);
		StoreManager.getCurrentStore().remove(PAY_ORDER_BACK_URL);
	}

	/**
	 * ���������� ����� ���
	 * @return ����� ����� ���
	 */
	public static String getUECWebSiteUrl()
	{
		CSAFrontConfig frontConfig = ConfigFactory.getConfig(CSAFrontConfig.class);
		return frontConfig.getUECWebSiteUrl();
	}
}
