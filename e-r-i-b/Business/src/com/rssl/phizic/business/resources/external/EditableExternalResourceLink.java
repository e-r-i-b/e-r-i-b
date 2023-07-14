package com.rssl.phizic.business.resources.external;

/**
 * @author mihaylov
 * @ created 26.07.2010
 * @ $Author$
 * @ $Revision$
 */

/**
 * Редактируемая ссылка на внешний объект (счет, карта, кредит и т.д.)
 */
public abstract class EditableExternalResourceLink extends ExternalResourceLinkBase
{
	private String name; // псевдоним объекта в системе, задается пользователем
	private Boolean isShowInMain; // признак отображения объекта на главной странице
	private Boolean isShowInSystem; // признак отображения объекта в системе
	private Boolean isShowOperations; // признак отображения/скрытия операций объекта
	private boolean isShowInATM; // признак показа в устройствах самообслуживания
	private Integer positionNumber;   // порядковый номер продукта в списке

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
