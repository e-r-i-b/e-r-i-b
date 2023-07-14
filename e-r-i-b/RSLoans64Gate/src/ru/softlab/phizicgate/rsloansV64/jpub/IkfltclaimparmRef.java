package ru.softlab.phizicgate.rsloansV64.jpub;

import java.sql.SQLException;
import java.sql.Connection;
import oracle.jdbc.OracleTypes;
import oracle.sql.ORAData;
import oracle.sql.ORADataFactory;
import oracle.sql.Datum;
import oracle.sql.REF;
import oracle.sql.STRUCT;

public class IkfltclaimparmRef implements ORAData, ORADataFactory
{
  public static final String _SQL_BASETYPE = "IKFLTCLAIMPARM";
  public static final int _SQL_TYPECODE = OracleTypes.REF;

  REF _ref;

private static final IkfltclaimparmRef _IkfltclaimparmRefFactory = new IkfltclaimparmRef();

  public static ORADataFactory getORADataFactory()
  { return _IkfltclaimparmRefFactory; }
  /* constructor */
  public IkfltclaimparmRef()
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
    IkfltclaimparmRef r = new IkfltclaimparmRef();
    r._ref = (REF) d;
    return r;
  }

  public static IkfltclaimparmRef cast(ORAData o) throws SQLException
  {
     if (o == null) return null;
     try { return (IkfltclaimparmRef) getORADataFactory().create(o.toDatum(null), OracleTypes.REF); }
     catch (Exception exn)
     { throw new SQLException("Unable to convert "+o.getClass().getName()+" to IkfltclaimparmRef: "+exn.toString()); }
  }

  public Ikfltclaimparm getValue() throws SQLException
  {
     return (Ikfltclaimparm) Ikfltclaimparm.getORADataFactory().create(
       _ref.getSTRUCT(), OracleTypes.REF);
  }

  public void setValue(Ikfltclaimparm c) throws SQLException
  {
    _ref.setValue((STRUCT) c.toDatum(_ref.getJavaSqlConnection()));
  }
}
