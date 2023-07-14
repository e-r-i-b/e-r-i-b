package com.rssl.phizic.operations.finances.targets;

import com.rssl.common.forms.FormConstants;
import com.rssl.common.forms.doc.CreationSourceType;
import com.rssl.common.forms.doc.CreationType;
import com.rssl.common.forms.doc.DocumentEvent;
import com.rssl.common.forms.doc.StateMachineEvent;
import com.rssl.common.forms.processing.FieldValuesSource;
import com.rssl.common.forms.sources.MapValuesSource;
import com.rssl.common.forms.state.ObjectEvent;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.documents.AccountOpeningClaim;
import com.rssl.phizic.business.documents.payments.BusinessDocument;
import com.rssl.phizic.business.documents.payments.CreateMoneyBoxPayment;
import com.rssl.phizic.business.documents.payments.source.DocumentSource;
import com.rssl.phizic.business.documents.payments.source.NewDocumentSource;
import com.rssl.phizic.business.resources.external.AccountLink;
import com.rssl.phizic.business.resources.external.CardLink;
import com.rssl.phizic.business.resources.external.ExternalResourceService;
import com.rssl.phizic.business.statemachine.StateMachineExecutor;
import com.rssl.phizic.context.PersonContext;
import com.rssl.phizic.gate.documents.InputSumType;
import com.rssl.phizic.operations.payment.ConfirmFormPaymentOperation;
import com.rssl.phizic.operations.payment.support.DbDocumentTarget;
import com.rssl.phizic.security.SecurityLogicException;
import com.rssl.phizic.utils.DateHelper;
import com.rssl.phizic.utils.StringHelper;

import java.text.DateFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * @author vagin
 * @ created 18.10.14
 * @ $Author$
 * @ $Revision$
 * Операция подтверждения открытия вклада для цели с подключением копилки.
 */
public class ConfirmAccountTargetWithMoneyBoxOperation extends ConfirmFormPaymentOperation
{
	private static final DbDocumentTarget documentTarget = new DbDocumentTarget();

	public void saveConfirm() throws BusinessException, BusinessLogicException, SecurityLogicException
	{
		super.saveConfirm();
		AccountOpeningClaim claim = (AccountOpeningClaim) getDocument();
		//если создание цели(открытие вклада) с поключением копилки.
		try
		{
			if (claim.getCreateMoneyBox())
			{
				processCreateMoneyBox(claim);
			}
		}
		catch (Exception e)
		{
			//не должно влиять на результат открытия вклада.
			log.error(e.getMessage(), e);
			getMessageCollector().addMessage("По техническим причинам не удалось подключить копилку для открываемой цели.");
		}
	}

	/**
	 * Заполняем заявку на создание копилки данными из документа на открытие вклада и подтвержадем ее.
	 * @param claim - заявка на открытие вклада.
	 */
	private void processCreateMoneyBox(AccountOpeningClaim claim) throws BusinessLogicException, BusinessException, ParseException
	{
		//если по какой то причине не удалось получить номер открытого вклада - отображаем клиенту.
		if (claim.getReceiverAccount() == null)
			throw new BusinessLogicException("По техническим причинам для открываемой цели не удалось подключить копилку.");
		//пересечиваем из UpdateAccountLinksHandler, на случай если в хендлере будет убрано.
		PersonContext.getPersonDataProvider().getPersonData().setNeedReloadAccounts();
		AccountLink toResource = PersonContext.getPersonDataProvider().getPersonData().findAccount(claim.getReceiverAccount());
		if (toResource == null)
			throw new BusinessLogicException("По техническим причинам для открываемой цели не удалось подключить копилку.");

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("form", FormConstants.CREATE_MONEY_BOX_FORM);
		String fromCard = claim.getMoneyBoxFromResourceCode();
		map.put("fromResource", fromCard);
		ExternalResourceService resourceService = new ExternalResourceService();
		CardLink fromCardLink = resourceService.findLinkById(CardLink.class, Long.valueOf(fromCard.substring(CardLink.CODE_PREFIX.length() + 1)));
		if (fromCardLink == null)
			throw new BusinessException("Карта списания для создаваемой копилки не принадлежит клиенту.");

		map.put("fromResourceType", fromCardLink.getClass().getName());
		map.put("fromAccountSelect", fromCardLink.getNumber());
		map.put("fromResourceCurrency", fromCardLink.getCurrency().getCode());
		map.put("fromAccountName", fromCardLink.getName());
		map.put("moneyBoxSumType", claim.getMoneyBoxSumType());
		map.put("moneyBoxName", claim.getMoneyBoxName());
		map.put("percent", claim.getMoneyBoxPercent());
		map.put("sellAmount", claim.getMoneyBoxSellAmount());

		String startDate = claim.getMoneyBoxStartDate();
		if (StringHelper.isNotEmpty(startDate))
		{
			SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yy", new DateFormatSymbols(new Locale("ru")));
			map.put("longOfferStartDate", DateHelper.toXMLDateFormat(sdf.parse(startDate)));
		}

		map.put("longOfferEventType", claim.getMoneyBoxEventType());
		map.put("exactAmount", InputSumType.CHARGEOFF.toValue());
		map.put("toAccountName", toResource.getName());
		map.put("toResourceCurrency", toResource.getCurrency().getCode());
		map.put("toResourceType", toResource.getClass().getName());

		CreateMoneyBoxPayment moneyBoxClaim = (CreateMoneyBoxPayment) createDocument(map);
		moneyBoxClaim.setReceiverAccount(claim.getReceiverAccount());
		moneyBoxClaim.setOffice(moneyBoxClaim.getDepartment());

		executor = new StateMachineExecutor(stateMachineService.getStateMachineByFormName(moneyBoxClaim.getFormName()));
		StateMachineEvent stateMachineEvent = new StateMachineEvent();
		executor.setStateMachineEvent(stateMachineEvent);
		executor.initialize(moneyBoxClaim);
		executor.fireEvent(new ObjectEvent(DocumentEvent.CONFIRM, ObjectEvent.CLIENT_EVENT_TYPE));
		documentTarget.save(moneyBoxClaim);
	}

	private BusinessDocument createDocument(Map<String, Object> values) throws BusinessException, BusinessLogicException
	{
		FieldValuesSource valuesSource = new MapValuesSource(values);
		DocumentSource source = new NewDocumentSource(FormConstants.CREATE_MONEY_BOX_FORM, valuesSource, CreationType.internet, CreationSourceType.ordinary);
		return source.getDocument();
	}
}
