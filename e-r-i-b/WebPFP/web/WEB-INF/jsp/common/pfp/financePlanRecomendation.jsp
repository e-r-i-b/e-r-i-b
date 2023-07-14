<%@ page contentType="text/html;charset=windows-1251" language="java" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-tiles" prefix="tiles" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-bean" prefix="bean" %>
<%@ taglib uri="http://jakarta.apache.org/struts/tags-html" prefix="html" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://rssl.com/pfptags" prefix="pfptags" %>

<tiles:importAttribute/>
<c:set var="globalImagePath" value="${globalUrl}/commonSkin/images"/>
<c:set var="personAge" value="${pfptags:getPersonAge()}"/>

<div id="bankRecommend" class="bankRecommend">
    <html:hidden property="field(changedPortfolio)"/>
    <html:hidden property="field(addAmount)"/>
    <html:hidden property="field(wantedIncome)"/>
    <div class="bankRecommendTitle"><bean:message bundle="pfpBundle" key="label.show.financePlan.recommend.text"/></div>

    <div id="startAmountRecomendation">
        <div class="recomendBlockTitle">
            <bean:message bundle="pfpBundle" key="label.show.financePlan.recommend.changeStartAmount"/>
        </div>
        <div class="clear"></div>

        <div id="startCapitalWantedAmountRecomend" class="recomend">
            <tiles:insert definition="roundBorder" flush="false">
                <tiles:put name="color" value="lightGrayWithoutTop"/>
                <tiles:put name="data">
                    <div class="recomendIcon"><img src="${globalImagePath}/pfp/change_start_amount.png" border="0" width="64px" height="64px"></div>
                    <div class="recomendText">
                        <div>
                            <html:link onclick="openConfirmWindowAmount(changeStartAmount,'START_CAPITAL'); return false;" href="#">
                                <bean:message bundle="pfpBundle" key="label.show.financePlan.recommend.changeStartAmount.START_CAPITAL"/>
                            </html:link>
                        </div>
                        <div>
                            <bean:message bundle="pfpBundle" key="label.show.financePlan.recommend.changeStartAmount.START_CAPITAL.text"/>
                            <span class="startCapitalAddedAmount textNobr"></span>
                            <bean:message bundle="pfpBundle" key="currency.rub"/>
                        </div>
                    </div>
                </tiles:put>
            </tiles:insert>
        </div>

        <div id="quarterlyInvestWantedAmountRecomend" class="recomend">
            <tiles:insert definition="roundBorder" flush="false">
                <tiles:put name="color" value="lightGrayWithoutTop"/>
                <tiles:put name="data">
                    <div class="recomendIcon"><img src="${globalImagePath}/pfp/change_start_amount.png" border="0" width="64px" height="64px"></div>
                    <div class="recomendText">
                        <div>
                            <html:link onclick="openConfirmWindowAmount(changeStartAmount,'QUARTERLY_INVEST'); return false;" href="#">
                                <bean:message bundle="pfpBundle" key="label.show.financePlan.recommend.changeStartAmount.QUARTERLY_INVEST"/>
                            </html:link>
                        </div>

                        <div>
                            <bean:message bundle="pfpBundle" key="label.show.financePlan.recommend.changeStartAmount.QUARTERLY_INVEST.text"/>
                            <span class="quarterlyInvestAddedAmount textNobr"></span>
                            <bean:message bundle="pfpBundle" key="currency.rub"/>
                        </div>
                    </div>
                </tiles:put>
            </tiles:insert>
        </div>
    </div>
    <div class="clear"></div>

    <div id="portfolioIncomeRecomendation">
        <div class="recomendBlockTitle">
            <bean:message bundle="pfpBundle" key="label.show.financePlan.recommend.changeIncome"/>
        </div>
        <div class="clear"></div>

        <div id="startCapitalWantedIncomeRecomend" class="recomend">
            <tiles:insert definition="roundBorder" flush="false">
                <tiles:put name="color" value="lightGrayWithoutTop"/>
                <tiles:put name="data">
                    <div class="recomendIcon"><img src="${globalImagePath}/pfp/change_income.png" border="0" width="64px" height="64px"></div>
                    <div class="recomendText">
                        <div>
                            <html:link onclick="openConfirmWindowIncome(changeIncome,'START_CAPITAL'); return false;" href="#">
                                <bean:message bundle="pfpBundle" key="label.show.financePlan.recommend.changeIncome.START_CAPITAL"/>
                            </html:link>
                        </div>
                        <div>
                            <bean:message bundle="pfpBundle" key="label.show.financePlan.recommend.changeIncome.START_CAPITAL.text"/>
                            <span class="startCapitalWantedIncome textNobr"></span><bean:message bundle="pfpBundle" key="label.income.legend"/>
                            <input type="hidden" id="wantedIncomeForSTART_CAPITAL">
                        </div>
                    </div>
                </tiles:put>
            </tiles:insert>
        </div>

        <div id="quarterlyInvestWantedIncomeRecomend" class="recomend">
            <tiles:insert definition="roundBorder" flush="false">
                <tiles:put name="color" value="lightGrayWithoutTop"/>
                <tiles:put name="data">
                    <div class="recomendIcon"><img src="${globalImagePath}/pfp/change_income.png" border="0" width="64px" height="64px"></div>
                    <div class="recomendText">
                        <div>
                            <html:link onclick="openConfirmWindowIncome(changeIncome,'QUARTERLY_INVEST'); return false;" href="#">
                                <bean:message bundle="pfpBundle" key="label.show.financePlan.recommend.changeIncome.QUARTERLY_INVEST"/>
                            </html:link>
                        </div>
                        <div>
                            <bean:message bundle="pfpBundle" key="label.show.financePlan.recommend.changeIncome.QUARTERLY_INVEST.text"/>
                            <span class="quarterlyInvestWantedIncome textNobr"></span><bean:message bundle="pfpBundle" key="label.income.legend"/>
                            <input type="hidden" id="wantedIncomeForQUARTERLY_INVEST">
                        </div>
                    </div>
                </tiles:put>
            </tiles:insert>
        </div>
    </div>
    <div class="clear"></div>

    <div id="loansRecomendation">
        <div class="recomendBlockTitle">
            <bean:message bundle="pfpBundle" key="label.show.financePlan.recommend.loans"/>
        </div>
        <div class="clear"></div>

        <div id="LoanRecomend" class="recomend">
            <tiles:insert definition="roundBorder" flush="false">
                <tiles:put name="color" value="lightGrayWithoutTop"/>
                <tiles:put name="data">
                    <div class="recomendIcon"><img src="${globalImagePath}/pfp/take_loan.jpg" border="0" width="64px" height="64px"></div>
                    <div class="recomendText">
                        <div>
                            <html:link onclick="pfpLoanList.openLoanWindow(); return false;" href="#">
                                <bean:message bundle="pfpBundle" key="label.show.financePlan.recommend.takeLoans"/>
                            </html:link>
                        </div>
                        <div>
                            <bean:message bundle="pfpBundle" key="label.show.financePlan.recommend.takeLoans.text"/>
                            <span class="loanAdded textNobr"></span>
                            <bean:message bundle="pfpBundle" key="currency.rub"/>
                        </div>
                    </div>
                </tiles:put>
            </tiles:insert>
        </div>
    </div>
    <div class="clear"></div>

</div>

<tiles:insert definition="window" flush="false">
    <tiles:put name="id" value="confirmApplyWantedAmount"/>
    <tiles:put name="data">
        <div id="confirmWidowWantedAmount_START_CAPITAL" class="confirmApplyWantedAmount" style="display: none;">
            <div class="confirmWindowTitle">
                <h2>
                    <bean:message bundle="pfpBundle" key="label.show.financePlan.recommend.window.confirm.amount.START_CAPITAL.title"/>
                </h2>
            </div>

            <div>
                <bean:message bundle="pfpBundle" key="label.show.financePlan.recommend.window.confirm.amount.START_CAPITAL.text"/>
                <span class="startCapitalAddedAmount textNobr bold"></span>
                <bean:message bundle="pfpBundle" key="currency.rub"/>
            </div>

            <div class="addAmountForPortfolio">
                <bean:message bundle="pfpBundle" key="label.show.financePlan.recommend.window.confirm.amount.START_CAPITAL.addText"/>
                <input type="text" id="addAmountForSTART_CAPITAL" class="moneyField" maxlength="13">
                <span class="bold"><bean:message bundle="pfpBundle" key="currency.rub"/></span>
            </div>
        </div>

        <div id="confirmWidowWantedAmount_QUARTERLY_INVEST" class="confirmApplyWantedAmount" style="display: none;">
            <div class="confirmWindowTitle">
                <h2>
                    <bean:message bundle="pfpBundle" key="label.show.financePlan.recommend.window.confirm.amount.QUARTERLY_INVEST.title"/>
                </h2>
            </div>

            <div>
                <bean:message bundle="pfpBundle" key="label.show.financePlan.recommend.window.confirm.amount.QUARTERLY_INVEST.text"/>
                <span class="quarterlyInvestAddedAmount textNobrbold"></span>
                <bean:message bundle="pfpBundle" key="currency.rub"/>
            </div>

            <div class="addAmountForPortfolio">
                <bean:message bundle="pfpBundle" key="label.show.financePlan.recommend.window.confirm.amount.QUARTERLY_INVEST.addText"/>
                <input type="text" id="addAmountForQUARTERLY_INVEST" class="moneyField" maxlength="13">
                <span class="bold"><bean:message bundle="pfpBundle" key="currency.rub"/></span>
            </div>
        </div>

        <div class="buttonsArea">
            <tiles:insert definition="clientButton" flush="false">
                <tiles:put name="commandTextKey" value="label.show.financePlan.recommend.window.confirm.button.cancel"/>
                <tiles:put name="commandHelpKey" value="label.show.financePlan.recommend.window.confirm.button.cancel"/>
                <tiles:put name="bundle" value="pfpBundle"/>
                <tiles:put name="viewType" value="buttonGrey"/>
                <tiles:put name="onclick" value="win.close('confirmApplyWantedAmount');"/>
            </tiles:insert>
            <tiles:insert definition="clientButton" flush="false">
                <tiles:put name="commandTextKey" value="label.show.financePlan.recommend.window.confirm.button.accept"/>
                <tiles:put name="commandHelpKey" value="label.show.financePlan.recommend.window.confirm.button.accept"/>
                <tiles:put name="bundle" value="pfpBundle"/>
                <tiles:put name="onclick" value="applyChange();win.close('confirmApplyWantedAmount');"/>
            </tiles:insert>
            <div class="clear"></div>
        </div>
    </tiles:put>
</tiles:insert>

<tiles:insert definition="window" flush="false">
    <tiles:put name="id" value="confirmApplyIncome"/>
    <tiles:put name="data">
        <div id="confirmWidowIncome_START_CAPITAL" class="confirmApplyIncome" style="display: none;">
            <div class="confirmWindowTitle">
                <h2>
                    <bean:message bundle="pfpBundle" key="label.show.financePlan.recommend.window.confirm.income.START_CAPITAL.title"/>
                </h2>
            </div>

            <div>
                <bean:message bundle="pfpBundle" key="label.show.financePlan.recommend.window.confirm.income.START_CAPITAL.text"/>
                <span class="startCapitalWantedIncome textNobr"></span><bean:message bundle="pfpBundle" key="label.income.legend"/>
            </div>
        </div>

        <div id="confirmWidowIncome_QUARTERLY_INVEST" class="confirmApplyIncome" style="display: none;">
            <div class="confirmWindowTitle">
                <h2>
                    <bean:message bundle="pfpBundle" key="label.show.financePlan.recommend.window.confirm.income.QUARTERLY_INVEST.title"/>
                </h2>
            </div>

            <div>
                <bean:message bundle="pfpBundle" key="label.show.financePlan.recommend.window.confirm.income.QUARTERLY_INVEST.text"/>
                <span class="quarterlyInvestWantedIncome textNobr"></span><bean:message bundle="pfpBundle" key="label.income.legend"/>
            </div>
        </div>

        <div class="buttonsArea">
            <tiles:insert definition="clientButton" flush="false">
                <tiles:put name="commandTextKey" value="label.show.financePlan.recommend.window.confirm.button.no"/>
                <tiles:put name="commandHelpKey" value="label.show.financePlan.recommend.window.confirm.button.no"/>
                <tiles:put name="bundle" value="pfpBundle"/>
                <tiles:put name="viewType" value="buttonGrey"/>
                <tiles:put name="onclick" value="win.close('confirmApplyIncome');"/>
            </tiles:insert>
            <tiles:insert definition="clientButton" flush="false">
                <tiles:put name="commandTextKey" value="label.show.financePlan.recommend.window.confirm.button.yes"/>
                <tiles:put name="commandHelpKey" value="label.show.financePlan.recommend.window.confirm.button.yes"/>
                <tiles:put name="bundle" value="pfpBundle"/>
                <tiles:put name="onclick" value="applyChange();win.close('confirmApplyIncome');"/>
            </tiles:insert>
            <div class="clear"></div>
        </div>
    </tiles:put>
</tiles:insert>

<div class="clear"></div>

<script type="text/javascript">

    var financePlanRecomendations = {
        startCapitalQuarterIncome: null,
        quarterlyInvestQuarterIncome: null,
        quarterlyInvestAmount: null,
        startCapitalAmount: null,
        executionDate: null,
        init: function(startCapitalAmount,quarterlyInvestAmount,startCapitalQuarterIncome,quarterlyInvestQuarterIncome,executionDate)
        {
            this.startCapitalAmount = startCapitalAmount;
            this.quarterlyInvestAmount = quarterlyInvestAmount;
            this.startCapitalQuarterIncome = startCapitalQuarterIncome;
            this.quarterlyInvestQuarterIncome = quarterlyInvestQuarterIncome;
            this.executionDate = executionDate;
        },
        calcRecomendations: function()
        {
            var financePlanCalculator = new FinancePlanCalculator(this.startCapitalAmount,this.quarterlyInvestAmount,this.startCapitalQuarterIncome,this.quarterlyInvestQuarterIncome,this.executionDate);
            financePlanCalculator.setTargetList(financePlan.targetList);
            financePlanCalculator.setLoanList(financePlan.loanList);
            financePlanCalculator.calculate();

            var negativeBalanceList = financePlanCalculator.getNegativeBalanceList();
            if(negativeBalanceList.length == 0)
            {
                $('#bankRecommend').hide();
                return;
            }

            $('#bankRecommend').show();
            if(this.startCapitalAmount > 0)
            {
                var startCapitalAddAmount = this.calcStartCapitalAddAmount();
                $('.startCapitalAddedAmount').html(FloatToString(startCapitalAddAmount,2,' '));
                $('#addAmountForSTART_CAPITAL').setMoneyValue(startCapitalAddAmount.toFixed(2));
                $('#startAmountRecomendation').show();
                $('#startCapitalWantedAmountRecomend').show();
            }
            else
                $('#startCapitalWantedAmountRecomend').hide();

            if(this.quarterlyInvestAmount > 0)
            {
                var quarterlyInvestAddAmount = this.calcQuarterlyInvestAddAmount();
                $('.quarterlyInvestAddedAmount').html(FloatToString(quarterlyInvestAddAmount,2,' '));
                $('#addAmountForQUARTERLY_INVEST').setMoneyValue(quarterlyInvestAddAmount.toFixed(2));
                $('#startAmountRecomendation').show();
                $('#quarterlyInvestWantedAmountRecomend').show();
            }
            else
                $('#quarterlyInvestWantedAmountRecomend').hide();

            var startCapitalWantedIncome = this.calcStartCapitalWantedIncome();
            if(startCapitalWantedIncome != null)
            {
                $('.startCapitalWantedIncome').html(FloatToString(startCapitalWantedIncome,1,'', undefined, RoundingMode.UP));
                $('#wantedIncomeForSTART_CAPITAL').val(Math.ceil(startCapitalWantedIncome*10)/10);
                $('#portfolioIncomeRecomendation').show();
                $('#startCapitalWantedIncomeRecomend').show();
            }
            else
                $('#startCapitalWantedIncomeRecomend').hide();

            var quarterlyInvestWantedIncome = this.calcQuarterlyInvestWantedIncome();
            if(quarterlyInvestWantedIncome != null)
            {
                $('.quarterlyInvestWantedIncome').html(FloatToString(quarterlyInvestWantedIncome,1,'', undefined, RoundingMode.UP));
                $('#wantedIncomeForQUARTERLY_INVEST').val(Math.ceil(quarterlyInvestWantedIncome*10)/10);
                $('#portfolioIncomeRecomendation').show();
                $('#quarterlyInvestWantedIncomeRecomend').show();
            }
            else
                $('#quarterlyInvestWantedIncomeRecomend').hide();

            if(startCapitalWantedIncome == null && quarterlyInvestWantedIncome == null)
                $('#portfolioIncomeRecomendation').hide();

            var personAge = ${personAge};
            var quarterlyInvest = ${quarterlyInvest.productSum.decimal};
            if ( personAge >= 60 || quarterlyInvest == 0.0)
            {
                $('#LoanRecomend').hide();
                $('#loansRecomendation').hide();
            }
            else
            {
                var loan = this.calcLoans();
                $('.loanAdded').html(FloatToString(loan,2,' '));
                $('#loanAdded').setMoneyValue(loan.toFixed(2));
                $('#loansRecomendation').show();
                $('#LoanRecomend').show();
            }

        },
        calcStartCapitalAddAmount: function()
        {
            var financePlanCalculator = new FinancePlanCalculator(this.startCapitalAmount,this.quarterlyInvestAmount,this.startCapitalQuarterIncome,this.quarterlyInvestQuarterIncome,this.executionDate);
            financePlanCalculator.setTargetList(financePlan.targetList);
            financePlanCalculator.setLoanList(financePlan.loanList);
            financePlanCalculator.calculate();

            var negativeBalanceList = financePlanCalculator.getNegativeBalanceList();
            var negativeBalanceTotalAmount = 0;
            for(var i=0; i < negativeBalanceList.length;i++)
            {
                negativeBalanceTotalAmount = negativeBalanceTotalAmount + negativeBalanceList[i].amount;
            }
            var min = this.startCapitalAmount;
            var max = this.startCapitalAmount + negativeBalanceTotalAmount;
            var tempAmount = (min + max) / 2;
            while(max - min > 10)
            {
                financePlanCalculator.setStartCapitalAmount(tempAmount);
                financePlanCalculator.calculate();
                negativeBalanceList = financePlanCalculator.getNegativeBalanceList();
                if(negativeBalanceList.length == 0)
                    max = tempAmount;
                else
                    min = tempAmount;
                tempAmount = (min + max) / 2;
            }
            return max - this.startCapitalAmount;
        },
        calcQuarterlyInvestAddAmount: function()
        {
            var financePlanCalculator = new FinancePlanCalculator(this.startCapitalAmount,this.quarterlyInvestAmount,this.startCapitalQuarterIncome,this.quarterlyInvestQuarterIncome,this.executionDate);
            financePlanCalculator.setTargetList(financePlan.targetList);
            financePlanCalculator.setLoanList(financePlan.loanList);
            financePlanCalculator.calculate();

            var min = this.quarterlyInvestAmount;
            var max = this.quarterlyInvestAmount;
            var negativeBalanceList = financePlanCalculator.getNegativeBalanceList();
            for(var i=0; i < negativeBalanceList.length;i++)
            {
                var negativeBalance = negativeBalanceList[i];
                max = max + negativeBalance.amount / pfpMath.getDiffInQuarters(this.executionDate,negativeBalance.date);
            }
            var tempAmount = (min + max) / 2;
            while(max - min > 10)
            {
                financePlanCalculator.setQuarterlyInvestAmount(tempAmount);
                financePlanCalculator.calculate();
                negativeBalanceList = financePlanCalculator.getNegativeBalanceList();
                if(negativeBalanceList.length == 0)
                    max = tempAmount;
                else
                    min = tempAmount;
                tempAmount = (min + max) / 2;
            }
            return max - this.quarterlyInvestAmount;
        },
        calcStartCapitalWantedIncome: function()
        {
            var financePlanCalculator = new FinancePlanCalculator(this.startCapitalAmount,this.quarterlyInvestAmount,this.startCapitalQuarterIncome,this.quarterlyInvestQuarterIncome,this.executionDate);
            financePlanCalculator.setTargetList(financePlan.targetList);
            financePlanCalculator.setLoanList(financePlan.loanList);


            var min = this.startCapitalQuarterIncome;
            var max = ${pfptags:getStartCapitalMaxWantedIncome()}/4/100;
            financePlanCalculator.setStartCapitalQuarterIncome(max);
            financePlanCalculator.calculate();
            var negativeBalanceList = financePlanCalculator.getNegativeBalanceList();
            if(negativeBalanceList.length > 0)
                return null;

            var tempIncome = (min + max) / 2;
            while(max - min > 0.00025)
            {
                financePlanCalculator.setStartCapitalQuarterIncome(tempIncome);
                financePlanCalculator.calculate();
                negativeBalanceList = financePlanCalculator.getNegativeBalanceList();
                if(negativeBalanceList.length == 0)
                    max = tempIncome;
                else
                    min = tempIncome;
                tempIncome = (min + max) / 2;
            }
            return max * 4 * 100;
        },
        calcQuarterlyInvestWantedIncome: function()
        {
            var financePlanCalculator = new FinancePlanCalculator(this.startCapitalAmount,this.quarterlyInvestAmount,this.startCapitalQuarterIncome,this.quarterlyInvestQuarterIncome,this.executionDate);
            financePlanCalculator.setTargetList(financePlan.targetList);
            financePlanCalculator.setLoanList(financePlan.loanList);


            var min = this.startCapitalQuarterIncome;
            var max = ${pfptags:getQuarterlyInvestMaxWantedIncome()}/4/100;
            financePlanCalculator.setQuarterlyInvestQuarterIncome(max);
            financePlanCalculator.calculate();
            var negativeBalanceList = financePlanCalculator.getNegativeBalanceList();
            if(negativeBalanceList.length > 0)
                return null;

            var tempIncome = (min + max) / 2;
            while(max - min > 0.00025)
            {
                financePlanCalculator.setQuarterlyInvestQuarterIncome(tempIncome);
                financePlanCalculator.calculate();
                negativeBalanceList = financePlanCalculator.getNegativeBalanceList();
                if(negativeBalanceList.length == 0)
                    max = tempIncome;
                else
                    min = tempIncome;
                tempIncome = (min + max) / 2;
            }
            return max * 4 * 100;
        },
        calcLoans: function()
        {
            return financePlan.negativeBalanceList[0].amount;
        }
    };

    var currentRecommendationResolver = null;

    function openConfirmWindowAmount(calback, param)
    {
        currentRecommendationResolver = function(){if (param == undefined) calback(); else calback(param);};
        $('.confirmApplyWantedAmount').hide();
        $('#confirmWidowWantedAmount_'+param).show();
        win.open('confirmApplyWantedAmount');
    }

    function openConfirmWindowIncome(calback, param)
    {
        currentRecommendationResolver = function(){if (param == undefined) calback(); else calback(param);};
        $('.confirmApplyIncome').hide();
        $('#confirmWidowIncome_'+param).show();
        win.open('confirmApplyIncome');
    }

    function applyChange()
    {
        if (currentRecommendationResolver != null)
            currentRecommendationResolver();

        return true;
    }

    function changeStartAmount(portfolioType)
    {
        $('input[name=field(changedPortfolio)]').val(portfolioType);
        var addAmount = $('#addAmountFor'+portfolioType).val();
        $('input[name=field(addAmount)]').val(addAmount);
        new CommandButton('button.changeStartAmount').click('',false);
    }

    function changeIncome(portfolioType)
    {
        $('input[name=field(changedPortfolio)]').val(portfolioType);
        var wantedIncome = $('#wantedIncomeFor'+portfolioType).val();
        $('input[name=field(wantedIncome)]').val(wantedIncome);
        new CommandButton('button.changeIncome').click('',false);
    }

    $(document).ready(function(){
        $('#bankRecommend .recomend .workspace-box').live("mouseenter",
            function(){
                $(this).addClass('lightGrayWithoutTopHover');
            });

        $('#bankRecommend .recomend .workspace-box').live("mouseleave",
            function(){
                $(this).removeClass('lightGrayWithoutTopHover');
            });
    });
</script>