package com.rssl.phizic.web.common.mobile.payments.internetShops;

import com.rssl.common.forms.doc.CreationType;
import com.rssl.common.forms.processing.FieldValuesSource;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.operations.payment.EditDocumentOperation;
import com.rssl.phizic.web.common.client.einvoicing.EditEInvoicingPaymentAction;
import org.apache.struts.action.ActionForm;

import java.util.HashMap;
import java.util.Map;

/**
 * Первый шаг редактирования платежа, созданного по технологии оплаты e-invoicing
 * @author Dorzhinov
 * @ created 18.06.2013
 * @ $Author$
 * @ $Revision$
 */
public class EditMobileEInvoicingPaymentAction extends EditEInvoicingPaymentAction
{
	protected CreationType getNewDocumentCreationType()
	{
		return CreationType.mobile;
	}
	
	protected Map<String, String> getKeyMethodMap()
	{
		Map<String, String> map = new HashMap<String, String>();
		map.put("init", "start");
		map.put("save", "save");
		return map;
	}

	protected String buildFormHtml(EditDocumentOperation operation, FieldValuesSource fieldValuesSource, ActionForm form) throws BusinessException
	{
		return operation.buildMobileXml(fieldValuesSource);
	}
}
