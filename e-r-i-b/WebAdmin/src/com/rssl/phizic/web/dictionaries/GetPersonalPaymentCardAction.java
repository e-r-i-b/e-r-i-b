package com.rssl.phizic.web.dictionaries;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.payments.PersonalPaymentCard;
import com.rssl.phizic.business.persons.PersonOperationMode;
import com.rssl.phizic.operations.dictionaries.GetPersonalPaymentCardOperation;
import com.rssl.phizic.operations.dictionaries.RemovePersonalPaymentCardOperation;
import com.rssl.phizic.web.actions.OperationalActionBase;
import com.rssl.phizic.logging.operations.BeanLogParemetersReader;
import org.apache.struts.action.*;

import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Erkin
 * @ created 23.08.2010
 * @ $Author$
 * @ $Revision$
 */
public class GetPersonalPaymentCardAction extends OperationalActionBase
{
	private static final String FORWARD_START = "Start";
	private static final String FORWARD_ADD = "Add";

	protected Map<String, String> getKeyMethodMap()
	{
		Map<String, String> map = new HashMap<String, String>();
		map.put("button.add.card", "add");
		map.put("button.remove", "remove");
		return map;
	}

	public ActionForward start(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response
	) throws BusinessException, BusinessLogicException
	{
		GetPersonalPaymentCardForm frm = (GetPersonalPaymentCardForm) form;
		try
		{
			GetPersonalPaymentCardOperation operation
					= createOperation(GetPersonalPaymentCardOperation.class);
			operation.initialize(frm.getPerson());

			if (operation.getCard() != null)
				frm.setCard(operation.getCard());
			frm.setActivePerson(operation.getPerson());
			frm.setModified(PersonOperationMode.shadow.equals(operation.getMode()));
		}
		catch (BusinessException ex)
		{
			ActionMessages errors = new ActionMessages();
			errors.add(ex.getMessage(), new ActionMessage("Информация по персональным платежам временно не доступна. Повторите операцию позже.",false) );
			saveErrors(request, errors);
		}
		return mapping.findForward(FORWARD_START);
	}

	/**
	 * Пользователь нажал кнопку "добавить карту"
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward add(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response
	) throws BusinessException, BusinessLogicException
	{
		GetPersonalPaymentCardForm frm = (GetPersonalPaymentCardForm) form;

		try {
			GetPersonalPaymentCardOperation operation
					= createOperation(GetPersonalPaymentCardOperation.class);
			operation.initialize(frm.getPerson());
			if (!operation.isPersonalPaymentsAvailable(frm.getCard().getBillingId()))
				throw new BusinessLogicException(
						"Указанная биллинговая система не поддерживает персональные платежи!\\n" +
								"Выберите другую систему");

			PersonalPaymentCard card = frm.getCard();
			return new ActionForward(
					mapping.findForward(FORWARD_ADD).getPath()
					+ "?id=" + card.getCardNumber()
					+ "&person=" + frm.getPerson()
					+ "&billing=" + card.getBillingId(), true);
		}
		catch(BusinessLogicException ex)
		{
			ActionMessages messageContainer = new ActionMessages();
			ActionMessage message = new ActionMessage(ex.getMessage(),false);
			messageContainer.add(ActionMessages.GLOBAL_MESSAGE, message);
		    saveErrors(request, messageContainer);
		}

		return start(mapping, form, request, response);
	}

	/**
	 * Пользователь нажал кнопку "удалить"
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward remove(
			ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response
	) throws BusinessException, BusinessLogicException
	{
		GetPersonalPaymentCardForm frm = (GetPersonalPaymentCardForm) form;

		try {
			RemovePersonalPaymentCardOperation operation
					= createOperation(RemovePersonalPaymentCardOperation.class);
			operation.initialize(frm.getPerson());
			addLogParameters(new BeanLogParemetersReader(
					"Удаляется карта персональных платежей", operation.getEntity()));
			operation.remove();
		}
		catch(BusinessLogicException ex)
		{
			ActionMessages messageContainer = new ActionMessages();
			ActionMessage message = new ActionMessage(ex.getMessage(),false);
			messageContainer.add(ActionMessages.GLOBAL_MESSAGE, message);
		    saveErrors(request, messageContainer);
		}

		return start(mapping, form, request, response);
	}
}
