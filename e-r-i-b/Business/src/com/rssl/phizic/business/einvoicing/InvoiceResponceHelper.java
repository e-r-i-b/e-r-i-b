package com.rssl.phizic.business.einvoicing;

import com.rssl.phizic.common.types.Currency;
import com.rssl.phizic.common.types.Money;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.currency.CurrencyService;
import com.rssl.phizic.gate.einvoicing.*;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.utils.xml.XMLDatatypeHelper;
import com.rssl.phizic.utils.xml.XmlHelper;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.math.BigDecimal;
import java.util.Calendar;
import javax.xml.transform.TransformerException;

import static com.rssl.phizic.business.einvoicing.Constants.*;

/**
 * @author gladishev
 * @ created 04.03.14
 * @ $Author$
 * @ $Revision$
 */
public class InvoiceResponceHelper
{
	/**
	 * Парсинг xml с информацией о заказе
	 * @param document - xml с описанием заказа
	 * @return - заказ
	 */
	public static ShopOrder fillShopOrder(Document document) throws GateException
	{
		return fillShopOrder(selectSingleNode(document.getDocumentElement(), ORDER_TAG));
	}

	/**
	 * Парсинг xml с информацией о заказе
	 * @param root - xml с описанием заказа
	 * @return - заказ
	 */
	public static ShopOrder fillShopOrder(Element root) throws GateException
	{
		ShopOrderImpl order = new ShopOrderImpl();
		order.setId(Long.parseLong(XmlHelper.getSimpleElementValue(root, ID_TAG)));
		order.setUuid(XmlHelper.getSimpleElementValue(root, UUID_TAG));
		order.setExternalId(XmlHelper.getSimpleElementValue(root, EXTERNAL_ID_TAG));
		order.setDate(parseDate(XmlHelper.getSimpleElementValue(root, DATE_TAG)));
		order.setState(OrderState.valueOf(XmlHelper.getSimpleElementValue(root, STATE_TAG)));
		order.setPhone(XmlHelper.getSimpleElementValue(root, PHONE_TAG));
		order.setReceiverCode(XmlHelper.getSimpleElementValue(root, RECEIVER_CODE_TAG));
		order.setReceiverName(XmlHelper.getSimpleElementValue(root, RECEIVER_NAME_TAG));
		String currencyElement = XmlHelper.getSimpleElementValue(root, CURRENCY_TAG);
		Currency currency = GateSingleton.getFactory().service(CurrencyService.class).findByAlphabeticCode(currencyElement);
		String amountElement = XmlHelper.getSimpleElementValue(root, AMOUNT_TAG);
		if (StringHelper.isNotEmpty(currencyElement) && StringHelper.isNotEmpty(amountElement))
			order.setAmount(new Money(new BigDecimal(amountElement), currency));

		order.setDescription(XmlHelper.getSimpleElementValue(root, DESCRIPTION_TAG));
		order.setReceiverAccount(XmlHelper.getSimpleElementValue(root, RECEIVER_ACCOUNT_TAG));
		order.setBic(XmlHelper.getSimpleElementValue(root, BIC_TAG));
		order.setCorrAccount(XmlHelper.getSimpleElementValue(root, CORR_ACCOUNT_TAG));
		order.setInn(XmlHelper.getSimpleElementValue(root, INN_TAG));
		order.setKpp(XmlHelper.getSimpleElementValue(root, KPP_TAG));
		order.setPrintDescription(XmlHelper.getSimpleElementValue(root, PRINT_DESCRIPTION_TAG));
		order.setBackUrl(XmlHelper.getSimpleElementValue(root, BACK_URL_TAG));
		order.setKind(OrderKind.valueOf(XmlHelper.getSimpleElementValue(root, KIND_TAG)));
		order.setMobileCheckout(Boolean.valueOf(XmlHelper.getSimpleElementValue(root, MOBILE_CHECKOUT_TAG)));
		order.setProfile(fillProfile(root));
		Calendar delayDate = null;
		if (XmlHelper.getSimpleElementValue(root, DELAY_DATE_TAG) != null)
		{
			delayDate = parseDate(XmlHelper.getSimpleElementValue(root, DELAY_DATE_TAG));
		}
		order.setDelayedPayDate(delayDate);
		order.setIsNew(Boolean.valueOf(XmlHelper.getSimpleElementValue(root, IS_NEW_TAG)));
		return order;
	}

	/**
	 * Парсинг xml с информацией о профиле
	 * @param root - xml
	 * @return - профиль
	 */
	public static ShopProfile fillProfile(Element root) throws GateException
	{
		Element element = selectSingleNode(root, PROFILE_TAG);
		if (element == null)
			return null;

		ShopProfileImpl profile = new ShopProfileImpl();
		profile.setFirstName(XmlHelper.getSimpleElementValue(element, FIRST_NAME_TAG));
		profile.setSurName(XmlHelper.getSimpleElementValue(element, SUR_NAME_TAG));
		profile.setPatrName(XmlHelper.getSimpleElementValue(element, PATR_NAME_TAG));
		profile.setPassport(XmlHelper.getSimpleElementValue(element, PASSPORT_TAG));
		profile.setBirthdate(parseDate(XmlHelper.getSimpleElementValue(element, BIRTHDATE_TAG)));
		profile.setTb(XmlHelper.getSimpleElementValue(element, TB_TAG));
		return profile;
	}

	public static FacilitatorProvider fillProvider(Element root)
	{
		FacilitatorProvider provider = new FacilitatorProvider();
		provider.setId(Long.parseLong(XmlHelper.getSimpleElementValue(root, ID_TAG)));
		provider.setName(XmlHelper.getSimpleElementValue(root, NAME_TAG));
		provider.setCode(XmlHelper.getSimpleElementValue(root, FACIL_CODE_TAG));
		provider.setDeleted(Boolean.parseBoolean(XmlHelper.getSimpleElementValue(root, DELETED_TAG)));
		provider.setEinvoiceEnabled(Boolean.parseBoolean(XmlHelper.getSimpleElementValue(root, EINVOICING_ENABLED_TAG)));
		provider.setFacilitatorCode(XmlHelper.getSimpleElementValue(root, FACILITATOR_CODE_TAG));
		provider.setInn(XmlHelper.getSimpleElementValue(root, INN_TAG));
		provider.setMbCheckEnabled(Boolean.parseBoolean(XmlHelper.getSimpleElementValue(root, MB_CHECK_ENABLED_TAG)));
		provider.setMobileCheckoutEnabled(Boolean.parseBoolean(XmlHelper.getSimpleElementValue(root, MOBILE_CHECKOUT_ENABLED_TAG)));
		provider.setUrl(XmlHelper.getSimpleElementValue(root, URL_TAG));

		return provider;
	}

	private static Calendar parseDate(String value)
	{
		return XMLDatatypeHelper.parseDateTime(value);
	}

	private static Element selectSingleNode(Element root, String tag) throws GateException
	{
		try
		{
			return XmlHelper.selectSingleNode(root, tag);
		}
		catch (TransformerException e)
		{
			throw new GateException(e);
		}
	}
}
