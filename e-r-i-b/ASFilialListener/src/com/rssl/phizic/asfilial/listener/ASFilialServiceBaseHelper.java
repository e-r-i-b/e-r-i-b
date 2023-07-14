package com.rssl.phizic.asfilial.listener;

import com.rssl.phizic.gate.csa.UserInfo;
import com.rssl.phizic.asfilial.listener.generated.IdentityCardType;
import com.rssl.phizic.asfilial.listener.generated.IdentityType;
import com.rssl.phizic.asfilial.listener.generated.StatusType;
import com.rssl.phizic.business.documents.DocumentHelper;
import com.rssl.phizic.business.ermb.migration.MigrationHelper;
import com.rssl.phizic.business.ermb.migration.list.task.migration.asfilial.ASFilialReturnCode;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.utils.DateHelper;
import com.rssl.phizic.utils.StringHelper;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

/**
 * @author gladishev
 * @ created 17.12.13
 * @ $Author$
 * @ $Revision$
 *
 * Базовый хелпер используется для формирования ответов в АС Филиал
 */
public class ASFilialServiceBaseHelper extends MigrationHelper
{
	protected static final Map<ASFilialReturnCode,String> codeDescription = new HashMap<ASFilialReturnCode,String>();
    static
    {
	    codeDescription.put(ASFilialReturnCode.BUSINES_ERROR, "Бизнес ошибка");
	    codeDescription.put(ASFilialReturnCode.TECHNICAL_ERROR, "Техническая ошибка");
	    codeDescription.put(ASFilialReturnCode.PROFILE_NOT_FOUND, "Профиль не найден");
	    codeDescription.put(ASFilialReturnCode.FORMAT_ERROR, "Ошибка формата");
	    codeDescription.put(ASFilialReturnCode.CONFIRM_HOLDER_ERR, "Для номера %s был указан не верный код подтверждения держателя номера.");
	    codeDescription.put(ASFilialReturnCode.DUPLICATION_PHONE_ERR, "номер телефона  занят: один или несколько указанных телефонов зарегистрированы на других лиц");
	    codeDescription.put(ASFilialReturnCode.MB_NOT_CONNECT, "услуга МБ не подключена");
	    codeDescription.put(ASFilialReturnCode.MB_HAVE_THIRD_PARTIES_ACCOUNTS, "Владельцу дополнительной карты необходимо самостоятельно подключить Мобильный Банк в отделении.");
	    codeDescription.put(ASFilialReturnCode.DEPARTMENT_NOT_FOUND, "Подразделение не найдено");
    }

	protected static final Log log = PhizICLogFactory.getLog(Constants.LOG_MODULE_CORE);

	/**
	 * Установить информацию о выполнении запроса.
	 * @param status Информация о выполнении запроса.
	 * @param statusCode  Код возврата.
	 */
	public void setStatus(StatusType status, ASFilialReturnCode statusCode)
	{
		setStatus(status, null, statusCode);
	}

	/**
     * Установить информацию о выполнении запроса.
     * @param status Информация о выполнении запроса.
     * @param logMsg Сообщвения в лог.
     * @param statusCode  Код возврата.
     */
    public void setStatus(StatusType status, Object logMsg, ASFilialReturnCode statusCode)
    {
        setStatus(status,logMsg,statusCode,null);
    }

    /**
     * Установить информацию о выполнении запроса.
     * @param status Информация о выполнении запроса
     * @param logMsg Сообщвения в лог.
     * @param statusCode Код возврата.
     * @param replaceStr Срока замены.
     */
    public void setStatus(StatusType status, Object logMsg, ASFilialReturnCode statusCode,String replaceStr)
    {
        String codeDesc = codeDescription.get(statusCode);

        if (!StringHelper.isEmpty(replaceStr))
            codeDesc = String.format(codeDesc,replaceStr);

        setStatus(status,logMsg,statusCode.toValue(),codeDesc);
    }


    /**
     * Установить информацию о выполнении запроса.
     * @param status Информация о выполнении запроса.
     * @param logMsg Сообщвения в лог.
     * @param code Код возврата.
     * @param codeDesc Описание кода возврата.
     */
    public void setStatus(StatusType status, Object logMsg,Long code ,String codeDesc)
    {
	    if (logMsg != null)
			log.error(logMsg);

        status.setStatusDesc(codeDesc);
        status.setStatusCode(code);
    }

    /**
     * Установить информацию о выполнении запроса.
     * @param status  Информация о выполнении запроса
     * @param clientInd Идентификационные данные клиента
     */
    public void setClientNotFoundMsg(StatusType status,IdentityType clientInd)
    {
        String patrName = StringHelper.getNullIfEmpty(clientInd.getMiddleName());
        String surName = clientInd.getLastName();
        String firstName = clientInd.getFirstName();
        IdentityCardType docInd = clientInd.getIdentityCard();
        String docSeries = StringHelper.getNullIfEmpty(docInd.getIdSeries());
        String docNumber = docInd.getIdNum();
        String tb = clientInd.getRegionId();
        Calendar birthDate = DateHelper.toCalendar(clientInd.getBirthday());
        setStatus(status, "Клиент не найден в системе ФИО:" + surName + " " + firstName + " " + patrName + ",ДР: " + DateHelper.formatDateToStringWithPoint(birthDate) + ", Серия и номер документа: " + docSeries + " " + docNumber, ASFilialReturnCode.PROFILE_NOT_FOUND);
    }

	/**
	 * построить информацию о клиенте для запроса в цса
	 * @param clientInd  Идентификационные данные клиента
	 * @return информация о пользователе
	 */
	public UserInfo createCsaUserInfo(IdentityType clientInd)
	{
		IdentityCardType identityCard = clientInd.getIdentityCard();
		String passport = DocumentHelper.getPassportWayNumber(identityCard.getIdSeries(), identityCard.getIdNum());
		Calendar birthday = DateHelper.toCalendar(clientInd.getBirthday());
		return new UserInfo(clientInd.getRegionId(), clientInd.getFirstName(), clientInd.getLastName(), clientInd.getMiddleName(), passport, birthday);
	}

	/**
	 * Удалить лидирующие нули в поле ТБ, добавленные по спецификации
	 * @param clientInd идентификационные данные клиента
	 */
	public void removeTbLeadingZeros(IdentityType clientInd)
	{
		clientInd.setRegionId(StringHelper.removeLeadingZeros(clientInd.getRegionId()));
	}
}
