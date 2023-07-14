package com.rssl.phizic.shoplistener.generated.registration;

import com.rssl.auth.csa.utils.CSAResponseUtils;
import com.rssl.auth.csa.wsclient.CSABackRequestHelper;
import com.rssl.auth.csa.wsclient.CSABackResponseSerializer;
import com.rssl.auth.csa.wsclient.exceptions.CardByPhoneNotFoundException;
import com.rssl.phizic.gate.csa.UserInfo;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.config.ShopListenerConfig;
import com.rssl.phizic.einvoicing.ShopOrderImpl;
import com.rssl.phizic.einvoicing.ShopProfileImpl;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.einvoicing.*;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.logging.system.LogModule;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.messaging.MessagingSingleton;
import com.rssl.phizic.messaging.mail.SmsTransportService;
import com.rssl.phizic.shoplistener.generated.*;
import com.rssl.phizic.utils.PhoneNumberFormat;
import com.rssl.phizic.utils.StringHelper;
import org.apache.commons.logging.Log;
import org.w3c.dom.Document;

/**
 * регистрация офлайн заказа.
 *
 * @author bogdanov
 * @ created 10.06.2013
 * @ $Author$
 * @ $Revision$
 */

public class Offline extends Full
{
	public static final long MC_PAYMENT_INIT_ERROR_CODE = -5L;
	private static final long PROFILE_NOT_FOUND_ERROR_CODE = -4L;
	private static final String PROFILE_NOT_FOUND_ERROR_DESCRIPTION = "Client not found";
	private static final String MC_PAYMENT_INIT_ERROR_DESCRIPTION = "Error in the formation of the mc-document";
	private static final Log log = PhizICLogFactory.getLog(LogModule.Gate);

	@Override
	protected ShopOrder fillOrder(DocRegRqType request)
	{
		ShopOrderImpl order = (ShopOrderImpl) super.fillOrder(request);
		String phone = request.getPhone();
		order.setPhone(phone);
		order.setMobileCheckout(request.getMobileCheckout());
		try
		{
			Document document = CSABackRequestHelper.sendGetUserInfoByPhoneWithMBRq(PhoneNumberFormat.MOBILE_INTERANTIONAL.translate(phone));
			UserInfo userInfo = CSABackResponseSerializer.getUserInfo(document);
			if(userInfo != null)
			{
				order.setProfile(new ShopProfileImpl(userInfo));
				order.setState(OrderState.RELATED);
				order.setNodeId(CSAResponseUtils.createNodeInfo(document.getDocumentElement()).getId());
			}
		}
		catch (CardByPhoneNotFoundException e)
		{
			log.error(e.getMessage(), e);
		}
		catch (Exception e)
		{
			log.error(e.getMessage(), e);
			return null;
		}

		return order;
	}

	@Override
	protected DocRegRsType createResponse(DocRegRqType request, ShopOrder order, StatusType status)
	{
		DocRegRsType response = super.createResponse(request, order, status);

		if (order == null)
			return response;

		if (order.getProfile() == null)
		{
			if (getFacilitatorProvider() != null && !getFacilitatorProvider().isMbCheckEnabled())
			{
				response.setStatus(new StatusType(DocRegServiceSoapBindingImpl.ERROR_STATE_CODE, DocRegServiceSoapBindingImpl.INTERNAL_ERROR));
			}
			else
				response.setStatus(new StatusType(PROFILE_NOT_FOUND_ERROR_CODE, PROFILE_NOT_FOUND_ERROR_DESCRIPTION));
		}
		else if (request.getMobileCheckout() && getActiveProvider().isAvailableMobileCheckout())
		{
			try
			{
				//инициируем создание документа оплаты заказа в блоке
				GateSingleton.getFactory().service(InvoiceGateBackService.class).createOrderPayment(order);
			}
			catch (Exception e)
			{
				if (order instanceof ShopOrderImpl)
				{
					((ShopOrderImpl) order).setMobileCheckout(false);
					try
					{
						shopOrderService.updateOrder(order);
					}
					catch (GateException ex)
					{
						log.error(ex);
					}
				}
				log.error(e.getMessage(), e);
				response.setStatus(new StatusType(MC_PAYMENT_INIT_ERROR_CODE, MC_PAYMENT_INIT_ERROR_DESCRIPTION));
			}
		}
		else
		{
			try
			{
				SmsTransportService smsTransportService = MessagingSingleton.getMbkSmsTransportService(false);
				ShopListenerConfig config = ConfigFactory.getConfig(ShopListenerConfig.class);
				String text = order.getKind() == OrderKind.AEROFLOT ? config.getNewAirlineOrderSms() : config.getNewInternetOrderSms();
				text = text.replaceAll("\\{0\\}", order.getReceiverName()).replaceAll("\\{1\\}", order.getExternalId());
				smsTransportService.sendSms(order.getPhone(), text, text, 1L);
			}
			catch (Exception e)
			{
				log.error(e.getMessage(), e);
			}
		}

		return response;
	}

	@Override
	protected com.rssl.phizic.gate.einvoicing.TypeOrder getType()
	{
		return com.rssl.phizic.gate.einvoicing.TypeOrder.O;
	}

	@Override
	protected void checkFields(DocRegRqType parameters) throws GateException
	{
		super.checkFields(parameters);

		if (StringHelper.isEmpty(parameters.getPhone()))
			throw new IllegalArgumentException("Не указан номер телефона для оффлайн заказа.");
		if (parameters.getMobileCheckout() == null)
			throw new IllegalArgumentException("Не указан признак MobileCheckout.");
		RegRqDocumentType doc = parameters.getDocument();
		if (doc.getAmount() == null)
			throw new IllegalArgumentException("Не указан признак в теге Document: Amount");
		if (doc.getAmountCur() == null)
			throw new IllegalArgumentException("Не указан признак в теге Document: AmountCur");
		if (doc.getFields() == null ||
				(
					(doc.getFields().getShop() == null && doc.getFields().getAirlineReservation() == null) ||
					(doc.getFields().getAirlineReservation() == null && doc.getFields().getShop() != null && doc.getFields().getShop().length == 0)
				)
			)
			throw new IllegalArgumentException("Не указан один из признаков в теге Document: Fields/Shop или Fields/AirlineReservation.");
	}
}
