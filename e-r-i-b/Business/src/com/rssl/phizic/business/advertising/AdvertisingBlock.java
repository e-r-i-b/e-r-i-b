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
 * �������� "������(��������� ����)"
 * @author komarov
 * @ created 19.12.2011
 * @ $Author$
 * @ $Revision$
 */

public class AdvertisingBlock extends MultiBlockDictionaryRecordBase
{
	private Long id; // �������������.
	private AdvertisingState state = AdvertisingState.ACTIVE; //���������.
    private AdvertisingAvailability availability = AdvertisingAvailability.FULL; //����������� (�������� ����������, ��������� API).
	private String name; //��������.
	private Calendar periodFrom; //���� ������ �����������.
	private Calendar periodTo; //���� ��������� �����������.
	private Set<String> departments; //������������ ��� ������� ����������.
	private Set<ProductRequirement> requirements = new HashSet<ProductRequirement>(); //���������� � ��������� �������.
	private Set<AccTypesRequirement> reqAccTypeRequirements = new HashSet<AccTypesRequirement>(); //���������� � ����� ������.
	private String title; //��������� �������.
	private String text;  //����� �� �������.
	private List<AdvertisingButton> buttons; //������ �� �������.
	private List<AdvertisingArea> areas; //������� �������.
	private Long showTime = Constants.DEFAULT_SHOW_TIME; //����� ����� ������� ���������� ������� ������.
	private Long orderIndex = 1L; //������� ����������� ��������.
	private Image image;  //�����������.

	/**
	 * @return �������������
	 */
	public Long getId()
	{
		return id;
	}

	/**
	 * @param id �������������
	 */
	public void setId(Long id)
	{
		this.id = id;
	}

	/**
	 * @return ���������
	 */
	public AdvertisingState getState()
	{
		return state;
	}

	/**
	 * @param state ���������
	 */
	public void setState(AdvertisingState state)
	{
		this.state = state;
	}

	/**
	 * @return �������� �������
	 */
	public String getName()
	{
		return name;
	}

	/**
	 * @param name �������� �������
	 */
	public void setName(String name)
	{
		this.name = name;
	}

	/**
	 * @return ���� ������ �����������
	 */
	public Calendar getPeriodFrom()
	{
		return periodFrom;
	}

	/**
	 * @param periodFrom ���� ������ �����������
	 */
	public void setPeriodFrom(Calendar periodFrom)
	{
		this.periodFrom = periodFrom;
	}

	/**
	 * @return ���� ��������� �����������
	 */
	public Calendar getPeriodTo()
	{
		return periodTo;
	}

	/**
	 * @param periodTo ���� ��������� �����������
	 */
	public void setPeriodTo(Calendar periodTo)
	{
		this.periodTo = periodTo;
	}

	/**
	 * @return ������ ���������
	 */
	public Set<String> getDepartments()
	{
		//noinspection ReturnOfCollectionOrArrayField
		return departments;
	}

	/**
	 * @param departments ������ ���������
	 */
	public void setDepartments(Set<String> departments)
	{
		//noinspection AssignmentToCollectionOrArrayFieldFromParameter
		this.departments = departments;
	}

	/**
	 * @return ���������� � ��������� �������
	 */
	public Set<ProductRequirement> getRequirements()
	{
		//noinspection ReturnOfCollectionOrArrayField
		return requirements;
	}

	/**
	 * @param requirements ���������� � ��������� �������
	 */
	public void setRequirements(Set<ProductRequirement> requirements)
	{
		//noinspection AssignmentToCollectionOrArrayFieldFromParameter
		this.requirements = requirements;
	}

	/**
	 * @return ���������� � ����� ������
	 */
	public Set<AccTypesRequirement> getReqAccTypes()
	{
		//noinspection ReturnOfCollectionOrArrayField
		return reqAccTypeRequirements;
	}

	/**
	 * @param reqAccTypeRequirements ���������� � ����� ������
	 */
	public void setReqAccTypes(Set<AccTypesRequirement> reqAccTypeRequirements)
	{
		//noinspection AssignmentToCollectionOrArrayFieldFromParameter
		this.reqAccTypeRequirements = reqAccTypeRequirements;
	}

	/**
	 * @return ��������� �������
	 */
	public String getTitle()
	{
		return title;
	}

	/**
	 * @param title ��������� �������
	 */
	public void setTitle(String title)
	{
		this.title = title;
	}

	/**
	 * @return ����� �� �������
	 */
	public String getText()
	{
		return text;
	}

	/**
	 * @param text ����� �� �������
	 */
	public void setText(String text)
	{
		this.text = text;
	}

	/**
	 * @return ������
	 */
	public List<AdvertisingButton> getButtons()
	{
		//noinspection ReturnOfCollectionOrArrayField
		return buttons;
	}

	/**
	 * @param buttons ������
	 */
	public void setButtons(List<AdvertisingButton> buttons)
	{
		//noinspection AssignmentToCollectionOrArrayFieldFromParameter
		this.buttons = buttons;
	}

	/**
	 * @return ������� �������
	 */
	public List<AdvertisingArea> getAreas()
	{
		//noinspection ReturnOfCollectionOrArrayField
		return areas;
	}

	/**
	 * @param areas ������� �������
	 */
	public void setAreas(List<AdvertisingArea> areas)
	{
		//noinspection AssignmentToCollectionOrArrayFieldFromParameter
		this.areas = areas;
	}

	/**
	 * @return ����� ����������� �������
	 */
	public Long getShowTime()
	{
		return showTime;
	}

	/**
	 * @param showTime ����� ����������� �������
	 */
	public void setShowTime(Long showTime)
	{
		this.showTime = showTime;
	}

	/**
	 * @return ������� ����������� ��������
	 */
	public Long getOrderIndex()
	{
		return orderIndex;
	}

	/**
	 * @param orderIndex ������� ����������� ��������
	 */
	public void setOrderIndex(Long orderIndex)
	{
		this.orderIndex = orderIndex;
	}

	/**
	 * @return ����������� ������� � �������� ����������/mapi
	 */
    public AdvertisingAvailability getAvailability()
    {
        return availability;
    }

	/**
	 * @param availability ����������� ������� � �������� ����������/mapi
	 */
    public void setAvailability(AdvertisingAvailability availability)
    {
        this.availability = availability;
    }

	/**
	 * @return �������� �����������
	 */
	public Image getImage()
	{
		return image;
	}

	/**
	 * @param image - �����������
	 */
	public void setImage(Image image)
	{
		this.image = image;
	}
}
