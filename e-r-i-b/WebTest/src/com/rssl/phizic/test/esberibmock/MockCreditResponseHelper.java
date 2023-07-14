package com.rssl.phizic.test.esberibmock;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.test.webgate.esberib.generated.*;
import com.rssl.phizic.test.webgate.esberib.utils.LoanHelper;
import com.rssl.phizic.utils.xml.XMLDatatypeHelper;
import org.apache.commons.collections.CollectionUtils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

/**
 @author: Egorovaa
 @ created: 06.03.2012
 @ $Author$
 @ $Revision$
 */
public class MockCreditResponseHelper extends MockBaseResponseHelper
{
	private static final MockProductService mockProductService = new MockProductService();
	private static final LoanHelper loanHelper = new LoanHelper();

	public LoanAcctRec_Type[] getLoanAcctRecs(Long gflId) throws BusinessException
	{
		List<MockCredit> credits = mockProductService.getCredits(gflId);
		if (CollectionUtils.isEmpty(credits)) // кредиты не найдены
		{
			return null;
		}
		else
		{
			LoanAcctRec_Type[] loanAcctRecs = new LoanAcctRec_Type[credits.size()];
			for (int i=0; i<credits.size(); i++)
			{
				LoanAcctRec_Type loanAcctRec = new LoanAcctRec_Type();
				MockCredit credit = credits.get(i);

				LoanAcctId_Type loanAcctId = new LoanAcctId_Type();
				loanAcctId.setSystemId(credit.getSystemId());
				loanAcctId.setAcctId(credit.getAcctId());
				loanAcctId.setAgreemtNum(credit.getAgreemtNum());
				loanAcctId.setProdType(credit.getProdType());
				loanAcctId.setLoanType(credit.getLoanType());
				loanAcctId.setAcctCur(credit.getAcctCur());
				loanAcctId.setOrigAmt(credit.getOrigAmt());
				loanAcctRec.setLoanAcctId(loanAcctId);

                loanAcctId.setProdType("12345123451234512345");
				loanAcctRec.setBankInfo(createMockBankInfo(credit));
				loanAcctRec.setBankAcctInfo(createMockBankAcctInfo(credit));

				loanAcctRec.setCustRec(createMockCustInfo(credit));

				loanAcctRecs[i] = loanAcctRec;
			}
			return loanAcctRecs;
		}
	}

	/**
	 * Заполнение дополнительных параметров для кредита
	 * @param credit
	 * @return
	 */
	private BankAcctInfo_Type createMockBankAcctInfo(MockCredit credit)
	{
		DateFormat mockDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		BankAcctInfo_Type bankAcctInfo = new BankAcctInfo_Type();
		bankAcctInfo.setAnn(credit.isAnn());
		bankAcctInfo.setIsAnn(credit.isAnn());
		if (credit.getStartDt() != null)
			bankAcctInfo.setStartDt(mockDateFormat.format(credit.getStartDt().getTime()));
		if (credit.getExpDt() != null)
			bankAcctInfo.setExpDt(mockDateFormat.format(credit.getExpDt().getTime()));
		return bankAcctInfo;
	}

	protected String getStringDateTime(Calendar date)
	{
		return XMLDatatypeHelper.formatDateTimeWithoutTimeZone(date);
	}

	/**
	 * Заполнение детальной информации о заемщике
	 * @param credit кредит, для которого формируются сведения
	 * @return информация о заемщике
	 */
	private CustRec_Type createMockCustInfo(MockCredit credit)
	{
		DateFormat mockDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		CustRec_Type custRec = null;
		if (credit.getPersonInfo() != null)
		{
			custRec = new CustRec_Type();
			CustInfo_Type custInfo = new CustInfo_Type();
			PersonInfo_Type personInfo = new PersonInfo_Type();

			PersonName_Type personName = new PersonName_Type();
			personName.setFirstName(credit.getPersonInfo().getFirstName());
			personName.setLastName(credit.getPersonInfo().getLastName());
			personName.setMiddleName(credit.getPersonInfo().getMiddleName());
			personInfo.setPersonName(personName);
			personInfo.setBirthday(mockDateFormat.format(credit.getPersonInfo().getBirthday().getTime()));
			personInfo.setBirthPlace(credit.getPersonInfo().getBirthPlace());
			personInfo.setTaxId(credit.getPersonInfo().getTaxId());
			personInfo.setCitizenship(credit.getPersonInfo().getCitizenship());
			personInfo.setAdditionalInfo(credit.getPersonInfo().getAdditionalInfo());

			IdentityCard_Type identityCard = new IdentityCard_Type();
			identityCard.setIdType(credit.getPersonInfo().getIdType());
			identityCard.setIdSeries(credit.getPersonInfo().getIdSeries());
			identityCard.setIdNum(credit.getPersonInfo().getIdNum());
			identityCard.setIssuedBy(credit.getPersonInfo().getIssuedBy());
			identityCard.setIssuedCode(credit.getPersonInfo().getIssuedCode());
			if (credit.getPersonInfo().getIssueDt() != null)
				identityCard.setIssueDt(mockDateFormat.format(credit.getPersonInfo().getIssueDt().getTime()));
			if (credit.getPersonInfo().getExpDt() != null)
				identityCard.setExpDt(mockDateFormat.format(credit.getPersonInfo().getExpDt().getTime()));

			personInfo.setPersonRole(credit.getPersonRole());
			personInfo.setIdentityCard(identityCard);
			custInfo.setPersonInfo(personInfo);
			custRec.setCustInfo(custInfo);
		}
		return custRec;
	}

	/**
	 * Заполнение информации о банке
	 * @param credit кредит, для которого формируются сведения
	 * @return информация о банке
	 */
	private BankInfo_Type createMockBankInfo(MockCredit credit)
	{
		BankInfo_Type bankInfo = new BankInfo_Type();
		bankInfo.setBranchId(credit.getBranchId());
		bankInfo.setAgencyId(credit.getAgencyId());
		bankInfo.setRegionId(credit.getRegionId());
		bankInfo.setRbBrchId(credit.getRbBrchId());
		return bankInfo;
	}

	/**
	 * Заполняем детальную информацию по кредитам (для LN_CI)
	 * @param parameters - входные параметры
	 * @return
	 * @throws BusinessException
	 */
	public IFXRs_Type createLoanInfoRs(IFXRq_Type parameters) throws BusinessException
	{
		Status_Type responseStatus = new Status_Type(0L, null, null, null);
		LoanInfoRq_Type request = parameters.getLoanInfoRq();
		IFXRs_Type ifxRs = new IFXRs_Type();

		LoanInfoRs_Type response = new LoanInfoRs_Type();
		response.setRqUID(request.getRqUID());
		response.setRqTm(request.getRqTm());
		response.setOperUID(request.getOperUID());
		response.setStatus(responseStatus);

		RequestLNCI requestLNCI = mockProductService.getCreditByLNCI(request);

		if (requestLNCI == null)  // если объект пустой(ничего не нашли по запросу), то вызываем супер
		{
			return loanHelper.createLoanInfoRs(parameters);
		}
		else if (requestLNCI.getMockCredit()==null)  // результат есть, кредит не получен
		{
			responseStatus = new Status_Type(-10L, "Ошибка: По данным параметрам запроса LNCI кредит не получен.",
						null, "Ошибка получения детальной информации по кредиту " + parameters.getLoanInfoRq().getLoanAcctId().getAcctId());
			response.setStatus(responseStatus);
			ifxRs.setLoanInfoRs(response);
		}
		else
		{
			LoanRec_Type loanRec = new LoanRec_Type();
			MockCredit credit = requestLNCI.getMockCredit();

			LoanInfo_Type loanInfo = new LoanInfo_Type();

			LoanAcctId_Type accId = new LoanAcctId_Type();
			accId.setSystemId(credit.getSystemId());
			accId.setAcctId(credit.getAcctId());
			loanInfo.setLoanAcctId(accId);

			loanInfo.setAgreemtNum(credit.getAgreemtNum());
			loanInfo.setProdType(credit.getProdType());
			loanInfo.setLoanType(credit.getLoanType());
			loanInfo.setAcctCur(credit.getAcctCur());
			loanInfo.setCurAmt(credit.getOrigAmt());
			loanInfo.setCustRec(new CustRec_Type[]{createMockCustInfo(credit)});

			loanRec.setLoanInfo(loanInfo);
			loanRec.setBankInfo(createMockBankInfo(credit));
			loanRec.setBankAccInfo(createMockBankAcctInfo(credit));
			loanRec.setStatus(responseStatus);

			response.setLoanRec(loanRec);
			ifxRs.setLoanInfoRs(response);
		}

		return ifxRs;
	}
}
