package com.rssl.phizic.business.personalOffer;


import java.util.Calendar;
import java.util.List;
import java.util.Set;

/**
 * ���������� � �������������� �����������
 * @author lukina
 * @ created 19.12.2013
 * @ $Author$
 * @ $Revision$
 */
public class PersonalOfferNotification
{
	private Long id; // �������������.
	private PersonalOfferState state = PersonalOfferState.ACTIVE; //���������.
	private String name; //��������.
	private Calendar periodFrom; //���� ������ �����������.
	private Calendar periodTo; //���� ��������� �����������.
	private Set<String> departments; //������������ ��� ������� ����������.
	private String title; //��������� �������.
	private String text;  //����� �� �������.
	private List<PersonalOfferNotificationButton> buttons; //������ �� �������.
	private List<PersonalOfferNotificationArea> areas; //������� �������.
	private Long imageId; //�����������.
	private Long showTime = 5L; //����� ����� ������� ���������� ������� ������.
	private Long orderIndex = 1L; //������� ����������� ��������.
	private PersonalOfferProduct productType = PersonalOfferProduct.LOAN;
	private PersonalOfferDisplayFrequency displayFrequency = PersonalOfferDisplayFrequency.ONE;
	private Long displayFrequencyDay;

	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public PersonalOfferState getState()
	{
		return state;
	}

	public void setState(PersonalOfferState state)
	{
		this.state = state;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public Calendar getPeriodFrom()
	{
		return periodFrom;
	}

	public void setPeriodFrom(Calendar periodFrom)
	{
		this.periodFrom = periodFrom;
	}

	public Calendar getPeriodTo()
	{
		return periodTo;
	}

	public void setPeriodTo(Calendar periodTo)
	{
		this.periodTo = periodTo;
	}

	public Set<String> getDepartments()
	{
		return departments;
	}

	public void setDepartments(Set<String> departments)
	{
		this.departments = departments;
	}

	public String getTitle()
	{
		return title;
	}

	public void setTitle(String title)
	{
		this.title = title;
	}

	public String getText()
	{
		return text;
	}

	public void setText(String text)
	{
		this.text = text;
	}

	public List<PersonalOfferNotificationButton> getButtons()
	{
		return buttons;
	}

	public void setButtons(List<PersonalOfferNotificationButton> buttons)
	{
		this.buttons = buttons;
	}

	public List<PersonalOfferNotificationArea> getAreas()
	{
		return areas;
	}

	public void setAreas(List<PersonalOfferNotificationArea> areas)
	{
		this.areas = areas;
	}

	public Long getImageId()
	{
		return imageId;
	}

	public void setImageId(Long imageId)
	{
		this.imageId = imageId;
	}

	public Long getShowTime()
	{
		return showTime;
	}

	public void setShowTime(Long showTime)
	{
		this.showTime = showTime;
	}

	public Long getOrderIndex()
	{
		return orderIndex;
	}

	public void setOrderIndex(Long orderIndex)
	{
		this.orderIndex = orderIndex;
	}

	public PersonalOfferProduct getProductType()
	{
		return productType;
	}

	public void setProductType(PersonalOfferProduct productType)
	{
		this.productType = productType;
	}

	public PersonalOfferDisplayFrequency getDisplayFrequency()
	{
		return displayFrequency;
	}

	public void setDisplayFrequency(PersonalOfferDisplayFrequency displayFrequency)
	{
		this.displayFrequency = displayFrequency;
	}

	public Long getDisplayFrequencyDay()
	{
		return displayFrequencyDay;
	}

	public void setDisplayFrequencyDay(Long displayFrequencyDay)
	{
		this.displayFrequencyDay = displayFrequencyDay;
	}
}
