package com.rssl.phizic.operations.locale.dynamic.resources;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.locale.dynamic.resources.LanguageResources;
import com.rssl.phizic.locale.entities.ERIBLocale;
import com.rssl.phizic.operations.EditEntityOperation;

/**
 * @author koptyaev
 * @ created 01.10.2014
 * @ $Author$
 * @ $Revision$
 */
public interface EditLanguageResourcesOperation<T extends LanguageResources, I> extends EditEntityOperation
{
	/**
	 * ���������������� �������� ������� �������� �������� � ������
	 * @param id ������������� ������� ��������
	 * @param localeId ������
	 * @throws BusinessException
	 * @throws BusinessLogicException
	 */
	public void initialize(I id, String localeId) throws BusinessException, BusinessLogicException;

	/**
	 * �������� ������
	 * @return ������
	 */
	public ERIBLocale getLocale();

	/**
	 * �������� ������
	 * @return ������
	 */
	public T getEntity();

}
