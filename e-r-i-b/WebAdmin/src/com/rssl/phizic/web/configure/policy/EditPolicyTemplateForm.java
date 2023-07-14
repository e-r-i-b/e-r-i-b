package com.rssl.phizic.web.configure.policy;

import com.rssl.phizic.business.departments.Department;
import com.rssl.phizic.operations.access.AssignAccessHelper;
import com.rssl.phizic.web.access.AssignAccessSubForm;
import com.rssl.phizic.web.common.EditFormBase;

import java.util.List;
import java.util.Map;
import java.util.HashMap;

/**
 * @author Evgrafov
 * @ created 18.04.2007
 * @ $Author: tisov $
 * @ $Revision: 72752 $
 */

@SuppressWarnings({"JavaDoc"})
public class EditPolicyTemplateForm extends EditFormBase
{
	private AssignAccessSubForm simple = new AssignAccessSubForm();
	private AssignAccessSubForm secure = new AssignAccessSubForm();

	private List<Department> terbanks;
	private List<AssignAccessHelper> helpers;
	private Map<String,String> schemesSBOL = new HashMap<String, String>();
	private Map<String,String> schemesUDBO = new HashMap<String, String>();

	private String schemeCARD;
	private String schemeCARD_TEMPORARY;
	private String schemeUDBO_TEMPORARY;
	private String schemeGUEST;

	public AssignAccessSubForm getSecureAccess()
	{
		return secure;
	}

	public AssignAccessSubForm getSimpleAccess()
	{
		return simple;
	}

	/**
	 * @return ������ �������� �������������
	 */
	public List<Department> getTerbanks()
	{
		return terbanks;
	}

	/**
	 * ���������� ������ ������� �������������
	 * @param terbanks ������ �������� �������������
	 */
	public void setTerbanks(List<Department> terbanks)
	{
		this.terbanks = terbanks;
	}

	/**
	 * @return ������ �������� ��� ��������� ����� ����
	 */
	public List<AssignAccessHelper> getHelpers()
	{
		return helpers;
	}

	/**
	 * ���������� ������ �������� ��� ��������� ����� ����
	 * @param helpers ������ ��������
	 */
	public void setHelpers(List<AssignAccessHelper> helpers)
	{
		this.helpers = helpers;
	}

	/**
	 * @return ���<id ������������, id ����� ���� �������> ��� ����-��������
	 */
	public Map<String, String> getSchemesSBOL()
	{
		return schemesSBOL;
	}

	/**
	 * ���������� ����� ������� ��� ����-��������
	 * @param schemesSBOL ���<id ������������, id ����� ���� �������>
	 */
	public void setSchemesSBOL(Map<String, String> schemesSBOL)
	{
		this.schemesSBOL = schemesSBOL;
	}

	/**
	 * @return ���<id ������������, id ����� ���� �������> ��� ����-��������
	 */
	public Map<String, String> getSchemesUDBO()
	{
		return schemesUDBO;
	}

	/**
	 * ���������� ����� ������� ��� ����-��������
	 * @param schemesUDBO ���<id ������������, id ����� ���� �������>
	 */
	public void setSchemesUDBO(Map<String, String> schemesUDBO)
	{
		this.schemesUDBO = schemesUDBO;
	}

	/**
	 * @return id ����� ���� ������� ��� ��������� ��������
	 */
	public String getSchemeCARD()
	{
		return schemeCARD;
	}

	/**
	 * ���������� ����� ���� ������� ��� ��������� ��������
	 * @param schemeCARD
	 */
	public void setSchemeCARD(String schemeCARD)
	{
		this.schemeCARD = schemeCARD;
	}

	/**
	 * @return ������������� ����� ���� ������� ��������� �������� �� ���������� �����
	 */
	public String getSchemeCARD_TEMPORARY()
	{
		return schemeCARD_TEMPORARY;
	}

	/**
	 * ������ ������������� ����� ���� ������� ��������� �������� �� ���������� �����
	 * @param schemeCARD_TEMPORARY �������������
	 */
	public void setSchemeCARD_TEMPORARY(String schemeCARD_TEMPORARY)
	{
		this.schemeCARD_TEMPORARY = schemeCARD_TEMPORARY;
	}

	/**
	 * @return ������������� ����� ���� ������� ���� �������� �� ���������� �����
	 */
	public String getSchemeUDBO_TEMPORARY()
	{
		return schemeUDBO_TEMPORARY;
	}

	/**
	 * ������ ������������� ����� ���� ������� ���� �������� �� ���������� �����
	 * @param schemeUDBO_TEMPORARY �������������
	 */
	public void setSchemeUDBO_TEMPORARY(String schemeUDBO_TEMPORARY)
	{
		this.schemeUDBO_TEMPORARY = schemeUDBO_TEMPORARY;
	}

	/**
	 * @return ������������� ����� ���� ��������� ����� ��� ���������� ������ �� ������, ��������� �����
	 */
	public String getSchemeGUEST()
	{
		return schemeGUEST;
	}

	/**
	 * ������ ������������� ����� ���� ��������� ����� ��� ���������� ������ �� ������, ��������� �����
	 * @param schemeGUEST �������������
	 */
	public void setSchemeGUEST(String schemeGUEST)
	{
		this.schemeGUEST = schemeGUEST;
	}
}