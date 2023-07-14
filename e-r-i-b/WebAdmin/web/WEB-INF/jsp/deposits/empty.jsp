<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<html:form action="/deposits/list" onsubmit="return setEmptyAction(event);">
  <tiles:insert definition="deposits">
    <tiles:put name="pageTitle" value="��������" type="string"/>
    <tiles:put name="needSave"  value="false"/>

    <tiles:put name="data" type="string">
      <tiles:insert definition="roundBorderLight" flush="false">
        <tiles:put name="color" value="orange"/>
        <tiles:put name="data">
          ��� ����������� ������, �������� ������ ����� � ����� ����.
        </tiles:put>
      </tiles:insert>
    </tiles:put>
  </tiles:insert>
</html:form>