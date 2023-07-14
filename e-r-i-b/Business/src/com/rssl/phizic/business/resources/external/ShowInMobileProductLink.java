package com.rssl.phizic.business.resources.external;

/**
 * ������� ����� ��� ������ �� ��������, ������� ������ ������������ � ��������� ����������
 * @author Pankin
 * @ created 26.08.2012
 * @ $Author$
 * @ $Revision$
 */

public abstract class ShowInMobileProductLink extends EditableExternalResourceLink
{
	private Boolean isShowInMobile;
    private Boolean isShowInSocial;

	/**
		 * ���������� ������� ����������� ������ � ��������� ������
		 * @return true - ������������
	 */
	public Boolean getShowInMobile()
	{
		return isShowInMobile;
	}

	/**
	 * ���������� ������� ����������� ������ � ��������� ������
	 * @param showInMobile - true -  ����������
	 */
	public void setShowInMobile(Boolean showInMobile)
	{
		isShowInMobile = showInMobile;
	}

    public Boolean getShowInSocial()
    {
        return isShowInSocial;
    }

    public void setShowInSocial(Boolean showInSocial)
    {
        isShowInSocial = showInSocial;
    }
}
