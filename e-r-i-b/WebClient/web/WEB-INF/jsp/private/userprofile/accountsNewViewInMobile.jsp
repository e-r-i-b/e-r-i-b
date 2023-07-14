<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib uri="http://struts.application-servers.com/layout" prefix="sl" %>

 <div id="inMobile-items" class="none">
    <div class="interface-items">

    <tiles:insert definition="accountsNewView" flush="false">
        <tiles:put name="selectedCardIds" value="selectedCardMobileIds"/>
        <tiles:put name="selectedAccountIds" value="selectedAccountMobileIds"/>
        <tiles:put name="selectedLoanIds" value="selectedLoanMobileIds"/>
        <tiles:put name="selectedIMAccountIds" value="selectedIMAccountMobileIds"/>
        <tiles:put name="label_show" value="label.show_mobile"/>
        <tiles:put name="label_no_show" value="label.not_show_mobile"/>
        <tiles:put name="confirmRequest" value="${confirmRequest}"/>
        <tiles:put name="typeView" value="inMobile"/>
        <tiles:put name="tableClosedCards" value="tableClosedCardsInMobile"/>
        <tiles:put name="productPredicat" value="_MOB"/>
        <tiles:put name="functionShowDesc" value="showDescSys"/>
     </tiles:insert>

    </div>

        <div class="buttonArea">
            <c:choose>
                <c:when test="${not empty confirmRequest  and confirmName=='settingsViewProductsInMobile'}">
                    <tiles:insert definition="clientButton" flush="false">
                        <tiles:put name="commandTextKey" value="button.cancelChanges"/>
                        <tiles:put name="commandHelpKey" value="button.cancelChanges"/>
                        <tiles:put name="bundle" value="userprofileBundle"/>
                        <tiles:put name="action" value="/private/userprofile/accountSecurity.do"/>
                        <tiles:put name="viewType" value="buttonGrey"/>
                    </tiles:insert>
                   <tiles:insert definition="confirmButtons" flush="false">
                       <tiles:put name="ajaxUrl" value="/private/async/userprofile/accountSecurity/accountsViewConfirm"/>
                       <tiles:put name="confirmRequest" beanName="confirmRequest"/>
                       <tiles:put name="confirmStrategy" beanName="confirmStrategy"/>
                       <tiles:put name="preConfirmCommandKey" value="button.preConfirmMobile"/>
                       <tiles:put name="confirmCommandKey" value="button.confirmMobile"/>
                   </tiles:insert>
                </c:when>
                <c:otherwise>
                   <tiles:insert definition="clientButton" flush="false">
                       <tiles:put name="commandTextKey" value="button.cancelChanges"/>
                       <tiles:put name="commandHelpKey" value="button.cancelChanges"/>
                       <tiles:put name="bundle" value="userprofileBundle"/>
                       <tiles:put name="action" value="/private/userprofile/accountSecurity.do"/>
                       <tiles:put name="viewType" value="buttonGrey"/>
                   </tiles:insert>
                   <tiles:insert definition="commandButton" flush="false">
                       <tiles:put name="commandKey" value="button.saveShowInMobile"/>
                       <tiles:put name="commandHelpKey" value="button.save.help"/>
                       <tiles:put name="isDefault" value="true"/>
                       <tiles:put name="validationFunction" value="checkData()"/>
                       <tiles:put name="bundle" value="userprofileBundle"/>
                       <tiles:put name="enabled" value="false"/>
                       <tiles:put name="id" value="nextButtonForMobile"/>
                   </tiles:insert>
                </c:otherwise>
            </c:choose>
        </div>
        <span class="rightButtonText" id="saveTextMobile">
            Для сохранения изменений необходимо подтверждение <b>SMS-паролем.</b>
        </span>

 </div>
