package com.rssl.phizic.operations.card;

import com.rssl.common.forms.doc.DocumentEvent;
import com.rssl.common.forms.processing.FormProcessor;
import com.rssl.common.forms.processing.StringErrorCollector;
import com.rssl.common.forms.sources.MapValuesSource;
import com.rssl.common.forms.state.ObjectEvent;
import com.rssl.common.forms.validators.strategy.DocumentValidationStrategy;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.SimpleService;
import com.rssl.phizic.business.cards.ReportByCardClaimSource;
import com.rssl.phizic.business.documents.BusinessDocumentService;
import com.rssl.phizic.business.documents.ReportByCardClaim;
import com.rssl.phizic.business.documents.metadata.Metadata;
import com.rssl.phizic.business.payments.PaymentsConfig;
import com.rssl.phizic.business.resources.external.CardLink;
import com.rssl.phizic.business.dictionaries.bankroll.SendedAbstract;
import com.rssl.phizic.common.types.exceptions.InactiveExternalSystemException;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.bankroll.BankrollNotificationService;
import com.rssl.phizic.gate.bankroll.CardLevel;
import com.rssl.phizic.gate.bankroll.ReportDeliveryLanguage;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.logging.system.LogModule;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.operations.EditEntityOperation;
import com.rssl.phizic.operations.OperationBase;
import com.rssl.phizic.context.PersonData;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.operations.payment.EditDocumentOperationBase;
import com.rssl.phizic.utils.DateHelper;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author rydvanskiy
 * @ created 13.06.2010
 * @ $Author$
 * @ $Revision$
 */

public class AddSendedAbstractsOperation extends OperationBase implements EditEntityOperation
{
	private static final Map<CardLevel, String> cardTypeToSettingName = new HashMap<CardLevel, String>();

	static {
		cardTypeToSettingName.put(CardLevel.A1, PaymentsConfig.AMEX);
		cardTypeToSettingName.put(CardLevel.A3, PaymentsConfig.AMEX);
		cardTypeToSettingName.put(CardLevel.A4, PaymentsConfig.AMEX);

		cardTypeToSettingName.put(CardLevel.VB, PaymentsConfig.VISA);
		cardTypeToSettingName.put(CardLevel.VC, PaymentsConfig.VISA);
		cardTypeToSettingName.put(CardLevel.VE, PaymentsConfig.VISA);
		cardTypeToSettingName.put(CardLevel.VG, PaymentsConfig.VISA);
		cardTypeToSettingName.put(CardLevel.VI, PaymentsConfig.VISA);
		cardTypeToSettingName.put(CardLevel.VP, PaymentsConfig.VISA);
		cardTypeToSettingName.put(CardLevel.VT, PaymentsConfig.VISA);
		cardTypeToSettingName.put(CardLevel.VV, PaymentsConfig.VISA);
		cardTypeToSettingName.put(CardLevel.VR, PaymentsConfig.VISA);
		cardTypeToSettingName.put(CardLevel.VQ, PaymentsConfig.VISA);

		cardTypeToSettingName.put(CardLevel.CM, PaymentsConfig.MASTER_CARD);
		cardTypeToSettingName.put(CardLevel.CT, PaymentsConfig.MASTER_CARD);
		cardTypeToSettingName.put(CardLevel.MB, PaymentsConfig.MASTER_CARD);
		cardTypeToSettingName.put(CardLevel.MG, PaymentsConfig.MASTER_CARD);
		cardTypeToSettingName.put(CardLevel.MP, PaymentsConfig.MASTER_CARD);
		cardTypeToSettingName.put(CardLevel.MS, PaymentsConfig.MASTER_CARD);
		cardTypeToSettingName.put(CardLevel.MV, PaymentsConfig.MASTER_CARD);
		cardTypeToSettingName.put(CardLevel.MQ, PaymentsConfig.MASTER_CARD);
		cardTypeToSettingName.put(CardLevel.MR, PaymentsConfig.MASTER_CARD);
		cardTypeToSettingName.put(CardLevel.MW, PaymentsConfig.MASTER_CARD);
	}

	private SendedAbstract sendedAbstract;

	private SimpleService simpleService = new SimpleService();
	private BusinessDocumentService businessDocumentService = new BusinessDocumentService();
	private String format;
	private String lang;
	private String cardType;
	private boolean asVypiskaActive;
	private boolean activePDF_RUS;
	private boolean activePDF_ENG;
	private boolean activeHTML_RUS;
	private boolean activeHTML_ENG;

	public SendedAbstract getEntity()
	{
		return sendedAbstract;
	}

	public void initialize(Long cardId)  throws BusinessException
	{
		//Неоходимо для проверки принадлежности карты конкретному пользователю
		PersonData personData = PersonContext.getPersonDataProvider().getPersonData();
		CardLink cardLink = personData.getCard(cardId);
		sendedAbstract = new SendedAbstract();
		sendedAbstract.setCardLink(cardLink);

		PaymentsConfig config = ConfigFactory.getConfig(PaymentsConfig.class);
		asVypiskaActive = config.isAsVipiskaActive();
		if (asVypiskaActive)
		{
			cardType = cardTypeToSettingName.get(cardLink.getCard().getCardLevel());

			//Неподдерживаемый тип карты.
			if (cardType == null)
			{
				asVypiskaActive = false;
				return;
			}

			//Для данной карты не доступна выписка через новую АС "Выписка".
			if (!config.isAsVypiskaForCardType(cardType, null, null))
			{
				asVypiskaActive = false;
				return;
			}

			if (cardLink.getReportDeliveryType() != null)
				format = cardLink.getReportDeliveryType().name();
			else
				format = PaymentsConfig.PDF;

			if (cardLink.getReportDeliveryLanguage() != null)
				lang = cardLink.getReportDeliveryLanguage() == ReportDeliveryLanguage.RU ? PaymentsConfig.RUS : PaymentsConfig.ENG;
			else
				lang = PaymentsConfig.RUS;

			activePDF_RUS = config.isAsVypiskaForCardType(cardType, PaymentsConfig.PDF, PaymentsConfig.RUS);
			activePDF_ENG = config.isAsVypiskaForCardType(cardType, PaymentsConfig.PDF, PaymentsConfig.ENG);
			activeHTML_RUS = config.isAsVypiskaForCardType(cardType, PaymentsConfig.HTML, PaymentsConfig.RUS);
			activeHTML_ENG = config.isAsVypiskaForCardType(cardType, PaymentsConfig.HTML, PaymentsConfig.ENG);
			if (!config.isAsVypiskaForCardType(cardType, format, lang)) {
				if (activePDF_RUS)
				{
					format = PaymentsConfig.PDF;
					lang = PaymentsConfig.RUS;
					return;
				}
				else if (activeHTML_RUS)
				{
					format = PaymentsConfig.HTML;
					lang = PaymentsConfig.RUS;
					return;
				}
				else if (activePDF_ENG)
				{
					format = PaymentsConfig.PDF;
					lang = PaymentsConfig.ENG;
					return;
				}
				else
				{
					format = PaymentsConfig.HTML;
					lang = PaymentsConfig.ENG;
					return;
				}
			}
		}
	}

	public boolean isActiveHTML_ENG()
	{
		return activeHTML_ENG;
	}

	public boolean isActiveHTML_RUS()
	{
		return activeHTML_RUS;
	}

	public boolean isActivePDF_ENG()
	{
		return activePDF_ENG;
	}

	public boolean isActivePDF_RUS()
	{
		return activePDF_RUS;
	}

	public boolean isAsVypiskaActive()
	{
		return asVypiskaActive;
	}

	public String getCardType()
	{
		return cardType;
	}

	public String getFormat()
	{
		return format;
	}

	public String getLang()
	{
		return lang;
	}

	public void save() throws BusinessException
	{
		simpleService.add(sendedAbstract);
	}

	/**
	 * отправка заявки на выписку.
	 *
	 * @param email адрес.
	 * @param format формат отчета.
	 * @param lang язык отчета.
	 * @param fillReport тип отчета.
	 * @throws BusinessException
	 * @throws BusinessLogicException
	 */
	public void sendReport(String email, String format, String lang, String fillReport) throws BusinessException, BusinessLogicException
	{
		BankrollNotificationService bankrollNotificationService = GateSingleton.getFactory().service(BankrollNotificationService.class);

		try
		{
			if (asVypiskaActive)
			{
				ReportByCardClaimSource source = new ReportByCardClaimSource(sendedAbstract.getCardLink(), email, format, lang, fillReport, sendedAbstract.getFromDate(), sendedAbstract.getToDate());

				//проверка данных и сохранение платежа
				EditDocumentOperationBase editOperation = new EditDocumentOperationBase()
				{
					@Override
					public Long save() throws BusinessException, BusinessLogicException
					{
						try
						{
							executor.fireEvent(new ObjectEvent(DocumentEvent.SAVE, getSourceEvent()));
						}
						catch (BusinessException e)
						{
							log.warn(e.getMessage());
							executor.fireEvent(new ObjectEvent(DocumentEvent.ERROR, getSourceEvent()));
							throw e;
						}
						catch (InactiveExternalSystemException e)
						{
							log.warn(e.getMessage());
							executor.fireEvent(new ObjectEvent(DocumentEvent.ERROR, getSourceEvent()));
							throw e;
						}
						catch (BusinessLogicException e)
						{
							log.warn(e.getMessage());
							executor.fireEvent(new ObjectEvent(DocumentEvent.ERROR, getSourceEvent()));
							throw e;
						}
						catch (Exception e)
						{
							log.warn(e.getMessage());
							executor.fireEvent(new ObjectEvent(DocumentEvent.ERROR, getSourceEvent()));
							throw new BusinessException(e);
						}
					    saveDocument();

						resetConfirmStrategy();
				        return document.getId();
					}
				};
				editOperation.initialize(source);

				Metadata metadata = editOperation.getMetadata();
				FormProcessor<List<String>, StringErrorCollector> formProcessor = new FormProcessor<List<String>, StringErrorCollector>(
									new MapValuesSource(Collections.EMPTY_MAP), metadata.getForm(), new StringErrorCollector(), DocumentValidationStrategy.getInstance());

				if (!formProcessor.process())
				{
					PhizICLogFactory.getLog(LogModule.Core).error(formProcessor.getErrors().get(0));
					throw new BusinessLogicException("Ошибка валидации документа");
				}

				editOperation.updateDocument(formProcessor.getResult());
				editOperation.save();
			}
			else
			{
				bankrollNotificationService.sendCardReport(sendedAbstract.getCardLink().getCard(), email,
					sendedAbstract.getFromDate(), sendedAbstract.getToDate());
			}
		}
		catch (GateException e)
		{
			throw new BusinessException(e);
		}
		catch (GateLogicException e)
		{
			throw new BusinessLogicException(e);
		}
	}

	/**
	 * История заказов отчета.
	 *
	 * @param cardId карта.
	 * @return история заказа отчетов.
	 * @throws BusinessException
	 */
	public List<String[]> getHistory(final long cardId) throws BusinessException
	{
		PersonData personData = PersonContext.getPersonDataProvider().getPersonData();
		String cardNumber = personData.getCard(cardId).getNumber();
		List<String[]> list = new LinkedList<String[]>();

		Calendar cal = Calendar.getInstance();
		cal.setTime(DateHelper.add(cal.getTime(), 0, -3, 0));
		List<ReportByCardClaim> claims = businessDocumentService.findReportByCardClaim(personData.getLogin(), cardNumber, cal);

		for (ReportByCardClaim claim : claims)
		{
			String[] strs = new String[3];
			strs[0] = Long.toString(claim.getId());
			strs[1] = DateHelper.formatDateWithMonthString(claim.getClientCreationDate());

			if (claim.getReportOrderType().equals("ER"))
			{
				strs[2] = new SimpleDateFormat("MMMM yyyy").format(claim.getReportStartDate().getTime());
			}
			else
			{
				Calendar startDate = claim.getReportStartDate();
				Calendar endDate = claim.getReportEndDate();
				if (startDate.get(Calendar.YEAR) == endDate.get(Calendar.YEAR) && startDate.get(Calendar.MONTH) == endDate.get(Calendar.MONTH))
				{
					strs[2] = startDate.get(Calendar.DAY_OF_MONTH) + " &mdash; " + DateHelper.formatDateWithMonthString(endDate);
				}
				else
				{
					strs[2] = DateHelper.formatDateWithMonthString(startDate) + " &mdash; " + DateHelper.formatDateWithMonthString(endDate);
				}
			}
			list.add(strs);
		}

		return list;
	}
}
