package com.rssl.phizic.test.webgate.esberib.utils;

import com.rssl.phizic.auth.Login;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.resources.external.IMAccountLink;
import com.rssl.phizic.test.webgate.esberib.generated.*;
import com.rssl.phizic.utils.DateHelper;
import com.rssl.phizic.utils.RandomHelper;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizicgate.esberibgate.types.LimitType;

import java.util.Calendar;
import java.util.List;
import java.util.Random;

/**
 * @author Balovtsev
 * @created 27.09.2010
 * @ $Author$
 * @ $Revision$
 */

public class IMAccountResponseHelper extends BaseResponseHelper
{

	public IFXRs_Type createImaAcctInRs(IFXRq_Type parameters)
	{
		ImaAcctInRq_Type request = parameters.getImaAcctInRq();

		ImaAcctInRs_Type response_type = new ImaAcctInRs_Type();
		response_type.setRqTm(getRqTm());
		response_type.setRqUID(request.getRqUID());
		response_type.setOperUID(request.getOperUID());
		response_type.setStatus(getStatus());
		
		ImsRec_Type[] records = request.getBankAcctRec();

		for(ImsRec_Type record : records)
		{
			ImsAcctId_Type id_type = new ImsAcctId_Type();
			id_type.setAcctId(record.getImsAcctId().getAcctId());
			id_type.setSystemId(record.getImsAcctId().getSystemId());

			ImsAcctInfo_Type info_type = buildImsAcctInfo();
			info_type.setAcctCur("A99");
			info_type.setAcctName("Серебро в граммах");
			info_type.setAmount(getRandomDecimal());
			info_type.setStartDate(getStringDate(DateHelper.getPreviousYear(Calendar.getInstance())));
			info_type.setMaxSumWrite(getRandomDecimal());

			record.setStatus(getStatus());
			record.setImsAcctId(id_type);
			record.setImsAcctInfo(info_type);
		}
		response_type.setImsRec(records);

		IFXRs_Type response = new IFXRs_Type();

		response.setImaAcctInRs(response_type);
		return response;
	}

	protected IFXRs_Type createBankAcctStmtInqRs(IFXRq_Type parameters)
	{
		BankAcctStmtInqRq_Type request = parameters.getBankAcctStmtInqRq();

		BankAcctStmtInqRs_Type response_type = new BankAcctStmtInqRs_Type();
		response_type.setRqTm(getRqTm());
		response_type.setRqUID(request.getRqUID());
		response_type.setOperUID(request.getOperUID());
		response_type.setStatus(getStatus());

		IMSAcctRec_Type imsAcctRec_type = new IMSAcctRec_Type();
		imsAcctRec_type.setStatus( getStatus() );

		BankAcctId_Type id_type = request.getBankAcctRec().getBankAcctId();
		BankAcctStmtRec_Type[] records = new BankAcctStmtRec_Type[10];
		for(int i=0; i<records.length; i++)
		{
			records[i] = new BankAcctStmtRec_Type();

			AcctBal_Type acctBal_type = new AcctBal_Type();
			acctBal_type.setBalType( "Avail" );
			acctBal_type.setCurAmt( getRandomDecimal() );

			StmtSummAmt_Type stmtSummAmt_type = new StmtSummAmt_Type();
			stmtSummAmt_type.setStmtSummType( RandomHelper.rand(1, "12") );
			stmtSummAmt_type.setCurAmt( getRandomDecimal() );
			stmtSummAmt_type.setCurAmtCur( "A99" );

			records[i].setAcctBal( acctBal_type );
			records[i].setEffDate( getStringDate( DateHelper.getPreviousWeek() ) );
			records[i].setStmtSummAmt( stmtSummAmt_type );

			//Тип Информация о конверсии при операциях с ОМС
			IMAOperConvInfo_Type imaOperConvInfo = new IMAOperConvInfo_Type();
			//Признак: операция с металлом (CS_METAL-слиток) или денежными средствами  (CS_MONEY-денежные средства)
			IMAOperCurType_Type imaOperCurType = IMAOperCurType_Type.CS_METAL;
			imaOperConvInfo.setCurType(imaOperCurType);
			//Сумма операции в рублях
			imaOperConvInfo.setCurAmt(getRandomDecimal());
			//Валюта
			imaOperConvInfo.setAcctCur("RUR");			
			records[i].setIMAOperConvInfo(imaOperConvInfo);
		}

		imsAcctRec_type.setBankAcctId(id_type);
		imsAcctRec_type.setBankAcctStmtRec(records);

		IFXRs_Type response = new IFXRs_Type();
		response_type.setIMSAcctRec(new IMSAcctRec_Type[] {imsAcctRec_type});
		response.setBankAcctStmtInqRs(response_type);
		return response;
	}

	protected IFXRs_Type createBankAcctFullStmtInqRs(IFXRq_Type parameters)
	{
		BankAcctFullStmtInqRq_Type request = parameters.getBankAcctFullStmtInqRq();

		BankAcctFullStmtInfo_Type info_type = new BankAcctFullStmtInfo_Type();

		IMSBalance_Type start_balance = new IMSBalance_Type();
		start_balance.setAmt( getRandomDecimal() );
		start_balance.setAmtRub( getRandomDecimal() );
		IMSBalance_Type end_balance = new IMSBalance_Type();
		end_balance.setAmt( getRandomDecimal() );
		end_balance.setAmtRub( getRandomDecimal() );

		info_type.setCurAmtCur( "A99" );
		info_type.setEndBalance( end_balance );
		info_type.setStartBalance( start_balance );
		info_type.setRate( getRandomDecimal() );
		info_type.setLastDate( getStringDate( DateHelper.getPreviousMonth() ) );
		info_type.setFromDate( request.getFromDate() );
		info_type.setToDate( request.getToDate() );

		BankAcctFullStmtRec_Type[] rec_types = new BankAcctFullStmtRec_Type[12];

		for(int i=0; i<rec_types.length; i++)
		{
			rec_types[i] = new BankAcctFullStmtRec_Type();
			rec_types[i].setAmt( getRandomDecimal() );
			rec_types[i].setAmtPhiz( getRandomDecimal() );

			rec_types[i].setBalance( getRandomDecimal() );
			rec_types[i].setBalancePhiz( getRandomDecimal() );
			rec_types[i].setStmtSummType(RandomHelper.rand(1, "12"));
			rec_types[i].setNumber(RandomHelper.rand(5, RandomHelper.DIGITS));
			rec_types[i].setCorAcc(RandomHelper.rand(22, RandomHelper.DIGITS));
			rec_types[i].setEffDate( getStringDate(DateHelper.getPreviousWeek()) );
			rec_types[i].setCode(RandomHelper.rand(2, RandomHelper.DIGITS));
			rec_types[i].setDocumentNumber( RandomHelper.rand(5, RandomHelper.UPPER_ENGLISH_LETTERS) );
		}

		IMSAcctRec_Type record_type = new IMSAcctRec_Type();
		record_type.setStatus( getStatus() );
		record_type.setBankAcctFullStmtInfo( info_type );
		record_type.setBankAcctId( request.getBankAcctRec().getBankAcctId() );
		record_type.setBankAcctFullStmtRec( rec_types );

		BankAcctFullStmtInqRs_Type response_type = new BankAcctFullStmtInqRs_Type();
		response_type.setRqTm( getRqTm() );
		response_type.setRqUID(request.getRqUID());
		response_type.setOperUID(request.getOperUID());
		response_type.setStatus(getStatus());
		response_type.setIMSAcctRec(new IMSAcctRec_Type[] {record_type});

		IFXRs_Type response = new IFXRs_Type();

		response.setBankAcctFullStmtInqRs(response_type);
		return response;
	}

	protected AcctBal_Type[] buildIMAccountBalance()
	{
		AcctBal_Type[] balanceType = new AcctBal_Type[2];
		AcctBal_Type limit = new AcctBal_Type();
		limit.setBalType(LimitType.Avail.toString());
		limit.setAcctCur("A99");
		limit.setCurAmt( getRandomDecimal() );
		balanceType[0] = limit;

		limit = new AcctBal_Type();
		limit.setBalType(LimitType.AvailCash.toString());
		limit.setAcctCur("A99");
		limit.setCurAmt( getRandomDecimal() );
		balanceType[1] = limit;
		return balanceType;
	}

	protected BankAcctId_Type buildBankImaAccountIdType(String number, BankInfo_Type bankInfo)
	{
		if( StringHelper.isEmpty(number) )
		{
			number = RandomHelper.rand(22, RandomHelper.DIGITS);
		}

		BankAcctId_Type acct = new BankAcctId_Type();
		acct.setAcctCur("A99");
		acct.setAcctId(number);
		acct.setSystemId("urn:sbrfsystems:40-cod");
		acct.setAcctName("Серебро в граммах");
		acct.setStartDate(getStringDate(DateHelper.getPreviousYear(Calendar.getInstance())));
		
		return acct;
	}

	protected void buildIMAccounts(BankAcctInqRs_Type bankAcctInqRs, Login login) throws BusinessException, BusinessLogicException
	{
		List<IMAccountLink> imAccountLinks = (login==null) ? null : resourceService.getLinks(login, IMAccountLink.class);

		boolean isNew = (imAccountLinks == null || imAccountLinks.isEmpty());
		int count = isNew ? 2 : imAccountLinks.size();

		BankAcctRec_Type[] bankRecord = new BankAcctRec_Type[count];

		for(int i=0; i<bankRecord.length; i++)
		{
			bankRecord[i] = new BankAcctRec_Type();
			bankRecord[i].setBankInfo(getMockBankInfo());
			bankRecord[i].setAcctBal( buildIMAccountBalance() );
			bankRecord[i].setBankAcctId( buildBankImaAccountIdType(isNew? "": imAccountLinks.get(i).getNumber(), getMockBankInfo()) );
			bankRecord[i].setImsAcctInfo(buildImsAcctInfo());
		}
		bankAcctInqRs.setBankAcctRec(bankRecord);
	}

	private ImsAcctInfo_Type buildImsAcctInfo()
	{
		ImsAcctInfo_Type imsAcctInfo = new ImsAcctInfo_Type();
		imsAcctInfo.setStatus(getIMAStatus());
		if (imsAcctInfo.getStatus().equals(IMSStatusEnum_Type.value2))
		{
			imsAcctInfo.setEndDate(getStringDate(DateHelper.getPreviousMonth()));
		}

		imsAcctInfo.setAgreementNumber(RandomHelper.rand(20, RandomHelper.DIGITS));
		imsAcctInfo.setCustRec(getMockCustRec());
		imsAcctInfo.setBankInfo(getMockBankInfo());

		return imsAcctInfo;
	}

	private CustRec_Type getMockCustRec()
	{
		CustRec_Type custRec = new CustRec_Type();
		custRec.setCustId("123123");
		custRec.setCustInfo(getMockCustInfo());
		return custRec;
	}

	private IMSStatusEnum_Type getIMAStatus()
	{
		Random rand = new Random();
		if (rand.nextInt(5) == 1)
			return IMSStatusEnum_Type.value2;
		if (rand.nextInt(5) == 2)
			return IMSStatusEnum_Type.value3;
		if (rand.nextInt(5) == 3)
			return IMSStatusEnum_Type.value4;
		else
			return IMSStatusEnum_Type.value1;

	}
}
