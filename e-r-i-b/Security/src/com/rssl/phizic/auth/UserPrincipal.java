package com.rssl.phizic.auth;

import com.rssl.phizic.auth.modes.AccessPolicy;
import com.rssl.phizic.auth.modes.AccessType;

import java.security.Principal;
import java.io.Serializable;

/**
 * Created by IntelliJ IDEA.
 * User: Roshka
 * Date: 13.09.2005
 * Time: 16:28:01
 */
public interface UserPrincipal extends Principal, Serializable
{
	/**
	 * @return �����
	 */
    CommonLogin getLogin();

	/**
	 * @return ��� �������
	 */
	AccessType getAccessType();

	/**
	 * @return �������� ������� ��������� � �����������
	 */
	AccessPolicy getAccessPolicy();

	/**
	 * �������� ��������� ����
	 * @param name ��� ��������
	 * @return ��������
	 */
	String getAccessProperty(String name);

    /**
     * �������� �� ����� ������� ������� � mAPI light-������
     * @return true - ����� light, false - ����� �� light
     */
    boolean isMobileLightScheme();

    /**
     * ��������� �������� light-����� ������� � mAPI
     * @param mobileLightScheme ������� light-�����
     */
    void setMobileLightScheme(boolean mobileLightScheme);

	/**
	 * �������� �� ����� ������� ������� � mAPI limited-������
	 * @return true - ����� limited, false - ����� �� limited
	 */
	boolean isMobileLimitedScheme();

	/**
	 * ��������� �������� ��������� �������� ������
	 */
	void setColdPeriod(boolean coldPeriod);

	/**
	 * @return �������� �� ������ �������� ������
	 */
	boolean isColdPeriod();
}
