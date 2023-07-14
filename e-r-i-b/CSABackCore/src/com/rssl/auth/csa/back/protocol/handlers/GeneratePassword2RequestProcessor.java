package com.rssl.auth.csa.back.protocol.handlers;

import com.rssl.auth.csa.back.protocol.Constants;
import com.rssl.auth.csa.back.protocol.LogableResponseInfo;
import com.rssl.auth.csa.back.protocol.RequestInfo;
import com.rssl.auth.csa.back.protocol.ResponseInfo;
import com.rssl.auth.csa.back.servises.Profile;
import com.rssl.auth.csa.back.servises.connectors.CSAConnector;
import com.rssl.auth.csa.back.servises.connectors.InternalPasswordGenerator;
import com.rssl.auth.csa.back.servises.operations.GeneratePasswordOperation;
import com.rssl.auth.csa.back.servises.operations.IdentificationContext;
import com.rssl.auth.csa.back.servises.operations.LogIdentificationContext;
import com.rssl.phizic.utils.xml.XmlHelper;
import org.apache.commons.collections.CollectionUtils;
import org.w3c.dom.Document;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author krenev
 * @ created 12.10.2012
 * @ $Author$
 * @ $Revision$
 * Обработчик запроса на генерацию пароля для ЦСА коннекторов всего профиля.
 * Страшный костыль - итог недодуманных требований ответственных исполнителей (ждем, что скоро эта бребятина уйдет)
 *
 * Пармерты запроса:
 *      firstname               Имя пользователя                                            [1]
 *      patrname                Отчество пользователя                                       [0..1]
 *      surname                 Фамилия пользователя                                        [1]
 *      birthdate               Дата рождения пользователя                                  [1]
 *      passport                ДУЛ пользователя                                            [1]
 *      cbCode                  Подразделение пользователя                                  [1]
 * Параметры ответа:
 *
 */
public class GeneratePassword2RequestProcessor extends LogableProcessorBase
{
	public static final String REQUEST_TYPE = "generatePassword2Rq";
	public static final String RESPONCE_TYPE = "generatePassword2Rs";

	private static final String ERIB_IDENTIFICATOR_NOT_FOUND_ERROR = "У клиента отсутствует идентификатор ЕРИБ.";

	protected String getRequestType()
	{
		return REQUEST_TYPE;
	}

	protected String getResponceType()
	{
		return RESPONCE_TYPE;
	}

	@Override
	protected LogableResponseInfo processRequest(RequestInfo requestInfo, IdentificationContext context) throws Exception
	{
		Document body = requestInfo.getBody();
		boolean ignoreImsiCheck = Boolean.parseBoolean(XmlHelper.getSimpleElementValue(body.getDocumentElement(), Constants.IGNORE_IMSI_CHEK));
		return new LogableResponseInfo(generatePassword(context.getProfile(), ignoreImsiCheck));
	}

	@Override
	protected LogIdentificationContext getIdentificationContext(RequestInfo requestInfo) throws Exception
	{
		Document body = requestInfo.getBody();
		Profile templateProfile = fillProfileTemplate(body.getDocumentElement());
		return LogIdentificationContext.createByTemplateProfile(templateProfile);
	}

	private ResponseInfo generatePassword(Profile profile, boolean ignoreImsiCheck) throws Exception
	{
		List<CSAConnector> connectors = CSAConnector.findNotClosedByProfileID(profile.getId());

		if (CollectionUtils.isEmpty(connectors))
		{
			return getFailureResponseBuilder().buildActiveCSAConnectorsNotFoundResponse(ERIB_IDENTIFICATOR_NOT_FOUND_ERROR);
		}

		Set<String> excludedPhones = new HashSet<String>();

		for (CSAConnector connector : connectors)
		{
			GeneratePasswordOperation operation = new GeneratePasswordOperation();
			operation.initialize(connector, ignoreImsiCheck);
			if (operation.getExcludedPhones() != null)
			{
				excludedPhones.addAll(operation.getExcludedPhones());
			}
		}
		new InternalPasswordGenerator(CSAConnector.getGeneratedPasswordAllowedChars(), CSAConnector.getGeneratedPasswordLength(), excludedPhones, connectors.toArray(new CSAConnector[connectors.size()])).generatePassword();
		return buildSuccessResponse();
	}
}