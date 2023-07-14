<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib uri="http://struts.application-servers.com/layout" prefix="sl" %>

 <div id="inMobile-items" class="none">
    <div class="interface-items">
    <script type="text/javascript">

            function showDescMobile(elementId)
            {
                if ($("#"+elementId).attr("checked") == true)
                {
                   $("."+elementId + ' label').html(availableinMobile);
                }
                else
                {
                    $("."+elementId + ' label').html(unavailableinMobile);
                }
            }
        </script>

    <tiles:insert definition="accountsView" flush="false">
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
        <tiles:put name="functionShowDesc" value="showDescMobile"/>
     </tiles:insert>

    </div>

        <div class="backToService bold">
            <html:link action="/private/userprofile/accountSecurity.do" onclick="return redirectResolved();" styleClass="blueGrayLink">
                <bean:message bundle="userprofileBundle" key="back.to.security"/>
            </html:link>
        </div>
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
                        <tiles:put name="preConfirmCommandKey" value="button.preConfirmMobile"/>
                        <tiles:put name="confirmCommandKey" value="button.confirmMobile"/>
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
                        <tiles:put name="commandKey" value="button.saveShowInMobile"/>
                        <tiles:put name="isDefault" value="true"/>
                        <tiles:put name="commandHelpKey" value="button.save.help"/>
                        <tiles:put name="validationFunction" value="checkData()"/>
                        <tiles:put name="bundle" value="userprofileBundle"/>
                    </tiles:insert>
                </c:otherwise>
            </c:choose>
            <div class="clear"></div>
        </div>

 </div>
