package com.rssl.phizic.logging.messaging;

import java.io.Serializable;

/**
 * Сущность журнала финансовых операций для таблицы FINANCIAL_CODLOG
 * User: miklyaev
 * Date: 22.09.14
 * Time: 11:11
 */
public class FinancialMessagingLogEntry extends MessagingLogEntry implements Serializable
{
	/**
	 * Конструктор
	 */
	public FinancialMessagingLogEntry()
	{
	}

	/**
	 * Конструктор для создания записи финансовой операции
	 * на основе объекта нефинансовой операции
	 * @param entry - объект базового класса
	 */
	public FinancialMessagingLogEntry(MessagingLogEntry entry)
	{
		this.id = entry.getId();
		this.date = entry.getDate();
		this.executionTime = entry.getExecutionTime();
		this.application = entry.getApplication();
		this.messageType = entry.getMessageType();
		this.messageRequestId = entry.getMessageRequestId();
		this.messageRequest = entry.getMessageRequest();
		this.messageResponseId = entry.getMessageResponseId();
		this.messageResponse = entry.getMessageResponse();
		this.loginId = entry.getLoginId();
		this.system = entry.getSystem();
		this.sessionId = entry.getSessionId();
		this.surName = entry.getSurName();
		this.patrName = entry.getPatrName();
		this.firstName = entry.getFirstName();
		this.departmentName = entry.getDepartmentName();
		this.personSeries = entry.getPersonSeries();
		this.personNumbers = entry.getPersonNumbers();
		this.birthDay = entry.getBirthDay();
		this.operationUID = entry.getOperationUID();
		this.login = entry.getLogin();
		this.departmentCode = entry.getDepartmentCode();
		this.promoterId = entry.getPromoterId();
		this.ipAddress = entry.getIpAddress();
		this.mGUID = entry.getmGUID();
		this.errorCode = entry.getErrorCode();
		this.logUID = entry.getLogUID();
		this.nodeId = entry.getNodeId();
		this.threadInfo = entry.getThreadInfo();
		this.tb = entry.getTb();
		this.osb = entry.getOsb();
		this.vsp = entry.getVsp();
		this.addInfo = entry.getAddInfo();
	}
}
