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

public class Ikfltauserfield implements ORAData, ORADataFactory
{
  public static final String _SQL_NAME = "IKFLTAUSERFIELD";
  public static final int _SQL_TYPECODE = OracleTypes.ARRAY;

  MutableArray _array;

private static final Ikfltauserfield _IkfltauserfieldFactory = new Ikfltauserfield();

  public static ORADataFactory getORADataFactory()
  { return _IkfltauserfieldFactory; }
  /* constructors */
  public Ikfltauserfield()
  {
    this((Ikfltuserfield[])null);
  }

  public Ikfltauserfield(Ikfltuserfield[] a)
  {
    _array = new MutableArray(2002, a, Ikfltuserfield.getORADataFactory());
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
    Ikfltauserfield a = new Ikfltauserfield();
    a._array = new MutableArray(2002, (ARRAY) d, Ikfltuserfield.getORADataFactory());
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
  public Ikfltuserfield[] getArray() throws SQLException
  {
    return (Ikfltuserfield[]) _array.getObjectArray(
      new Ikfltuserfield[_array.length()]);
  }

  public Ikfltuserfield[] getArray(long index, int count) throws SQLException
  {
    return (Ikfltuserfield[]) _array.getObjectArray(index,
      new Ikfltuserfield[_array.sliceLength(index, count)]);
  }

  public void setArray(Ikfltuserfield[] a) throws SQLException
  {
    _array.setObjectArray(a);
  }

  public void setArray(Ikfltuserfield[] a, long index) throws SQLException
  {
    _array.setObjectArray(a, index);
  }

  public Ikfltuserfield _getElement(long index) throws SQLException
  {
    return (Ikfltuserfield) _array.getObjectElement(index);
  }

  public void _setElement(Ikfltuserfield a, long index) throws SQLException
  {
    _array.setObjectElement(a, index);
  }

}
