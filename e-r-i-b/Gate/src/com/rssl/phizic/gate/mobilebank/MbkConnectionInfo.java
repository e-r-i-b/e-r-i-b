package com.rssl.phizic.gate.mobilebank;

import com.rssl.phizic.utils.PhoneNumber;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

import java.util.List;

/**
 * User: Moshenko
 * Date: 29.08.13
 * Time: 9:15
 * ������ ������������ BeginMigrationJDBCAction � ����������� ��� ������� ���� � ��� ���-��������.
 */
public class MbkConnectionInfo
{
    //�� ����������� ���.
	private int LinkID;
    // ����� �������� ����������� � 11-������� �������. ����� ������ ���������� �� 7. ��������: 79261112233
    private PhoneNumber phoneNumber;
    //����� �������� ����� �����������
	private String pymentCard;
    //���������� �� �������������� ������
	private List<InfoCardImpl> infoCards;
    //1 � ������ �����; 0 � ��������� �����
	private int linkTariff;
    //1 � ����������� ������������� �� ��������; 0-����������� �� �������������  ���������� ��������� ��������������. ������������ ����� �������� ��� COMMIT ��������.
	private int linkPaymentBlockID;
    //���������� ��������   1- ������������ 0 � �� ������������
    private int phoneBlockCode;
    //������� ������� ��� �������� �����������   1-���������� 0- ���������
    private int phoneQuickServices;
    //������ ������������ ��� �������
	private List<String> phoneOffers;
    //������� ��������
	private List<MobileBankTemplate> templates;

	public int getLinkID()
	{
		return LinkID;
	}

	public void setLinkID(int linkID)
	{
		LinkID = linkID;
	}

	public PhoneNumber getPhoneNumber()
	{
		return phoneNumber;
	}

	public void setPhoneNumber(PhoneNumber phoneNumber)
	{
		this.phoneNumber = phoneNumber;
	}

	public String getPymentCard()
	{
		return pymentCard;
	}

	public void setPymentCard(String pymentCard)
	{
		this.pymentCard = pymentCard;
	}

    public List<InfoCardImpl> getInfoCards() {
        return infoCards;
    }

    public void setInfoCards(List<InfoCardImpl> infoCards) {
        this.infoCards = infoCards;
    }

    public int getLinkTariff()
	{
		return linkTariff;
	}

	public void setLinkTariff(int linkTariff)
	{
		this.linkTariff = linkTariff;
	}

	public int getLinkPaymentBlockID()
	{
		return linkPaymentBlockID;
	}

	public void setLinkPaymentBlockID(int linkPaymentBlockID)
	{
		this.linkPaymentBlockID = linkPaymentBlockID;
	}

	public int getPhoneBlockCode()
	{
		return phoneBlockCode;
	}

	public void setPhoneBlockCode(int phoneBlockCode)
	{
		this.phoneBlockCode = phoneBlockCode;
	}

	public int getPhoneQuickServices()
	{
		return phoneQuickServices;
	}

	public void setPhoneQuickServices(int phoneQuickServices)
	{
		this.phoneQuickServices = phoneQuickServices;
	}

	public List<String>  getPhoneOffers()
	{
		return phoneOffers;
	}

	public void setPhoneOffers(List<String>  phoneOffers)
	{
		this.phoneOffers = phoneOffers;
	}

	public List<MobileBankTemplate> getTemplates()
	{
		return templates;
	}

	public void setTemplates(List<MobileBankTemplate> templates)
	{
		this.templates = templates;
	}

	@Override
	public String toString()
	{
		return new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE)
				.append("LinkID", LinkID)
				.append("phoneNumber", phoneNumber)
				.append("pymentCard", pymentCard)
				.append("infoCards", infoCards)
				.append("linkTariff", linkTariff)
				.append("linkPaymentBlockID", linkPaymentBlockID)
				.append("phoneBlockCode", phoneBlockCode)
				.append("phoneQuickServices", phoneQuickServices)
				.append("phoneOffers", phoneOffers)
				.append("templates", templates)
				.toString();
	}
}
