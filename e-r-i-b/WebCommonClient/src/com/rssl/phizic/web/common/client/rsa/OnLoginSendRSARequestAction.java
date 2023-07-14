package com.rssl.phizic.web.common.client.rsa;

import com.rssl.phizic.auth.CommonLogin;
import com.rssl.phizic.auth.modes.AccessPolicyService;
import com.rssl.phizic.auth.modes.AthenticationCompleteAction;
import com.rssl.phizic.auth.modes.AuthenticationContext;
import com.rssl.phizic.auth.modes.Choice;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.ermb.MobileBankManager;
import com.rssl.phizic.business.fraudMonitoring.FraudMonitoringSendersFactory;
import com.rssl.phizic.business.resources.external.CardLink;
import com.rssl.phizic.context.HeaderContext;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.context.RSAContext;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.LogModule;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.rsa.exceptions.BlockClientOperationFraudException;
import com.rssl.phizic.rsa.exceptions.ProhibitionOperationFraudGateException;
import com.rssl.phizic.rsa.senders.FraudMonitoringSender;
import com.rssl.phizic.rsa.senders.initialization.InitializationData;
import com.rssl.phizic.rsa.senders.initialization.OnLoginSenderInitializationData;
import com.rssl.phizic.rsa.senders.types.ClientDefinedEventType;
import com.rssl.phizic.rsa.senders.types.EventsType;
import com.rssl.phizic.security.SecurityDbException;
import com.rssl.phizic.security.SecurityLogicException;
import com.rssl.phizic.web.WebContext;
import com.rssl.phizic.web.util.FraudMonitoringUtils;
import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import javax.servlet.http.HttpSession;

/**
 * @author tisov
 * @ created 17.02.15
 * @ $Author$
 * @ $Revision$
 * On-complete экшн, оповещающий систему фрод-мониторинга об успешном входе клиента
 */
public class OnLoginSendRSARequestAction implements AthenticationCompleteAction
{
	private static final char SEPARATOR                                                     = ',';

	protected static final Log log = PhizICLogFactory.getLog(LogModule.Core);

	public void execute(AuthenticationContext context) throws SecurityException, SecurityLogicException
	{
		try
		{
			initialize();
			doFraudControl(context);
		}
		catch (ProhibitionOperationFraudGateException e)
		{
			throw new BlockClientOperationFraudException(e.getMessage(), e);
		}
		catch (BlockClientOperationFraudException e)
		{
			throw e;
		}
		catch (Exception e)
		{
			log.error(e);
		}
		finally
		{
			FraudMonitoringUtils.storeTokens();         //сохран€ем полученные от ‘ћ значени€ токенов в сессию
			FraudMonitoringUtils.storeCookie();         //обновл€ем значение cookie
		}
	}

	protected void initialize()
	{
		HttpSession session = WebContext.getCurrentRequest().getSession();
		HeaderContext.initialize(session);
		RSAContext.initialize(session);
	}

	protected void doFraudControl(AuthenticationContext context) throws Exception
	{
		FraudMonitoringSender sender = FraudMonitoringSendersFactory.getInstance().getSender(EventsType.SESSION_SIGNIN);
		if (sender.isNull())
		{
			return;
		}

		//noinspection unchecked
		sender.initialize(getInitData(context));
		sender.send();
	}

	private List<String> getClientCardNumbers() throws BusinessLogicException, BusinessException
	{
		List<String> result = new ArrayList<String>();
		List<CardLink> cardLinks = PersonContext.getPersonDataProvider().getPersonData().getCardsAll();
		for (CardLink link: cardLinks)
		{
			result.add(link.getNumber());
		}
		return result;
	}

	/**
	 * формирование данные дл€ инициализации сендера
	 * @param context - контекст аутентификации
	 * @return данные дл€ инициализации
	 * @throws BusinessException
	 * @throws BusinessLogicException
	 * @throws SecurityDbException
	 */
	private InitializationData getInitData(AuthenticationContext context) throws BusinessException, BusinessLogicException, SecurityDbException
	{
		List<String> clientCardNumbers = getClientCardNumbers();
		ClientDefinedEventType eventType = getEventTypeByContext(context);
		String cardNumbers = StringUtils.join(clientCardNumbers, SEPARATOR);
		String phoneNumbers = StringUtils.join(MobileBankManager.getPhonesByCardNumbers(clientCardNumbers), SEPARATOR);

		return new OnLoginSenderInitializationData(eventType, cardNumbers, phoneNumbers);
	}

	/**
	 * получение типа событи€ (определ€ем было ли подтверждение входа по одноразовому паролю)
	 * @param context - контекст аутентификации
	 * @return - собственно, тип событи€
	 * @throws SecurityDbException
	 */
	protected ClientDefinedEventType getEventTypeByContext(AuthenticationContext context) throws SecurityDbException
	{
		CommonLogin login = context.getLogin();
		AccessPolicyService service = new AccessPolicyService();
		Choice choice = context.getPolicy().getAuthenticationChoice();
		Properties properties = service.getProperties(login, context.getPolicy().getAccessType());
		String authPasswordType = properties.getProperty(choice.getProperty());

		if (authPasswordType.equals("lp"))
		{
			return ClientDefinedEventType.WITHOUT_OTP;
		}
		else
		{
			return ClientDefinedEventType.WITH_OTP;
		}
	}
}
