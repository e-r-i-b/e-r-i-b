package com.rssl.phizic.test.esberibmock;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.test.webgate.esberib.generated.*;
import com.rssl.phizic.test.webgate.esberib.utils.AccountResponseHelper;
import org.hibernate.util.StringHelper;
import org.apache.commons.collections.CollectionUtils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Egorovaa
 * Date: 29.11.2011
 * Time: 8:50:19
 */
public class MockAccountResponseHelper extends MockBaseResponseHelper
{
	private static final AccountResponseHelper accountResponseHelper = new AccountResponseHelper();
	private static final MockProductService mockProductService = new MockProductService();

	/**
	 * ������������ ������ ��� GFL, ����������� ���������� �� �������
	 * @param gflId ������������� ������� GFL, �� �������� �������� �����
	 * @return ������ �������, ���������� �� ������� GFL
	 * @throws BusinessException
	 * @throws BusinessLogicException
	 */
	public DepAcctRec_Type[] getDepAcctRec(Long gflId) throws BusinessException, BusinessLogicException
	{
		List<MockDeposit> deposits = mockProductService.getDeposits(gflId);
		if (CollectionUtils.isEmpty(deposits)) // ������ �� �������
		{
			return null;
		}
		else  // ���� ������ �������
		{
			DepAcctRec_Type[] depAcctRecs = new DepAcctRec_Type[deposits.size()];
			for (int i = 0; i < deposits.size(); i++)
			{
				DepAcctRec_Type depAcctRec = new DepAcctRec_Type();
				MockDeposit deposit = deposits.get(i);
				depAcctRec.setDepAcctId(createMockAccount(deposit));
				if (deposit.isNeedDepAccInfo())
					depAcctRec.setDepAccInfo(createDepAccInfo(deposit));
				depAcctRec.setBankInfo(createMockBankInfo(deposit));
				depAcctRec.setAcctBal(buildBalance(deposit.getAcctBal()));
				if (deposit.isNeedBankAcctPermiss())
					depAcctRec.setBankAcctPermiss(createPermissions(deposit));

				depAcctRecs[i] = depAcctRec;
			}
			return depAcctRecs;
		}
	}

	/**
	 * ���������� ���� ������� � �����
	 * @param deposit �����, ��� �������� ����������� ��������
	 * @return ������ ���� �������
	 * @throws BusinessException
	 */
	private BankAcctPermiss_Type[] createPermissions(MockDeposit deposit) throws BusinessException
	{
		if (!StringHelper.isEmpty(deposit.getBankAcctPermiss()))
		{
			// �������� � ��������� ������, ���������� �������� ���� �������
			// ����� ������ �� �����, ����������� ����� (��������� ��������) ���� [��� ����������� �������] - [�����������]
			String[] strPermissions = deposit.getBankAcctPermiss().replaceAll(" ", "").split(",");
			BankAcctPermiss_Type[] permissions = new BankAcctPermiss_Type[strPermissions.length];
			for (int i = 0; i < strPermissions.length; i++)
			{
				// ��������� �������� ����� ������� �� �����
				BankAcctPermiss_Type permission = new BankAcctPermiss_Type();
				String[] strPermission = strPermissions[i].split("-");
				permission.setPermissType(PermissType_Type.View);
				if (getBooleanValue(strPermission[1]) != null)
				{
					// ��������� ����������� ������ ����� ������� �������������� �������
					// strPermission[0] - ��� ����������� �������, strPermission[1] - ���� �������
					permission.setPermissValue(getBooleanValue(strPermission[1]));
					if (SPName_Type._BP_ES.equals(strPermission[0]))
						permission.setSPName(SPName_Type.BP_ES);
					else if (SPName_Type._BP_ERIB.equals(strPermission[0]))
						permission.setSPName(SPName_Type.BP_ERIB);
					else
						throw new BusinessException("������������ �������� ���� �������!");
					permissions[i] = permission;
				}
				else
				{
					throw new BusinessException("������������ �������� ���� �������!");
				}
			}
			return permissions;
		}
		else
			return null;
	}

	/**
	 * ��������, ���������� �� ������ �� �� ������ ��������
	 * @param value ����������� ������
	 * @return ������ ��������, ���� ������ �������������, ����� null
	 */
	private Boolean getBooleanValue(String value)
	{
		if (value.equalsIgnoreCase("true"))
			return true;
		if (value.equalsIgnoreCase("false"))
			return false;
		return null;
	}

	/**
	 * ���������� ���������� � �����
	 * @param deposit �����, ��� �������� ����������� ��������
	 * @return ���������� � �����
	 */
	private BankInfo_Type createMockBankInfo(MockDeposit deposit)
	{
		BankInfo_Type bankInfo = new BankInfo_Type();
		bankInfo.setBranchId(deposit.getBranchId());
		bankInfo.setAgencyId(deposit.getAgencyId());
		bankInfo.setRegionId(deposit.getRegionId());
		bankInfo.setRbBrchId(deposit.getRbBrchId());
		return bankInfo;
	}

	/**
	 * ���������� �������������� ���������� �� ������
	 * @param deposit �����, ��� �������� ����������� ��������
	 * @return ��������� ����������
	 */
	private DepAccInfo_Type createDepAccInfo(MockDeposit deposit)
	{
		DateFormat mockDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		DepAccInfo_Type depAccInfo = new DepAccInfo_Type();

		depAccInfo.setInterestOnDeposit(new InterestOnDeposit_Type(deposit.getInterestOnDepositAcctId(),
				deposit.getInterestOnDepositCardNum()));
		depAccInfo.setPeriod(deposit.getPeriod());
		depAccInfo.setRate(deposit.getRate());
		if (AccountStatusEnum_Type._value2.equals(deposit.getStatus()) && deposit.getEndDate() != null)
			depAccInfo.setEndDate(mockDateFormat.format(deposit.getEndDate().getTime()));
		depAccInfo.setIsCreditAllowed(deposit.isCreditAllowed());
		depAccInfo.setIsDebitAllowed(deposit.isDebitAllowed());
		depAccInfo.setIsProlongationAllowed(deposit.isProlongationAllowed());
		depAccInfo.setIsCreditCrossAgencyAllowed(deposit.isCreditCrossAgencyAllowed());
		depAccInfo.setIsDebitCrossAgencyAllowed(deposit.isDebitCrossAgencyAllowed());
		depAccInfo.setIsPassBook(deposit.isPassBook());

		CustRec_Type custRec = new CustRec_Type();
		custRec.setCustInfo(createMockCustInfo(deposit));
		if (custRec.getCustInfo()!=null)
			depAccInfo.setCustRec(custRec);

		return depAccInfo;
	}

	/**
	 * ���������� ��������� ���������� � ��������� �����
	 * @param deposit �����, ��� �������� ����������� ��������
	 * @return ���������� � ��������� �����
	 */
	private CustInfo_Type createMockCustInfo(MockDeposit deposit)
	{
		DateFormat mockDateFormat = new SimpleDateFormat("yyyy-MM-dd");

		if (deposit.getPersonInfo() != null)
		{
			CustInfo_Type custInfo = new CustInfo_Type();
			PersonInfo_Type personInfo = new PersonInfo_Type();

			PersonName_Type personName = new PersonName_Type();
			personName.setFirstName(deposit.getPersonInfo().getFirstName());
			personName.setLastName(deposit.getPersonInfo().getLastName());
			personName.setMiddleName(deposit.getPersonInfo().getMiddleName());
			personInfo.setPersonName(personName);
			personInfo.setBirthday(mockDateFormat.format(deposit.getPersonInfo().getBirthday().getTime()));
			personInfo.setBirthPlace(deposit.getPersonInfo().getBirthPlace());
			personInfo.setTaxId(deposit.getPersonInfo().getTaxId());
			personInfo.setCitizenship(deposit.getPersonInfo().getCitizenship());

			IdentityCard_Type identityCard = new IdentityCard_Type();
			identityCard.setIdType(deposit.getPersonInfo().getIdType());
			identityCard.setIdSeries(deposit.getPersonInfo().getIdSeries());
			identityCard.setIdNum(deposit.getPersonInfo().getIdNum());
			identityCard.setIssuedBy(deposit.getPersonInfo().getIssuedBy());
			identityCard.setIssuedCode(deposit.getPersonInfo().getIssuedCode());
			if (deposit.getPersonInfo().getIssueDt() != null)
				identityCard.setIssueDt(mockDateFormat.format(deposit.getPersonInfo().getIssueDt().getTime()));
			if (deposit.getPersonInfo().getExpDt() != null)
				identityCard.setExpDt(mockDateFormat.format(deposit.getPersonInfo().getExpDt().getTime()));

			personInfo.setIdentityCard(identityCard);
			custInfo.setPersonInfo(personInfo);
			return custInfo;
		}
		return null;
	}

	/**
	 * ���������� ���������� �� �������������� ����������� �����
	 * @param deposit �����, ��� �������� ����������� ��������
	 * @return
	 */
	private DepAcctId_Type createMockAccount(MockDeposit deposit)
	{
		DateFormat mockDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		DepAcctId_Type depAcctId = new DepAcctId_Type();
		if (deposit.getOpenDate() != null)
			depAcctId.setOpenDate(mockDateFormat.format(deposit.getOpenDate().getTime()));
		depAcctId.setAcctCur(deposit.getAcctCur());
		depAcctId.setAcctId(deposit.getAcctId());
		depAcctId.setAcctName(deposit.getAcctName());
		depAcctId.setSystemId(deposit.getSystemId());
		depAcctId.setAcctCode(deposit.getAcctCode());
		depAcctId.setAcctSubCode(deposit.getAcctSubCode());
		if (deposit.getStatus() != null)
			depAcctId.setStatus(getAccountState(deposit.getStatus()));

		return depAcctId;
	}

	/**
	 * ��������, ���������� �� ������ �� �� �������� �������� ��������� ������
	 * @param state ����������� ������
	 * @return ��������� ������ (���� AccountStatusEnum_Type), ���� ������ �������������,
	 * ����� null
	 */
	private AccountStatusEnum_Type getAccountState(String state)
	{
		if (AccountStatusEnum_Type._value1.equals(state)) // Opened
			return AccountStatusEnum_Type.value1;
		if (AccountStatusEnum_Type._value2.equals(state)) // Closed
			return AccountStatusEnum_Type.value2;
		if (AccountStatusEnum_Type._value3.equals(state)) // Arrested
			return AccountStatusEnum_Type.value3;
		if (AccountStatusEnum_Type._value4.equals(state)) // Lost-passbook
			return AccountStatusEnum_Type.value4;
		return null;
	}

	/**
	 * ���������� ������ �� ACC_DI-������ (��������� ��������� ���������� �� ������)
	 * @param parameters ������� ���������, �� ������� ����� ����������� �����
	 * @return ��������� ���������� �� ������
	 * @throws BusinessException
	 */
	public IFXRs_Type getAccountDetailInfo(IFXRq_Type parameters) throws BusinessException
	{
		DateFormat mockDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		IFXRs_Type ifxrs_Type = new IFXRs_Type();
		AcctInfoRs_Type acctInfoRs = new AcctInfoRs_Type();      

		AcctInfoRq_Type acctInfoRq = parameters.getAcctInfoRq();
		acctInfoRs.setRqUID(acctInfoRq.getRqUID());
		acctInfoRs.setOperUID(acctInfoRq.getOperUID());
		acctInfoRs.setRqTm(acctInfoRq.getRqTm());
		acctInfoRs.setStatus(new Status_Type(0L, null, null, null));

		DetailAcctInfo_Type[] detailAcctInfoArray  = new DetailAcctInfo_Type[acctInfoRq.getDepAcctRec().length];
		for (int i = 0; i < acctInfoRq.getDepAcctRec().length; i++)
		{
			DepAcctRec_Type depAcctRec = parameters.getAcctInfoRq().getDepAcctRec(i);
			RequestACCDI requestACCDI = mockProductService.getDepositByACCDI(parameters, depAcctRec);

			if (requestACCDI == null)   // ���� ������ ������(������ �� ����� �� �������), �� �������� �����
			{
				return accountResponseHelper.getAccountDetailInfo(parameters);
			}
			else if (requestACCDI.getMockDeposit()==null) // ��������� ����, ����� �� �������
			{
				DetailAcctInfo_Type detailAcctInfo = new DetailAcctInfo_Type();
				DepAcctId_Type depAcctId = new DepAcctId_Type();
				depAcctId.setSystemId(depAcctRec.getDepAcctId().getSystemId());
				depAcctId.setAcctId(acctInfoRq.getDepAcctRec()[i].getDepAcctId().getAcctId());
				Status_Type responseStatus = new Status_Type(-10L, "������: �� ������ ���������� ������� ACC_DI ����� �� �������.",
						null, "������ ��������� ��������� ���������� �� ����� " + depAcctRec.getDepAcctId().getAcctId());
				acctInfoRs.setStatus(responseStatus);
				detailAcctInfo.setStatus(responseStatus);
				detailAcctInfo.setDepAccInfo(new DepAccInfo_Type());
				detailAcctInfo.setDepAcctId(depAcctId);
				detailAcctInfoArray [i] = detailAcctInfo;
			}
			else
			{
				MockDeposit deposit = requestACCDI.getMockDeposit();

				DepAcctId_Type depAcctId = new DepAcctId_Type();
				depAcctId.setSystemId(deposit.getSystemId());
				depAcctId.setAcctId(deposit.getAcctId());

				DepAccInfo_Type depAccInfo = createDepAccInfo(deposit);
				depAccInfo.setAcctName(deposit.getAcctName());
				depAccInfo.setAcctCode(deposit.getAcctCode());
				depAccInfo.setAcctSubCode(deposit.getAcctSubCode());
				depAccInfo.setAcctCur(deposit.getAcctCur());

				AcctBal_Type[] limits = buildBalance(deposit.getAcctBal());
				for (AcctBal_Type limit : limits)
				{
					if (limit.getBalType().equalsIgnoreCase("Avail"))
						depAccInfo.setCurAmt(limit.getCurAmt());
					else if (limit.getBalType().equalsIgnoreCase("AvailCash"))
						depAccInfo.setMaxSumWrite(limit.getCurAmt());
					else if (limit.getBalType().equalsIgnoreCase("MinAvail"))
						depAccInfo.setIrreducibleAmt(limit.getCurAmt());
					else if (limit.getBalType().equalsIgnoreCase("ClearBalance"))
						depAccInfo.setClearBalance(limit.getCurAmt());
					else if (limit.getBalType().equalsIgnoreCase("MaxBalance"))
						depAccInfo.setClearBalance(limit.getCurAmt());
				}

				if (deposit.getOpenDate() != null)
					depAccInfo.setOpenDate(mockDateFormat.format(deposit.getOpenDate().getTime()));
				depAccInfo.setStatus(getAccountState(deposit.getStatus()));

				depAccInfo.setBankInfo(createMockBankInfo(deposit));

				DetailAcctInfo_Type detailAcctInfo = new DetailAcctInfo_Type();
				detailAcctInfo.setStatus(new Status_Type(0L, null, null, null));
				detailAcctInfo.setDepAccInfo(depAccInfo);
				detailAcctInfo.setDepAcctId(depAcctId);
				detailAcctInfoArray[0] = detailAcctInfo;
			}
		}
		acctInfoRs.setDetailAcctInfo(detailAcctInfoArray );
		ifxrs_Type.setAcctInfoRs(acctInfoRs);
		return ifxrs_Type;
	}
}
