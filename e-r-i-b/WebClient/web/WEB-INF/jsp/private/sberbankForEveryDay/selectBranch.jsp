<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/tags" prefix="phiz"%>

<c:set var="editThis" value="${form.editStageNumber <= thisStage && form.editStageNumber ne 1 && form.editStageNumber ne '1' && form.stageNumber < 6}"/>
<script type="text/javascript">
    function showOrHideErrorMsg()
    {
        var officeVal = $('#credit-card-office').attr('value');
        if(officeVal != "" && officeVal != null)
        {
            $('#emptyDeptError').addClass('invisibleBlock');
            $('#withoutEmptyDeptError').removeClass('invisibleBlock');
        } else {
            $('#emptyDeptError').removeClass('invisibleBlock');
            $('#withoutEmptyDeptError').addClass('invisibleBlock');
        }

    }
</script>

<c:choose>
    <c:when test="${(form.stageNumber == thisStage || editThis) && !form.preparedForConfirm}">
        <script type="text/javascript">

            function setCreditCardOfficeInfo(info)
            {
                setElement('field(credit-card-office)',info["name"]+', '+info["address"]);
                setElement('field(tb)', info["tb"]);
                setElement('field(osb)', info["osb"]);
                setElement('field(vsp)', info["vsp"]);
                $("#cardOfficeAddr").html(info["address"]);
                $("#cardOfficeName").html(info["name"]);
                showOrHideErrorMsg();
                <c:if test="${form.guest}">
                    $('[name="field(regionId)"]').val(info["regionId"]);
                    $('[name="field(regionName)"]').val(info["regionName"]);
                    $('#regionNameSpan').html(info["regionName"]);
                    $('#regionChangeWindowNameSpan').html(info["regionName"]);
                </c:if>
            }

            function showDepartmentsList()
            {
                var url = ${form.guest} ? "${phiz:calculateActionURL(pageContext, "/guest/debit-card-office/list.do")}" : "${phiz:calculateActionURL(pageContext, "/private/debit-card-office/list.do")}";
                url = url + "?guest=" + "${form.guest}";
                <c:choose>
                    <c:when test="${form.guest}">
                        url = url + "&regionId=" + $('[name="field(regionId)"]').get(0).value;
                    </c:when>
                    <c:otherwise>
                        url = url + "&regionId=" + ${phiz:getPersonRegionId()};
                    </c:otherwise>
                </c:choose>
                var win  =  window.open(url, 'Departments', "resizable=1,menubar=0,toolbar=0,scrollbars=1, width=1050, height=700");
                win.moveTo(screen.width / 2 - 410, screen.height / 2 - 325);
                return false;
            }
        </script>

        <div class="view_field <c:if test="${form.needEmptyDeptErrorMessage}">invisibleBlock</c:if>" id="withoutEmptyDeptError">
            <div class="description_text"></div>
            <div class="clear"></div>

            <span class="showList" onclick="showDepartmentsList()" ondblclick="showDepartmentsList()">
                <div class="showListIcon"></div>
                <span class="showListText"><bean:message key="label.selectBranchFromDictionary" bundle="sbnkdBundle"/></span>
            </span>
            <div class="clear"></div>
            <br />
            <div class="title_common subtitle_2_level subtitle_2_view" id="cardOfficeAddr"></div>
            <div class="selectedOfficeTxt" id="cardOfficeName"></div>
        </div>

        <div id="emptyDeptError" class="errorTypeRed <c:if test="${not form.needEmptyDeptErrorMessage}">invisibleBlock</c:if>">
            <div class="view_field paddingLeft34">
                <div class="description_text"></div>
                <div class="clear"></div>

                <span class="showList" onclick="showDepartmentsList()" ondblclick="showDepartmentsList()">
                    <div class="showListIcon"></div>
                    <span class="showListText"><bean:message key="label.selectBranchFromDictionary" bundle="sbnkdBundle"/></span>
                </span>
                <div class="clear"></div>
                <br />

                <div class="title_common subtitle_2_level subtitle_2_view" id="cardOfficeAddr"></div>
                <div class="selectedOfficeTxt" id="cardOfficeName"></div>
            </div>
            <div class="errorTypeRedText"><bean:message key="label.emptyDepartmentError" bundle="sbnkdBundle"/></div>
        </div>

    </c:when>
    <c:otherwise>
        <%-- отделение --%>
        <c:set var ="cardOffice" value="${form.fields['credit-card-office']}"/>
        <div class="view_field">
            <div class="title_common subtitle_2_level subtitle_2_view">${fn:substring(cardOffice, fn:indexOf(cardOffice, ",") + 1, fn:length(cardOffice))}</div>
            <div class="selectedOfficeTxt">${fn:substring(cardOffice, 0, fn:indexOf(cardOffice, ","))}</div>
        </div>
    </c:otherwise>
</c:choose>

<html:hidden property="field(vsp)" styleId="vsp" />
<html:hidden property="field(osb)" styleId="osb" />
<html:hidden property="field(tb)" styleId="tb" />
<html:hidden property="field(credit-card-office)" styleId="credit-card-office" />

<c:if test="${form.stageNumber == thisStage || editThis}">
    <script type="text/javascript">
        $(document).ready(function() {
            var officeVal = $('#credit-card-office').attr('value');
            if(officeVal != "" && officeVal != null)
            {
                showOrHideErrorMsg();
                <c:set var ="cardOffice" value="${form.fields['credit-card-office']}"/>
                $("#cardOfficeAddr").html('${fn:substring(cardOffice, fn:indexOf(cardOffice, ",") + 1, fn:length(cardOffice))}');
                $("#cardOfficeName").html('${fn:substring(cardOffice, 0, fn:indexOf(cardOffice, ","))}');
            }
        });
    </script>
</c:if>
