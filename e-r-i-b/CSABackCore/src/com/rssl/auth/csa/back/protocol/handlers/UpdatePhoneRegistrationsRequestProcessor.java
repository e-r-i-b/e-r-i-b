package com.rssl.auth.csa.back.protocol.handlers;

import com.rssl.auth.csa.back.CSAUserInfo;
import com.rssl.auth.csa.back.exceptions.ActivatePhoneRegistrationException;
import com.rssl.auth.csa.back.exceptions.DuplicatePhoneRegistrationsException;
import com.rssl.auth.csa.back.protocol.Constants;
import com.rssl.auth.csa.back.protocol.RequestInfo;
import com.rssl.auth.csa.back.protocol.ResponseInfo;
import com.rssl.auth.csa.back.servises.ConnectorState;
import com.rssl.auth.csa.back.servises.connectors.ERMBConnector;
import com.rssl.auth.csa.back.servises.operations.IdentificationContext;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizic.dataaccess.hibernate.HibernateExecutor;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.utils.xml.XmlHelper;
import org.apache.commons.collections.CollectionUtils;
import org.hibernate.Session;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.util.Iterator;
import java.util.List;

/**
 * @author osminin
 * @ created 16.09.13
 * @ $Author$
 * @ $Revision$
 *
 * Обработчик запроса на обновление (добавление, удаление, смена активного коннектора) регистраций телефонов
 *
 * Параметры запроса:
 * profileInfo		            Информация о пользователе                                   [1]
 *      firstname               Имя пользователя                                            [1]
 *      patrname                Отчество пользователя                                       [1]
 *      surname                 Фамилия пользователя                                        [1]
 *      birthdate               Дата рождения пользователя                                  [1]
 *      passport                ДУЛ пользователя                                            [1]
 *      cbCode                  Подразделение пользователя                                  [1]
 * mainPhone                    регистрируемый основной телефон                             [0..1]
 * addPhones                    Список добавляемых телефонов                                [0..1]
 *      addPhone                номер добавляемого телефона                                 [0..n]
 * removePhones                 Список удаляемых телефонов                                  [0..1]
 *      removePhone             номер удаляемого телефона                                   [0..n]
 *
 *
 * Параметры ответа:
 */
public class UpdatePhoneRegistrationsRequestProcessor extends RequestProcessorBase
{
	private static final String REQUEST_TYPE  = "updatePhoneRegistrationsRq";
	private static final String RESPONSE_TYPE = "updatePhoneRegistrationsRs";

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

		String phoneNumber = XmlHelper.getSimpleElementValue(element, Constants.MAIN_PHONE_TAG);
		CSAUserInfo userInfo = getUserInfo(element);
		List<String> addPhones = XmlHelper.getSimpleListElementValue(element, Constants.ADD_PHONES_TAG, Constants.ADD_PHONE_TAG);
		List<String> removePhones = XmlHelper.getSimpleListElementValue(element, Constants.REMOVE_PHONES_TAG, Constants.REMOVE_PHONE_TAG);

		return doRequest(userInfo, phoneNumber, addPhones, removePhones);
	}

	private ResponseInfo doRequest (final CSAUserInfo userInfo, final String mainPhone, final List<String> addPhones, final List<String> removePhones) throws Exception
	{
		//Дубликаты обрабатываем вне общей транзакции, что бы данные были актуальные
		if (CollectionUtils.isNotEmpty(addPhones))
		{
			//Ищем дубликаты
			List<String> duplicates = ERMBConnector.findDuplicatesPhones(addPhones);
			if (CollectionUtils.isNotEmpty(duplicates))
			{
				processDuplicates(duplicates);
			}
		}

		HibernateExecutor.getInstance().execute(new HibernateAction<Void>()
		{
			public Void run(Session session) throws Exception
			{
				//В случае, если необходимо создать новый профиль, присваиваем уроень безопасности null
				final IdentificationContext context = IdentificationContext.createByUserInfo(userInfo, null);

				if (StringHelper.isNotEmpty(mainPhone))
				{
					boolean isNew = CollectionUtils.isNotEmpty(addPhones) && addPhones.contains(mainPhone);
					addOrUpdateMainPhone(mainPhone, !isNew, context.getProfile().getId());
				}

				addPhones(context, addPhones, mainPhone);
				removePhones(context, removePhones);
				return null;
			}
		});

		return getSuccessResponseBuilder().end().getResponceInfo();
	}

	private void addOrUpdateMainPhone(String phone, boolean isOld, Long profileId) throws Exception
	{
		//сбрасываем текущий активный коннектор
		ERMBConnector.resetProfileActiveConnector(profileId);
		// если коннектор для телефона уже есть, то устанавливаем его активным
		if (isOld)
		{
			int result = ERMBConnector.setProfileActiveConnector(profileId, phone);
			if (result != 1)
			{
				throw new ActivatePhoneRegistrationException("Найдено " + result + " коннекторов для обновления по телефону " + phone + " вместо одного.");
			}
		}
	}

	private void addPhones(IdentificationContext context, List<String> phones, String mainPhone) throws Exception
	{
		if (CollectionUtils.isNotEmpty(phones))
		{
			//Есть ли среди добавляемых телефонов основной
			boolean mainPhoneContained = StringHelper.isNotEmpty(mainPhone);
			trace("Добавляем телефоны для профиля " + context.getProfile().getId());
			for (String phone : phones)
			{
				//Если телефон указан основным, устанавливаем статус ACTIVE, иначе CLOSED
				ConnectorState state = (mainPhoneContained && phone.equals(mainPhone)) ? ConnectorState.ACTIVE : ConnectorState.CLOSED;
				//Создаем коннектор
				ERMBConnector.create(context, phone, state);
			}
		}
	}

	protected void processDuplicates(List<String> duplicates) throws Exception
	{
		throw new DuplicatePhoneRegistrationsException(getDuplicateErrorMessage(duplicates));
	}

	private String getDuplicateErrorMessage(List<String> duplicates)
	{
		StringBuilder builder = new StringBuilder();
		Iterator iterator = duplicates.iterator();
		while (iterator.hasNext())
		{
			builder.append(iterator.next());
			if (iterator.hasNext())
			{
				builder.append(",");
			}
		}
		return "Телефоны " + builder.toString() + " уже зарегистрированы.";
	}

	private void removePhones(IdentificationContext context, List<String> phones) throws Exception
	{
		if (CollectionUtils.isNotEmpty(phones))
		{
			trace("Удаляем телефоны для профиля " + context.getProfile().getId());
			ERMBConnector.removeByPhonesAndProfileId(phones, context.getProfile().getId());
		}
	}

	private CSAUserInfo getUserInfo(Element root) throws Exception
	{
		Element profileInfo = XmlHelper.selectSingleNode(root, Constants.PROFILE_INFO_TAG);

		return profileInfo == null ? null : fillUserInfo(profileInfo);
	}
}
