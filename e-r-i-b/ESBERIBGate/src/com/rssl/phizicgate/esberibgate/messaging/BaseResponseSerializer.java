package com.rssl.phizicgate.esberibgate.messaging;

import com.rssl.phizgate.common.services.bankroll.ExtendedCodeGateImpl;
import com.rssl.phizgate.common.services.bankroll.ExtendedOfficeGateImpl;
import com.rssl.phizgate.common.services.types.CodeImpl;
import com.rssl.phizic.common.types.Currency;
import com.rssl.phizic.common.types.Money;
import com.rssl.phizic.common.types.exceptions.IKFLException;
import com.rssl.phizic.common.types.transmiters.Pair;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.config.OfficeCodeReplacer;
import com.rssl.phizic.gate.GateSingleton;
import com.rssl.phizic.gate.ProductPermission;
import com.rssl.phizic.gate.bankroll.Account;
import com.rssl.phizic.gate.bankroll.Card;
import com.rssl.phizic.gate.clients.ClientDocument;
import com.rssl.phizic.gate.currency.CurrencyService;
import com.rssl.phizic.gate.dictionaries.officies.BackRefOfficeGateService;
import com.rssl.phizic.gate.dictionaries.officies.Code;
import com.rssl.phizic.gate.dictionaries.officies.Office;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.ima.IMAccount;
import com.rssl.phizic.jmx.BusinessSettingsConfig;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.utils.ListUtil;
import com.rssl.phizic.utils.PassportTypeWrapper;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.utils.xml.XMLDatatypeHelper;
import com.rssl.phizicgate.esberibgate.types.ClientDocumentImpl;
import com.rssl.phizicgate.esberibgate.types.ClientImpl;
import com.rssl.phizicgate.esberibgate.types.CurrencyHelper;
import com.rssl.phizicgate.esberibgate.types.ProductPermissionImpl;
import com.rssl.phizicgate.esberibgate.utils.EntityIdHelper;
import com.rssl.phizicgate.esberibgate.ws.generated.*;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.*;

/**
 * @author Balovtsev
 * @created 27.09.2010
 * @ $Author$
 * @ $Revision$
 */

public class BaseResponseSerializer
{
	protected static Log log = PhizICLogFactory.getLog(Constants.LOG_MODULE_GATE);
	private static final String FULL_NAME_DELIMETER = " ";

	/*
	*  CHG027259
	*/

	protected static final Long CORRECT_MESSAGE_STATUS = 0L;
	protected static final Long ESB_REALLY_NOT_SUPPORTED = -102L;
	protected static final Long CLIENT_NOT_FOUND = -425L;
	protected static final Long DIFFERENT_CLIENT_DATA = -424L;
	protected static final Long OFFLINE_SYSTEM_STATUS_400 = -400L;
	protected static final Set<Long> OFFLINE_SYSTEM_STATUSES;

	static
	{
		OFFLINE_SYSTEM_STATUSES = new HashSet<Long>(3);
		OFFLINE_SYSTEM_STATUSES.add(-100L);
		OFFLINE_SYSTEM_STATUSES.add(-105L);
		OFFLINE_SYSTEM_STATUSES.add(OFFLINE_SYSTEM_STATUS_400);
	}

	public BaseResponseSerializer()
	{
	}

	/**
	 * �������������� �� � ������������� �� ������� ����
	 * @param ifxRs - ����� �� ����
	 * @return true - �������������� ��, false - �� �������������� ��
	 */
	public boolean isESBReallySupported(IFXRs_Type ifxRs)
	{
		Status_Type status = ifxRs.getBankAcctInqRs().getStatus();
		//���� ������ != -102, �� �������������, ����� �� ��������������.
		return status.getStatusCode() != ESB_REALLY_NOT_SUPPORTED;
	}

	/*
	�������� ����� ������������ ���������. ���� ������ ���, ���������� null.
	 */
	protected IKFLException checkResponse(Object responseObj)
	{
		if (responseObj==null)
		{
			return new GateException("���������� �� ��������");
		}
		return null;
	}


	protected static Calendar parseCalendar(String date)
	{
		if (StringHelper.isEmpty(date))
			return null;
		return XMLDatatypeHelper.parseDateTime(date);
	}

	/*
	������������� ����������� ���� ������ � ������
	*/
	protected Currency getCurrencyByString(String currencyCode)
	{
		CurrencyService currencyService = GateSingleton.getFactory().service(CurrencyService.class);

		try
		{
			String code = CurrencyHelper.getCurrencyCode(currencyCode);
			//�������� ������ �� ���������� ��� ������ ISO (alphabetical)
			return currencyService.findByAlphabeticCode(code);
		}
		catch (IKFLException e)
		{
			log.error("������ �������������� ������. ISO ��� = " + currencyCode, e);
			return null;
		}
	}

	/*
	���������� ����� ����������� �������
	 */
	protected Office getOffice(BankInfo_Type bankInfo) throws GateLogicException, GateException
	{
		if(bankInfo == null)
			return null;

		Code code = getOfficeCode(bankInfo);
		Office office = getOffice(code);
		if(office != null)
			return office;

		office = new ExtendedOfficeGateImpl();
		office.setCode(code);

		return office;
	}

	/**
	 * ���������� ��� ������������� ����� � ������������������� ��������� (�� ������������ �������� ��� ������)
	 * @param bankInfo
	 * @return
	 */
	protected Code getOriginalTbCode(BankInfo_Type bankInfo)
	{
		if(bankInfo == null)
			return null;

		return new CodeImpl(bankInfo.getRegionId(), bankInfo.getAgencyId(), bankInfo.getBranchId());
	}

	/**
	 *
	 * ��������� ����� ��� ������� ����, � �������������������� �� ������ ����� (CHG027259)
	 *
	 * @param bankInfo
	 * @return
	 * @throws GateLogicException
	 * @throws GateException
	 */
	protected Office getCorrectedOffice(BankInfo_Type bankInfo) throws GateLogicException, GateException
	{
		if (bankInfo == null)
			return null;

		//������ �����, ����� ���������� �� �������� ���������������� �������� �� ������ ����.
		BankInfo_Type bankInfoCopy = new BankInfo_Type(bankInfo.getBranchId(),bankInfo.getAgencyId(), bankInfo.getRegionId(),bankInfo.getRbTbBrchId(), bankInfo.getRbBrchId());

		bankInfoCopy.setAgencyId(ConfigFactory.getConfig(OfficeCodeReplacer.class).replaceCode(bankInfoCopy.getRegionId(), bankInfoCopy.getAgencyId()));

		ExtendedCodeGateImpl code = getOfficeCode(bankInfoCopy);
		return findOffice(code);
	}

	/**
	 * ��������� ������������� ��� �������� Account, IMAccount � ���������������� �����
	 * @param regionId ��
	 * @param agencyId ���
	 * @param branchId ���
	 * @return �������������
	 */
	public Office getCorrectedOffice(String regionId, String agencyId, String branchId) throws GateLogicException, GateException
	{
		if (regionId == null || agencyId == null)
			return null;

		String agency = ConfigFactory.getConfig(OfficeCodeReplacer.class).replaceCode(regionId, agencyId);
		ExtendedCodeGateImpl code = new ExtendedCodeGateImpl(getCorrectTB(regionId), agency, branchId);
		return findOffice(code);
	}

	/**
	 * ����� ������������� �� ��, ���, ���. ���� �� �������, �� ����� �� �� ���
	 * @param code ���
	 * @return �������������
	 */
	private Office findOffice(ExtendedCodeGateImpl code) throws GateLogicException, GateException
	{
		Office findedOffice = getOffice(code);

		if(findedOffice == null)
		{
			code.setOffice(null);
			findedOffice = getOffice(code);
			if (findedOffice == null)
				throw new GateException("�� ������� �� ������ ������������� ��� �� = " + code.getRegion() + " � ��� = " + code.getBranch());
		}

		return findedOffice;
	}

	/**
	 * �������� ��� ����� �� ��������� BankInfo_Type
	 * @param bankInfo BankInfo_Type
	 * @return ��� ����� ��� null, ���� bankInfo null
	 */
	public static ExtendedCodeGateImpl getOfficeCode(BankInfo_Type bankInfo)
	{
		if (bankInfo == null)
			return null;
		return new ExtendedCodeGateImpl(getCorrectTB(bankInfo.getRegionId()), bankInfo.getAgencyId(), bankInfo.getBranchId());
	}

	/**
	 * ���������� ��������� �������
	 * @param identityCard - ���������� ������  
	 * @return �������� �������
	 */
	protected ClientDocument fillDocument(IdentityCard_Type identityCard)
	{
		if (identityCard == null)
			return null;
		ClientDocumentImpl document = new ClientDocumentImpl();
		document.setDocumentType(PassportTypeWrapper.getClientDocumentType(identityCard.getIdType()));
		document.setDocSeries(identityCard.getIdSeries());
		document.setDocNumber(identityCard.getIdNum());
		document.setDocIdentify(true);
		document.setDocIssueBy(identityCard.getIssuedBy());
		document.setDocIssueByCode(identityCard.getIssuedCode());
		document.setDocIssueDate(StringHelper.isEmpty(identityCard.getIssueDt()) ? null : parseCalendar(identityCard.getIssueDt()));
		document.setDocTimeUpDate(StringHelper.isEmpty(identityCard.getExpDt()) ? null : parseCalendar(identityCard.getExpDt()));

		return document;
	}

	/**
	 * ���������� ������ ��� �������
	 * @param personName - ���� ��������
	 * @return fullName
	 */
	protected String getPersonFullName(PersonName_Type personName)
	{
		StringBuilder fullName = new StringBuilder();
		fullName.append(personName.getLastName()).append(FULL_NAME_DELIMETER);
		fullName.append(personName.getFirstName());
		if (!StringHelper.isEmpty(personName.getMiddleName()))
			fullName.append(FULL_NAME_DELIMETER).append(personName.getMiddleName());

		return fullName.toString();
	}

	/**
	 * ��������� ������ �� ������ �����, ��� ������ - ��� 6-8 ������� � �����
	 * @param accountNumber ����� �����
	 * @return ������
	 * @throws GateLogicException
	 * @throws GateException
	 */
	protected Currency getCurrencyByAccountNumber(String accountNumber) throws GateLogicException, GateException
	{
		CurrencyService service = GateSingleton.getFactory().service(CurrencyService.class);
		String code = accountNumber.substring(5,8);
		if (code.equals("810"))
			code = "643";
		return service.findByNumericCode(code);
	}

	/*
	����� �����/����� �� ������ �� ������
	 */
	protected <T> T findObject(String objectNumber, T... object) throws GateException
	{
		for (T obj : object)
		{
			String number;
			if (obj instanceof Card)
				number = ((Card)obj).getNumber();
			else if(obj instanceof Account)
				number = ((Account)obj).getNumber();
			else if(obj instanceof IMAccount)
				number = ((IMAccount)obj).getNumber();
			else break;
			if (objectNumber.equals(number))
				return obj;
		}

		throw new GateException("���������� ��������� �� �� ���� �������");
	}

	protected Pair<String, Office> findObjectInfo(String number, Pair<String, Office> ... objectInfo) throws GateException
	{
		for (Pair<String, Office> info : objectInfo)
		{
			if (number.equals(info.getFirst()))
				return info;
		}

		throw new GateException("���������� ��������� �� �� ���� �������");
	}

	/**
	 * ��������� ����� null save ����������� �� BigInteger � Long
	 * @param number BigInteger
	 * @return Long
	 */
	protected Long converToLong(BigInteger number)
	{
		if (number == null)
		{
			return null;
		}
		return number.longValue();
	}

	/**
	 * ��������� ����� null save ����������� �� BigInteger � Integer
	 * @param number BigInteger
	 * @return Integer
	 */
	protected Integer convertToInteger(BigInteger number)
	{
		if (number == null)
		{
			return null;
		}
		return number.intValue();
	}

	/**
	 * ��������� ����� null save ����������� �� ������� ����s� � ����
	 * @param data ������
	 * @return ����
	 */
	protected <T> List<T> convertToList(T[] data)
	{
		if (data == null)
		{
			return null;
		}
		return ListUtil.fromArray(data);
	}

	/**
	 * ��������� ������ ProductPermission
	 * @param permissions
	 * @return
	 */
	protected ProductPermission fillProductPermission(BankAcctPermiss_Type[] permissions)
	{
		if (permissions == null || permissions.length == 0)
			return null;

		ProductPermissionImpl result = new ProductPermissionImpl();
		for (BankAcctPermiss_Type perm : permissions)
		{
			log.trace("[com.rssl.phizicgate.esberibgate.messaging.BaseResponseSerializer.fillProductPermission] SPName = " + perm.getSPName().getValue() + "; isPermissValue = " + perm.isPermissValue());
			if (SPName_Type._BP_ES.equals(perm.getSPName().getValue()))
			{//������� ����������� �������� � ��
				result.setShowInES(!perm.isPermissValue());
			}
			else
			{//������� ����������� �������� � ����
				result.setShowInSbol(!perm.isPermissValue());
			}
		}

		return result;
	}


	/**
	 * ������� ������ ������
	 * @param decimal �����
	 * @param currency ISO ��� ������
	 * @return ������ ������, ���� �� �������� �� ����� �� ������ ������������ null
	 */
	public Money createMoney(BigDecimal decimal, String currency)
	{
		if (decimal == null && currency == null)
		{
			return null;
		}
		if (currency == null)
		{
			throw new IllegalArgumentException("�� ������ ������");
		}
		if (decimal == null)
		{
			throw new IllegalArgumentException("�� ������ �����");
		}
		return new Money(decimal, getCurrencyByString(currency));
	}

	/**
	 * �������� ������� ������, ������ ���� ��� ��������� �� null
	 * @param decimal �����
	 * @param currency ������
	 * @return ������ ������. ���� ������ ��� ����� �� ������, �� ���������� null
	 */
	public Money safeCreateMoney(BigDecimal decimal, Currency currency)
	{
		if (decimal == null || currency == null)
		{
			return null;
		}
		return new Money(decimal, currency);
	}

	/**
	 * ��������� ����� �� ���� ������������� �����
	 * @param code ��� ������������� �����
	 * @return ��������� ���� (null ���� ���� �� ������)
	 * @throws GateLogicException
	 * @throws GateException
	 */
	public Office getOffice(Code code) throws GateLogicException, GateException
	{
		if(StringHelper.isEmpty(new ExtendedCodeGateImpl(code).getRegion()))
			return null;

		BackRefOfficeGateService service = GateSingleton.getFactory().service(BackRefOfficeGateService.class);
		return service.getOfficeByCode(code);
	}

	/**
	 * ���������� ����� ������ �������
	 * @param custInfo ��������� ���� � ������� �� �������
	 * @return ������
	 */
	protected ClientImpl fillClient(CustInfo_Type custInfo)
	{
		ClientImpl client = new ClientImpl();

		PersonInfo_Type personInfo = custInfo.getPersonInfo();
		if (personInfo != null)
		{
			client.setGender(getGender(personInfo.getGender()));
			client.setBirthDay(parseCalendar(personInfo.getBirthday()));
			client.setBirthPlace(personInfo.getBirthPlace());
			client.setCitizenship(personInfo.getCitizenship());
			client.setINN(personInfo.getTaxId());

			PersonName_Type personName = personInfo.getPersonName();
			if (personName != null)
			{
				client.setSurName(personName.getLastName());
				client.setFirstName(personName.getFirstName());
				client.setPatrName(personName.getMiddleName());
				client.setFullName(getPersonFullName(personName));
			}

			ClientDocument clientDocument = fillDocument(personInfo.getIdentityCard());
			if (clientDocument != null)
			{
				List<ClientDocument> clientDocuments = new ArrayList<ClientDocument>(1);
				clientDocuments.add(clientDocument);
				client.setDocuments(clientDocuments);
			}
		}

		if (StringHelper.isNotEmpty(custInfo.getCustId()))
		{
			client.setId(custInfo.getCustId());
		}	
		ContactInfo_Type contactInfo = custInfo.getPersonInfo().getContactInfo();
		client.setEmail((contactInfo == null) ? null : contactInfo.getEmailAddr());

		return client;
	}

	/**
	 * ���������� ��� �������. � ������, ���� ������ ��� 2 - �������, ��� ��� �������. � ��������� ������� (0, 1, null) - �������
	 * @param code - ���
	 * @return ��� ������� (F - �������, M - �������)
	 */
	private String getGender(String code)
	{
		return StringHelper.equalsNullIgnore(code, "Female") ? "F" : "M";
	}

	/**
	 * ����� �������������� �������� �� ������
	 * @param number �����
	 * @param ids ��������������
	 * @return ������� �������������
	 */
	protected String findId(String number, String... ids)
	{
		for (String id : ids)
		{
			if (number.equals(EntityIdHelper.getCommonCompositeId(id).getEntityId()))
				return id;
		}

		throw new RuntimeException("���������� ��������� �� �� ���� �������");
	}

	protected static String getCorrectTB(String tb)
	{
		Map<String, String> tbReplacements = ConfigFactory.getConfig(BusinessSettingsConfig.class).getTBReplacementsMap();

		if (tbReplacements.keySet().contains(tb))
			return tbReplacements.get(tb);

		return tb;
	}
}
