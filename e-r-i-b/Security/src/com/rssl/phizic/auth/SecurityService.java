package com.rssl.phizic.auth;

import com.rssl.phizic.auth.pin.PINEnvelope;
import com.rssl.phizic.common.types.ApplicationType;
import com.rssl.phizic.common.types.client.LoginType;
import com.rssl.phizic.security.SecurityDbException;
import com.rssl.phizic.security.SecurityLogicException;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Kidyaev
 * Date: 12.09.2005
 * Time: 14:28:47
 */
public class SecurityService extends MultiInstanceSecurityService
{
	public void addWrongAttempt(final CommonLogin login) throws SecurityDbException
	{
		super.addWrongAttempt(login, null);
	}

	public List<LoginBlock> getBlocksForLogin(final CommonLogin login, final Date blockedUntil, final BlockingReasonType reasonType) throws SecurityException
	{
		return super.getBlocksForLogin(login, blockedUntil, reasonType, null);
	}

	public boolean isLoginBlocked(final CommonLogin login, final Date blockedUntil) throws SecurityException
	{
		return super.isLoginBlocked(login, blockedUntil, null);
	}

	public void changePassword(CommonLogin login, char[] newPassword) throws SecurityDbException
	{
		super.changePassword(login, newPassword, null);
	}

	public CommonLogin changeUserId(final CommonLogin login, final String newUserId) throws SecurityDbException
	{
		return super.changeUserId(login, newUserId, null);
	}

	public void clearWrongAttempts(final CommonLogin login) throws SecurityDbException
	{
		super.clearWrongAttempts(login, null);
	}

	public CommonLogin findById(final Long id) throws SecurityDbException
	{
		return super.findById(id, null);
	}

	@Deprecated // userId в общем случае неуникально
	public BankLogin getBankLogin(final String userId) throws SecurityDbException
	{
		return super.getBankLogin(userId, null);
	}

	public BankLogin getActiveBankLoginByCsaUserId(String csaUserId) throws SecurityDbException
	{
		return super.getActiveBankLoginByCsaUserId(csaUserId, null);
	}

	@Deprecated // userId в общем случае неуникально
	public Login getClientLogin(final String userId) throws SecurityDbException
	{
		return super.getClientLogin(userId, null);
	}

	@Deprecated // userId в общем случае неуникально
	public CommonLogin getLogin(final String userId, final String scope) throws SecurityDbException
	{
		return super.getLogin(userId,scope,null);
	}

	public String getPasswordHash(final CommonLogin login) throws SecurityDbException
	{
		return super.getPasswordHash(login, null);
	}

	public void linkPinEvenlope(final Login login, final PINEnvelope pinEnvelope) throws SecurityDbException, SecurityLogicException
	{
		super.linkPinEvenlope(login, pinEnvelope, null);
	}

	public LoginBlock lock(final CommonLogin login, final Date blockedFrom, final Date blockedUntil, final BlockingReasonType reasonType, final String reasonDescription, final Long employeeLogin) throws SecurityDbException
	{
		return super.lock(login, blockedFrom, blockedUntil, reasonType, reasonDescription, employeeLogin, null);
	}

	public void lock(CommonLogin login) throws SecurityDbException
	{
		super.lock(login, null);
	}

	public void lockAndClear(final CommonLogin login, final Date blockedUntil) throws SecurityDbException
	{
		super.lockAndClear(login, blockedUntil, null);
	}

	public void markDeleted(final CommonLogin login) throws SecurityDbException
	{
		super.markDeleted(login, null);
	}

	public void setPasswordHash(CommonLogin login, String hash, boolean needChange) throws SecurityDbException
	{
		super.setPasswordHash(login, hash, null, needChange);
	}

	public void unlock(CommonLogin login, BlockingReasonType reasonType, Date blockedUntil) throws SecurityDbException
	{
		super.unlock(login, reasonType, blockedUntil, null);
	}

	public void unlock(final CommonLogin login, List<BlockingReasonType> reasonTypes, final Date blockUntil) throws SecurityDbException
	{
		super.unlock(login, reasonTypes, blockUntil, null);
	}

	public void remove(final CommonLogin login) throws SecurityException
	{
		super.remove(login, null);
	}

   /**
   * Обновить информауцию о параметрах текущего входа клиента
   * @param login Логин, для которого произведен вход
   * @param ipAddress ip - клиента, откуда совершен вход
   * @param sessionId - идентификатор сессии, в рамках которой произведен вход в систему
   * @param applicationType тип приложения в рамках, которой произведен вход в систему
   * @throws SecurityException
   */

   public void updateLogonInfo(final CommonLogin login, String ipAddress, String sessionId, final ApplicationType applicationType)  throws SecurityException
   {
       super.updateLogonInfo(login, ipAddress, sessionId, applicationType, null, null, null);
   }

   public void updateLogonInfo(final CommonLogin login, String ipAddress, String sessionId, final ApplicationType applicationType, final LoginType loginType, final String identificationParameter)  throws SecurityException
    {
        super.updateLogonInfo(login, ipAddress, sessionId, applicationType, null, loginType, identificationParameter);
    }

	public void updateClientLastLogonData(Login login, String logonCardNumber, String logonCardDepartment, boolean isMobileBankConnected, String csaUserId, String csaUserAlias) throws SecurityException
	{
		super.updateClientLastLogonData(login, logonCardNumber, logonCardDepartment, isMobileBankConnected, csaUserId, csaUserAlias, null);
	}

	public void updateClientLastLogonDepartmentsData(Login login, String tb, String osb, String vsp) throws SecurityException
	{
		super.updateClientLastLogonDepartmentsData(login, tb, osb, vsp, null);
	}

	/**
	 * обновить дату последней синхронизации данных
	 * @param login логин
	 * @param lastSynchronizationDate дата последней синхронизации данных
	 * @throws SecurityException
	 */
	public void updateLastSynchronizationDate(BankLogin login, Calendar lastSynchronizationDate) throws SecurityException
	{
		super.updateLastSynchronizationDate(login, lastSynchronizationDate, null);
	}

	public String getLogonSessionId(Long loginId, ApplicationType applicationType) throws SecurityException
	{
		return super.getLogonSessionId(loginId, applicationType, null);
	}
}
