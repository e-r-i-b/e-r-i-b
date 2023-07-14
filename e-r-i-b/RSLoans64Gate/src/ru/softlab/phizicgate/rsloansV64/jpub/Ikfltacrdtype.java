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

public class Ikfltacrdtype implements ORAData, ORADataFactory
{
  public static final String _SQL_NAME = "IKFLTACRDTYPE";
  public static final int _SQL_TYPECODE = OracleTypes.ARRAY;

  MutableArray _array;

private static final Ikfltacrdtype _IkfltacrdtypeFactory = new Ikfltacrdtype();

  public static ORADataFactory getORADataFactory()
  { return _IkfltacrdtypeFactory; }
  /* constructors */
  public Ikfltacrdtype()
  {
    this((Ikfltcrdtype[])null);
  }

  public Ikfltacrdtype(Ikfltcrdtype[] a)
  {
    _array = new MutableArray(2002, a, Ikfltcrdtype.getORADataFactory());
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
    Ikfltacrdtype a = new Ikfltacrdtype();
    a._array = new MutableArray(2002, (ARRAY) d, Ikfltcrdtype.getORADataFactory());
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
  public Ikfltcrdtype[] getArray() throws SQLException
  {
    return (Ikfltcrdtype[]) _array.getObjectArray(
      new Ikfltcrdtype[_array.length()]);
  }

  public Ikfltcrdtype[] getArray(long index, int count) throws SQLException
  {
    return (Ikfltcrdtype[]) _array.getObjectArray(index,
      new Ikfltcrdtype[_array.sliceLength(index, count)]);
  }

  public void setArray(Ikfltcrdtype[] a) throws SQLException
  {
    _array.setObjectArray(a);
  }

  public void setArray(Ikfltcrdtype[] a, long index) throws SQLException
  {
    _array.setObjectArray(a, index);
  }

  public Ikfltcrdtype _getElement(long index) throws SQLException
  {
    return (Ikfltcrdtype) _array.getObjectElement(index);
  }

  public void _setElement(Ikfltcrdtype a, long index) throws SQLException
  {
    _array.setObjectElement(a, index);
  }

}
