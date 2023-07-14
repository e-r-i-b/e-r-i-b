package com.rssl.phizic.business.ermb.migration.onthefly.fpp;

import com.rssl.phizic.common.types.annotation.PlainOldJavaObject;
import com.rssl.phizic.utils.jaxb.BigDecimalAdapter;

import java.math.BigDecimal;
import java.util.List;
import javax.xml.bind.annotation.*;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

@XmlRootElement(name="config")
@XmlType(name = "Config")
@XmlAccessorType(XmlAccessType.NONE)
@PlainOldJavaObject
class FPPMigrationConfigBean
{
	@XmlElementWrapper(name = "pilot-zone")
	@XmlElement(name = "department")
	private List<DepartmentIdentity> departments;

	@XmlElement(name = "template-amount", required = true)
	@XmlJavaTypeAdapter(BigDecimalAdapter.class)
	private BigDecimal templateAmount;

	List<DepartmentIdentity> getDepartments()
	{
		//noinspection ReturnOfCollectionOrArrayField
		return departments;
	}

	void setDepartments(List<DepartmentIdentity> departments)
	{
		//noinspection AssignmentToCollectionOrArrayFieldFromParameter
		this.departments = departments;
	}

	BigDecimal getTemplateAmount()
	{
		return templateAmount;
	}

	void setTemplateAmount(BigDecimal templateAmount)
	{
		this.templateAmount = templateAmount;
	}
}
