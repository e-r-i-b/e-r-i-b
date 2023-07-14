package com.rssl.phizic.web.common.socialApi.cards.delivery;

import com.rssl.phizic.auth.Login;
import com.rssl.phizic.auth.modes.SmsPasswordConfirmStrategy;
import com.rssl.phizic.business.documents.forms.TransformInfo;
import com.rssl.phizic.business.documents.payments.BusinessDocument;
import com.rssl.phizic.common.types.ConfirmStrategyType;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.operations.payment.ConfirmFormPaymentOperation;
import com.rssl.phizic.security.ConfirmableObject;
import com.rssl.phizic.security.SecurityDbException;
import com.rssl.phizic.security.config.Constants;
import com.rssl.phizic.security.password.OneTimePassword;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.web.common.client.cards.delivery.EditEmailDeliveryAction;
import com.rssl.phizic.web.servlet.HttpServletEditableRequest;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

import java.util.Calendar;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author komarov
 * @ created 26.05.2014
 * @ $Author$
 * @ $Revision$
 */
public class EditEmailDeliveryMobileAction extends EditEmailDeliveryAction
{
	private static final String SMS_PASSWORD_REAL_NAME = "confirmSmsPassword";
	private static final String PUSH_PASSWORD_REAL_NAME = "confirmPushPassword";
	private static final String CARD_PASSWORD_REAL_NAME = "confirmCardPassword";

	protected boolean getEmptyErrorPage()
	{
		return false;
	}

	@Override
	public ActionForward confirm(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		HttpServletEditableRequest req = new HttpServletEditableRequest (request);

		// для SMS пароля
		if (!StringHelper.isEmpty(request.getParameter(SMS_PASSWORD_REAL_NAME)))
		{
			req.putParameter(Constants.CONFIRM_SMS_PASSWORD_FIELD, request.getParameter(SMS_PASSWORD_REAL_NAME));
		}
		else if (!StringHelper.isEmpty(request.getParameter(CARD_PASSWORD_REAL_NAME)))
		{
			// для карточного пароля
			req.putParameter(Constants.CONFIRM_CARD_PASSWORD_FIELD, request.getParameter(CARD_PASSWORD_REAL_NAME));
		}
		else if (!StringHelper.isEmpty(request.getParameter(PUSH_PASSWORD_REAL_NAME)))
		{   //для паролей по "Push"
			req.putParameter(Constants.CONFIRM_PUSH_PASSWORD_FIELD, request.getParameter(PUSH_PASSWORD_REAL_NAME));
		}

		ActionForward forward = super.confirm(mapping, form, req, response);

		if (FORWARD_SHOW_FORM.equals(forward.getName()))
		{
			ConfirmFormPaymentOperation operation = getOperation(request);
			updateSmsInfo(req, operation.getConfirmableObject());
		}
		return forward;
	}

	private void updateSmsInfo(HttpServletRequest request, ConfirmableObject confirmableObject) throws SecurityDbException
	{
		if (confirmableObject==null || ((BusinessDocument)confirmableObject).getConfirmStrategyType() != ConfirmStrategyType.sms)
			return;

		Login login = PersonContext.getPersonDataProvider().getPersonData().getPerson().getLogin();
		OneTimePassword smsPassword = SmsPasswordConfirmStrategy.getSmsPassword(login, confirmableObject);

		//время жизни
		long lifeTime = (smsPassword == null) ? 0L : ((smsPassword.getExpireDate().getTimeInMillis() - Calendar.getInstance().getTimeInMillis()) / 1000);
		request.setAttribute(Constants.SMS_CURRENT_TIME_TO_LIVE, lifeTime);

		//кол-во попыток
		Long attemptsLeft = (smsPassword == null) ? 0L : (SmsPasswordConfirmStrategy.getWrongAttemptsCount() - smsPassword.getWrongAttempts());
		request.setAttribute(Constants.SMS_ATTEMPTS_LEFT, attemptsLeft);
	}

	protected TransformInfo getTransformInfo(String mode)
	{
		return new TransformInfo(mode, "mobile");
	}
}
