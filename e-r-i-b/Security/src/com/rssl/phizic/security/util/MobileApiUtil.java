package com.rssl.phizic.security.util;

import com.rssl.phizic.auth.AuthModule;
import com.rssl.phizic.auth.UserPrincipal;
import com.rssl.phizic.auth.modes.AuthenticationContext;
import com.rssl.phizic.common.types.MobileAppScheme;
import com.rssl.phizic.common.types.VersionNumber;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;
import com.rssl.phizic.logging.system.LogModule;
import com.rssl.phizic.util.ApplicationUtil;

import java.security.AccessControlException;

/**
 * ��������� ����� ��� mAPI.
 * @author Dorzhinov
 * @ created 14.03.2013
 * @ $Author$
 * @ $Revision$
 */
public class MobileApiUtil
{
	private static final Log LOG = PhizICLogFactory.getLog(LogModule.Core);
	
	/**
	 * ���������� ����� ������ API ����������� ����������.
	 * ������ ������� �� ��������� ��������������, ���� ����������� �� ��������� ������� version.
	 * @return
	 */
	public static VersionNumber getApiVersionNumber()
	{
		AuthenticationContext authContext = AuthenticationContext.getContext();
		return (authContext != null) ? authContext.getClientMobileAPIVersion() : null;
	}

	/**
	 * ���������, ��� ������ API > ���������
	 * ���� ������� ���������� �� mAPI ��� ������ == null, ���������� false
	 * @param comparingVersion ������ ��� ���������
	 * @return true - ���� ��� mAPI � ������ mAPI > ���������
	 */
	public static boolean isMobileApiGT(VersionNumber comparingVersion)
	{
		if (ApplicationUtil.isNotMobileApi())
			return false;
		VersionNumber versionNumber = getApiVersionNumber();
		return versionNumber != null && versionNumber.gt(comparingVersion);
	}

	/**
	 * ���������, ��� ������ API >= ���������
	 * ���� ������� ���������� �� mAPI ��� ������ == null, ���������� false
	 * @param comparingVersion ������ ��� ���������
	 * @return true - ���� ��� mAPI � ������ mAPI >= ���������
	 */
	public static boolean isMobileApiGE(VersionNumber comparingVersion)
	{
		if (ApplicationUtil.isNotMobileApi())
			return false;
		VersionNumber versionNumber = getApiVersionNumber();
		return versionNumber != null && versionNumber.ge(comparingVersion);
	}

	/**
	 * ���������, ��� ������ API < ���������
	 * ���� ������� ���������� �� mAPI ��� ������ == null, ���������� false
	 * @param comparingVersion ������ ��� ���������
	 * @return true - ���� ��� mAPI � ������ mAPI < ���������
	 */
	public static boolean isMobileApiLT(VersionNumber comparingVersion)
	{
		if (ApplicationUtil.isNotMobileApi())
			return false;
		VersionNumber versionNumber = getApiVersionNumber();
		return versionNumber != null && versionNumber.lt(comparingVersion);
	}

	/**
	 * ���������, ��� ������ API <= ���������
	 * ���� ������� ���������� �� mAPI ��� ������ == null, ���������� false
	 * @param comparingVersion ������ ��� ���������
	 * @return true - ���� ��� mAPI � ������ mAPI <= ���������
	 */
	public static boolean isMobileApiLE(VersionNumber comparingVersion)
	{
		if (ApplicationUtil.isNotMobileApi())
			return false;
		VersionNumber versionNumber = getApiVersionNumber();
		return versionNumber != null && versionNumber.le(comparingVersion);
	}

	/**
	 * @return �������� �� ������ � ����������������� ����
	 */
	public static boolean isLimitedScheme()
	{
		UserPrincipal principal = AuthModule.getAuthModule().getPrincipal();
		if (principal == null)
		{
			LOG.error("��������� �� ��������");
			return true; //���������� ����������������� ��� ����� ������������
		}
		return principal.isMobileLimitedScheme();
	}

	/**
	 * @return �������� �� ������ �� light �����
	 */
	public static boolean isLightScheme()
	{
		UserPrincipal principal = AuthModule.getAuthModule().getPrincipal();
		if (principal == null)
		{
			LOG.error("��������� �� ��������");
			return true; //���������� light ��� ����� ������������
		}
		return principal.isMobileLightScheme();
	}

	/**
	 * @return �������� �� ������ �� full �����
	 */
	public static boolean isFullScheme()
	{
		return !isLimitedScheme() && !isLightScheme();
	}

	/**
	 * @return �������� �� ������ � �������������� ���� (full ��� light)
	 */
	public static boolean isAuthorizedZone()
	{
		return !isLimitedScheme();
	}

	/**
	 * ��������� �������� �� ������ �� full ����� � ���� ���, �� ����������� AccessControlException
	 */
	public static void checkFullVersion()
	{
		if (!isFullScheme())
			throw new AccessControlException("������ �������� �� �� full-�����.");
	}

	/**
	 * ��������� �������� �� ������ � �������������� ���� (full ��� light �����) � ���� ���, �� ����������� AccessControlException
	 */
	public static void checkAuthorizedZone()
	{
		if (!isAuthorizedZone())
			throw new AccessControlException("������ �������� �� ����������������� �����.");
	}

	/**
	 * @return �������� �� ������ �� light �����
	 */
	public static Boolean isLightSchemeAuthContext()
	{
		MobileAppScheme mobileAppScheme = AuthenticationContext.getContext().getMobileAppScheme();
		return (mobileAppScheme == null)? null :mobileAppScheme.equals(MobileAppScheme.LIGHT);
	}
}
