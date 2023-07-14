<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>

<tiles:importAttribute/>
<c:set var="globalImagePath" value="${globalUrl}/images"/>
<c:set var="imagePath" value="${skinUrl}/images"/>
<c:set var="bundle" value="userprofileBundle"/>

    <script type="text/javascript">
        function submitUserPensionFundRF()
        {
            var snils = trim(ensureElement("fieldSNILS").value);

            ensureElement("SNILS").innerHTML = snils;

            setField("SNILS", snils);

            var obj = payInput.getParenDivByName('field(SNILS)');
            
            obj.className = 'form-row';
            var errorDiv = findChildByClassName(obj, 'errorDiv');

            if (errorDiv != undefined)
            {
                errorDiv.style.display = 'none';
                errorDiv.innerHTML = '';
            }

            win.close('userPensionFundRF');
        }
    </script>


<h1><bean:message key="title.profile.pensionFundRF" bundle="${bundle}"/></h1>

<tiles:insert definition="formRow" flush="false">
    <tiles:put name="title"><bean:message key="label.profile.SNILS" bundle="${bundle}"/></tiles:put>
    <tiles:put name="data">
        <input type="text" size="40" maxlength="14" id="fieldSNILS" name="fieldSNILS"/>
        <script type="text/javascript">
            $(document).ready(function(){$("#fieldSNILS").createMask(SNILS_TEMPLATE); });
        </script>
    </tiles:put>
    <tiles:put name="description" value="Введите Страховой Номер Индивидуального Лицевого Счёта по маске XXX-XXX-XXX XX"/>
    <tiles:put name="regexp">SNILS_LETTERS_REGEXP</tiles:put>
    <tiles:put name="fieldName">fieldSNILS</tiles:put>
</tiles:insert>

<div class="buttonsArea">
    <tiles:insert definition="clientButton" flush="false">
        <tiles:put name="commandTextKey" value="button.cancel"/>
        <tiles:put name="commandHelpKey" value="button.cancel.help"/>
        <tiles:put name="bundle" value="commonBundle"/>
        <tiles:put name="onclick" value="win.close('userPensionFundRF')"/>
        <tiles:put name="viewType" value="buttonGrey"/>
    </tiles:insert>
    <tiles:insert definition="clientButton" flush="false">
        <tiles:put name="commandTextKey" value="button.save"/>
        <tiles:put name="commandHelpKey" value="button.save.help"/>
        <tiles:put name="bundle" value="userprofileBundle"/>
        <tiles:put name="onclick" value="submitUserPensionFundRF()"/>
    </tiles:insert>
</div>
