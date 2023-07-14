package com.rssl.phizic.web.common.client.ext.sbrf.security;

import com.rssl.phizic.auth.CommonLogin;
import com.rssl.phizic.auth.Login;
import com.rssl.phizic.auth.PrincipalImpl;
import com.rssl.phizic.auth.modes.*;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.resources.external.CardLink;
import com.rssl.phizic.business.resources.external.ExternalResourceService;
import com.rssl.phizic.common.types.ConfirmStrategyType;
import com.rssl.phizic.common.types.security.Constants;
import com.rssl.phizic.operations.ext.sbrf.strategy.ConfirmStrategyProviderImpl;
import com.rssl.phizic.operations.push.CallBackHandlerPushImpl;
import com.rssl.phizic.operations.sms.CallBackHandlerSmsImpl;
import com.rssl.phizic.security.SecurityDbException;
import com.rssl.phizic.util.ApplicationUtil;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.web.common.confirm.ConfirmHelper;
import com.rssl.phizic.web.security.AuthenticationManager;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessages;

import java.util.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author mihaylov
 * @ created 24.03.2011
 * @ $Author$
 * @ $Revision$
 */

public class ConfirmLoginAction extends ConfirmWay4PasswordAction
{
	private static final ExternalResourceService resourceService = new ExternalResourceService();

	protected Map<String, String> getAditionalKeyMethodMap()
	{
		Map <String, String> keyMap= new HashMap<String,String>();
		keyMap.put("button.confirm", "validate");
		keyMap.put("button.confirmSMS", "changeToSMS");
		keyMap.put("button.confirmPush", "changeToPush");
		keyMap.put("button.confirmCard", "showCardsConfirm");
		keyMap.put("confirmBySelectedCard","changeToCard");
		keyMap.put("button.confirmCap", "changeToCap");
		keyMap.put("button.nextStage", "forwardNext");
		return keyMap;
	}

    @Override
    protected ConfirmStrategy initConfirmForm(ConfirmWay4PasswordForm form, HttpServletRequest request) throws Exception
    {
        if (ApplicationUtil.isMobileApi() || ApplicationUtil.isSocialApi())
	        return super.initConfirmForm(form, request);

        ConfirmStrategy strategy = getStrategy(null);
        setAnotherStrategy(form,strategy);
        return strategy;
    }

    public ActionForward forwardNext(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		Stage stage = AuthenticationManager.getStage();
		return redirectToStage(request,stage);
	}

	protected ConfirmStrategy getStrategy(ConfirmStrategyType type)
	{
		AccessPolicy policy =  getAuthenticationContext().getPolicy();
		CommonLogin login = getAuthenticationContext().getLogin();
		Properties properties = new Properties();
		try
		{
			properties = new AccessPolicyService().getProperties(login, policy.getAccessType());
		}
		catch (SecurityDbException e)
		{
			log.error(e.getMessage(), e);
		}

		ConfirmStrategyProvider strategyProvider = new ConfirmStrategyProviderImpl(new PrincipalImpl(login, policy, properties));
		iPasCompositeConfirmStrategy strategy = (iPasCompositeConfirmStrategy)strategyProvider.getStrategy();

		if(type==null)
		{
			String confirmValue = getAuthenticationContext().getPolicyProperties().getProperty("userOptionType");
			if (StringHelper.isEmpty(confirmValue))
				confirmValue="sms";
			type = ConfirmStrategyType.valueOf(confirmValue);
		}
		strategy.setDefaultStrategy(type);
		return strategy;
	}

	/**
	 * ќтобразить пользователю список карт по чекам с которых может быть подтверждена оперци€.
	 * ≈сли в списке только одна карта то список не отображаетс€ и переходим на следующий шаг.
	 */
	public ActionForward showCardsConfirm(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		ConfirmWay4PasswordForm frm = (ConfirmWay4PasswordForm) form;
		frm.setField("confirmCardId", null);
		frm.setField("confirmByCard", true);

		AuthenticationContext authCtx = getAuthenticationContext();

		String pan   = authCtx.getPAN();
		Login  login = (Login) authCtx.getLogin();
		//при входе клиента, если это его первый раз - кардлинков еще нет в базе.
		//испльзуем старый алгоритм.
		if (login.getLastLogonDate() == null)
		{
			return changeToCard(mapping, form, request, response);
		}

		List<CardLink> cardLinks = resourceService.getLinks(login, CardLink.class);
		List<CardLink> result    = new ArrayList<CardLink>();

		boolean inCardLink = false;
		for(CardLink link:cardLinks)
		{
			if(link.isMain())
				result.add(link);

			if (StringHelper.isNotEmpty(pan))
			{
				if (!inCardLink)
				{
					inCardLink = link.getNumber().equals(pan);
				}
			}
		}

		if (!inCardLink)
		{
			CardLink link = new CardLink();
			link.setNumber(pan);
			result.add(link);
		}

		ConfirmRequest rq = new CardClientConfirmRequest() ;
		frm.setConfirmRequest(rq);
		//если ошибок не было и только одна карта по коорой возможно подтверждение чеком не отображаем формы с выбором карт.
		if (result.size() == 1 && frm.getField("cardConfirmError") == null)
		{
			CardLink link = result.get(0);

			if (link.getId() != null)
			{
				frm.setField("confirmCardId", result.get(0).getId().toString());
			}
			else if (StringHelper.isNotEmpty(pan))
			{
				frm.setField("confirmByPan",  Boolean.TRUE.toString());
			}

			return changeToCard(mapping, form, request, response);
		}

		frm.setField("confirmCards", result);
		frm.getConfirmRequest().setPreConfirm(false);
		return mapping.findForward(FORWARD_SHOW);
	}

	public ActionForward changeToCard( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws Exception
	{
		ConfirmWay4PasswordForm frm = (ConfirmWay4PasswordForm) form;

		if (StringHelper.isNotEmpty((String) frm.getField("confirmCardId")))
		{
			frm.setField("confirmByCard", true);
			frm.setField("confirmCard",   resourceService.findLinkById(CardLink.class, Long.valueOf((String) frm.getField("confirmCardId"))).getNumber());
		}
		else if(Boolean.parseBoolean((String) frm.getField("confirmByPan")))
		{
			frm.setField("confirmByCard", true);

			CardLink link = new CardLink();
			link.setNumber(getAuthenticationContext().getPAN());
			frm.setField("confirmCard", link);
		}

		ConfirmStrategy strategy = getStrategy(ConfirmStrategyType.card);
		try
		{
			ConfirmRequest confirmRequest = createStrategyRequest(strategy, frm, request);

			frm.getConfirmRequest().setPreConfirm(true);
			setAnotherStrategy(frm,strategy);
			// ≈сли сменилась стратеги€ подтверждени€ из-за ошибки, пишем причину и отправл€ем —ћ—-пароль
			// —ейчас фактически других стратегий быть не может. ≈сли по€в€тс€, нужно будет уточн€ть, что с ними делать.
			ConfirmStrategyType currentType = confirmRequest.getStrategyType();
			saveConfirmStrategyMessages(request, confirmRequest);

			if (currentType == ConfirmStrategyType.sms || currentType == ConfirmStrategyType.cap)
			{
				//noinspection ThrowableResultOfMethodCallIgnored
				saveConfirmErrors(Collections.singletonList(strategy.getWarning().getMessage()), request, confirmRequest);
				return preConfirm(mapping, frm, request, response, new CallBackHandlerSmsImpl());
			}
			return mapping.findForward(FORWARD_SHOW);
		}
		catch(BusinessLogicException e)
		{
			frm.setField("cardConfirmError", e.getMessage());
	        return showCardsConfirm(mapping,frm,request,response);	
		}
	}

	public ActionForward changeToCap( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws Exception
	{
		ConfirmWay4PasswordForm frm = (ConfirmWay4PasswordForm) form;
		ConfirmStrategy strategy = getStrategy(ConfirmStrategyType.cap);
		ConfirmRequest confirmRequest = createStrategyRequest(strategy, frm, request);
		frm.getConfirmRequest().setPreConfirm(true);
		setAnotherStrategy(frm,strategy);
		ConfirmStrategyType currentType = confirmRequest.getStrategyType();
		saveConfirmStrategyMessages(request, confirmRequest);
		if (currentType == ConfirmStrategyType.sms || currentType == ConfirmStrategyType.card)
		{
			saveConfirmErrors(Collections.singletonList(strategy.getWarning().getMessage()), request, confirmRequest);
			return preConfirm(mapping, frm, request, response, new CallBackHandlerSmsImpl());
		}

		return mapping.findForward(FORWARD_SHOW);
	}

	public ActionForward changeToSMS( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws Exception
	{
		ConfirmWay4PasswordForm frm = (ConfirmWay4PasswordForm) form;
		ConfirmStrategy strategy = getStrategy(ConfirmStrategyType.sms);
		ConfirmRequest confirmRequest = createStrategyRequest(strategy, frm, request);
		frm.getConfirmRequest().setPreConfirm(true);
		setAnotherStrategy(frm,strategy);
		saveConfirmStrategyMessages(request, confirmRequest);
		return preConfirm(mapping,frm,request,response, new CallBackHandlerSmsImpl());
	}

	public ActionForward changeToPush( ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response ) throws Exception
	{
		ConfirmWay4PasswordForm frm = (ConfirmWay4PasswordForm) form;
		ConfirmStrategy strategy = getStrategy(ConfirmStrategyType.push);
		ConfirmRequest confirmRequest = createStrategyRequest(strategy, frm, request);
		frm.getConfirmRequest().setPreConfirm(true);
		setAnotherStrategy(frm,strategy);
		saveConfirmStrategyMessages(request, confirmRequest);
		return preConfirm(mapping,frm,request,response, new CallBackHandlerPushImpl());
	}

	/*добавление информационных сообщений в запрос (всплывающее окно) подтверждени€ входа*/
	private void saveConfirmStrategyMessages(HttpServletRequest request, ConfirmRequest confirmRequest)
	{
		if (confirmRequest == null)
			return;

		ActionMessages msgs  = new ActionMessages();
		switch (confirmRequest.getStrategyType())
		{
			case card:

				if (ApplicationUtil.isMobileApi())
				{
					saveMessage(request, getCardPasswordLeftCount(confirmRequest));
					saveMessage(request, Constants.HOW_TO_GET_NEW_CARD_PASSWORDS_MESSAGE);
				}
				else
				{
					confirmRequest.addMessage(getCardPasswordLeftCount(confirmRequest));
					confirmRequest.addMessage(Constants.HOW_TO_GET_NEW_CARD_PASSWORDS_MESSAGE);
				}
				break;
			case cap:
				confirmRequest.addMessage(ConfirmHelper.getPreConfirmCapString());
			default:
				break;
		}
		saveMessages(request, msgs);
	}

	protected ActionForward forwardError(ActionMapping mapping, ActionForm form, ConfirmRequest confirmRequest)
	{
		if(confirmRequest!= null && confirmRequest.getStrategyType() == ConfirmStrategyType.card)
		{
			ConfirmWay4PasswordForm frm = (ConfirmWay4PasswordForm) form;
			frm.setField("confirmByCard", true);
		}
		return mapping.findForward(FORWARD_SHOW);
	}

	private static String getCardPasswordLeftCount(ConfirmRequest confirmRequest)
	{
		if (!(confirmRequest instanceof iPasPasswordCardConfirmRequest))
			return Constants.DAFAULT_ERROR_MESSAGE;

		iPasPasswordCardConfirmRequest cardConfirmRequest = (iPasPasswordCardConfirmRequest) confirmRequest;
		return "ќбратите внимание! Ќа ¬ашем чеке осталось " + cardConfirmRequest.getPasswordsLeft() + " одноразовых паролей.";
	}

	protected void saveConfirmErrors(List<String> errors, HttpServletRequest request, ConfirmRequest confirmRequest)
	{
		ConfirmHelper.saveConfirmErrors(confirmRequest, errors);
	}

	protected void clearConfirmErrors(HttpServletRequest httpServletRequest, ConfirmRequest confirmRequest)
	{
		ConfirmHelper.clearConfirmErrors(confirmRequest);
	}

	protected void saveConfirmMessage(String message, HttpServletRequest request, ConfirmRequest confirmRequest)
	{
		confirmRequest.addMessage(message);
	}
}
