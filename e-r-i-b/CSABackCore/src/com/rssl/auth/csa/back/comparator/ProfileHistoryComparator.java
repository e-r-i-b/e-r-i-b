package com.rssl.auth.csa.back.comparator;

import com.rssl.auth.csa.back.servises.ProfileHistory;

import java.util.Comparator;

/**
 * @author osminin
 * @ created 06.11.13
 * @ $Author$
 * @ $Revision$
 *
 * Компаратор для истории изменений данных пользователя
 * сначала сравниваем по фамилии, потом по ДУЛ, ДР и ТБ
 *
 * !!! Порядок и действия должны полностью совпадать с CSAUserInfoComparator !!!
 */
public class ProfileHistoryComparator extends UserInfoComparatorBase implements Comparator<ProfileHistory>
{
	public int compare(ProfileHistory history, ProfileHistory compHistory)
	{
		if (history == null || compHistory == null)
		{
			int compareToNullResult = compareToNull(history, compHistory);
			if (compareToNullResult != 0)
			{
				return compareToNullResult;
			}
		}

		String infoFIO = getFIOToCompare(history.getFirstname(), history.getSurname(), history.getPatrname());
		String compInfoFIO = getFIOToCompare(compHistory.getFirstname(), compHistory.getSurname(), compHistory.getPatrname());
		int compareFIOResult = infoFIO.compareTo(compInfoFIO);
		if (compareFIOResult != 0)
		{
			return compareFIOResult;
		}

		int comparePassportResult = history.getPassport().compareTo(compHistory.getPassport());
		if (comparePassportResult != 0)
		{
			return comparePassportResult;
		}

		int compareBirthDateResult = history.getBirthdate().compareTo(compHistory.getBirthdate());
		if (compareBirthDateResult != 0)
		{
			return compareBirthDateResult;
		}

		return history.getTb().compareTo(compHistory.getTb());
	}
}
