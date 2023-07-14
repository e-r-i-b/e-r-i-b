package com.rssl.auth.csa.back.protocol.handlers;

import com.rssl.auth.csa.back.Utils;
import com.rssl.auth.csa.back.protocol.ResponseInfo;
import com.rssl.auth.csa.back.servises.Profile;
import com.rssl.auth.csa.back.servises.nodes.CreateProfileNodeMode;
import org.xml.sax.SAXException;

/**
 * @author osminin
 * @ created 29.10.13
 * @ $Author$
 * @ $Revision$
 *
 * Базовый класс-обработчик запросов на получение информации о пользователе
 */
public abstract class GetUserInfoRequestProcessorBase extends RequestProcessorBase
{
	/**
	 * Создать ответ на запрос о получении информации о пользователе
	 * @param profile профиль
	 * @return информация об ответе
	 * @throws SAXException
	 */
	protected ResponseInfo createUserAndNodeInfoRs(Profile profile) throws Exception
	{
		return getSuccessResponseBuilder()
				.addUserInfo(profile)
				.addNodeInfo(Utils.getActiveProfileNode(profile.getId(), CreateProfileNodeMode.CREATION_ALLOWED_FOR_ALL_NODES).getNode())
				.end().getResponceInfo();
	}
}
