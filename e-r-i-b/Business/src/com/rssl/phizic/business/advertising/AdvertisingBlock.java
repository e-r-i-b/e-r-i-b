 package com.rssl.phizic.business.advertising;

import com.rssl.phizic.business.dictionaries.productRequirements.ProductRequirement;
import com.rssl.phizic.business.dictionaries.productRequirements.AccTypesRequirement;
import com.rssl.phizic.business.dictionaries.synchronization.MultiBlockDictionaryRecordBase;
import com.rssl.phizic.business.image.Image;

import java.util.Calendar;
import java.util.List;
import java.util.Set;
import java.util.HashSet;

/**
 * —ущность "Ѕаннер(рекламный блок)"
 * @author komarov
 * @ created 19.12.2011
 * @ $Author$
 * @ $Revision$
 */

public class AdvertisingBlock extends MultiBlockDictionaryRecordBase
{
	private Long id; // »дентификатор.
	private AdvertisingState state = AdvertisingState.ACTIVE; //—осто€ние.
    private AdvertisingAvailability availability = AdvertisingAvailability.FULL; //ƒоступность (основное приложение, мобильный API).
	private String name; //Ќазвание.
	private Calendar periodFrom; //ƒата начала отображени€.
	private Calendar periodTo; //ƒата окончани€ отображени€.
	private Set<String> departments; //ƒепартаменты дл€ которых отображаем.
	private Set<ProductRequirement> requirements = new HashSet<ProductRequirement>(); //“ребовани€ к продуктам клиента.
	private Set<AccTypesRequirement> reqAccTypeRequirements = new HashSet<AccTypesRequirement>(); //“ребовани€ к типам вклада.
	private String title; //«аголовок баннера.
	private String text;  //“екст на баннере.
	private List<AdvertisingButton> buttons; // нопки на баннере.
	private List<AdvertisingArea> areas; //ќбласти баннера.
	private Long showTime = Constants.DEFAULT_SHOW_TIME; //¬рем€ через которое необходимо сменить баннер.
	private Long orderIndex = 1L; //ѕор€док отображени€ баннеров.
	private Image image;  //»зображение.

	/**
	 * @return идентификатор
	 */
	public Long getId()
	{
		return id;
	}

	/**
	 * @param id идентификатор
	 */
	public void setId(Long id)
	{
		this.id = id;
	}

	/**
	 * @return состо€ние
	 */
	public AdvertisingState getState()
	{
		return state;
	}

	/**
	 * @param state состо€ние
	 */
	public void setState(AdvertisingState state)
	{
		this.state = state;
	}

	/**
	 * @return название баннера
	 */
	public String getName()
	{
		return name;
	}

	/**
	 * @param name название баннера
	 */
	public void setName(String name)
	{
		this.name = name;
	}

	/**
	 * @return дата начала отображени€
	 */
	public Calendar getPeriodFrom()
	{
		return periodFrom;
	}

	/**
	 * @param periodFrom дата начала отображени€
	 */
	public void setPeriodFrom(Calendar periodFrom)
	{
		this.periodFrom = periodFrom;
	}

	/**
	 * @return дата окончани€ отображени€
	 */
	public Calendar getPeriodTo()
	{
		return periodTo;
	}

	/**
	 * @param periodTo дата окончани€ отображени€
	 */
	public void setPeriodTo(Calendar periodTo)
	{
		this.periodTo = periodTo;
	}

	/**
	 * @return номера тербанков
	 */
	public Set<String> getDepartments()
	{
		//noinspection ReturnOfCollectionOrArrayField
		return departments;
	}

	/**
	 * @param departments номера тербанков
	 */
	public void setDepartments(Set<String> departments)
	{
		//noinspection AssignmentToCollectionOrArrayFieldFromParameter
		this.departments = departments;
	}

	/**
	 * @return требовани€ к продуктам клиента
	 */
	public Set<ProductRequirement> getRequirements()
	{
		//noinspection ReturnOfCollectionOrArrayField
		return requirements;
	}

	/**
	 * @param requirements требовани€ к продуктам клиента
	 */
	public void setRequirements(Set<ProductRequirement> requirements)
	{
		//noinspection AssignmentToCollectionOrArrayFieldFromParameter
		this.requirements = requirements;
	}

	/**
	 * @return требовани€ к типам вклада
	 */
	public Set<AccTypesRequirement> getReqAccTypes()
	{
		//noinspection ReturnOfCollectionOrArrayField
		return reqAccTypeRequirements;
	}

	/**
	 * @param reqAccTypeRequirements требовани€ к типам вклада
	 */
	public void setReqAccTypes(Set<AccTypesRequirement> reqAccTypeRequirements)
	{
		//noinspection AssignmentToCollectionOrArrayFieldFromParameter
		this.reqAccTypeRequirements = reqAccTypeRequirements;
	}

	/**
	 * @return заголовок баннера
	 */
	public String getTitle()
	{
		return title;
	}

	/**
	 * @param title заголовок баннера
	 */
	public void setTitle(String title)
	{
		this.title = title;
	}

	/**
	 * @return текст на баннере
	 */
	public String getText()
	{
		return text;
	}

	/**
	 * @param text текст на баннере
	 */
	public void setText(String text)
	{
		this.text = text;
	}

	/**
	 * @return кнопки
	 */
	public List<AdvertisingButton> getButtons()
	{
		//noinspection ReturnOfCollectionOrArrayField
		return buttons;
	}

	/**
	 * @param buttons кнопки
	 */
	public void setButtons(List<AdvertisingButton> buttons)
	{
		//noinspection AssignmentToCollectionOrArrayFieldFromParameter
		this.buttons = buttons;
	}

	/**
	 * @return области баннера
	 */
	public List<AdvertisingArea> getAreas()
	{
		//noinspection ReturnOfCollectionOrArrayField
		return areas;
	}

	/**
	 * @param areas области баннера
	 */
	public void setAreas(List<AdvertisingArea> areas)
	{
		//noinspection AssignmentToCollectionOrArrayFieldFromParameter
		this.areas = areas;
	}

	/**
	 * @return врем€ отображени€ баннера
	 */
	public Long getShowTime()
	{
		return showTime;
	}

	/**
	 * @param showTime врем€ отображени€ баннера
	 */
	public void setShowTime(Long showTime)
	{
		this.showTime = showTime;
	}

	/**
	 * @return пор€док отображени€ баннеров
	 */
	public Long getOrderIndex()
	{
		return orderIndex;
	}

	/**
	 * @param orderIndex пор€док отображени€ баннеров
	 */
	public void setOrderIndex(Long orderIndex)
	{
		this.orderIndex = orderIndex;
	}

	/**
	 * @return доступность баннера в основном приложении/mapi
	 */
    public AdvertisingAvailability getAvailability()
    {
        return availability;
    }

	/**
	 * @param availability доступность баннера в основном приложении/mapi
	 */
    public void setAvailability(AdvertisingAvailability availability)
    {
        this.availability = availability;
    }

	/**
	 * @return получить изображение
	 */
	public Image getImage()
	{
		return image;
	}

	/**
	 * @param image - изображение
	 */
	public void setImage(Image image)
	{
		this.image = image;
	}
}
