<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags/csa" prefix="csa" %>

<csa:messages  id="error" bundle="commonBundle" field="field" message="error">
    <bean:write name='field' filter='false' ignore="true"/>:<bean:write name='error' filter='false'/>
</csa:messages>

<%-- флажок по которому яваскрипт понимает что это сообщение об ошибке --%>
<input type="hidden" name="$$errorFlag"/>