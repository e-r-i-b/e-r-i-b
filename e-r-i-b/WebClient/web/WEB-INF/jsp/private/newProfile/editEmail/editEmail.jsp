<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions"         prefix="fn" %>

<tiles:importAttribute/>
<c:set var="bundle" value="userprofileBundle"/>
<c:set var="errors" value=""/>
&nbsp;
<html:form action="/private/async/userprofile/editEmail"  appendToken="true">
    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>
    <script type="text/javascript">
        var emailLast = '${form.fields.email}';
        function saveEmail()
        {
            var url = "${phiz:calculateActionURL(pageContext, '/private/async/userprofile/editEmail')}";
            var email = $("#fieldEmail").val();
            var format = $('input[name=field(mailFormat)]:checked').val();
            var param = "operation=button.save&field(email)="+email+"&field(mailFormat)="+format;

            ajaxQuery(param,  url, function(data) {
                var actualToken = $(data).find('input[name=org.apache.struts.taglib.html.TOKEN]').val();
                if (actualToken != undefined)
                    $('input[name=org.apache.struts.taglib.html.TOKEN]').val(actualToken);

                if(trim(data) == ''){
                    win.close("editEmailDiv");
                }
                else
                {
                    $("#editEmailDiv").html(data);
                    $("#fieldEmail").focus();
                    $("#fieldEmail").blur();
                }
            },null, false);
        }
        if (window.isClientApp != undefined)
        {
            function addErrorField(field, message)
            {
                var errorParentContainer = $('[name=field(' + field + ')]').parents('td');
                errorParentContainer.addClass('showError');
                var errorContainer = errorParentContainer.find('.errorPofileDiv');
                errorContainer.html(message);
                errorContainer.show();
            }

            <phiz:messages  id="error" bundle="commonBundle" field="field" title="title" message="error">
            <c:choose>
                <c:when test="${field != null}">
                    <c:set var="errorValue"><c:out value="${error}"/></c:set>
                    addErrorField('<bean:write name="field" filter="false"/>', "${phiz:replaceNewLine(errorValue,'<br>')}");
                </c:when>
                <c:otherwise>
                    <c:set var="errors">${errors}<div class="itemDiv">${phiz:processBBCodeAndEscapeHtml(error, false)}</div></c:set>
                </c:otherwise>
                </c:choose>
            </phiz:messages>
        }
        function showMessage()
        {
            var isValidationError = $(".errorPofileDiv").html() != "";
            if (emailLast != $("#fieldEmail").val() || isValidationError)
                $(".hintSms").show();
            else
                $(".hintSms").hide();
        }
    </script>

    <div class="winTitle">Настройка e-mail </div>
    <div class="winDesc">На указанный вами адрес будут приходить оповещения о совершаемых операциях.</div>

    <c:set var="errorsLength" value="${fn:length(errors)}"/>
    <tiles:insert definition="errorBlock" flush="false">
        <tiles:put name="regionSelector" value="errors"/>
        <tiles:put name="isDisplayed" value="${errorsLength gt 0 ? true : false}"/>
        <tiles:put name="data">
            <bean:write name="errors" filter="false"/>
        </tiles:put>
    </tiles:insert>

    <c:if test="${not empty form.fields.confirmableObject}">
        <c:set var="confirmRequest" value="${phiz:currentConfirmRequest(form.fields.confirmableObject)}" scope="request"/>
    </c:if>
    <c:set var="isConfirmPage" value="${not empty confirmRequest}"/>
    <table class="editEmailTable" onkeypress="onEnterKeyPress(event, function(){saveEmail();})">
        <tr><td class="fieldName  alignTop">E-mail</td><td>
                <html:text property="field(email)" size="25" maxlength="100" styleId="fieldEmail" onkeyup="showMessage()" disabled="${isConfirmPage}"/><div class="errorPofileDiv" style="display:none;"></div>
        </td></tr>
        <tr><td class="fieldName alignTop">Формат</td>
            <td>
                <div class="radioInputBlock"><html:radio property="field(mailFormat)" value="PLAIN_TEXT" styleId="plainText" disabled="${isConfirmPage}"/><label for="plainText"><span class="bold">Текст</span> (письмо без картинок)</label></div>
                <div class="radioInputBlock"><html:radio property="field(mailFormat)" value="HTML" styleId="htmlText" disabled="${isConfirmPage}"/><label for="htmlText"> <span class="bold">HTML-письмо</span> (письмо с картинками)</label></div>
            </td>
        </tr>
    </table>
        <c:choose>
            <c:when test="${isConfirmPage}">
                <div class="buttonsArea">
                    <div class="float">
                        <tiles:insert definition="clientButton" flush="false">
                            <tiles:put name="commandTextKey" value="button.cancel"/>
                            <tiles:put name="commandHelpKey" value="button.cancel.help"/>
                            <tiles:put name="bundle" value="commonBundle"/>
                            <tiles:put name="onclick" value="win.close('editEmailDiv');"/>
                            <tiles:put name="viewType" value="buttonGrey"/>
                        </tiles:insert>
                        <tiles:insert definition="confirmButtons" flush="false">
                            <tiles:put name="ajaxUrl" value="/private/async/userprofile/editEmail"/>
                            <tiles:put name="confirmRequest" beanName="confirmRequest"/>
                            <tiles:put name="anotherStrategy" value="false"/>
                            <tiles:put name="needWindow" value="false"/>
                            <tiles:put name="winConfirmName" value="editEmailDiv"/>
                        </tiles:insert>
                    </div>
                </div>
            </c:when>
            <c:otherwise>
                <div class="buttonsArea">
                    <div class="float">
                        <tiles:insert definition="clientButton" flush="false">
                            <tiles:put name="commandTextKey" value="button.cancel"/>
                            <tiles:put name="commandHelpKey" value="button.cancel.help"/>
                            <tiles:put name="bundle" value="commonBundle"/>
                            <tiles:put name="onclick" value="win.close('editEmailDiv');"/>
                            <tiles:put name="viewType" value="buttonGrey"/>
                        </tiles:insert>
                        <tiles:insert definition="clientButton" flush="false">
                            <tiles:put name="commandHelpKey" value="button.save"/>
                            <tiles:put name="commandTextKey" value="button.save"/>
                            <tiles:put name="bundle" value="commonBundle"/>
                            <tiles:put name="onclick" value="saveEmail();"/>
                        </tiles:insert>
                    </div>
                     <div class="hintSms">
                         Для сохранения изменений необходимо подтверждение <b>SMS-паролем</b>
                     </div>
                </div>
            </c:otherwise>
        </c:choose>
</html:form>

