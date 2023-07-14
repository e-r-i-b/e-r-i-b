package com.rssl.auth.csa.front.operations.auth;

import com.rssl.auth.csa.front.security.*;
import com.rssl.auth.csa.wsclient.CSABackRequestHelper;
import com.rssl.auth.csa.wsclient.CSABackResponseSerializer;
import com.rssl.auth.csa.wsclient.NodeInfo;
import com.rssl.auth.csa.wsclient.exceptions.BackException;
import com.rssl.auth.csa.wsclient.exceptions.BackLogicException;
import com.rssl.auth.csa.wsclient.exceptions.LoginAlreadyRegisteredException;
import com.rssl.auth.csa.front.exceptions.FrontException;
import com.rssl.auth.csa.front.exceptions.FrontLogicException;
import com.rssl.phizic.authgate.AuthConfig;
import com.rssl.phizic.authgate.AuthGateSingleton;
import com.rssl.phizic.authgate.authorization.AuthGateService;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.utils.http.UrlBuilder;
import org.w3c.dom.Document;
import com.rssl.auth.security.SecurityManager;
import javax.xml.transform.TransformerException;

/**
 * @author niculichev
 * @ created 13.09.2012
 * @ $Author$
 * @ $Revision$
 */
public class PostRegistrationOperation extends InterchangeCSABackOperationBase
{
	protected static final String AUTH_TOKEN_PARAM_NAME = "AuthToken";
	protected static final AuthGateService authService = AuthGateSingleton.getAuthService();

	private String login;
	private String password;
	private RegistrationOperationInfo info;

	public void initialize(OperationInfo info, String login, String password)
	{
		this.info = (RegistrationOperationInfo) info;
		this.login = login;
		this.password = password;
	}

	protected Document doRequest() throws BackLogicException, BackException
	{
		SecurityManager manager = NewRegistrationSecurityManager.getIt();

		try
		{
			Document responce = CSABackRequestHelper.sendFinishUserRegistrationRq(info.getOUID(), login, password, true);
			// ввели всё успешно, все сбрасываем
			manager.reset(info.getKeyByUserInfo());
			return responce;
		}
		catch (LoginAlreadyRegisteredException e)
		{
			// дополнительная обработка ошибки
			manager.processUserAction(info.getKeyByUserInfo());
			throw e;
		}
	}

	protected void processResponce(Document responce) throws FrontLogicException, FrontException
	{
		//Логирование входа клиента если он привлечен промоутером
		updatePromoClientLog(null, info.getOUID());

		try
		{
			String authToken = CSABackResponseSerializer.getOUID(responce);
			NodeInfo nodeInfo = CSABackResponseSerializer.getNodeInfo(responce);

			if(StringHelper.isNotEmpty(authToken) && nodeInfo != null)
				info.setRedirect(makeRedirectURL(nodeInfo, authToken));
		}
		catch (TransformerException e)
		{
			throw new FrontException(e);
		}
	}

	private String makeRedirectURL(NodeInfo nodeInfo, String authToken) throws FrontLogicException, FrontException
	{
		AuthConfig authConfig = authService.getConfig();
		String redirectUrlPattern = authConfig.getProperty("csa.front.ikfl.login.url");

		UrlBuilder builder = new UrlBuilder(String.format(redirectUrlPattern, nodeInfo.getHost()));
		builder.addParameter(AUTH_TOKEN_PARAM_NAME, authToken);

		return builder.getUrl();
	}
}
