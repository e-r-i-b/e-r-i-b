package ru.softlab.phizicgate.rsloansV64.jpub;

import java.sql.SQLException;
import java.sql.Connection;
import oracle.jdbc.OracleTypes;
import oracle.sql.ORAData;
import oracle.sql.ORADataFactory;
import oracle.sql.Datum;
import oracle.sql.REF;
import oracle.sql.STRUCT;

public class IkfltcrdtypeRef implements ORAData, ORADataFactory
{
  public static final String _SQL_BASETYPE = "IKFLTCRDTYPE";
  public static final int _SQL_TYPECODE = OracleTypes.REF;

  REF _ref;

private static final IkfltcrdtypeRef _IkfltcrdtypeRefFactory = new IkfltcrdtypeRef();

  public static ORADataFactory getORADataFactory()
  { return _IkfltcrdtypeRefFactory; }
  /* constructor */
  public IkfltcrdtypeRef()
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
    IkfltcrdtypeRef r = new IkfltcrdtypeRef();
    r._ref = (REF) d;
    return r;
  }

  public static IkfltcrdtypeRef cast(ORAData o) throws SQLException
  {
     if (o == null) return null;
     try { return (IkfltcrdtypeRef) getORADataFactory().create(o.toDatum(null), OracleTypes.REF); }
     catch (Exception exn)
     { throw new SQLException("Unable to convert "+o.getClass().getName()+" to IkfltcrdtypeRef: "+exn.toString()); }
  }

  public Ikfltcrdtype getValue() throws SQLException
  {
     return (Ikfltcrdtype) Ikfltcrdtype.getORADataFactory().create(
       _ref.getSTRUCT(), OracleTypes.REF);
  }

  public void setValue(Ikfltcrdtype c) throws SQLException
  {
    _ref.setValue((STRUCT) c.toDatum(_ref.getJavaSqlConnection()));
  }
}
