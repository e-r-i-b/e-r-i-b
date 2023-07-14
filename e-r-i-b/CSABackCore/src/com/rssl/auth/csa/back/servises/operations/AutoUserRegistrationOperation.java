package com.rssl.auth.csa.back.servises.operations;

import com.rssl.auth.csa.back.servises.*;
import com.rssl.auth.csa.back.servises.connectors.CSAConnector;
import com.rssl.auth.csa.back.servises.connectors.DisposableConnector;
import com.rssl.phizic.common.types.exceptions.LogicException;
import com.rssl.phizic.common.types.registration.RegistrationType;
import com.rssl.phizic.common.types.security.SecurityType;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import org.apache.commons.collections.CollectionUtils;

import java.util.List;

/**
 * Операция авторегистрации клиента в новой цса, в случае если он зашел под алиасом старой цса
 * @author niculichev
 * @ created 06.02.14
 * @ $Author$
 * @ $Revision$
 */
public class AutoUserRegistrationOperation extends Operation
{
	private static final String OLD_CONNECTOR = "old-connector";

	public AutoUserRegistrationOperation(){}

	public AutoUserRegistrationOperation(IdentificationContext identificationContext)
	{
		super(identificationContext);
	}

	public void initialize(final Connector connector) throws Exception
	{
		initialize(new HibernateAction<Void>()
		{
			public Void run(org.hibernate.Session session) throws Exception
			{
				setOldConnectorGUID(connector.getGuid());
				return null;
			}
		});
	}

	public Connector execute(final String password) throws Exception
	{
		return execute(new HibernateAction<Connector>()
		{
			public Connector run(org.hibernate.Session session) throws Exception
			{
				// лочим профиль, чтобы синхронизировать проверки
				Profile profile = lockProfile();

				// если есть цса коннекторы, значит ничего делать не нужно
				List<CSAConnector> csaConnectors = CSAConnector.findNotClosedByProfileID(profile.getId());
				if (CollectionUtils.isNotEmpty(csaConnectors))
					throw new LogicException("Для профиля уже существуют цса коннекторы c id = " + csaConnectors.get(0).getGuid());

				Connector connector = findConnectorByGuid(getOldConnectorGUID(), null);
				// ищем логин для коннектора
				Login oldLogin = Login.findLoginForConnector(connector.getId());
				// дезактивируем старый коннектор
				connector.close();

				// иначе создаем новый цса коннектор с введенным алиасом и паролем
				CSAConnector result = new CSAConnector(connector.getUserId(), connector.getCbCode(), connector.getCardNumber(), profile, RegistrationType.AUTO);
				result.save();
				result.setPassword(password);
				oldLogin.changeConnector(result.getId());

				// при автоматической регистрации клиенту присваивается низкий уровень безопасности
				profile.setSecurityType(SecurityType.LOW);
				profile.save();

				// создание нового коннектора равносильно сбросу временных коннекторов
				DisposableConnector activeDisposableConnector = DisposableConnector.findNotClosedByUserId(connector.getUserId());
				if (activeDisposableConnector != null)
					activeDisposableConnector.close();

				return result;
			}
		});
	}

	private void setOldConnectorGUID(String guid)
	{
		addParameter(OLD_CONNECTOR, guid);
	}

	private String getOldConnectorGUID()
	{
		return getParameter(OLD_CONNECTOR);
	}
}
