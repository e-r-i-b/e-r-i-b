package com.rssl.phizic.business.profileSynchronization;

import com.rssl.phizic.auth.modes.AuthenticationContext;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.utils.DateHelper;
import com.rssl.phizic.utils.xml.XmlEntityBuilder;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import static com.rssl.phizic.common.types.limits.Constants.*;
import static com.rssl.phizic.common.types.limits.Constants.PROFILE_INFO_TAG;

/**
 * @author lepihina
 * @ created 23.05.14
 * $Author$
 * $Revision$
 * Построение сообщений jms для обмена с в централизованным хранилищем
 */
public class PersonSettingsJMSBuilder
{
	/**
	 * Строит сообщение для сохранения данных профиля клиента
	 * @param informationType - тип даных
	 * @param data - сериализованные данные
	 * @param context - информация о профиле клиента
	 * @return сообщение
	 * @throws com.rssl.phizic.business.BusinessException
	 */
	public static String buildSavePersonSettingsMessage(String informationType, String data, AuthenticationContext context) throws BusinessException
	{
		XmlEntityBuilder builder = new XmlEntityBuilder();
		builder.openEntityTag(SAVE_PERSON_SETTINGS_REQUEST_NAME);
		builder.append(buildProfileInfo(context));
		builder.createEntityTag(PERSON_SETTING_INFORMATION_TYPE_TAG, informationType);
		builder.createEntityTag(PERSON_SETTING_DATA_TAG, data);
		builder.closeEntityTag(SAVE_PERSON_SETTINGS_REQUEST_NAME);

		return builder.toString();
	}

	private static String buildProfileInfo(AuthenticationContext context) throws BusinessException
	{
		XmlEntityBuilder builder = new XmlEntityBuilder();
		builder.openEntityTag(PROFILE_INFO_TAG);
		builder.createEntityTag(FIRST_NAME_TAG, context.getFirstName());
		builder.createEntityTag(SUR_NAME_TAG, context.getLastName());
		builder.createEntityTag(PATR_NAME_TAG, context.getMiddleName());
		builder.createEntityTag(PASSPORT_NAME_TAG, context.getDocumentNumber());
		builder.createEntityTag(BIRTH_DATE_TAG, DateHelper.getXmlDateTimeFormat(getBirthDateFromString(context.getBirthDate()).getTime()));
		builder.createEntityTag(TB_TAG, context.getTB());
		builder.closeEntityTag(PROFILE_INFO_TAG);

		return builder.toString();
	}

	private static Calendar getBirthDateFromString(String birthDateStr) throws BusinessException
	{
		Calendar birthDate = Calendar.getInstance();
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		try
		{
			birthDate.setTime(dateFormat.parse(birthDateStr));
			return birthDate;
		}
		catch (ParseException e)
		{
			throw new BusinessException("Ошибка при преобразовании даты рождения клиента", e);
		}
	}
}
