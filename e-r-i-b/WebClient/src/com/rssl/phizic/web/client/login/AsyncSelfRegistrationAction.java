package com.rssl.phizic.web.client.login;

import com.rssl.auth.csa.wsclient.CSABackRequestHelper;
import com.rssl.auth.csa.wsclient.CSABackResponseSerializer;
import com.rssl.auth.csa.wsclient.exceptions.BackException;
import com.rssl.auth.csa.wsclient.exceptions.BackLogicException;
import com.rssl.common.forms.processing.FormProcessor;
import com.rssl.common.forms.sources.MapValuesSource;
import com.rssl.phizic.authgate.AuthConfig;
import com.rssl.phizic.authgate.AuthGateSingleton;
import com.rssl.phizic.utils.http.UrlBuilder;
import com.rssl.phizic.utils.xml.XmlHelper;
import com.rssl.phizic.web.util.StringFunctions;
import org.apache.struts.action.*;
import org.w3c.dom.Document;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Асинхронное получение пароля и логина ЦСА
 * @author basharin
 * @ created 27.01.14
 * @ $Author$
 * @ $Revision$
 */

public class AsyncSelfRegistrationAction extends SelfRegistrationAction
{
	private static final String FORWARD_SUCCESS = "Success";
	private static final String FORWARD_FIELD_ERROR = "FieldError";
	private static final String FORWARD_MESSAGE_ERROR = "MessageError";
	private static final String FAILURE_IDENTIFICATION_MESSAGE = "Ошибка идентификации. Вы указали неправильный идентификатор/логин или неправильный пароль.";
	private static final String AUTH_TOKEN_PARAM_NAME = "AuthToken";
	private static final String HOST_TAG_NAME = "host";

	protected Map<String, String> getKeyMethodMap()
	{
	    Map<String,String> map = new HashMap<String, String>();
		map.put("login","login");
		return map;
	}

	public ActionForward login(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception
	{
		AsyncSelfRegistrationForm frm = (AsyncSelfRegistrationForm) form;
		FormProcessor<ActionMessages, ?> processor = createFormProcessor(new MapValuesSource(frm.getFields()), AsyncSelfRegistrationForm.FORM);

		try
		{
			if (processor.process())
			{
				Map<String, Object> result = processor.getResult();
				Document responce = CSABackRequestHelper.sendStartCreateSessionRq((String)result.get("AuthLoginInput"), (String) result.get("AuthPasswordInput"));
				frm.setRedirect(makeRedirectURL(responce));
				return mapping.findForward(FORWARD_SUCCESS);
			}
			else
			{
				saveErrors(request, processor.getErrors(), form);
				return mapping.findForward(FORWARD_FIELD_ERROR);
			}
		}
		catch (BackLogicException ignored)
		{
			frm.setTextError(StringFunctions.replaceQuotes(FAILURE_IDENTIFICATION_MESSAGE));
			return mapping.findForward(FORWARD_MESSAGE_ERROR);
		}
		catch (BackException ignored)
		{
			return mapping.findForward(FORWARD_MESSAGE_ERROR);
		}
	}

	private String makeRedirectURL(Document responce)
	{
		String host = XmlHelper.getSimpleElementValue(responce.getDocumentElement(), HOST_TAG_NAME);

		UrlBuilder builder = new UrlBuilder(String.format(getRedirectUrlPattern(), host));
		builder.addParameter(AUTH_TOKEN_PARAM_NAME, CSABackResponseSerializer.getOUID(responce));
		return builder.getUrl();
	}

	/**
	 * Получить шаблон урла для редиректа в целевую АС.
	 * @return шаблон в формате ПРОТОКОЛ://%s/URL_ДО_СТРАНИЦЫ_ПОСТАУТЕНИТИФИКАЦИИ_ЦЕЛЕВОЙ_АС
	 * %s заменяется в последствии на хост, соответсвующий блоку.
	 */
	private String getRedirectUrlPattern()
	{
		AuthConfig authConfig = AuthGateSingleton.getAuthService().getConfig();
		return authConfig.getProperty("csa.front.ikfl.login.url");
	}

	protected void saveErrors(HttpServletRequest request, ActionMessages errors, ActionForm form)
	{
		AsyncSelfRegistrationForm frm = (AsyncSelfRegistrationForm) form;
		Iterator iterator = errors.get(ActionMessages.GLOBAL_MESSAGE);
		while (iterator.hasNext())
		{
			ActionMessage message = (ActionMessage)iterator.next();
			if (message.getValues() != null)
			{
				frm.setNameFieldError("field(" + message.getKey() + ")");
				frm.setTextError(StringFunctions.replaceQuotes(((ActionMessage) message.getValues()[0]).getKey()));
			}
		}
	}
}
