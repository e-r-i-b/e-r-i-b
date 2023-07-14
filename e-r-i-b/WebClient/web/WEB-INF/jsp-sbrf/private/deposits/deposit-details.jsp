<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<html:form action="/private/async/deposits/details">

    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>
    ${form.html}
    <br/>
    <div class="text-align-center">
        <tiles:insert definition="clientButton" flush="false">
            <tiles:put name="commandTextKey" value="button.info.close"/>
            <tiles:put name="commandHelpKey" value="button.info.close.help"/>
            <tiles:put name="bundle" value="depositInfoBundle"/>
            <tiles:put name="onclick" value="win.close(this);"/>
            <tiles:put name="viewType" value="buttonGrey"/>
        </tiles:insert>
    </div>

</html:form>

