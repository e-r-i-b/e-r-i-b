<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<html:form action="/claim/payments/confirm" onsubmit="return setEmptyAction(event)">

    <c:set var="frm" value="${phiz:currentForm(pageContext)}" scope="request"/>

    <tiles:insert definition="loanClaim">
        <tiles:put name="submenu" type="string" value="LoanClaimFind"/>

        <tiles:put name="data" type="string">
            <tiles:insert definition="paymentForm" flush="false">
                <tiles:put name="id"            value=""/>
                <tiles:put name="alignTable"    value="center"/>
                <tiles:put name="name"          value="${title}"/>
                <tiles:put name="description"   value="Внимательно проверьте реквизиты платежа и нажмите на кнопку «Подтвердить»."/>
                <tiles:put name="data">
                    <tiles:insert page="/WEB-INF/jsp/common/loan/loanClaimParametersBlock.jsp" flush="false">
                        <tiles:put name="document" beanName="frm" beanProperty="document"/>
                        <tiles:put name="description" value=""/>
                    </tiles:insert>
                    <div class="clear"></div>
                    ${frm.html}
                </tiles:put>
                <tiles:put name="buttons">
                    <tiles:insert definition="commandButton" operation="EmployeeConfirmFormPaymentOperation" service="LoanClaimEmployeeService" flush="false">
                        <tiles:put name="commandKey"        value="button.confirm"/>
                        <tiles:put name="commandHelpKey"    value="button.confirm.help"/>
                        <tiles:put name="bundle"            value="commonBundle"/>
                        <tiles:put name="stateObject"       value="document"/>
                    </tiles:insert>
                </tiles:put>
            </tiles:insert>
        </tiles:put>
    </tiles:insert>

    <c:if test="${not empty frm.document.state.code and frm.document.state.code =='REFUSED'
                and not empty frm.document.refusingReason
                and (phiz:isInstance(frm.document, 'com.rssl.phizic.business.documents.payments.LoanClaimBase')
                or phiz:isInstance(frm.document, 'com.rssl.phizic.business.documents.payments.VirtualCardClaim'))}">
        <div id="stateDescription" onmouseover="showLayer('state','stateDescription','default');"
             onmouseout="hideLayer('stateDescription');" class="layerFon"
             style="position:absolute; display:none; width:400px; height:37px;overflow:auto;">
            <c:out value="${frm.document.refusingReason}"/>
        </div>
    </c:if>
</html:form>
