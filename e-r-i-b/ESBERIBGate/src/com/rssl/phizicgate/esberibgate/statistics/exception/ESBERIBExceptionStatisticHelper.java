package com.rssl.phizicgate.esberibgate.statistics.exception;

import com.rssl.phizic.common.types.transmiters.GroupResult;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.statistics.exception.ExternalExceptionInfo;
import com.rssl.phizic.gate.statistics.exception.GateExceptionStatisticHelper;
import com.rssl.phizic.gate.utils.EntityCompositeId;
import com.rssl.phizic.gate.utils.ExternalSystemHelper;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.logging.messaging.System;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.utils.BeanHelper;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizicgate.esberibgate.ws.generated.*;
import com.rssl.phizicgate.esberibgate.ws.jms.segment.federal.generated.StatusType;
import org.apache.commons.lang.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * @author akrenev
 * @ created 18.04.2013
 * @ $Author$
 * @ $Revision$
 *
 * Хелпер для сбора статистики и декорирования ошибок шины
 */

public final class ESBERIBExceptionStatisticHelper
{
	private static final Log log = PhizICLogFactory.getLog(Constants.LOG_MODULE_CORE);
	private static final Map<Class, String> responseMapping = new HashMap<Class, String>();

	static
	{
		responseMapping.put(AdditionalCardInfo_Type.class,              "AdditionalCardInfo");
		responseMapping.put(AutoSubscriptionModRs_Type.class,           "AutoSubscriptionMod");
		responseMapping.put(AutoSubscriptionStatusModRs_Type.class,     "AutoSubscriptionStatusMod");
		responseMapping.put(BankAcctInqRs_Type.class,                   "BankAcctInq");
		responseMapping.put(BankAcctPermissModRs_Type.class,            "BankAcctPermissMod");
		responseMapping.put(BankAcctStmtImgInqRs_Type.class,            "BankAcctStmtImgInq");
		responseMapping.put(BillingPayExecRs_Type.class,                "BillingPayExec");
		responseMapping.put(BillingPayInqRs_Type.class,                 "BillingPayInq");
		responseMapping.put(BillingPayPrepRs_Type.class,                "BillingPayPrep");
		responseMapping.put(CardAcctRec_Type.class,                     "CardAcctRec");
		responseMapping.put(CardBlockRs_Type.class,                     "CardBlock");
		responseMapping.put(CardReissuePlaceRs_Type.class,              "CardReissuePlace");
		responseMapping.put(CardToIMAAddRs_Type.class,                  "CardToIMAAdd");
		responseMapping.put(CardToNewDepAddRs_Type.class,               "CardToNewDepAdd");
		responseMapping.put(CCAcctExtStmtInqRs_TypeCardAcctRec.class,   "CCAcctExtStmtInq");
		responseMapping.put(ChangeAccountInfoRs_Type.class,             "ChangeAccountInfo");
		responseMapping.put(DepAcct_Type.class,                         "DepAcct");
		responseMapping.put(DepoAccSecInfoRsTypeDepoAccSecInfoRec.class,"DepoAccSecInfoRsTypeDepoAccSecInfo");
		responseMapping.put(DepoAccSecRegRsType.class,                  "DepoAccSecReg");
		responseMapping.put(DepoAcctId_Type.class,                      "DepoAcctId");
		responseMapping.put(DepoAcctRes_Type.class,                     "DepoAcctRes");
		responseMapping.put(DepoArRsType.class,                         "DepoAr");
		responseMapping.put(DepoClientRegRsType.class,                  "DepoClientReg");
		responseMapping.put(DepoDeptRes_Type.class,                     "DepoDeptRes");
		responseMapping.put(DepoDeptResZad_Type.class,                  "DepoDeptResZad");
		responseMapping.put(DepoRevokeDocRsType.class,                  "DepoRevokeDoc");
		responseMapping.put(DepToNewIMAAddRs_Type.class,                "DepToNewIMAAdd");
		responseMapping.put(DepToNewDepAddRs_Type.class,                "DepToNewDepAdd");
		responseMapping.put(DetailAcctInfo_Type.class,                  "DetailAcctInfo");
		responseMapping.put(IMSAcctRec_Type.class,                      "IMSAcctRec");
		responseMapping.put(ImsRec_Type.class,                          "ImsRec");
		responseMapping.put(GetAutoPaymentListRs_Type.class,            "GetAutoPaymentList");
		responseMapping.put(GetAutoPaymentDetailInfoRs_Type.class,      "GetAutoPaymentDetailInfo");
		responseMapping.put(GetAutoSubscriptionDetailInfoRs_Type.class, "GetAutoSubscriptionDetailInfo");
		responseMapping.put(GetAutoSubscriptionListRs_Type.class,       "GetAutoSubscriptionList");
		responseMapping.put(GetInsuranceAppRs_Type.class,               "GetInsuranceApp");
		responseMapping.put(GetInsuranceListRs_Type.class,              "GetInsuranceList");
		responseMapping.put(LoanInqRs_Type.class,                       "LoanInq");
		responseMapping.put(LoanRec_Type.class,                         "LoanRec");
		responseMapping.put(LoanPaymentRs_Type.class,                   "LoanPayment");
		responseMapping.put(OTPRestrictionModRs_Type.class,             "OTPRestrictionMod");
		responseMapping.put(RecipientRec_Type.class,                    "RecipientRec");
		responseMapping.put(SecuritiesInfoInqRs_Type.class,             "SecuritiesInfoInq");
		responseMapping.put(ServiceStmtRs_Type.class,                   "ServiceStmt");
		responseMapping.put(SetAccountStateRs_Type.class,               "SetAccountState");
		responseMapping.put(SvcAcctDelRs_Type.class,                    "SvcAcctDel");
		responseMapping.put(SvcActInfo_Type.class,                      "SvcActInfo");
		responseMapping.put(SvcAddRs_Type.class,                        "SvcAdd");
		responseMapping.put(XferAddRs_Type.class,                       "XferAdd");
		responseMapping.put(XferOperStatusInfoRs_Type.class,            "XferOperStatusInfo");
	}

	private ESBERIBExceptionStatisticHelper(){}

	/**
	 * покладываем ошибку в результат
	 * @param result результат
	 * @param key ключ
	 * @param status статус ответа
	 * @param message класс содержательной части ответа (то что содержит статус)
	 */
	public static <K, V> void addError(GroupResult<K, V> result, K key, Status_Type status, Class message)
	{
		addError(result, key, status, message, (String) null);
	}

	/**
	 * покладываем ошибку в результат
	 * @param result результат
	 * @param key ключ
	 * @param status статус ответа
	 * @param messageType класс содержательной части ответа (то что содержит статус)
	 * @param compositeId композитный ключ сущности, по кторой пришла ошибка
	 */
	public static <K, V> void addError(GroupResult<K, V> result, K key, Status_Type status, Class messageType, EntityCompositeId compositeId)
	{
		addError(result, key, status, messageType, getSystemId(compositeId));
	}

	/**
	 * покладываем ошибку в результат
	 * @param result результат
	 * @param key ключ
	 * @param status статус ответа
	 * @param messageType класс содержательной части ответа (то что содержит статус)
	 * @param system система
	 */
	public static <K, V> void addError(GroupResult<K, V> result, K key, Status_Type status, Class messageType, String system)
	{
		//noinspection ThrowableInstanceNeverThrown
		String message = decorateErrorMessage(status, messageType, getStatusDescription(status), getSystem(system));
		result.putException(key, new GateLogicException(message));
	}

	/**
	 * покладываем ошибку в результат
	 * @param result результат
	 * @param key ключ
	 * @param status статус ответа
	 * @param messageType класс содержательной части ответа (то что содержит статус)
	 * @param compositeId композитный ключ сущности, по кторой пришла ошибка
	 */
	public static <K, V> void addOfflineError(GroupResult<K, V> result, K key, Status_Type status, Class messageType, EntityCompositeId compositeId)
	{
		//noinspection ThrowableInstanceNeverThrown
		String message = decorateErrorMessage(status, messageType, getStatusDescription(status), getSystemId(compositeId));
		result.putException(key, new ExternalSystemOfflineErrorCodeException(message));
	}

	/**
	 * Выбрасываем GateLogicException с заданным сотрудником сообщением или (если не задано) из статуса ответа
	 * @param status статус ответа
	 * @param request запрос
	 * @param messageType класс содержательной части ответа (то что содержит статус)
	 */
	public static void throwErrorResponse(Status_Type status, Class messageType, IFXRq_Type request) throws GateLogicException
	{
		String message = decorateErrorMessage(status, messageType, getStatusDescription(status), getSystemId(request));
		throwErrorResponse(status, messageType, message);
	}

	/**
	 * Выбрасываем GateLogicException с заданным сотрудником сообщением или (если не задано) из статуса ответа
	 * @param status статус ответа
	 * @param systemId id системы запроса
	 * @param messageType класс содержательной части ответа (то что содержит статус)
	 */
	public static void throwErrorResponse(StatusType status, Class messageType, String systemId) throws GateLogicException
	{
		Status_Type status_type = new Status_Type(status.getStatusCode(),
												  status.getStatusDesc(),
												  status.getStatusType(),
												  status.getServerStatusDesc());

		throwErrorResponse(status_type, messageType, decorateErrorMessage(status, messageType, getStatusDescription(status), systemId));
	}

	/**
	 * Выбрасываем GateLogicException с заданным сотрудником сообщением или (если не задано) из статуса ответа
	 * @param status статус ответа
	 * @param request запрос
	 * @param messageType класс содержательной части ответа (то что содержит статус)
	 */
	public static void throwOfflineResponse(Status_Type status, Class messageType, IFXRq_Type request) throws GateLogicException
	{
		String message = decorateErrorMessage(status, messageType, getStatusDescription(status), getSystemId(request));
		throwOfflineResponse(message);
	}

	/**
	 * Выбрасываем GateLogicException с заданным сотрудником сообщением или (если не задано) из статуса ответа
	 * @param status статус ответа
	 * @param systemId id системы запроса
	 * @param messageType класс содержательной части ответа (то что содержит статус)
	 */
	public static void throwOfflineResponse(StatusType status, Class messageType, String systemId) throws GateLogicException
	{
		String message = decorateErrorMessage(status, messageType, getStatusDescription(status), systemId);
		throwOfflineResponse(message);
	}

	/**
	 * Выбрасываем GateLogicException с заданным сотрудником или (если не задано) переданным (defaultMessage) сообщением
	 * @param status статус ответа
	 * @param message класс содержательной части ответа (то что содержит статус)
	 * @param defaultMessage дефолтное сообщение
	 */
	public static void throwErrorResponse(Status_Type status, Class message, String defaultMessage) throws GateLogicException
	{
		throw new ExternalSystemErrorCodeException(status, message, defaultMessage);
	}

	/**
	 * Выбрасываем OfflineExternalSystemException с заданным сотрудником или (если не задано) переданным (defaultMessage) сообщением
	 * @param message сообщение
	 */
	private static void throwOfflineResponse(String message) throws GateLogicException
	{
		throw new ExternalSystemOfflineErrorCodeException(message);
	}

	/**
	 * Выбрасываем GateTimeOutException с заданным сотрудником или (если не задано) переданным (defaultMessage) сообщением
	 * @param status статус ответа
	 * @param message класс содержательной части ответа (то что содержит статус)
	 * @param defaultMessage дефолтное сообщение
	 */
	public static void throwTimeoutErrorResponse(Status_Type status, Class message, String defaultMessage) throws GateLogicException
	{
		throw new ExternalSystemTimeoutErrorCodeException(status, message, defaultMessage);
	}

	/**
	 * Обработать исключение
	 * @param exception исключение
	 * @param request запрос
	 * @throws GateLogicException
	 */
	public static void process(ExternalSystemErrorCodeException exception, IFXRq_Type request) throws GateLogicException
	{
		String newErrorMessage = decorateErrorMessage(exception.getStatus(), exception.getMessageType(), exception.getMessage(), getSystemId(request));
		exception.setErrorMessage(newErrorMessage);
		throw exception;
	}

	/**
	 * Обработать исключение
	 * @param exception исключение
	 * @param request запрос
	 * @throws GateLogicException
	 */
	public static void process(ExternalSystemTimeoutErrorCodeException exception, IFXRq_Type request) throws GateLogicException
	{
		String newErrorMessage = decorateErrorMessage(exception.getStatus(), exception.getMessageType(), exception.getMessage(), getSystemId(request));
		exception.setErrorMessage(newErrorMessage);
		throw exception;
	}

	private static String getEsbSystem()
	{
		return ExternalSystemHelper.getESBSystemCode();
	}

	private static String getSystem(String system)
	{
		return StringHelper.isNotEmpty(system)? system: getEsbSystem();
	}

	/**
	 * получить systemId
	 * @param compositeId идентификатор
	 * @return systemId
	 */
	private static String getSystemId(EntityCompositeId compositeId)
	{
		return compositeId == null? getEsbSystem(): getSystem(compositeId.getSystemId());
	}

	/**
	 * определить systemId запроса
	 * @param ifxRq запрос
	 * @return systemId
	 */
	private static String getSystemId(IFXRq_Type ifxRq)
	{
		try
		{
			if (ifxRq == null)
				return getEsbSystem();

			//todo убрать костыли в рамках запроса BUG057697
			if (ifxRq.getBillingPayExecRq()!=null)
				return getSystem(ifxRq.getBillingPayExecRq().getSystemId());
			if (ifxRq.getBillingPayPrepRq()!=null)
				return getSystem(ifxRq.getBillingPayPrepRq().getSystemId());
			SystemIdResolver systemIdResolver = BeanHelper.copyObject(ifxRq, TypesCorrelation.getTypes());
			return getSystem(systemIdResolver.getSystemId());
		}
		catch (Exception e)
		{
			log.error("Ошибка получения системы.", e);
			return getEsbSystem();
		}
	}

	private static String getStatusDescription(Status_Type status)
	{
		return status == null? StringUtils.EMPTY: status.getStatusDesc();
	}

	private static String decorateErrorMessage(Status_Type status, Class message, String defaultMessage, String system)
	{
		if (status == null || message == null)
			return defaultMessage;

		String messageName = responseMapping.get(message);
		if (StringHelper.isEmpty(messageName))
		{
			log.error("Не задано имени запроса для " + message.getName());
			return defaultMessage;
		}

		String errorCode = String.valueOf(status.getStatusCode());
		String errorDescription = getStatusDescription(status);
		ExternalExceptionInfo exceptionInfo = GateExceptionStatisticHelper.getBaseExceptionInfo(system, messageName, errorCode, errorDescription);
		exceptionInfo.setDetail("Запрос " + messageName + " вернул ошибку " + errorCode + ": " + errorDescription);
		exceptionInfo.setGate(System.esb);
		String mappedMessage = GateExceptionStatisticHelper.getCustomErrorMessage(exceptionInfo);
		return StringHelper.isNotEmpty(mappedMessage)? mappedMessage: defaultMessage;
	}

	private static String getStatusDescription(StatusType status)
	{
		return status == null? StringUtils.EMPTY: status.getStatusDesc();
	}

	private static String decorateErrorMessage(StatusType status, Class message, String defaultMessage, String system)
	{
		if (status == null || message == null)
			return defaultMessage;

		String messageName = responseMapping.get(message);
		if (StringHelper.isEmpty(messageName))
		{
			log.error("Не задано имени запроса для " + message.getName());
			return defaultMessage;
		}

		String errorCode = String.valueOf(status.getStatusCode());
		String errorDescription = getStatusDescription(status);
		ExternalExceptionInfo exceptionInfo = GateExceptionStatisticHelper.getBaseExceptionInfo(system, messageName, errorCode, errorDescription);
		exceptionInfo.setDetail("Запрос " + messageName + " вернул ошибку " + errorCode + ": " + errorDescription);
		exceptionInfo.setGate(System.esb);
		String mappedMessage = GateExceptionStatisticHelper.getCustomErrorMessage(exceptionInfo);
		return StringHelper.isNotEmpty(mappedMessage)? mappedMessage: defaultMessage;
	}
}
