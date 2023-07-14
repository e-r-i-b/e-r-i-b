package com.rssl.phizicgate.esberibgate.documents.senders;

import com.rssl.phizic.common.types.DateSpan;
import com.rssl.phizic.common.types.commission.WriteDownOperationHelper;
import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.commission.BackRefCommissionTBSettingService;
import com.rssl.phizic.gate.documents.GateDocument;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.payments.AccountClosingPayment;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.utils.DateHelper;
import com.rssl.phizic.utils.DateSpanFormat;
import com.rssl.phizic.utils.MoneyHelper;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizicgate.esberibgate.statistics.exception.ESBERIBExceptionStatisticHelper;
import com.rssl.phizicgate.esberibgate.statistics.exception.ExternalSystemErrorCodeException;
import com.rssl.phizicgate.esberibgate.statistics.exception.ExternalSystemTimeoutErrorCodeException;
import com.rssl.phizicgate.esberibgate.ws.generated.*;
import org.apache.commons.lang.StringUtils;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.Date;

import static org.apache.commons.lang.StringUtils.strip;

/**
 * Базовый класс для отсылки заявок на закрытие вкладов
 * @author Pankin
 * @ created 28.11.2011
 * @ $Author$
 * @ $Revision$
 */

public abstract class AccountClosingPaymentSenderBase extends ClientAccountsTransferSender
{
	protected static final Log log = PhizICLogFactory.getLog(Constants.LOG_MODULE_GATE);

	public AccountClosingPaymentSenderBase(GateFactory factory) throws GateException
	{
		super(factory);
	}

	protected void processPrepareResponse(GateDocument document, IFXRs_Type ifxRs) throws GateLogicException, GateException
	{
		super.processResponse(document, ifxRs);
		saveInfoMessages(document, ifxRs);
		BackRefCommissionTBSettingService commissionTBSerivice = getFactory().service(BackRefCommissionTBSettingService.class);
		if(commissionTBSerivice.isCalcCommissionSupport(document))
		{
			fillCommissions(document, ifxRs);
		}
		fillAmounts(document, ifxRs);
	}

	protected void saveInfoMessages(GateDocument document, IFXRs_Type ifxRs)
	{
		AccountClosingPayment payment = (AccountClosingPayment) document;
		DepInfo_Type depInfo = getDepInfo(ifxRs);
		payment.setAgreementViolation(appendAgreementViolationMessage(depInfo, payment.getTariffClosingMsg()));
		payment.setLongOffertFormalized(appendLongOffertFormalizedMessage(depInfo));
	}

	protected DepInfo_Type getDepInfo(IFXRs_Type ifxRs)
	{
		return ifxRs.getXferAddRs().getAgreemtInfo().getDepInfo();
	}

	protected String appendAgreementViolationMessage(DepInfo_Type depInfo, boolean createTariffDependenceClosingMsg)
	{
		Long days2End = depInfo.getDaysToEndOfSaving();
		String date2End = depInfo.getDateToEndOfSaving();
		IntRate_Type rate = depInfo.getEarlyTermRate();

		if (days2End == null && StringHelper.isEmpty(date2End) && rate == null)
		{
			return null;
		}

		boolean saveAgreementViolation = true;
		if ( days2End == null && (!StringHelper.isEmpty(date2End)  || rate != null) )
		{
			log.warn("Ошибка в ответе при закрытии вклада! Не пришло поле - DaysToEndOfSaving");
			saveAgreementViolation = false;
		}

		if ( rate == null && (!StringHelper.isEmpty(date2End)  || days2End != null) )
		{
			log.warn("Ошибка в ответе при закрытии вклада! Не пришло поле - EarlyTermRate");
			saveAgreementViolation= false;
		}

		if ( StringHelper.isEmpty(date2End) && (rate != null || days2End != null) )
		{
			log.warn("Ошибка в ответе при закрытии вклада! Не пришло поле - DateToEndOfSaving");
			saveAgreementViolation = false;
		}

		// Если до окончания срока хранения вклада осталось 0 дней, никаких сообщений не нужно.
		if (!saveAgreementViolation || days2End.equals(Long.valueOf(0L)))
		{
			return null;
		}

		try
		{
			Date endDate = DateHelper.fromXMlDateToDate(date2End);
			date2End = String.format("%1$td.%1$tm.%1$tY", endDate);
		}
		catch (ParseException ignored)
		{
			log.warn("Ошибка в ответе при закрытии вклада! Неверный формат даты [" +date2End+ "]");
		}

		StringBuilder builder = new StringBuilder();
		builder.append("Если Вы закроете вклад, то будет прерван срок хранения вклада. Срок хранения вклада заканчивается через");
		builder.append(" ");
		builder.append(DateSpanFormat.format(new DateSpan(0, 0, days2End.intValue())));
		builder.append(" - ");
		builder.append(date2End);
		builder.append(" ");
		builder.append("года. При закрытии вклада сегодня проценты будут");
		if(createTariffDependenceClosingMsg)
		{
			builder.append(" пересчитаны в соответствии с «Порядком начисления дохода при досрочном закрытии вклада» Условий вклада на дату открытия (пролонгации). ");
		}
		else
		{
			builder.append(" начислены из расчёта ");
			builder.append(MoneyHelper.formatAmount( rate.getRate() ));
			builder.append("% годовых. ");
		}
		builder.append("Если Вы согласны с данными условиями, то нажмите на кнопку ");

		return builder.toString();
	}

	protected String appendLongOffertFormalizedMessage(DepInfo_Type depInfo)
	{
		if (depInfo.getHaveForm190())
		{
			return "По закрываемому вкладу создан автоплатеж.";
		}
		return null;
	}

	/**
	 * Получение суммы списания по документу согласно микрооперациям списания.
	 * равное сумме всех микроопераций с caption='Закрытие счета'
	 * @param srcLayoutInfo - информация о микрооперациях списания
	 * @return сумма списания.
	 */
	protected BigDecimal getSrcAmountFromSrcLayoutInfo(SrcLayoutInfo_Type srcLayoutInfo)
	{
		WriteDownOperation_Type[] commissions = srcLayoutInfo.getWriteDownOperation();
		BigDecimal srcCurAmt = BigDecimal.ZERO;
		if (commissions != null)
		{
			for (WriteDownOperation_Type microOperation : commissions)
			{
				if(StringUtils.equalsIgnoreCase(strip(microOperation.getOperationName()), strip(WriteDownOperationHelper.ACCOUNT_CLOSING_OPERATION_NAME)))
					srcCurAmt = srcCurAmt.add(microOperation.getCurAmt());
			}
		}
		return srcCurAmt;
	}

	protected abstract void fillAmounts(GateDocument document, IFXRs_Type ifxRs) throws GateException, GateLogicException;

	@Override
	public void prepare(GateDocument document) throws GateException, GateLogicException
	{
		IFXRq_Type ifxRq = createPrepareRequest(document);
		IFXRs_Type ifxRs = doRequest(ifxRq);
		try
		{
			processPrepareResponse(document, ifxRs);
		}
		catch (ExternalSystemErrorCodeException e)
		{
			ESBERIBExceptionStatisticHelper.process(e, ifxRq);
		}
		catch (ExternalSystemTimeoutErrorCodeException e)
		{
			ESBERIBExceptionStatisticHelper.process(e, ifxRq);
		}
	}
}
