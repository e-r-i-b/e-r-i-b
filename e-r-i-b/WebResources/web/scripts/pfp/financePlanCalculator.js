function FinancePlanCalculator(startCapitalAmount,quarterlyInvestAmount,
                                     startCapitalQuarterIncome,quarterlyInvestQuarterIncome,
                                     startFinancePlanDate)
{
    this.minNegativeAmount = -0.005;
    this.defaultQuarterCount = 10;
    this.QUARTERS_IN_YEAR = 4;

    //данные на основе которых производим вычисления
    this.startCapitalAmount = startCapitalAmount;
    this.quarterlyInvestAmount = quarterlyInvestAmount;
    this.startCapitalQuarterIncome = startCapitalQuarterIncome;
    this.quarterlyInvestQuarterIncome = quarterlyInvestQuarterIncome;
    this.startFinancePlanDate = startFinancePlanDate;
    this.endFinancePlanDate = startFinancePlanDate;
    this.lastInListTargetDate = null;

    //внутринние данные для рассчетов
    this.loanQuarterAmountList = null;
    this.loanList = [];
    this.targetQuarterAmountList = null;
    this.loanQuarterPaymentList = null;
    this.quarterCount = this.defaultQuarterCount;
    this.yearStep = 1;
    this.lastTarget = null; // цель "создание пенсионных накоплений"

    //выходные данные
    this.totalAmountList = [];

    this.negativeBalanceList = [];

    this.setStartCapitalAmount = function(startCapitalAmount)
    {
        this.startCapitalAmount = startCapitalAmount;
    };

    this.setQuarterlyInvestAmount = function(quarterlyInvestAmount)
    {
        this.quarterlyInvestAmount = quarterlyInvestAmount;
    };

    this.setStartCapitalQuarterIncome = function(startCapitalQuarterIncome)
    {
        this.startCapitalQuarterIncome = startCapitalQuarterIncome;
    };

    this.setQuarterlyInvestQuarterIncome = function(quarterlyInvestQuarterIncome)
    {
        this.quarterlyInvestQuarterIncome = quarterlyInvestQuarterIncome;
    };

    /**
     * Установить список кредитов клиента
     * Сам список кредитов нам не интересен, поэтому сразу рассчитаем суммы кредитов по кварталам
     * и суммы платежей по кредитам по кварталам
     * @param loanList - список кредитов
     */
    this.setLoanList = function(loanList)
    {
        this.loanQuarterAmountList = [];
        this.loanList = loanList;
        var personLoan = null;
        for(var key in loanList)
        {
            personLoan = loanList[key];
            var loanQuarterSumm = this.loanQuarterAmountList[personLoan.startDate];
            if(loanQuarterSumm)
                this.loanQuarterAmountList[personLoan.startDate] = loanQuarterSumm + personLoan.amount;
            else
                this.loanQuarterAmountList[personLoan.startDate] = personLoan.amount;
        }

        this.loanQuarterPaymentList = [];
        for(var key in loanList)
        {
            personLoan = loanList[key];
            var quarter = pfpMath.nextQuarter(personLoan.startDate);
            var endQuarter = pfpMath.nextQuarter(personLoan.endDate);
            while(quarter.getTime() < endQuarter.getTime())
            {
                var loanQuarterPayment = this.loanQuarterPaymentList[quarter];
                if(loanQuarterPayment)
                    this.loanQuarterPaymentList[quarter] = loanQuarterPayment + personLoan.quarterPayment;
                else
                    this.loanQuarterPaymentList[quarter] = personLoan.quarterPayment;
                quarter = pfpMath.nextQuarter(quarter);
            }
        }
    };

    /**
     * Установить список целей.
     * Сами цели нам не интересны, поэому хранить их здесь не будем.
     * Посчитаем суммы, необходимые для целей по кварталам, их и будем хранить в этом объекте.
     * @param targetList - список целей
     */
    this.setTargetList = function(targetList)
    {
        this.targetQuarterAmountList = [];
        this.lastTarget = null;
        var personTarget = null;
        for(var key in targetList)
        {
            personTarget = targetList[key];
            var targetQuarterAmount = this.targetQuarterAmountList[personTarget.plannedDate];
            if(targetQuarterAmount)
                this.targetQuarterAmountList[personTarget.plannedDate] = targetQuarterAmount + personTarget.amount;
            else
                this.targetQuarterAmountList[personTarget.plannedDate] = personTarget.amount;
            if(personTarget.veryLast)
                this.lastTarget = personTarget;
        }

        var lastTargetDate = null;
        for(key in targetList)
        {
            personTarget = targetList[key];
            if(lastTargetDate == null || lastTargetDate.getTime() < personTarget.plannedDate.getTime())
                lastTargetDate = personTarget.plannedDate;
        }
        if(lastTargetDate == null)
            this.quarterCount = this.defaultQuarterCount;
        else
        {
            this.lastInListTargetDate = lastTargetDate;
            this.quarterCount = this.calcQuarterCount(lastTargetDate);
        }
    };

    this.calcQuarterCount = function(lastTargetDate)
    {
        var quarterCount = pfpMath.getDiffInQuarters(this.startFinancePlanDate, lastTargetDate);
        if(quarterCount <= 4 * this.QUARTERS_IN_YEAR)
        {
            this.endFinancePlanDate = lastTargetDate;
            return quarterCount;
        }

        this.yearStep = 1;
        // Если период больше 26-ти лет, то шаг равен 16-ти кварталам (4 года).
        if(quarterCount > 26 * this.QUARTERS_IN_YEAR)
        {
            this.yearStep = 4;
        }
        // Если период больше 8-ми лет и меньше 26 лет, то шаг равен 8-ми кварталам (2 года).
        else if(quarterCount > 8 * this.QUARTERS_IN_YEAR)
        {
            this.yearStep = 2 ;
        }

        var lastYearQuarter = pfpMath.getLastQuarterForYear(lastTargetDate);
        if((lastYearQuarter.getFullYear() - this.startFinancePlanDate.getFullYear()) % this.yearStep != 0)
        {
            var yearCount = (lastYearQuarter.getFullYear() - this.startFinancePlanDate.getFullYear()) % this.yearStep;
            lastYearQuarter.setFullYear(lastYearQuarter.getFullYear() + this.yearStep - yearCount);
        }
        this.endFinancePlanDate = lastYearQuarter;

        return pfpMath.getDiffInQuarters(this.startFinancePlanDate, lastYearQuarter) + 1;
    };

    this.getYearStep = function()
    {
        return this.yearStep;
    };

    this.getYearCount = function()
    {
        return this.endFinancePlanDate.getFullYear() - this.startFinancePlanDate.getFullYear();
    };

    this.getQuartersInPeriod = function(fromYear, toYear)
    {
        var fromDate = this.startFinancePlanDate;
        var toDate = this.lastInListTargetDate;

        if (fromDate.getFullYear() < fromYear)
        {
            fromDate = new Date(fromYear, 0, 1);
        }
        if (toDate.getFullYear() > toYear)
        {
            toDate = new Date(toYear, 9, 1);
        }

        return pfpMath.getDiffInQuarters(fromDate, toDate);
    };

    this.getTotalAmount = function()
    {
        return this.totalAmountList;
    };

    this.getNegativeBalanceList = function()
    {
        return this.negativeBalanceList;
    };

    this.calculate = function()
    {
        this.totalAmountList = [];
        this.negativeBalanceList = [];
        var startCapitalAmountList = [];//стоимость портфеля стартовый капитал по кварталам
        var quarterlyInvestAmountList = []; //стоимость портфеля ежеквартальные вложения по кварталам

        var currentQuarterDate = this.startFinancePlanDate;

        startCapitalAmountList[currentQuarterDate] = this.startCapitalAmount;
        quarterlyInvestAmountList[currentQuarterDate] = 0;

        var totalAmount = {};
        totalAmount.date = currentQuarterDate;
        totalAmount.amount = this.startCapitalAmount;
        this.totalAmountList.push(totalAmount);

        var factor = 0;
        for(var i=1; i <= this.quarterCount; i++)
        {
            var lastTargetFactor = 0;
            var prevQuarterDate = currentQuarterDate;
            currentQuarterDate = pfpMath.nextQuarter(prevQuarterDate);

            var startCapitalPrevQuarterAmount = startCapitalAmountList[prevQuarterDate];
            var startCapitalQuarterAmount = startCapitalPrevQuarterAmount * (1 + this.startCapitalQuarterIncome);

            var loanQuarterPayment = this.loanQuarterPaymentList[currentQuarterDate];
            if(!loanQuarterPayment)
                loanQuarterPayment = 0;

            var quarterlyInvestPrevQuarterAmount = quarterlyInvestAmountList[prevQuarterDate];
            var currentQuarterlyInvestAmount = this.quarterlyInvestAmount - loanQuarterPayment;
            var quarterlyInvestQuarterAmount = currentQuarterlyInvestAmount + quarterlyInvestPrevQuarterAmount * (1 + this.quarterlyInvestQuarterIncome);
            //quarterlyInvestQuarterAmountList.put(i, currentQuarterlyInvestAmount);

            var currentQuarterLoanAmount = this.loanQuarterAmountList[currentQuarterDate];
            if (currentQuarterLoanAmount)
                factor = factor + currentQuarterLoanAmount;

            /*если цель не создание пенсионных накоплений, то на графике отображаем сумму за вычитом цели*/
            if (this.lastTarget == null || currentQuarterDate.getTime() < this.lastTarget.plannedDate.getTime())
            {
                var currentQuarterTargetAmount = this.targetQuarterAmountList[currentQuarterDate];
                if (currentQuarterTargetAmount)
                    factor = factor - currentQuarterTargetAmount;
            }
            else  /* иначе учитываем сумму цели только в списке кварталов с отрицательным балансом */
            {
                var currentQuarterTargetAmount = this.targetQuarterAmountList[currentQuarterDate];
                if (currentQuarterTargetAmount)
                    lastTargetFactor = currentQuarterTargetAmount;
            }

            if (factor < 0)
            {
                var totalSum = startCapitalQuarterAmount + quarterlyInvestQuarterAmount;
                var factorAbsoluteValue = Math.abs(factor);
                if (totalSum <= factorAbsoluteValue)
                {
                    startCapitalQuarterAmount = 0;
                    quarterlyInvestQuarterAmount = 0;
                    factor = factor + totalSum;
                }
                else
                {
                    var startCapitalRatio = startCapitalQuarterAmount / totalSum;
                    var quarterlyInvestRatio = quarterlyInvestQuarterAmount / totalSum;
                    startCapitalQuarterAmount = startCapitalQuarterAmount - startCapitalRatio * factorAbsoluteValue;
                    quarterlyInvestQuarterAmount = quarterlyInvestQuarterAmount - quarterlyInvestRatio * factorAbsoluteValue;
                    factor = 0;
                }
            }

            startCapitalAmountList[currentQuarterDate] = startCapitalQuarterAmount;
            quarterlyInvestAmountList[currentQuarterDate] =  quarterlyInvestQuarterAmount;

            totalAmount = {};
            totalAmount.date = currentQuarterDate;
            totalAmount.amount = startCapitalQuarterAmount + quarterlyInvestQuarterAmount + factor;
            this.totalAmountList.push(totalAmount);

            /* заполняем список теми кварталами, что дают отрицательный результат с учетом пенсионных накоплений */
            if(totalAmount.amount - lastTargetFactor <= this.minNegativeAmount)
            {
                this.negativeBalanceList.push({
                    date:totalAmount.date,
                    amount:Math.abs(totalAmount.amount - lastTargetFactor)
                });
            }
        }
    };
    
    this.canAddLoan = function(loan)
    {
        var currentLoanQuarterPaymentList = [];
        var loanList = [];
        //строим актуальный список кредитов
        for (var k in this.loanList)
            loanList[k] = this.loanList[k];
        var id = loan.id?loan.id:-1;
        loanList[id] = loan;
        //строим поквартальный список выплат по кредитам
        for(var key in loanList)
        {
            var personLoan = loanList[key];
            var quarter = pfpMath.nextQuarter(personLoan.startDate);
            var endQuarter = pfpMath.nextQuarter(personLoan.endDate);
            while(quarter.getTime() < endQuarter.getTime())
            {
                var loanQuarterPayment = currentLoanQuarterPaymentList[quarter];
                if(loanQuarterPayment)
                    currentLoanQuarterPaymentList[quarter] = loanQuarterPayment + personLoan.quarterPayment;
                else
                    currentLoanQuarterPaymentList[quarter] = personLoan.quarterPayment;
                quarter = pfpMath.nextQuarter(quarter);
            }
        }
        //проверяем, все ли во кварталы пользователь сможет оплатить кредиты
        for (var n in currentLoanQuarterPaymentList)
        {
            if (currentLoanQuarterPaymentList[n] > this.quarterlyInvestAmount)
                return false;
        }
        return true;
    }
}
