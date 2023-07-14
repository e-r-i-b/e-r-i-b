package com.rssl.phizic.business.xslt.lists.builder;

import com.rssl.phizic.utils.BeanHelper;
import com.rssl.phizic.utils.StringHelper;
import com.rssl.phizic.utils.xml.XmlHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author akrenev
 * @ created 12.09.2011
 * @ $Author$
 * @ $Revision$
 */
public class Entity implements Entityes
{
	private static final String OPEN_ENTITY_TAG = "<entity key=\"";
	private static final String CLOSE_ENTITY_TAG = "</entity>";
	private static final String CLOSE_ATTRIBUTES = "\">";
	private static final String DESCRIPTION_ATTRIBUTE = "\" description=\"";

	private String key;
	private String description;
	private List<Field> fieldList;
	private EntityList entityList;

	/**
	 * ����������� ��� �������� ������
	 * @param key ���� ������
	 * @param description �������� �������
	 */
	public Entity(String key, String description)
	{
		this.key = key;
		this.description = XmlHelper.escape(StringHelper.getEmptyIfNull(description));
		fieldList = new ArrayList<Field>();
		entityList = new EntityList();
	}

	/**
	 * �������� ���� � ������
	 * @param field ����������� ����
	 */
	public void addField(Field field)
	{
		fieldList.add(field);
	}

	public void addEntity(Entity entity)
	{
		entityList.addEntity(entity);
	}

	/**
	 * �������� ������ ������
	 * @param entityList ������ ������
	 */
	public void addEntityList(EntityList entityList)
	{
		this.entityList.addEntityList(entityList);
	}

	/**
	 *  �������� ������ �� ������ �������� ������
	 * @return ������ �� ������ �������� ������
	 */
	public EntityList getEntityList()
	{
		return entityList;
	}

	/**
	 * �������� ���� � ������, ������ �������� ���� ����� ���� � ������, � ���������, ������ �������� ���� � ������������ ������� 
	 * @param item ������ �� �������� �������� ����
	 * @param prefix ������� ����� ����
	 */
	public void appendObject(Object item, String prefix)
	{
		Map<String, String> map = BeanHelper.createMapFromProperties(item);
		if (map.isEmpty())
			return;
		for (String mapKey: map.keySet())
		{
			addField(new Field(mapKey, map.get(mapKey), prefix));
		}
	}

	public String toString()
	{
		StringBuffer buf = new StringBuffer();
		buf.append(OPEN_ENTITY_TAG);
		buf.append(key);
		if (!StringHelper.isEmpty(description))
		{
			buf.append(DESCRIPTION_ATTRIBUTE);
			buf.append(description);
		}
		buf.append(CLOSE_ATTRIBUTES);
		for (Field field: fieldList)
		{
			buf.append(field.toString());
		}
		if (!entityList.isEmpty())
		{
			buf.append(entityList.toString());
		}
		buf.append(CLOSE_ENTITY_TAG);
		return buf.toString();
	}
}
