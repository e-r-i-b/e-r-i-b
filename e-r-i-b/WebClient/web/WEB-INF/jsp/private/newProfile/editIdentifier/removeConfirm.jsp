<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean"     prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles"    prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core"              prefix="c" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html"     prefix="html"  %>
<%@ taglib uri="http://rssl.com/tags"                           prefix="phiz" %>

&nbsp;
<tiles:importAttribute/>
<html:form action="/private/async/userprofile/editIdentifier" appendToken="true">
    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>
    <c:set var="confirmRequest" value="${phiz:currentConfirmRequest(form.confirmableObject)}"/>
    <c:set var="confirmStrategy" value="${form.confirmStrategy}"/>
    <c:set var="accessToBasket" value="${phiz:impliesOperation('ViewPaymentsBasketOperation', 'PaymentBasketManagment')}"/>
    <tiles:insert definition="confirm_${confirmRequest.strategyType}" flush="false">
        <tiles:put name="confirmRequest" beanName="confirmRequest"/>
        <tiles:put name="useAjax" value="true"/>
        <tiles:put name="ajaxUrl" value="/private/async/userprofile/editIdentifier"/>
        <tiles:put name="data">
            <div class="winDesc">
                <c:choose>
                    <c:when test="${accessToBasket}">
                        Вы действительно хотите удалить этот документ и все связи с выставленными счетами, использующими эти данные?
                    </c:when>
                    <c:otherwise>
                        Вы действительно хотите удалить этот документ?
                    </c:otherwise>
                </c:choose>
            </div>
        </tiles:put>
        <tiles:put name="anotherStrategy"       value="false"/>
        <tiles:put name="confirmableObject"     value="userDocumentRemove"/>
        <tiles:put name="byCenter"              value="Center"/>
        <tiles:put name="confirmCommandKey"     value="button.remove.confirm"/>
        <tiles:put name="preConfirmCommandKey"     value="button.removeSMS"/>
        <tiles:put name="showCancelButton"      value="false"/>
        <tiles:put name="buttonType"            value="singleRow"/>
        <tiles:put name="confirmStrategy"       beanName="confirmStrategy"/>
        <tiles:put name="ajaxOnCompleteCallback">win.close('addIdetnifierBasketSNILS');</tiles:put>
    </tiles:insert>
</html:form>