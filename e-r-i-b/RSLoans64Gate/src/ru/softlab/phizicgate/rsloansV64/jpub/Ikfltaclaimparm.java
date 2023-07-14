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

public class Ikfltaclaimparm implements ORAData, ORADataFactory
{
  public static final String _SQL_NAME = "IKFLTACLAIMPARM";
  public static final int _SQL_TYPECODE = OracleTypes.ARRAY;

  MutableArray _array;

private static final Ikfltaclaimparm _IkfltaclaimparmFactory = new Ikfltaclaimparm();

  public static ORADataFactory getORADataFactory()
  { return _IkfltaclaimparmFactory; }
  /* constructors */
  public Ikfltaclaimparm()
  {
    this((Ikfltclaimparm[])null);
  }

  public Ikfltaclaimparm(Ikfltclaimparm[] a)
  {
    _array = new MutableArray(2002, a, Ikfltclaimparm.getORADataFactory());
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
    Ikfltaclaimparm a = new Ikfltaclaimparm();
    a._array = new MutableArray(2002, (ARRAY) d, Ikfltclaimparm.getORADataFactory());
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
  public Ikfltclaimparm[] getArray() throws SQLException
  {
    return (Ikfltclaimparm[]) _array.getObjectArray(
      new Ikfltclaimparm[_array.length()]);
  }

  public Ikfltclaimparm[] getArray(long index, int count) throws SQLException
  {
    return (Ikfltclaimparm[]) _array.getObjectArray(index,
      new Ikfltclaimparm[_array.sliceLength(index, count)]);
  }

  public void setArray(Ikfltclaimparm[] a) throws SQLException
  {
    _array.setObjectArray(a);
  }

  public void setArray(Ikfltclaimparm[] a, long index) throws SQLException
  {
    _array.setObjectArray(a, index);
  }

  public Ikfltclaimparm _getElement(long index) throws SQLException
  {
    return (Ikfltclaimparm) _array.getObjectElement(index);
  }

  public void _setElement(Ikfltclaimparm a, long index) throws SQLException
  {
    _array.setObjectElement(a, index);
  }

}
