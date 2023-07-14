package com.rssl.phizic.scheduler.jobs.infobank;

import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizicgate.esberibgate.messaging.RequestHelperBase;
import com.rssl.phizicgate.esberibgate.ws.generated.*;

/**
 * ������ ��� ��������� ������� ��� ������������ ������ �������������� ���� � ����������
 * @author tisov
 * @ created 24.10.13
 * @ $Author$
 * @ $Revision$
 */

public class SecurityAccountTestRequestHelper extends RequestHelperBase
{
	public SecurityAccountTestRequestHelper(GateFactory factory)
	{
		super(factory);
	}

	/**
	 * ��������� �������
	 * @return ������, ����������� �������� ���������� �������, �������� - ������� �� ����� infobank.properties
	 */
	public IFXRq_Type createSecurityAccountListRequest()
	{
		InfobankMonitoringConfig config = ConfigFactory.getConfig(InfobankMonitoringConfig.class);
		/**
		 * ���������� �� ��������� �������������� ��������
		 */
		IdentityCard_Type identityCard = new IdentityCard_Type();
		identityCard.setIdType(config.getIdType());
		identityCard.setIdSeries(config.getIdSeries());
		identityCard.setIdNum(config.getIdNumber());
		/**
		 * ���������� �� ���
		 */
		PersonName_Type personName = new PersonName_Type();
		personName.setFirstName(config.getFirstName());
		personName.setLastName(config.getLastName());
		/**
		 * ���������� � ������������ ������
		 */
		PersonInfo_Type personInfo = new PersonInfo_Type();
		personInfo.setBirthday(config.getBirthday());
		personInfo.setPersonName(personName);
		personInfo.setIdentityCard(identityCard);
		/**
		 * ���������� � �����������
		 */
		CustInfo_Type custInfo = new CustInfo_Type();
		custInfo.setPersonInfo(personInfo);
		/**
		 * ���������� � �����
		 */
		BankInfo_Type bankInfo = new BankInfo_Type();
		bankInfo.setRbTbBrchId(config.getRbTbBrchId());

		/**
		 * ���������� � �������
		 */
		BankAcctInqRq_Type bankAcctInqRq = getMainBankAcctInqRq();
		bankAcctInqRq.setCustInfo(custInfo);
		bankAcctInqRq.setBankInfo(bankInfo);
		AcctType_Type[] accType = new AcctType_Type[1];
		accType[0] = AcctType_Type.fromString(config.getAccountType());
		bankAcctInqRq.setAcctType(accType);
		/**
		 * �������� ���������� �������
		 */
		IFXRq_Type ifxRq_type = new IFXRq_Type();
		ifxRq_type.setBankAcctInqRq(bankAcctInqRq);
		return ifxRq_type;
	}
}
