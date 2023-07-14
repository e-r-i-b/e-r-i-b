<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>

<c:set var="form" value="${phiz:currentForm(pageContext)}"/>
<c:set var="dictionaryLoanList" value="${form.dictionaryLoanList}"/>
<c:set var="quarterlyInvestSum" value="${quarterlyInvest.productSum.decimal}"/>

<fieldset>
    <div class="pfpPersonLoans">
        <table id="pfpTableLoansContainer" class="pfpTableContainer" cellpadding="0" cellspacing="0">
            <tbody>
                <tr class="tblInfHeader">
                    <th>&nbsp;</th>
                    <th><bean:message key="personLoanList.loanProduct" bundle="pfpBundle"/></th>
                    <th><bean:message key="personLoanList.startDate" bundle="pfpBundle"/></th>
                    <th><bean:message key="personLoanList.endDate" bundle="pfpBundle"/></th>
                    <th class="alignRight">
                        <div class="tableBlock floatRight">
                            <div class="tableCellBlock alignRight"><bean:message key="personLoanList.quarterlyPayment" bundle="pfpBundle"/></div>
                            <div class="tableCellBlock">
                                <tiles:insert definition="floatMessageShadow" flush="false">
                                    <tiles:put name="id" value="quarterlyPaymentHint"/>
                                    <tiles:put name="text">
                                        <bean:message key="personLoanList.quarterlyPayment.hint" bundle="pfpBundle"/>
                                    </tiles:put>
                                    <tiles:put name="dataClass" value="dataHint"/>
                                </tiles:insert>
                            </div>
                        </div>
                    </th>
                    <th width="20px">&nbsp;</th>
                    <th width="20px">&nbsp;</th>
                </tr>

                <tr id="emptyPersonLoanList" style="display: none;">
                    <td colspan="7" style="color:#999">
                        <bean:message key="personLoanList.emptyList" bundle="pfpBundle"/>
                    </td>
                </tr>
                <c:forEach items="${personLoanList}" var="personLoan">
                    <%@ include file="/WEB-INF/jsp/common/pfp/personLoan/personLoanLine.jsp"  %>
                </c:forEach>
                <c:if test="${quarterlyInvestSum == '0.00'}">
                    <tr id="emptyQuarterlyInvestments" class="personLoanLine">
                        <td colspan="7" style="color: #999">
                            Если Вы хотите использовать кредитный продукт для персонального планирования,
                            Вам необходимо создать портфель
                            <html:link onclick="findCommandButton('button.back2').click('',false);" href="#">«Ежеквартальные вложения»</html:link>.
                        </td>
                    </tr>
                </c:if>
            </tbody>
        </table>

        <c:if test="${quarterlyInvestSum != '0.00'}">
            <div class="pfpTableButtonAdd">
                <tiles:insert definition="clientButton" flush="false">
                    <tiles:put name="commandTextKey" value="button.addLoan"/>
                    <tiles:put name="commandHelpKey" value="button.addLoan.help"/>
                    <tiles:put name="viewType" value="linkWithImg"/>
                    <tiles:put name="imageUrl" value="${globalImagePath}/add.gif"/>
                    <tiles:put name="bundle"  value="pfpBundle"/>
                    <tiles:put name="onclick" value="pfpLoanList.openLoanWindow();"/>
                </tiles:insert>
                <div class="clear"></div>
            </div>
        </c:if>
    </div>
</fieldset>
<jsp:include page="/WEB-INF/jsp/common/pfp/personLoan/editLoanWindow.jsp"/>
<jsp:include page="/WEB-INF/jsp/common/pfp/personLoan/confirmRemoveLoan.jsp"/>
<c:set var="removeLoanUrl" value="${phiz:calculateActionURL(pageContext,'/async/editPfpLoan')}"/>
<c:set var="defaultDate"><fmt:formatDate value="${phiz:getNextQuarter().time}" pattern="MM/dd/yyyy"/></c:set>

<script type="text/javascript">

    function processLoanList()
    {
        if ( $('.personLoanLine').size() == 0)
            $('#emptyPersonLoanList').show();
        else
            $('#emptyPersonLoanList').hide();
    }

    $(document).ready(function(){
        processLoanList();
    });

    var pfpLoanList = {
        currentDate : new Date("${defaultDate}"),
        currentRemovedLoanId : null,
        callbackFunctions : [],
        addCallbackFunction : function(func)
        {
            this.callbackFunctions.push(func);
        },
        callback: function(loanId)
        {
            for (var i = 0; i < this.callbackFunctions.length; i++)
            {
                this.callbackFunctions[i](loanId);
            }
        },
        openLoanWindow: function()
        {
            var loanStartDate = this.currentDate;
            var recomendLoanAmount = 0;
            var negativeBalance = financePlan.negativeBalanceList[0];
            if(negativeBalance)
            {
                loanStartDate = negativeBalance.date;
                recomendLoanAmount = negativeBalance.amount;
            }

            pfpLoan.initNewLoan(loanStartDate,recomendLoanAmount);
            win.open("addLoan");
        },
        removePersonLoan: function(loanId, removedLoanName)
        {
            currentRemovedLoanId = loanId;
            var loanNameSpan = $('#removedLoanName');
            loanNameSpan.html(removedLoanName);
            loanNameSpan.breakWords();
            win.open('confirmRemoveLoan');
        },
        doRemovePersonLoan: function()
        {
            var params = 'operation=button.remove';
            params += '&loanId='+currentRemovedLoanId;
            params += '&profileId=' + ${form.id};
            ajaxQuery(params,'${removeLoanUrl}',this.removePersonLoanResult);
        },
        removePersonLoanResult: function(data)
        {
            data = trim(data);
            if(data == '')
            {
                reload();
            }
            //если в дате не нашли ключа об удачном удалении, то в ней содержится ошибка
            else if(data.search("removeSuccessful") == -1)
            {
                addError(data);
                up(0);
            }
            else
            {
                $('#personLoan'+currentRemovedLoanId).remove();
            }
            pfpLoanList.callback(currentRemovedLoanId);
            processLoanList();
            win.close('confirmRemoveLoan');
        }
    };

</script>
