package com.rssl.phizic.web.client.depo;

import com.rssl.phizic.auth.Login;
import com.rssl.phizic.auth.modes.ConfirmRequest;
import com.rssl.phizic.auth.modes.PreConfirmObject;
import com.rssl.phizic.auth.modes.iPasPasswordCardConfirmRequest;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.payments.InvalidUserIdBusinessException;
import com.rssl.phizic.business.persons.ActivePerson;
import com.rssl.phizic.business.resources.external.AbstractStoredResource;
import com.rssl.phizic.business.resources.external.CardLink;
import com.rssl.phizic.business.resources.external.DepoAccountLink;
import com.rssl.phizic.business.resources.external.StoredResourceMessages;
import com.rssl.phizic.business.web.ConfirmationManager;
import com.rssl.phizic.common.types.ConfirmStrategyType;
import com.rssl.phizic.common.types.UserIdClientTypes;
import com.rssl.phizic.common.types.exceptions.InactiveExternalSystemException;
import com.rssl.phizic.common.types.bankroll.BankProductType;
import com.rssl.phizic.common.types.transmiters.Pair;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.context.PersonData;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.utils.ExternalSystemHelper;
import com.rssl.phizic.gate.clients.Client;
import com.rssl.phizic.gate.clients.ClientService;
import com.rssl.phizic.gate.depo.DepoAccount;
import com.rssl.phizic.gate.depo.DepoAccountService;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.operations.ConfirmableOperationBase;
import com.rssl.phizic.operations.depo.GetDepoAccountListOperation;
import com.rssl.phizic.operations.push.CallBackHandlerPushImpl;
import com.rssl.phizic.operations.sms.CallBackHandlerSmsImpl;
import com.rssl.phizic.person.Person;
import com.rssl.phizic.security.CallBackHandler;
import com.rssl.phizic.security.SecurityLogicException;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.web.actions.payments.forms.RequestValuesSource;
import com.rssl.phizic.web.client.ext.sbrf.accounts.ShowAccountsExtendedAction;
import com.rssl.phizic.web.common.confirm.ConfirmHelper;
import com.rssl.phizic.logging.operations.BeanLogParemetersReader;
import com.rssl.phizic.web.security.SecurityMessages;
import org.apache.struts.action.*;

import java.util.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author lukina
 * @ created 26.08.2010
 * @ $Author$
 * @ $Revision$
 */

public class ShowDepoAccountsListAction extends ShowAccountsExtendedAction
{
	protected static final Log log = PhizICLogFactory.getLog(Constants.LOG_MODULE_CORE);

	protected Map<String, String> getKeyMethodMap()
    {
	    Map<String, String> keyMap = super.getKeyMethodMap();
	    keyMap.put("button.register", "register");
		keyMap.put("button.confirm", "confirm");
	    keyMap.put("button.preConfirm", "preConfirm");
	    keyMap.put("button.confirmCard", "showCardsConfirm");
	    keyMap.put("confirmBySelectedCard","changeToCard");
	    keyMap.put("button.confirmCap", "changeToCap");
	    keyMap.put("button.confirmSMS", "changeToSMS");
	    keyMap.put("button.confirmPush", "changeToPush");
        return keyMap;
    }

	public ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
            throws Exception
    {
	    ShowDepoAccountsListForm frm = (ShowDepoAccountsListForm) form;

	    GetDepoAccountListOperation operation = createOperation(GetDepoAccountListOperation.class);
		operation.initialize();
	    List<DepoAccountLink> depoAccounts = getPersonDepoAccountLinks(operation);
	    frm.setDepoAccounts(depoAccounts);
	    saveOperation(request, operation);

	    if (depoAccounts.isEmpty())
	    {
		    try
		    {
			    ActivePerson person = PersonContext.getPersonDataProvider().getPersonData().getPerson();
			    ExternalSystemHelper.check(person.asClient().getOffice(), BankProductType.DepoAcc);
		    }
		    catch (InactiveExternalSystemException e)
		    {
			    saveInactiveESMessage(request, e);
		    }
	    }

	    if (operation.isUseStoredResource())
	    {
		    saveInactiveESMessage(currentRequest(), StoredResourceMessages.getUnreachableMessageSystem((AbstractStoredResource) operation.getDepoAccounts().get(0).getDepoAccount()));
	    }

	    return mapping.findForward(FORWARD_SHOW);
    }

	public ActionForward confirm(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)  throws Exception
	{
		GetDepoAccountListOperation operation = getOperation(request);
		ShowDepoAccountsListForm frm = (ShowDepoAccountsListForm) form;
		DepoAccountService service = GateSingleton.getFactory().service(DepoAccountService.class);
		ClientService clientService = GateSingleton.getFactory().service(ClientService.class);
		Person person = PersonContext.getPersonDataProvider().getPersonData().getPerson();
		Client client = clientService.getClientById(person.getClientId());

		try
		{
			List<String> errors = ConfirmationManager.readResponse(operation, new RequestValuesSource(request));
			if (!errors.isEmpty() )
			{
				frm.setConfirmableObject(operation.getConfirmableObject());
				frm.setAnotherStrategyAvailable(operation.isAnotherStrategy());
				frm.setConfirmStrategy(operation.getConfirmStrategy());
				operation.getRequest().setErrorMessage(errors.get(0));
				return mapping.findForward(FORWARD_SHOW);
			}
			else
			{
				//проверку введенного пароля делаем перед отправкой запроса на регистрацию
				operation.validateConfirm();
				
				service.register(client);
				//если регистрация прошла успешно устанавливаем у клиента признак регистрации в депозитарии
				operation.updateRegisteredInDepo(true);

				//если регистрация прошла успешно получаем список счетов депо и добавляем записи в DEPO_ACCOUNT_LINK
				List<DepoAccount> depoAccounts = service.getDepoAccounts(client);
				operation.updateDepoAccounts(depoAccounts);

				operation.saveConfirm();
				return start(mapping, form, request, response);
			}

		}
		catch (GateLogicException e) //ошибка при регистрации или получении счетов депо
		{
			frm.setRegistrationError(true);
			saveError(request, e);
			return start(mapping, form, request, response);
		}
		catch (SecurityLogicException e) // ошибка подтверждения
		{
			frm.setAnotherStrategyAvailable(operation.isAnotherStrategy());
			frm.setConfirmStrategy(operation.getConfirmStrategy());
			frm.setConfirmableObject(operation.getConfirmableObject());
			operation.getRequest().setErrorMessage(e.getMessage());
			if(operation.getRequest().getStrategyType() == ConfirmStrategyType.card)
				frm.setField("confirmByCard",true);
			return mapping.findForward(FORWARD_SHOW);
		}
		catch (SecurityException e) //упал сервис
	    {
		    log.error(e.getMessage(), e);
		    frm.setAnotherStrategyAvailable(operation.isAnotherStrategy());
            frm.setConfirmStrategy(operation.getConfirmStrategy());
            frm.setConfirmableObject(operation.getConfirmableObject());
            operation.getRequest().setErrorMessage("Сервис временно недоступен, попробуйте позже");
            if(operation.getRequest().getStrategyType() == ConfirmStrategyType.card)
                frm.setField("confirmByCard",true);
            return mapping.findForward(FORWARD_SHOW);
	    }
	}

	//отправляем запрос на подключение депозитария
	public ActionForward register(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ShowDepoAccountsListForm frm = (ShowDepoAccountsListForm) form;
		GetDepoAccountListOperation operation = getOperation(request);

		operation.resetConfirmStrategy();
		ConfirmationManager.sendRequest(operation);

		frm.setConfirmableObject(operation.getConfirmableObject());
		frm.setConfirmStrategyType(operation.getStrategyType());
	    frm.setAnotherStrategyAvailable(operation.isAnotherStrategy());
		frm.setConfirmStrategy(operation.getConfirmStrategy());

		return mapping.findForward(FORWARD_SHOW);
	}

	public ActionForward preConfirm( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response, CallBackHandler callBackHandler) throws Exception
	{
		Login login = PersonContext.getPersonDataProvider().getPersonData().getPerson().getLogin();
		ShowDepoAccountsListForm frm = (ShowDepoAccountsListForm) form;
		GetDepoAccountListOperation operation = getOperation(request);
		operation.initialize();
		frm.setConfirmableObject(operation.getConfirmableObject());
		frm.setAnotherStrategyAvailable(operation.isAnotherStrategy());
		frm.setConfirmStrategy(operation.getConfirmStrategy());
		callBackHandler.setConfirmableObject(operation.getConfirmableObject());
		callBackHandler.setLogin(login);
		try
		{
			PreConfirmObject preConfirmObject = operation.preConfirm(callBackHandler);
			operation.getRequest().addMessage(ConfirmHelper.getPreConfirmString(preConfirmObject));
			addLogParameters(new BeanLogParemetersReader("Обрабатываемая сущность", operation.getConfirmableObject()));
		}
		catch (SecurityException e)
		{
			ActionMessages errors = new ActionMessages();
            errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(e.getMessage(), false));
            saveErrors(request, errors);
		}
		catch (SecurityLogicException e)
		{
			ConfirmHelper.saveConfirmErrors(operation.getRequest(), Collections.singletonList(SecurityMessages.translateException(e)));
		}

		return mapping.findForward(FORWARD_SHOW);		
	}
	/**
	 * Отобразить пользователю список карт по чекам с которых может быть подтверждена оперция.
	 * Если в списке только одна карта то список не отображается и переходим на следующий шаг.
	 */
	public ActionForward showCardsConfirm( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws Exception
	{
		ShowDepoAccountsListForm frm = (ShowDepoAccountsListForm) form;
		frm.setField("confirmCardId", null);//при многократной операции выбрать карту-закрыть окно, в урл остается поле id линка. Т.к. крутим все время одну страницу.
		PersonData personData = PersonContext.getPersonDataProvider().getPersonData();
		List<CardLink> cardLinks = personData.getCards();
		List<CardLink> result = new ArrayList<CardLink>();
		for(CardLink link:cardLinks)
		{
			if(link.isMain() && link.isActive())
				result.add(link);
		}
		//если ошибок не было и только одна карта по коорой возможно подтверждение чеком не отображаем формы с выбором карт.
		if(result.size() == 1 && frm.getField("cardConfirmError") == null)
		{
			frm.setField("confirmCardId", result.get(0).getId().toString());
			return changeToCard(mapping, form, request, response);
		}
		GetDepoAccountListOperation operation = getOperation(request);
		frm.setField("confirmCards", result);
		ConfirmRequest confirmRequest = operation.getRequest();
		confirmRequest.setPreConfirm(false);
		frm.setConfirmableObject(operation.getConfirmableObject());
		frm.setConfirmStrategyType(operation.getStrategyType());
	    frm.setAnotherStrategyAvailable(operation.isAnotherStrategy());
		frm.setConfirmStrategy(operation.getConfirmStrategy());
		frm.setField("confirmByCard",true);
		return mapping.findForward(FORWARD_SHOW);
	}

	/**
	 * В случае подтверждения по чеку. UserId в запросе для iPasPasswordCardConfirmStrategy
	 * необходимо указывать для выбранной карты клиента, полученный из МБ.
	 * @param frm - форма
	 * @param operation - текущая операция
	 * @param useStoredValue - признак использования при поиске значений из нашей БД(кардлинка)
	 * @throws com.rssl.phizic.business.BusinessException
	 */
	private void addConfirmCardParameter(ShowDepoAccountsListForm frm, ConfirmableOperationBase operation, boolean useStoredValue) throws BusinessException, BusinessLogicException
	{
		String confirmCardNumberId = (String) frm.getField("confirmCardId");
		//если передан идентифакатор карты подтверждения, то подтверждать будем по ней.
		if (!StringHelper.isEmpty(confirmCardNumberId))
		{
			Login login = PersonContext.getPersonDataProvider().getPersonData().getLogin();
			Pair<UserIdClientTypes, String> userId = ConfirmHelper.getUserIdByConfirmCard(Long.valueOf(confirmCardNumberId), login, useStoredValue);
			if (operation.getPreConfirm() == null)
			{
				Map<String, Object> params = new HashMap<String, Object>();
				params.put("confirmUserId", userId);
				operation.setPreConfirm(new PreConfirmObject(params));
			}
			else
				operation.getPreConfirm().getPreConfimParamMap().put("confirmUserId", userId);

			//устанавливаем поле "карта подтверждения" для отображения на странице подтверждения
			frm.setField("confirmCard", PersonContext.getPersonDataProvider().getPersonData().getCard(Long.valueOf(confirmCardNumberId)).getNumber());
		}
	}

	public ActionForward changeToCard( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws Exception
	{
		ShowDepoAccountsListForm frm = (ShowDepoAccountsListForm) form;
		GetDepoAccountListOperation operation = getOperation(request);
		ConfirmRequest confirmRequest;
		try
		{
			frm.setField("confirmByCard",true);
			addConfirmCardParameter(frm, operation, true);
			operation.setUserStrategyType(ConfirmStrategyType.card);
			confirmRequest = sendChangeToCardRequest(operation, frm);
		}
		catch (BusinessLogicException e)
		{
			frm.setField("cardConfirmError", e.getMessage());
			frm.setField("confirmCardId", null);//зануляем значение, чтобы в случае отказа по одной карте можно было выбрать другую.
			return showCardsConfirm(mapping,frm,request,response);
		}
		confirmRequest.setPreConfirm(true);
		if(confirmRequest instanceof iPasPasswordCardConfirmRequest)
		{
			iPasPasswordCardConfirmRequest iPasReq = (iPasPasswordCardConfirmRequest) confirmRequest;
			iPasReq.setAdditionInfo(ConfirmHelper.getPasswordCardConfirmStrategyAdditionalInfo(iPasReq.getPasswordsLeft()));
		}
		frm.setConfirmableObject(operation.getConfirmableObject());
		frm.setConfirmStrategyType(operation.getStrategyType());
	    frm.setAnotherStrategyAvailable(operation.isAnotherStrategy());
		frm.setConfirmStrategy(operation.getConfirmStrategy());
		if (confirmRequest.getAdditionInfo() != null)
		{
			for (String str : confirmRequest.getAdditionInfo())
			{
				if (!StringHelper.isEmpty(str))
					confirmRequest.addMessage(str);
			}
		}

		// Если сменилась стратегия подтверждения из-за ошибки, пишем причину и отправляем СМС-пароль
		// Сейчас фактически других стратегий быть не может. Если появятся, нужно будет уточнять, что с ними делать.
		ConfirmStrategyType currentType = confirmRequest.getStrategyType();
		if (currentType == ConfirmStrategyType.sms || currentType == ConfirmStrategyType.cap || currentType == ConfirmStrategyType.push)
		{
			//noinspection ThrowableResultOfMethodCallIgnored
			ConfirmHelper.saveConfirmErrors(operation.getRequest(), Collections.singletonList(operation.getWarning().getMessage()));
			return preConfirm(mapping, frm, request, response, createCallBackHandler(currentType));
		}

		return mapping.findForward(FORWARD_SHOW);
	}

	public ActionForward changeToCap( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws Exception
	{
		ShowDepoAccountsListForm frm = (ShowDepoAccountsListForm) form;
		GetDepoAccountListOperation operation = getOperation(request);
		operation.setUserStrategyType(ConfirmStrategyType.cap);

		ConfirmationManager.sendRequest(operation);
		ConfirmRequest confirmRequest = operation.getRequest();
		confirmRequest.setPreConfirm(true);
		frm.setConfirmableObject(operation.getConfirmableObject());
		frm.setConfirmStrategyType(operation.getStrategyType());
		frm.setConfirmStrategy(operation.getConfirmStrategy());
	    frm.setAnotherStrategyAvailable(operation.isAnotherStrategy());
		ConfirmStrategyType currentType = confirmRequest.getStrategyType();
		if (currentType == ConfirmStrategyType.sms || currentType == ConfirmStrategyType.card || currentType == ConfirmStrategyType.push)
		{
			ConfirmHelper.saveConfirmErrors(operation.getRequest(), Collections.singletonList(operation.getWarning().getMessage()));
			return preConfirm(mapping, frm, request, response, createCallBackHandler(currentType));
		}

		return mapping.findForward(FORWARD_SHOW);
	}

	private CallBackHandler createCallBackHandler(ConfirmStrategyType currentType)
	{
		if (currentType == ConfirmStrategyType.push)
			return new CallBackHandlerPushImpl();
		else
			return new CallBackHandlerSmsImpl();
	}

	public ActionForward changeToSMS( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws Exception
	{
		GetDepoAccountListOperation operation = getOperation(request);
		operation.setUserStrategyType(ConfirmStrategyType.sms);
		ConfirmationManager.sendRequest(operation);
		operation.getRequest().setPreConfirm(true);
		return preConfirm(mapping, form, request, response, new CallBackHandlerSmsImpl());
	}

	public ActionForward changeToPush( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws Exception
	{
		GetDepoAccountListOperation operation = getOperation(request);
		operation.setUserStrategyType(ConfirmStrategyType.push);
		ConfirmationManager.sendRequest(operation);
		operation.getRequest().setPreConfirm(true);
		return preConfirm(mapping, form, request, response, new CallBackHandlerPushImpl());
	}

	protected List<DepoAccountLink> getPersonDepoAccountLinks(GetDepoAccountListOperation operationAccounts)
	{
		return operationAccounts.getDepoAccounts();
	}

	private ConfirmRequest sendChangeToCardRequest(GetDepoAccountListOperation operation, ShowDepoAccountsListForm frm) throws BusinessException, BusinessLogicException
	{
		try
		{
			ConfirmationManager.sendRequest(operation);
		}
		catch (InvalidUserIdBusinessException ignore)
		{
			//в случае если по запросу вернулась ошибка неверного userId полученого по карте из базы.
			//Актуализируеим значение userId через МБ и отправляем запрос снова.
			addConfirmCardParameter(frm, operation, false);
			ConfirmationManager.sendRequest(operation);
		}
		finally
		{
			frm.setField("confirmCardId", null);//зануляем значение, чтобы в случае отказа по одной карте можно было выбрать другую.	
		}
		return operation.getRequest();
	}
}
