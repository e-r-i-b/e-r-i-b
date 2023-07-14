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

public class Ikfltaclaimstate implements ORAData, ORADataFactory
{
  public static final String _SQL_NAME = "IKFLTACLAIMSTATE";
  public static final int _SQL_TYPECODE = OracleTypes.ARRAY;

  MutableArray _array;

private static final Ikfltaclaimstate _IkfltaclaimstateFactory = new Ikfltaclaimstate();

  public static ORADataFactory getORADataFactory()
  { return _IkfltaclaimstateFactory; }
  /* constructors */
  public Ikfltaclaimstate()
  {
    this((Ikfltclaimstate[])null);
  }

  public Ikfltaclaimstate(Ikfltclaimstate[] a)
  {
    _array = new MutableArray(2002, a, Ikfltclaimstate.getORADataFactory());
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
    Ikfltaclaimstate a = new Ikfltaclaimstate();
    a._array = new MutableArray(2002, (ARRAY) d, Ikfltclaimstate.getORADataFactory());
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
  public Ikfltclaimstate[] getArray() throws SQLException
  {
    return (Ikfltclaimstate[]) _array.getObjectArray(
      new Ikfltclaimstate[_array.length()]);
  }

  public Ikfltclaimstate[] getArray(long index, int count) throws SQLException
  {
    return (Ikfltclaimstate[]) _array.getObjectArray(index,
      new Ikfltclaimstate[_array.sliceLength(index, count)]);
  }

  public void setArray(Ikfltclaimstate[] a) throws SQLException
  {
    _array.setObjectArray(a);
  }

  public void setArray(Ikfltclaimstate[] a, long index) throws SQLException
  {
    _array.setObjectArray(a, index);
  }

  public Ikfltclaimstate _getElement(long index) throws SQLException
  {
    return (Ikfltclaimstate) _array.getObjectElement(index);
  }

  public void _setElement(Ikfltclaimstate a, long index) throws SQLException
  {
    _array.setObjectElement(a, index);
  }

}
