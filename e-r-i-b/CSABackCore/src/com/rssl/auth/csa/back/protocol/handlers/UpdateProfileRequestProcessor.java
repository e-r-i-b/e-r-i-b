package com.rssl.auth.csa.back.protocol.handlers;

import com.rssl.auth.csa.back.CSAUserInfo;
import com.rssl.auth.csa.back.Utils;
import com.rssl.auth.csa.back.protocol.Constants;
import com.rssl.auth.csa.back.protocol.RequestInfo;
import com.rssl.auth.csa.back.protocol.ResponseInfo;
import com.rssl.auth.csa.back.servises.Profile;
import com.rssl.auth.csa.back.servises.nodes.CreateProfileNodeMode;
import com.rssl.auth.csa.back.servises.nodes.ProfileNode;
import com.rssl.phizic.utils.xml.XmlHelper;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * @author osminin
 * @ created 20.10.13
 * @ $Author$
 * @ $Revision$
 *
 * Обработчик запроса на обновление профиля
 *
 * Параметры запроса:
 * newUserInfo	                    Новая информация пользователе                               [1]
 *      firstname                   Имя пользователя                                            [1]
 *      patrname                    Отчество пользователя                                       [0..1]
 *      surname                     Фамилия пользователя                                        [1]
 *      birthdate                   Дата рождения пользователя                                  [1]
 *      passport                    ДУЛ пользователя                                            [1]
 *      cbCode                      Подразделение пользователя                                  [1]
 * oldUserInfo	                    Стараая информация о пользователе                           [1]
 *      firstname                   Имя пользователя                                            [1]
 *      patrname                    Отчество пользователя                                       [0..1]
 *      surname                     Фамилия пользователя                                        [1]
 *      birthdate                   Дата рождения пользователя                                  [1]
 *      passport                    ДУЛ пользователя                                            [1]
 *      cbCode                      Подразделение пользователя                                  [1]
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
public class UpdateProfileRequestProcessor extends RequestProcessorBase
{
	private static final String RESPONSE_TYPE = "updateProfileRs";
	private static final String REQUEST_TYPE  = "updateProfileRq";

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
	protected ResponseInfo processRequest(RequestInfo requestInfo) throws Exception
	{
		Document document = requestInfo.getBody();
		Element element = document.getDocumentElement();
		Element newUserInfoElement = XmlHelper.selectSingleNode(element, Constants.NEW_USER_INFO_TAG);
		Element oldUserInfoElement = XmlHelper.selectSingleNode(element, Constants.OLD_USER_INFO_TAG);

		CSAUserInfo newUserInfo = fillUserInfo(newUserInfoElement);
		CSAUserInfo oldUserInfo = fillUserInfo(oldUserInfoElement);

		return doRequest(newUserInfo, oldUserInfo);
	}

	private ResponseInfo doRequest(CSAUserInfo newUserInfo, CSAUserInfo oldUserInfo) throws Exception
	{
		Profile profile = updateProfile(newUserInfo, oldUserInfo);
		ProfileNode profileNode = Utils.getActiveProfileNode(profile.getId(), CreateProfileNodeMode.CREATION_ALLOWED_FOR_ALL_NODES);

		return getSuccessResponseBuilder().addNodeInfo(profileNode).end();
	}

	private Profile updateProfile(CSAUserInfo newUserInfo, CSAUserInfo oldUserInfo) throws Exception
	{
		//Если пользователь после смены данных входил в ЕРИБ, то ЦСА профиль уже обновлен
		Profile newProfile = Profile.getByUserInfo(newUserInfo, true);
		if (newProfile == null)
		{
			//Иначе обновляем профиль
			Profile oldProfile = Profile.getByUserInfo(oldUserInfo, true);
			oldProfile.update(newUserInfo);
			return oldProfile;
		}
		return newProfile;
	}
}
