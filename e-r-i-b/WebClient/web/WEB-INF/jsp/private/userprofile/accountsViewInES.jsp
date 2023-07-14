<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib uri="http://struts.application-servers.com/layout" prefix="sl" %>

<div id="inES-items" class="none">
    <div class="interface-items"  >
        <script type="text/javascript">

            function showDescES(elementId)
            {
                if ($("#"+elementId).attr("checked") == true)
                {
                   $("."+elementId + ' label').html(availableinES);
                }
                else
                {
                    $("."+elementId + ' label').html(unavailableinES );
                }
            }
        </script>

        <tiles:insert definition="accountsView" flush="false">
            <tiles:put name="selectedCardIds" value="selectedCardESIds"/>
            <tiles:put name="selectedAccountIds" value="selectedAccountESIds"/>
            <tiles:put name="selectedLoanIds" value="selectedLoanESIds"/>
            <tiles:put name="selectedIMAccountIds" value="selectedIMAccountESIds"/>
            <tiles:put name="label_show" value="label.show_es"/>
            <tiles:put name="label_no_show" value="label.not_show_es"/>
            <tiles:put name="confirmRequest" value="${confirmRequest}"/>
            <tiles:put name="typeView" value="inES"/>
            <tiles:put name="tableClosedCards" value="tableClosedCardsInES"/>
            <tiles:put name="productPredicat" value="_ES"/>
            <tiles:put name="functionShowDesc" value="showDescES"/>
         </tiles:insert>

        <c:if test="${empty form.accounts && empty form.cards && empty form.loans && empty form.imAccounts}">
            <div class="emptyText">
                <tiles:insert definition="roundBorderLight" flush="false">
                    <tiles:put name="color" value="greenBold"/>
                    <tiles:put name="data">
                        <c:set var="services" value=""/>
                        <span class="text-dark-gray normal relative">
                            <bean:message bundle="userprofileBundle" key="label.viewInES.emptyMessage"/>
                        </span>
                    </tiles:put>
                </tiles:insert>
            </div>
        </c:if>
    </div>


    <div class="backToService bold">
    <html:link action="/private/userprofile/accountSecurity.do" onclick="return redirectResolved();" styleClass="blueGrayLink">
        <bean:message bundle="userprofileBundle" key="back.to.security"/>
    </html:link>
    </div>
    <c:if test="${not empty form.accounts || not empty form.cards || not empty form.loans || not empty form.imAccounts}">
        <div class="buttonsArea">
         <c:choose>
            <c:when test="${not empty confirmRequest}">
                <tiles:insert definition="commandButton" flush="false">
                    <tiles:put name="commandKey" value="button.backToEdit"/>
                    <tiles:put name="commandHelpKey" value="button.backToEdit.help"/>
                    <tiles:put name="viewType" value="buttonGrey"/>
                    <tiles:put name="bundle" value="commonBundle"/>
                </tiles:insert>
                <tiles:insert definition="confirmButtons" flush="false">
                    <tiles:put name="ajaxUrl" value="/private/async/userprofile/accountsSystemView"/>
                    <tiles:put name="confirmRequest" beanName="confirmRequest"/>
                    <tiles:put name="confirmStrategy" beanName="confirmStrategy"/>
                    <tiles:put name="preConfirmCommandKey" value="button.preConfirmES"/>
                    <tiles:put name="confirmCommandKey" value="button.confirmES"/>
                </tiles:insert>
            </c:when>
            <c:otherwise>
                <tiles:insert definition="clientButton" flush="false">
                    <tiles:put name="commandTextKey" value="button.cancel"/>
                    <tiles:put name="commandHelpKey" value="button.cancel.help"/>
                    <tiles:put name="bundle" value="commonBundle"/>
                    <tiles:put name="action" value="/private/userprofile/accountSecurity.do"/>
                    <tiles:put name="viewType" value="buttonGrey"/>
                </tiles:insert>
                <tiles:insert definition="commandButton" flush="false">
                    <tiles:put name="commandKey" value="button.saveShowInES"/>
                    <tiles:put name="isDefault" value="true"/>
                    <tiles:put name="commandHelpKey" value="button.save.help"/>
                    <tiles:put name="validationFunction" value="checkData()"/>
                     <tiles:put name="bundle" value="userprofileBundle"/>
                </tiles:insert>
            </c:otherwise>
        </c:choose>
        <div class="clear"></div>
        </div>
    </c:if>
</div>
