package com.rssl.phizic.web.persons.basket;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.StaticPersonData;
import com.rssl.phizic.operations.ViewEntityOperation;
import com.rssl.phizic.operations.basket.BindSubscriptionToEntityOperation;
import com.rssl.phizic.operations.basket.ViewPaymentsBasketOperation;
import com.rssl.phizic.web.common.EditFormBase;
import com.rssl.phizic.web.common.ViewActionBase;
import com.rssl.phizic.web.persons.basket.ViewPaymentsBasketForm;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
/**
 * @author osminin
 * @ created 09.04.14
 * @ $Author$
 * @ $Revision$
 *
 * Экшен просмотра страницы "корзина платежей"
 */
public class ViewPaymentsBasketAction extends ViewActionBase
{

	@Override
	protected Map<String, String> getKeyMethodMap()
	{
		Map<String, String> map = super.getKeyMethodMap();
		map.put("move", "moveSubscription");
		return map;
	}
	@Override
	protected ViewEntityOperation createViewEntityOperation(EditFormBase frm) throws BusinessException, BusinessLogicException
	{
		ViewPaymentsBasketOperation operation = createOperation(ViewPaymentsBasketOperation.class);
		operation.initialize(((ViewPaymentsBasketForm)frm).getPerson());
		return operation;
	}

	@Override
	protected void updateFormData(ViewEntityOperation operation, EditFormBase frm) throws BusinessException, BusinessLogicException
	{
		ViewPaymentsBasketOperation oper = (ViewPaymentsBasketOperation) operation;
		ViewPaymentsBasketForm form = (ViewPaymentsBasketForm) frm;
		form.setAccountingEntities(oper.getAccountingEntities());
		form.setServiceCategories(oper.getServiceCategories());
		form.setActiveSubscriptions(oper.getActiveSubscriptions());
		form.setStoppedSubscriptions(oper.getStoppedSubscriptions());
		form.setRecommendedSubscriptions(oper.getRecommendedSubscriptions());
		form.setAutoSubscriptions(oper.getAutoSubscriptions());
		form.setImageIds(oper.getImageIds());
		form.setRequisites(oper.getRequisites());
		form.setAllSubscriptions(oper.getAllSubscriptions());
		form.setDetailInfo(oper.getDetailInfo());
		form.setActivePerson(oper.getPerson());
	}

	/**
	 * Выполняем этот экшен, когда пришёл асинхронный запрос на привязку автоподписку к новому объекту учёта (вследствие манипуляций пользователя визуальными объектами
	 * на странице). Посылаем запрос в Бд на апдейт, клиенту ничего не возвращаем
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward moveSubscription(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ViewPaymentsBasketForm frm = (ViewPaymentsBasketForm)form;
		BindSubscriptionToEntityOperation operation = createOperation(BindSubscriptionToEntityOperation.class);

		try
		{
			operation.move(frm.getSubscriptionId(), frm.getEntityId() > 0L ? frm.getEntityId() : null, frm.getType());
		}
		catch (BusinessException e)
		{
			log.error(e.getMessage(), e);
		}

		return null;
	}
}
