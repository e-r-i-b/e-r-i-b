package com.rssl.phizic.web.security;

import com.rssl.common.forms.FieldBuilder;
import com.rssl.common.forms.Form;
import com.rssl.common.forms.FormBuilder;
import com.rssl.common.forms.processing.FormProcessor;
import com.rssl.common.forms.validators.RequiredFieldValidator;
import com.rssl.phizic.auth.CommonLogin;
import com.rssl.phizic.auth.modes.AccessPolicy;
import com.rssl.phizic.auth.modes.AccessType;
import com.rssl.phizic.auth.modes.AuthenticationConfig;
import com.rssl.phizic.auth.modes.AuthenticationContext;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.security.SecurityLogicException;
import com.rssl.phizic.security.config.SecurityFactory;
import com.rssl.phizic.security.password.BlockedException;
import com.rssl.phizic.security.password.NamePasswordValidator;
import com.rssl.phizic.security.password.WrongAttemptException;
import com.rssl.phizic.utils.RandomHelper;
import com.rssl.phizic.web.actions.payments.forms.RequestValuesSource;
import org.apache.struts.action.*;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Evgrafov
 * @ created 14.12.2006
 * @ $Author: krenev $
 * @ $Revision: 82782 $
 */

@SuppressWarnings({"JavaDoc"})
public class LoginPasswordStageAction extends LoginStageActionSupport
{
	private static final String FORWARD_SHOW = "Show";

	protected Map<String, String> getKeyMethodMap()
	{
		Map<String, String> map = new HashMap<String, String>();
		map.put("button.login", "login");
		return map;
	}

	protected AccessPolicy tryStartAuthentication(ActionMapping mapping, HttpServletRequest request)
	{
		AccessPolicy policy = super.tryStartAuthentication(mapping, request);

		if(policy != null)
			return policy;

		AuthenticationConfig config = ConfigFactory.getConfig(AuthenticationConfig.class);
		policy = config.getPolicies().get(0);
		AuthenticationManager.startAuthentication(request, policy, getUserVisitingMode());
		return policy;
	}

	public ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		return forwardShow(mapping,form,request);
	}

	public ActionForward login(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		FormProcessor<ActionMessages,?> processor = createFormProcessor(new RequestValuesSource(request), buildForm());

		ActionMessages errors = new ActionMessages();
		ActionForward forward;
		if(!processor.process())
		{
			errors.add(processor.getErrors());
			forward = forwardError(mapping,form,request);
		}
		else
		{
			Map<String, Object> result = processor.getResult();

			String     userId     = (String) result.get("login");
			String     password   = (String) result.get("password");
			AccessType accessType = AccessType.valueOf((String) result.get("accessType"));

			AccessPolicy policy = AuthenticationManager.getPolicy();
			if(!policy.getAccessType().equals(accessType))
			{
				AuthenticationManager.startAuthentication(request, accessType, getUserVisitingMode());
			}

			password = password == null ? "" : password;
			NamePasswordValidator validator = createPasswordValidator(request, result);
			validateLoginPassword(validator, userId, password, errors);

			if(errors.isEmpty())
				forward = null; //определяется следующим stage
			else
				forward = forwardError(mapping,form,request);
		}

		saveErrors(request, errors);

		return forward;
	}

	protected NamePasswordValidator createPasswordValidator(HttpServletRequest request, Map<String, Object> params)
	{
		return SecurityFactory.createPasswordValidator();
	}

	protected void validateLoginPassword(NamePasswordValidator validator, String userId, String password, ActionMessages errors) throws BusinessException
	{
		Calendar start = GregorianCalendar.getInstance();

		try
		{
			CommonLogin login = validator.validateLoginInfo(userId, password.toCharArray());
			AuthenticationContext authenticationContext = getAuthenticationContext();
			authenticationContext.setLogin(login);
			authenticationContext.setUserId(userId);
			authenticationContext.setCB_CODE(RandomHelper.rand(8, RandomHelper.DIGITS));
			completeStage();
		}
		catch (WrongAttemptException e)
		{
			if (e.isNeedMessage())
			{
				log.info(e);
				writeToOperationLog(start, e.getMessage());
				errors.add(ActionMessages.GLOBAL_MESSAGE,
						new ActionMessage("error.login.wrong.attempt", e.getAttemptsLeft()));
			}
			else
			{
				log.info("Ошибка регистрации. Доступ в систему запрещен.");
				writeToOperationLog(start,"Ошибка регистрации. Доступ в систему запрещен.");
				errors.add(ActionMessages.GLOBAL_MESSAGE,
						new ActionMessage("error.login.failed", e.getAttemptsLeft()));
			}
		}
		catch (BlockedException be)
		{
			log.info(be);
			writeToOperationLog(start, be.getMessage());
			LoginMessagesHelper helper = new LoginMessagesHelper();
			errors.add(ActionMessages.GLOBAL_MESSAGE, helper.getLoginBlockMessage(userId));
		}
		catch (SecurityLogicException e)
		{
			log.info(e);
			writeToOperationLog(start, e.getMessage());
			errors.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage("error.login.failed"));
		}
	}

	protected ActionForward forwardShow(ActionMapping mapping,ActionForm form, HttpServletRequest request)
	{
		fillParams(form, request);
		return mapping.findForward(FORWARD_SHOW);
	}

	public Form buildForm()
	{
		FormBuilder formBuilder = new FormBuilder();
		//noinspection TooBroadScope
		FieldBuilder fieldBuilder;

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("login");
		fieldBuilder.setDescription("Логин");
		fieldBuilder.setValidators(new RequiredFieldValidator());
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("password");
		fieldBuilder.setDescription("Пароль");
		fieldBuilder.setValidators(new RequiredFieldValidator());
		formBuilder.addField(fieldBuilder.build());

		fieldBuilder = new FieldBuilder();
		fieldBuilder.setName("accessType");
		fieldBuilder.setDescription("Тип доступа");
		fieldBuilder.setValidators(new RequiredFieldValidator());
		formBuilder.addField(fieldBuilder.build());

		return formBuilder.build();
	}

	protected ActionForward forwardError(ActionMapping mapping,ActionForm form, HttpServletRequest request)
	{
		return forwardShow(mapping, form, request);
	}

	protected void fillParams(ActionForm form, HttpServletRequest request)
	{

	}

}
