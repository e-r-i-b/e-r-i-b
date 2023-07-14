package com.rssl.phizic.business.payments.forms.meta;

import com.rssl.common.forms.DocumentException;
import com.rssl.common.forms.DocumentLogicException;
import com.rssl.common.forms.doc.BusinessDocumentHandlerBase;
import com.rssl.common.forms.doc.StateMachineEvent;
import com.rssl.common.forms.state.StateObject;
import com.rssl.phizic.ApplicationAutoRefreshConfig;
import com.rssl.phizic.auth.modes.AuthenticationContext;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.dictionaries.providers.ServiceProviderService;
import com.rssl.phizic.business.documents.AbstractPaymentDocument;
import com.rssl.phizic.business.documents.payments.BusinessDocument;
import com.rssl.phizic.business.documents.payments.JurPayment;
import com.rssl.phizic.business.documents.payments.RurPayment;
import com.rssl.phizic.business.ext.sbrf.dictionaries.offices.SBRFOfficeAdapter;
import com.rssl.phizic.business.finances.targets.AccountTarget;
import com.rssl.phizic.business.finances.targets.AccountTargetService;
import com.rssl.phizic.business.providers.ProvidersConfig;
import com.rssl.phizic.common.types.Money;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.gate.claims.AccountOpeningClaim;
import com.rssl.phizic.gate.claims.AccountOpeningFromCardClaim;
import com.rssl.phizic.gate.claims.DemandAccountOpeningClaim;
import com.rssl.phizic.gate.documents.GateDocument;
import com.rssl.phizic.gate.documents.GateTemplate;
import com.rssl.phizic.gate.documents.SynchronizableDocument;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.payments.*;
import com.rssl.phizic.gate.payments.autopayment.CreateAutoPayment;
import com.rssl.phizic.gate.payments.loan.ETSMLoanClaim;
import com.rssl.phizic.gate.payments.systems.CardPaymentSystemPayment;
import com.rssl.phizic.logging.monitoring.MonitoringDocumentType;
import com.rssl.phizic.logging.monitoring.MonitoringLogEntry;
import com.rssl.phizic.logging.monitoring.MonitoringOperationConfig;
import com.rssl.phizic.logging.system.LogModule;
import com.rssl.phizic.logging.system.PhizICLogFactory;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

/**
 * Хендлер мониторинга бизнес-опеарций
 *
 * @author bogdanov
 * @ created 26.02.15
 * @ $Author$
 * @ $Revision$
 */

public class MonitoringDocumentHandler extends BusinessDocumentHandlerBase
{
	private static final String DOCUMENT_STATE = "state";
	private static final ServiceProviderService SERVICE_PROVIDER_SERVICE = new ServiceProviderService();
	private static final AccountTargetService TARGET_SERIVCE = new AccountTargetService();

	/**
	 * Класс для выбора конкретного типа документа.
	 */
	private static interface DocumentTypeChoiser
	{
		MonitoringDocumentType getType(GateDocument doc, MonitoringLogEntry entry) throws BusinessException;
	}

	private static class SimpleDocymentTypeChoiser implements DocumentTypeChoiser
	{
		private final MonitoringDocumentType type;

		private SimpleDocymentTypeChoiser(MonitoringDocumentType type)
		{
			this.type = type;
		}

		public MonitoringDocumentType getType(GateDocument doc, MonitoringLogEntry entry)
		{
			return type;
		}
	}

	private static class JurPaymentDocumentTypeChoiser implements DocumentTypeChoiser
	{
		public MonitoringDocumentType getType(GateDocument doc, MonitoringLogEntry entry) throws BusinessException
		{
			if (doc instanceof GateTemplate)
			{
				//Оплата услуг.
				boolean serviceProvider = ((GateTemplate)doc).getReceiverInternalId() != null;

				if (!serviceProvider)
					return null;

				entry.setProviderUuid(
						SERVICE_PROVIDER_SERVICE.findShortProviderById(((GateTemplate) doc).getReceiverInternalId()).getUuid()
				);
				return MonitoringDocumentType.SPT;
			}

			if (!(doc instanceof BusinessDocument))
				return null;

			BusinessDocument bd = (BusinessDocument) doc;

			//Платеж Яндекс.Деньги.
			boolean yandexMoney = !(bd instanceof JurPayment) && (bd instanceof RurPayment) && (((RurPayment)bd).isServiceProviderPayment());
			//Оплата услуг.
			boolean serviceProvider = (bd instanceof JurPayment) && ((JurPayment) bd).getReceiverInternalId() != null;

			if (!yandexMoney && !serviceProvider)
				return null;

			entry.setProviderUuid(
					SERVICE_PROVIDER_SERVICE.findShortProviderById(
							yandexMoney ? ConfigFactory.getConfig(ProvidersConfig.class).getYandexPaymentId() : ((JurPayment) bd).getReceiverInternalId()
					).getUuid()
			);

			if (bd instanceof GateTemplate)
			{
				return MonitoringDocumentType.SPT;
			}

			//Создание автоплатежа.
			if (bd.getType() == CreateAutoPayment.class)
			{
				return MonitoringDocumentType.SPAP;
			}

			//Платеж по шаблону.
			if (bd.isByTemplate())
			{
				return MonitoringDocumentType.SPPBT;
			}

			//Платже из корзины платежей.
			if (bd.isMarkReminder())
			{
				return MonitoringDocumentType.SPPBR;
			}

			return MonitoringDocumentType.SPP;
		}
	}

	private static final Map<Class<? extends SynchronizableDocument>, DocumentTypeChoiser> MONITORING_TYPES = new HashMap<Class<? extends SynchronizableDocument>, DocumentTypeChoiser>();

	static
	{
		//Карта на счет в Сбербанке.
		MONITORING_TYPES.put(CardIntraBankPayment.class, new SimpleDocymentTypeChoiser(MonitoringDocumentType.CAOTB));
		//Карта на счет в другом банке
		MONITORING_TYPES.put(CardRUSPayment.class, new SimpleDocymentTypeChoiser(MonitoringDocumentType.CAOB));
		//Карта-Вклад
		MONITORING_TYPES.put(CardToAccountTransfer.class, new SimpleDocymentTypeChoiser(MonitoringDocumentType.CA));
		//Вклад-Вклад
		MONITORING_TYPES.put(ClientAccountsTransfer.class, new SimpleDocymentTypeChoiser(MonitoringDocumentType.AA));
		//Вклад-Карта
		MONITORING_TYPES.put(AccountToCardTransfer.class, new SimpleDocymentTypeChoiser(MonitoringDocumentType.AC));
		//Карта-Карта не Сбербанка (ММС, VMT)
		MONITORING_TYPES.put(ExternalCardsTransferToOtherBank.class, new SimpleDocymentTypeChoiser(MonitoringDocumentType.CCNS));
		//Карта-Карта Сбербанка
		MONITORING_TYPES.put(ExternalCardsTransferToOurBank.class, new SimpleDocymentTypeChoiser(MonitoringDocumentType.CCS));
		//Карта-Карта Сбербанка
		MONITORING_TYPES.put(InternalCardsTransfer.class, new SimpleDocymentTypeChoiser(MonitoringDocumentType.CCS));
		//Открытие вклада
		MONITORING_TYPES.put(AccountOpeningClaim.class, new SimpleDocymentTypeChoiser(MonitoringDocumentType.AOC));
		MONITORING_TYPES.put(DemandAccountOpeningClaim.class, new SimpleDocymentTypeChoiser(MonitoringDocumentType.AOC));
		MONITORING_TYPES.put(AccountOpeningFromCardClaim.class, new SimpleDocymentTypeChoiser(MonitoringDocumentType.AOC));
		//Оплата услуг
		MONITORING_TYPES.put(CardPaymentSystemPayment.class, new JurPaymentDocumentTypeChoiser());
		//Заявка на кредит.
		MONITORING_TYPES.put(ETSMLoanClaim.class, new SimpleDocymentTypeChoiser(MonitoringDocumentType.CREDIT));
	}

	public void process(StateObject document, StateMachineEvent stateMachineEvent) throws DocumentLogicException, DocumentException
	{
		MonitoringOperationConfig config = ConfigFactory.getConfig(MonitoringOperationConfig.class);
		if (!config.isActive())
			return;

		String state = getParameter(DOCUMENT_STATE);

		if (!(document instanceof SynchronizableDocument))
			throw new DocumentException("Документ должен быть " + SynchronizableDocument.class.getName());

		SynchronizableDocument doc = (SynchronizableDocument) document;
		MonitoringLogEntry entry = new MonitoringLogEntry();
		MonitoringDocumentType docType = getDocumentType(doc, entry);
		if (docType == null)
			return;

		entry.setStateCode(state);
		entry.setStartDate(Calendar.getInstance());
		entry.setNodeId(ConfigFactory.getConfig(ApplicationAutoRefreshConfig.class).getNodeNumber());
		entry.setDocumentType(docType.name());
		entry.setApplication(doc.getClientCreationChannel().name());
		if (doc instanceof BusinessDocument)
		{
			entry.setCreationDate(((BusinessDocument)doc).getDateCreated());
		}
		else if (doc instanceof GateTemplate)
		{
			entry.setCreationDate(doc.getClientCreationDate());
		}
		else
		{
			entry.setCreationDate(entry.getStartDate());
		}

		try
		{
			entry.setPlatform(AuthenticationContext.getContext().getDeviceInfo());
		}
		catch (Exception e)
		{
			PhizICLogFactory.getLog(LogModule.Core).warn(e);
		}

		if (docType.isInternal() || docType.isAccountOpen())
		{
			try
			{
				entry.setTb(new SBRFOfficeAdapter(doc.getOffice()).getCode().getRegion());
			}
			catch (GateException e)
			{
				PhizICLogFactory.getLog(LogModule.Core).error(e);
			}
		}

		if (doc instanceof AbstractPaymentDocument && docType.isInternal())
		{
			Money money = ((AbstractPaymentDocument) doc).getChargeOffAmount() == null ? ((AbstractPaymentDocument) doc).getDestinationAmount() : ((AbstractPaymentDocument) doc).getChargeOffAmount();
			if (money.getCurrency().getNumber().equals("810") || money.getCurrency().getNumber().equals("643"))
			{
				entry.setAmount(money.getDecimal());
				entry.setAmountCurrency(money.getCurrency().getCode());
			}
		}

		try
		{
			if (doc instanceof AccountOpeningClaim)
			{
				entry.setAccountType(Long.toString(((AccountOpeningClaim) doc).getAccountType()));
				AccountTarget target = TARGET_SERIVCE.findByClaim((com.rssl.phizic.business.documents.AccountOpeningClaim) doc);

				if (target != null)
					entry.setDocumentType(MonitoringDocumentType.AOC_ALF.name());
			}
		}
		catch (Exception e)
		{
			PhizICLogFactory.getLog(LogModule.Core).error(e);
		}

		try
		{
			config.writeLog(entry);
		}
		catch (Exception e)
		{
			PhizICLogFactory.getLog(LogModule.Core).error(e);
		}
	}

	private MonitoringDocumentType getDocumentType(SynchronizableDocument doc, MonitoringLogEntry entry) throws DocumentException
	{

		try
		{
			DocumentTypeChoiser ch = MONITORING_TYPES.get(doc.getType());
			if (ch == null)
				return null;

			return ch.getType(doc, entry);
		}
		catch (BusinessException e)
		{
			throw new DocumentException(e);
		}
	}
}
