package com.rssl.phizicgate.esberibgate.messaging;

import com.rssl.phizgate.common.services.bankroll.ExtendedCodeGateImpl;
import com.rssl.phizic.common.types.exceptions.LogicException;
import com.rssl.phizic.common.types.exceptions.SystemException;
import com.rssl.phizic.common.types.transmiters.Pair;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.gate.GateFactory;
import com.rssl.phizic.gate.bankroll.Account;
import com.rssl.phizic.gate.bankroll.BackRefBankrollService;
import com.rssl.phizic.gate.bankroll.BankrollService;
import com.rssl.phizic.gate.bankroll.Card;
import com.rssl.phizic.gate.clients.BackRefClientService;
import com.rssl.phizic.gate.clients.Client;
import com.rssl.phizic.gate.commission.BackRefCommissionTBSettingService;
import com.rssl.phizic.gate.dictionaries.officies.Office;
import com.rssl.phizic.gate.documents.GateDocument;
import com.rssl.phizic.gate.exceptions.GateException;
import com.rssl.phizic.gate.exceptions.GateLogicException;
import com.rssl.phizic.gate.ima.IMAccount;
import com.rssl.phizic.gate.ima.IMAccountService;
import com.rssl.phizic.gate.utils.EntityCompositeId;
import com.rssl.phizic.gate.utils.ExternalSystemHelper;
import com.rssl.phizic.utils.DocumentConfig;
import com.rssl.phizic.utils.GroupResultHelper;
import com.rssl.phizic.utils.StringHelper;
import org.apache.commons.lang.ArrayUtils;

import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * @author akrenev
 * @ created 02.06.2015
 * @ $Author$
 * @ $Revision$
 *
 * ������ ������ � ���������� ���� � �����
 */

public final class BackRefInfoRequestHelper
{
	private static final String SRB_SYSTEM_ID = "urn:sbrfsystems:40-cod";
	private static final String SRB_TB_CODE = "40";

	private static final String GROUP_PREFIX = "com.rssl.phizic.gate.TB.group";
	private static final String SPLITER = ",";
	private static final String SEPARATOR = "\\|";

	private static Properties groups;                                               //������ ���������

	static
	{
		groups = ConfigFactory.getReaderByFileName("gate.properties").getProperties(GROUP_PREFIX);
	}

	private final GateFactory factory;

	/**
	 * �����������
	 * @param factory ������� �����
	 */
	public BackRefInfoRequestHelper(GateFactory factory)
	{
		this.factory = factory;
	}

	private GateFactory getFactory()
	{
		return factory;
	}

	/**
	 * �������������� �� ����������� ��� ������� �������.
	 * @param document - ������.
	 * @return true/false - �������������/�� �������������
	 * @throws GateException
	 */
	public boolean isCalcCommissionSupport(GateDocument document) throws GateException
	{
		return getFactory().service(BackRefCommissionTBSettingService.class).isCalcCommissionSupport(document);
	}

	/**
	 * �������� ��������� ��������� �� ������ ������� � �������(����� BackRefClientService)
	 * @param document ��������
	 * @return ��������
	 * @throws GateLogicException
	 * @throws GateException
	 */
	public Client getBusinessOwner(GateDocument document) throws GateLogicException, GateException
	{
		return getFactory().service(BackRefClientService.class).getClientById(document.getInternalOwnerId());
	}

	/**
	 * ��������� RbTbBrch ����� loginId �������
	 * @param loginId �������
	 * @return ������� ��� ������ ��� ������ ���������������� ����� � ������� RbTbBrch � ��� ��-�������� ��������� �������� ��� ��.
	 */
	public String getRbTbBrch(Long loginId) throws GateLogicException, GateException
	{
		BackRefClientService service = getFactory().service(BackRefClientService.class);
		String departmentCode = service.getClientDepartmentCode(loginId);
		if (departmentCode == null)
			return null;
		String[] codeArray = departmentCode.split(SEPARATOR);
		return ArrayUtils.isEmpty(codeArray) ? null : codeArray[0];
	}

	/**
	 * ��������� RbTbBrch ����� ��������
	 * @param document ��������
	 * @return ������� ��� ������ ��� ������ ���������������� ����� � ������� RbTbBrch � ��� ��-�������� ��������� �������� ��� ��.
	 */
	public String getRbTbBrch(GateDocument document) throws GateLogicException, GateException
	{
		return getRbTbBrch(document.getInternalOwnerId());
	}

	/**
	 * �������� ������������� �����
	 * @param owner ��������
	 * @param cardNumber ����� �����
	 * @return ������������� �����
	 * @throws GateException
	 * @throws GateLogicException
	 */
	public String getCardExternalId(Client owner, String cardNumber) throws GateException, GateLogicException
	{
		return getFactory().service(BackRefBankrollService.class).findCardExternalId(owner.getInternalOwnerId(), cardNumber);
	}

	/**
	 * �������� ����� �� ������
	 * @param client �������� ��� ��������� �������
	 * @param number ����� �����
	 * @param office ����
	 * @return �����
	 * @throws GateLogicException
	 * @throws GateException
	 */
	public Card getCard(Client client, String number, Office office) throws GateException, GateLogicException
	{
		try
		{
			BankrollService bankrollService = getFactory().service(BankrollService.class);
			//noinspection unchecked
			return GroupResultHelper.getOneResult(bankrollService.getCardByNumber(client, new Pair<String, Office>(number, office)));
		}
		catch (SystemException e)
		{
			throw new GateException(e);
		}
		catch (LogicException e)
		{
			throw new GateLogicException(e.getMessage(), e);
		}
	}

	/**
	 * �������� ���� �� ������
	 * @param number ����� �����
	 * @param office ����
	 * @return ����
	 * @throws GateLogicException
	 * @throws GateException
	 */
	public Account getAccount(String number, Office office) throws GateException, GateLogicException
	{
		try
		{
			BankrollService bankrollService = getFactory().service(BankrollService.class);
			//noinspection unchecked
			return GroupResultHelper.getOneResult(bankrollService.getAccountByNumber(new Pair<String, Office>(number, office)));
		}
		catch (SystemException e)
		{
			throw new GateException(e);
		}
		catch (LogicException e)
		{
			throw new GateLogicException(e.getMessage(), e);
		}
	}

	/**
	 * �������� ��� �� ������
	 * @param client �������� ��� ��������� �������
	 * @param number ����� �����
	 * @return ����
	 */
	public IMAccount getIMAccount(Client client, String number) throws GateException, GateLogicException
	{
		IMAccountService imAccountService = getFactory().service(IMAccountService.class);

		try
		{
			return GroupResultHelper.getOneResult(imAccountService.getIMAccountByNumber(client, number));
		}
		catch (SystemException e)
		{
			throw new GateException(e);
		}
		catch (LogicException e)
		{
			throw new GateLogicException(e.getMessage(), e);
		}
	}

	private List<String> getSrbTBCodes()
	{
		return ConfigFactory.getConfig(DocumentConfig.class).getAllTbCodesSrb();
	}

	/**
	 * ����� �� ������������ �������� ��� ���
	 * @param compositeId ����������� ����
	 * @param client ������
	 * @return ����� �� ������������ �������� ��� ���
	 */
	public boolean isUseSRBValues(EntityCompositeId compositeId, Client client)
	{
		String tb = client.getOffice().getCode().getFields().get("region");
		String rbBrchId = compositeId.getRbBrchId();
		String systemId = compositeId.getSystemId();
		return StringHelper.isEmpty(systemId) && StringHelper.isEmpty(rbBrchId) && getSrbTBCodes().contains(tb);
	}

	/**
	 * @return ��� ������� ������� ��� ���
	 * @throws GateException
	 */
	public String getSRBExternalSystemCode() throws GateException
	{
		return ExternalSystemHelper.getCode(SRB_SYSTEM_ID);
	}

	/**
	 * �������� RbBrchId ��� ���
	 * @param office ����
	 * @return RbBrchId ��� ���
	 * @throws GateLogicException
	 * @throws GateException
	 */
	public String getSRBRbBrchId(Office office) throws GateLogicException, GateException
	{
		//������� ������������:
		//� �������� ������ ��������� ��� ��� ������ ���������� 40.
		//� �������� ����� ��� ���������� ��������, ��������� �� ���������� ���������:
		//1. �� �� ���� ��� ������������� ���������� �� �����, � ��� ����� ���������� � ������������� ������� ����.
		//2. �� ���� ���������� ���������� ����� � ����������� ���, ������� �������� � ������� ����.
		//3. �� ���������� ������������� ������� ����� ��� � ���������� ������� �� 4 ������.
		// �������� ���� � ����� RbBrchId ����������� �� ����� 40 � ����������� ������ ���.
		// ��������, ���� ����� ��� 1234, �� �������� ���� ����� 401234.
		ExtendedCodeGateImpl code = new ExtendedCodeGateImpl(office.getCode());
		String branch = code.getBranch();
		if (branch.length() > 4)
		{
			branch = branch.substring(branch.length() - 4);//��������
		}
		branch = StringHelper.appendLeadingZeros(branch, 4);
		return SRB_TB_CODE + branch;
	}

	/**
	 * ��������� ������������ �� ���� ��������� �������� � ���� ���������� 1 ��.
	 * @param office ���� ��������� ��������
	 * @param receiverAccount ���� ����������. �����: ������������ ������ ������ ���������� �����
	 * �� ����� ������� ����������� ��������� �������� �� ���� ����������� ���������� �����
	 * @return true - �����������
	 */
	public static boolean isSameTB(Office office, String receiverAccount) throws GateException
	{
		String payerTB = office.getCode().getFields().get("region");
		if (payerTB.length() == 1)
			payerTB = StringHelper.appendLeadingZeros(payerTB, 2);

		String receiverTB = receiverAccount.substring(9, 11);
		if (receiverTB.equals(payerTB))
			return true;

/*
		TODO �������� �� ��������������� �������� ���������, �� ������������ ���������
		//���� ������������� ������ - ������ �� � ����� ��
		if (!StringHelper.isEmpty(assignee.get(receiverTB)))
			return true;
*/

		for (Map.Entry entry : groups.entrySet())
		{
			StringBuilder group = new StringBuilder();
			//��������� ���������� ���� �� ������, ���� �� ������
			for (String tb : ((String) entry.getValue()).split(SPLITER))
			{
				if (tb.length() == 1)
					tb = StringHelper.appendLeadingZeros(tb, 2);
				group.append(tb).append(SPLITER);
			}

			//���� ��� �� �� ����� ������, �� ������ � ����� ��
			if (group.indexOf(payerTB) > -1 && group.indexOf(receiverTB) > -1)
				return true;
		}

		return false;
	}
}