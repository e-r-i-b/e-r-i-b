package com.rssl.phizic.business.profileSynchronization.products;

/**
 * @author lepihina
 * @ created 28.05.14
 * $Author$
 * $Revision$
 *
 * ��������� ����������� � ��������� ��������� (��� �������� � ���������������� ���������)
 */
public class ResourceInfo
{
	private String number;
	private String name;
	private Boolean isShowInSystem;
	private Boolean isShowInMobile;
	private Boolean isShowInSocial;
	private Boolean isShowInATM;
	private Boolean isShowInSms;
	private Boolean isShowInMain;
	private Integer positionNumber;

	/**
	 * @return ������������� ��������(��������, ����� �����, ����� � ��.)
	 */
	public String getNumber()
	{
		return number;
	}

	/**
	 * @param number - ������������� ��������(��������, ����� �����, ����� � ��.)
	 */
	public void setNumber(String number)
	{
		this.number = number;
	}

	/**
	 * @return ����� ��������
	 */
	public String getName()
	{
		return name;
	}

	/**
	 * @param name - ����� ��������
	 */
	public void setName(String name)
	{
		this.name = name;
	}

	/**
	 * @return ������� ��������� ������� � �������
	 */
	public Boolean getShowInSystem()
	{
		return isShowInSystem;
	}

	/**
	 * @param showInSystem - ������� ��������� ������� � �������
	 */
	public void setShowInSystem(Boolean showInSystem)
	{
		isShowInSystem = showInSystem;
	}

	/**
	 * @return ������� ��������� ������� � ��������� ����������
	 */
	public Boolean getShowInMobile()
	{
		return isShowInMobile;
	}

	/**
	 * @param showInMobile - ������� ��������� ������� � ��������� ����������
	 */
	public void setShowInMobile(Boolean showInMobile)
	{
		isShowInMobile = showInMobile;
	}

    /**
     * @return ������� ��������� ������� � ���. ����������
     */
    public Boolean getShowInSocial()
    {
        return isShowInSocial;
    }

    /**
     * @param showInSocial - ������� ��������� ������� � ���. ����������
     */
    public void setShowInSocial(Boolean showInSocial)
    {
        isShowInSocial = showInSocial;
	}

	/**
	 * @return ������� ��������� ������� � ����������� ����������������
	 */
	public Boolean getShowInATM()
	{
		return isShowInATM;
	}

	/**
	 * @param showInATM - ������� ��������� ������� � ����������� ����������������
	 */
	public void setShowInATM(Boolean showInATM)
	{
		isShowInATM = showInATM;
	}

	/**
	 * @return ������� ��������� ������� � ��� ������
	 */
	public Boolean getShowInSms()
	{
		return isShowInSms;
	}

	/**
	 * @param showInSms - ������� ��������� ������� � ��� ������
	 */
	public void setShowInSms(Boolean showInSms)
	{
		isShowInSms = showInSms;
	}

	/**
	 * @return ������� ����������� ������� �� ������� ��������
	 */
	public Boolean getShowInMain()
	{
		return isShowInMain;
	}

	/**
	 * @param showInMain - ������� ����������� ������� �� ������� ��������
	 */
	public void setShowInMain(Boolean showInMain)
	{
		isShowInMain = showInMain;
	}

	/**
	 * @return ���������� ����� �������� � ������
	 */
	public Integer getPositionNumber()
	{
		return positionNumber;
	}

	/**
	 * @param positionNumber - ���������� ����� �������� � ������
	 */
	public void setPositionNumber(Integer positionNumber)
	{
		this.positionNumber = positionNumber;
	}
}
