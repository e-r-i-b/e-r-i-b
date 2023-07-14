<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<html:form action="/private/payments/CSARedirect" onsubmit="return setEmptyAction(event)">
    <c:set var="frm" value="${phiz:currentForm(pageContext)}"/>
    <tiles:insert definition="paymentCurrent">
        <tiles:put name="mainmenu" value="Info"/>
        <tiles:put name="submenu" type="string" value=""/>
        <tiles:put name="pageTitle" type="string">
            <c:out value=""/>
        </tiles:put>
        <tiles:put name="menu" type="string"/>
        <tiles:put name="data" type="string">
            <script type="text/javascript">
                <%--alert("/private/payments/confirm.do" + "?${frm.path}");--%>
                if(${frm.payment})
                    top.location.href = "/PhizIC/private/payments/confirm.do"+"?${frm.path}";
                else
                    top.location.href = "/PhizIC/private/receivers/confirm.do"+"?${frm.path}";    
            </script>
        </tiles:put>
    </tiles:insert>
</html:form>