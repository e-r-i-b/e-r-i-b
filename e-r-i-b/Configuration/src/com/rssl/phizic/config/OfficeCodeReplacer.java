package com.rssl.phizic.config;

import com.rssl.phizic.utils.StringHelper;

import java.util.HashSet;
import java.util.Set;
import java.util.StringTokenizer;

/**
 * Используем для получения параметров и замены ОСБ, не содержащихся в справочнике, 
 * для клиентов московского региона
 * @author Egorovaa
 * @ created 20.02.2012
 * @ $Author$
 * @ $Revision$
 */
public class OfficeCodeReplacer extends Config
{
	public static final String WRONG_BRANCHES_KEY = "wrong_office_branches";

	public OfficeCodeReplacer(PropertyReader reader)
	{
		super(reader);
	}

	public void doRefresh() throws ConfigurationException	{}

	/**
	 * В случае, если для данного ТБ (regionID) в справочнике отсутствует ОСБ (branchID),
	 * заменяем его тем, который там точно есть (rightOfficeBranch)
	 * @param regionID - код ТБ, для которого выполяется проверка
	 * @param branchID - код ОСБ, который возможно потребуется заменить
	 * @return если regionID и branchID находятся в файле параметров (коды ОСБ, которых нет в справочнике),
	 * то возвращаем код ОСБ из файла параметров (он содержится в справочнике),
	 * если нет- то возвращаем проверяемый код ОСБ
	 */
	public String replaceCode (String regionID, String branchID)
	{
		if (StringHelper.removeLeadingZeros(regionID).equals(getWrongOfficeRegion()))
		{
			for (String branch: getWrongOfficeBranches())
			{
				if (StringHelper.removeLeadingZeros(branchID).equals(StringHelper.removeLeadingZeros(branch)))
				{
					return getRightOfficeBranch();
				}
			}
		}
		return branchID;
	}

	/**
	 * Получение ТБ, для которого требуется подстановка ОСБ
	 * @return строка с кодом ТБ
	 */
	public String getWrongOfficeRegion()
	{
		return StringHelper.removeLeadingZeros(getProperty("wrong_office_region"));
	}

	/**
	 * Получение номера ОСБ, которым заменяем отсутствующие в справочнике ОСБ
	 * @return строка с кодом ОСБ
	 */
	public String getRightOfficeBranch()
	{
		return getProperty("right_office_branch");
	}

	/**
	 * Список ОСБ, отсутствущих в справочнике
	 * @return перечень кодов ОСБ
	 */
	public Set<String> getWrongOfficeBranches()
	{
		Set<String> wrongOfficeBranches = new HashSet<String>();

		String wrongBranches = getProperty(WRONG_BRANCHES_KEY);
		if (!StringHelper.isEmpty(wrongBranches))
		{
			StringTokenizer tokenizer = new StringTokenizer(wrongBranches, ",");
			while (tokenizer.hasMoreTokens())
			{
				String wrongOfficeBranch = tokenizer.nextToken();
				wrongOfficeBranches.add(wrongOfficeBranch);
			}
		}

		return wrongOfficeBranches;
	}
}
