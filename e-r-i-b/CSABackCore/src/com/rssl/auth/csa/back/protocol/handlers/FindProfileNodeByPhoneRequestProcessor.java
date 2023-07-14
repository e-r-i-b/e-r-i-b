package com.rssl.auth.csa.back.protocol.handlers;

import com.rssl.auth.csa.back.Utils;
import com.rssl.auth.csa.back.protocol.Constants;
import com.rssl.auth.csa.back.protocol.LogableResponseInfo;
import com.rssl.auth.csa.back.protocol.RequestInfo;
import com.rssl.auth.csa.back.servises.nodes.CreateProfileNodeMode;
import com.rssl.auth.csa.back.servises.nodes.ProfileNode;
import com.rssl.auth.csa.back.servises.operations.IdentificationContext;
import com.rssl.auth.csa.back.servises.operations.LogIdentificationContext;
import com.rssl.auth.csa.back.servises.restrictions.ERMBAuthenticationRestriction;
import com.rssl.phizic.utils.xml.XmlHelper;
import org.w3c.dom.Document;

/**
 * @author osminin
 * @ created 17.09.13
 * @ $Author$
 * @ $Revision$
 *
 * Обработчик запроса на получение информации о блоке пользователя по телефону
 *
 * Парметры запроса
 * phoneNumber                  Номер телефона                                              [1]
 *
 * Параметры ответа:
 * nodeInfo                         Информация об узле                                          [1]
 *      host                        хост                                                        [1]
 *      smsQueueName                наименование очереди смс канала                             [1]
 *      smsFactoryName              наименование фабрики смс канала                             [1]
 *      smsWithIMSIQueueName        наименование очереди смс канала                             [1]
 *      smsWithIMSIFactoryName      наименование фабрики смс канала                             [1]
 *      serviceProfileQueueName     наименование очереди служебного канала (ConfirmProfilesRq)  [1]
 *      serviceProfileFactoryName   наименование фабрики служебного канала (ConfirmProfilesRq)  [1]
 *      serviceClientQueueName      наименование очереди служебного канала (UpdateClientRq)     [1]
 *      serviceClientFactoryName    наименование фабрики служебного канала (UpdateClientRq)     [1]
 *      serviceResourceQueueName    наименование очереди служебного канала (UpdateResourceRq)   [1]
 *      serviceResourceFactoryName  наименование фабрики служебного канала (UpdateResourceRq)   [1]
 *
 */
public class FindProfileNodeByPhoneRequestProcessor extends LogableProcessorBase
{
	private static final String REQUEST_TYPE  = "findProfileNodeByPhoneRq";
	private static final String RESPONSE_TYPE = "findProfileNodeByPhoneRs";

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
	protected LogableResponseInfo processRequest(RequestInfo requestInfo, IdentificationContext context) throws Exception
	{
		// Операция для ЕРМБ не нужна, поэтому используем рестрикшен прямо здесь
		ERMBAuthenticationRestriction.getInstance().check(context);
		ProfileNode profileNode = Utils.getActiveProfileNode(context.getProfile().getId(), CreateProfileNodeMode.CREATION_ALLOWED_FOR_ALL_NODES);

		return new LogableResponseInfo(getSuccessResponseBuilder().addNodeInfo(profileNode).end());
	}

	@Override
	protected LogIdentificationContext getIdentificationContext(RequestInfo requestInfo) throws Exception
	{
		Document document = requestInfo.getBody();
		String phoneNumber = XmlHelper.getSimpleElementValue(document.getDocumentElement(), Constants.PHONE_NUMBER_TAG);

		return LogIdentificationContext.createByPhoneNumber(phoneNumber);
	}
}
