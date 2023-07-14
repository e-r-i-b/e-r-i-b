package ru.softlab.phizicgate.rsloansV64.jpub;

import java.sql.SQLException;
import java.sql.Connection;
import oracle.jdbc.OracleTypes;
import oracle.sql.ORAData;
import oracle.sql.ORADataFactory;
import oracle.sql.Datum;
import oracle.sql.REF;
import oracle.sql.STRUCT;

public class IkflttypecrdlstRef implements ORAData, ORADataFactory
{
  public static final String _SQL_BASETYPE = "IKFLTTYPECRDLST";
  public static final int _SQL_TYPECODE = OracleTypes.REF;

  REF _ref;

private static final IkflttypecrdlstRef _IkflttypecrdlstRefFactory = new IkflttypecrdlstRef();

  public static ORADataFactory getORADataFactory()
  { return _IkflttypecrdlstRefFactory; }
  /* constructor */
  public IkflttypecrdlstRef()
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
    IkflttypecrdlstRef r = new IkflttypecrdlstRef();
    r._ref = (REF) d;
    return r;
  }

  public static IkflttypecrdlstRef cast(ORAData o) throws SQLException
  {
     if (o == null) return null;
     try { return (IkflttypecrdlstRef) getORADataFactory().create(o.toDatum(null), OracleTypes.REF); }
     catch (Exception exn)
     { throw new SQLException("Unable to convert "+o.getClass().getName()+" to IkflttypecrdlstRef: "+exn.toString()); }
  }

  public Ikflttypecrdlst getValue() throws SQLException
  {
     return (Ikflttypecrdlst) Ikflttypecrdlst.getORADataFactory().create(
       _ref.getSTRUCT(), OracleTypes.REF);
  }

  public void setValue(Ikflttypecrdlst c) throws SQLException
  {
    _ref.setValue((STRUCT) c.toDatum(_ref.getJavaSqlConnection()));
  }
}
