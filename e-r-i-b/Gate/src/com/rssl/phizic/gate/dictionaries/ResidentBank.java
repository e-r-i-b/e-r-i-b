package com.rssl.phizic.gate.dictionaries;

import java.io.Serializable;
import java.util.Calendar;

/**
 * ���������� ������ ����������
 *
 * Created by IntelliJ IDEA.
 * User: Roshka
 * Date: 24.10.2005
 * Time: 20:59:00
 */
public class ResidentBank extends Bank implements Serializable
{
	//���
    private String BIC;
	//������� ������������
	private String shortName;
	//�������� �� ���� "�����"
	private Boolean our;
	//����� �����
	private String address;
	//��� ���� ��������� �������� � ���� ��
	private String participantCode;
	//���
	private String INN;
	//���
	private String KPP;
	//����������� ����
	private Calendar dateCh;

	public ResidentBank()
	{
		this.BIC = "";
		this.shortName = "";
	}

	/**
	 * @param name ��������
	 * @param bic  ���
	 * @param account ���. ����
	 */
	public ResidentBank(String name, String bic, String account)
	{
		setName(name);
		setBIC(bic);
		setAccount(account);
	}

	public String getBIC()
    {
        return BIC;
    }

    public void setBIC(String BIC)
    {
        this.BIC = BIC;
    }

	public String getShortName()
    {
        return shortName;
    }

    public void setShortName(String shortName)
    {
        this.shortName = shortName;
    }

	public Boolean isOur()
	{
		return our;
	}

	public void setOur(Boolean our)
	{
		this.our = our;
	}

	//��� ���� web-��������
	public Boolean getOur()
	{
		return our;
	}

	public String getAddress()
	{
		return address;
	}

	public void setAddress(String address)
	{
		this.address = address;
	}

	public String getParticipantCode()
	{
		return participantCode;
	}

	public void setParticipantCode(String participantCode)
	{
		this.participantCode = participantCode;
	}

	public String getINN()
	{
		return INN;
	}

	public void setINN(String INN)
	{
		this.INN = INN;
	}

	public String getKPP()
	{
		return KPP;
	}

	public void setKPP(String KPP)
	{
		this.KPP = KPP;
	}

	public Calendar getDateCh()
	{
		return dateCh;
	}

	public void setDateCh(Calendar dateCh)
	{
		this.dateCh = dateCh;
	}
}
