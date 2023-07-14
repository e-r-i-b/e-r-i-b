package com.rssl.phizic.business.ext.sbrf.csa.blockingrules;

import com.rssl.phizic.business.Constants;
import com.rssl.phizic.business.SimpleService;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.dataaccess.DatabaseServiceBase;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Expression;

/**
 * @author vagin
 * @ created 21.08.2012
 * @ $Author$
 * @ $Revision$
 * ������ ��� ������ � ��������� ����������
 */
public class BlockingRulesService
{
	private static final SimpleService simpleService = new SimpleService();

	/**
	 * ���������� � �� ������� ����������
	 * @param rule - ������� ��� ����������
	 * @throws BusinessException
	 */
	public void save(BlockingRules rule) throws BusinessException
	{
		simpleService.addOrUpdateWithConstraintViolationException(rule, getInstanceName());
	}

	/**
	 * �������� ������� ���������
	 * @param rule - ������� ��� ��������
	 */
	public void remove(BlockingRules rule) throws BusinessException
	{
		simpleService.remove(rule,getInstanceName());
	}

	/**
	 * ����� ������� ���������� �� id
	 * @param id - ������������� �������
	 * @return �������� �������
	 * @throws BusinessException
	 */
	public BlockingRules findById(Long id) throws BusinessException
	{
		return simpleService.findById(BlockingRules.class, id, getInstanceName());
	}

	/**
	 * ��������� ���������� ����������. ������ � ������� � departments = 'global'-������ � ���������� ����������.
	 * @return true/false
	 * @throws BusinessException
	 */
	public BlockingRules getGlobalBlocking() throws BusinessException
	{
		DetachedCriteria criteria = DetachedCriteria.forClass(BlockingRules.class);
		criteria.add(Expression.eq("departments", "global"));
		return simpleService.findSingle(criteria, getInstanceName());
	}

	/**
	 * ���������� �� ���������� ����������� �� ����
	 * @return true/false
	 * @throws BusinessException
	 */
	public boolean isGlobalBlock() throws BusinessException
	{
		BlockingRules global = getGlobalBlocking();
		//���� ������ � ���������� ���������� �� ������� �������, ��� ���������� ���.
		if (global == null)
			return false;
		return global.getState() == BlockingState.blocked;
	}

	private static String getInstanceName()
	{
		return Constants.DB_CSA;
	}
}
