<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz" %>

<tiles:importAttribute/>
&nbsp;
<html:form action="/private/async/userprofile/editIdentifier"   appendToken="true">
    <c:set var="form" value="${phiz:currentForm(pageContext)}"/>
    <c:set var="userDocument" value="${form.userDocument}"/>
    <c:set var="accessBasket" value="${phiz:impliesService('EditIdentifierBasket') and phiz:impliesService('PaymentBasketManagment') }"/>
    <c:set var="docType" value="${form.documentType}"/>
    <script type="text/javascript">
        function showEditDocumentName(){
            $(".documentNameView").hide();
            $(".documentNameEdit").show();
            $("#fieldName${docType}").focus();
        }
        $(document).ready(function(){
            $("#fieldName${docType}").blur(function() {
                $("#documentName").html( $(this).val());
                $(".documentNameView").show();
                $(".documentNameEdit").hide();
            });
        });
        function saveIdentifier(docType){

            var url = "${phiz:calculateActionURL(pageContext, '/private/async/userprofile/editIdentifier')}";
            var name=decodeURItoWin($("input[name=field(name)]").val());
            var number=decodeURItoWin($("input[name=field(number)]").val());
            var param ="operation=button.save&id=${form.id}&documentType="+docType+"&field(name)="+name+"&field(number)="+number;
            if (docType == 'DL')
            {
                param = param + "&field(series)="+ decodeURItoWin($('input[name=field(series)]').val())
                        + "&field(issueBy)="+decodeURItoWin($('input[name=field(issueBy)]').val())
                        + "&field(issueDate)="+$('input[name=field(issueDate)]').val()
                        + "&field(expireDate)="+$('input[name=field(expireDate)]').val();
            }
            if (docType == 'RC')
            {
                param = param + "&field(series)="+ decodeURItoWin($('input[name=field(series)]').val());
            }
            ajaxQuery(param,  url, function(data) {
                var actualToken = $(data).find('input[name=org.apache.struts.taglib.html.TOKEN]').val();
                if (actualToken != undefined)
                    $('input[name=org.apache.struts.taglib.html.TOKEN]').val(actualToken);

                $("#addIdetnifierBasket"+docType).html(data);
                if (typeof onAfterLoad == "function")
                    onAfterLoad();
            },null, false);
        }

        function removeIdentifier(docId, docType){
            var url = "${phiz:calculateActionURL(pageContext, '/private/async/userprofile/editIdentifier')}";
            var param ="operation=button.remove&id="+docId;
            ajaxQuery(param,  url, function(data) {
                var actualToken = $(data).find('input[name=org.apache.struts.taglib.html.TOKEN]').val();
                if (actualToken != undefined)
                    $('input[name=org.apache.struts.taglib.html.TOKEN]').val(actualToken);
                win.open('removeIdentifier');
                $("#removeIdentifier").html(data);
            },null, false);
        }
        if (window.isClientApp != undefined)
        {
            function addErrorField(field, message)
            {
                var errorParentContainer = $('[name=field(' + field + ')]').parents('.fieldInput');
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
        function changeViewMode(docType){
            $("#"+docType+"View").hide();
            $("#"+docType+"Edit").show();
        }
    </script>


    <c:set var="docName" value="${form.fields.name}"/>
    <c:if test="${empty docName}"><c:set var="docName"><bean:message key="user.document.default.${docType}" bundle="userprofileBundle"/></c:set></c:if>
    <c:if test="${not empty form.fields.confirmableObject}">
        <c:set var="confirmRequest" value="${phiz:currentConfirmRequest(form.fields.confirmableObject)}" scope="request"/>
    </c:if>
    <c:set var="isConfirmPage" value="${not empty confirmRequest}"/>
    <input type="hidden" name="id" value="${form.id}"/>
    <div class="windowEdit${docType}">
        <div class="windowEditIcon"></div>
            <c:choose>
                <c:when test="${docType == 'INN'}">
                    <%@ include file="inn.jsp"%>
                </c:when>
                <c:when test="${docType == 'SNILS'}">
                    <%@ include file="snils.jsp"%>
                </c:when>
                <c:when test="${docType == 'DL'}">
                    <%@ include file="dl.jsp"%>
                </c:when>
                <c:when test="${docType == 'RC'}">
                    <%@ include file="rc.jsp"%>
                </c:when>
            </c:choose>
        </div>
</html:form>

