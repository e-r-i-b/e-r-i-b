package com.rssl.auth.csa.front.operations.auth;

import com.rssl.auth.csa.back.servises.UserLogonType;
import com.rssl.auth.csa.front.business.regions.RegionHelper;
import com.rssl.auth.csa.wsclient.CSABackResponseSerializer;
import com.rssl.auth.csa.wsclient.NodeInfo;
import com.rssl.auth.csa.wsclient.responses.ConnectorInfo;
import com.rssl.auth.csa.front.exceptions.FrontException;
import com.rssl.auth.csa.front.exceptions.FrontLogicException;
import com.rssl.phizic.authgate.AuthConfig;
import com.rssl.phizic.authgate.AuthGateSingleton;
import com.rssl.phizic.authgate.authorization.AuthGateService;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.utils.http.UrlBuilder;
import com.rssl.phizic.utils.promoters.PromoterContext;
import com.rssl.phizic.utils.xml.XmlHelper;
import org.apache.commons.collections.CollectionUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.util.List;
import javax.xml.transform.TransformerException;

/**
 * @author niculichev
 * @ created 21.01.2013
 * @ $Author$
 * @ $Revision$
 */
@SuppressWarnings({"FieldNameHidesFieldInSuperclass", "ProtectedField"})
abstract public class LoginOperationBase extends InterchangeCSABackOperationBase
{
	protected static final String AUTH_TOKEN_PARAM_NAME = "AuthToken";
	protected static final AuthGateService authService = AuthGateSingleton.getAuthService();
	protected static final Log log = PhizICLogFactory.getLog(Constants.LOG_MODULE_CORE);

	private static final String CLIENT_URL = "csa.front.ikfl.login.url";
	private static final String GUEST_URL = "guest.postRegistration.url";


	protected LoginOperationInfo operationInfo;

	protected void initialize(OperationInfo operationInfo)
	{
		this.operationInfo = (LoginOperationInfo) operationInfo;
	}

	protected void processResponce(Document responce) throws FrontException, FrontLogicException
	{
		try
		{
			operationInfo.setRedirect(makeRedirectURL(responce));

			//Логирование входа клиента, если он привлечен промоутером
			List<ConnectorInfo> connectorInfos = CSABackResponseSerializer.getConnectorInfos(responce);
			if(CollectionUtils.isNotEmpty(connectorInfos))
				updatePromoClientLog(connectorInfos.get(0), null);

			Long profileId = CSABackResponseSerializer.getProfileId(responce);
			if (profileId != null && StringHelper.isEmpty(PromoterContext.getShift()))
				RegionHelper.saveUserRegion(profileId);
		}
		catch (TransformerException e)
		{
			throw new FrontException(e);
		}
	}

	private String makeRedirectURL(Document responce) throws FrontLogicException, FrontException
	{
		try
		{
			NodeInfo nodeInfo = CSABackResponseSerializer.getNodeInfo(responce);
			UserLogonType logonType = CSABackResponseSerializer.getUserLogonType(responce);

			UrlBuilder builder = new UrlBuilder(String.format(getRedirectUrlPattern(logonType), nodeInfo.getHost()));
			builder.addParameter(AUTH_TOKEN_PARAM_NAME, CSABackResponseSerializer.getOUID(responce));
			return builder.getUrl();
		}
		catch (TransformerException e)
		{
			throw new FrontException(e);
		}
	}

	/**
	 * Получить шаблон урла для редиректа в целевую АС.
	 * @return шаблон в формате ПРОТОКОЛ://%s/URL_ДО_СТРАНИЦЫ_ПОСТАУТЕНИТИФИКАЦИИ_ЦЕЛЕВОЙ_АС
	 * %s заменяется в последствии на хост, соответсвующий блоку.
	 */
	protected String getRedirectUrlPattern(UserLogonType userType)
	{
		AuthConfig authConfig = authService.getConfig();
		if (userType == UserLogonType.GUEST)
		{
			return authConfig.getProperty(GUEST_URL);
		}
		else
		{
			return authConfig.getProperty(CLIENT_URL);
		}
	}
}
