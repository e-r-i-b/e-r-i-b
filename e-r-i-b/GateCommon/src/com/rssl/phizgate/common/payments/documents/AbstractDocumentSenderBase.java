package com.rssl.phizgate.common.payments.documents;

import com.rssl.phizgate.common.payments.offline.OfflineDocumentInfo;
import com.rssl.phizgate.common.payments.offline.OfflineDocumentInfoService;
import com.rssl.phizic.ApplicationAutoRefreshConfig;
import com.rssl.phizic.common.types.Currency;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.clients.BackRefClientService;
import com.rssl.phizic.gate.clients.Client;
import com.rssl.phizic.gate.clients.ClientService;
import com.rssl.phizic.gate.currency.CurrencyService;
import com.rssl.phizic.gate.documents.DocumentSender;
import com.rssl.phizic.gate.documents.DocumentUpdater;
import com.rssl.phizic.gate.documents.GateDocument;
import com.rssl.phizic.gate.documents.StateUpdateInfo;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.impl.AbstractService;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.utils.DateHelper;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Krenev
 * @ created 17.08.2007
 * @ $Author$
 * @ $Revision$
 */
public abstract class AbstractDocumentSenderBase extends AbstractService implements DocumentSender, DocumentUpdater
{
	protected static final Log log = PhizICLogFactory.getLog(Constants.LOG_MODULE_GATE);
	protected static final String NOT_SYNCH_DOC_ERROR_TEMPLATE = "Некорректный тип документа id= %s: ожидался SynchronizableDocument.";

	private Map<String, ?> parameters = new HashMap<String, Object>();

	protected AbstractDocumentSenderBase(GateFactory factory)
	{
		super(factory);
	}

	public StateUpdateInfo execute(GateDocument document) throws GateException, GateLogicException
	{
		return checkOfflineDocument(document);
	}

	/**
	 * Получить владельца документа из ВС (через ClientService)
	 * @param document документ
	 * @return владелец
	 * @throws GateLogicException
	 * @throws GateException
	 */
	protected Client getGateOwner(GateDocument document) throws GateLogicException, GateException
	{
		return getFactory().service(ClientService.class).getClientById(document.getExternalOwnerId());
	}

	/**
	 * Получить владельца документа из анкеты клиента в бизнесе(через BackRefClientService)
	 * @param document документ
	 * @return владелец
	 * @throws GateLogicException
	 * @throws GateException
	 */
	protected Client getBusinessOwner(GateDocument document) throws GateLogicException, GateException
	{
		return getFactory().service(BackRefClientService.class).getClientById(document.getInternalOwnerId());
	}

	public void setParameters(Map<String, ?> params)
	{
		parameters = params;
	}

	protected Object getParameter(String name)
	{
		return parameters.get(name);
	}

	/**
	 * Утилитный метод получения валюты счета по номеру счета
	 * @param accountNumber номер счета
	 * @return валюта
	 * @throws GateException
	 * @throws GateLogicException
	 */
	public static Currency getAccountCurrency(String accountNumber) throws GateException, GateLogicException
	{
		CurrencyService currencyService = GateSingleton.getFactory().service(CurrencyService.class);
		//Валюту счета определяем  из самого счета -  это 6-8 позиция в номере счета.
		String code = accountNumber.substring(5, 8);
		if ("810".equals(code))
			code = "643";
		return currencyService.findByNumericCode(code);
	}

	/**
	 * Добавить запись для отправленного документа в базе оффлайн документов
	 * @param document - документ
	 */
	protected void addOfflineDocumentInfo(GateDocument document) throws GateException
	{
		if (!ConfigFactory.getConfig(ApplicationAutoRefreshConfig.class).isMultiblockMode())
			return;

		OfflineDocumentInfoService.addOfflineDocumentInfo(document);
	}

	private StateUpdateInfo checkOfflineDocument(GateDocument document) throws GateException, GateLogicException
	{
		if (!ConfigFactory.getConfig(ApplicationAutoRefreshConfig.class).isMultiblockMode())
			throw new GateException("В одноблочном режиме работы запрещено использование БД обратного потока.");

		OfflineDocumentInfo offlineDocumentInfo = OfflineDocumentInfoService.getOfflineDocumentInfo(document);
		if (offlineDocumentInfo == null)
		{
			log.error("Не найдена информация для документа id=" + document.getId());
			return null;
		}

		if (offlineDocumentInfo.getStateCode() == null)
		{
			log.error("Не получен статус документа id=" + document.getId());
			return null;
		}

		String additInfo = offlineDocumentInfo.getAdditInfo();
		if (additInfo != null)
			fillPaymentData(document, additInfo);

		return new StateUpdateInfoImpl(offlineDocumentInfo.getStateCode(), offlineDocumentInfo.getStateDescription());
	}

	protected void fillPaymentData(GateDocument document, String additInfo) throws GateException, GateLogicException
	{
		//nothing
	}

	protected Calendar getAllowedUpdateDate(Calendar clientOperationDate, Integer documentUpdateWaitingTime)
	{
		Calendar date = Calendar.getInstance();
		date.add(Calendar.SECOND, - documentUpdateWaitingTime);
		return date.after(clientOperationDate) ? null : DateHelper.addSeconds(clientOperationDate, documentUpdateWaitingTime);
	}
}