package com.rssl.phizicgate.esberibgate.messaging;

import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizicgate.esberibgate.ws.generated.*;

import java.math.BigDecimal;
import java.util.GregorianCalendar;
import java.util.Random;

/**
 * Хелпер для тестирования запросов, после нормальной реализации нужных запросов, этот класс надо убить
 @author Pankin
 @ created 08.09.2010
 @ $Author$
 @ $Revision$
 */
public class RequestTestHelper extends RequestHelperBase
{
	private static final String BLOCK_REASON = "HOLDER";
	private static final String SP_NAME = "BP_ERIB";
	private static final String ACCT_TYPE_CARD = "Card";

	public RequestTestHelper(GateFactory factory)
	{
		super(factory);
	}

	public IFXRq_Type createCustInqRq()
	{
		IFXRq_Type ifxRq = new IFXRq_Type();

		CustInqRq_Type request = new CustInqRq_Type();
		request.setRqUID(generateUUID());
		request.setRqTm(generateRqTm());
		request.setOperUID(generateOUUID());
		request.setSPName(getSPName());
		request.setBankInfo(getBankInfo());

		CardAcctId_Type cardAcctId = new CardAcctId_Type();
		cardAcctId.setCardNum("1234123412341234");
		request.setCardAcctId(cardAcctId);

		ifxRq.setCustInqRq(request);
		return ifxRq;
	}

	public IFXRq_Type createBankAcctInqRq()
	{
		IFXRq_Type ifxRq = new IFXRq_Type();

		BankAcctInqRq_Type request = new BankAcctInqRq_Type();
		request.setRqUID(generateUUID());
		request.setRqTm(generateRqTm());
		request.setOperUID(generateOUUID());
		request.setSPName(getSPName());

		request.setBankInfo(getBankInfo());

		CustInfo_Type custInfo = new CustInfo_Type();
		PersonInfo_Type personInfo = new PersonInfo_Type();
		personInfo.setBirthday(getStringDate(new GregorianCalendar()));
		personInfo.setPersonName(getPersonName());
		personInfo.setIdentityCard(getIdentityCard());
		custInfo.setPersonInfo(personInfo);
		request.setCustInfo(custInfo);

		//Тип продукта: Deposit, IMA, Card, Credit, DepoAcc, LongOrd
		request.setAcctType(0,AcctType_Type.fromString("Deposit"));

		ifxRq.setBankAcctInqRq(request);
		return ifxRq;
	}

	public IFXRq_Type createAcctInqRq()
	{
		IFXRq_Type ifxRq = new IFXRq_Type();

		AcctInqRq_Type request = new AcctInqRq_Type();
		request.setRqUID(generateUUID());
		request.setRqTm(generateRqTm());
		request.setOperUID(generateOUUID());
		request.setSPName(getSPName());

		request.setBankInfo(getBankInfo());

		ifxRq.setAcctInqRq(request);
		return ifxRq;
	}

	public IFXRq_Type createAcctInfoRq()
	{
		IFXRq_Type ifxRq = new IFXRq_Type();

		AcctInfoRq_Type request = new AcctInfoRq_Type();
		request.setRqUID(generateUUID());
		request.setRqTm(generateRqTm());
		request.setOperUID(generateOUUID());
		request.setSPName(getSPName());
		request.setBankInfo(getBankInfo());

		DepAcctRec_Type depAcctRec = new DepAcctRec_Type();
		DepAcctId_Type depAcctId = new DepAcctId_Type();
		depAcctId.setAcctId("123123123123");
		depAcctRec.setDepAcctId(depAcctId);
		request.setDepAcctRec(new DepAcctRec_Type[]{depAcctRec});

		ifxRq.setAcctInfoRq(request);
		return ifxRq;
	}

	public IFXRq_Type createDepAcctStmtInqRq()
	{
		IFXRq_Type ifxRq = new IFXRq_Type();

		DepAcctStmtInqRq_Type request = new DepAcctStmtInqRq_Type();
		request.setRqUID(generateUUID());
		request.setRqTm(generateRqTm());
		request.setOperUID(generateOUUID());
		request.setSPName(getSPName());
		request.setBankInfo(getBankInfo());

		DepAcctStmtInqRq_TypeDepAcctRec depAcctRec = new DepAcctStmtInqRq_TypeDepAcctRec();
		DepAcctId_Type depAcctId = new DepAcctId_Type();
		depAcctId.setAcctId("123123123123");
		depAcctRec.setDepAcctId(depAcctId);
		request.setDepAcctRec(depAcctRec);
		request.setStmtType(StmtType_Type.fromString("Full"));

		ifxRq.setDepAcctStmtInqRq(request);
		return ifxRq;
	}

	public IFXRq_Type createImaAcctInRq()
	{
		IFXRq_Type ifxRq = new IFXRq_Type();

		ImaAcctInRq_Type request = new ImaAcctInRq_Type();
		request.setRqUID(generateUUID());
		request.setRqTm(generateRqTm());
		request.setOperUID(generateOUUID());
		request.setSPName(getSPName());
		request.setBankInfo(getBankInfo());

		//странный тип
		ImsRec_Type imsRec = new ImsRec_Type();
		ImsAcctId_Type imsAcctId = new ImsAcctId_Type();
		imsAcctId.setSystemId("SystemId");
		imsAcctId.setAcctId("123123123123");
		imsRec.setImsAcctId(imsAcctId);
		request.setBankAcctRec(new ImsRec_Type[]{imsRec});

		ifxRq.setImaAcctInRq(request);
		return ifxRq;
	}

	public IFXRq_Type createBankAcctStmtInqRq()
	{
		IFXRq_Type ifxRq = new IFXRq_Type();

		BankAcctStmtInqRq_Type request = new BankAcctStmtInqRq_Type();
		request.setRqUID(generateUUID());
		request.setRqTm(generateRqTm());
		request.setOperUID(generateOUUID());
		request.setSPName(getSPName());
		request.setBankInfo(getBankInfo());

		request.setBankAcctRec(new BankAcctRec_Type());
		request.setStmtType(StmtType_Type.fromString("Short"));

		ifxRq.setBankAcctStmtInqRq(request);
		return ifxRq;
	}

	public IFXRq_Type createBankAcctFullStmtInqRq()
	{
		IFXRq_Type ifxRq = new IFXRq_Type();

		BankAcctFullStmtInqRq_Type request = new BankAcctFullStmtInqRq_Type();
		request.setRqUID(generateUUID());
		request.setRqTm(generateRqTm());
		request.setOperUID(generateOUUID());
		request.setSPName(getSPName());
		request.setBankInfo(getBankInfo());

		request.setBankAcctRec(new BankAcctRec_Type());

		request.setFromDate(getStringDate(new GregorianCalendar()));
		request.setToDate(getStringDate(new GregorianCalendar()));

		ifxRq.setBankAcctFullStmtInqRq(request);
		return ifxRq;
	}

	public IFXRq_Type createCardAcctDInqRq()
	{
		IFXRq_Type ifxRq = new IFXRq_Type();

		CardAcctDInqRq_Type request = new CardAcctDInqRq_Type();
		request.setRqUID(generateUUID());
		request.setRqTm(generateRqTm());
		request.setOperUID(generateOUUID());
		request.setSPName(getSPName());
		request.setBankInfo(getBankInfo());

		CardInfo_Type cardInfo = new CardInfo_Type();
		CardAcctId_Type cardAcctId = new CardAcctId_Type();
		cardAcctId.setCardNum("1234123412341234");
		cardInfo.setCardAcctId(cardAcctId);
		request.setCardInfo(new CardInfo_Type[]{cardInfo});

		ifxRq.setCardAcctDInqRq(request);
		return ifxRq;
	}

	//3.10
	public IFXRq_Type createBankAcctStmtImgInqRq()
	{
		IFXRq_Type ifxRq = new IFXRq_Type();

		BankAcctStmtImgInqRq_Type request = new BankAcctStmtImgInqRq_Type();
		request.setRqUID(generateUUID());
		request.setRqTm(generateRqTm());
		request.setOperUID(generateOUUID());
		request.setSPName(getSPName());
		request.setBankInfo(getBankInfo());

		SelRangeDt_Type selRangeDt = new SelRangeDt_Type(getStringDate(new GregorianCalendar()), getStringDate(new GregorianCalendar()));
		request.setSelRangeDt(selRangeDt);

		CardAcctId_Type cardAcctId = new CardAcctId_Type();
		cardAcctId.setSystemId("SystemId");
		cardAcctId.setCardNum("1234123412341234");
		BankInfo_Type bankInfo = new BankInfo_Type();
		bankInfo.setRbBrchId("123");
		cardAcctId.setBankInfo(bankInfo);
		request.setCardAcctId(cardAcctId);

		ContactInfo_Type contactInfo = new ContactInfo_Type();
		contactInfo.setEmailAddr("qwer@awe.we");
		contactInfo.setMessageDeliveryType(MessageDeliveryType_Type.fromString("E"));
		request.setContactInfo(contactInfo);

		ifxRq.setBankAcctStmtImgInqRq(request);
		return ifxRq;
	}

	//3.12
	public IFXRq_Type createCCAcctExtStmtInqRq()
	{
		IFXRq_Type ifxRq = new IFXRq_Type();

		CCAcctExtStmtInqRq_Type request = new CCAcctExtStmtInqRq_Type();
		request.setRqUID(generateUUID());
		request.setRqTm(generateRqTm());
		request.setOperUID(generateOUUID());
		request.setSPName(getSPName());
		request.setBankInfo(getBankInfo());

		CardInfo_Type cardInfo = new CardInfo_Type();
		CardAcctId_Type cardAcctId = new CardAcctId_Type();
		cardAcctId.setSystemId("SystemId");
		cardAcctId.setCardNum("1234123412341234");
		BankInfo_Type bankInfo = new BankInfo_Type();
		bankInfo.setRbBrchId("123");
		cardAcctId.setBankInfo(bankInfo);
		cardInfo.setCardAcctId(cardAcctId);
		request.setCardInfo(new CardInfo_Type[]{cardInfo});

		request.setOpCount(5L);

		ifxRq.setCCAcctExtStmtInqRq(request);
		return ifxRq;
	}

	//3.14
	public IFXRq_Type createLoanInqRq()
	{
		IFXRq_Type ifxRq = new IFXRq_Type();

		LoanInqRq_Type request = new LoanInqRq_Type();
		request.setRqUID(generateUUID());
		request.setRqTm(generateRqTm());
		request.setOperUID(generateOUUID());
		request.setSPName(getSPName());
		request.setBankInfo(getBankInfo());

		LoanAcctId_Type loanAcctId = new LoanAcctId_Type();
		loanAcctId.setSystemId("SystemId");
		loanAcctId.setProdType("10");
		loanAcctId.setDateCalc(getStringDate(new GregorianCalendar()));
		BankInfo_Type bankInfo = new BankInfo_Type();
		bankInfo.setBranchId("123");
		bankInfo.setRbBrchId("123");
		loanAcctId.setBankInfo(bankInfo);
		request.setLoanAcctId(loanAcctId);

		ifxRq.setLoanInqRq(request);
		return ifxRq;
	}

	//3.15
	public IFXRq_Type createLoanInfoRq()
	{
		IFXRq_Type ifxRq = new IFXRq_Type();

		LoanInfoRq_Type request = new LoanInfoRq_Type();
		request.setRqUID(generateUUID());
		request.setRqTm(generateRqTm());
		request.setOperUID(generateOUUID());
		request.setSPName(getSPName());
		request.setBankInfo(getBankInfo());

		LoanAcctId_Type loanAcctId = new LoanAcctId_Type();
		loanAcctId.setSystemId("SystemId");
		loanAcctId.setAcctId("1234123412341234");
		BankInfo_Type bankInfo = new BankInfo_Type();
		bankInfo.setRbBrchId("123");
		loanAcctId.setBankInfo(bankInfo);
		request.setLoanAcctId(loanAcctId);

		ifxRq.setLoanInfoRq(request);
		return ifxRq;
	}

	//3.16
	public IFXRq_Type createLoanPaymentRq()
	{
		IFXRq_Type ifxRq = new IFXRq_Type();

		LoanPaymentRq_Type request = new LoanPaymentRq_Type();
		request.setRqUID(generateUUID());
		request.setRqTm(generateRqTm());
		request.setOperUID(generateOUUID());
		request.setSPName(getSPName());
		request.setBankInfo(getBankInfo());

		LoanAcctId_Type loanAcctId = new LoanAcctId_Type();
		loanAcctId.setSystemId("SystemId");
		loanAcctId.setAcctId("123123123123");
		BankInfo_Type bankInfo = new BankInfo_Type();
		bankInfo.setRbBrchId("123");
		loanAcctId.setBankInfo(bankInfo);
		request.setLoanAcctId(loanAcctId);

		ifxRq.setLoanPaymentRq(request);
		return ifxRq;
	}

	//3.17
	public IFXRq_Type createDepoClientRegRq()
	{
		IFXRq_Type ifxRq = new IFXRq_Type();

		DepoClientRegRqType request = new DepoClientRegRqType();
		request.setRqUID(generateUUID());
		request.setRqTm(generateRqTm());
		request.setOperUID(generateOUUID());
		request.setSPName(getSPName());
		request.setBankInfo(getBankInfo());

		CustInfo_Type custInfo = new CustInfo_Type();
		PersonInfo_Type personInfo = new PersonInfo_Type();
		personInfo.setBirthday(getStringDate(new GregorianCalendar()));
		personInfo.setPersonName(getPersonName());
		personInfo.setIdentityCard(getIdentityCard());
		custInfo.setPersonInfo(personInfo);
		request.setCustInfo(custInfo);

		ifxRq.setDepoClientRegRq(request);
		return ifxRq;
	}

	//3.18
	public IFXRq_Type createDepoAccInfoRq()
	{
		IFXRq_Type ifxRq = new IFXRq_Type();

		DepoAccInfoRqType request = new DepoAccInfoRqType();
		request.setRqUID(generateUUID());
		request.setRqTm(generateRqTm());
		request.setOperUID(generateOUUID());
		request.setSPName(getSPName());
		request.setBankInfo(getBankInfo());

		DepoAcctId_Type depoAcctId = new DepoAcctId_Type();
		depoAcctId.setAcctId("123123123123");
		request.setDepoAcctId(new DepoAcctId_Type[]{depoAcctId});

		ifxRq.setDepoAccInfoRq(request);
		return ifxRq;
	}

	//3.19
	public IFXRq_Type createDepoDeptsInfoRq()
	{
		IFXRq_Type ifxRq = new IFXRq_Type();

		//странный тип
		DepoAccInfoRqType request = new DepoAccInfoRqType();
		request.setRqUID(generateUUID());
		request.setRqTm(generateRqTm());
		request.setOperUID(generateOUUID());
		request.setSPName(getSPName());
		request.setBankInfo(getBankInfo());

		DepoAcctId_Type depoAcctId = new DepoAcctId_Type();
		depoAcctId.setSystemId("SystemId");
		depoAcctId.setAcctId("123123123123");
		BankInfo_Type bankInfo = new BankInfo_Type();
		bankInfo.setRbBrchId("123");
		depoAcctId.setBankInfo(bankInfo);
		request.setDepoAcctId(new DepoAcctId_Type[]{depoAcctId});

		ifxRq.setDepoDeptsInfoRq(request);
		return ifxRq;
	}

	//3.20
	public IFXRq_Type createDepoDeptDetInfoRq()
	{
		IFXRq_Type ifxRq = new IFXRq_Type();

		DepoDeptDetInfoRqType request = new DepoDeptDetInfoRqType();
		request.setRqUID(generateUUID());
		request.setRqTm(generateRqTm());
		request.setOperUID(generateOUUID());
		request.setSPName(getSPName());
		request.setBankInfo(getBankInfo());

		DepoAcctId_Type depoAcctId = new DepoAcctId_Type();
		depoAcctId.setSystemId("SystemId");
		depoAcctId.setAcctId("123123123123");
		BankInfo_Type bankInfo = new BankInfo_Type();
		bankInfo.setRbBrchId("123");
		depoAcctId.setBankInfo(bankInfo);
		request.setDepoAcctId(new DepoAcctId_Type[]{depoAcctId});

		DeptId_Type deptId = new DeptId_Type();
		deptId.setRecNumber("123123123");
		deptId.setEffDt(getStringDate(new GregorianCalendar()));
		request.setDeptId(new DeptId_Type[]{deptId});

		ifxRq.setDepoDeptDetInfoRq(request);
		return ifxRq;
	}

	//3.22
	public IFXRq_Type createDepoAccSecInfoRq()
	{
		IFXRq_Type ifxRq = new IFXRq_Type();

		//странный тип
		DepoAccInfoRqType request = new DepoAccInfoRqType();
		request.setRqUID(generateUUID());
		request.setRqTm(generateRqTm());
		request.setOperUID(generateOUUID());
		request.setSPName(getSPName());
		request.setBankInfo(getBankInfo());

		DepoAcctId_Type depoAcctId = new DepoAcctId_Type();
		depoAcctId.setSystemId("SystemId");
		depoAcctId.setAcctId("123123123123");
		BankInfo_Type bankInfo = new BankInfo_Type();
		bankInfo.setRbBrchId("123");
		depoAcctId.setBankInfo(bankInfo);
		request.setDepoAcctId(new DepoAcctId_Type[]{depoAcctId});

		ifxRq.setDepoAccSecInfoRq(request);
		return ifxRq;
	}

	//3.24
	public IFXRq_Type createDepoAccTranRq()
	{
		IFXRq_Type ifxRq = new IFXRq_Type();

		DepoAccTranRqType request = new DepoAccTranRqType();
		request.setRqUID(generateUUID());
		request.setRqTm(generateRqTm());
		request.setOperUID(generateOUUID());
		request.setSPName(getSPName());
		request.setBankInfo(getBankInfo());

		DepoAcctId_Type depoAcctId = new DepoAcctId_Type();
		depoAcctId.setSystemId("SystemId");
		depoAcctId.setAcctId("123123123123");
		BankInfo_Type bankInfo = new BankInfo_Type();
		bankInfo.setRbBrchId("123");
		depoAcctId.setBankInfo(bankInfo);
		request.setDepoAcctId(depoAcctId);

		DivisionNumber_Type divisionNumber = new DivisionNumber_Type();
		divisionNumber.setType("Базовый");
		divisionNumber.setNumber("1010002");

		TransferInfo_Type transferInfo = new TransferInfo_Type();
		transferInfo.setOperType(DepoOperType_Type.fromString("231"));
		transferInfo.setOperationSubType(DepoOperationSubType_Type.fromString("INTERNAL_TRANSFER"));
		transferInfo.setOperationDesc("description");				
		transferInfo.setDivisionNumber(divisionNumber);
		transferInfo.setSecurityCount(10L);
		TransferRcpInfo_Type transferRcpInfo = new TransferRcpInfo_Type();
		transferRcpInfo.setAdditionalInfo("AdditionalInfo");
		transferInfo.setTransferRcpInfo(transferRcpInfo);
		transferInfo.setInsideCode("456456456");
		request.setTransferInfo(transferInfo);

		ifxRq.setDepoAccTranRq(request);
		return ifxRq;
	}

	//09.09
	//3.26
	public IFXRq_Type createMessageRecvRq()
	{
		IFXRq_Type ifxRq = new IFXRq_Type();

		MessageRecvRqType request = new MessageRecvRqType();
		request.setRqUID(generateUUID());
		request.setRqTm(generateRqTm());
		request.setOperUID(generateOUUID());
		request.setSPName(getSPName());
		request.setDocNumber("123");
		request.setStatus(getStatus());

		ifxRq.setMessageRecvRq(request);
		return ifxRq;
	}

	//3.27
	public IFXRq_Type createDepoAccSecRegRq()
	{
		IFXRq_Type ifxRq = new IFXRq_Type();

		DepoAccSecRegRqType request = new DepoAccSecRegRqType();
		request.setRqUID(generateUUID());
		request.setRqTm(generateRqTm());
		request.setOperUID(generateOUUID());
		request.setSPName(getSPName());
		request.setBankInfo(getBankInfo());

		DepoAcctId_Type depoAcctId = new DepoAcctId_Type();
		depoAcctId.setSystemId("SystemId");
		depoAcctId.setAcctId("123123123123");
		BankInfo_Type bankInfo = new BankInfo_Type();
		bankInfo.setRbBrchId("123");
		depoAcctId.setBankInfo(bankInfo);
		request.setDepoAcctId(new DepoAcctId_Type[]{depoAcctId});

		DepoSecurityOperationInfo_Type depoSecurityOperationInfo = new DepoSecurityOperationInfo_Type();
		depoSecurityOperationInfo.setSecurityName("SecurityName");
		depoSecurityOperationInfo.setSecurityNumber("123");
		depoSecurityOperationInfo.setIssuer("456");
		DepoSecurityOperationList_Type depoSecurityOperationList = new DepoSecurityOperationList_Type();
		depoSecurityOperationList.setOperName(0,DepoSecurityOperationType_Type.fromString("OperName"));
		depoSecurityOperationInfo.setOperations(new DepoSecurityOperationList_Type[]{depoSecurityOperationList});
		request.setOperationInfo(depoSecurityOperationInfo);

		ifxRq.setDepoAccSecRegRq(request);
		return ifxRq;
	}

	//3.28
	public IFXRq_Type createDepoArRq()
	{
		IFXRq_Type ifxRq = new IFXRq_Type();

		DepoAccInfoRqType request = new DepoAccInfoRqType();
		request.setRqUID(generateUUID());
		request.setRqTm(generateRqTm());
		request.setOperUID(generateOUUID());
		request.setSPName(getSPName());
		request.setBankInfo(getBankInfo());

		DepoAcctId_Type depoAcctId = new DepoAcctId_Type();
		depoAcctId.setSystemId("SystemId");
		depoAcctId.setAcctId("123123123123");
		BankInfo_Type bankInfo = new BankInfo_Type();
		bankInfo.setRbBrchId("123");
		depoAcctId.setBankInfo(bankInfo);
		request.setDepoAcctId(new DepoAcctId_Type[]{depoAcctId});

		ifxRq.setDepoArRq(request);
		return ifxRq;
	}

	//3.29
	public IFXRq_Type createDepoRevokeDocRq()
	{
		IFXRq_Type ifxRq = new IFXRq_Type();

		DepoRevokeDocRqType request = new DepoRevokeDocRqType();
		request.setRqUID(generateUUID());
		request.setRqTm(generateRqTm());
		request.setOperUID(generateOUUID());
		request.setSPName(getSPName());
		request.setBankInfo(getBankInfo());
		request.setRevokePurpose("qwert");
		request.setRevokeDate(getStringDate(new GregorianCalendar()));

		ifxRq.setDepoRevokeDocRq(request);
		return ifxRq;
	}

	//3.30
	public IFXRq_Type createXferAddRqTCD()
	{
		IFXRq_Type ifxRq = new IFXRq_Type();

		XferAddRq_Type request = new XferAddRq_Type();
		request.setRqUID(generateUUID());
		request.setRqTm(generateRqTm());
		request.setOperUID(generateOUUID());
		request.setSPName(getSPName());
		request.setBankInfo(getBankInfo());

		XferInfo_Type xferInfo = new XferInfo_Type();
		CardAcctId_Type cardAcctIdFrom = new CardAcctId_Type();
		cardAcctIdFrom.setSystemId("SystemId");
		cardAcctIdFrom.setCardNum("1234123412341234");
		cardAcctIdFrom.setEndDt(getStringDate(new GregorianCalendar()));

		CustInfo_Type custInfo = new CustInfo_Type();
		PersonInfo_Type personInfo = new PersonInfo_Type();
		personInfo.setPersonName(getPersonName());
		custInfo.setPersonInfo(personInfo);
		cardAcctIdFrom.setCustInfo(custInfo);

		BankInfo_Type bankInfo = new BankInfo_Type();
		bankInfo.setRbBrchId("123");
		cardAcctIdFrom.setBankInfo(bankInfo);
		xferInfo.setCardAcctIdFrom(cardAcctIdFrom);

		DepAcctId_Type depAcctIdTo = new DepAcctId_Type();
		depAcctIdTo.setSystemId("SystemId");
		depAcctIdTo.setAcctId("123123123123");
		depAcctIdTo.setCustInfo(custInfo);
		depAcctIdTo.setBankInfo(bankInfo);
		xferInfo.setDepAcctIdTo(depAcctIdTo);
		request.setXferInfo(xferInfo);

		ifxRq.setXferAddRq(request);
		return ifxRq;
	}

	//3.31
	public IFXRq_Type createXferAddRqTDD()
	{
		IFXRq_Type ifxRq = new IFXRq_Type();

		XferAddRq_Type request = new XferAddRq_Type();
		request.setRqUID(generateUUID());
		request.setRqTm(generateRqTm());
		request.setOperUID(generateOUUID());
		request.setSPName(getSPName());
		request.setBankInfo(getBankInfo());

		XferInfo_Type xferInfo = new XferInfo_Type();
		DepAcctId_Type depAcctIdFrom = new DepAcctId_Type();
		depAcctIdFrom.setSystemId("SystemId");
		depAcctIdFrom.setAcctId("1234123412341234");

		CustInfo_Type custInfo = new CustInfo_Type();
		PersonInfo_Type personInfo = new PersonInfo_Type();
		personInfo.setPersonName(getPersonName());
		custInfo.setPersonInfo(personInfo);
		depAcctIdFrom.setCustInfo(custInfo);

		BankInfo_Type bankInfo = new BankInfo_Type();
		bankInfo.setRbBrchId("123");
		depAcctIdFrom.setBankInfo(bankInfo);
		xferInfo.setDepAcctIdFrom(depAcctIdFrom);

		DepAcctId_Type depAcctIdTo = new DepAcctId_Type();
		depAcctIdTo.setSystemId("SystemId");
		depAcctIdTo.setAcctId("123123123123");
		depAcctIdTo.setCustInfo(custInfo);
		depAcctIdTo.setBankInfo(bankInfo);
		xferInfo.setDepAcctIdTo(depAcctIdTo);

		xferInfo.setAcctCur("RUR");
		request.setXferInfo(xferInfo);

		ifxRq.setXferAddRq(request);
		return ifxRq;
	}

	//3.32
	public IFXRq_Type createXferAddRqTDC()
	{
		IFXRq_Type ifxRq = new IFXRq_Type();

		XferAddRq_Type request = new XferAddRq_Type();
		request.setRqUID(generateUUID());
		request.setRqTm(generateRqTm());
		request.setOperUID(generateOUUID());
		request.setSPName(getSPName());
		request.setBankInfo(getBankInfo());

		XferInfo_Type xferInfo = new XferInfo_Type();
		DepAcctId_Type depAcctIdFrom = new DepAcctId_Type();
		depAcctIdFrom.setSystemId("SystemId");
		depAcctIdFrom.setAcctId("1234123412341234");

		CustInfo_Type custInfo = new CustInfo_Type();
		PersonInfo_Type personInfo = new PersonInfo_Type();
		personInfo.setPersonName(getPersonName());
		custInfo.setPersonInfo(personInfo);
		depAcctIdFrom.setCustInfo(custInfo);

		BankInfo_Type bankInfo = new BankInfo_Type();
		bankInfo.setRbBrchId("123");
		depAcctIdFrom.setBankInfo(bankInfo);
		xferInfo.setDepAcctIdFrom(depAcctIdFrom);

		CardAcctId_Type cardAcctIdTo = new CardAcctId_Type();
		cardAcctIdTo.setSystemId("SystemId");
		cardAcctIdTo.setAcctId("123123123123");
		cardAcctIdTo.setEndDt(getStringDate(new GregorianCalendar()));
		cardAcctIdTo.setCustInfo(custInfo);
		cardAcctIdTo.setBankInfo(bankInfo);
		xferInfo.setCardAcctIdTo(cardAcctIdTo);

		xferInfo.setAcctCur("RUR");
		request.setXferInfo(xferInfo);

		ifxRq.setXferAddRq(request);
		return ifxRq;
	}

	//3.33
	public IFXRq_Type createXferAddRqTCP()
	{
		IFXRq_Type ifxRq = new IFXRq_Type();

		XferAddRq_Type request = new XferAddRq_Type();
		request.setRqUID(generateUUID());
		request.setRqTm(generateRqTm());
		request.setOperUID(generateOUUID());
		request.setSPName(getSPName());
		request.setBankInfo(getBankInfo());

		XferInfo_Type xferInfo = new XferInfo_Type();
		xferInfo.setXferMethod("xferMethod");
		xferInfo.setTaxIdTo("1234567890");

		CardAcctId_Type cardAcctIdFrom = new CardAcctId_Type();
		cardAcctIdFrom.setSystemId("SystemId");
		cardAcctIdFrom.setCardNum("1234123412341234");
		cardAcctIdFrom.setEndDt(getStringDate(new GregorianCalendar()));

		BankInfo_Type bankInfo = new BankInfo_Type();
		bankInfo.setRbBrchId("123");
		cardAcctIdFrom.setBankInfo(bankInfo);
		xferInfo.setCardAcctIdFrom(cardAcctIdFrom);

		DepAcctId_Type depAcctIdTo = new DepAcctId_Type();
		depAcctIdTo.setAcctId("123123123123");
		depAcctIdTo.setBIC("12312313212");
		depAcctIdTo.setCorrAcctId("11111111111111");
		xferInfo.setDepAcctIdTo(depAcctIdTo);
		request.setXferInfo(xferInfo);

		ifxRq.setXferAddRq(request);
		return ifxRq;
	}

	//3.34
	public IFXRq_Type createSvcAddRq()
	{
		IFXRq_Type ifxRq = new IFXRq_Type();

		SvcAddRq_Type request = new SvcAddRq_Type();
		request.setRqUID(generateUUID());
		request.setRqTm(generateRqTm());
		request.setOperUID(generateOUUID());
		request.setSPName(getSPName());
		request.setBankInfo(getBankInfo());

		XferInfo_Type xferInfo = new XferInfo_Type();
		xferInfo.setXferMethod("xferMethod");
		xferInfo.setTaxIdTo("1234567890");

		CustInfo_Type custInfo = new CustInfo_Type();
		PersonInfo_Type personInfo = new PersonInfo_Type();
		personInfo.setPersonName(getPersonName());
		custInfo.setPersonInfo(personInfo);
		xferInfo.setCustInfo(custInfo);

		DepAcctId_Type depAcctIdFrom = new DepAcctId_Type();
		depAcctIdFrom.setSystemId("SystemId");
		depAcctIdFrom.setAcctId("1234123412341234");

		BankInfo_Type bankInfo = new BankInfo_Type();
		bankInfo.setRbBrchId("123");
		depAcctIdFrom.setBankInfo(bankInfo);
		xferInfo.setDepAcctIdFrom(depAcctIdFrom);

		DepAcctId_Type depAcctIdTo = new DepAcctId_Type();
		depAcctIdTo.setAcctId("123123123123");
		depAcctIdTo.setBIC("12312313212");
		depAcctIdTo.setCorrAcctId("11111111111111");
		xferInfo.setDepAcctIdTo(depAcctIdTo);
		xferInfo.setCurAmt1(BigDecimal.valueOf(10.5));
		xferInfo.setAcctCur1("RUR");
		request.setXferInfo(xferInfo);

		ifxRq.setSvcAddRq(request);
		return ifxRq;
	}

	//3.35
	public IFXRq_Type createBillingPayInqRq()
	{
		IFXRq_Type ifxRq = new IFXRq_Type();

		BillingPayInqRq_Type request = new BillingPayInqRq_Type();
		request.setRqUID(generateUUID());
		request.setRqTm(generateRqTm());
		request.setOperUID(generateOUUID());
		request.setSPName(getSPName());
		request.setBankInfo(getBankInfo());

		RecipientRec_Type recipientRec = new RecipientRec_Type();
		recipientRec.setTaxId("1231231231");
		recipientRec.setCorrAccount("10101010101010");
		recipientRec.setBIC("1232123");
		recipientRec.setAcctId("1234123412341234");
		request.setRecipientRec(recipientRec);

		ifxRq.setBillingPayInqRq(request);
		return ifxRq;
	}

	//3.36
	public IFXRq_Type createBillingPayPrepRq()
	{
		IFXRq_Type ifxRq = new IFXRq_Type();

		BillingPayPrepRq_Type request = new BillingPayPrepRq_Type();
		request.setRqUID(generateUUID());
		request.setRqTm(generateRqTm());
		request.setOperUID(generateOUUID());
		request.setSPName(getSPName());
		request.setBankInfo(getBankInfo());
		request.setSystemId("BillingSystemId");


		RecipientRec_Type recipientRec = new RecipientRec_Type();
		recipientRec.setCodeRecipientBS("123456789");
		recipientRec.setCodeService("987654321");
		recipientRec.setNameService("ServiceName");
		request.setRecipientRec(recipientRec);

		ifxRq.setBillingPayPrepRq(request);
		return ifxRq;
	}

	//3.37
	public IFXRq_Type createBillingPayExecRq()
	{
		IFXRq_Type ifxRq = new IFXRq_Type();

		BillingPayExecRq_Type request = new BillingPayExecRq_Type();
		request.setRqUID(generateUUID());
		request.setRqTm(generateRqTm());
		request.setOperUID(generateOUUID());
		request.setSPName(getSPName());
		request.setBankInfo(getBankInfo());

		CardAcctId_Type cardAcctId = new CardAcctId_Type();
		cardAcctId.setCardNum("1234123412341234");
		request.setCardAcctId(cardAcctId);
		request.setSystemId("BillingSystemId");

		RecipientRec_Type recipientRec = new RecipientRec_Type();
		recipientRec.setCodeRecipientBS("123456789");
		recipientRec.setCodeService("987654321");
		recipientRec.setName("Name");
		recipientRec.setNameService("ServiceName");
		recipientRec.setTaxId("1234567890");
		recipientRec.setBIC("123456");
		recipientRec.setAcctId("1234123412341234");
		request.setRecipientRec(recipientRec);

		request.setCommission(BigDecimal.valueOf(10.2));
		request.setCommissionCur("RUR");

		ifxRq.setBillingPayExecRq(request);
		return ifxRq;
	}

	//3.38
	public IFXRq_Type createServiceStmtRq()
	{
		IFXRq_Type ifxRq = new IFXRq_Type();

		ServiceStmtRq_Type request = new ServiceStmtRq_Type();
		request.setRqUID(generateUUID());
		request.setRqTm(generateRqTm());
		request.setOperUID(generateOUUID());
		request.setSPName(getSPName());
		//request.setBankInfo(getBankInfo());

		SvcAcctId_Type svcAcctId = new SvcAcctId_Type();
		svcAcctId.setSystemId("SystemId");
		svcAcctId.setSvcAcctNum(10L);
		BankInfo_Type bankInfo = new BankInfo_Type();
		bankInfo.setRbBrchId("123");
		svcAcctId.setBankInfo(bankInfo);
		request.setSvcAcctId(svcAcctId);

		request.setDtBegin(getStringDate(new GregorianCalendar()));
		request.setDtEnd(getStringDate(new GregorianCalendar()));

		ifxRq.setServiceStmtRq(request);
		return ifxRq;
	}

	//3.39
	public IFXRq_Type createSvcAcctAudRq()
	{
		IFXRq_Type ifxRq = new IFXRq_Type();

		SvcAcctAudRq_Type request = new SvcAcctAudRq_Type();
		request.setRqUID(generateUUID());
		request.setRqTm(generateRqTm());
		request.setOperUID(generateOUUID());
		request.setSPName(getSPName());
		request.setBankInfo(getBankInfo());

		SvcAcctId_Type svcAcctId = new SvcAcctId_Type();
		svcAcctId.setSystemId("SystemId");
		svcAcctId.setSvcAcctNum(10L);
		BankInfo_Type bankInfo = new BankInfo_Type();
		bankInfo.setBranchId("123");
		bankInfo.setAgencyId("123");
		bankInfo.setRegionId("123");
		bankInfo.setRbBrchId("123");
		svcAcctId.setBankInfo(bankInfo);
		SvcAcctAudRq_TypeSvcAcct svcAcct = new SvcAcctAudRq_TypeSvcAcct();
		svcAcct.setSvcAcctId(svcAcctId);
		request.setSvcAcct(new SvcAcctAudRq_TypeSvcAcct[]{svcAcct});

		ifxRq.setSvcAcctAudRq(request);
		return ifxRq;
	}

	//3.40
	public IFXRq_Type createSvcAcctDelRq()
	{
		IFXRq_Type ifxRq = new IFXRq_Type();

		SvcAcctDelRq_Type request = new SvcAcctDelRq_Type();
		request.setRqUID(generateUUID());
		request.setRqTm(generateRqTm());
		request.setOperUID(generateOUUID());
		request.setSPName(getSPName());

		SvcAcctDelRq_TypeSvcAcct svcAcct = new SvcAcctDelRq_TypeSvcAcct();
		SvcAcctId_Type svcAcctId = new SvcAcctId_Type();
		svcAcctId.setSystemId("SystemId");
		svcAcctId.setSvcAcctNum(10L);
		BankInfo_Type bankInfo = new BankInfo_Type();
		bankInfo.setBranchId("123");
		bankInfo.setAgencyId("123");
		bankInfo.setRegionId("123");
		bankInfo.setRbBrchId("123");
		svcAcctId.setBankInfo(bankInfo);
		svcAcct.setSvcAcctId(svcAcctId);
		request.setSvcAcct(svcAcct);

		ifxRq.setSvcAcctDelRq(request);
		return ifxRq;
	}

	//3.42
	public IFXRq_Type createCardBlockRq()
	{
		IFXRq_Type ifxRq = new IFXRq_Type();

		CardBlockRq_Type request = new CardBlockRq_Type();
		request.setRqUID(generateUUID());
		request.setRqTm(generateRqTm());
		request.setOperUID(generateOUUID());
		request.setSPName(getSPName());
		request.setBankInfo(getBankInfo());

		CardAcctId_Type cardAcctId = new CardAcctId_Type();
		cardAcctId.setSystemId("SystemId");
		cardAcctId.setCardNum("1234123412341234");
		BankInfo_Type bankInfo = new BankInfo_Type();
		bankInfo.setRbBrchId("123");
		cardAcctId.setBankInfo(bankInfo);
		request.setCardAcctId(cardAcctId);
		request.setBlockReason(BlockReasonType.fromString(BLOCK_REASON));

		ifxRq.setCardBlockRq(request);
		return ifxRq;
	}

	//3.43
	public IFXRq_Type createXferAddRqTCC()
	{
		IFXRq_Type ifxRq = new IFXRq_Type();

		XferAddRq_Type request = new XferAddRq_Type();
		request.setRqUID(generateUUID());
		request.setRqTm(generateRqTm());
		request.setOperUID(generateOUUID());
		request.setSPName(getSPName());
		request.setBankInfo(getBankInfo());

		XferInfo_Type xferInfo = new XferInfo_Type();
		CardAcctId_Type cardAcctIdFrom = new CardAcctId_Type();
		cardAcctIdFrom.setSystemId("SystemId");
		cardAcctIdFrom.setCardNum("1234123412341234");
		cardAcctIdFrom.setEndDt(getStringDate(new GregorianCalendar()));
		BankInfo_Type bankInfo = new BankInfo_Type();
		bankInfo.setRbBrchId("123");
		cardAcctIdFrom.setBankInfo(bankInfo);
		xferInfo.setCardAcctIdFrom(cardAcctIdFrom);

		LoanAcctId_Type loanAcctIdTo = new LoanAcctId_Type();
		loanAcctIdTo.setSystemId("SystemId");
		loanAcctIdTo.setAcctId("1234123412341234");
		loanAcctIdTo.setBankInfo(bankInfo);
		xferInfo.setLoanAcctIdTo(loanAcctIdTo);

		xferInfo.setIdSpacing("123");
		xferInfo.setCurAmt(BigDecimal.valueOf(155.5));
		xferInfo.setAcctCur("RUR");
		request.setXferInfo(xferInfo);

		ifxRq.setXferAddRq(request);
		return ifxRq;
	}

	//3.44
	public IFXRq_Type createSvcAddRqSDC()
	{
		IFXRq_Type ifxRq = new IFXRq_Type();

		SvcAddRq_Type request = new SvcAddRq_Type();
		request.setRqUID(generateUUID());
		request.setRqTm(generateRqTm());
		request.setOperUID(generateOUUID());
		request.setSPName(getSPName());
		request.setBankInfo(getBankInfo());

		XferInfo_Type xferInfo = new XferInfo_Type();
		LoanAcctId_Type loanAcctIdTo = new LoanAcctId_Type();
		loanAcctIdTo.setSystemId("SystemId");
		loanAcctIdTo.setAcctId("1234123412341234");
		BankInfo_Type bankInfo = new BankInfo_Type();
		bankInfo.setRbBrchId("123");
		loanAcctIdTo.setBankInfo(bankInfo);
		xferInfo.setLoanAcctIdTo(loanAcctIdTo);

		xferInfo.setCurAmt(BigDecimal.valueOf(155.5));
		xferInfo.setAcctCur("RUR");
		request.setXferInfo(xferInfo);

		//странные вещи в xsd
		Regular_Type regular = new Regular_Type();
		regular.setDateBegin(getStringDate(new GregorianCalendar()));
		regular.setDateEnd(getStringDate(new GregorianCalendar()));
		regular.setPayDay(new Regular_TypePayDay(10L));
		regular.setExeEventCode("exeEventCode");
		regular.setSummaKindCode("SummaKindCode");

		ifxRq.setSvcAddRq(request);
		return ifxRq;
	}

	//3.48
	public IFXRq_Type createMDMClientInfoUpdateRq()
	{
		IFXRq_Type ifxRq = new IFXRq_Type();

		MDMClientInfoUpdateRq_Type request = new MDMClientInfoUpdateRq_Type();
		request.setRqUID(generateUUID());
		request.setRqTm(generateRqTm());
		request.setOperUID(generateOUUID());
		request.setSPName(getSPName());
		request.setBankInfo(getBankInfo());

		request.setOperType("ESK_MDF");
		CustRec_Type custRec = new CustRec_Type();
		custRec.setCustId("123");
		CustInfo_Type custInfo = new CustInfo_Type();
		custInfo.setEffDt(generateRqTm());
		PersonInfo_Type personInfo = new PersonInfo_Type();
		personInfo.setBirthday(getStringDate(new GregorianCalendar()));
		personInfo.setBirthPlace("Москва");
		personInfo.setTaxId("123456789");
		personInfo.setCitizenship("RF");
		personInfo.setGender("M");
		personInfo.setResident(true);
		personInfo.setPersonName(getPersonName());
		personInfo.setIdentityCard(getIdentityCard());
		custInfo.setPersonInfo(personInfo);
		custRec.setCustInfo(custInfo);

		ServiceInfo_Type serviceInfo = new ServiceInfo_Type();
		serviceInfo.setAgreementNum("456");
		serviceInfo.setStartDt(getStringDate(new GregorianCalendar()));
		serviceInfo.setEndDt(getStringDate(new GregorianCalendar()));
		BankInfo_Type bankInfo = new BankInfo_Type();
		bankInfo.setBranchId("123");
		bankInfo.setAgencyId("123");
		bankInfo.setRegionId("123");
		serviceInfo.setBankInfo(bankInfo);
		custRec.setServiceInfo(serviceInfo);

		request.setCustRec(custRec);

		ifxRq.setMDMClientInfoUpdateRq(request);
		return ifxRq;
	}

	//3.51
	public IFXRq_Type createBankAcctInqRqCESK()
	{
		IFXRq_Type ifxRq = new IFXRq_Type();

		BankAcctInqRq_Type request = new BankAcctInqRq_Type();
		request.setRqUID(generateUUID());
		request.setRqTm(generateRqTm());
		request.setOperUID(generateOUUID());
		request.setSPName(getSPName());
		request.setBankInfo(getBankInfo());

		CustInfo_Type custInfo = new CustInfo_Type();
		PersonInfo_Type personInfo = new PersonInfo_Type();
		personInfo.setBirthday(getStringDate(new GregorianCalendar()));
		personInfo.setPersonName(getPersonName());
		personInfo.setIdentityCard(getIdentityCard());
		custInfo.setPersonInfo(personInfo);
		request.setCustInfo(custInfo);

		ifxRq.setBankAcctInqRq(request);
		return ifxRq;
	}

	private PersonName_Type getPersonName()
	{
		return new PersonName_Type("Иванов", "Иван", null, null);
	}

	private IdentityCard_Type getIdentityCard()
	{
		IdentityCard_Type identityCard = new IdentityCard_Type();
		//21 - Паспорт гражданина РФ
		identityCard.setIdType("21");
		identityCard.setIdNum("123456");
		return identityCard;
	}

	private BankInfo_Type getBankInfo()
	{
		BankInfo_Type bankInfo = new BankInfo_Type();
		bankInfo.setRbTbBrchId("123");
		return bankInfo;
	}

	private Status_Type getStatus()
	{
		Random rand = new Random();
		int i = rand.nextInt(30);
		if (i == 0)
			return new Status_Type(-10L, "statusDesc", null, null);
		return new Status_Type(0L, null, null, null);
	}

	protected SPName_Type getSPName()
	{
		return SPName_Type.fromString(SP_NAME);
	}

	private String[] getAcctType()
	{
		return new String[]{ACCT_TYPE_CARD};
	}
}
