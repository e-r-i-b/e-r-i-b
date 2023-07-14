package com.rssl.phizic.web.client.security;

import com.rssl.phizic.auth.CommonLogin;
import com.rssl.phizic.auth.modes.AuthenticationContext;
import com.rssl.phizic.logging.operations.BeanLogParemetersReader;
import com.rssl.phizic.security.certification.CertificateOwnService;
import com.rssl.phizic.security.crypto.Certificate;
import com.rssl.phizic.utils.RandomHelper;
import com.rssl.phizic.web.security.ConfirmLoginActionBase;
import org.apache.struts.action.*;

import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Evgrafov
 * @ created 26.12.2006
 * @ $Author: sergunin $
 * @ $Revision: 61877 $
 */
@SuppressWarnings({"JavaDoc"})
@Deprecated
//метод next не реализован с учетом использования certId
public class ConfirmCryptoLoginAction extends ConfirmLoginActionBase
{
	private static final String FORWARD_SHOW = "Show";
	private static final String FORWARD_INSTALL = "Install";
	private static final int RANDOM_STRING_LENGTH = 256;

	protected Map<String, String> getKeyMethodMap()
	{
		Map<String, String> map = new HashMap<String, String>();
		map.put("button.next", "next");
		return map;
	}

	public ActionForward start(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		CertificateOwnService  certificateOwnService = new CertificateOwnService();
		ConfirmCryptoLoginForm frm                   = (ConfirmCryptoLoginForm) form;
		AuthenticationContext  context               = getAuthenticationContext();

		ActionForward          forward               = mapping.findForward(FORWARD_SHOW);

		CommonLogin login = context.getLogin();
		if(login == null)
			throw new SecurityException("Не определен логин");


		Certificate certificate = certificateOwnService.findActive(login);

		if(certificate == null)
		{
			addError(login, "Не найден активный сертификат");
			return new ActionForward( mapping.findForward(FORWARD_INSTALL).getPath()+"?needChange=1", true);
		}

		String randomString = RandomHelper.rand(RANDOM_STRING_LENGTH);

		context.setRandomString(randomString);
		context.setCertificate(certificate);
		updateForm(frm, context);

		addLogParameters(new BeanLogParemetersReader("Сертификат", certificate));

		return forward;
	}


	private void updateForm(ConfirmCryptoLoginForm frm, AuthenticationContext context)
	{
		frm.setRandomString(context.getRandomString());
		frm.setCertId(context.getCertificate().getId());
	}

	private void addError(CommonLogin login, String message)
	{
		ActionMessages msgs = new ActionMessages();
		msgs.add(ActionMessages.GLOBAL_MESSAGE, new ActionMessage(message, false));
		log.warn(message + " login=" + login.getId());
		saveErrors(currentRequest(), msgs);
	}

	public ActionForward next(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		throw new UnsupportedOperationException("не умею работать с certId");
	}
}