package com.rssl.phizic.web.common.client.payments.internetShops;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.LogModule;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.operations.payment.internetShops.InternetOrderDetailInfoOperation;
import com.rssl.phizic.web.actions.OperationalActionBase;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * экшн для формирования параметров интернет-заказа.
 *
 * @author bogdanov
 * @ created 06.06.2013
 * @ $Author$
 * @ $Revision$
 */

public class AsyncOrderDetailInfoAction extends OperationalActionBase
{
	protected static final String INTERNET_SHOP_FORWARD = "InternetShops";
	protected static final String AIRLINE_FORWARD       = "Airline";

	protected static final Log log = PhizICLogFactory.getLog(LogModule.Core);

	public ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
	{
		try
		{
			AsyncOrderDetailInfoForm frm = (AsyncOrderDetailInfoForm) form;
			InternetOrderDetailInfoOperation operation = createOperation(InternetOrderDetailInfoOperation.class);
			if (frm.getOrderId() == null)
				throw new BusinessException("не указан идентификатор");

			operation.initialize(frm.getOrderId());

			if (operation.isAirline())
			{
				frm.setOrderInfo(operation.getAirlineInfoFields());
				return mapping.findForward(AIRLINE_FORWARD);
			}
			else
			{
				frm.setOrderInfo(operation.getOrderDetailInfoFields());
				return mapping.findForward(INTERNET_SHOP_FORWARD);
			}
		}
		catch (Exception e)
		{
			log.error("Ошибка во время выполнения запроса информации по интернет-заказу", e);			
			return mapping.findForward(INTERNET_SHOP_FORWARD);
		}
	}

	@Override
	protected boolean isAjax()
	{
		return true;
	}
}
