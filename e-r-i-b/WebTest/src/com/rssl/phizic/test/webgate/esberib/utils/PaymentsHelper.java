package com.rssl.phizic.test.webgate.esberib.utils;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.dictionaries.tariffPlan.TariffPlanHelper;
import com.rssl.phizic.common.types.commission.WriteDownOperationHelper;
import com.rssl.phizic.test.webgate.esberib.generated.AgreemtInfo_Type;
import com.rssl.phizic.test.webgate.esberib.generated.DepInfo_Type;
import com.rssl.phizic.test.webgate.esberib.generated.IFXRq_Type;
import com.rssl.phizic.test.webgate.esberib.generated.IFXRs_Type;
import com.rssl.phizic.test.webgate.esberib.generated.IntRate_Type;
import com.rssl.phizic.test.webgate.esberib.generated.OperName_Type;
import com.rssl.phizic.test.webgate.esberib.generated.SrcLayoutInfo_Type;
import com.rssl.phizic.test.webgate.esberib.generated.Status_Type;
import com.rssl.phizic.test.webgate.esberib.generated.Turnover_Type;
import com.rssl.phizic.test.webgate.esberib.generated.WriteDownOperation_Type;
import com.rssl.phizic.test.webgate.esberib.generated.XferAddRq_Type;
import com.rssl.phizic.test.webgate.esberib.generated.XferAddRs_Type;
import com.rssl.phizic.utils.RandomHelper;
import com.rssl.phizic.utils.xml.XMLDatatypeHelper;
import com.rssl.phizicgate.esberibgate.documents.PaymentsRequestHelper;

import java.math.BigDecimal;
import java.util.*;

/**
 * @author krenev
 * @ created 10.09.2010
 * @ $Author$
 * @ $Revision$
 */
public class PaymentsHelper
{
	private static final Long NOT_SUPPORT_IMA_PAYMENTS_CODE = -102L;
	private static final String SUPPORT_IMA_PAYMENTS_TB_CODE = "99";
	private static final List<WriteDownOperation_Type> writeDownOperationNames = new ArrayList<WriteDownOperation_Type>();
	static
	{
		writeDownOperationNames.add(new WriteDownOperation_Type("Взимание комиссии", BigDecimal.ZERO, Turnover_Type.CHARGE));
		writeDownOperationNames.add(new WriteDownOperation_Type("Взимание платы за перевод", BigDecimal.ZERO, Turnover_Type.CHARGE));
		writeDownOperationNames.add(new WriteDownOperation_Type("Прочие списания", BigDecimal.ZERO, Turnover_Type.CHARGE));
		writeDownOperationNames.add(new WriteDownOperation_Type("Прочие причисления", BigDecimal.ZERO, Turnover_Type.RECEIPT));
		writeDownOperationNames.add(new WriteDownOperation_Type("Причисление процентов", BigDecimal.ZERO, Turnover_Type.RECEIPT));
		writeDownOperationNames.add(new WriteDownOperation_Type("Взимание излишне причисленных процентов", BigDecimal.ZERO, Turnover_Type.CHARGE));
	}

	private static int counter=0;

	public IFXRs_Type doIFX(IFXRq_Type parameters) throws java.rmi.RemoteException {
        XferAddRq_Type xferAddRq = parameters.getXferAddRq();
		XferAddRs_Type xferAddRs = new XferAddRs_Type();

		Status_Type status = new Status_Type();
		counter++;
		//если это предварительный расчет комиссий - добалвяем микрооперации.
		SrcLayoutInfo_Type srcLayoutInfo_type = xferAddRq.getXferInfo().getSrcLayoutInfo();
		boolean isWithPrepare = xferAddRq.getOperName() == OperName_Type.TDDC || xferAddRq.getOperName() == OperName_Type.TDDO || xferAddRq.getOperName() == OperName_Type.TDCC;
		//при подготовке платежа(prepare запрос) всегда возврщаем хоть какие-нибудь операции, по ним считаются суммы перевода.
		if (srcLayoutInfo_type != null && srcLayoutInfo_type.getIsCalcOperation() && (isWithPrepare || counter % 10 != 3))
		{
			SrcLayoutInfo_Type srcLayoutInfo_typeRs = new SrcLayoutInfo_Type();
			srcLayoutInfo_typeRs.setWriteDownOperation(generateWriteDownOperations(xferAddRq.getOperName()));
			xferAddRs.setSrcLayoutInfo(srcLayoutInfo_typeRs);
			//если TDDC, TDDO, TDCC - статус ответа от должен быть "0" - предварительный расчет.
			status.setStatusCode(isWithPrepare ? 0 : -433);
		}
		else if (counter % 10 == 5)
		{
			status.setStatusCode(500);
			status.setStatusDesc("Тестовая заглушка отказывает каждый 10 платеж");
		}
		else
		{
			status.setStatusCode(0);
		}
		xferAddRs.setOperUID(xferAddRq.getOperUID());
		xferAddRs.setRqTm(XMLDatatypeHelper.formatDateTimeWithoutTimeZone(Calendar.getInstance()));
		xferAddRs.setRqUID(PaymentsRequestHelper.generateUUID());
		xferAddRs.setStatus(status);

		appendInfoForAccountClosingPayment(xferAddRs, parameters);
		appendInfoForIMATransfers(xferAddRs,   parameters);

		IFXRs_Type response = new IFXRs_Type();
		response.setXferAddRs(xferAddRs);

		return response;
    }

	/**
	 * Генерация микроопераций списания(комиссий)
	 * @param operNameType тип операции.
	 * @return массив микроопераций.
	 */
	public static WriteDownOperation_Type[] generateWriteDownOperations(OperName_Type operNameType)
	{
		Random rand = new Random();
		List<String> requiredOperationNames = getRequiredOperationNames(operNameType);
		int count = rand.nextInt(writeDownOperationNames.size())+1;
		WriteDownOperation_Type[] result = new WriteDownOperation_Type[count + requiredOperationNames.size() + 1];

		int i = 0;
		for (; i < count; i++)
		{
			result[i] = writeDownOperationNames.get(rand.nextInt(writeDownOperationNames.size()));
			result[i].setCurAmt(new BigDecimal(RandomHelper.rand(2, RandomHelper.DIGITS)));
		}
		//добавляем обязательные мирооперации
		for (; i < count + requiredOperationNames.size(); i++)
		{
			result[i] = new WriteDownOperation_Type(requiredOperationNames.get(i-count),new BigDecimal(RandomHelper.rand(3, RandomHelper.DIGITS)), Turnover_Type.CHARGE);
		}
		if (operNameType == OperName_Type.TCD || operNameType == OperName_Type.TDC || operNameType == OperName_Type.TDD || operNameType == OperName_Type.TCP)
			result[count + requiredOperationNames.size()] = createCommission();
		else
			result[count + requiredOperationNames.size()] = new WriteDownOperation_Type("Для данного платежа не рассчитывается льготная комиссия", BigDecimal.ZERO, Turnover_Type.CHARGE);
		return result;
	}

	/**
	 * Получение списка названий операций, обязательных для данного типа платежа
	 * @return список названий операций.
	 */
	private static List<String> getRequiredOperationNames(OperName_Type type)
	{
		if (type == OperName_Type.TDI || type == OperName_Type.TDC || type == OperName_Type.TCP || type == OperName_Type.TDD || type == OperName_Type.TDDO || type == OperName_Type.TDIO)
			return Arrays.asList(WriteDownOperationHelper.PARTIAL_ISSUE_OPERATION_NAME);
		if (type == OperName_Type.TDDC || type == OperName_Type.TDCC || type == OperName_Type.TDDO)
			return Arrays.asList(WriteDownOperationHelper.ACCOUNT_CLOSING_OPERATION_NAME);
		return Collections.emptyList();
	}

	/**
	 *
	 * Дополняет ответ о переводе с вклада на омс или с омс на вклад. Если ТБ не поддерживает перевод в ответе
	 * будет возвращена ошибка -102.
	 *
	 * @param xferAddRs  ответ на запрос о переводе
	 * @param parameters запрос на перевод с вклада на омс или наоборот
	 * @return XferAddRs_Type дополненный ответ
	 */
	private XferAddRs_Type appendInfoForIMATransfers(XferAddRs_Type xferAddRs, IFXRq_Type parameters)
	{
		if(xferAddRs.getStatus().getStatusCode() != 0)
		{
			return xferAddRs;
		}

		XferAddRq_Type request_type = parameters.getXferAddRq();
		if (request_type.getOperName() == OperName_Type.TDI || request_type.getOperName() == OperName_Type.TID)
		{
			/*
			 * Если тербанк не поддерживает перевод, то код ошибки -102
			 */
			if (!isTBTransferSupported(request_type.getBankInfo().getRbTbBrchId()))
			{
				xferAddRs.setStatus(new Status_Type(NOT_SUPPORT_IMA_PAYMENTS_CODE, "Территориальный банк клиента не поддерживает переводы", null, null));
			}
		}
		return xferAddRs;
	}

	private boolean isTBTransferSupported(String rbTbBrchId)
	{
		if(true)               //TODO  убрать!
			return true;
		return rbTbBrchId.equals(SUPPORT_IMA_PAYMENTS_TB_CODE);
	}

	/**
	 * Поскольку сейчас(релиз 1) в поле Сумма списания всегда передается 0, в ответе сумма генерируется случайно. 
	 *
	 * @param xferAddRs ответ на запрос на закрытие вклада
	 * @param parameters запрос на закрытие вклада
	 * @return ответ дополненный
	 */
	private XferAddRs_Type appendInfoForAccountClosingPayment(XferAddRs_Type xferAddRs, IFXRq_Type parameters)
	{
		if(xferAddRs.getStatus().getStatusCode() != 0)
		{
			return xferAddRs;
		}

		Object receiverObject = null;
		if(parameters.getXferAddRq().getOperName().equals(OperName_Type.TDDC))
		{
			receiverObject = parameters.getXferAddRq().getXferInfo().getDepAcctIdTo();
		}
		else if(parameters.getXferAddRq().getOperName().equals(OperName_Type.TDCC))
		{
			receiverObject = parameters.getXferAddRq().getXferInfo().getCardAcctIdTo();
		}
		else
		{
			// если это не закрытие вкладов то ничего не делаем
			return xferAddRs;
		}

		DepInfo_Type     depInfo     = new DepInfo_Type();
		AgreemtInfo_Type agreemtInfo = new AgreemtInfo_Type();

		Random random = new Random();

		if (!parameters.getXferAddRq().getXferInfo().getExecute())
		{
//			if (random.nextBoolean())
//			{
				depInfo.setDaysToEndOfSaving( new Long(random.nextInt(365)) );
				depInfo.setDateToEndOfSaving( "31.06.2015" );
				depInfo.setEarlyTermRate(new IntRate_Type( new BigDecimal(random.nextInt(50))) );
//			}
			depInfo.setHaveForm190(random.nextBoolean() ? Boolean.TRUE : Boolean.FALSE);
		}

		BigDecimal srcCurAmt = null;
		BigDecimal dstCurAmt = null;

		if (receiverObject == null)
		{
			srcCurAmt = random.nextBoolean() ? BigDecimal.ZERO : new BigDecimal(RandomHelper.rand(5, RandomHelper.DIGITS));
		}
		else
		{
			/*
			 * Брать первоначальные значения неоткуда поэтому просто рандом
			 */
			srcCurAmt = new BigDecimal(RandomHelper.rand(5, RandomHelper.DIGITS));
			dstCurAmt = new BigDecimal(RandomHelper.rand(5, RandomHelper.DIGITS));
		}
		//если для операции  TDDC, TDCC, TDDO в ответе будет приходить SrcLayoutInfo, то SrcCurAmt возвращаться не будет.
		SrcLayoutInfo_Type srcLayoutInfo_type = parameters.getXferAddRq().getXferInfo().getSrcLayoutInfo();
		XferAddRq_Type xferAddRq = parameters.getXferAddRq();
		boolean supportTypes = xferAddRq.getOperName().equals(OperName_Type.TDDC) || xferAddRq.getOperName().equals(OperName_Type.TDDO) || xferAddRq.getOperName().equals(OperName_Type.TDCC);
		if(supportTypes && srcLayoutInfo_type != null && srcLayoutInfo_type.getIsCalcOperation())
			srcCurAmt = null;

		xferAddRs.setSrcCurAmt( srcCurAmt );
		xferAddRs.setDstCurAmt( dstCurAmt );

		agreemtInfo.setDepInfo(depInfo);
		xferAddRs.setAgreemtInfo(agreemtInfo);
		return xferAddRs;
	}

	private static WriteDownOperation_Type createCommission()
	{
		try
		{
			return new WriteDownOperation_Type("для ТП " + TariffPlanHelper.getActualTariffPlanByCode("1").getName(), new BigDecimal(RandomHelper.rand(3, RandomHelper.DIGITS)), Turnover_Type.CHARGE);
		}
		catch (BusinessException e)
		{
			return new WriteDownOperation_Type("Комиссия по ТП не пришла", BigDecimal.ZERO, Turnover_Type.CHARGE);
		}
	}
}
