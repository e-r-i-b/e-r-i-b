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
    <div class="winTitle">Удаление документа </div>
    <div class="winDesc removeDocsType">
        <c:choose>
            <c:when test="${accessToBasket}">
                Вы действительно хотите удалить этот документ и все связи с выставленными счетами, использующими эти данные?
            </c:when>
            <c:otherwise>
                Вы действительно хотите удалить этот документ?
            </c:otherwise>
        </c:choose>
    </div>
    <div class="buttonsArea ButtAreaLeft">
        <div class="float">
            <tiles:insert definition="clientButton" flush="false">
                <tiles:put name="commandTextKey" value="button.cancel"/>
                <tiles:put name="commandHelpKey" value="button.cancel.help"/>
                <tiles:put name="bundle" value="commonBundle"/>
                <tiles:put name="onclick" value="win.close('removeIdentifier');"/>
                <tiles:put name="viewType" value="buttonGrey"/>
            </tiles:insert>
            <tiles:insert definition="confirmButtons" flush="false">
                <tiles:put name="ajaxUrl" value="/private/async/userprofile/editIdentifier"/>
                <tiles:put name="confirmRequest" beanName="confirmRequest"/>
                <tiles:put name="anotherStrategy" value="false"/>
                <tiles:put name="needWindow" value="false"/>
                <tiles:put name="winConfirmName" value="removeIdentifier"/>
                <tiles:put name="preConfirmCommandKey" value="button.remove"/>
            </tiles:insert>
        </div>
    </div>
</html:form>