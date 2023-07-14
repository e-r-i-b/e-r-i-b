package com.rssl.phizic.business.favouritelinks;

import org.apache.commons.collections.MapUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * типы ссылок соответствующие разым урлам
 * @author basharin
 * @ created 05.06.14
 * @ $Author$
 * @ $Revision$
 */

public enum FavouriteLinkFormat
{
	ACCOUNT_LINK("/private/accounts/operations.do", Arrays.asList("id")),
	CARD_LINK("/private/cards/info.do", Arrays.asList("id")),
	LOAN_LINK("/private/loans/detail.do", Arrays.asList("id")),
	SERVICES_PAYMENT_LINK("/private/payments/servicesPayments/edit.do", Arrays.asList("recipient", "template")),
	CREATE_TEMPLATE_LINK("/private/template/services-payments/edit.do", Arrays.asList("recipient")),
	ARCHIVE_MAIL_LINK("/private/mail/archive.do", new ArrayList()),
	INCOMING_MAIL_LINK("/private/mail/list.do", new ArrayList()),
	SENT_MAIL_LINK("/private/mail/sentList.do", new ArrayList()),
	DEPO_LINK("/private/depo/documents.do", Arrays.asList("id")),
	TARGET_LINK("/private/targets/detailInfoNoAccount.do", Arrays.asList("id")),
	PAYMENT_LINK("/private/payments/payment.do", Arrays.asList("form","receiverSubType","id", "template")),
	JUR_PAYMENT_LINK("/private/payments/jurPayment/edit.do", Arrays.asList("template")),
	IMA_LINK("/private/ima/info.do", Arrays.asList("id")),
	INSURANCE_LINK("/private/insurance/view.do", Arrays.asList("id")),
	NPF_LINK("/private/npf/view.do", Arrays.asList("id")),
	PFR_LINK("/private/pfr/list.do", new ArrayList());

	private static final String TEMPLATE_NAME = "template";
	private String url;
	private List<String> listKeys;

	/**
	 * Форматирование урла из типа ссылки и параметров
	 * @param params - параметры
	 * @return урл соответствующий ссылке
	 */
	public String toUrl(Map<String, String> params)
	{
		//если это шаблон то остальные параметры это случайный мусор
		if (params.containsKey(TEMPLATE_NAME))
		{
			String value = params.get(TEMPLATE_NAME);
			params.clear();
			params.put(TEMPLATE_NAME, value);
		}

		StringBuilder param = new StringBuilder();
		if (MapUtils.isNotEmpty(params))
			for (String key : listKeys)
				if (params.containsKey(key))
				{
					param.append("&");
					param.append(key);
					param.append("=");
					param.append(params.get(key));
				}

		if (param.length() != 0)
			return url + "?" + param.toString().substring(1);
		return url;
	}

	private FavouriteLinkFormat(String url, List<String> listKeys)
	{
		this.url = url;
		this.listKeys = listKeys;
	}
}
