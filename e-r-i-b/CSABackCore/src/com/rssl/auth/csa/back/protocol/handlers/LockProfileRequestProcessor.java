package com.rssl.auth.csa.back.protocol.handlers;

import com.rssl.auth.csa.back.protocol.Constants;
import com.rssl.auth.csa.back.protocol.LogableResponseInfo;
import com.rssl.auth.csa.back.protocol.RequestInfo;
import com.rssl.auth.csa.back.servises.ActiveRecord;
import com.rssl.auth.csa.back.servises.Profile;
import com.rssl.auth.csa.back.servises.operations.IdentificationContext;
import com.rssl.auth.csa.back.servises.operations.LogIdentificationContext;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.utils.xml.XMLDatatypeHelper;
import com.rssl.phizic.utils.xml.XmlHelper;
import org.hibernate.Session;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.util.Calendar;

/**
 * @author krenev
 * @ created 12.10.2012
 * @ $Author$
 * @ $Revision$
 * Обработчик запроса на блокировку профиля.
 *
 * Пармерты запроса:
 * UserInfo                     Информация о пользователе                                   [1]
 *      firstname               Имя пользователя                                            [1]
 *      patrname                Отчество пользователя                                       [0..1]
 *      surname                 Фамилия пользователя                                        [1]
 *      birthdate               Дата рождения пользователя                                  [1]
 *      passport                ДУЛ пользователя                                            [1]
 *      tb                      ТБ пользователя                                             [1]
 * blockFrom                    Дата начала блокировки                                      [1]
 * blockTo                      Дата окончания блокировки(отсутсвует для бесскрочных)       [0..1]
 * reason                       Текстовое описание причины блокировки                       [1]
 * blockerFIO                   ФИО сотрудника, блокируюющего пользователя                  [1]
 *
 * Параметры ответа:
 *
 */
public class LockProfileRequestProcessor extends LogableProcessorBase
{
	private static final String REQUEST_TYPE = "lockProfileRq";
	private static final String RESPONCE_TYPE = "lockProfileRs";

	protected String getRequestType()
	{
		return REQUEST_TYPE;
	}

	protected String getResponceType()
	{
		return RESPONCE_TYPE;
	}

	@Override
	public boolean isAccessStandIn()
	{
		return true;
	}

	@Override
	protected LogableResponseInfo processRequest(RequestInfo requestInfo, IdentificationContext context) throws Exception
	{
		Document body = requestInfo.getBody();
		Element element = body.getDocumentElement();
		Calendar lockFrom = XMLDatatypeHelper.parseDateTime(XmlHelper.getSimpleElementValue(element, Constants.LOCK_FROM_TAG));
		String lockToStr = XmlHelper.getSimpleElementValue(element, Constants.LOCK_TO_TAG);
		Calendar lockTo = StringHelper.isEmpty(lockToStr) ? null : XMLDatatypeHelper.parseDateTime(lockToStr);
		String reason = XmlHelper.getSimpleElementValue(element, Constants.REASON_TAG);
		String blockerFIO = XmlHelper.getSimpleElementValue(element, Constants.LOCKER_FIO_TAG);

		return blockProfile(context.getProfile(), lockFrom, lockTo, reason, blockerFIO);
	}

	@Override
	protected LogIdentificationContext getIdentificationContext(RequestInfo requestInfo) throws Exception
	{
		Document body = requestInfo.getBody();
		Element element = body.getDocumentElement();
		Profile profileTemplate = fillProfileTemplate(element);
		return LogIdentificationContext.createByTemplateProfile(profileTemplate);
	}

	protected LogableResponseInfo blockProfile(final Profile profile, final Calendar blockFrom, final Calendar blockTo, final String reason, final String blockerFIO) throws Exception
	{
		return ActiveRecord.executeAtomic(new HibernateAction<LogableResponseInfo>()
		{
			public LogableResponseInfo run(Session session) throws Exception
			{
				profile.lock(blockFrom, blockTo, reason, blockerFIO);
				return new LogableResponseInfo(buildSuccessResponse());
			}
		});
	}
}