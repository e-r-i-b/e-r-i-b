package com.rssl.phizic.operations.loanOffer;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.BusinessLogicException;
import com.rssl.phizic.business.loanOffer.SettingLoanAbstract;
import com.rssl.phizic.business.loanOffer.SettingLoanCardOfferLoad;
import com.rssl.phizic.common.types.transmiters.Pair;
import com.rssl.phizic.utils.files.FileHelper;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

/**
 * @author Mescheryakova
 * @ created 11.07.2011
 * @ $Author$
 * @ $Revision$
 */

public class SettingLoanCardOfferLoadOperation  extends AutoActionOperationBase
{
	protected Pair<SettingLoanAbstract, Map<String, ScheduleData>> settings;

	public void init() throws BusinessException, BusinessLogicException
	{
		settings = new Pair<SettingLoanAbstract, Map<String, ScheduleData>>();
		/////////////////////////////////////////////////////////////////////////////////
		// ��������� ������� �������, ��� �������������� ��������
		scheduleMap = new HashMap<String, ScheduleData>(1);
		putExist(LoanCardOfferLoadOperation.class.getName());
		settings.setSecond(scheduleMap);

		/////////////////////////////////////////////////////////////////////////////////
		// ��������� �����.
		// ���� � �� ��� ���� ������ ��������, �� ����� ������� �� (� ��� ����� ���� ������ ���� ������)
		List<SettingLoanCardOfferLoad> dirSetting = simpleService.getAll(SettingLoanCardOfferLoad.class);
		if (dirSetting == null || dirSetting.isEmpty())
			settings.setFirst(new SettingLoanCardOfferLoad());
		else
			settings.setFirst(dirSetting.get(0));
	}

	public void save() throws BusinessException, BusinessLogicException
	{

		/////////////////////////////////////////////////////////////////////////////////
		// ��������� ������� �������, ��� �������������� ��������
		super.save();
		settings.setSecond(scheduleMap);

		/////////////////////////////////////////////////////////////////////////////////
		// ��������� �����.
		SettingLoanCardOfferLoad loanCardOfferSetting = (SettingLoanCardOfferLoad) settings.getFirst();
		String autoFilePath = FileHelper.normalizePath(loanCardOfferSetting.getAutomaticLoadDirectory() + File.separator + loanCardOfferSetting.getAutomaticLoadFileName());
		String manualFilePath = FileHelper.normalizePath(loanCardOfferSetting.getDirectory() + File.separator + loanCardOfferSetting.getFileName());
		if (!(new File(manualFilePath)).exists())
		{
			throw new BusinessLogicException("���� � �������� ��� � ����� ������ �������� ������ �������");
		}
		else if(!(new File(autoFilePath)).exists())
		{
			throw new BusinessLogicException("���� � �������� ��� � ����� �������������� �������� ������ �������");
		}
		simpleService.addOrUpdate(settings.getFirst());
	}

	/**
	 * �������� ���������������/������������� ��������
	 * @return ���������������/������������� ��������.
	 */
	public Pair<SettingLoanAbstract, Map<String, ScheduleData>> getEntity() throws BusinessException, BusinessLogicException
	{
		return settings;
	}
}
