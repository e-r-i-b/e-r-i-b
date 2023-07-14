package ru.softlab.phizicgate.rsloansV64.jpub;

import java.sql.SQLException;
import java.sql.Connection;
import oracle.jdbc.OracleTypes;
import oracle.sql.ORAData;
import oracle.sql.ORADataFactory;
import oracle.sql.Datum;
import oracle.sql.REF;
import oracle.sql.STRUCT;

public class IkfltclaimstateRef implements ORAData, ORADataFactory
{
  public static final String _SQL_BASETYPE = "IKFLTCLAIMSTATE";
  public static final int _SQL_TYPECODE = OracleTypes.REF;

  REF _ref;

private static final IkfltclaimstateRef _IkfltclaimstateRefFactory = new IkfltclaimstateRef();

  public static ORADataFactory getORADataFactory()
  { return _IkfltclaimstateRefFactory; }
  /* constructor */
  public IkfltclaimstateRef()
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
    IkfltclaimstateRef r = new IkfltclaimstateRef();
    r._ref = (REF) d;
    return r;
  }

  public static IkfltclaimstateRef cast(ORAData o) throws SQLException
  {
     if (o == null) return null;
     try { return (IkfltclaimstateRef) getORADataFactory().create(o.toDatum(null), OracleTypes.REF); }
     catch (Exception exn)
     { throw new SQLException("Unable to convert "+o.getClass().getName()+" to IkfltclaimstateRef: "+exn.toString()); }
  }

  public Ikfltclaimstate getValue() throws SQLException
  {
     return (Ikfltclaimstate) Ikfltclaimstate.getORADataFactory().create(
       _ref.getSTRUCT(), OracleTypes.REF);
  }

  public void setValue(Ikfltclaimstate c) throws SQLException
  {
    _ref.setValue((STRUCT) c.toDatum(_ref.getJavaSqlConnection()));
  }
}
