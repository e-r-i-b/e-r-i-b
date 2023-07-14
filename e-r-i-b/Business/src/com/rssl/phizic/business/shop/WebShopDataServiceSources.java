package com.rssl.phizic.business.shop;

import com.rssl.common.forms.PaymentFieldKeys;
import com.rssl.common.forms.sources.MapValuesSource;
import com.rssl.phizgate.common.providers.ProviderPropertiesService;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.dictionaries.providers.InternetShopsServiceProvider;
import com.rssl.phizic.business.documents.payments.JurPayment;
import com.rssl.phizic.business.documents.payments.RurPayment;
import com.rssl.phizic.gate.dictionaries.ResidentBank;
import com.rssl.phizic.gate.einvoicing.OrderKind;
import com.rssl.phizic.gate.einvoicing.ShopOrder;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.utils.xml.XmlHelper;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.util.*;

import static com.rssl.common.forms.PaymentFieldKeys.AIRLINE_RESERVATION;
import static com.rssl.common.forms.PaymentFieldKeys.ORDER_FIELDS;
import static com.rssl.common.forms.PaymentFieldKeys.ORDER_FIELDS_SIZE;

/**
 * @author Mescheryakova
 * @ created 23.12.2010
 * @ $Author$
 * @ $Revision$
 */

/**
 * Получает пришедшие по заказу данные из базы и заполняет ими поля для формы-платежа
 */

public class WebShopDataServiceSources extends ExternalPaymentDataServiceSources
{
	private static final String BACK_URL = "backUrl";
	private static final String BACK_URL_ACTION= "backUrlAction";

	private static final String AIRLINE_RESERV_ID = "airlineReservId";
	private static final String AIRLINE_RESERV_EXPIRATION = "airlineReservExpiration";

	private static final String PRINT_DESC = "printDesc";
	private static final String DEFAULT_NAME = "Интернет магазин";
	protected static final String RECEIVER_FACILITATOR = "receiverFacilitator";
	private static final ProviderPropertiesService providerPropertiesService = new ProviderPropertiesService();

	public static final int MAX_FIELD_SIZE = 4000;

	public WebShopDataServiceSources(String orderId) throws BusinessException
	{
		Map<String, String> map = new HashMap<String, String>();
		ShopOrder orderInfo  = ShopHelper.get().getShopOrder(orderId);

		// узнаем все о поставщике
		InternetShopsServiceProvider provider = ShopHelper.get().getRecipientBySystemName(orderInfo.getReceiverCode());
		if (provider == null)
			return;

		map = getSourceMap(orderInfo);
		boolean afterAction = provider.isAfterAction();
		if  (afterAction)
		{
			map.put(BACK_URL_ACTION,Boolean.valueOf(afterAction).toString());

			String backUrl = orderInfo.getBackUrl();

			if (!StringHelper.isEmpty(backUrl))
				map.put(BACK_URL,(backUrl));
			else
				map.put(BACK_URL,(provider.getBackUrl()));
		}


		// получатель
		map.put(RECIPIENT, provider.getId().toString());
		String receiverName = provider.getName();
		if (provider.isFacilitator())
		{
			receiverName = orderInfo.getReceiverName();
			//для частичного режима,если название поставщика пустое заполняем дефолтным значением
			if (StringHelper.isEmpty(receiverName))
				receiverName = DEFAULT_NAME;
			map.put(RECEIVER_FACILITATOR,  provider.getName());
		}

		map.put(PaymentFieldKeys.RECEIVER_NAME, receiverName);
		map.put(RECEIVER_ID,  provider.getSynchKey().toString());
		map.put(CODE_SERVICE, provider.getCodeService());
		map.put(NAME_SERVICE, provider.getServiceName());
		map.put(RECEIVER_PHONE_NUMBER, provider.getPhoneNumber());
		map.put(RECEIVER_NAME_ON_BILL, provider.getNameOnBill());

		map.put(RECEIVER_NAME_ORDER, receiverName);
		map.put(INN, orderInfo.getInn());
		map.put(KPP,  orderInfo.getKpp());

		map.put(PaymentFieldKeys.ORDER_ID_KEY, orderId);
		map.put(JurPayment.BILLING_CODE_ATTRIBUTE_NAME, provider.getBilling().getCode());
		map.put(RurPayment.RECEIVER_INN_ATTRIBUTE_NAME, provider.getINN());

		String corrAccount = orderInfo.getCorrAccount();
		ResidentBank bank =  bankService.findByBIC(orderInfo.getBic());;
		if (StringHelper.isEmpty(corrAccount) && bank != null)
			corrAccount = bank.getAccount();

		map.put(RECEIVER_ACCOUNT, orderInfo.getReceiverAccount());
		map.put(RECEIVER_BANK_CODE, orderInfo.getBic());
		map.put(RECEIVER_COR_ACCOUNT, corrAccount);

		if (bank != null)
			map.put(RECEIVER_BANK_NAME, bank.getName());

		try
		{
			//если оплачиваем фасилитатора через КСШ  подменяем счет, коррсчет, БИК и название банка
			if (provider.isFacilitator() && providerPropertiesService.isUseESBProvider(provider.getId()))
			{
				map.put(RECEIVER_ACCOUNT, provider.getAccount());
				bank =  bankService.findByBIC(provider.getBIC());
				if (bank != null)
				{
					map.put(RECEIVER_COR_ACCOUNT, bank.getAccount());
					map.put(RECEIVER_BANK_CODE, provider.getBIC());
					map.put(RECEIVER_BANK_NAME, bank.getName());
				}
			}
		}
		catch (GateException e)
		{
			log.error("Ошибка при получении свойств фасилитатора", e);
		}
		this.source = new MapValuesSource(map);
	}

	/**
	 * заполняем поля формы для оплаты заказа из интеренет-магазина
	 * @param order -  заполненный из базы объект
	 * @return - map вида: название_поля => значение из базы
	 */
	private Map<String, String> getSourceMap(ShopOrder order) throws BusinessException
	{
		Map<String, String> map = new HashMap<String, String>();
		map.put(AMOUNT, getDecimalFromMoney(order.getAmount()));
		map.put(CURRENCY, getCurrencyFromMoney(order.getAmount()));
		map.put(REC_IDENTIFIER, order.getExternalId());

		try
		{
			if (order.getKind() == OrderKind.AEROFLOT)
			{
				String airlineReservation = ShopHelper.get().getOrderDetailInfo(order.getUuid());
				Document airlineReservationInfo = XmlHelper.parse(airlineReservation);
				Element reserv = airlineReservationInfo.getDocumentElement();
				map.put(AIRLINE_RESERV_ID, XmlHelper.selectNodeList(reserv, "./ReservId").item(0).getTextContent());
				if (XmlHelper.selectNodeList(reserv, "./ReservExpiration").getLength() > 0)
					map.put(AIRLINE_RESERV_EXPIRATION, XmlHelper.selectNodeList(reserv, "./ReservExpiration").item(0).getTextContent());
				map.put(AIRLINE_RESERVATION, airlineReservation);
			}
			else if (order.getKind() == OrderKind.INTERNET_SHOP) {
				String shopFields = ShopHelper.get().getOrderDetailInfo(order.getUuid());
				if (shopFields.length() > MAX_FIELD_SIZE)
				{
					int i = 0;
					while (shopFields.length() > 0) {
						map.put(ORDER_FIELDS + "_" + i, shopFields.substring(0, Math.min(MAX_FIELD_SIZE, shopFields.length())));
						shopFields = shopFields.substring(Math.min(MAX_FIELD_SIZE, shopFields.length()));
						i++;
					}
					map.put(ORDER_FIELDS_SIZE, Integer.toString(i));
				}
				else
				{
					map.put(ORDER_FIELDS, shopFields);
				}
			}
		}
		catch (Exception e)
		{
			throw new BusinessException(e);
		}

		//информация о заказе для печати
		map.put(PRINT_DESC, order.getPrintDescription());
		return map;
	}
}
