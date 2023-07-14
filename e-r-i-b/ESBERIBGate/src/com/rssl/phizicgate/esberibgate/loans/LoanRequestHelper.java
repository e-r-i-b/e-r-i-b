package com.rssl.phizicgate.esberibgate.loans;

import com.rssl.phizic.common.types.Money;
import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizicgate.esberibgate.messaging.RequestHelperBase;
import com.rssl.phizicgate.esberibgate.utils.LoanCompositeId;
import com.rssl.phizicgate.esberibgate.ws.generated.*;

import java.util.Calendar;

/**
 * @author gladishev
 * @ created 20.09.2010
 * @ $Author$
 * @ $Revision$
 */
public class LoanRequestHelper extends RequestHelperBase
{

	public LoanRequestHelper(GateFactory factory)
	{
		super(factory);
	}

	//3.16 Получение графика платежей
	public IFXRq_Type createLoanPaymentRq(LoanCompositeId compositeId, Long startNumber, Long maxForwardCount, Long maxRewardCount) throws GateLogicException, GateException
	{
		IFXRq_Type ifxRq = new IFXRq_Type();

		LoanPaymentRq_Type request = new LoanPaymentRq_Type();
		request.setRqUID(generateUUID());
		request.setRqTm(generateRqTm());
		request.setOperUID(generateOUUID());
		request.setSPName(getSPName());
		request.setBankInfo(getBankInfo(compositeId));

		LoanAcctId_Type loanAcctId = new LoanAcctId_Type();
		loanAcctId.setSystemId(compositeId.getSystemIdActiveSystem());
		loanAcctId.setAcctId(compositeId.getEntityId());
		BankInfo_Type bankInfo = new BankInfo_Type();
		bankInfo.setRbBrchId(compositeId.getRbBrchId());
		loanAcctId.setBankInfo(bankInfo);
		request.setLoanAcctId(loanAcctId);
		request.setStartNumber(startNumber);
		request.setMaxForwardCount(maxForwardCount);
		request.setMaxRewardCount(maxRewardCount);

		ifxRq.setLoanPaymentRq(request);
		return ifxRq;
	}

	//3.15 Получение детальной информации по кредиту
	public IFXRq_Type createLoanInfoRq(LoanCompositeId compositeId) throws GateLogicException, GateException
	{
		IFXRq_Type ifxRq = new IFXRq_Type();

		LoanInfoRq_Type request = new LoanInfoRq_Type();
		request.setRqUID(generateUUID());
		request.setRqTm(generateRqTm());
		request.setOperUID(generateOUUID());
		request.setSPName(getSPName());
		request.setBankInfo(getBankInfo(compositeId));

		LoanAcctId_Type loanAcctId = new LoanAcctId_Type();
		loanAcctId.setSystemId(compositeId.getSystemIdActiveSystem());
		loanAcctId.setAcctId(compositeId.getEntityId());
		BankInfo_Type bankInfo = new BankInfo_Type();
		bankInfo.setRbBrchId(compositeId.getRbBrchId());
		loanAcctId.setBankInfo(bankInfo);
		request.setLoanAcctId(loanAcctId);

		ifxRq.setLoanInfoRq(request);
		return ifxRq;
	}

	//3.14 Получение задолженности по кредиту
	public IFXRq_Type createLoanInqRq(LoanCompositeId compositeId, String branchId, Calendar date, Money amount) throws GateLogicException, GateException
	{
		IFXRq_Type ifxRq = new IFXRq_Type();

		LoanInqRq_Type request = new LoanInqRq_Type();
		request.setRqUID(generateUUID());
		request.setRqTm(generateRqTm());
		request.setOperUID(generateOUUID());
		request.setSPName(getSPName());
		request.setBankInfo(getBankInfo(compositeId));

		LoanAcctId_Type loanAcctId = new LoanAcctId_Type();
		loanAcctId.setSystemId(compositeId.getSystemIdActiveSystem());
		loanAcctId.setProdType(compositeId.getProductType());
		loanAcctId.setDateCalc(getStringDate(date));
		if (amount != null)
		{
			loanAcctId.setCurAmt(amount.getDecimal());
			loanAcctId.setAcctCur(amount.getCurrency().getCode());
		}
		BankInfo_Type bankInfo = new BankInfo_Type();
		bankInfo.setBranchId(branchId);
		bankInfo.setRbBrchId(compositeId.getRbBrchId());
		loanAcctId.setBankInfo(bankInfo);
		request.setLoanAcctId(loanAcctId);

		ifxRq.setLoanInqRq(request);
		return ifxRq;
	}
	
	private BankInfo_Type getBankInfo(LoanCompositeId compositeId) throws GateLogicException, GateException
	{
		BankInfo_Type bankInfo = new BankInfo_Type();
		bankInfo.setRbTbBrchId(getRbTbBrch(compositeId));
		return bankInfo;
	}
}
