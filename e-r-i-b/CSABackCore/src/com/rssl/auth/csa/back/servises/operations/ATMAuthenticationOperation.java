package com.rssl.auth.csa.back.servises.operations;

import com.rssl.auth.csa.back.CSAUserInfo;
import com.rssl.auth.csa.back.Utils;
import com.rssl.auth.csa.back.exceptions.AuthenticationFailedException;
import com.rssl.auth.csa.back.exceptions.CardNotActiveAndMainException;
import com.rssl.auth.csa.back.integration.UserInfoProvider;
import com.rssl.auth.csa.back.servises.Connector;
import com.rssl.auth.csa.back.servises.ConnectorState;
import com.rssl.auth.csa.back.servises.Operation;
import com.rssl.auth.csa.back.servises.SyncUtil;
import com.rssl.auth.csa.back.servises.connectors.ATMConnector;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizic.gate.mobilebank.UserInfo;
import org.hibernate.LockMode;
import org.hibernate.Session;

/**
 * @author osminin
 * @ created 22.08.13
 * @ $Author$
 * @ $Revision$
 *
 * Операция аутентификации пользователя через терминал
 */
public class ATMAuthenticationOperation extends Operation
{
	private static final String USER_ID_PARAM = "userId";

	private String cardNumber;
	private String identityUserId; // userId, полученнный при идентификации

	/**
	 * ctor
	 */
	public ATMAuthenticationOperation() {}

	/**
	 * ctor
	 * @param identificationContext контекст идентификации
	 */
	public ATMAuthenticationOperation(IdentificationContext identificationContext)
	{
		super(identificationContext);
		this.cardNumber = identificationContext.getCardNumber();
		this.identityUserId = identificationContext.getUserId();
	}

	/**
	 * Ининциализация операции
	 * @throws Exception
	 */
	public void initialize() throws Exception
	{
		final String userId = receiveUserId();
		initialize(new HibernateAction<Object>()
		{
			public Object run(Session session) throws Exception
			{
				setUserId(userId);
				return null;
			}
		});
	}

	private String receiveUserId() throws Exception
	{
		try
		{
			UserInfo userInfo = UserInfoProvider.getInstance().getUserInfoByCardNumber(cardNumber);
			if (!(userInfo.isActiveCard() && userInfo.isMainCard()))
			{
				throw new CardNotActiveAndMainException("Аутентификация возможна только по активной и основной карте");
			}

			return userInfo.getUserId();
		}
		catch (CardNotActiveAndMainException e)
		{
			throw e;
		}
		catch (Exception e)
		{
			log.warn("Ошибка получения информации о пользователе по номеру карты " + Utils.maskCard(cardNumber), e);
			// не смогли получить, берем тот, что нашли при идентификации
			return identityUserId;
		}
	}

	/**
	 * Исполнить. Если коннектор не найдет, создаем новый
	 * @return коннектор
	 * @throws Exception
	 */
	public Connector execute() throws Exception
	{
		return execute(new HibernateAction<Connector>()
		{
			public Connector run(Session session) throws Exception
			{
				Connector connector = ATMConnector.findByCardNumber(cardNumber, LockMode.UPGRADE_NOWAIT);

				if (connector == null)
				{
					Connector newConnector = new ATMConnector(cardNumber, getUserId(), getCbCode(), getProfile());
					newConnector.save();

					return newConnector;
				}

				if (ConnectorState.ACTIVE != connector.getState())
				{
					throw new AuthenticationFailedException(connector);
				}

				return connector;
			}
		});
	}

	private String getUserId()
	{
		return getParameter(USER_ID_PARAM);
	}

	private void setUserId(String userId)
	{
		addParameter(USER_ID_PARAM, userId);
	}
}
