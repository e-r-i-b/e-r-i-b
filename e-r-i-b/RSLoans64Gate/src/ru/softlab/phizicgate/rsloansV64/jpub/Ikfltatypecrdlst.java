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

public class Ikfltatypecrdlst implements ORAData, ORADataFactory
{
  public static final String _SQL_NAME = "IKFLTATYPECRDLST";
  public static final int _SQL_TYPECODE = OracleTypes.ARRAY;

  MutableArray _array;

private static final Ikfltatypecrdlst _IkfltatypecrdlstFactory = new Ikfltatypecrdlst();

  public static ORADataFactory getORADataFactory()
  { return _IkfltatypecrdlstFactory; }
  /* constructors */
  public Ikfltatypecrdlst()
  {
    this((Ikflttypecrdlst[])null);
  }

  public Ikfltatypecrdlst(Ikflttypecrdlst[] a)
  {
    _array = new MutableArray(2002, a, Ikflttypecrdlst.getORADataFactory());
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
    Ikfltatypecrdlst a = new Ikfltatypecrdlst();
    a._array = new MutableArray(2002, (ARRAY) d, Ikflttypecrdlst.getORADataFactory());
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
  public Ikflttypecrdlst[] getArray() throws SQLException
  {
    return (Ikflttypecrdlst[]) _array.getObjectArray(
      new Ikflttypecrdlst[_array.length()]);
  }

  public Ikflttypecrdlst[] getArray(long index, int count) throws SQLException
  {
    return (Ikflttypecrdlst[]) _array.getObjectArray(index,
      new Ikflttypecrdlst[_array.sliceLength(index, count)]);
  }

  public void setArray(Ikflttypecrdlst[] a) throws SQLException
  {
    _array.setObjectArray(a);
  }

  public void setArray(Ikflttypecrdlst[] a, long index) throws SQLException
  {
    _array.setObjectArray(a, index);
  }

  public Ikflttypecrdlst _getElement(long index) throws SQLException
  {
    return (Ikflttypecrdlst) _array.getObjectElement(index);
  }

  public void _setElement(Ikflttypecrdlst a, long index) throws SQLException
  {
    _array.setObjectElement(a, index);
  }

}
