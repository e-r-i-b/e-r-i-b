package com.rssl.phizic.security.config;

import com.rssl.common.forms.validators.passwords.generated.Charset;
import com.rssl.phizic.config.Config;
import com.rssl.phizic.config.PropertyReader;

import java.util.List;

/**
 * Created by IntelliJ IDEA. User: Evgrafov Date: 19.09.2005 Time: 12:41:14
 */
public abstract class SecurityConfig extends Config
{
	protected SecurityConfig(PropertyReader reader)
	{
		super(reader);
	}

	/**
	 * @return ��. PermissionsProvider
	 */
    public abstract String getPermissionProviderClassName();

	/**
	 * @return ��� ������, ���������������� ����� ��� ������
	 */
    public abstract String getGuestPermissionProviderClassName();

	/**
	 * @return ��. PermissionsCalculator
	 */
	public abstract String getPermissionCalculatorClassName();

	/**
	 * @return ��. LoginInfoProvider
	 */
    public abstract String getLoginInfoProviderClassName();

	/**
	 * @return ��� ������������ �� ��������� ��� PhizIA
	 */
    public abstract String getDefaultAdminName();

	/**
	 * @return ��� ���������� ������������
	 */
	public abstract String getAnonymousClientName();

	/**
	 * @return ���������� ������� ����� ������
	 */
    public abstract int getLoginAttempts ();

	/**
	 * @return ���������� ���� ����� ������
	 */
    public abstract int getPasswordLifeTime ();

    /**
     * @return ����� ���������� � ��������
     */
    public abstract int getBlockedTimeout ();

	/**
	 * @return ���������� ������� �������������
	 */
    public abstract int getConfirmAttempts ();

	/**
     * @return ������ ������
     */
    public abstract int getPasswordLength();

    /**
     * @return ���������� ������ ��������, �� �������� ��������� ������ ������
     */
    public abstract String getPasswordAllowedChars();

	/**
	 *
	 * @return ������ �������� ��� �������� ������
	 */
	public abstract String getLoginAllowedChars();

    /**
     * @return ������ ������ � ����� ������
     */
    public abstract int getCardPasswordLength();

    /**
     * @return ���������� ������ ��������, �� ������� ��������� ������ ������ ��� ����� ������
     */
    public abstract String getCardPasswordAllowedChars();

    /**
     * @return ���������� ���� ����������� ��-���������
     */
    public abstract int getCardsCount();

    /**
     * @return ���������� ������� � ����� ������ ����������� ��-���������
     */
    public abstract int getCardPasswords();

	/**
	 * ����� ����������� ���������� ��� ������������ admin
	 * @return �����
	 */
	public abstract String getAdminPermissionProvider();

	/**
	 * @return ������� �������������� �������� ���� ������� ������������ 
	 */
	public abstract boolean isCardPasswordAutoAssign();

	/**
	 * @return ���������� ��� ������ ���������� CryptoService
	 */
	public abstract String  getCryptoServiceClassName();

	/**
	 * ����� �� ����������� ������, ��� ������������ ����� ������.
	 * @return
	 */
	public abstract boolean isAdminNeedBlocked();

	/**
	 * ����������� �� ������ ������ ��� ������ ����� � �������
	 * @return
	 */
	public abstract boolean getNeedChangePassword();

	/**
	 * @return ������ ��������, �� ������� ����� �������� ������
	 */
	public abstract List<Charset> getPasswordCharsets();

	/**
	 * @return ������ �������� (�����) ������������� ��� �������� �����������.
	 * �.�. ��������� �������� �������� ����������� � �������������� (��������, ��������  - 1 �������, �.�. ���)
	 */
	public abstract int getDepartmentsAllowedLevel();

	/**
	 * @return ����� ��������������� ��� �������� �������������
	 */
	public abstract int getDepartmentAdminsLimit();

	/**
	 * ����������, ��������� �� �������������� ������������� ����������� ����� �������������� �����
	 * @return true - ���������, false - ���������
	 */
	public abstract boolean isDenyCustomRights();

	/**
	 * ��������� ����������� ����� ������ ��� ���������� ����������
	 * @return ����� ������ ��� ���������� ����������
	 */
	public abstract int getMobilePINLength();

	/**
	 * �������, ������������ ����������� ������� � ������ ��� ���������� ����������
	 * @return ������
	 */
	public abstract String getMobilePINRegExp();

	/**
	 * ������� ������������� ���������� ��������� ������������� ��� ����� �������� ��������� ��������� �������������
	 * @return ������������� ���������� ��������� �������������
	 */
	public abstract boolean getNeedLoginConfirmAutoselect();

	/**
	 * ������� ������������� ���������� ��������� ������������� ��� ������������� ������� �������� ��������� ��������� �������������
	 * @return ������������� ���������� ��������� �������������
	 */
	public abstract boolean getNeedPaymentConfirmAutoselect();

	public abstract int getSmsBankingSessionLifetime();
	/**
	 * @return ����� ������� �������� ������� ��� ����������� ������� ����.
	 */
	public abstract int getNumberOfLoginAttemptsAtRegistration();

	/**
	 * @return ����� ����� ����� ������� ����� ��������� ��������� ������� ����� ������ ���������� ���� ����� ��� ����������� ������� ����.
	 */
	public abstract int getMinuteToResetCaptchaAtRegistration();

	/**
	 * @return ����� �� ���������� ������� �����������.
	 */
	public abstract boolean getNeedKasperskyScript();

	/**
	 * ����� �� ���������� ������� ������ ���������� �� ���������� ������������
	 * @return
	 */
	public abstract long getTimeToBlock();
}
