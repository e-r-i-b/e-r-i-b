package com.rssl.phizic.operations.autosubscription;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.departments.DepartmentService;
import com.rssl.phizic.business.resources.external.AutoSubscriptionLink;
import com.rssl.phizic.business.resources.external.ExternalResourceService;
import com.rssl.phizic.common.types.exceptions.LogicException;
import com.rssl.phizic.common.types.exceptions.SystemException;
import com.rssl.phizic.common.types.transmiters.GroupResult;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.autopayments.AutoSubscriptionService;
import com.rssl.phizic.gate.payments.longoffer.AutoSubscriptionDetailInfo;
import com.rssl.phizic.operations.ViewEntityOperation;
import com.rssl.phizic.utils.GroupResultHelper;

/**
 * @author bogdanov
 * @ created 13.02.2012
 * @ $Author$
 * @ $Revision$
 *
 * детальная информация по исполненному платежму по подписке на автоплатеж.
 */

public class GetAutoSubscriptionPaymentInfoOperation extends AutoSubscriptionOperationBase implements ViewEntityOperation
{
	protected static final ExternalResourceService externalResourceService = new ExternalResourceService();
	protected static final DepartmentService departmentService = new DepartmentService();

	private AutoSubscriptionDetailInfo payment;
	private AutoSubscriptionLink subscriptionLink;

	/**
	 * инициализирует операцию.
	 * @param paymentId идентификатор запрашиваемого платежа по автоплатежу
	 * @param subscriptionId идентификатор автоплатежа
	 */
	public final void initialize(Long subscriptionId, Long paymentId) throws BusinessLogicException, BusinessException
	{
		initialize();

		try
		{
			AutoSubscriptionService service = GateSingleton.getFactory().service(AutoSubscriptionService.class);
			AutoSubscriptionLink link = getPersonData().getAutoSubscriptionLink(subscriptionId);

			GroupResult<Long, AutoSubscriptionDetailInfo> result = service.getSubscriptionPayments(link.getValue(), paymentId);
			AutoSubscriptionDetailInfo detailInfo = GroupResultHelper.getOneResult(result);
			//проверка принадлежности подписки на автоплатеж клиенту
			if (detailInfo == null)
			{
				throw new BusinessLogicException("Информация по платежу временно недоступна.");
			}

			subscriptionLink = link;
			payment = detailInfo;
		}
		catch (LogicException e)
		{
			throw new BusinessLogicException(e);
		}
		catch (SystemException e)
		{
			throw new BusinessException(e);
		}
	}

	public AutoSubscriptionDetailInfo getEntity() throws BusinessException, BusinessLogicException
	{
		return payment;
	}

	/**
	 * @return линк подписки
	 * @throws BusinessException
	 * @throws BusinessLogicException
	 */
	public AutoSubscriptionLink getSubscriptionLink() throws BusinessException, BusinessLogicException
	{
		return subscriptionLink;
	}
}
