package com.rssl.phizic.web.common.mobile.payments.internetShops;

import com.rssl.phizic.operations.payment.internetShops.InternetOrderDetailInfoOperation;
import com.rssl.phizic.web.common.client.payments.internetShops.AsyncOrderDetailInfoAction;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Детальная информация по заказу в интернет-магазине
 * @author Dorzhinov
 * @ created 13.06.2013
 * @ $Author$
 * @ $Revision$
 */
public class InternetOrderDetailInfoMobileAction extends AsyncOrderDetailInfoAction
{
	protected boolean isAjax()
	{
		return false;
	}

	@Override
	public ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
	{
		try
		{
			InternetOrderDetailInfoMobileForm frm       = (InternetOrderDetailInfoMobileForm) form;
			InternetOrderDetailInfoOperation  operation = createOperation(InternetOrderDetailInfoOperation.class);
			operation.initialize(Long.parseLong(frm.getOrderId()));

			if (operation.isAirline())
			{
				frm.setAirlinesInfo(operation.getAirlinesInfo());
				return mapping.findForward(AIRLINE_FORWARD);
			}
			else
			{
				frm.setShopOrdersInfo(operation.getShopOrdersInfo());
				return mapping.findForward(INTERNET_SHOP_FORWARD);
			}
		}
		catch (Exception e)
		{
			log.error("Ошибка во время выполнения запроса информации по интернет-заказу", e);
			return mapping.findForward(INTERNET_SHOP_FORWARD);
		}
	}
}
