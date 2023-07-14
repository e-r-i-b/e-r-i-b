<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-logic" prefix="logic" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html:form action="/loans/offers/loanLoad" enctype="multipart/form-data">
    <c:set var="from" value="${LoanOfferLoadForm}"/>
    <script type="text/javascript">
        function changefileValue()
        {
            var filePath = document.getElementById('file');
            var fileName = document.getElementById('file-file-name');
            fileName.value = filePath.value.split('/').pop().split('\\').pop();
        }
    </script>
    <tiles:insert definition="loansMain">
        <tiles:put name="submenu" type="string" value="LoanOffersLoad"/>

        <tiles:put name="data" type="string">
            <tiles:insert definition="paymentForm" flush="false">
                <tiles:put name="id" value="offerLoad"/>
                <tiles:put name="name" value="Загрузка предложений по кредитам"/>
                <tiles:put name="description" value="Выберите файл для загрузки предложений по кредитам"/>
                <tiles:put name="alignTable" value="center"/>
                <tiles:put name="data">
                    <h4>Файл</h4>
                    <input type="hidden" id="file-file-name" name="field(fileName)">
                    <input type="file" id="file" name="file" size="70" class="contactInput" onchange="changefileValue();"/>
                </tiles:put>
                <tiles:put name="buttons">
                    <tiles:insert definition="commandButton" flush="false">
                        <tiles:put name="commandKey" value="button.replic"/>
                        <tiles:put name="commandTextKey" value="button.replic"/>
                        <tiles:put name="commandHelpKey" value="button.replic"/>
                        <tiles:put name="bundle" value="loansBundle"/>
                    </tiles:insert>
                </tiles:put>
            </tiles:insert>
        </tiles:put>
    </tiles:insert>
</html:form>