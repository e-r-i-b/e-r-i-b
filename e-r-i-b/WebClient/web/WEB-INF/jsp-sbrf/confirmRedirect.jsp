<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="phiz" uri="http://rssl.com/tags" %>
<html:form action="/confirm/csa">
    <c:set var="frm" value="${phiz:currentForm(pageContext)}"/>
    <script type="text/javascript">
        top.location.href = "/PhizIC/confirm/csa.do" + "?AuthToken=${frm.token}";
    </script>
</html:form>