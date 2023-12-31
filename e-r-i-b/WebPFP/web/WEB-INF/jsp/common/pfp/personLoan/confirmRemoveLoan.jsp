<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<tiles:insert definition="window" flush="false">
    <tiles:put name="id" value="confirmRemoveLoan"/>
    <tiles:put name="data">
        <div class="confirmWindowTitle">
            <h2>
                �������� �������
            </h2>
        </div>

        <div class="confirmWindowMessage">
            �� ������������� ������ ������� ������ &laquo;<span id="removedLoanName"></span>&raquo;?
        </div>

        <div class="tableArea pfpButtonsBlock">
            <tiles:insert definition="clientButton" flush="false">
                <tiles:put name="commandTextKey" value="button.cancel"/>
                <tiles:put name="commandHelpKey" value="button.cancel.help"/>
                <tiles:put name="bundle" value="pfpBundle"/>
                <tiles:put name="onclick" value="win.close('confirmRemoveLoan');"/>
                <tiles:put name="viewType" value="buttonGrey"/>
            </tiles:insert>
            <tiles:insert definition="clientButton" flush="false">
                <tiles:put name="commandTextKey" value="button.remove"/>
                <tiles:put name="commandHelpKey" value="button.remove"/>
                <tiles:put name="bundle" value="pfpBundle"/>
                <tiles:put name="onclick" value="pfpLoanList.doRemovePersonLoan();"/>
            </tiles:insert>
        </div>
    </tiles:put>
</tiles:insert>