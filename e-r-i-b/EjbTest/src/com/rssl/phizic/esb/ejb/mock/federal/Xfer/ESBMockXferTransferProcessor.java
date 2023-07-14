package com.rssl.phizic.esb.ejb.mock.federal.Xfer;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.dictionaries.tariffPlan.TariffPlanHelper;
import com.rssl.phizic.common.types.commission.WriteDownOperationHelper;
import com.rssl.phizic.common.types.exceptions.InternalErrorException;
import com.rssl.phizic.esb.ejb.mock.ESBMessage;
import com.rssl.phizic.esb.ejb.mock.federal.FederalESBMockProcessorBase;
import com.rssl.phizic.module.Module;
import com.rssl.phizic.utils.RandomHelper;
import com.rssl.phizicgate.esberibgate.documents.senders.XferMethodType;
import com.rssl.phizicgate.esberibgate.ws.jms.segment.federal.generated.*;

import java.math.BigDecimal;
import java.util.*;

/**
 * @author akrenev
 * @ created 03.06.2015
 * @ $Author$
 * @ $Revision$
 *
 * заглушка, имитирующая работу шины при запросе перевода с карты на вклад
 */

public class ESBMockXferTransferProcessor extends FederalESBMockProcessorBase<XferAddRq, XferAddRs>
{
	private static final Long NOT_SUPPORT_IMA_PAYMENTS_CODE = -102L;
	private static final String SUPPORT_IMA_PAYMENTS_TB_CODE = "99";
	private static final List<WriteDownOperationType> writeDownOperationNames = new ArrayList<WriteDownOperationType>();

	static
	{
		writeDownOperationNames.add(getWriteDownOperation("Взимание комиссии", BigDecimal.ZERO, TurnoverType.CHARGE));
		writeDownOperationNames.add(getWriteDownOperation("Взимание платы за перевод", BigDecimal.ZERO, TurnoverType.CHARGE));
		writeDownOperationNames.add(getWriteDownOperation("Прочие списания", BigDecimal.ZERO, TurnoverType.CHARGE));
		writeDownOperationNames.add(getWriteDownOperation("Прочие причисления", BigDecimal.ZERO, TurnoverType.RECEIPT));
		writeDownOperationNames.add(getWriteDownOperation("Причисление процентов", BigDecimal.ZERO, TurnoverType.RECEIPT));
		writeDownOperationNames.add(getWriteDownOperation("Взимание излишне причисленных процентов", BigDecimal.ZERO, TurnoverType.CHARGE));
	}

	/**
	 * конструктор
	 * @param module модуль
	 */
	public ESBMockXferTransferProcessor(Module module)
	{
		super(module);
	}

	@Override
	protected boolean needSendResult(ESBMessage<XferAddRq> xmlRequest, XferAddRs message)
	{
		return getRandomBoolean();
	}

	@Override
	protected boolean needSendOnline(ESBMessage<XferAddRq> xmlRequest, XferAddRs message)
	{
		return getRandomBoolean();
	}

	@Override
	protected void process(ESBMessage<XferAddRq> xmlRequest)
	{
		XferAddRq xferAddRq = xmlRequest.getObject();
		XferAddRs xferAddRs = new XferAddRs();

		OperNameType operName = xferAddRq.getOperName();

		xferAddRs.setOperUID(xferAddRq.getOperUID());
		xferAddRs.setRqTm(xferAddRq.getRqTm());
		xferAddRs.setRqUID(xferAddRq.getRqUID());
		xferAddRs.setStatus(getStatus(xferAddRs, xferAddRq));

		appendInfoForIMATransfers(xferAddRs,   xferAddRq);
		send(xmlRequest, xferAddRs, getServiceName(xferAddRq, operName));
	}

	private String getServiceName(XferAddRq xferAddRq, OperNameType operName)
	{
		switch (operName)
		{
			case TCD: return "CardToAccountTransfer";
			case TDC: return "AccountToCardTransfer";
			case TDD: return "AccountToAccountTransfer";
			case TDI: return "AccountToIMATransfer";
			case TID: return "IMAToAccountTransfer";
			case TCP: return XferMethodType.EXTERNAL_BANK.toValue().equals(xferAddRq.getXferInfo().getXferMethod())? "CardToAccountPhizIntraBankTransfer": "CardToAccountPhizOurBankTransfer";
			default: throw new InternalErrorException("Неизвестный тип операции " + operName);
		}
	}

	private StatusType getStatus(XferAddRs xferAddRs, XferAddRq xferAddRq)
	{
		//при подготовке платежа(prepare запрос) всегда возврщаем хоть какие-нибудь операции, по ним считаются суммы перевода.
		SrcLayoutInfoType srcLayoutInfo = xferAddRq.getXferInfo().getSrcLayoutInfo();
		OperNameType operName = xferAddRq.getOperName();
		if (srcLayoutInfo != null && srcLayoutInfo.getIsCalcOperation() && getRequestIndex() % 10 != 3)
		{
			SrcLayoutInfoType srcLayoutInfo_typeRs = new SrcLayoutInfoType();
			srcLayoutInfo_typeRs.getWriteDownOperations().addAll(generateWriteDownOperations(operName));
			xferAddRs.setSrcLayoutInfo(srcLayoutInfo_typeRs);
			return getErrorStatus(-433, null);
		}

		if (getRequestIndex() % 10 == 5)
			return getErrorStatus(500, "Тестовая заглушка отказывает каждый 10 платеж");

		return getOkStatus();
	}

	/**
	 *
	 * Дополняет ответ о переводе с вклада на омс или с омс на вклад. Если ТБ не поддерживает перевод в ответе
	 * будет возвращена ошибка -102.
	 *
	 * @param xferAddRs  ответ на запрос о переводе
	 * @param xferAddRq запрос на перевод с вклада на омс или наоборот
	 */
	private void appendInfoForIMATransfers(XferAddRs xferAddRs, XferAddRq xferAddRq)
	{
		if(xferAddRs.getStatus().getStatusCode() != 0 || xferAddRq.getOperName() != OperNameType.TDI && xferAddRq.getOperName() != OperNameType.TID)
			return;

		/*
		 * Если тербанк не поддерживает перевод, то код ошибки -102
		 */
		if (SUPPORT_IMA_PAYMENTS_TB_CODE.equals(xferAddRq.getBankInfo().getRbTbBrchId()))
			return;

		xferAddRs.setStatus(getStatusInstance(NOT_SUPPORT_IMA_PAYMENTS_CODE, "Территориальный банк клиента не поддерживает переводы", null, null));
	}

	/**
	 * Получение списка названий операций, обязательных для данного типа платежа
	 * @return список названий операций.
	 */
	private static List<String> getRequiredOperationNames(OperNameType type)
	{
		if (type == OperNameType.TDI || type == OperNameType.TDC || type == OperNameType.TCP || type == OperNameType.TDD)
			return Arrays.asList(WriteDownOperationHelper.PARTIAL_ISSUE_OPERATION_NAME);
		return Collections.emptyList();
	}

	/**
	 * Генерация микроопераций списания(комиссий)
	 * @param operNameType тип операции.
	 * @return массив микроопераций.
	 */
	public static List<WriteDownOperationType> generateWriteDownOperations(OperNameType operNameType)
	{
		Random rand = new Random();
		List<String> requiredOperationNames = getRequiredOperationNames(operNameType);
		int count = rand.nextInt(writeDownOperationNames.size()) + 1;
		List<WriteDownOperationType> result = new ArrayList<WriteDownOperationType>();

		int i = 0;
		for (; i < count; i++)
		{
			WriteDownOperationType source = writeDownOperationNames.get(rand.nextInt(writeDownOperationNames.size()));
			WriteDownOperationType operation = getWriteDownOperation(source.getOperationName(), new BigDecimal(RandomHelper.rand(2, RandomHelper.DIGITS)), source.getTurnover());
			result.add(operation);
		}
		//добавляем обязательные мирооперации
		for (; i < count + requiredOperationNames.size(); i++)
		{
			result.add(getWriteDownOperation(requiredOperationNames.get(i-count),new BigDecimal(RandomHelper.rand(3, RandomHelper.DIGITS)), TurnoverType.CHARGE));
		}

		if (operNameType == OperNameType.TCD || operNameType == OperNameType.TDC || operNameType == OperNameType.TDD || operNameType == OperNameType.TCP)
			result.add(createCommission());
		else
			result.add(getWriteDownOperation("Для данного платежа не рассчитывается льготная комиссия", BigDecimal.ZERO, TurnoverType.CHARGE));
		return result;
	}

	private static WriteDownOperationType createCommission()
	{
		try
		{
			return getWriteDownOperation("для ТП " + TariffPlanHelper.getActualTariffPlanByCode("1").getName(), new BigDecimal(RandomHelper.rand(3, RandomHelper.DIGITS)), TurnoverType.CHARGE);
		}
		catch (BusinessException ignore)
		{
			return getWriteDownOperation("Комиссия по ТП не пришла", BigDecimal.ZERO, TurnoverType.CHARGE);
		}
	}

	private static WriteDownOperationType getWriteDownOperation(String name, BigDecimal amount, TurnoverType turnover)
	{
		WriteDownOperationType type = new WriteDownOperationType();
		type.setOperationName(name);
		type.setCurAmt(amount);
		type.setTurnover(turnover);
		return type;
	}
}
