package com.rssl.auth.csa.back.protocol.handlers;

import com.rssl.auth.csa.back.CSAUserInfo;
import com.rssl.auth.csa.back.Utils;
import com.rssl.auth.csa.back.protocol.Constants;
import com.rssl.auth.csa.back.protocol.RequestInfo;
import com.rssl.auth.csa.back.protocol.ResponseInfo;
import com.rssl.auth.csa.back.servises.Profile;
import com.rssl.auth.csa.back.servises.connectors.MAPIConnector;
import com.rssl.auth.csa.back.servises.nodes.CreateProfileNodeMode;
import com.rssl.auth.csa.back.servises.nodes.Node;
import com.rssl.auth.csa.back.servises.nodes.ProfileNode;
import com.rssl.phizic.utils.DateHelper;
import org.apache.commons.collections.CollectionUtils;
import org.w3c.dom.Document;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author osminin
 * @ created 10.12.14
 * @ $Author$
 * @ $Revision$
 *
 * Обработчик запроса на получение информации о получателе запроса на сбор средств
 *
 * Параметры запроса:
 * firstname               Имя пользователя                                            [1]
 * patrname                Отчество пользователя                                       [1]
 * surname                 Фамилия пользователя                                        [1]
 * birthdate               Дата рождения пользователя                                  [1]
 * passport                ДУЛ пользователя                                            [1]
 * cbCode                  Подразделение пользователя                                  [1]
 *
 */
public class FindFundSenderInfoRequestProcessor extends RequestProcessorBase
{
	private static final String RESPONSE_TYPE   = "findFundSenderInfoRs";
	private static final String REQUEST_TYPE    = "findFundSenderInfoRq";

	@Override
	protected String getResponceType()
	{
		return RESPONSE_TYPE;
	}

	@Override
	protected String getRequestType()
	{
		return REQUEST_TYPE;
	}

	@Override
	public boolean isAccessStandIn()
	{
		return true;
	}

	@Override
	protected ResponseInfo processRequest(RequestInfo requestInfo) throws Exception
	{
		Document document = requestInfo.getBody();
		CSAUserInfo userInfo = fillUserInfo(document.getDocumentElement());
		//ищем профили по ФИО+ДУЛ+ДР, Выбираем все активные mAPI Full коннекторы найденных клиентов.
		//Все активные mAPI
		List<MAPIConnector> allConnectors = MAPIConnector.findNotClosedByClient(userInfo);
		if (CollectionUtils.isEmpty(allConnectors))
		{
			return getFailureResponseBuilder().buildUserNotFoundResponse(userInfo);
		}
		//Full коннекторы
		List<MAPIConnector> fullConnectors = new ArrayList<MAPIConnector>();
		for (MAPIConnector connector : allConnectors)
		{
			if (connector.getDeviceState().equals("FULL"))
				fullConnectors.add(connector);
		}

		//Если коннекторов нет – клиент не найден
		if (fullConnectors.isEmpty())
			return getFailureResponseBuilder().buildUserNotFoundResponse(userInfo);

		List<MAPIConnector> pushSupportedConnectors = new ArrayList<MAPIConnector>();
		//Если есть, то выбираем по алгоритму:
		//Выбираем те, где подключены push
		for (MAPIConnector connector : fullConnectors)
		{
			if (connector.getPushSupported())
			{
				pushSupportedConnectors.add(connector);
			}
		}

		try
		{
			//Если ни одного – то первый попавшийся (например, с последней датой входа) для отправки СМС через push сервер.
			if (pushSupportedConnectors.isEmpty())
			{
				return fillResponse(allConnectors, getMaxEnteredDateProfile(fullConnectors));
			}
			//Если один – возвращаем в блок для отправки push.
			else if (pushSupportedConnectors.size() == 1)
			{
				return fillResponse(allConnectors, pushSupportedConnectors.get(0).getProfile());
			}
			//Если несколько:
			else
			{
				List<MAPIConnector> sameTbConnectors = new ArrayList<MAPIConnector>();
				List<MAPIConnector> otherTbConnectors = new ArrayList<MAPIConnector>();
				for (MAPIConnector connector : pushSupportedConnectors)
				{
					//Выбираем из ТБ отправителя запроса:
					if (connector.getProfile().getTb().equals(userInfo.getCbCode()))
					{
						sameTbConnectors.add(connector);
					}
					//другие ТБ
					otherTbConnectors.add(connector);
				}
				//Если ни одного – выбираем по остальным ТБ:
				if (sameTbConnectors.isEmpty())
				{
					//Если один – возвращаем в блок для отправки push.
					if (otherTbConnectors.size() == 1)
					{
						return fillResponse(allConnectors, otherTbConnectors.get(0).getProfile());
					}
					//Если несколько – выбираем с максимальной датой входа и возвращаем в блок для отправки push.
					else
					{
						return fillResponse(allConnectors, getMaxEnteredDateProfile(otherTbConnectors));
					}
				}
				//Если один – возвращаем в блок для отправки push
				else if (sameTbConnectors.size() == 1)
				{
					return fillResponse(allConnectors, sameTbConnectors.get(0).getProfile());
				}
				//Если несколько – выбираем с максимальной датой входа и возвращаем в блок для отправки push.
				else
				{
					return fillResponse(allConnectors, getMaxEnteredDateProfile(sameTbConnectors));
				}
			}
		}
		catch (ProfileNodeNotFoundException ignore)
		{
			return getFailureResponseBuilder().buildUserNotFoundResponse(userInfo);
		}
	}

	private Node getProfileNode(Profile profile) throws Exception
	{
		ProfileNode profileNode = Utils.getActiveProfileNode(profile.getId(), CreateProfileNodeMode.CREATION_DENIED);
		if (profileNode == null)
		{
			throw new ProfileNodeNotFoundException();
		}
		return profileNode.getNode();
	}

	/**
	 * Поиск профиля с коннектором где максимальная дата входа.
	 * @param connectors - коннекторы
	 * @return профиль клиента
	 */
	private Profile getMaxEnteredDateProfile(List<MAPIConnector> connectors)
	{
		if (CollectionUtils.isEmpty(connectors))
			return null;
		MAPIConnector actualConnector = connectors.get(0);
		for (int i = 0; i < connectors.size(); i++)
		{
			if (DateHelper.nullSafeCompare(actualConnector.getCurrentSessionDate(), connectors.get(i).getCurrentSessionDate()) < 0)
				actualConnector = connectors.get(i);
		}
		return actualConnector.getProfile();
	}

	/**
	 * Фильтрация всех коннекторов по текущему профилю из списка.
	 * @param connectorList - список коннекторов
	 * @param profile - профиль
	 */
	private static void filterConnectorsByProfile(List<MAPIConnector> connectorList, Profile profile)
	{
		for (Iterator<MAPIConnector> iter = connectorList.iterator(); iter.hasNext(); )
		{
			MAPIConnector connector = iter.next();
			if (!connector.getProfile().equals(profile))
			{
				iter.remove();
			}
		}
	}

	private ResponseInfo fillResponse(List<MAPIConnector> connectors, Profile actualProfile) throws Exception
	{
		filterConnectorsByProfile(connectors, actualProfile);
		return getSuccessResponseBuilder()
				.addConnectorsInfo(connectors)
				.addParameter(Constants.NODE_INFO_ID_NODE_NAME, getProfileNode(actualProfile).getId())
				.addParameter(Constants.TB_TAG, actualProfile.getTb())
				.end();
	}

	private class ProfileNodeNotFoundException extends Exception
	{
	}
}
