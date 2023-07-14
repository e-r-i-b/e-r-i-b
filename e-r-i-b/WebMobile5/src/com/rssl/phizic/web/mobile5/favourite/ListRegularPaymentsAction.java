package com.rssl.phizic.web.mobile5.favourite;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.resources.external.EditableExternalResourceLink;
import com.rssl.phizic.common.types.exceptions.InactiveExternalSystemException;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.operations.autopayments.links.ListAutoPaymentLinksOperation;
import com.rssl.phizic.web.actions.OperationalActionBase;
import com.rssl.phizic.web.common.client.ext.sbrf.LongOfferUtils;
import com.rssl.phizic.web.common.client.favourite.ListRegularPaymentsForm;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Action дл€ отображени€ списка автоплатежей типа AutoPayment
 * @author basharin
 * @ created 08.12.2011
 * @ $Author$
 * @ $Revision$
 */

public class ListRegularPaymentsAction extends OperationalActionBase
{
	private static final Log log = PhizICLogFactory.getLog(Constants.LOG_MODULE_CORE);
	private static final String ERROR_MESSAGE = "»нформаци€ по автоплатежам временно не доступна. ѕовторите операцию позже.";

	protected Map<String, String> getKeyMethodMap()
	{
		Map<String, String> map = new HashMap<String, String>();
		map.put("button.filter", "start");
		return map;
	}

	public final ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ListRegularPaymentsForm frm = (ListRegularPaymentsForm) form;
		//обновл€ем длительные поручени€ и автоплатежи
		updateFormRegularPayments(frm);

		return mapping.findForward(FORWARD_START);
	}

	protected void updateFormRegularPayments(ListRegularPaymentsForm frm) throws BusinessException
	{
		List<EditableExternalResourceLink> regularPayments = new ArrayList<EditableExternalResourceLink>();

		if (checkAccess(ListAutoPaymentLinksOperation.class))
		{
			try
			{
				ListAutoPaymentLinksOperation listAutoPaymentLinksOperation = createOperation(ListAutoPaymentLinksOperation.class);
				listAutoPaymentLinksOperation.initialize();
				regularPayments.addAll(LongOfferUtils.getNotEmptyAutoPaymentLinks(listAutoPaymentLinksOperation.getEntity()));
			}
			catch (InactiveExternalSystemException e)
			{
				saveInactiveESMessage(currentRequest(), e);
			}
			catch (BusinessException e)
			{
				saveMessage(currentRequest(), ERROR_MESSAGE);
				log.error(e.getMessage(), e);
			}
			catch (BusinessLogicException e)
			{
				saveMessage(currentRequest(), ERROR_MESSAGE);
				log.error(e.getMessage(), e);
			}
		}

		// выбор только автоплатежей
		frm.setAutoPayments(LongOfferUtils.getAllAutoPayments(regularPayments));
	}
}