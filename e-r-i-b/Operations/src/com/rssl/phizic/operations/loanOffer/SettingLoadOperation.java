package com.rssl.phizic.operations.loanOffer;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.SimpleService;
import com.rssl.phizic.business.loanOffer.SettingLoanAbstract;
import com.rssl.phizic.operations.EditEntityOperation;
import com.rssl.phizic.operations.OperationBase;
import com.rssl.phizic.utils.files.FileHelper;

import java.io.File;

/**
 * @author Mescheryakova
 * @ created 11.07.2011
 * @ $Author$
 * @ $Revision$
 */

public abstract  class SettingLoadOperation  extends OperationBase implements EditEntityOperation
{
	protected static  final SimpleService simpleService = new SimpleService();
	protected SettingLoanAbstract setting;
	/**
	 * C�������� ��������.
	 * @throws com.rssl.phizic.business.BusinessLogicException ���������� ������
	 * @throws com.rssl.phizic.business.BusinessException
	 */
	public void save() throws BusinessException, BusinessLogicException
	{
		String filePath = FileHelper.normalizePath(setting.getDirectory() + File.separator + setting.getFileName());
		if ((new File(filePath)).exists())
			simpleService.addOrUpdate(setting);
		else
			throw new BusinessLogicException("���� � �������� ��� � ����� ������ �������� ������ �������");
	}

	/**
	 * �������� ���������������/������������� ��������
	 * @return ���������������/������������� ��������.
	 */
	public SettingLoanAbstract getEntity() throws BusinessException, BusinessLogicException
	{
		return setting;
	}
}
