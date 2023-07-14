package com.rssl.phizic.business.dictionaries.offices.extended;

import com.rssl.phizic.business.departments.Department;
import com.rssl.phizic.business.departments.SendSMSPreferredMethod;
import com.rssl.phizic.gate.dictionaries.officies.Code;
import com.rssl.phizic.utils.StringHelper;

import java.util.Map;

/**
 * @author Omeliyanchuk
 * @ created 19.12.2006
 * @ $Author$
 * @ $Revision$
 */

public class ExtendedDepartment extends Department
{
	private String sbidnt;
	private boolean esbSupported;
	private SendSMSPreferredMethod sendSMSPreferredMethod;
	private DepartmentAutoType automationType;

	public Code getCode()
	{
		return code;
	}

	public void setCode(Code code)
	{
		if (code == null)
			return;
		this.code = new ExtendedCodeImpl(code);
	}

	public void buildCode(Map<String, Object> codeFields)
	{
		ExtendedCodeImpl newCode = new ExtendedCodeImpl();
		if (codeFields.get("branch") != null)
			newCode.setBranch((String) codeFields.get("branch"));
		if (codeFields.get("region") != null)
			newCode.setRegion((String) codeFields.get("region"));
		if (codeFields.get("office") != null)
			newCode.setOffice((String) codeFields.get("office"));
		code = newCode;
	}

	public String getFullName()
	{
		if (code == null)
		{
			return getName();
		}
		if (((ExtendedCodeImpl) code).getOffice() == null)
		{
			if (((ExtendedCodeImpl) code).getBranch() == null)
			{
				// иначе вернется null + название банка
				return getName();
			}
			return ((ExtendedCodeImpl) code).getBranch() + " " + getName();
		}
		else
		{
			return ((ExtendedCodeImpl) code).getBranch() + "/" + ((ExtendedCodeImpl) code).getOffice() + " " + getName();
		}
	}

	public String getSbidnt()
	{
		return sbidnt;
	}

	public void setSbidnt(String sbidnt)
	{
		this.sbidnt = sbidnt;
	}

	/**
	 *
	 * @return поддерживается ли подразделение "Базовый продукт"
	 */
	public boolean isEsbSupported()
	{
		return esbSupported;
	}

	public void setEsbSupported(boolean esbSupported)
	{
		this.esbSupported = esbSupported;
	}

	/**
	 * @return предпочтительный способ отправки СМС
	 */
	public SendSMSPreferredMethod getSendSMSPreferredMethod()
	{
		return sendSMSPreferredMethod;
	}

	public void setSendSMSPreferredMethod(SendSMSPreferredMethod sendSMSPreferredMethod)
	{
		this.sendSMSPreferredMethod = sendSMSPreferredMethod;
	}

	/**
	 * Является ли подразделение ВСП.
	 *
	 * @return ВСП или нет.
	 */
	public boolean getIsVSP()
	{
		ExtendedCodeImpl extendedCode = (ExtendedCodeImpl) code;
		if (extendedCode == null)
			return false;

		return StringHelper.isNotEmpty(extendedCode.getOffice()) &&
				StringHelper.isNotEmpty(extendedCode.getRegion()) &&
				StringHelper.isNotEmpty(extendedCode.getBranch());
	}

	/**
	 * @return Тип автоматизации подразделения
	 */
	public DepartmentAutoType getAutomationType()
	{
		return automationType;
	}

	/**
	 * Установить тип автоматизации подразделения
	 * @param automationType тип автоматизации подразделения
	 */
	public void setAutomationType(DepartmentAutoType automationType)
	{
		this.automationType = automationType;
	}

	public boolean updateFrom(ExtendedDepartment department)
	{
		boolean result = false;

		if (!getCode().equals(department.getCode()))
		{
			setCode(department.getCode());
			result = true;
		}

		// NAME
		if (!StringHelper.equalsNullIgnore(getName(), department.getName()))
		{
			setName(department.getName());
			result = true;
		}

		// POST_ADDRESS
		if (!StringHelper.equalsNullIgnore(getAddress(), department.getAddress()))
		{
			setAddress(department.getAddress());
			result = true;
		}

		// BIC
		if (!StringHelper.equalsNullIgnore(getBIC(), department.getBIC()))
		{
			setBIC(department.getBIC());
			result = true;
		}

		// SBINT
		if (!StringHelper.equalsNullIgnore(getSbidnt(), department.getSbidnt()))
		{
			setSbidnt(department.getSbidnt());
			result = true;
		}

		// PHONE
		if (!StringHelper.equalsNullIgnore(getTelephone(), department.getTelephone()))
		{
			setTelephone(department.getTelephone());
			result = true;
		}

		// IS_OPEN_IMA_OFFICE
		if (isOpenIMAOffice() != department.isOpenIMAOffice())
		{
			setOpenIMAOffice(department.isOpenIMAOffice());
			result = true;
		}

		return result;
	}
}
