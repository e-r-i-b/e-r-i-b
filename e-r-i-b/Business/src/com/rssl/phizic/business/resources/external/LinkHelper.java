package com.rssl.phizic.business.resources.external;

import com.rssl.phizic.util.ApplicationUtil;

/**
 * ������ ��� ������ � ���������� ������ ������: �����, ������, ������� ...
 *
 * @author saharnova
 * @ created 16.03.15
 * @ $Author$
 * @ $Revision$
 */
public class LinkHelper
{
	/**
	 * ��������� ��������� ����� � ������� ������
	 * @param link - ����(�����, ����, ������� ...)
	 * @return true/false
	 */
	public static boolean isVisibleInChannel(ShowInMobileProductLink link)
	{
		if(link == null)
			return false;
		if(ApplicationUtil.isATMApi())
			return link.getShowInATM();
		if(ApplicationUtil.isMobileApi())
			return link.getShowInMobile();
		if(ApplicationUtil.isSocialApi())
			return link.getShowInSocial();
		//��� ��������� ����� ��������� showInSystem.
		if(!ApplicationUtil.isAdminApplication())
			return link.getShowInSystem();
		return true;
	}
}
