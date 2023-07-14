<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<html:form action="/autopayment/servicesPayments">

    <tiles:insert definition="autoSubscriptions">
        <tiles:put name="submenu" type="string" value="AutoSubscriptions"/>

        <tiles:put name="menu" type="string">
            <%@ include file="/WEB-INF/jsp-sbrf/autopayments/resetClientInfoButton.jsp" %>
        </tiles:put>

        <tiles:put name="data" type="string">
            <tiles:insert definition="paymentForm" flush="false">
                <tiles:put name="id" value=""/>
                <tiles:put name="name" value="Заявка на оформление автоплатежа"/>
                <tiles:put name="description" value="Для того чтобы создать автоплатеж, заполните все поля формы и нажмите на кнопку «Продолжить»."/>
                <tiles:put name="data">
                    <jsp:include page="servicesPaymentData.jsp"/>
                </tiles:put>
                <tiles:put name="buttons" type="string">
                    <tiles:insert definition="clientButton" flush="false">
                        <tiles:put name="commandTextKey" value="button.cancel"/>
                        <tiles:put name="commandHelpKey" value="button.cancel.help"/>
                        <tiles:put name="bundle"         value="autopaymentsBundle"/>
                        <tiles:put name="action"         value="/autopayment/providers.do"/>
                    </tiles:insert>

                     <tiles:insert definition="commandButton" flush="false" service="CreateEmployeeAutoPayment">
                        <tiles:put name="commandKey"     value="button.next"/>
                        <tiles:put name="commandHelpKey" value="button.next.help"/>
                        <tiles:put name="bundle"  value="autopaymentsBundle"/>
                        <tiles:put name="isDefault" value="true"/>
                        <tiles:put name="postbackNavigation" value="true"/>
                    </tiles:insert>
                </tiles:put>
                <tiles:put name="alignTable" value="center"/>
            </tiles:insert>
        </tiles:put>
    </tiles:insert>

</html:form>


