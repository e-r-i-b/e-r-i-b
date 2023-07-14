package com.rssl.phizic.operations.ermb;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.ermb.subscribeFee.SubscribeFeeService;
import com.rssl.phizic.business.operations.restrictions.Restriction;
import com.rssl.phizic.config.ermb.SubscribeFeeConstants;
import com.rssl.phizic.operations.config.EditPropertiesOperation;
import org.apache.commons.lang.StringUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * �������� ��� �������������� �������� ��� ��� �������� ����������� ����� ����
 * @author Rtischeva
 * @ created 02.06.14
 * @ $Author$
 * @ $Revision$
 */
public class SubscribeFeeSettingsEditOperation extends EditPropertiesOperation<Restriction>
{
	private final SubscribeFeeService subscribeFeeService = new SubscribeFeeService();

	/**
	 * ��������, ��� ��������� ����������� ���� ����������
	 * @param filePath - ����
	 * @throws BusinessLogicException
	 */
	public void checkFilePath(String filePath) throws BusinessLogicException
	{
		File file = new File(filePath);
		if(!file.exists())
			throw new BusinessLogicException("�� ����������� ������� ���� � ����� ���. ����������, ��������� ���� ��������.");
	}

	@Override
	public void save() throws BusinessException, BusinessLogicException
	{
		String unloadTimes = getEntity().get(SubscribeFeeConstants.FPP_UNLOAD_TIME);

		List<String> unloadTimeList = new ArrayList<String>();
		for (String unloadTime : unloadTimes.split(";"))
		{
			unloadTimeList.add(StringUtils.deleteWhitespace(unloadTime));
		}

		subscribeFeeService.updateFPPTriggers(unloadTimeList);
		super.save();
	}
}