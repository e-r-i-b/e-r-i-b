package com.rssl.phizic.web.dictionaries;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.persons.PersonOperationMode;
import com.rssl.phizic.logging.operations.SimpleLogParametersReader;
import com.rssl.phizic.operations.dictionaries.AddPersonalPaymentCardOperation;
import com.rssl.phizic.operations.dictionaries.GetPersonalPaymentCardOperation;
import com.rssl.phizic.web.actions.OperationalActionBase;
import com.rssl.phizic.web.persons.PersonUtils;
import org.apache.struts.action.*;

import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Gainanov
 * @ created 22.07.2008
 * @ $Author$
 * @ $Revision$
 */
public class ShowPersonalPaymentCardInfoAction extends OperationalActionBase
{
	private static final String FORWARD_START = "Start";
	private static final String FORWARD_CLOSE = "Close";

	protected Map<String, String> getKeyMethodMap()
	{
		Map<String, String> map = new HashMap<String, String>();

		map.put("button.save", "save");

		return map;
	}

	public ActionForward start(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response
	) throws BusinessException
	{
		Long personId = PersonUtils.getPersonId(request);
		String cardId = request.getParameter("id");
		Long billingId = retriveBillingId(request);
		try
		{
			GetPersonalPaymentCardOperation operation
					= createOperation(GetPersonalPaymentCardOperation.class);
			ShowPersonalPaymentCardInfoForm frm = (ShowPersonalPaymentCardInfoForm)form;
			operation.initialize(personId, cardId, billingId);
			frm.setClient(operation.getCardOwner());
			frm.setActivePerson(operation.getPerson());
			frm.setValidCard(operation.checkFIO());
			frm.setCardId(cardId);
			frm.setModified(PersonOperationMode.shadow.equals(operation.getMode()));
			return mapping.findForward(FORWARD_START);
		}
		catch(BusinessLogicException e)
		{
			ActionMessages actionErrors = new ActionMessages();
			actionErrors.add(e.getMessage(), new ActionMessage(e.getMessage(),false) );
			saveErrors(request, actionErrors);
		}
		return new ActionForward(
				mapping.findForward(FORWARD_CLOSE).getPath() + "?person=" + personId, true);
	}

	/**
	 * Пользователь нажал кнопку "сохранить"
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward save(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response
	) throws BusinessException
	{
		Long personId = PersonUtils.getPersonId(request);

		try
		{
			AddPersonalPaymentCardOperation operation
					= createOperation(AddPersonalPaymentCardOperation.class);
			operation.initialize(personId, request.getParameter("id"), retriveBillingId(request));

			request.setAttribute("$$newId", personId);

			operation.switchToShadow();
			operation.save();

			addLogParameters(new SimpleLogParametersReader(
					"ID cохраняемой сущности", request.getParameter("id")) );
		}
		catch (BusinessLogicException ex)
		{
			saveError(request, ex);
		}

		return new ActionForward(
				mapping.findForward(FORWARD_CLOSE).getPath() + "?person=" + personId, true);
	}

	private static Long retriveBillingId(HttpServletRequest request) throws BusinessException
	{
		try
		{
			return Long.parseLong(request.getParameter("billing"));
		}
		catch (NumberFormatException ex)
		{
			throw new BusinessException(ex);
		}
	}
}
