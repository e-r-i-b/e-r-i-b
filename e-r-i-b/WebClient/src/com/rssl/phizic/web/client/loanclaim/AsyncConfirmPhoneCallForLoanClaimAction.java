package com.rssl.phizic.web.client.loanclaim;

import com.rssl.phizic.auth.GuestLogin;
import com.rssl.phizic.auth.Login;
import com.rssl.phizic.auth.modes.ConfirmRequest;
import com.rssl.phizic.auth.modes.SmsPasswordExistException;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.documents.payments.ExtendedLoanClaim;
import com.rssl.phizic.business.persons.PersonHelper;
import com.rssl.phizic.business.web.ConfirmationManager;
import com.rssl.phizic.common.types.ConfirmStrategyType;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.logging.operations.BeanLogParemetersReader;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.messaging.OperationType;
import com.rssl.phizic.operations.loanclaim.AsyncConfirmPhoneCallForLoanClaimOperation;
import com.rssl.phizic.operations.sms.CallBackHandlerSmsImpl;
import com.rssl.phizic.security.SecurityLogicException;
import com.rssl.phizic.security.SmsErrorLogicException;
import com.rssl.phizic.web.actions.NoActiveOperationException;
import com.rssl.phizic.web.actions.OperationalActionBase;
import com.rssl.phizic.web.actions.StrutsUtils;
import com.rssl.phizic.web.actions.payments.forms.RequestValuesSource;
import com.rssl.phizic.web.common.confirm.ConfirmHelper;
import com.rssl.phizic.web.security.SecurityMessages;
import org.apache.commons.collections.CollectionUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import java.util.Calendar;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Асинхронное подтверждение разрешения запроса кредитной истории в БКИ для продолжения заполнения заявки на кредит
 *
 * @ author: Gololobov
 * @ created: 06.02.15
 * @ $Author$
 * @ $Revision$
 */
public class AsyncConfirmPhoneCallForLoanClaimAction extends OperationalActionBase
{
	private static final Log log = PhizICLogFactory.getLog(com.rssl.phizic.logging.Constants.LOG_MODULE_CORE);

	protected Map<String, String> getKeyMethodMap()
	{
		Map<String, String> map = super.getKeyMethodMap();
		map.put("button.confirmSMS", "changeToSMS");
		map.put("button.dispatch", "confirm");
		map.put("button.checkGuest", "checkGuest");
		return map;
	}
	public ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		return null;
	}

	/**
	 * Отправка СМС клиенту для подтверждения заказа обратного звонка
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward changeToSMS(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		AsyncConfirmPhoneCallForLoanClaimForm frm = (AsyncConfirmPhoneCallForLoanClaimForm) form;
		AsyncConfirmPhoneCallForLoanClaimOperation operation = createConfirmOperation(frm);
		operation.initialize(frm.getLoanClaimId());
		ExtendedLoanClaim claim = operation.getClaim();

		if (claim == null)
		{
			log.error(StrutsUtils.getMessage("loan.callFromBank.messages.errorSendSMS.log", "commonBundle"));
			frm.setErrorMessage(StrutsUtils.getMessage("loan.callFromBank.messages.errorSendSMS.client", "commonBundle"));
			return mapping.findForward(FORWARD_SHOW);
		}

		boolean isMBConnected = PersonHelper.isMBConnected();
		boolean isGuest = PersonHelper.isGuest();
		if (isGuest && isMBConnected)
		{
			//Если попытка отправить СМС из гостевой сессии с подключенным МБК, то шлём лесом. Ему дорога через джоб и МБК.
			log.error(StrutsUtils.getMessage("loan.callFromBank.messages.guestSession.errorSendSMS.log", "commonBundle"));
			frm.setErrorMessage(StrutsUtils.getMessage("error.errorHeader", "commonBundle"));
		}
		else//Заказ обратного звонка для клиентской сессии или гостевой, но при этом нет подключенного МБК и отправка ему СМС
		{
			if ( frm.haveAcceptToCreditHistory() || (isGuest && !isMBConnected) )
			{
				try
				{
					//отправляем запрос на подтверждение
					sendSMSConfirmRequest(frm, request, operation);
				}
				catch (Exception e)
				{
					log.error(StrutsUtils.getMessage("loan.callFromBank.messages.errorSendSMS.log", "commonBundle"), e);
					frm.setErrorMessage(StrutsUtils.getMessage("loan.callFromBank.messages.errorSendSMS.client", "commonBundle"));
				}
			}
			else//Необходимо согласие на запрос банком информации из Бюро кредитных историй.
				frm.setErrorMessage(StrutsUtils.getMessage("loan.callFromBank.messages.errorCreditHistAccept.client", "commonBundle"));
		}
		return mapping.findForward(FORWARD_SHOW);
	}

	/**
	 * Отправка запроса на подтверждение
	 * @param form
	 * @param request
	 * @param confirmOperation
	 * @throws BusinessLogicException
	 * @throws BusinessException
	 */
	private void sendSMSConfirmRequest(AsyncConfirmPhoneCallForLoanClaimForm form, HttpServletRequest request,
	                                   AsyncConfirmPhoneCallForLoanClaimOperation confirmOperation) throws BusinessLogicException, BusinessException
	{
		ConfirmationManager.sendRequest(confirmOperation);
		preSMSConfirm(confirmOperation, form);
		confirmOperation.getRequest().setPreConfirm(true);
		saveOperation(request, confirmOperation);
	}

	private void preSMSConfirm(AsyncConfirmPhoneCallForLoanClaimOperation operation, AsyncConfirmPhoneCallForLoanClaimForm form) throws BusinessLogicException, BusinessException
	{
		ConfirmRequest confirmRequest = operation.getRequest();

		try
		{
			CallBackHandlerSmsImpl callBackHandler = new CallBackHandlerSmsImpl();
			callBackHandler.setConfirmableObject(operation.getConfirmableObject());
			Login login = PersonContext.getPersonDataProvider().getPersonData().getLogin();
			callBackHandler.setLogin(login);
			ExtendedLoanClaim claim = operation.getClaim();
			if (login instanceof GuestLogin)
				callBackHandler.setPhoneNumber(claim.getOwnerGuestPhone());
			else
				callBackHandler.setPhoneNumber(claim.getFullMobileNumber());
			//Подтверждение заказа обратного звонка
			callBackHandler.setOperationType(OperationType.CALLBACK_CONFIRM_OPERATION);
			//отправка только по номеру получателя
			callBackHandler.setUseRecipientMobilePhoneOnly(true);
			String resultMessage = ConfirmHelper.getPreConfirmString(operation.preConfirm(callBackHandler));
			saveMessage(currentRequest(), resultMessage);
			form.setInformMessage(resultMessage);
			addLogParameters(new BeanLogParemetersReader("Обрабатываемая сущность", operation.getConfirmableObject()));
		}
		catch (SecurityLogicException e)
		{
			String errorMessage = SecurityMessages.translateException(e);
			confirmRequest.setErrorMessage(errorMessage);
			form.setErrorMessage(errorMessage);
			//А не повтор ли это отправки СМС, пока старый пароль ещё свежий
			if (e.getCause() != null)
				form.setSmsPasswordExist(e.getCause().getClass().equals(SmsPasswordExistException.class));
		}
	}

	/**
	 * Подтверждение клиентом по СМС
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward confirm(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		//Подтверждение по СМС и отправка с CRM запроса на звонок
		AsyncConfirmPhoneCallForLoanClaimForm frm = (AsyncConfirmPhoneCallForLoanClaimForm) form;
		boolean confirmedInGuestSession = false;
		if (PersonHelper.isGuest())
		{
			AsyncConfirmPhoneCallForLoanClaimOperation operation = createOperation(AsyncConfirmPhoneCallForLoanClaimOperation.class, "ExtendedLoanClaim");
			operation.initialize(frm.getLoanClaimId());
			ExtendedLoanClaim claim = operation.getClaim();
			if (PersonHelper.isMBConnected())
			{
				//Подтверждение обратного звонка в гостевой сессии
				if (operation.confirmInGuestSession(request.getParameter("$$confirmSmsPassword")))
					confirmedInGuestSession = true;
				else
				{
					frm.setErrorMessage(StrutsUtils.getMessage("loan.callFromBank.messages.errorDispatchSMS.client", "commonBundle"));
					confirmedInGuestSession = true;
				}
			}
		}

		//Если это обычная (не гостевая) сессия или гостевая, но у гостя не подключен МБ
		if (!confirmedInGuestSession)
		{
			if (PersonHelper.isGuest() || frm.haveAcceptToCreditHistory())
			{
				confirmInClientSessionOrWithoutMBC(frm, request);
			}
			else
			{
				//Необходимо согласие на запрос банком информации из Бюро кредитных историй.
				frm.setErrorMessage(StrutsUtils.getMessage("loan.callFromBank.messages.errorCreditHistAccept.client", "commonBundle"));
				frm.setSmsPasswordExist(true);
			}
		}

		return mapping.findForward(FORWARD_SHOW);
	}

	/**
	 * Фиксирование факта запроса обратного звонка в гостевой сессии
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward checkGuest(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		AsyncConfirmPhoneCallForLoanClaimForm frm = (AsyncConfirmPhoneCallForLoanClaimForm) form;
		AsyncConfirmPhoneCallForLoanClaimOperation operation = createOperation(AsyncConfirmPhoneCallForLoanClaimOperation.class, "ExtendedLoanClaim");
		operation.initialize(frm.getLoanClaimId());
		ExtendedLoanClaim claim = operation.getClaim();

		if (claim != null)
		{
			if (PersonHelper.isGuest())
			{
				//Если клиент подключен к мобильному банку то отправка СМС идет через джоб "CardOfferRefusesJob" после обработки сообщ. от МБК
				if (PersonHelper.isMBConnected())
				{
					//Сохранение пароля и даты заказа в заявке. Пароль потом подцепит джоб обработки сообщений (UESI_MessagesFromMBK) из МБК
					claim.setConfirmPassword(operation.generatePasswordForGuestSession());
					claim.setBkiConfirmDate(Calendar.getInstance());
					claim.setActionSign(ExtendedLoanClaim.CONFIRM_PHONE_CALL_GUEST_ACTION);
					operation.saveOrUpdateLoanClaim(claim);
				}
			}
		}
		else
			log.error(StrutsUtils.getMessage("loan.callFromBank.messages.error.claimNotFound", "commonBundle"));

		return mapping.findForward(FORWARD_SHOW);
	}

	private AsyncConfirmPhoneCallForLoanClaimOperation createConfirmOperation(AsyncConfirmPhoneCallForLoanClaimForm frm) throws BusinessException, BusinessLogicException
	{
		AsyncConfirmPhoneCallForLoanClaimOperation operation = createOperation(AsyncConfirmPhoneCallForLoanClaimOperation.class, "ExtendedLoanClaim");
		operation.initialize(frm.getLoanClaimId());
		//Подтверждение запроса на звонок по СМС
		operation.setUserStrategyType(ConfirmStrategyType.sms);

		return operation;
	}

	/**
	 * Подтверждение обратного звонка в клиентской (не гостевой) сессии или в гостевой, но без подключенного МБ
	 * @param frm
	 * @param request
	 */
	private void confirmInClientSessionOrWithoutMBC(AsyncConfirmPhoneCallForLoanClaimForm frm, HttpServletRequest request)
	{
		try
		{
			AsyncConfirmPhoneCallForLoanClaimOperation operation = getOperation(request);
			ConfirmRequest confirmRequest = operation.getRequest();
			ConfirmHelper.clearConfirmErrors(confirmRequest);

			List<String> errors = ConfirmationManager.readResponse(operation, new RequestValuesSource(request));

			if(CollectionUtils.isNotEmpty(errors))
			{
				ConfirmHelper.saveConfirmErrors(confirmRequest, errors);
				frm.setErrorMessage(confirmRequest.getErrorMessage());
				return;
			}
			operation.confirm();
			addLogParameters(new BeanLogParemetersReader("Подтверждаемая сущность", operation.getConfirmableObject()));
			resetOperation(request);
			}
		catch (BusinessLogicException e)
		{
			log.error(e);
			saveError(request, e);
			frm.setErrorMessage(StrutsUtils.getMessage("loan.callFromBank.messages.errorDispatchSMS.client", "commonBundle"));
		}
		catch (NoActiveOperationException e)
		{
			log.error(e);
			saveError(request, e);
			frm.setErrorMessage(StrutsUtils.getMessage("loan.callFromBank.messages.errorDispatchSMS.client", "commonBundle"));
		}
		catch (SmsErrorLogicException e)
		{
			log.error(e);
			saveError(request, e);
			frm.setErrorMessage(SecurityMessages.translateException(e));
		}
		catch (SecurityLogicException e)
		{
			log.error(e);
			String errorMessage = StrutsUtils.getMessage("loan.callFromBank.messages.errorPasswLifeTime.client", "commonBundle");
			saveError(request, errorMessage);
			frm.setErrorMessage(errorMessage);
			frm.setNeedNewPassword(true);
		}
		catch (Exception e)
		{
			log.error(e);
			String errorMessage = StrutsUtils.getMessage("error.errorHeader", "paymentsBundle");
			saveError(request, errorMessage);
			frm.setErrorMessage(errorMessage);
		}
	}
}
