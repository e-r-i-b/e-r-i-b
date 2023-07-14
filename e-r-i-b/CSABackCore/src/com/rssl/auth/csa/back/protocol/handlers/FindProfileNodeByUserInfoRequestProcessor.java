package com.rssl.auth.csa.back.protocol.handlers;

import com.rssl.auth.csa.back.CSAUserInfo;
import com.rssl.auth.csa.back.Config;
import com.rssl.auth.csa.back.Utils;
import com.rssl.auth.csa.back.protocol.Constants;
import com.rssl.auth.csa.back.protocol.RequestInfo;
import com.rssl.auth.csa.back.protocol.ResponseInfo;
import com.rssl.auth.csa.back.servises.Profile;
import com.rssl.auth.csa.back.servises.nodes.CreateProfileNodeMode;
import com.rssl.auth.csa.back.servises.nodes.ProfileNode;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.utils.xml.XmlHelper;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * @author osminin
 * @ created 19.10.13
 * @ $Author$
 * @ $Revision$
 *
 * Обработчик запроса на получение информации о блоке пользователя по ФИО ДУЛ ДР ТБ
 *
 * Параметры запроса:
 * firstname               Имя пользователя                                            [1]
 * patrname                Отчество пользователя                                       [1]
 * surname                 Фамилия пользователя                                        [1]
 * birthdate               Дата рождения пользователя                                  [1]
 * passport                ДУЛ пользователя                                            [1]
 * cbCode                  Подразделение пользователя                                  [1]
 * needCreateProfile       Необходимо ли создавать профиль                             [1]
 * createProfileNodeMode   Тип с которым создается профиль                             [1]
 *
 * Параметры ответа:
 * nodeInfo                         Информация об узле                                          [1]
 *      host                        хост                                                        [1]
 *      smsQueueName                наименование очереди смс канала                             [1]
 *      smsFactoryName              наименование фабрики смс канала                             [1]
 *      serviceProfileQueueName     наименование очереди служебного канала (ConfirmProfilesRq)  [1]
 *      serviceProfileFactoryName   наименование фабрики служебного канала (ConfirmProfilesRq)  [1]
 *      serviceClientQueueName      наименование очереди служебного канала (UpdateClientRq)     [1]
 *      serviceClientFactoryName    наименование фабрики служебного канала (UpdateClientRq)     [1]
 *      serviceResourceQueueName    наименование очереди служебного канала (UpdateResourceRq)   [1]
 *      serviceResourceFactoryName  наименование фабрики служебного канала (UpdateResourceRq)   [1]
 *
 */
public class FindProfileNodeByUserInfoRequestProcessor extends RequestProcessorBase
{
	private static final String RESPONSE_TYPE   = "findProfileNodeByUserInfoRs";
	private static final String REQUEST_TYPE    = "findProfileNodeByUserInfoRq";

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
		Element element = document.getDocumentElement();
		boolean needCreateProfile = Boolean.parseBoolean(XmlHelper.getSimpleElementValue(element, Constants.NEED_CREATE_PROFILE));
		CreateProfileNodeMode createProfileNodeMode = CreateProfileNodeMode.valueOf(XmlHelper.getSimpleElementValue(element, Constants.CREATE_PROFILE_NODE_MODE));

		CSAUserInfo userInfo = fillUserInfo(element);
		userInfo.setCbCode(formatCbCode(userInfo.getCbCode()));
		Profile profile = Profile.getByUserInfo(userInfo, true);
		Config config = ConfigFactory.getConfig(Config.class);

		if (profile == null && (!needCreateProfile || config.isStandInMode()))
		{
			return getFailureResponseBuilder().buildUserNotFoundResponse(userInfo);
		}
		else if (profile == null)
		{
			profile = Profile.create(userInfo);
		}

		ProfileNode profileNode = Utils.getActiveProfileNode(profile.getId(), createProfileNodeMode);
		if (profileNode == null)
			return getFailureResponseBuilder().buildUserNotFoundResponse(userInfo);
		return getSuccessResponseBuilder().addNodeInfo(profileNode).end();
	}

	/**
	 * Форматирование кода тербанка.
	 * Возвращает 2 символа.
	 * Если код состоит из 1 символа, то метод дополняет до 2 нулем,
	 * если из 3 и более символов, то метод возвращает 2 символа справа,
	 * если из двух, то 2 символа
	 * @param cbCode код тербанка
	 * @return отформатированный код тербанка
	 */
	private String formatCbCode(String cbCode)
	{
		if (StringHelper.isNotEmpty(cbCode))
		{
			cbCode = StringHelper.addLeadingZeros(cbCode, 2);
			cbCode = cbCode.substring(cbCode.length() - 2);
		}
		return cbCode;
	}
}
