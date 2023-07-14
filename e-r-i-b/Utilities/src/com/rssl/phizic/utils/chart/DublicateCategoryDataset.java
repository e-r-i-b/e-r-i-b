package com.rssl.phizic.utils.chart;

import org.jfree.data.category.CategoryDataset;
import org.jfree.data.general.AbstractDataset;
import org.jfree.util.PublicCloneable;

import java.io.Serializable;
import java.util.*;

/**
 * @author akrenev
 * @ created 18.10.2012
 * @ $Author$
 * @ $Revision$
 *
 * датасет для повторяющихся категорий
 */

public class DublicateCategoryDataset extends AbstractDataset implements CategoryDataset, PublicCloneable, Serializable
{
	private Map<Comparable, DublicateKeyedValue> row = new HashMap<Comparable, DublicateKeyedValue>();  //значения строки
	private String rowKey; //ключ строки

	public int getColumnCount()
	{
		return row.size();
	}

	/**
	 * добавить значение
	 * @param value значение
	 * @param rowKey ключ строки
	 * @param columnKey столбец категории
	 * @param columnLabel название столбца
	 */
	public void addValue(Double value, String rowKey, int columnKey, String columnLabel)
	{
		row.put(columnKey, new DublicateKeyedValue(value, columnLabel));
		this.rowKey = rowKey;
	}

	/**
	 * @return имя строки
	 */
	public String getRowKey()
	{
		return rowKey;
	}

	public DublicateKeyedValue getColumnKey(int column)
	{
	    return row.get(column);
	}

    public int getRowCount()
    {
        return 1;
    }

    public Number getValue(int row, int column)
    {
        return getColumnKey(column).getValue();
    }

    public Comparable getRowKey(int row)
    {
        return rowKey;
    }

    public int getRowIndex(Comparable key)
    {
        return 0;
    }

    public List getRowKeys()
    {
        return Arrays.asList(rowKey);
    }

    public int getColumnIndex(Comparable key)
    {
	    for (Map.Entry<Comparable, DublicateKeyedValue> entry : row.entrySet())
	    {
		    if (entry.getValue().compareTo(key) == 0)
			    return (Integer) entry.getKey();
	    }

        return 0;
    }

    public List getColumnKeys()
    {
	    //noinspection unchecked
	    return new ArrayList(row.keySet());
    }

    public Number getValue(Comparable rowKey, Comparable columnKey)
    {
        return ((DublicateKeyedValue) columnKey).getValue();
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof CategoryDataset)) {
            return false;
        }
        CategoryDataset that = (CategoryDataset) obj;
        if (!getRowKeys().equals(that.getRowKeys())) {
            return false;
        }
        if (!getColumnKeys().equals(that.getColumnKeys())) {
            return false;
        }
        int rowCount = getRowCount();
        int colCount = getColumnCount();
        for (int r = 0; r < rowCount; r++) {
            for (int c = 0; c < colCount; c++) {
                Number v1 = getValue(r, c);
                Number v2 = that.getValue(r, c);
                if (v1 == null) {
                    if (v2 != null) {
                        return false;
                    }
                }
                else if (!v1.equals(v2)) {
                    return false;
                }
            }
        }
        return true;
    }

    public int hashCode() {
        return this.hashCode();
    }

    public Object clone() throws CloneNotSupportedException
    {
        return super.clone();
    }
}
