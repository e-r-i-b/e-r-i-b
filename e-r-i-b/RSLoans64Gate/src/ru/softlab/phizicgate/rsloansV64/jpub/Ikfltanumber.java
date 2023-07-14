package ru.softlab.phizicgate.rsloansV64.jpub;

import java.sql.SQLException;
import java.sql.Connection;
import oracle.jdbc.OracleTypes;
import oracle.sql.ORAData;
import oracle.sql.ORADataFactory;
import oracle.sql.Datum;
import oracle.sql.ARRAY;
import oracle.sql.ArrayDescriptor;
import oracle.jpub.runtime.MutableArray;

public class Ikfltanumber implements ORAData, ORADataFactory
{
  public static final String _SQL_NAME = "IKFLTANUMBER";
  public static final int _SQL_TYPECODE = OracleTypes.ARRAY;

  MutableArray _array;

private static final Ikfltanumber _IkfltanumberFactory = new Ikfltanumber();

  public static ORADataFactory getORADataFactory()
  { return _IkfltanumberFactory; }
  /* constructors */
  public Ikfltanumber()
  {
    this((java.math.BigDecimal[])null);
  }

  public Ikfltanumber(java.math.BigDecimal[] a)
  {
    _array = new MutableArray(2, a, null);
  }

  /* ORAData interface */
  public Datum toDatum(Connection c) throws SQLException
  {
    return _array.toDatum(c, _SQL_NAME);
  }

  /* ORADataFactory interface */
  public ORAData create(Datum d, int sqlType) throws SQLException
  {
    if (d == null) return null; 
    Ikfltanumber a = new Ikfltanumber();
    a._array = new MutableArray(2, (ARRAY) d, null);
    return a;
  }

  public int length() throws SQLException
  {
    return _array.length();
  }

  public int _getBaseType() throws SQLException
  {
    return _array.getBaseType();
  }

  public String _getBaseTypeName() throws SQLException
  {
    return _array.getBaseTypeName();
  }

  public ArrayDescriptor _getDescriptor() throws SQLException
  {
    return _array.getDescriptor();
  }

  /* array accessor methods */
  public java.math.BigDecimal[] getArray() throws SQLException
  {
    return (java.math.BigDecimal[]) _array.getObjectArray();
  }

  public java.math.BigDecimal[] getArray(long index, int count) throws SQLException
  {
    return (java.math.BigDecimal[]) _array.getObjectArray(index, count);
  }

  public void setArray(java.math.BigDecimal[] a) throws SQLException
  {
    _array.setObjectArray(a);
  }

  public void setArray(java.math.BigDecimal[] a, long index) throws SQLException
  {
    _array.setObjectArray(a, index);
  }

  public java.math.BigDecimal _getElement(long index) throws SQLException
  {
    return (java.math.BigDecimal) _array.getObjectElement(index);
  }

  public void _setElement(java.math.BigDecimal a, long index) throws SQLException
  {
    _array.setObjectElement(a, index);
  }

}
