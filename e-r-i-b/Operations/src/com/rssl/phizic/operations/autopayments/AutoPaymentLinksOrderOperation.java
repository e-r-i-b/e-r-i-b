package com.rssl.phizic.operations.autopayments;

import com.rssl.phizic.auth.Login;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.longoffer.autopayment.links.AutoPaymentLinksOrderService;
import com.rssl.phizic.business.resources.external.AutoPaymentLinkOrder;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.operations.ListEntitiesOperation;
import com.rssl.phizic.operations.OperationBase;

import java.util.ArrayList;
import java.util.List;

/**
 * Операция сохранения списка линков автоплатежей и их порядка отображения
 * @ author gorshkov
 * @ created 27.09.13
 * @ $Author$
 * @ $Revision$
 */
public class AutoPaymentLinksOrderOperation extends OperationBase implements ListEntitiesOperation
{
	protected static final AutoPaymentLinksOrderService autoPaymentService = new AutoPaymentLinksOrderService();
	private List<AutoPaymentLinkOrder> autoPayments = new ArrayList<AutoPaymentLinkOrder>();
	private Login login;

	public void initialize() throws BusinessException
	{
		login = PersonContext.getPersonDataProvider().getPersonData().getPerson().getLogin();
		autoPayments = autoPaymentService.findByLogin(login);
	}

	/**
	  * Сохраняем автоплатежи и порядок их отображения
	  * @param sortAutoPayments - порядок отображения
	  * @throws BusinessException
	  */
	public void save(String[] sortAutoPayments) throws BusinessException
	{
		int i = 1;

		for (String linkId : sortAutoPayments)
		{
			AutoPaymentLinkOrder autoPayment = findAutoPayments(linkId);

			if (autoPayment == null)
			{
				autoPayment = new AutoPaymentLinkOrder();
				autoPayment.setLinkId(linkId);
				autoPayment.setLoginId(login.getId());
				autoPayment.setOrderInd(i);
				autoPaymentService.addOrUpdateAutoPaymentOrder(autoPayment);
			}
			else if (autoPayment.getOrderInd() != i)
			{
				autoPayment.setOrderInd(i);
				autoPaymentService.addOrUpdateAutoPaymentOrder(autoPayment);
			}

			i++;
		}
	}

	/**
	 * Поиск автоплатежа в текущем списке
	 * @param linkId - внешний ID автоплатежа
	 * @return автоплатеж
	 */
	private AutoPaymentLinkOrder findAutoPayments(String linkId)
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
