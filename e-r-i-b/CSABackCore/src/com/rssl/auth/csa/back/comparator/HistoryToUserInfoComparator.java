package com.rssl.auth.csa.back.comparator;

import com.rssl.auth.csa.back.CSAUserInfo;
import com.rssl.auth.csa.back.Utils;
import com.rssl.auth.csa.back.servises.ProfileHistory;
import com.rssl.phizic.utils.DateHelper;

import java.util.Calendar;

/**
 * @author osminin
 * @ created 06.11.13
 * @ $Author$
 * @ $Revision$
 *
 * Компаратор для сравнения данных истории изменений и информации о пользователе
 */
public class HistoryToUserInfoComparator extends UserInfoComparatorBase
{
	/**
	 * Сравнить данные истории и информации о пользователе
	 * @param info информация о пользователе
	 * @param history информации из истории
	 * @return 0 если равны, -1 или 1 иначе
	 */
	public int compare(CSAUserInfo info, ProfileHistory history)
	{
		String infoFIO = getFIOToCompare(info.getFirstname(), info.getSurname(), info.getPatrname());
		String compInfoFIO = getFIOToCompare(history.getFirstname(), history.getSurname(), history.getPatrname());
		int compareFIOResult = infoFIO.compareTo(compInfoFIO);
		if (compareFIOResult != 0)
		{
			return compareFIOResult;
		}

		int comparePassportResult = info.getPassport().compareTo(history.getPassport());
		if (comparePassportResult != 0)
		{
			return comparePassportResult;
		}

		Calendar infoBirthDate = DateHelper.clearTime(info.getBirthdate());
		Calendar historyBirthDate = DateHelper.clearTime(history.getBirthdate());
		int compareBirthDateResult = infoBirthDate.compareTo(historyBirthDate);
		if (compareBirthDateResult != 0)
		{
			return compareBirthDateResult;
		}

		String infoTb = Utils.getTBByCbCode(info.getCbCode());

		return infoTb.compareTo(history.getTb());
	}
}
