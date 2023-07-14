<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean"     prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles"    prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core"              prefix="c" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html"     prefix="html"  %>
<%@ taglib uri="http://rssl.com/tags"                           prefix="phiz" %>

&nbsp;
<tiles:importAttribute/>
<html:form action="/private/async/userprofile/editEmail" appendToken="true">
    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>

    <c:set var="confirmRequest" value="${phiz:currentConfirmRequest(form.confirmableObject)}"/>
    <c:set var="confirmStrategy" value="${form.confirmStrategy}"/>
    <tiles:insert definition="confirm_${confirmRequest.strategyType}" flush="false">
        <tiles:put name="confirmRequest" beanName="confirmRequest"/>
        <tiles:put name="useAjax" value="true"/>
        <tiles:put name="ajaxUrl" value="/private/async/userprofile/editEmail"/>
        <tiles:put name="data">
            <table class="editEmailTable">
                <tr><td class="fieldName">E-mail</td><td><html:text property="field(email)" size="40" maxlength="100" styleId="fieldEmail" disabled="true"/></td></tr>
                <tr><td class="fieldName alignTop">Формат</td>
                    <td>
                        <div class="radioInputBlock"><html:radio property="field(mailFormat)" value="PLAIN_TEXT" styleId="plainText" disabled="true"/><label for="plainText"><span class="bold">Текст</span> (письмо без картинок)</label></div>
                        <div class="radioInputBlock"><html:radio property="field(mailFormat)" value="HTML" styleId="htmlText" disabled="true"/><label for="htmlText"> <span class="bold">HTML-письмо</span> (письмо с картинками)</label></div>
                    </td>
                </tr>
            </table>
        </tiles:put>
        <tiles:put name="anotherStrategy"       value="false"/>
        <tiles:put name="confirmableObject"     value="userEmailSettings"/>
        <tiles:put name="byCenter"              value="Center"/>
        <tiles:put name="confirmCommandKey"     value="button.confirm"/>
        <tiles:put name="showCancelButton"      value="false"/>
        <tiles:put name="buttonType"            value="singleRow"/>
        <tiles:put name="confirmStrategy"       beanName="confirmStrategy"/>
        <tiles:put name="ajaxOnCompleteCallback">win.close('editEmailDiv');</tiles:put>

    </tiles:insert>
</html:form>