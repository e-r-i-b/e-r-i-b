package com.rssl.phizic.business.dictionaries.pfp.products;

import com.rssl.phizic.business.dictionaries.pfp.products.types.TableColumn;
import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;

import java.util.Map;

/**
 * @author akrenev
 * @ created 28.01.2014
 * @ $Author$
 * @ $Revision$
 *
 */

public class TableViewParametersConverter implements Converter
{
	public boolean canConvert(Class aClass)
	{
		return TableViewParameters.class.isAssignableFrom(aClass);
	}

	public void marshal(Object o, HierarchicalStreamWriter hierarchicalStreamWriter, MarshallingContext marshallingContext)
	{
		TableViewParameters parameters = (TableViewParameters) o;
		hierarchicalStreamWriter.startNode(TableViewParameters.class.getName());
		hierarchicalStreamWriter.startNode("useIcon");
		hierarchicalStreamWriter.setValue(String.valueOf(parameters.isUseIcon()));
		hierarchicalStreamWriter.endNode();

		hierarchicalStreamWriter.startNode("columns");
		for (Map.Entry<TableColumn, String> tableColumnStringEntry : parameters.getColumns().entrySet())
		{
			TableColumn tableColumn = tableColumnStringEntry.getKey();
			hierarchicalStreamWriter.startNode("column");
			hierarchicalStreamWriter.startNode("id");
			hierarchicalStreamWriter.setValue(String.valueOf(tableColumn.getId()));
			hierarchicalStreamWriter.endNode();
			hierarchicalStreamWriter.startNode("name");
			hierarchicalStreamWriter.setValue(tableColumn.getValue());
			hierarchicalStreamWriter.endNode();
			hierarchicalStreamWriter.startNode("orderIndex");
			hierarchicalStreamWriter.setValue(String.valueOf(tableColumn.getOrderIndex()));
			hierarchicalStreamWriter.endNode();
			hierarchicalStreamWriter.startNode("uid");
			hierarchicalStreamWriter.setValue(tableColumn.getUuid());
			hierarchicalStreamWriter.endNode();
			hierarchicalStreamWriter.startNode("value");
			hierarchicalStreamWriter.setValue(tableColumnStringEntry.getValue());
			hierarchicalStreamWriter.endNode();
			hierarchicalStreamWriter.endNode();
		}
		hierarchicalStreamWriter.endNode();
		hierarchicalStreamWriter.endNode();
	}


	public Object unmarshal(HierarchicalStreamReader hierarchicalStreamReader, UnmarshallingContext unmarshallingContext)
	{
		TableViewParameters parameters = new TableViewParameters();
		hierarchicalStreamReader.moveDown();
		hierarchicalStreamReader.moveDown();
		parameters.setUseIcon(Boolean.getBoolean(hierarchicalStreamReader.getValue()));
		hierarchicalStreamReader.moveUp();
		hierarchicalStreamReader.moveDown();
		while (hierarchicalStreamReader.hasMoreChildren())
		{
			//todo отвязаться от порядка следования тегов (исполнитель: Коптяев Иван)
			TableColumn column = new TableColumn();
			hierarchicalStreamReader.moveDown();
			hierarchicalStreamReader.moveDown();
			column.setId(Long.valueOf(hierarchicalStreamReader.getValue()));
			hierarchicalStreamReader.moveUp();
			hierarchicalStreamReader.moveDown();
			column.setValue(hierarchicalStreamReader.getValue());
			hierarchicalStreamReader.moveUp();
			hierarchicalStreamReader.moveDown();
			column.setOrderIndex(Long.valueOf(hierarchicalStreamReader.getValue()));
			hierarchicalStreamReader.moveUp();
			hierarchicalStreamReader.moveDown();
			column.setUuid(hierarchicalStreamReader.getValue());
			hierarchicalStreamReader.moveUp();
			hierarchicalStreamReader.moveDown();
			String value = hierarchicalStreamReader.getValue();
			hierarchicalStreamReader.moveUp();
			hierarchicalStreamReader.moveUp();

			parameters.getColumns().put(column, value);
		}
		hierarchicalStreamReader.moveUp();
		hierarchicalStreamReader.moveUp();
		return parameters;
	}
}