package com.rssl.phizic.business.payments.forms.meta;

import com.rssl.common.forms.DocumentException;
import com.rssl.common.forms.DocumentLogicException;
import com.rssl.common.forms.doc.BusinessDocumentHandlerBase;
import com.rssl.common.forms.doc.StateMachineEvent;
import com.rssl.common.forms.state.StateObject;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.deposits.DepositProduct;
import com.rssl.phizic.business.deposits.DepositProductHelper;
import com.rssl.phizic.business.deposits.DepositProductService;
import com.rssl.phizic.business.documents.AccountOpeningClaim;
import com.rssl.phizic.business.documents.BusinessDocumentService;
import com.rssl.phizic.business.documents.payments.BusinessDocumentOwner;
import com.rssl.phizic.business.ext.sbrf.deposits.entities.DepositProductEntity;
import com.rssl.phizic.business.persons.PersonService;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.gate.documents.InputSumType;
import com.rssl.phizic.util.ApplicationUtil;
import com.rssl.phizic.utils.DepositConfig;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.utils.xml.XmlHelper;
import org.w3c.dom.Element;

import java.util.Map;

/**
 * Хэндлер для перки процентной ставки открываемого срочного вклада без неснижаемого остатка
 * @author Pankin
 * @ created 20.03.2012
 * @ $Author$
 * @ $Revision$
 */

public class AccountOpeningPercentHandler extends BusinessDocumentHandlerBase
{
	private static final String NOT_FOUND_RATE = "На введенных Вами условиях невозможно открыть вклад. " +
			"Пожалуйста, внимательно ознакомьтесь с условиями вклада, который хотите открыть.";
	private static final String CHANGE_RATE_MESSAGE = "Обратите внимание, изменились значения в выделенных полях!";

	private static final DepositProductService depositProductService = new DepositProductService();
	private static final BusinessDocumentService businessDocumentService = new BusinessDocumentService();

	public void process(StateObject document, StateMachineEvent stateMachineEvent) throws DocumentException, DocumentLogicException
	{
		if (!(document instanceof AccountOpeningClaim))
		{
			throw new DocumentException("Неверный тип платежа id=" + document.getId() +
					" (Ожидается AccountOpeningClaim)");
		}

		AccountOpeningClaim accountOpeningClaim = (AccountOpeningClaim) document;

		// Пересчитываем процентную ставку только для срочных вкладов без неснижаемого остатка
		if (accountOpeningClaim.isWithMinimumBalance() || !accountOpeningClaim.isNeedInitialFee())
			return;

		// Если операция не конверионная или клиент вводил сумму зачисления, то она не меняется, ничего пересчитывать не надо
		if (!accountOpeningClaim.isConvertion() || accountOpeningClaim.getInputSumType() == InputSumType.DESTINATION)
			return;

		Map<String, String> depInfo = null;
		String depositTariffPlanCode = accountOpeningClaim.getDepositTariffPlanCode();
		String clientTariffCode = accountOpeningClaim.getTarifPlanCodeType();
		Long depositType = accountOpeningClaim.getAccountType();
		String accountGroup = accountOpeningClaim.getAccountGroup();

		try
		{
			BusinessDocumentOwner documentOwner = accountOpeningClaim.getOwner();
			if (documentOwner.isGuest())
				throw new UnsupportedOperationException("Операция в гостевой сессии не поддерживается");

			if (ConfigFactory.getConfig(DepositConfig.class).isUseCasNsiDictionaries())
			{
				Long depositProductGroup = StringHelper.isEmpty(accountGroup) ? 0L : Long.valueOf(accountGroup);
				DepositProductEntity depositEntity = depositProductService.findDepositEntity(depositType, depositProductGroup);
				if (depositEntity == null)
					throw new DocumentException("В справочнике депозитных продуктов нет записи для вклада с номером " +
							accountOpeningClaim.getAccountType());

				depInfo = DepositProductHelper.calculateEntityRate(accountOpeningClaim.getOpeningDate(),
						accountOpeningClaim.getPeriod(), accountOpeningClaim.getDestinationCurrency().getCode(),
						accountOpeningClaim.getDestinationAmount().getDecimal(), accountOpeningClaim.isPension(),
						depositType, depositTariffPlanCode, clientTariffCode, accountGroup,
						accountOpeningClaim.getSegment());
			}
			else
			{
				DepositProduct depositProduct = depositProductService.findByProductId(accountOpeningClaim.getAccountType());

				if (depositProduct == null)
					throw new DocumentException("В справочнике депозитных продуктов нет записи для вклада с номером " +
							accountOpeningClaim.getAccountType());

				// Рассчитываем процентную ставку, номер вида и размер минимального доп. взноса
				Element description = XmlHelper.parse(depositProduct.getDescription()).getDocumentElement();
				depInfo = DepositProductHelper.calculateRate(accountOpeningClaim.getOpeningDate(),
						accountOpeningClaim.getPeriod(), accountOpeningClaim.getDestinationCurrency().getCode(),
						accountOpeningClaim.getDestinationAmount().getDecimal(), accountOpeningClaim.isPension(),
						description, depositTariffPlanCode, clientTariffCode, accountGroup,
						accountOpeningClaim.getSegment(), accountOpeningClaim.isPromoDepositProduct());

				processVerification(accountOpeningClaim, stateMachineEvent, depInfo);
			}
		}
		catch (BusinessException e)
		{
			throw new DocumentException(e);
		}
		catch (Exception e)
		{
			throw new DocumentException(e);
		}
	}

	private void processVerification(AccountOpeningClaim accountOpeningClaim, StateMachineEvent stateMachineEvent, Map<String, String> depInfo) throws DocumentException, DocumentLogicException
	{
		// По переданным параметрам не найден подходящий подвид
		if (depInfo.containsKey(DepositProductHelper.CALC_RATE_ERROR))
			throw new DocumentLogicException(NOT_FOUND_RATE);

		if (!accountOpeningClaim.getAttribute(AccountOpeningClaim.INTEREST_RATE).getStringValue().equals(
				depInfo.get(AccountOpeningClaim.INTEREST_RATE)))
		{
			// Обновляем поля платежа рассчитанными данными
			for (String key : depInfo.keySet())
			{
				accountOpeningClaim.setTermsPosition(key, depInfo.get(key));
			}

			try
			{
				// Обновляем документ в базе, чтобы не потерять пересчитанные данные
				businessDocumentService.addOrUpdate(accountOpeningClaim);
			}
			catch (BusinessException e)
			{
				throw new DocumentException(e);
			}

			stateMachineEvent.addMessage(CHANGE_RATE_MESSAGE);
			stateMachineEvent.registerChangedField("interestRate");
		}
	}
}
