package com.rssl.phizic.gate.ermb;

import com.rssl.phizic.common.types.annotation.PlainOldJavaObject;
import com.rssl.phizic.gate.csa.NodeInfo;
import com.rssl.phizic.gate.csa.UserInfo;
import com.rssl.phizic.gate.dictionaries.officies.Code;
import com.rssl.phizic.gate.mobilebank.MBKRegistrationResultCode;
import com.rssl.phizic.utils.MaskUtil;
import com.rssl.phizic.utils.PhoneNumber;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import java.io.Serializable;
import java.util.List;

/**
 * @author Nady
 * @ created 02.07.14
 * @ $Author$
 * @ $Revision$
 */

/**
 * ������ (������ ���->����) �� ��������� ���������� ������ "��������� ����" � ������� ����
 * ������ ���������� ����������� � ��� � ����� (�������� �� ����)
 * ������ �������, ���� ���� wellParsed = true
 */
@PlainOldJavaObject
@SuppressWarnings("PublicField")
public class MBKRegistration implements Serializable
{
	/**
	 * ID ������
	 * �������� ������
	 * ��������� �� ���
	 */
	private long id;

	/**
	 * ��� ������
	 * �������� ������, ���� ������ �������
	 * ��������� �� ���
	 */
	private RegAction regAction;

	/**
	 * ���� ���������� ������
	 * �������� ������, ���� ������ �������
	 * ��������� �� ���
	 */
	private RegTranType regTranType;

	/**
	 * �������, �� ������� ������ ���������� ��� ���������� �������� � ���� � �������� ����������.
	 * �������� ������, ���� ������ �������
	 * ��������� �� ���
	 */
	private FiltrationReason filtrationReason;

	/**
	 * �������, ������� �������������� � �����������
	 * �������� ������, ���� ������ �������
	 * ��������� �� ���
	 */
	private PhoneNumber phoneNumber;

	/**
	 * ������������� ����������� - ���������� �������
	 * �������� ������, ���� ������ ������� � regTranType = MOPS
	 * ��������� �� ���
	 */
	private String pp;

	/**
	 * ������ ��������������� �������� ��� ��
	 * ����� ���� ��������, ���� ������ ������� � regTranType = MOPS
	 * ��������� �� ���
	 */
	private List<String> ipList;

	/**
	 * ������ �������� (��������������) ����
	 * ����� ���� ��������, ���� ������ ������� � regTranType = MOBI
	 * ��������� �� ���
	 */
	private List<String> activeCards;

	/**
	 * ���������, ������� ������������ �����������
	 * �������� ������, ���� ������ �������
	 * ��������� �� ���
	 */
	private Code officeCode;

	/**
	 * �����
	 * ��� ������ (0-��� �����������, 1 - � �������������)
	 * �������� ������, ���� ������ ������� � regTranType = MOBI
	 * ��������� �� ���
	 */
	private MbkTariff tariff;

	/**
	 * ����� �������� �����
	 * �������� ������, ���� ������ �������
	 * ��������� �� ���
	 */
	private String paymentCardNumber;

	/**
	 * ������ �������
	 * ����� ��� ����������� ����-����� � ��� ��������� ������ � �����
	 * ����������� �� �������� ����� ������
	 */
	private UserInfo owner;

	/**
	 * ���� ����, � ������� �������������� ������
	 * �� ������, ���� ������:
	 * ��� �� �������
	 * ��� ���� ���������� �����
	 * ��� ���� �� ������
	 * ����������� �� owner
	 *
	 * �����! ���� �� ��������� � ���� ����
	 */
	private transient NodeInfo node;

	/**
	 * ������ "������ ������� ��������� �� �������-����" (�.�. �������)
	 */
	private boolean wellParsed;

	/**
	 * ������ "��� ������ ���������� ��������� ������"
	 * �� ������, ���� ���������� �� ������������ � ���
	 */
	private Boolean returnedToMBK;

	/**
	 * ������ "������ �������� � ���� ����"
	 * �� ������, ���� ������ �� ������������ � ����
	 */
	private Boolean passedToNode;

	/**
	 * ��� ���������� ���������
	 * �� ������, ���� ������ �� ����������
	 */
	private MBKRegistrationResultCode resultCode;

	/**
	 * �������� ������
	 * �� ������, ���� ������ �� ���������� ��� ������ ���������� ��� ������
	 */
	private String errorDescr;

	///////////////////////////////////////////////////////////////////////////

	public long getId()
	{
		return id;
	}

	public void setId(long id)
	{
		this.id = id;
	}

	public RegAction getRegAction()
	{
		return regAction;
	}

	public void setRegAction(RegAction regAction)
	{
		this.regAction = regAction;
	}

	public void setRegAction(String regAction)
	{
		if(regAction == null || regAction.trim().length() == 0)
			return;
		this.regAction = Enum.valueOf(RegAction.class, regAction);
	}

	public RegTranType getRegTranType()
	{
		return regTranType;
	}

	public void setRegTranType(RegTranType regTranType)
	{
		this.regTranType = regTranType;
	}

	public void setRegTranType(String regTranType)
	{
		if(regTranType == null || regTranType.trim().length() == 0)
			return;
		this.regTranType = Enum.valueOf(RegTranType.class, regTranType);
	}

	public FiltrationReason getFiltrationReason()
	{
		return filtrationReason;
	}

	public void setFiltrationReason(FiltrationReason filtrationReason)
	{
		this.filtrationReason = filtrationReason;
	}

	public void setFiltrationReason(String filtrationReason)
	{
		if(filtrationReason == null || filtrationReason.trim().length() == 0)
			return;
		this.filtrationReason = Enum.valueOf(FiltrationReason.class, filtrationReason);
	}

	public PhoneNumber getPhoneNumber()
	{
		return phoneNumber;
	}

	public void setPhoneNumber(PhoneNumber phoneNumber)
	{
		this.phoneNumber = phoneNumber;
	}

	public String getPp()
	{
		return pp;
	}

	public void setPp(String pp)
	{
		this.pp = pp;
	}

	public List<String> getIpList()
	{
		return ipList;
	}

	public void setIpList(List<String> ipList)
	{
		this.ipList = ipList;
	}

	public List<String> getActiveCards()
	{
		return activeCards;
	}

	public void setActiveCards(List<String> activeCards)
	{
		this.activeCards = activeCards;
	}

	public Code getOfficeCode()
	{
		return officeCode;
	}

	public void setOfficeCode(Code officeCode)
	{
		this.officeCode = officeCode;
	}

	public MbkTariff getTariff()
	{
		return tariff;
	}

	public void setTariff(MbkTariff tariff)
	{
		this.tariff = tariff;
	}

	public void setTariff(String tariff)
	{
		if(tariff == null || tariff.trim().length() == 0)
			return;
		this.tariff = Enum.valueOf(MbkTariff.class, tariff);
	}

	public String getPaymentCardNumber()
	{
		return paymentCardNumber;
	}

	public void setPaymentCardNumber(String paymentCardNumber)
	{
		this.paymentCardNumber = paymentCardNumber;
	}

	public UserInfo getOwner()
	{
		return owner;
	}

	public void setOwner(UserInfo owner)
	{
		this.owner = owner;
	}

	public NodeInfo getNode()
	{
		return node;
	}

	public void setNode(NodeInfo node)
	{
		this.node = node;
	}

	public boolean isWellParsed()
	{
		return wellParsed;
	}

	public void setWellParsed(boolean wellParsed)
	{
		this.wellParsed = wellParsed;
	}

	public Boolean getReturnedToMBK()
	{
		return returnedToMBK;
	}

	public void setReturnedToMBK(Boolean returnedToMBK)
	{
		this.returnedToMBK = returnedToMBK;
	}

	public Boolean getPassedToNode()
	{
		return passedToNode;
	}

	public void setPassedToNode(Boolean passedToNode)
	{
		this.passedToNode = passedToNode;
	}

	public MBKRegistrationResultCode getResultCode()
	{
		return resultCode;
	}

	public void setResultCode(MBKRegistrationResultCode resultCode)
	{
		this.resultCode = resultCode;
	}

	public void setResultCode(String resultCode)
	{
		if(resultCode == null || resultCode.trim().length() == 0)
			return;
		this.resultCode = Enum.valueOf(MBKRegistrationResultCode.class, resultCode);
	}

	public String getErrorDescr()
	{
		return errorDescr;
	}

	public void setErrorDescr(String errorDescr)
	{
		this.errorDescr = errorDescr;
	}

	@Override
	public String toString()
	{
		return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
				.append("id",                   id)
				.append("regAction",            regAction)
				.append("regTranType",          regTranType)
				.append("filtrationReason",     filtrationReason)
				.append("phoneNumber",          (phoneNumber != null) ? phoneNumber.hideAbonent() : null)
				.append("paymentCardNumber",    MaskUtil.getCutCardNumber(paymentCardNumber))
				.append("officeCode",           officeCode)
				.append("tariff",               tariff)
				.append("pp",                   pp)
				.append("ipList",               ipList)
				.append("activeCards",          (activeCards != null) ? MaskUtil.getCutCardNumber(activeCards) : null)
				.append("owner",                owner)
				.append("node",                 (node != null) ? node.getName() : null)
				.append("wellParsed",           wellParsed)
				.append("returnedToMBK",        returnedToMBK)
				.append("passedToNode",         passedToNode)
				.append("resultCode",           resultCode)
				.append("errorDescr",           errorDescr)
				.toString();
	}
}
