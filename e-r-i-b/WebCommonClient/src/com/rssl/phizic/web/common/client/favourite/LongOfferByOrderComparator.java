package com.rssl.phizic.web.common.client.favourite;

import com.rssl.phizic.auth.CommonLogin;
import com.rssl.phizic.auth.Login;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.longoffer.autopayment.links.AutoPaymentLinksOrderService;
import com.rssl.phizic.business.resources.external.*;
import com.rssl.phizic.context.PersonContext;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Компаратор для LongOffer по порядку отображения
 * @ author gorshkov
 * @ created 27.09.13
 * @ $Author$
 * @ $Revision$
 */
public class LongOfferByOrderComparator extends LongOfferByDateComparator
{
	private static final AutoPaymentLinksOrderService autoPaymentOrderService = new AutoPaymentLinksOrderService();
	private static final String DELIMITER = "_";
	private List<AutoPaymentLinkOrder> autoPayments = new ArrayList<AutoPaymentLinkOrder>();
	private Login login;


	public LongOfferByOrderComparator() throws BusinessException
	{
		login = PersonContext.getPersonDataProvider().getPersonData().getPerson().getLogin();
		autoPayments = autoPaymentOrderService.findByLogin(login);
	}

	/**
	 * Сравниваем 2 автоплатежа по порядку отображения, если порядок не задан сравниваем по дате добавления
	 */
    public int compare(ExternalResourceLinkBase o1, ExternalResourceLinkBase o2)
	{
			String linkId1 = o1.getExternalId() + DELIMITER + o1.getCodePrefix();
			String linkId2 = o2.getExternalId() + DELIMITER + o2.getCodePrefix();

			AutoPaymentLinkOrder autoPayment1 = findAutoPayment(linkId1);
			AutoPaymentLinkOrder autoPayment2 = findAutoPayment(linkId2);

			if (autoPayment1 == null || autoPayment2 == null)
				return super.compare(o1, o2);
			else
				return autoPayment1.getOrderInd() - autoPayment2.getOrderInd();
	}

	/**
	 * Поиск автоплатежа в текущем списке
	 * @param linkId - внешний ID автоплатежа
	 * @return автоплатеж
	 */
	private AutoPaymentLinkOrder findAutoPayment(String linkId)
	{
		for (AutoPaymentLinkOrder autoPayment : autoPayments)
		{
			if (autoPayment.getLinkId().equals(linkId))
			{
				return autoPayment;
			}
		}
		return null;
	}
}
