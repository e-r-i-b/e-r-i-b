package com.rssl.auth.csa.back.comparator;

import com.rssl.auth.csa.back.CSAUserInfo;
import com.rssl.auth.csa.back.Utils;

import java.util.Comparator;

/**
 * @author osminin
 * @ created 06.11.13
 * @ $Author$
 * @ $Revision$
 *
 * Компаратор для информации о пользователе
 * сначала сравниваем по фамилии, потом по ДУЛ, ДР и ТБ
 *
 * !!! Порядок и действия должны полностью совпадать с ProfileHistoryComparator !!!
 */
public class CSAUserInfoComparator extends UserInfoComparatorBase implements Comparator<CSAUserInfo>
{
	public int compare(CSAUserInfo info, CSAUserInfo compInfo)
	{
		if (info == null || compInfo == null)
		{
			int compareToNullResult = compareToNull(info, compInfo);
			if (compareToNullResult != 0)
			{
				return compareToNullResult;
			}
		}

		String infoFIO = getFIOToCompare(info.getFirstname(), info.getSurname(), info.getPatrname());
		String compInfoFIO = getFIOToCompare(compInfo.getFirstname(), compInfo.getSurname(), compInfo.getPatrname());
		int compareFIOResult = infoFIO.compareTo(compInfoFIO);
		if (compareFIOResult != 0)
		{
			return compareFIOResult;
		}

		int comparePassportResult = info.getPassport().compareTo(compInfo.getPassport());
		if (comparePassportResult != 0)
		{
			return comparePassportResult;
		}

		int compareBirthDateResult = info.getBirthdate().compareTo(compInfo.getBirthdate());
		if (compareBirthDateResult != 0)
		{
			return compareBirthDateResult;
		}

		String infoTb = Utils.getTBByCbCode(info.getCbCode());
		String compInfoTb = Utils.getTBByCbCode(compInfo.getCbCode());

		return infoTb.compareTo(compInfoTb);
	}
}
