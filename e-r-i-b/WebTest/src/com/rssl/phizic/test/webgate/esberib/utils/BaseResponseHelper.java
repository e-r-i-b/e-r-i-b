package com.rssl.phizic.test.webgate.esberib.utils;

import com.rssl.phizgate.common.services.bankroll.ExtendedCodeGateImpl;
import com.rssl.phizic.business.resources.external.ExternalResourceService;
import com.rssl.phizic.common.types.RequisiteType;
import com.rssl.phizic.common.types.exceptions.IKFLException;
import com.rssl.phizic.common.types.exceptions.InactiveExternalSystemException;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.config.PropertyReader;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.clients.BackRefClientService;
import com.rssl.phizic.gate.clients.Client;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.utils.ExternalSystemHelper;
import com.rssl.phizic.test.webgate.esberib.generated.*;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.utils.xml.XMLDatatypeHelper;

import java.math.BigDecimal;
import java.util.*;

/**
 * @author Balovtsev
 * @created 27.09.2010
 * @ $Author$
 * @ $Revision$
 */

public class BaseResponseHelper
{
    protected ExternalResourceService     resourceService    = new ExternalResourceService();

	protected static final String         SERVER_STATUS_DESC = "����� � ��������� ������ ��� �������������� (CHG030857).";
	/*������, ���������� ��� �������� ��������� � ���������� � ������������� ����������� ��������� ������� ��������*/
	protected static final long UNKNOW_DOCUMENT_STATE_ERROR_CODE = -105;

	private static final int MAX_SIZE_REQUISITE_TYPES = 10; // ������������ ���������� ���������� � ����, �������� ��� ����������� ���������� ������

	protected String getRqTm()
	{
		GregorianCalendar messageDate = new GregorianCalendar();
		return getStringDateTime(messageDate);
	}

	protected BigDecimal getRandomDecimal()
	{
		Random random = new Random();
		float count = random.nextInt(10000) + random.nextFloat();
		return BigDecimal.valueOf(count);
	}

	/**
	 * ���������� ��������� ����
	 * @return
	 */
	protected Calendar getRandomDate()
	{
		Random random = new Random();
		GregorianCalendar date = new GregorianCalendar();
		date.add(Calendar.DATE, -1 * random.nextInt(1000));
		return date;
	}


	protected String getStringDate(Calendar date)
	{
		return XMLDatatypeHelper.formatDateWithoutTimeZone(date);
	}

	protected String getStringDateTime(Calendar date)
	{
		return XMLDatatypeHelper.formatDateTimeWithoutTimeZone(date);
	}

	protected Status_Type getStatusForGFL(BankAcctInqRq_Type bankAcctInqRq)
	{
		//���� � ������� ���������� ����� � ������ �� �������� ��������� ����������� ����, ���� ���������� �� ������ 400.
		List<AcctType_Type> productList = Arrays.asList(bankAcctInqRq.getAcctType());
		try
		{
			if(productList.contains(AcctType_Type.fromValue("Card")) && bankAcctInqRq.getCustInfo().getPersonInfo().getIdentityCard().getIdType().equals("21"))
				ExternalSystemHelper.check("urn:sbrfsystems:40-cod");
		}
		catch (InactiveExternalSystemException e)
		{
			return new Status_Type(-400L, e.getMessage(), null, "��� ����������.");
		}
		catch (GateException e)
		{
			//nothing
		}
		return getStatus();
	}

	protected Status_Type getStatus()
	{
		Random rand = new Random();
		int i = rand.nextInt(30);
		if (i == 0)
		{
			return new Status_Type(-10L, "�������� ������, ������� ��� ����������� ������������.", null, SERVER_STATUS_DESC);
		}
		return new Status_Type(0L, null, null, null);
	}

	//���� ����� ��� ����� ������, � ��� ������������� ������� ��� ����.
	protected BankInfo_Type getMockBankInfo()
	{
		ExtendedCodeGateImpl code = new ExtendedCodeGateImpl("77", "1573", "15730");
		return getBankInfo(code);
	}

	protected BankInfo_Type getBankInfo(ExtendedCodeGateImpl code)
	{
		BankInfo_Type bankInfo = new BankInfo_Type();

		//����� �������������(���).
		String agencyId = StringHelper.appendLeadingZeros(code.getBranch(), 4);
		//����� ��������(��).
		String regionId = StringHelper.appendLeadingZeros(code.getRegion(), 2);
		//������� ��� ������ ��� ������ ���������������� ����� � ������� RbTbBrch � ��� �������� ��� ��.
		//�������� ������ ��������������� ����������� ��������� �[0-9]{6}�
		StringBuilder rbBrchId = new StringBuilder();

		rbBrchId.append(regionId).append(agencyId);

		if (!StringHelper.isEmpty(code.getOffice()))
			bankInfo.setBranchId(code.getOffice());
		if (!StringHelper.isEmpty(code.getBranch()))
			bankInfo.setAgencyId(code.getBranch());
		bankInfo.setRegionId(code.getRegion());
		bankInfo.setRbBrchId(rbBrchId.toString());
		return bankInfo;
	}

	protected BankInfo_Type getBankInfo(Long loginId)
	{
		try
		{
			BackRefClientService clientService = GateSingleton.getFactory().service(BackRefClientService.class);
			Client client = clientService.getClientById(loginId);
			ExtendedCodeGateImpl code = new ExtendedCodeGateImpl(client.getOffice().getCode());
		    return getBankInfo(code);
		}
		catch (IKFLException e)
		{
			return getMockBankInfo();
		}
	}

	protected BankInfo_Type getBankInfo(String rbTbBrch)
	{
		BankInfo_Type bankInfo = new BankInfo_Type();
		bankInfo.setRbTbBrchId(rbTbBrch);

		return bankInfo;
	}

	protected CustInfo_Type getMockCustInfo()
	{
		CustInfo_Type custInfo = new CustInfo_Type();
		PersonInfo_Type personInfo = new PersonInfo_Type();

		PersonName_Type personName = new PersonName_Type();
		personName.setFirstName("������");
		personName.setLastName("�����������");
		personName.setMiddleName("����������");
		personInfo.setPersonName(personName);
		personInfo.setBirthday("2011-12-27");

		IdentityCard_Type identityCard = new IdentityCard_Type();
		identityCard.setIdType("21");
		identityCard.setIdSeries("2501");
		identityCard.setIdNum("250116");

		personInfo.setIdentityCard(identityCard);
		custInfo.setPersonInfo(personInfo);

		personInfo.setGender("1");

		return custInfo;
	}

	/**
	 *
	 * ������� ��������� ������ ��� ������ �� ������
	 *
	 * @return boolean
	 */
	protected boolean isNightBuildsEnabled()
	{
		PropertyReader testConfigReader   = ConfigFactory.getReaderByFileName("default.test.properties");
		return Boolean.parseBoolean( testConfigReader.getProperty("test.night.builds.enabled") );	
	}

	/**
	 * ������� ������ ���������� ���� ������������� �������, ����� ������ ����
	 * @return ������ ����������
	 */
	protected String[] createRandomRequisiteTypes()
	{
		Random random = new Random();
		int size = random.nextInt(MAX_SIZE_REQUISITE_TYPES);
		return size == 0 ? null : createRequisiteTypes(size);
	}

	/**
	 * ������� ������ ���������� ���� ��������� �������
	 * ��������� ���������� ����������� �� ��������� ��������� ����� RequisiteType
	 * @param size ������ �������
	 * @return ������ ����������
	 */
	private String[] createRequisiteTypes(int size)
	{
		Random random = new Random();
		String[] result = new String[size];
		RequisiteType[] resource = RequisiteType.values();

		for (int i = 0; i < size; i++)
		{
			int randomResource = random.nextInt(resource.length);
			result[i] = resource[randomResource].getDescription();
		}
		return result;
	}
}
