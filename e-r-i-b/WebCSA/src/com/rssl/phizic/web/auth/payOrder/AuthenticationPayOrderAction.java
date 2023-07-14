package com.rssl.phizic.web.auth.payOrder;

import static com.rssl.phizic.einvoicing.EInvoicingConstants.*;
import com.rssl.phizic.utils.store.Store;
import com.rssl.phizic.utils.store.StoreManager;
import com.rssl.phizic.web.auth.AuthenticationActionBase;
import com.rssl.phizic.web.auth.AuthenticationFormBase;

import javax.servlet.http.HttpServletRequest;

/**
 * @ author: Vagin
 * @ created: 08.02.2013
 * @ $Author
 * @ $Revision
 * Вход клиента при оплате с внешней ссылки.
 */
public class AuthenticationPayOrderAction extends AuthenticationActionBase
{
	protected void storePayOrderInfo(HttpServletRequest request, AuthenticationFormBase frm)
	{
		super.storePayOrderInfo(request, frm);
		//храним в сессии на случай переходов по другим ссылкам.
		Store currStore = StoreManager.getCurrentStore();
		currStore.save(PayOrderHelper.PAY_ORDER_BACK_URL, request.getRequestURI());
		frm.setField(AuthenticationFormBase.IS_PAYORDER, true);
		if (request.getParameter(WEBSHOP_REQ_ID) != null)
		{
			frm.setField(WEBSHOP_REQ_ID, request.getParameter(WEBSHOP_REQ_ID));
			currStore.save(WEBSHOP_REQ_ID, request.getParameter(WEBSHOP_REQ_ID));
		}
		else if (request.getParameter(FNS_PAY_INFO) != null)
		{
			frm.setField(FNS_PAY_INFO, request.getParameter(FNS_PAY_INFO));
			currStore.save(FNS_PAY_INFO, request.getParameter(FNS_PAY_INFO));
		}
		else if (request.getParameter(UEC_PAY_INFO) != null)
		{
			frm.setField(UEC_PAY_INFO, request.getParameter(UEC_PAY_INFO));
			currStore.save(UEC_PAY_INFO, request.getParameter(UEC_PAY_INFO));
		}
		else if (currStore.restore(WEBSHOP_REQ_ID)!= null)
		{
			frm.setField(WEBSHOP_REQ_ID, currStore.restore(WEBSHOP_REQ_ID));
		}
		else if (currStore.restore(FNS_PAY_INFO)!= null)
		{
			frm.setField(FNS_PAY_INFO, currStore.restore(FNS_PAY_INFO));
		}
		else if (currStore.restore(UEC_PAY_INFO)!= null)
		{
			frm.setField(UEC_PAY_INFO, currStore.restore(UEC_PAY_INFO));
		}
	}
}
