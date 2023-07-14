<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean"     prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles"    prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core"              prefix="c" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html"     prefix="html"  %>
<%@ taglib uri="http://rssl.com/tags"                           prefix="phiz" %>

&nbsp;
<tiles:importAttribute/>
<html:form action="/private/async/userprofile/addressbook/editContactTrust" appendToken="true">
    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>
    <c:set var="confirmRequest" value="${phiz:currentConfirmRequest(form.confirmableObject)}"/>
    <c:set var="confirmStrategy" value="${form.confirmStrategy}"/>
     <script type="text/javascript">
         function reloadContact()
         {
             win.close('oneTimePasswordWindow');
             showOrHideAjaxPreloader(false);
             showDetailInfo(${form.id});
             contactListManager.contacts[${form.id}].trusted = true;
             contactListManager.contacts[${form.id}].contactInfo.innerHTML =  contactListManager.contacts[${form.id}].rebuildContactDetailInfo();
         }
        function hideError()
         {
             <c:if test="${!confirmRequest.error}">
                 $("#warningMessages").hide();
             </c:if>
         }
     </script>


    <tiles:insert definition="confirm_sms" flush="false">
        <tiles:put name="confirmRequest" beanName="confirmRequest"/>
        <tiles:put name="useAjax" value="true"/>
        <tiles:put name="ajaxUrl" value="/private/async/userprofile/addressbook/editContactTrust"/>
        <tiles:put name="data">
            <div class="infoMessage">
                <c:if test="${form.allowOneConfirm}">
                    <bean:message key="message.one.confirm.trusted" bundle="userprofileBundle"/>
                </c:if>
            </div>
        </tiles:put>
        <tiles:put name="anotherStrategy"       value="false"/>
        <tiles:put name="confirmableObject"     value="contactTrustSettings"/>
        <tiles:put name="byCenter"              value="Center"/>
        <tiles:put name="confirmCommandKey"     value="button.confirm"/>
        <tiles:put name="showCancelButton"      value="false"/>
        <tiles:put name="buttonType"            value="singleRow"/>
        <tiles:put name="confirmStrategy"       beanName="confirmStrategy"/>
        <tiles:put name="ajaxOnCompleteCallback">reloadContact();</tiles:put>
    </tiles:insert>
    <script type="text/javascript">
        doOnLoad(hideError);
    </script>
</html:form>