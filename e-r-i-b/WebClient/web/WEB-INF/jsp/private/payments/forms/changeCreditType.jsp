<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>

<html:form action="/private/payments/changeCreditType" onsubmit="return setEmptyAction()">
<c:set var="form" value="${phiz:currentForm(pageContext)}"/>
    <tiles:insert definition="paymentMain">
        <tiles:put name="data" type="string">
            <tiles:insert definition="mainWorkspace" flush="false">
                <tiles:put name="data">
                        <div align="center" class="title">
                            Указанная Вами сумма кредита превышает сумму, предложенную
                            банком. <br>Ваша заявка будет рассмотрена на общих условиях
                        </div>
                        <c:forEach items="${form.fields}" var="field">
                            <input type="text" value="${field.value}" name="field(${field.key})"/><br/> 
                        </c:forEach>
                        <div class="buttonsArea">
                            <tiles:insert definition="commandButton" flush="false">
                                <tiles:put name="commandKey"     value="button.edit"/>
                                <tiles:put name="commandHelpKey" value="button.edit.help"/>
                                <tiles:put name="bundle"         value="paymentsBundle"/>
                                <tiles:put name="viewType"       value="simpleLink"/>
                            </tiles:insert>
                            <tiles:insert definition="commandButton" flush="false">
                                <tiles:put name="commandKey" value="button.continue"/>
                                <tiles:put name="commandHelpKey" value="button.continue.help"/>
                                <tiles:put name="bundle" value="paymentsBundle"/>
                            </tiles:insert>
                        </div>
                </tiles:put>
            </tiles:insert>
        </tiles:put>
    </tiles:insert>

</html:form>    