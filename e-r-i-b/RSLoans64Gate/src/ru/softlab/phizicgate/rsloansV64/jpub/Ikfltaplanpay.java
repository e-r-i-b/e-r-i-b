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

public class Ikfltaplanpay implements ORAData, ORADataFactory
{
  public static final String _SQL_NAME = "IKFLTAPLANPAY";
  public static final int _SQL_TYPECODE = OracleTypes.ARRAY;

  MutableArray _array;

private static final Ikfltaplanpay _IkfltaplanpayFactory = new Ikfltaplanpay();

  public static ORADataFactory getORADataFactory()
  { return _IkfltaplanpayFactory; }
  /* constructors */
  public Ikfltaplanpay()
  {
    this((Ikfltplanpay[])null);
  }

  public Ikfltaplanpay(Ikfltplanpay[] a)
  {
    _array = new MutableArray(2002, a, Ikfltplanpay.getORADataFactory());
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
    Ikfltaplanpay a = new Ikfltaplanpay();
    a._array = new MutableArray(2002, (ARRAY) d, Ikfltplanpay.getORADataFactory());
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
  public Ikfltplanpay[] getArray() throws SQLException
  {
    return (Ikfltplanpay[]) _array.getObjectArray(
      new Ikfltplanpay[_array.length()]);
  }

  public Ikfltplanpay[] getArray(long index, int count) throws SQLException
  {
    return (Ikfltplanpay[]) _array.getObjectArray(index,
      new Ikfltplanpay[_array.sliceLength(index, count)]);
  }

  public void setArray(Ikfltplanpay[] a) throws SQLException
  {
    _array.setObjectArray(a);
  }

  public void setArray(Ikfltplanpay[] a, long index) throws SQLException
  {
    _array.setObjectArray(a, index);
  }

  public Ikfltplanpay _getElement(long index) throws SQLException
  {
    return (Ikfltplanpay) _array.getObjectElement(index);
  }

  public void _setElement(Ikfltplanpay a, long index) throws SQLException
  {
    _array.setObjectElement(a, index);
  }

}
