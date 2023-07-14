package com.rssl.auth.csa.front.unallowedbrowsers;

import com.rssl.auth.csa.front.exceptions.FrontException;
import com.rssl.auth.csa.front.exceptions.FrontLogicException;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.mobileDevices.MobilePlatform;
import com.rssl.phizic.business.mobileDevices.MobilePlatformService;
import com.rssl.phizic.utils.StringHelper;

import java.util.List;
import java.util.regex.Pattern;
import javax.servlet.http.HttpServletRequest;

/**
 * @ author: Vagin
 * @ created: 19.12.2012
 * @ $Author
 * @ $Revision
 * �������� ��������� ������ �� ���������� ������������ ��� ���������� ����������.
 */
public class ListDownloadLinksOperation
{
	private static final MobilePlatformService mobilePlatformService = new MobilePlatformService();
	/**
	 * ������������� ������ ��� ���������� ���������� ��� ��������� � User-Agent
	 * ���������� ����������.
	 * @param request HttpServletRequest
	 * @return ������ ��� ���������� ����������.
	 */
	public String getLink(HttpServletRequest request) throws FrontLogicException, FrontException, BusinessException
	{
		String userAgent = request.getHeader("User-Agent");
		List<MobilePlatform> mobilePlatforms = mobilePlatformService.getMobile();
		for (MobilePlatform mobilePlatform : mobilePlatforms)
		{
			String unallowedBrowsers = mobilePlatform.getUnallowedBrowsers();
			if(StringHelper.isEmpty(unallowedBrowsers))
				continue;
			Pattern pattern = Pattern.compile(unallowedBrowsers);
			if (pattern.matcher(userAgent).matches())
			{
				return mobilePlatform.getBankURL();
			}
		}
		throw new FrontException("��� ������� ���������� ��� ������������ ����������. User-Agent:" + userAgent);
	}
}
