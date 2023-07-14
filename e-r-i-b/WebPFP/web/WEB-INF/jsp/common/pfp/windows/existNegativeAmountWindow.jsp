<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags"  prefix="phiz"%>

<c:set var="form" value="${phiz:currentForm(pageContext)}"/>
<c:set var="imagePath" value="${skinUrl}/images"/>


<div class="confirmWindowTitle">
    <h2>
        Обратите внимание!
    </h2>
</div>

<div class="confirmWindowMessage">
    <bean:message bundle="pfpBundle" key="existNegativeAmountWindow.alertText"/>
</div>

<div class="tableArea pfpButtonsBlock">
    <tiles:insert definition="clientButton" flush="false">
        <tiles:put name="commandTextKey" value="button.cancelAddTarget"/>
        <tiles:put name="commandHelpKey" value="button.cancelAddTarget.help"/>
        <tiles:put name="bundle" value="pfpBundle"/>
        <tiles:put name="onclick" value="win.close('existNegativeAmount');"/>
        <tiles:put name="viewType" value="buttonGrey"/>
    </tiles:insert>    
</div>