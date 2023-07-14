package com.rssl.phizic.business.ermb;

import com.rssl.phizic.common.types.Money;
import com.rssl.phizic.utils.StringHelper;

/**
 * User: Moshenko
 * Date: 02.10.12
 * Time: 19:04
 * �������� ����� ����
 */
public class ErmbTariff
{
	private Long id;
	private String name;
	private Money connectionCost;
	private int chargePeriod = 1;
	private int gracePeriod = 0;

	private Money gracePeriodCost;
	private Money graceClass;
	private Money premiumClass;
	private Money socialClass;
	private Money standardClass;

	private ErmbOperationStatus noticeConsIncomCardOperation;
	private ErmbOperationStatus noticeConsIncomAccountOperation;
	private ErmbOperationStatus cardInfoOperation;
	private ErmbOperationStatus accountInfoOperation;
	private ErmbOperationStatus cardMiniInfoOperation;
	private ErmbOperationStatus accountMiniInfoOperation;
	private ErmbOperationStatus reIssueCardOperation;
	private ErmbOperationStatus jurPaymentOperation;
	private ErmbOperationStatus transfersToThirdPartiesOperation;
	private String code; //��� ���� ������(full, saving)
	private String description;

	private ErmbTariffStatus tariffStatus = ErmbTariffStatus.ACTIVE;

	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}


	/**
	 * @return ������������ ������
	 */
	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	/**
	 * @return ����� �� �����������
	 */
	public Money getConnectionCost()
	{
		return connectionCost;
	}

	public void setConnectionCost(Money connectionCost)
	{
		this.connectionCost = connectionCost;
	}

	/**
	 * @return ������ �������� ����������� �����.
	 * ���������� �������, �� ������� ������� ����������� �����.
	 * �� ��������� - 1
	 */
	public int getChargePeriod()
	{
		return chargePeriod;
	}

	public void setChargePeriod(int chargePeriod)
	{
		this.chargePeriod = chargePeriod;
	}

	/**
	 * @return �������� ������.
	 * ������ ��������� �������. ����������� ���������� ����������� �������.
	 * �� ��������� - 0
	 */
	public int getGracePeriod()
	{
		return gracePeriod;
	}

	public void setGracePeriod(int gracePeriod)
	{
		this.gracePeriod = gracePeriod;
	}

	/**
	 * @return ������ ����� � �������� ������
	 */
	public Money getGracePeriodCost()
	{
		return gracePeriodCost;
	}

	public void setGracePeriodCost(Money gracePeriodCost)
	{
		this.gracePeriodCost = gracePeriodCost;
	}

	/**
	 * @return ����������� �����. ����� ��������. ��������.
	 */
	public Money getGraceClass()
	{
		return graceClass;
	}

	public void setGraceClass(Money graceClass)
	{
		this.graceClass = graceClass;
	}

	/**
	 * @return ����������� �����. ����� ��������. ��������. �������
	 */
	public Money getPremiumClass()
	{
		return premiumClass;
	}

	public void setPremiumClass(Money premiumClass)
	{
		this.premiumClass = premiumClass;
	}

	/**
	 * @return  ����������� �����. ����� ��������. ��������. ����������
	 */
	public Money getSocialClass()
	{
		return socialClass;
	}

	public void setSocialClass(Money socialClass)
	{
		this.socialClass = socialClass;
	}

	/**
	 * @return ����������� �����. ����� ��������. ��������. ��������
	 */
	public Money getStandardClass()
	{
		return standardClass;
	}

	public void setStandardClass(Money standardClass)
	{
		this.standardClass = standardClass;
	}

	/**
	 * @return �������������� �� ������� ������� ���������� �� �����
	 */
	public ErmbOperationStatus getAccountInfoOperation()
	{
		return accountInfoOperation;
	}

	public void setAccountInfoOperation(ErmbOperationStatus accountInfoOperation)
	{
		this.accountInfoOperation = accountInfoOperation;
	}

	/**
	 * @return �������������� �� ������� ������� ������� ������� �� ����� (����-�������)
	 */
	public ErmbOperationStatus getAccountMiniInfoOperation()
	{
		return accountMiniInfoOperation;
	}

	public void setAccountMiniInfoOperation(ErmbOperationStatus accountMiniInfoOperation)
	{
		this.accountMiniInfoOperation = accountMiniInfoOperation;
	}

	/**
	 * @return �������������� �� ������� ������� ���������� �� �����
	 */
	public ErmbOperationStatus getCardInfoOperation()
	{
		return cardInfoOperation;
	}

	public void setCardInfoOperation(ErmbOperationStatus cardInfoOperation)
	{
		this.cardInfoOperation = cardInfoOperation;
	}

	/**
	 * @return �������������� �� ������� ������� ������� ������� �� ����� (����-�������)
	 */
	public ErmbOperationStatus getCardMiniInfoOperation()
	{
		return cardMiniInfoOperation;
	}

	public void setCardMiniInfoOperation(ErmbOperationStatus cardMiniInfoOperation)
	{
		this.cardMiniInfoOperation = cardMiniInfoOperation;
	}

	/**
	 * @return ������� � ������ ����������� (������ �����)
	 */
	public ErmbOperationStatus getJurPaymentOperation()
	{
		return jurPaymentOperation;
	}

	public void setJurPaymentOperation(ErmbOperationStatus jurPaymentOperation)
	{
		this.jurPaymentOperation = jurPaymentOperation;
	}

	/**
	 * @return ����������� � ���������/��������� ��������� �� ����� ������
	 */
	public ErmbOperationStatus getNoticeConsIncomAccountOperation()
	{
		return noticeConsIncomAccountOperation;
	}

	public void setNoticeConsIncomAccountOperation(ErmbOperationStatus noticeConsIncomAccountOperation)
	{
		this.noticeConsIncomAccountOperation = noticeConsIncomAccountOperation;
	}

	/**
	 * @return ����������� �  ���������/��������� ���������, ������������ �� ����� �����
	 */
	public ErmbOperationStatus getNoticeConsIncomCardOperation()
	{
		return noticeConsIncomCardOperation;
	}

	public void setNoticeConsIncomCardOperation(ErmbOperationStatus noticeConsIncomCardOperation)
	{
		this.noticeConsIncomCardOperation = noticeConsIncomCardOperation;
	}

	/**
	 * @return ����������/������ �� ���������� ����� �� ������� �������
	 */
	public ErmbOperationStatus getReIssueCardOperation()
	{
		return reIssueCardOperation;
	}

	public void setReIssueCardOperation(ErmbOperationStatus reIssueCardOperation)
	{
		this.reIssueCardOperation = reIssueCardOperation;
	}

	/**
	 * @return �������� � ������ ������� ��� (�� �������)
	 */
	public ErmbOperationStatus getTransfersToThirdPartiesOperation()
	{
		return transfersToThirdPartiesOperation;
	}

	public void setTransfersToThirdPartiesOperation(ErmbOperationStatus transfersToThirdPartiesOperation)
	{
		this.transfersToThirdPartiesOperation = transfersToThirdPartiesOperation;
	}

	public String getCode()
	{
		return code;
	}

	public void setCode(String code)
	{
		this.code = code;
	}

	/**
	 * @return �������� ������.
	 */
	public String getDescription()
	{
		return description;
	}

	public void setDescription(String description)
	{
		this.description = description;
	}

	/**
	 * @return ������ ������
	 */
	public ErmbTariffStatus getTariffStatus()
	{
		return tariffStatus;
	}

	public void setTariffStatus(ErmbTariffStatus tariffStatus)
	{
		this.tariffStatus = tariffStatus;
	}

	/**
	 * �������� ��������� ����������� ����� � ����������� �� ������ ��������� �������
	 * @param ermbProductClass ����� ��������� �������
	 * @return ����������� �����
	 */
	public Money getCost(ErmbProductClass ermbProductClass)
	{
		switch (ermbProductClass)
		{
			case preferential:
				return getGraceClass();
			case premium:
				return getPremiumClass();
			case social:
				return getSocialClass();
			case standart:
				return getStandardClass();
			default:
				throw new IllegalArgumentException("����������� ����� ��������� �������");
		}
	}

    public String toString ()
    {
        return name;
    }

    public int hashCode ()
    {
        return code.hashCode();
    }

    public boolean equals (Object o)
    {
        if (this == o) return true;
        if (o == null || !(o instanceof ErmbTariff)) return false;

        final ErmbTariff that = (ErmbTariff) o;

        if (!StringHelper.equalsNullIgnore(name, that.name))
            return false;

        return true;
    }
}
