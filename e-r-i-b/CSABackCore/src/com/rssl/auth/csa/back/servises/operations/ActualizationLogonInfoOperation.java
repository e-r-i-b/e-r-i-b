package com.rssl.auth.csa.back.servises.operations;

import com.rssl.auth.csa.back.exceptions.AuthentificationReguiredException;
import com.rssl.auth.csa.back.servises.Connector;
import com.rssl.auth.csa.back.servises.Operation;
import com.rssl.auth.csa.back.servises.Profile;
import com.rssl.auth.csa.back.servises.connectors.CSAConnector;
import com.rssl.auth.csa.back.servises.connectors.MAPIConnector;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;

import java.util.ArrayList;
import java.util.List;

/**
 * @author krenev
 * @ created 11.01.2013
 * @ $Author$
 * @ $Revision$
 * ќпераци€ актуализации информации о входе.
 */
public class ActualizationLogonInfoOperation extends Operation
{
	private static final String CONNETORS_COUNT_PARAM = "connetorsCount";
	private static final String CONNECTOR_GUID_PARAM = "connetor_";
	private static final String AUTH_CONNECTOR_PARAM = "authConnecotrUID";

	public ActualizationLogonInfoOperation() {}

	public ActualizationLogonInfoOperation(IdentificationContext identificationContext)
	{
		super(identificationContext);
	}

	/**
	 * проинициализировать операцию
	 * @param connectors список коннекторов.
	 * @param authConnectorUID идентфикатор коннектор, по которому проведена аутентификаци€.
	 * @throws Exception
	 */
	public void initialize(final List<CSAConnector> connectors, final String authConnectorUID) throws Exception
	{
		initialize(new HibernateAction<Void>()
		{
			public Void run(org.hibernate.Session session) throws Exception
			{
				setAuthConnectorUID(authConnectorUID);
				addParameter(CONNETORS_COUNT_PARAM, connectors.size());
				int i = 0;
				for (CSAConnector connector : connectors)
				{
					addParameter(CONNECTOR_GUID_PARAM + i++, connector.getGuid());
				}
				return null;
			}
		});
	}

	/**
	 * @return список коннекторов дл€ выбора из них.
	 */
	public List<CSAConnector> getConnectors() throws Exception
	{
		int count = Integer.parseInt(getParameter(CONNETORS_COUNT_PARAM));
		List<CSAConnector> result = new ArrayList<CSAConnector>();
		for (int i = 0; i < count; i++)
		{
			result.add((CSAConnector) CSAConnector.findByGUID(getParameter(CONNECTOR_GUID_PARAM + i)));
		}
		return result;
	}

	private void setAuthConnectorUID(String authConnectorUID)
	{
		addParameter(AUTH_CONNECTOR_PARAM, authConnectorUID);
	}

	private String getAuthConnecotUID()
	{
		return getParameter(AUTH_CONNECTOR_PARAM);
	}

	/**
	 * ”бить лишние коннекторы входа, оставив переданный в параметре.
	 * @param guid идентфикатор коннектора который требуетс€ оставить.
	 * @return коннектор, который был оставлен.
	 */
	public Connector execute(final String guid) throws Exception
	{
		Connector connector = execute(new HibernateAction<Connector>()
		{
			public Connector run(org.hibernate.Session session) throws Exception
			{
				Profile profile = lockProfile();
				log.trace("ѕолучаем список коннекторов дл€ актуализации");
				List<CSAConnector> connectors = getConnectors();
				Connector newestConnector = null; //наиболее новый коннектор
				for (CSAConnector connector : connectors)
				{
					if (newestConnector == null || newestConnector.getCreationDate().before(connector.getCreationDate()))
					{
						//»щем самый свежий коннектор.
						newestConnector = connector;
					}
					if (connector.getGuid().equals(guid))
					{
						continue; // пропускаем коннектор, который требуетс€ оставить.
					}
					//ќбнул€ем уровни безопаснсти, выставленные коннекторам при сли€ние профилей
					connector.setSecurityType(null);
					log.info("«акрыт коннекор " + connector.getGuid() + " при актуализации информации о входе ");
					connector.close();
				}
				//ќбнул€ем уровни безопаснсти, выставленные коннекторам при сли€ние профилей
				MAPIConnector.setSecurityTypeToNotClosed(profile.getId(), null);

				if (newestConnector.getGuid().equals(guid))
				{
					//уровень безопасности выбранного коннектора присваиваем профилю
					profile.setSecurityType(newestConnector.calcSecurityType());
					profile.save();
					//обнул€ем уровень безопасности коннектора
					newestConnector.setSecurityType(null);
					newestConnector.save();
					return newestConnector; //если выбран самый новый коннектор, его и возвращаем.
				}
				log.info("”станавливаем дл€ коннектора " + guid + " параметры карты от коннектора " + newestConnector.getGuid());
				Connector connector = Connector.findByGUID(guid);
				connector.changeCardInfo(newestConnector);
				//уровень безопасности выбранного коннектора присваиваем профилю
				profile.setSecurityType(connector.calcSecurityType());
				profile.save();
				//обнул€ем уровень безопасности коннектора
				connector.setSecurityType(null);
				connector.save();
				return connector;
			}
		});
		//ѕровер€ем по этому ли коннектору клиент аутентифицировалс€.
		if (!connector.getGuid().equals(getAuthConnecotUID()))
		{
			//Ќет. ѕусть вводит логин пароль от выбранного коннектора.
			throw new AuthentificationReguiredException();
		}
		return connector;
	}
}
