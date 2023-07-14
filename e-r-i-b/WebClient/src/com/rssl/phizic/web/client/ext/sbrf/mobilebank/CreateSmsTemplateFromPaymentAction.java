package com.rssl.phizic.web.client.ext.sbrf.mobilebank;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.ext.sbrf.mobilebank.PaymentTemplateShortcut;
import com.rssl.phizic.operations.ext.sbrf.mobilebank.ListPaymentTemplatesOperation;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.web.actions.OperationalActionBase;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Erkin
 * @ created 25.05.2010
 * @ $Author$
 * @ $Revision$
 * @deprecated избавление от МБК
 */

/**
 * Экшен для страницы "Создание SMS-шаблона на основе шаблона платежа"
 */
@Deprecated
//todo CHG059738 удалить
public class CreateSmsTemplateFromPaymentAction extends OperationalActionBase
{
	private static final String FORWARD_START = "Start";

	///////////////////////////////////////////////////////////////////////////

	protected Map<String, String> getKeyMethodMap()
	{
		Map<String, String> map = new HashMap<String, String>();
		map.put("button.save", "save");
		map.put("button.filter", "start");
		return map;
	}

	public ActionForward start(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response
	) throws BusinessException, BusinessLogicException
	{
		return doStart(mapping, form);
	}

	private ActionForward doStart(ActionMapping mapping, ActionForm form)
			throws BusinessException, BusinessLogicException
	{
		CreateSmsTemplateFromPaymentForm frm = (CreateSmsTemplateFromPaymentForm) form;

		String phoneCode = frm.getPhoneCode();
		if (StringHelper.isEmpty(phoneCode))
			throw new BusinessException("Не указан номер телефона");

		String cardCode = frm.getCardCode();
		if (StringHelper.isEmpty(cardCode))
			throw new BusinessException("Не указан номер карты");

		ListPaymentTemplatesOperation operation = createOperation("ListMobileBankPaymentTemplatesOperation");
		operation.initialize(cardCode);

		List<PaymentTemplateShortcut> templates = operation.findAll();
		frm.setTemplates(templates);

		return mapping.findForward(FORWARD_START);
	}

}
