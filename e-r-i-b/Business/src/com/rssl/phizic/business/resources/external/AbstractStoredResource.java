package com.rssl.phizic.business.resources.external;

import com.rssl.phizgate.common.services.types.CodeImpl;
import com.rssl.phizic.business.BusinessException;
import com.rssl.phizic.business.departments.Department;
import com.rssl.phizic.business.departments.DepartmentService;
import com.rssl.phizic.business.dictionaries.offices.extended.ExtendedCodeImpl;
import com.rssl.phizic.business.dictionaries.offices.extended.ExtendedDepartment;
import com.rssl.phizic.business.persons.clients.ClientImpl;
import com.rssl.phizic.config.ConfigFactory;
import com.rssl.phizic.config.OfficeCodeReplacer;
import com.rssl.phizic.gate.clients.Client;
import com.rssl.phizic.gate.dictionaries.officies.Code;
import com.rssl.phizic.gate.dictionaries.officies.Office;
import com.rssl.phizic.logging.Constants;
import com.rssl.phizic.logging.system.Log;
import com.rssl.phizic.logging.system.PhizICLogFactory;

import java.util.Calendar;

/**
 *
 * ������� ����������������� ����� ��� ��������� �������� ���������� �� ���������
 *
 * User: Balovtsev
 * Date: 26.10.2012
 * Time: 17:54:22
 *
 */
public abstract class AbstractStoredResource<StoredProduct, StoredProductInfo>
{
	protected static final DepartmentService departmentService = new DepartmentService();
	protected static final Log log = PhizICLogFactory.getLog(Constants.LOG_MODULE_CORE);

	protected static final Client MOCK_CLIENT = new ClientImpl();

	private Long                     storedId;
	private Calendar                 entityUpdateTime;
	private Calendar                 entityInfoUpdateTime;
	private ExternalResourceLink     resourceLink;
	private Long                     departmentId;

	private String                   officeTB;
	private String                   officeOSB;
	private String                   officeVSP;

	public Long getStoredId()
	{
		return storedId;
	}

	public void setStoredId(Long storedId)
	{
		this.storedId = storedId;
	}

	public String getOfficeTB()
	{
		return officeTB;
	}

	public void setOfficeTB(String officeTB)
	{
		this.officeTB = officeTB;
	}

	public String getOfficeOSB()
	{
		return officeOSB;
	}

	public void setOfficeOSB(String officeOSB)
	{
		this.officeOSB = officeOSB;
	}

	public String getOfficeVSP()
	{
		return officeVSP;
	}

	public void setOfficeVSP(String officeVSP)
	{
		this.officeVSP = officeVSP;
	}

	/**
	 *
	 * �������� ������ �� �������
	 *
	 * @return ExternalResourceLink
	 */
	public ExternalResourceLink getResourceLink()
	{
		return resourceLink;
	}

	public Long getDepartmentId()
	{
		return departmentId;
	}

	public void setDepartmentId(Long departmentId)
	{
		this.departmentId = departmentId;
	}

	public Office getOffice()
	{
		ExtendedDepartment department = new ExtendedDepartment();
		department.setCode(new ExtendedCodeImpl(officeTB, officeOSB, officeVSP));

		if (departmentId == null)
		{
			return department;
		}

		try
		{
			return departmentService.findById(departmentId);
		}
		catch (BusinessException e)
		{
			log.error("������ ��� ��������� ������������� �� ��������������", e);
			return department;
		}
	}

	public Code getOriginalTbCode()
	{
		return new CodeImpl(officeTB, officeOSB, officeVSP);
	}

	/**
	 *
	 * ���������� ������ �� �������
	 *
	 * @param resourceLink ������ �� �������
	 */
	public void setResourceLink(ExternalResourceLink resourceLink)
	{
		this.resourceLink = resourceLink;
	}
	
	/**
	 *
	 * �������� �����, ����� � ��������� ��� ��� ��������� ���������� �� ��������
	 *
	 * @return Calendar ��������� ���������� ���������� ��������
	 */
	public Calendar getEntityUpdateTime()
	{
		return entityUpdateTime;
	}

	/**
	 *
	 * ���������� �����, ����� � ��������� ��� ���� ��������� ���������� �� ��������
	 *
	 * @param entityUpdateTime ����� ����������
	 */
	public void setEntityUpdateTime(Calendar entityUpdateTime)
	{
		this.entityUpdateTime = entityUpdateTime;
	}

	/**
	 *
	 * �������� �����, ����� � ��������� ��� ���� ��������� �������������� ���������� �� ��������
	 *
	 * @return Calendar ��������� ���������� �������������� ���������� ��������
	 */
	public Calendar getEntityInfoUpdateTime()
	{
		return entityInfoUpdateTime;
	}

	/**
	 *
	 * ���������� �����, ����� � ��������� ��� ��� ��������� �������������� ���������� �� ��������
	 *
	 * @param entityInfoUpdateTime ����� ����������
	 */
	public void setEntityInfoUpdateTime(Calendar entityInfoUpdateTime)
	{
		this.entityInfoUpdateTime = entityInfoUpdateTime;
	}

	/**
	 *
	 * ����� ������ ������������� ���������� ����� �������� ���������� �� ��������
	 *
	 * @param storedProduct �������� ���������� �� ��������
	 */
	public abstract void update(StoredProduct storedProduct);

	/**
	 *
	 * ����� ������ ������������� ���������� ����� �������� �������������� ���������� �� ��������
	 *
	 * @param updateSourceInfo �������� �������������� ���������� �� ��������
	 */
	public void updateInfo(StoredProductInfo updateSourceInfo) {}

	/**
	 * ����� �� �������� ����������� � �� �������
	 * @param storedProduct �������� ���������� �� ��������
	 * @return true - ���� ���������
	 */
	public abstract boolean needUpdate(StoredProduct storedProduct);

	/**
	 * ����� �� �������� ����������� � �� �������
	 * @param storedProductInfo �������� �������������� ���������� �� ��������
	 * @return true - ���� ���������
	 */
	public boolean needUpdateInfo(StoredProductInfo storedProductInfo)
	{
		return false;
	}


	protected void updateOffice(Office office)
	{
		ExtendedCodeImpl officeCode = new ExtendedCodeImpl(office.getCode());
		setOfficeTB(officeCode.getRegion());
		setOfficeOSB(officeCode.getBranch());
		setOfficeVSP(officeCode.getOffice());

		// �������� ���
		OfficeCodeReplacer officeCodeReplacer = ConfigFactory.getConfig(OfficeCodeReplacer.class);
		officeCode.setBranch(officeCodeReplacer.replaceCode(officeCode.getRegion(), officeCode.getBranch()));

		try
		{
			Department department = departmentService.findByCode(officeCode);
			if (department == null)
			{
				log.info("������ ��� ���������� ������� ��������. �� �������� ���������� � ������������� � ����� - " + officeCode.getId());
				setDepartmentId(null);
			}
			else
			{
				setDepartmentId(department.getId());
			}
		}
		catch (BusinessException e)
		{
			log.info("������ ��� ���������� ������� ��������. �� �������� ���������� � ������������� � ����� - " + officeCode.getId(), e);
		}
	}
}
