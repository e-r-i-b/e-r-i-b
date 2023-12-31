package ru.softlab.phizicgate.rsloansV64.jpub;

import java.sql.SQLException;
import java.sql.Connection;
import oracle.jdbc.OracleTypes;
import oracle.sql.ORAData;
import oracle.sql.ORADataFactory;
import oracle.sql.Datum;
import oracle.sql.REF;
import oracle.sql.STRUCT;

public class IkfltplanpayRef implements ORAData, ORADataFactory
{
  public static final String _SQL_BASETYPE = "IKFLTPLANPAY";
  public static final int _SQL_TYPECODE = OracleTypes.REF;

  REF _ref;

private static final IkfltplanpayRef _IkfltplanpayRefFactory = new IkfltplanpayRef();

  public static ORADataFactory getORADataFactory()
  { return _IkfltplanpayRefFactory; }
  /* constructor */
  public IkfltplanpayRef()
  {
  }

  /* ORAData interface */
  public Datum toDatum(Connection c) throws SQLException
  {
    return _ref;
  }

  /* ORADataFactory interface */
  public ORAData create(Datum d, int sqlType) throws SQLException
  {
    if (d == null) return null; 
    IkfltplanpayRef r = new IkfltplanpayRef();
    r._ref = (REF) d;
    return r;
  }

  public static IkfltplanpayRef cast(ORAData o) throws SQLException
  {
     if (o == null) return null;
     try { return (IkfltplanpayRef) getORADataFactory().create(o.toDatum(null), OracleTypes.REF); }
     catch (Exception exn)
     { throw new SQLException("Unable to convert "+o.getClass().getName()+" to IkfltplanpayRef: "+exn.toString()); }
  }

  public Ikfltplanpay getValue() throws SQLException
  {
     return (Ikfltplanpay) Ikfltplanpay.getORADataFactory().create(
       _ref.getSTRUCT(), OracleTypes.REF);
  }

  public void setValue(Ikfltplanpay c) throws SQLException
  {
    _ref.setValue((STRUCT) c.toDatum(_ref.getJavaSqlConnection()));
  }
}
