package com.rssl.phizic.operations.person.search;

import com.rssl.phizic.auth.modes.UserVisitingMode;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.operations.Operation;
import com.rssl.phizic.business.operations.restrictions.Restriction;
import com.rssl.phizic.business.persons.ActivePerson;

import java.util.Map;

/**
 * �������� ������ �������
 *
 * @author khudyakov
 * @ created 17.07.2012
 * @ $Author$
 * @ $Revision$
 */
public interface SearchPersonOperation<R extends Restriction> extends Operation<R>
{
	/**
	 * ������������� ��������
	 * @throws BusinessException
	 * @throws BusinessLogicException
	 */
	void initialize() throws BusinessException, BusinessLogicException;

	/**
	 * ���������� ����� �������. ������������ � ������������ ������.
	 * @param userVisitingMode ����� ���������
	 * @throws BusinessException
	 * @throws BusinessLogicException
	 */
	void continueSearch(UserVisitingMode userVisitingMode) throws BusinessException, BusinessLogicException;

	/**
	 * ������������� ��������
	 * @param identityData - ����������������� ������ �������
     * @param userVisitingMode - ����� ����� �������
	 * @throws BusinessException
	 * @throws BusinessLogicException
	 */
	void initialize(Map<String, Object> identityData, UserVisitingMode userVisitingMode) throws BusinessException, BusinessLogicException;

	/**
	 * @return �������
	 */
	ActivePerson getEntity() throws BusinessException, BusinessLogicException;
}
