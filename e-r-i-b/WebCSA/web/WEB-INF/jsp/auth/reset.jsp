<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags/csa" prefix="csa" %>
&nbsp;<%-- костыль для ie6 --%>
<%-- флажок по которому яваскрипт понимает что нужно сбросить форму в начальное состояние --%>
<input type="hidden" name="$$reset"/>
<c:set var="resetMessage">
    <csa:messages  id="error" bundle="commonBundle" field="field" message="error">
         <div class="error-message"><bean:write name='error' filter='false'/></div>
    </csa:messages>
</c:set>

<c:if test="${not empty resetMessage}">
    <div id="reset-messages">
        ${resetMessage}
    </div>
</c:if>

