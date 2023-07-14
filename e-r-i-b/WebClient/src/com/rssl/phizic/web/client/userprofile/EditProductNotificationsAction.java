package com.rssl.phizic.web.client.userprofile;

import com.rssl.phizic.auth.modes.PreConfirmObject;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.persons.PersonHelper;
import com.rssl.phizic.business.resources.external.AccountLink;
import com.rssl.phizic.business.resources.external.ErmbProductLink;
import com.rssl.phizic.business.web.ConfirmationManager;
import com.rssl.phizic.common.types.ConfirmStrategyType;
import com.rssl.phizic.common.types.exceptions.InactiveExternalSystemException;
import com.rssl.phizic.logging.operations.BeanLogParemetersReader;
import com.rssl.phizic.operations.userprofile.SetupResourcesSmsNotificationOperation;
import com.rssl.phizic.security.SecurityLogicException;
import com.rssl.phizic.web.actions.payments.forms.RequestValuesSource;
import com.rssl.phizic.web.common.confirm.ConfirmHelper;
import com.rssl.phizic.web.security.SecurityMessages;
import org.apache.struts.action.*;

import java.security.AccessControlException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.rssl.phizic.operations.userprofile.SetupNotificationOperation.NONE_CHANGE_MESSAGE;

/**
 * @author lukina
 * @ created 10.07.2014
 * @ $Author$
 * @ $Revision$
 */
public class EditProductNotificationsAction    extends EditUserProfileActionBase
{
	private static final String EMPTY_CHANGE_MESSAGE = "Вы не внесли никаких изменений в настройки SMS-оповещений по продуктам.";
	private static final String ATTENTION_MESSAGE = "Обратите внимание, если Вы перейдете на другую страницу системы без подтверждения изменения настроек SMS-паролем, то Ваши изменения не будут сохранены в системе.";


	protected Map<String, String> getKeyMethodMap()
	{
		Map<String,String> keys = new HashMap<String,String>();

		//Настройка смс-оповещений по продуктам
		keys.put("button.saveProductsSmsNotificationSettings", "save");
		keys.put("button.preConfirmProductsSmsNotificationSMS", "preConfirmSMS");
		keys.put("button.confirmProductsSms", "confirm");
		keys.put("button.nextStage", "doNextStage");
		return keys;
	}

	public ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		if (!PersonHelper.availableNewProfile())
			throw new AccessControlException("Доступ к операции запрещен.");
		EditProductNotificationsForm frm = (EditProductNotificationsForm) form;
		SetupResourcesSmsNotificationOperation productsSmsNotificationOperation = createOperation(SetupResourcesSmsNotificationOperation.class);
		productsSmsNotificationOperation.initialize();
		smsNotificationUpdateFormData(productsSmsNotificationOperation, frm);

		frm.setSelectedAccountIds(getSelectedProductIds(frm.getAccounts()));
		frm.setSelectedCardIds(getSelectedProductIds(frm.getCards()));
		frm.setSelectedLoanIds(getSelectedProductIds(frm.getLoans()));

		return mapping.findForward(FORWARD_START);
	}


	public ActionForward save(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		SetupResourcesSmsNotificationOperation operation = createOperation(SetupResourcesSmsNotificationOperation.class);
		operation.initialize();

		EditProductNotificationsForm frm = (EditProductNotificationsForm) form;

		operation.updateResourcesNotificationSettings(frm.getSelectedAccountIds(), frm.getSelectedCardIds(), frm.getSelectedLoanIds(), frm.isNewProductsNotification());
		operation.resetConfirmStrategy();

		if (operation.noChanges())
		{
			ActionMessages message = new ActionMessages();
			message.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(EMPTY_CHANGE_MESSAGE, false));
			saveErrors(request, message);
			return start(mapping, form, request, response);
		}

		smsNotificationUpdateFormData(operation, frm);
		frm.setProductsSmsNotificationConfirmableObject(operation.getConfirmableObject());
		frm.setProductsSmsNotificationConfirmStrategy(operation.getConfirmStrategy());

		ConfirmationManager.sendRequest(operation);
		ActionMessages message = new ActionMessages();
		message.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(ATTENTION_MESSAGE, false));
		saveMessages(request, message);
		saveOperation(request,operation);

		return mapping.findForward(FORWARD_START);
	}

	/**
	 * Активация окна, подтверждения по SMS
	 */
	public ActionForward preConfirmSMS(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		SetupResourcesSmsNotificationOperation operation = getOperation(request);
		EditProductNotificationsForm frm   = (EditProductNotificationsForm) form;

		operation.setUserStrategyType(ConfirmStrategyType.sms);

		ConfirmationManager.sendRequest(operation);

		smsNotificationUpdateFormData(operation, frm);
		frm.setProductsSmsNotificationConfirmableObject(operation.getConfirmableObject());
		frm.setProductsSmsNotificationConfirmStrategy(operation.getConfirmStrategy());

		try
		{
			operation.getRequest().setPreConfirm(true);
			PreConfirmObject preConfirmObject = operation.preConfirm(createCallBackHandler(ConfirmStrategyType.sms, operation.getLogin(), operation.getConfirmableObject()));
			operation.getRequest().addMessage(ConfirmHelper.getPreConfirmString(preConfirmObject));
			addLogParameters(new BeanLogParemetersReader("Обрабатываемая сущность", operation.getConfirmableObject()));
		}
		catch (SecurityLogicException e)
		{
			operation.getRequest().setErrorMessage(SecurityMessages.translateException(e));
		}

		return mapping.findForward(FORWARD_START);
	}

	public ActionForward confirm(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		EditProductNotificationsForm frm = (EditProductNotificationsForm)form;
		SetupResourcesSmsNotificationOperation operation = getOperation(request);
		smsNotificationUpdateFormData(operation, frm);
		frm.setProductsSmsNotificationConfirmableObject(operation.getConfirmableObject());
		frm.setProductsSmsNotificationConfirmStrategy(operation.getConfirmStrategy());

		return confirmSettings(operation, mapping, frm, request, response);
	}

	private ActionForward confirmSettings(SetupResourcesSmsNotificationOperation operation, ActionMapping mapping, EditProductNotificationsForm frm, HttpServletRequest request, HttpServletResponse response)  throws Exception
	{
		try
		{
			List<String> errors = ConfirmationManager.readResponse(operation, new RequestValuesSource(request));
			if (!errors.isEmpty() )
			{
				operation.getRequest().setErrorMessage(errors.get(0));
				frm.setConfirmableObject(operation.getConfirmableObject());
				return mapping.findForward(FORWARD_START);
			}
			else
			{
				operation.confirm();
				operation.sendSmsNotification(true);
				if(isAjax())
					if (operation.getNotUpdatedLinks() == null || operation.getNotUpdatedLinks().isEmpty())
						saveSessionMessage(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("Данные успешно сохранены.", false), null);
					else
						saveSessionMessage(ActionMessages.GLOBAL_MESSAGE, getInfoMessage(operation.getNotUpdatedLinks()), null);
				else
				{
					ActionMessages actionMessages = new ActionMessages();
					if (operation.getNotUpdatedLinks() == null || operation.getNotUpdatedLinks().isEmpty())
						actionMessages.add(ActionMessages.GLOBAL_MESSAGE, (new ActionMessage("Данные успешно сохранены.", false)));
					else
						actionMessages.add(ActionMessages.GLOBAL_MESSAGE, getInfoMessage(operation.getNotUpdatedLinks()));
					saveMessages(request, actionMessages);
				}
				return doNextStage(mapping, frm, request, response);
			}
		}
		catch (BusinessLogicException ble)
		{
			saveError(request, SecurityMessages.translateException(ble));
			frm.setConfirmableObject(operation.getConfirmableObject());
			return mapping.findForward(FORWARD_START);
		}
		catch (SecurityLogicException e) // ошибка подтверждения
		{
			operation.getRequest().setErrorMessage(SecurityMessages.translateException(e));
			frm.setConfirmableObject(operation.getConfirmableObject());
			return mapping.findForward(FORWARD_START);
		}
		catch (SecurityException e) //упал сервис
		{
			operation.getRequest().setErrorMessage("Сервис временно недоступен, попробуйте позже");
			frm.setConfirmableObject(operation.getConfirmableObject());
			return mapping.findForward(FORWARD_START);
		}
		catch (InactiveExternalSystemException e) // ошибка неактивна внешняя система
		{
			operation.getRequest().setErrorMessage(SecurityMessages.translateException(e));
			frm.setConfirmableObject(operation.getConfirmableObject());
			return mapping.findForward(FORWARD_START);
		}
	}

	private ActionMessage getInfoMessage(List<AccountLink> links)
	{
		StringBuilder linkNames = new StringBuilder();
		for (AccountLink link : links)
		{
			linkNames.append("\"" + link.getName() + " " + link.getNumber() + "\"" + ", ");
		}
		linkNames = linkNames.delete(linkNames.length()-1, linkNames.length());
		return new ActionMessage(String.format(NONE_CHANGE_MESSAGE, linkNames), false);
	}

	private void smsNotificationUpdateFormData(SetupResourcesSmsNotificationOperation operation, EditProductNotificationsForm frm) throws BusinessException
	{
		frm.setAccounts(operation.getClientAccounts());
		frm.setCards(operation.getClientCards());
		frm.setLoans(operation.getClientLoans());
		frm.setShowResourcesSmsNotificationBlock(operation.isShowResourcesSmsNotificationBlock());
		frm.setTariffAllowAccountSmsNotification(operation.isTariffAllowAccountSmsNotification());
		frm.setTariffAllowCardSmsNotification(operation.isTariffAllowCardSmsNotification());
		frm.setNewProductsNotification(operation.isNewProductNotification());
	}

	private String[] getSelectedProductIds(List<? extends ErmbProductLink> productLinks)
	{
		List<String> selectedProductIds = new ArrayList<String>();
		for (ErmbProductLink productLink : productLinks)
		{
			if (productLink.getErmbNotification())
				selectedProductIds.add(productLink.getId().toString());
		}
		return selectedProductIds.toArray(new String[0]);
	}

	public ActionForward doNextStage(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		return start(mapping, form, request, response);
	}
}
