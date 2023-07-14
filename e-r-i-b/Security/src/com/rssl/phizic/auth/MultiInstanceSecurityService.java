package com.rssl.phizic.auth;

import com.rssl.phizic.auth.pin.PINEnvelope;
import com.rssl.phizic.common.types.Application;
import com.rssl.phizic.common.types.ApplicationInfo;
import com.rssl.phizic.common.types.ApplicationType;
import com.rssl.phizic.common.types.client.LoginType;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.dataaccess.hibernate.HibernateAction;
import com.rssl.phizic.dataaccess.hibernate.HibernateExecutor;
import com.rssl.phizic.security.SecurityDbException;
import com.rssl.phizic.security.SecurityLogicException;
import com.rssl.phizic.security.config.SecurityConfig;
import com.rssl.phizic.security.password.HashPasswordChanger;
import com.rssl.phizic.security.password.PasswordChanger;
import com.rssl.phizic.security.password.UserPassword;
import com.rssl.phizic.security.password.UserPasswordChanger;
import org.hibernate.LockMode;
import org.hibernate.Query;
import org.hibernate.ReplicationMode;
import org.hibernate.Session;
import org.hibernate.criterion.Expression;

import java.util.*;

/**
 * @author Omeliyanchuk
 * @ created 09.07.2008
 * @ $Author$
 * @ $Revision$
 */

public class MultiInstanceSecurityService
{
	public final static String SCOPE_CLIENT   = "user";
	public final static String SCOPE_EMPLOYEE = "employee";

	public void replicate(final CommonLogin login,final String instanceName) throws SecurityDbException
	{
		try
		{
		    HibernateExecutor.getInstance(instanceName).execute(new HibernateAction<Void>()
		    {
		        public Void run(Session session) throws Exception
		        {
			        session.replicate(login, ReplicationMode.OVERWRITE);
			        List<LoginBlock> loginBlocks = getBlocksForLogin(login,new GregorianCalendar().getTime(),null,instanceName);
					for (LoginBlock loginBlock : loginBlocks)
					{
						// ���� ���������� �� �����������, �� ������ ������������ ���
						session.replicate(loginBlock,ReplicationMode.OVERWRITE);
					}
			        return null;
		        }
		    });
		}
		catch (Exception e)
		{
		   throw new SecurityDbException(e);
		}
	}

	/**
	 * ����� ������ �� ��� ID
	 * @param id ID ������
	 * @return �����
	 */
	public CommonLogin findById(final Long id, String instanceName) throws SecurityDbException
	{
		try
		{
		    return HibernateExecutor.getInstance(instanceName).execute(new HibernateAction<CommonLogin>()
		    {
		        public CommonLogin run(Session session) throws Exception
		        {
			        return (CommonLogin) session.createCriteria(CommonLogin.class)
					        .add(Expression.eq("id", id)).uniqueResult();
		        }
		    });
		}
		catch (Exception e)
		{
		   throw new SecurityDbException(e);
		}
	}

    /**
     * �������� ����� �������
     * @param userId ����� ������� (���������)
     * @return �����
     */
    @Deprecated // userId � ����� ������ �����������
    public Login getClientLogin(final String userId, String instanceName) throws SecurityDbException
    {
	    return (Login) getLogin(userId, SCOPE_CLIENT, instanceName);
    }

	/**
	 * ����� ������ � ��������������� scope
	 * @param userId ���� id
	 * @param scope (UserPasswordValidator.SCOPE_USER ��� UserPasswordValidator.SCOPE_EMPLOYEE )
	 * @return login
	 */
	@Deprecated // userId � ����� ������ �����������
	public CommonLogin getLogin(final String userId, final String scope, String instanceName) throws SecurityDbException
	{
		try
		{
		    return HibernateExecutor.getInstance(instanceName).execute(new HibernateAction<CommonLogin>()
		    {
		        public CommonLogin run(Session session) throws Exception
		        {

			        Query query = session.getNamedQuery("com.rssl.phizic.security.login.findLoginByUserIdAndScope");
			        query.setParameter("userId", userId);
			        query.setParameter("scope", scope);
			        query.setMaxResults(2);

			        return (CommonLogin) query.uniqueResult();
		        }
		    });
		}
		catch (Exception e)
		{
		    throw new SecurityDbException(e);
		}
	}

	/**
	 * �������� ������� ������ ������������ ��� ����� � �������
	 * @param login ����� ������������
	 * @param newPassword ����� ������
	 */
	public void changePassword(CommonLogin login, char[] newPassword, String instanceName) throws SecurityDbException
	{
		UserPasswordChanger passwordChanger = new UserPasswordChanger();
		passwordChanger.changePassword(login, newPassword, instanceName);
	}

	/**
	 * �������� ������� ������ ������������ ��� ����� � �������
	 * @param login ����� ������������
	 * @param hash ��� ������ ������
	 */
	public void setPasswordHash (CommonLogin login, String hash , String instanceName, boolean needPasswordChange) throws SecurityDbException
	{
		PasswordChanger passwordChanger = new HashPasswordChanger(needPasswordChange);
		passwordChanger.changePassword(login, hash.toCharArray(), instanceName);
	}

	/**
	 * �������� ��. ������������
	 * @param login - ����� ��� �������� ���� �������� userId
	 * @param newUserId - ����� �������������
	 * @return �����
	 * @throws SecurityDbException
	 */
	public CommonLogin changeUserId(final CommonLogin login, final String newUserId, String instanceName) throws SecurityDbException
	{
		try
		{
		    return HibernateExecutor.getInstance(instanceName).execute(new HibernateAction<CommonLogin>()
		    {
		        public CommonLogin run(Session session) throws Exception
		        {
			        LoginBase loginBase = (LoginBase) login;
			        loginBase.setUserId(newUserId);
			        session.update(loginBase);

			        return login;
		        }
		    });
		}
		catch (Exception e)
		{
		   throw new SecurityException(e);
		}
	}

	/**
	 * ��������� ��� ������� � ������
	 * @param login �����
	 * @param pinEnvelope ��� �������
	 * @throws com.rssl.phizic.security.SecurityLogicException �������� ������ PIN-��������
	 */
	public void linkPinEvenlope(final Login login, final PINEnvelope pinEnvelope,final String instanceName) throws SecurityDbException, SecurityLogicException
	{
		try
		{
			//������ ��� �� ���-�������� � �������� ����
		    HibernateExecutor.getInstance().execute(new HibernateAction<Void>()
		    {
		        public Void run(Session session) throws Exception
		        {
			       session.lock(pinEnvelope, LockMode.UPGRADE);

			        if (!pinEnvelope.getState().equals(PINEnvelope.STATE_UPLOADED))
				        throw new SecurityLogicException("�������� ������ PIN-��������");


			        pinEnvelope.setState(PINEnvelope.STATE_PROCESSED);
			        lock(login, instanceName);
			        return null;
		        }
		    });
			//����������� � ������ instanceName
			HibernateExecutor.getInstance(instanceName).execute(new HibernateAction<Void>()
		    {
		        public Void run(Session session) throws Exception
		        {
			        session.lock(login, LockMode.UPGRADE);

			        LoginImpl loginImpl = (LoginImpl) login;
			        loginImpl.setPinEnvelopeId(pinEnvelope.getId());

			        session.saveOrUpdate(login);
			        changeUserId(login, pinEnvelope.getUserId(), instanceName);
			        setPasswordHash(login, pinEnvelope.getValue(), instanceName, ConfigFactory.getConfig(SecurityConfig.class).getNeedChangePassword());

			        session.update(pinEnvelope);

		            return null;
		        }
		    });
		}
		catch(SecurityLogicException e)
		{
			throw e;
		}
		catch(SecurityDbException e)
		{
			throw e;
		}
		catch (Exception e)
		{
		   throw new SecurityDbException(e);
		}

	}

	/**
     * �������� ����� ���������� �����
	 * @param userId ��������� ����� (USER ID)
	 * @return �����
     */
	@Deprecated // userId � ����� ������ �����������
    public BankLogin getBankLogin(final String userId, String instanceName) throws SecurityDbException
    {
        return (BankLogin) getLogin(userId, SCOPE_EMPLOYEE, instanceName);
    }

	/**
	 * �������� �������� ����� ���������� ����� �� �������������� � ��� �����
	 * @param csaUserId - ������������� ���������� � ��� �����
	 * @param instanceName - ������� ��
	 * @return ����� ���������� �����
	 * @throws SecurityDbException
	 */
	public BankLogin getActiveBankLoginByCsaUserId(final String csaUserId, String instanceName) throws SecurityDbException
	{
		try
		{
		    return HibernateExecutor.getInstance(instanceName).execute(new HibernateAction<BankLogin>()
		    {
		        public BankLogin run(Session session) throws Exception
		        {
		            return (BankLogin) session.createCriteria(BankLogin.class).
		                    add(Expression.eq("csaUserId",  csaUserId)).
				            add(Expression.eq("deleted",false)).uniqueResult();
		        }
		    }
		    );
		}
		catch(SecurityDbException e)
		{
		    throw e;
		}
		catch (Exception e)
		{
		    throw new SecurityDbException(e);
		}
	}

	/**
	 * �������������� ������ � �������
	 * @param login ����� ��� �������� ���� �������������� ������
	 * @param isSystemBlock - ��������� ����� ��� ���������� ��������������, ���� true - ����� ��������� ����������,
	 *                        false -  ����� ��� ���������� ������������
	 */
	public void unlock(CommonLogin login, Boolean isSystemBlock, String instanceName, final Date blockUntil) throws SecurityDbException
	{

		List<BlockingReasonType> reasonTypes = new ArrayList();
		if (isSystemBlock)
		{
		      reasonTypes.add(BlockingReasonType.system);
		}
		else
		{
			reasonTypes.add(BlockingReasonType.employee);
			reasonTypes.add(BlockingReasonType.wrongLogons);
		}
		unlock(login, reasonTypes, blockUntil, instanceName);
	}

	public void unlock(CommonLogin login, BlockingReasonType reasonType, Date blockedUntil, String instanceName) throws SecurityDbException
	{
		List<BlockingReasonType> reasonTypes = new ArrayList();
		reasonTypes.add(reasonType);
		unlock(login, reasonTypes, blockedUntil, instanceName);
	}


	/**
	 * ������������� login, ��������������� ����������� ��� ��� ������������ ����� ������
	 * @param login - ����� ��� ����������
	 * @param reasonTypes - ������ ����� ���������������� �� ������� ��������������
	 * @throws SecurityDbException
	*/
	public void unlock(final CommonLogin login, List<BlockingReasonType> reasonTypes, final Date blockUntil, String instanceName) throws SecurityDbException
	{
		final List<LoginBlock> blocks = getBlocksForLogin(login, blockUntil, null, instanceName);
		if (blocks.isEmpty())
			return;
		for (final LoginBlock block: blocks)
		{
			if (reasonTypes.contains(block.getReasonType()))
			{
				try
				{
					HibernateExecutor.getInstance(instanceName).execute(new HibernateAction<Void>()
					{
						public Void run(Session session) throws Exception
						{
							block.setBlockedUntil(blockUntil);
							session.update(block);
							session.flush();

							return null;
						}
					}
					);
				}
				catch (Exception e)
				{
					 throw new SecurityDbException(e);
				}
			}
		}
	}

	/**
	 * ��� �������������� ����������, ��� ��������� ������ ������������� �����
	 * ������������� ������ � �������
	 * @param login ����� ��� �������� ���� ������������� ������
	 */
	public void lock(CommonLogin login, String instanceName) throws SecurityDbException
	{
		Calendar calendar = new GregorianCalendar();
		lock(login, calendar.getTime(), null, BlockingReasonType.system, null, null, instanceName);
	}

	/**
	 * �������� ������������� login �� ����������� blockedUntil
	 * @param login - ����� ��� ����������
	 * @param blockedUntil - ����-����� ��������� ����������
	 * @throws SecurityDbException
	 */
	public void lockAndClear(final CommonLogin login, final Date blockedUntil, String instanceName) throws SecurityDbException
	{
		Calendar calendar = new GregorianCalendar();
		lock(login, calendar.getTime(), blockedUntil, BlockingReasonType.wrongLogons, null, null, instanceName);
		clearWrongAttempts(login, instanceName);
	}

	/**
	 * ������������� login � �������� �������
	 * @param login - ����� ��� ����������
	 * @param blockedUntil - ����-����� ��������� ����������, ���� ��� null
	 * @param blockedFrom - ���� ������ ����������
	 * @param reasonType - ��� ������� ����������
	 * @param reasonDescription - �������� ������� ����������, ���� ��� null
	 * @param employeeLogin - ����� ����, ��� ���������, ���� �������������, �� null
	 * @throws SecurityDbException
	*/
	public LoginBlock lock(final CommonLogin login, final Date blockedFrom, final Date blockedUntil,
	                 final BlockingReasonType reasonType, final String reasonDescription,
	                 final Long employeeLogin, String instanceName)  throws SecurityDbException
	{
		try
		{
			return HibernateExecutor.getInstance(instanceName).execute(new HibernateAction<LoginBlock>()
			{
				public LoginBlock run(Session session) throws Exception
				{
					LoginBlock lg = new LoginBlock();

					lg.setBlockedFrom(blockedFrom);
					lg.setBlockedUntil(blockedUntil);
					lg.setReasonType(reasonType);
					lg.setReasonDescription(reasonDescription);
					lg.setLogin(login);
					lg.setEmployee(employeeLogin);

					session.save(lg);
					return lg;
		        }
			});
		}
		catch (Exception e)
		{
			throw new SecurityDbException(e);
		}
	}

	/**
	 * ���������������� ������� �������� �������
	 * @param login - ����� ��� ���������� ��������
	 */
	public void addWrongAttempt(final CommonLogin login, String instanceName) throws SecurityDbException
	{
		try
		{
			HibernateExecutor.getInstance(instanceName).execute(new HibernateAction<Void>()
			{
				public Void run(Session session) throws Exception
				{
					LoginBase loginBase = (LoginBase) login;
					loginBase.setWrongLoginAttempts(loginBase.getWrongLoginAttempts() + 1);
					session.saveOrUpdate(loginBase);
					return null;
				}
			});
		}
		catch (Exception e)
		{
			throw new SecurityDbException(e);
		}

	}

	/**
	 * �������� ������� �������� �������
	 * @param login - ����� ��� ������ ��������
	 */
	public void clearWrongAttempts(final CommonLogin login, String instanceName) throws SecurityDbException
	{
		try
		{
			HibernateExecutor.getInstance(instanceName).execute(new HibernateAction<Void>()
			{
				public Void run(Session session) throws Exception
				{
					LoginBase loginBase = (LoginBase) login;
					loginBase.setWrongLoginAttempts(0);
					session.saveOrUpdate(loginBase);
					return null;
				}
			});
		}
		catch (Exception e)
		{
			throw new SecurityDbException(e);
		}
	}

    /**
     * �������� ����� ��� ���������.
     * @param login - ����� ��� ��������
     * @throws SecurityDbException
     */
    public void markDeleted(final CommonLogin login, final String instanceName) throws SecurityDbException
    {
        try
        {
            HibernateExecutor.getInstance(instanceName).execute(new HibernateAction<Void>()
            {
                public Void run(Session session) throws Exception
                {
                    LoginBase loginBase = (LoginBase) login;
                    loginBase.setDeleted(true);
                    // ���������� ������
                    lock(loginBase, instanceName);
                    session.saveOrUpdate(loginBase);
                    return null;
                }
            });
        }
        catch (Exception e)
        {
            throw new SecurityDbException(e);
        }
    }

	/**
	 * ������� ������ (��� ���) ������������ ��� ����� � �������
	 *
	 * @param login ����� ������������
	 * @return ��� ������ ������������
	 */
	public String getPasswordHash(final CommonLogin login, String instanceName) throws SecurityDbException
	{
		UserPassword password = getPassword(login, instanceName);
		return String.valueOf(password.getValue());
	}

	/**
	 * ������� ������ ����������� ������������
	 * @param login
	 * @param instanceName
	 * @throws SecurityDbException
	 */
	public void removePassword(final CommonLogin login,final String instanceName) throws SecurityDbException
	{
		try
		{
			HibernateExecutor.getInstance(instanceName).execute(new HibernateAction<Void>()
			{
				public Void run(Session session) throws Exception
				{

					UserPassword password = getPassword(login, instanceName);
					if(password!=null)
						session.delete(password);
					return null;
				}
			});
		}
		catch (Exception e)
		{
			throw new SecurityDbException(e);
		}
	}

	public UserPassword getPassword(final CommonLogin login, String instanceName) throws SecurityDbException
	{
		try
		{
			return HibernateExecutor.getInstance(instanceName).execute(new HibernateAction<UserPassword>()
			{
				public UserPassword run(Session session) throws Exception
				{

					Query query = session.getNamedQuery("com.rssl.phizic.security.password.getPasswordByLogin");
					query.setParameter("loginId", login.getId());

					return (UserPassword) query.uniqueResult();
				}
			});
		}
		catch (Exception e)
		{
			throw new SecurityDbException(e);
		}
	}

	/**
	 * @return ������ �������� ���������� ��� ������
	 * @param login �����, ��� �������� ���������� ����� ����������
	 * @param blockedUntil ���� ��� ������� �����������, �������� ����������
	 * @param reasonType ������� ����������, null ���� ���
	 * @param instanceName ��� ��, �� ������� ���������� �������� ����������
	 *
	 */
	public List<LoginBlock> getBlocksForLogin(final CommonLogin login, final Date blockedUntil, final BlockingReasonType reasonType, String instanceName) throws SecurityException
	{
		try
		{
		    Object result = HibernateExecutor.getInstance(instanceName).execute(new HibernateAction<Object>()
		    {
		        public List<LoginBlock> run(Session session) throws Exception
		        {
			        Query query = session.getNamedQuery("com.rssl.phizic.security.auth.getBlocksByLogin");
					query.setParameter("login", login);
			        query.setParameter("blockedUntil", blockedUntil);
			        String tempReason = null;
			        if (reasonType!=null)
			            tempReason = reasonType.toString();
			        query.setParameter("reasonType", tempReason);

					return query.list();
		        }
		    });
			return (List<LoginBlock>) result;
		}
		catch(Exception e)
		{
			throw new SecurityException(e);
		}
	}

	/**
	 * ��������� ������� ���������� �� ������� � ��� ���������� ��� ���������� ������������,
	 * �������� � ��������� ������ �������
	 * @param login - ����� ������������� ������������
	 * @param blockedUntil - ������������ ������ �������
	 * @param instanceName
	 * @return true, ���� ������������ � ��������� ������� ������������ � ������� ������
	 */
	public boolean isLoginBlocked(final CommonLogin login, final Date blockedUntil, String instanceName) throws SecurityException
	{
		try
		{
		    return HibernateExecutor.getInstance(instanceName).execute(new HibernateAction<Boolean>()
		    {
		        public Boolean run(Session session) throws Exception
		        {
			        Query query = session.getNamedQuery("com.rssl.phizic.security.auth.isLoginBlocked");
					query.setParameter("login", login);
			        query.setParameter("blockedUntil", blockedUntil);
			        query.setMaxResults(1);
					return query.uniqueResult() != null;
		        }
		    });
		}
		catch(Exception e)
		{
			throw new SecurityException(e);
		}
	}

	/**
	 * @return ������ ���� ���������� ����������
	 */
	public List<LoginBlock> getDelayedBlocks(String instanceName, final Date blockedUntil) throws SecurityException
	{
		try
		{
		    Object result = HibernateExecutor.getInstance(instanceName).execute(new HibernateAction<Object>()
		    {
		        public List<LoginBlock> run(Session session) throws Exception
		        {
			        Query query = session.getNamedQuery("com.rssl.phizic.security.auth.getDelayBlocks");
			        Calendar currentDate = new GregorianCalendar();
			        Date dateUntil = (blockedUntil == null)?currentDate.getTime():blockedUntil;
			        query.setParameter("blockedUntil", dateUntil);
					return query.list();
		        }
		    });
			return (List<LoginBlock>) result;
		}
		catch(Exception e)
		{
			throw new SecurityException(e);
		}
	}

	/**
	 * @return ������ ���� ����������, ��������� ������ �����������
	 */
	private List<LoginBlock> blocksByBlockerId(final Long blockerLogin, final Date blockedUntil, String instanceName) throws SecurityException
	{
		try
		{
		    Object result = HibernateExecutor.getInstance(instanceName).execute(new HibernateAction<Object>()
		    {
		        public List<LoginBlock> run(Session session) throws Exception
		        {
			        Query query = session.getNamedQuery("com.rssl.phizic.security.auth.getBlocksByBlockerId");
			        query.setParameter("blockerLogin", blockerLogin);
			        query.setParameter("blockedUntil", blockedUntil);
					return query.list();
		        }
		    });
			return (List<LoginBlock>) result;
		}
		catch(Exception e)
		{
			throw new SecurityException(e);
		}
	}

	/**
	 * ������� ����� ����������, ���� ��� ���������� �� ���� ����������
	 * @param login - ����� ���������� �������
	 * @param instanceName
	 * @throws SecurityDbException
	 */
	public void removeLastBlockers(final CommonLogin login, String instanceName) throws SecurityDbException
	{
		List<LoginBlock> loginBlocks = getBlocksForLogin(login,new GregorianCalendar().getTime(),null,instanceName);

		for (LoginBlock loginBlock : loginBlocks)
		{
			if(loginBlock.getEmployee()!=null)
			{
				CommonLogin employeeShadowLogin = findById(Long.valueOf(loginBlock.getEmployee()), instanceName);
				if (blocksByBlockerId(loginBlock.getEmployee(), new GregorianCalendar().getTime(), instanceName).size() == 0 && employeeShadowLogin != null)
				{
					remove(employeeShadowLogin, instanceName);
				}
			}
		}
	}

	/**
	 * ������� ����� � ��������� � ��� ��������
	 * @param login
	 * @param instanceName
	 * @throws SecurityException
	 */
	public void remove(final CommonLogin login,final String instanceName) throws SecurityException
	{
		try
		{
		    HibernateExecutor.getInstance(instanceName).execute(new HibernateAction<Void>()
		    {
		        public Void run(Session session) throws Exception
		        {
			        //noinspection unchecked
			        session.delete(login);
			        return null;
		        }
		    });
		}
		catch (Exception e)
		{
		   throw new SecurityException(e);
		}
	}


	/**
	 * �������� ���������� � ���������� ������� ����� �������
	 * @param login �����, ��� �������� ���������� ����
	 * @param ipAddress ip - �������, ������ �������� ����
	 * @param sessionId - ������������� ������, � ������ ������� ���������� ���� � �������
	 * @param applicationType ��� ���������� � ������, ������� ���������� ���� � �������
	 * @throws SecurityException
	 */
    public void updateLogonInfo(final CommonLogin login, final String ipAddress, final String sessionId, final ApplicationType applicationType, final String instanceName, final LoginType loginType, final String identificationParameter) throws SecurityException
    {

	    try
		{
			HibernateExecutor.getInstance(instanceName).execute(new HibernateAction<Void>()
			{
				public Void run(Session session)
				{
					LoginBase loginBase = (LoginBase) login;
                    //������� ������� ����� � �������
                    //���� null �� ������� ��� ������ �������
                    loginBase.setFirstLogin(login.getLastLogonDate()==null);
                    loginBase.setLastIpAddress(loginBase.getIpAddress());
                    if (Application.atm !=  ApplicationInfo.getCurrentApplication())
                    {
					    loginBase.setIpAddress(ipAddress);
                    }
					loginBase.setLastLogonType(loginType == null ? "" : loginType.toString());
					loginBase.setLastLogonParameter(identificationParameter);
                    loginBase.setLastLogonDate(login.getLogonDate());
                    loginBase.setLogonDate(Calendar.getInstance());
					session.saveOrUpdate(loginBase);
					session.lock(login, LockMode.UPGRADE);

					LogonSession logonSession = getLogonSession(login.getId(), applicationType, instanceName);
					if (logonSession == null)
					{
						session.save(new LogonSession(login.getId(), applicationType, sessionId));
					}
					else
					{
						logonSession.setSessionId(sessionId);
						session.update(logonSession);
					}
					return null;
				}
			});
		}
		catch (Exception e)
		{
			throw new SecurityException(e);
		}
    }

	/**
	 * todo. BUG025218
	 *
	 * ��������� ������ ��������� �������������� �������
	 * @param login - ����� �������
	 * @param logonCardNumber - ����� �����
	 * @param logonCardDepartment - �������������. � �������� ��������� �����, �� ������� ����� ������
	 * @param isMobileBankConnected - ������ "������ ����� ����������� � ���������� �����"
	 * @param csaUserId - ����� ������������ �� ���
	 * @param instanceName - ������� ��
	 * @throws SecurityException
	 */
	public void updateClientLastLogonData(final Login login, final String logonCardNumber, final String logonCardDepartment, final boolean isMobileBankConnected, final String csaUserId, final String csaUserAlias, String instanceName) throws SecurityException
	{
		try
		{
			HibernateExecutor.getInstance(instanceName).execute(new HibernateAction<Void>()
			{
				public Void run(Session session)
				{
					LoginImpl loginBase = (LoginImpl) login;
					loginBase.setLastLogonCardNumber(logonCardNumber);
					loginBase.setLastLogonCardDepartment(logonCardDepartment);
					loginBase.setMobileBankConnected(isMobileBankConnected);
					loginBase.setCsaUserId(csaUserId);
					loginBase.setCsaUserAlias(csaUserAlias);
					session.saveOrUpdate(loginBase);
					return null;
				}
			}
			);
		}
		catch (Exception ex)
		{
			throw new SecurityException(ex);
		}
	}

	/**

	 * ��������� ������ �� ������������� ��������� �������������� �������
	 * @param login - ����� �������
	 * @param tb - id ��������
	 * @param osb - id ���
	 * @param vsp - id ���
	 * @param instanceName - ������� ��
	 * @throws SecurityException
	 */
	public void updateClientLastLogonDepartmentsData(final Login login, final String tb, final String osb, final String vsp, String instanceName) throws SecurityException
	{
		try
		{
			HibernateExecutor.getInstance(instanceName).execute(new HibernateAction<Void>()
			{
				public Void run(Session session)
				{
					LoginImpl loginBase = (LoginImpl) login;
					loginBase.setLastLogonCardTB(tb);
					loginBase.setLastLogonCardOSB(osb);
					loginBase.setLastLogonCardVSP(vsp);
					session.saveOrUpdate(loginBase);
					return null;
				}
			}
			);
		}
		catch (Exception ex)
		{
			throw new SecurityException(ex);
		}
	}

	/**
	 * �������� ���� ��������� ������������� ������
	 * @param login �����
	 * @param lastSynchronizationDate ���� ��������� ������������� ������
	 * @param instanceName ������� ��
	 * @throws SecurityException
	 */
	public void updateLastSynchronizationDate(final BankLogin login, final Calendar lastSynchronizationDate, String instanceName) throws SecurityException
	{
		try
		{
			HibernateExecutor.getInstance(instanceName).execute(new HibernateAction<Void>()
			{
				public Void run(Session session)
				{
					login.setLastSynchronizationDate(lastSynchronizationDate);
					session.update(login);
					return null;
				}
			}
			);
		}
		catch (Exception ex)
		{
			throw new SecurityException(ex);
		}
	}

	/**
	 * ���������� ������������� ������ � ������ ������� ��� ���������� ���� � ������� ��� ����������� loginId
	 * @param loginId - ������������� ������ ������������
	 * @param applicationType - ��� ����������
	 * @param instanceName - ��������� ��
	 * @return
	 * @throws SecurityException
	 */
	public String getLogonSessionId(final Long loginId, final ApplicationType applicationType, String instanceName) throws SecurityException
	{
		LogonSession logonSession = getLogonSession(loginId, applicationType, instanceName);
		if (logonSession == null)
		{
			return null;
		}
		return logonSession.getSessionId();
	}

	private LogonSession getLogonSession(final Long loginId, final ApplicationType applicationType , String instanceName)
	{
		try
		{
			return HibernateExecutor.getInstance(instanceName).execute(new HibernateAction<LogonSession>()
			{
				public LogonSession run(Session session) throws Exception
				{
					Query query = session.getNamedQuery("com.rssl.phizic.security.login.getLogonSession");
					query.setParameter("loginId", loginId);
					query.setParameter("applicationType", applicationType);
					return (LogonSession)query.uniqueResult();
				}
			});
		}
		catch(Exception e)
		{
			throw new SecurityException(e);
		}
	}
}
