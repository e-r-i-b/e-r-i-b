<%--
  Created by IntelliJ IDEA.
  User: lepihina
  Date: 14.12.2011
  Time: 7:17:16
--%>
<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>

<script type="text/javascript">
    var OTP_GET = '<bean:message bundle="userprofileBundle" key="label.OTPGet"/>';
    var NOT_OTP_GET = '<bean:message bundle="userprofileBundle" key="label.notOTPGet"/>';
    var OTP_USE = '<bean:message bundle="userprofileBundle" key="label.OTPUse"/>';
    var NOT_OTP_USE = '<bean:message bundle="userprofileBundle" key="label.notOTPUse"/>';
    var GET = "OTPGet";
    var USE = "OTPUse";

    function showDescOTPGet(elementId)
    {
        if ($("#" + GET + elementId).attr("checked"))
        {
            $("." + GET + elementId + ' label').html(OTP_GET);
            $("#" + USE + elementId).removeAttr("checked");
            $("#div" + USE + elementId).css("display","none");
        }
        else
        {
            $("." + GET + elementId + ' label').html(NOT_OTP_GET);
            $("#div" + USE + elementId).css("display","");
            if (!$("#" + USE + elementId).attr("disabled"))
            {
                $("#" + USE + elementId).attr("checked","checked");
                $("." + USE + elementId + ' label').html(OTP_USE);
            }
        }
    }

    function showDescOTPUse(elementId)
    {
        if ($("#" + USE + elementId).attr("checked"))
            $("." + USE + elementId + ' label').html(OTP_USE);
        else
            $("." + USE + elementId + ' label').html(NOT_OTP_USE);
    }
</script>

<c:set var="cards" value="${form.cards}"/>
<c:set var="isEmpty" value="${empty cards}"/>
<c:set var="confirmStrategy" value="${form.confirmStrategy}"/>
<c:choose>
    <c:when test="${not isEmpty}">
        <div class="otpRestrictions simpleTable">
            <table width="100%">
                <tr class="tblInfHeader">
                    <th class="titleTable"><bean:message bundle="userprofileBundle" key="title.cards"/></th>
                    <th class="titleTable"><bean:message bundle="userprofileBundle" key="title.OTPGet"/></th>
                    <th class="titleTable"><bean:message bundle="userprofileBundle" key="title.OTPUse"/></th>
                </tr>
                <c:forEach items="${cards}" var="cardLink">
                    <c:set var="state" value="${cardLink.value.cardState}"/>
                    <c:set var="id" value="${cardLink.id}"/>
                    <c:set var="card" value="${cardLink.card}"/>
                    <c:set var="isLock" value="${card.cardState!=null && card.cardState!='active'}"/>
                    <tr>
                        <td class="align-left firstCell leftPaddingCell" width="300px">
                            <div class="products-text-style word-wrap">
                                <c:set var="spanClass" value="text-green"/>
                                <c:if test="${cardLink.card.availableLimit.decimal < 0 || isLock}">
                                    <c:set var="spanClass" value="text-red"/>
                                </c:if>
                                <bean:write name="cardLink" property="name"/>&nbsp;
                                <span class="card-number">${phiz:getCutCardNumber(cardLink.number)}</span>&nbsp;
                                <span class="${spanClass}"><nobr>${phiz:formatAmount(cardLink.card.availableLimit)}</nobr></span>
                            </div>
                        </td>
                        <td class="align-left">
                            <div class="products-text-style">
                                <c:set var="spanClass" value="OTPGet${id}"/>
                                <html:checkbox property="field(OTPGet${id})" styleId="OTPGet${id}" onclick="showDescOTPGet(${id});" disabled="${not empty confirmRequest}"/>
                                 <c:if test="${not empty confirmRequest}">
                                    <html:hidden property="field(OTPGet${id})" styleId="hiddenOTPGet${id}"/>
                                </c:if>

                                <c:set var="mess" value="label.OTPGet"/>
                                <c:set var="get"><bean:write name="form" property="field(OTPGet${id})"/></c:set>
                                <c:if test="${get == false}">
                                    <c:set var="mess" value="label.notOTPGet"/>
                                </c:if>
                                <span class="${spanClass}"><label for="OTPGet${id}"><bean:message bundle="userprofileBundle" key="${mess}"/></label></span>
                            </div>
                        </td>
                        <td class="align-left lastCell">
                            <div id="divOTPUse${id}" <c:if test="${get}">style="display: none"</c:if>>
                                <c:set var="use"><bean:write name="form" property="field(OTPUse${id})"/></c:set>
                                <c:set var="spanClass" value="OTPUse${id}"/>
                                <c:set var="use"><bean:write name="form" property="field(OTPUse${id})"/></c:set>
                                <c:set var="disabled" value="${not empty confirmRequest || use == false && use != ''}"/>
                                <html:checkbox property="field(OTPUse${id})" styleId="OTPUse${id}" onclick="showDescOTPUse(${id});" disabled="${disabled}"/>
                                <c:if test="${not empty confirmRequest}">
                                    <html:hidden property="field(OTPUse${id})" styleId="hiddenOTPUse${id}"/>
                                </c:if>

                                <c:set var="mess" value="label.OTPUse"/>
                                <c:if test="${use == false}">
                                    <c:set var="mess" value="label.notOTPUse"/>
                                </c:if>
                                <span class="${spanClass}"><label for="OTPUse${id}"><bean:message bundle="userprofileBundle" key="${mess}"/></label></span>
                            </div>
                        </td>
                    </tr>
                </c:forEach>
              </table>
        </div>


        <div class="backToService">
            <html:link action="/private/userprofile/accountSecurity.do" styleClass="blueGrayLink">
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
                        <tiles:put name="ajaxUrl" value="/private/async/userprofile/otpRestriction"/>
                        <tiles:put name="confirmRequest" beanName="confirmRequest"/>
                        <tiles:put name="confirmStrategy" beanName="confirmStrategy"/>
                        <tiles:put name="anotherStrategy" value="false"/>
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
                        <tiles:put name="commandKey" value="button.saveOTPRestrictions"/>
                        <tiles:put name="commandHelpKey" value="button.save.help"/>
                        <tiles:put name="validationFunction" value="checkData()"/>
                        <tiles:put name="isDefault" value="true"/>
                        <tiles:put name="bundle" value="userprofileBundle"/>
                    </tiles:insert>
                </c:otherwise>
            </c:choose>
            <div class="clear"></div>
        </div>
    </c:when>
    <c:otherwise>
        <div class="emptyText">
            <tiles:insert definition="roundBorderLight" flush="false">
                <tiles:put name="color" value="greenBold"/>
                <tiles:put name="data">
                    <span class="text-dark-gray normal relative">
                        <bean:message bundle="userprofileBundle" key="label.otpRestr.emptyMessage"/>
                    </span>
                </tiles:put>
            </tiles:insert>
        </div>
    </c:otherwise>
</c:choose>