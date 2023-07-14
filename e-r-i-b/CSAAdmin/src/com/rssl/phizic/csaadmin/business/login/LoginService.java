package com.rssl.phizic.csaadmin.business.login;

import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.csaadmin.business.common.AdminException;
import com.rssl.phizic.csaadmin.business.common.AdminLogicException;
import com.rssl.phizic.csaadmin.business.common.SimpleService;
import com.rssl.phizic.csaadmin.business.departments.AllowedDepartmentService;
import com.rssl.phizic.security.config.SecurityConfig;
import com.rssl.phizic.utils.DateHelper;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Expression;

import java.util.Calendar;

/**
 * @author akrenev
 * @ created 30.09.13
 * @ $Author$
 * @ $Revision$
 *
 * ������ ������ � ��������
 */

public class LoginService extends SimpleService<Login>
{
	private static final AllowedDepartmentService allowedDepartmentService = new AllowedDepartmentService();

	@Override
	protected Class<Login> getEntityClass()
	{
		return Login.class;
	}

	/**
	 * ����� ������ �� �����
	 * @param name - �����(��� ������������)
	 * @return �����
	 * @throws AdminException
	 */
	public Login findByName(String name) throws AdminException
	{
		DetachedCriteria criteria = DetachedCriteria.forClass(Login.class);
		criteria.add(Expression.eq("name",name));
		criteria.add(Expression.eq("deleted",false));
		return findSingle(criteria);
	}

	/**
	 * �������� �����
	 * @param login - ����� ����������
	 * @return ����������� �����
	 * @throws AdminException
	 */
	public Login update(Login login) throws AdminException
	{
		login.setLastUpdateDate(Calendar.getInstance());
		return super.save(login);
	}

	/**
	 * �������� ���� ������������
	 * @param login - ����� ������������
	 * @param nodeId - ������������� �����
	 * @return �����
	 * @throws AdminException
	 */
	public Login changeNode(Login login, Long nodeId) throws AdminException
	{
		login.setNodeId(nodeId);
		return super.save(login);
	}

	@Override
	public Login delete(Login login) throws AdminException, AdminLogicException
	{
		login.setDeleted(true);
		login.setAccessScheme(null);
		allowedDepartmentService.deleteAllAllowed(login);
		return save(login);
	}

	/**
	 * �������� ������� �������� ������� �����
	 * @return �����
	 * @param login - ����� ��� ������ ��������
	 */
	public Login clearWrongAttempts(Login login) throws AdminException
	{
		login.setWrongLoginAttempts(0);
		return super.save(login);
	}

	/**
	 * ��������� ����� ������
	 * @param login - ����� ����������,������� ������ ������
	 * @param newPassword - ����� ������
	 * @throws AdminException
	 * @throws AdminLogicException
	 */
	public void selfChangePassword(Login login, String newPassword) throws AdminException, AdminLogicException
	{
		login.setNewPassword(newPassword);
		login.setPasswordExpireDate(calculateExpireDate());
		update(login);
	}

	private Calendar calculateExpireDate()
	{
		SecurityConfig securityConfig = ConfigFactory.getConfig(SecurityConfig.class);
		Calendar expireDate = DateHelper.getCurrentDate();
		expireDate.add(Calendar.DATE, securityConfig.getPasswordLifeTime() + 1);
		return expireDate;
	}
}
