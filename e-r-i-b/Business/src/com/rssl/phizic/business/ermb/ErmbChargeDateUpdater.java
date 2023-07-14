package com.rssl.phizic.business.ermb;

import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.common.types.Period;
import com.rssl.phizic.gate.mobilebank.ClientTariffInfo;
import com.rssl.phizic.utils.DateHelper;

import java.util.Calendar;

/**
 * ���������� ���������� � ���� �������� ����� �� ������ ��������� ����
 * @author Puzikov
 * @ created 02.06.14
 * @ $Author$
 * @ $Revision$
 */

public class ErmbChargeDateUpdater
{
	/**
	 * ���������������� ���������� � ������ �������� ����������� �����
	 * (����������� ������� ��� ����� ������)
	 * @param profile ���������������� �������
	 */
	public void initChargeDate(ErmbProfileImpl profile) throws BusinessException
	{
		Calendar currentDate = DateHelper.getCurrentDate();
		profile.setChargeNextDate(currentDate);
		profile.setChargeDayOfMonth(currentDate.get(Calendar.DAY_OF_MONTH));
	}

	/**
	 * �������� ���������� � �������� ����������� �����
	 * ������ ���������� �� ��������� ���� �������
	 * ���������� ���������� ��� ��������� ��������� WAY � �������� ��� ������� ����������� ������
	 * @param profile ����������� �������
	 * @return ������ �� ������� ��������� ��������
	 */
	public Period shiftChargeDate(ErmbProfileImpl profile) throws BusinessException
	{
		return shiftChargeDate(profile, profile.getChargeNextDate(), profile.getChargeDayOfMonth());
	}

	/**
	 * ���������� ��������� �� �������� ����������� ����� �������� ���
	 * ���� ���� �� ��������� ��� ��������� ��� ���������, �� ��������������� ����������� ������
	 * @param profile ����������� �������
	 * @param mbkInfo ���������� �� ��� �� ������� ��������
	 */
	public void setChargeDateFromMbk(ErmbProfileImpl profile, ClientTariffInfo mbkInfo) throws BusinessException
	{
		Calendar mbkNextPaidPeriod = mbkInfo.getNextPaidPeriod();
		int chargeDayOfMonth = mbkInfo.getPayDate();

		if (mbkNextPaidPeriod == null)
		{
			initChargeDate(profile);
		}
		else
		{
			Calendar chargeNextDate = (Calendar) mbkNextPaidPeriod.clone();
			DateHelper.setDayOfMonth(chargeNextDate, chargeDayOfMonth);

			if (chargeNextDate.before(DateHelper.getCurrentDate()))
			{
				initChargeDate(profile);
			}
			else
			{
				profile.setChargeNextDate(chargeNextDate);
				profile.setChargeDayOfMonth(chargeDayOfMonth);
			}
		}
	}

	/**
	 * �������� ���������� � �������� ����������� ����� ��� ������������� ������� �� ��������
	 * ���� ������� ��������������� ������� ���� (���� �������������)
	 * @param profile ����������� �������
	 * @return ������ �� ������� ��������� ��������
	 */
	public Period resetChargeDate(ErmbProfileImpl profile) throws BusinessException
	{
		Calendar currentDate = DateHelper.getCurrentDate();
		int currentDay = currentDate.get(Calendar.DAY_OF_MONTH);
		return shiftChargeDate(profile, currentDate, currentDay);
	}

	private Period shiftChargeDate(ErmbProfileImpl profile, Calendar chargeDate, Integer chargeDayOfMonth) throws BusinessException
	{
		ErmbTariff tariff = profile.getTarif();
		int chargePeriod = tariff.getChargePeriod();

		Calendar nextChargeDate = DateHelper.addMonths(chargeDate, chargePeriod);
		DateHelper.setDayOfMonth(nextChargeDate, chargeDayOfMonth);

		profile.setChargeNextDate(nextChargeDate);
		profile.setChargeDayOfMonth(chargeDayOfMonth);

		return new Period(chargeDate, nextChargeDate);
	}
}
