package com.rssl.phizic.einvoicing.multiblock.processors;

import com.rssl.phizgate.messaging.internalws.server.protocol.InternalMessageBuilder;
import com.rssl.phizic.common.types.Money;
import com.rssl.phizic.einvoicing.ShopProfileImpl;
import com.rssl.phizic.gate.einvoicing.FacilitatorProvider;
import com.rssl.phizic.gate.einvoicing.ShopOrder;
import com.rssl.phizic.gate.einvoicing.ShopProfile;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.utils.xml.XMLDatatypeHelper;
import com.rssl.phizic.utils.xml.XmlHelper;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import java.math.BigDecimal;
import java.util.Calendar;

import static com.rssl.phizic.einvoicing.multiblock.processors.Constants.*;
/**
 * @author gladishev
 * @ created 17.02.14
 * @ $Author$
 * @ $Revision$
 */
public class ResponseBuilderHelper
{
	/**
	 * Добавить в ответ информацию о заказе
	 * @param order - заказ
	 * @param builder - компановщик ответов
	 */
	public static void addOrder(ShopOrder order, InternalMessageBuilder builder) throws SAXException
	{
		Money amount = order.getAmount();
		builder.openTag(ORDER_TAG)
			.addParameter(ID_TAG, order.getId())
			.addParameter(UUID_TAG, order.getUuid())
			.addParameter(EXTERNAL_ID_TAG, order.getExternalId())
			.addParameter(DATE_TAG, formatDate(order.getDate()))
			.addParameter(STATE_TAG, order.getState().name())
			.addParameterIfNotEmpty(PHONE_TAG, order.getPhone())
			.addParameter(RECEIVER_CODE_TAG, order.getReceiverCode())
			.addParameter(RECEIVER_NAME_TAG, order.getReceiverName())
			.addParameter(DESCRIPTION_TAG, order.getDescription())
			.addParameter(RECEIVER_ACCOUNT_TAG, order.getReceiverAccount())
			.addParameter(BIC_TAG, order.getBic())
			.addParameter(CORR_ACCOUNT_TAG, order.getCorrAccount())
			.addParameter(INN_TAG, order.getInn())
			.addParameterIfNotEmpty(KPP_TAG, order.getKpp())
			.addParameterIfNotEmpty(PRINT_DESCRIPTION_TAG, order.getPrintDescription())
			.addParameterIfNotEmpty(BACK_URL_TAG, order.getBackUrl())
			.addParameter(KIND_TAG, order.getKind())
			.addParameter(MOBILE_CHECKOUT_TAG, order.isMobileCheckout())
			.addParameterIfNotEmpty(DELAY_DATE_TAG, order.getDelayedPayDate() == null ? null : formatDate(order.getDelayedPayDate()))
			.addParameter(IS_NEW_TAG, order.getIsNew());
		if (amount != null)
		{
			builder.addParameter(AMOUNT_TAG, amount.getDecimal().toString());
			builder.addParameter(CURRENCY_TAG, amount.getCurrency().getCode());
		}
		addProfileInfo(order.getProfile(), builder);
		builder.closeTag();
	}

	static void addProfileInfo(ShopProfile profile, InternalMessageBuilder builder) throws SAXException
	{
		if (profile == null)
			return;

		builder.openTag(PROFILE_TAG)
			.addParameter(FIRST_NAME_TAG, profile.getFirstName())
			.addParameter(SUR_NAME_TAG, profile.getSurName())
			.addParameterIfNotEmpty(PATR_NAME_TAG, profile.getPatrName())
			.addParameter(PASSPORT_TAG, profile.getPassport())
			.addParameter(BIRTHDATE_TAG, formatDate(profile.getBirthdate()))
			.addParameter(TB_TAG, profile.getTb())
			.closeTag();
	}

	static void addFacilitatorProvider(FacilitatorProvider provider, InternalMessageBuilder builder) throws SAXException
	{
		builder.openTag(Constants.FACILITATOR_TAG)
				.addParameter(FACIL_CODE_TAG, provider.getCode())
				.addParameter(FACILITATOR_CODE_TAG, provider.getFacilitatorCode())
				.addParameter(INN_TAG, provider.getInn())
				.addParameter(NAME_TAG, provider.getName())
				.addParameter(URL_TAG, provider.getUrl())
				.addParameter(DELETED_TAG, provider.isDeleted())
				.addParameter(ID_TAG, provider.getId())
				.addParameter(EINVOICING_ENABLED_TAG, provider.isEinvoiceEnabled())
				.addParameter(MB_CHECK_ENABLED_TAG, provider.isMbCheckEnabled())
				.addParameter(MOBILE_CHECKOUT_ENABLED_TAG, provider.isMobileCheckoutEnabled())
				.closeTag();
	}

	static ShopProfile parseProfile(Element element) throws Exception
	{
		ShopProfileImpl profile = new ShopProfileImpl();
		profile.setFirstName(getElementValue(element, FIRST_NAME_TAG));
		profile.setSurName(getElementValue(element, SUR_NAME_TAG));
		profile.setPatrName(getElementValue(element, PATR_NAME_TAG));
		profile.setPassport(getElementValue(element, PASSPORT_TAG));
		profile.setBirthdate(parseDate(getElementValue(element, BIRTHDATE_TAG)));
		profile.setTb(getElementValue(element, TB_TAG));
		return profile;
	}

	static String getElementValue(Element element, String tagName)
	{
		return XmlHelper.getSimpleElementValue(element, tagName);
	}

	static Calendar getDateValue(Element element, String tagName)
	{
		String value = XmlHelper.getSimpleElementValue(element, tagName);
		return StringHelper.isNotEmpty(value) ? parseDate(value) : null;
	}

	static BigDecimal getBigDecimalValue(Element element, String tagName)
	{
		String value = XmlHelper.getSimpleElementValue(element, tagName);
		return StringHelper.isNotEmpty(value) ? new BigDecimal(value) : null;
	}

	static Calendar parseDate(String value)
	{
		return XMLDatatypeHelper.parseDateTime(value);
	}

	private static String formatDate(Calendar date)
	{
		return XMLDatatypeHelper.formatDateTimeWithoutTimeZone(date);
	}
}
