package com.rssl.phizic.business.payments.forms.meta;

import com.rssl.common.forms.DocumentException;
import com.rssl.common.forms.DocumentLogicException;
import com.rssl.phizic.business.documents.payments.BusinessDocument;

/**
 * @author eMakarov
 * @ created 24.01.2008
 * @ $Author$
 * @ $Revision$
 */
public class SetGoodAndServicesDateAction extends SetBusinessDocumentDateAction
{
	private static final String CHECK_TIME = "checkTime";

	public SetGoodAndServicesDateAction()
	{
		super();
	}

	//TODO пока не ясно как тут быть. Т.к. время приема документов задается для всей формы, а для оплаты услуг используется одна форма платежа
	protected int isWorkTime(BusinessDocument document) throws DocumentLogicException, DocumentException
	{
		//так не правильно! - чтобы не падало
		return 0;
	}

	//TODO пока не ясно как тут быть
	/* Оставила старый функционал, до восстановления разграничения времени приема документов оплаты товаров и услуг.
	protected Time getCheckTime(BusinessDocument document) throws DocumentLogicException
	{
		if (!(document instanceof GoodsAndServicesPayment)) {
			throw new IllegalArgumentException("документ не является платежом");
		}
		try
		{
			GoodsAndServicesPayment payment = (GoodsAndServicesPayment)document;
			String checkTimeString = dbPropertyReader.getProperty(getParameter(CHECK_TIME) + "-" + payment.getAttribute("appointment").getStringValue());
			//                                                           разделитель  ^
			return Time.valueOf(checkTimeString);
		}
		catch (IllegalArgumentException e)
		{
			throw new DocumentLogicException("Неверный формат параметра время, должно быть hh:mm:ss", e);
		}
	}
	*/
}
