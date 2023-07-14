package com.rssl.phizic.business.resources.external;

/**
 * @author mihaylov
 * @ created 26.07.2010
 * @ $Author$
 * @ $Revision$
 */

/**
 * ������������� ������ �� ������� ������ (����, �����, ������ � �.�.)
 */
public abstract class EditableExternalResourceLink extends ExternalResourceLinkBase
{
	private String name; // ��������� ������� � �������, �������� �������������
	private Boolean isShowInMain; // ������� ����������� ������� �� ������� ��������
	private Boolean isShowInSystem; // ������� ����������� ������� � �������
	private Boolean isShowOperations; // ������� �����������/������� �������� �������
	private boolean isShowInATM; // ������� ������ � ����������� ����������������
	private Integer positionNumber;   // ���������� ����� �������� � ������

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public Boolean getShowInMain()
	{
		return isShowInMain;
	}

	public void setShowInMain(Boolean showInMain)
	{
		isShowInMain = showInMain;
	}

	public Boolean getShowInSystem()
	{
		return isShowInSystem;
	}

	public void setShowInSystem(Boolean showInSystem)
	{
		isShowInSystem = showInSystem;
	}

	public Boolean getShowOperations()
	{
		return isShowOperations;
	}

	public void setShowOperations(Boolean showOperations)
	{
		isShowOperations = showOperations;
	}

	public Boolean isShowInATM()
	{
		return isShowInATM;
	}

	public void setShowInATM(boolean showInATM)
	{
		isShowInATM = showInATM;
	}

	public boolean getShowInATM()
	{
		return isShowInATM;
	}

	public Integer getPositionNumber()
	{
		return positionNumber;
	}

	public void setPositionNumber(Integer positionNumber)
	{
		this.positionNumber = positionNumber;
	}
}
