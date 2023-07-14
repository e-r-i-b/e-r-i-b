package com.rssl.auth.csa.back.protocol.handlers;

import com.rssl.auth.csa.back.CSAUserInfo;
import com.rssl.auth.csa.back.Utils;
import com.rssl.auth.csa.back.protocol.CSAUpdateProfileInfo;
import com.rssl.auth.csa.back.protocol.Constants;
import com.rssl.auth.csa.back.protocol.RequestInfo;
import com.rssl.auth.csa.back.protocol.ResponseInfo;
import com.rssl.auth.csa.back.servises.Profile;
import com.rssl.auth.csa.back.servises.ProfileHistory;
import com.rssl.auth.csa.back.servises.nodes.CreateProfileNodeMode;
import com.rssl.phizic.utils.xml.ForeachElementAction;
import com.rssl.phizic.utils.xml.XmlHelper;
import org.hibernate.LockMode;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author osminin
 * @ created 05.11.13
 * @ $Author$
 * @ $Revision$
 *
 * Обработчик запроса на получение списка блоков по списку обновленных профилей
 * если в запросе пришла история изменений информации о пользователе, проверяем полное соответствие ФИО ДУЛ ДР ТБ
 * в ответе отправляем информацию о блоке и пользователе, что бы фронт мог определить, какому пользователю какой блок соответствует
 *
 * Параметры запроса:
 * updateProfileInfo            Информация об обновленном профиле                           [1-n]
 *   newUserInfo	            Новая информация пользователе                               [1]
 *      firstname               Имя пользователя                                            [1]
 *      patrname                Отчество пользователя                                       [0..1]
 *      surname                 Фамилия пользователя                                        [1]
 *      birthdate               Дата рождения пользователя                                  [1]
 *      passport                ДУЛ пользователя                                            [1]
 *      cbCode                  Подразделение пользователя                                  [1]
 *   oldUserInfoList            Список истории о пользовтеле                                [0-1]
 *      oldUserInfo	            Стараая информация о пользователе                           [1-n]
 *          firstname           Имя пользователя                                            [1]
 *          patrname            Отчество пользователя                                       [0..1]
 *          surname             Фамилия пользователя                                        [1]
 *          birthdate           Дата рождения пользователя                                  [1]
 *          passport            ДУЛ пользователя                                            [1]
 *          cbCode              Подразделение пользователя                                  [1]
 *
 * Параметры ответа:
 * updateProfileInfo                Информация об обновленном профиле                           [1-n]
 *   nodeInfo                       Информация об узле                                          [1]
 *      host                        хост                                                        [1]
 *      smsQueueName                наименование очереди смс канала                             [1]
 *      smsFactoryName              наименование фабрики смс канала                             [1]
 *      serviceProfileQueueName     наименование очереди служебного канала (ConfirmProfilesRq)  [1]
 *      serviceProfileFactoryName   наименование фабрики служебного канала (ConfirmProfilesRq)  [1]
 *      serviceClientQueueName      наименование очереди служебного канала (UpdateClientRq)     [1]
 *      serviceClientFactoryName    наименование фабрики служебного канала (UpdateClientRq)     [1]
 *      serviceResourceQueueName    наименование очереди служебного канала (UpdateResourceRq)   [1]
 *      serviceResourceFactoryName  наименование фабрики служебного канала (UpdateResourceRq)   [1]
 *   UserInfo	                    Информация о пользователе                                   [1]
 *      firstname                   Имя пользователя                                            [1]
 *      patrname                    Отчество пользователя                                       [0..1]
 *      surname                     Фамилия пользователя                                        [1]
 *      birthdate                   Дата рождения пользователя                                  [1]
 *      passport                    ДУЛ пользователя                                            [1]
 *      tb                          Подразделение пользователя
 *
 */
public class FindNodesByUpdatedProfilesRequestProcessor extends RequestProcessorBase
{
	private static final String RESPONSE_TYPE = "findNodesByUpdatedProfilesRs";
	private static final String REQUEST_TYPE  = "findNodesByUpdatedProfilesRq";

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

		ResponseBuilderHelper builder = getSuccessResponseBuilder();
		for (CSAUpdateProfileInfo profileInfo : parseRequest(document.getDocumentElement()))
		{
			Profile profile = findProfile(profileInfo);
			if(profile != null)
			{
				builder.openTag(Constants.UPDATE_PROFILE_INFO_TAG);
				builder.addUserInfo(profile);
				builder.addNodeInfo(Utils.getActiveProfileNode(profile.getId(), CreateProfileNodeMode.CREATION_ALLOWED_FOR_ALL_NODES));
				builder.closeTag();
			}
		}

		return builder.end().getResponceInfo();
	}

	private Profile findProfile(CSAUpdateProfileInfo profileInfo) throws Exception
	{
		CSAUserInfo newUserInfo = profileInfo.getNewUserInfo();

		Profile profile = findByUserInfo(newUserInfo);
		if(profile != null)
			return profile;

		for(CSAUserInfo userInfo : profileInfo.getOldUserInfoList())
		{
			profile = findByUserInfo(userInfo);
			if(profile != null)
				return profile;
		}

		return null;
	}

	private Profile findByUserInfo(CSAUserInfo userInfo) throws Exception
	{
		Profile profile = Profile.getByUserInfo(userInfo, false);
		if(profile != null)
			return profile;

		ProfileHistory newProfileHistory = ProfileHistory.findHistoryByProfileTemplate(fillProfileTemplate(userInfo));
		if(newProfileHistory != null)
			return Profile.findById(newProfileHistory.getProfileId(), LockMode.NONE);

		return null;
	}

	private List<CSAUpdateProfileInfo> parseRequest(Element root) throws Exception
	{
		final List<CSAUpdateProfileInfo> updateProfileInfoList = new ArrayList<CSAUpdateProfileInfo>();
		XmlHelper.foreach(root, Constants.UPDATE_PROFILE_INFO_TAG, new ForeachElementAction()
		{
			public void execute(Element element) throws Exception
			{
				CSAUserInfo newUserInfo = fillUserInfo(XmlHelper.selectSingleNode(element, Constants.NEW_USER_INFO_TAG));
				updateProfileInfoList.add(new CSAUpdateProfileInfo(newUserInfo, getOldUserInfoList(element)));
			}
		});
		return updateProfileInfoList;
	}

	private List<CSAUserInfo> getOldUserInfoList(Element profileInfo) throws Exception
	{
		Element oldUserInfoListElement = XmlHelper.selectSingleNode(profileInfo, Constants.OLD_USER_INFO_LIST_TAG);
		if (oldUserInfoListElement != null)
		{
			final List<CSAUserInfo> oldUserInfoList = new ArrayList<CSAUserInfo>();
			XmlHelper.foreach(oldUserInfoListElement, Constants.OLD_USER_INFO_TAG, new ForeachElementAction()
			{
				public void execute(Element element) throws Exception
				{
					oldUserInfoList.add(fillUserInfo(element));
				}
			});
			return oldUserInfoList;
		}
		return Collections.emptyList();
	}
}
