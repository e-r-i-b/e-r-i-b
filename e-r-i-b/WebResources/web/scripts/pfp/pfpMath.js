var calculateBonus =
{
    beneficent: function(outcome, yearCount, bonus, devider){
        return 12*yearCount*outcome*bonus/devider*2;
    },
    aeroflot: function(outcome, yearCount, bonus, devider){
        return 12*yearCount*outcome*bonus/devider;
    },
    mts: function(outcome, yearCount, bonus, devider){
        return 12*yearCount*outcome*bonus/devider;
    },
    empty: function(outcome, yearCount, bonus, devider){
        return 0;
    }
};

function ProductAmount()
{
    this.totalAmount = 0;// обща€ сумма средств на продукте
    this.investment = 0; //инвестированные средства в продукт
    this.income = 0;     //доход принесенный продуктом
    this.add = function(anotherProductAmount)
    {
        this.totalAmount = this.totalAmount + anotherProductAmount.totalAmount;
        this.investment = this.investment + anotherProductAmount.investment;
        this.income = this.income + anotherProductAmount.income;
    };
};

var pfpMath =
{
    calculateInsuranceIncome: function(monthInPeriod, amount, income, quarterCount)
    {
        var quarterIncome = income/4/100;
        var productAmount = new ProductAmount();
        var totalAmountFactor = 0;
        var periodFactor = 0; //кака€ часть от вложений в страховку пприходитс€ на один квартал(1 - вложение необходимо делать каждый квартал
                                                                                            //  1/2 - в квартал необходимо вкладывать половину страхового взноса)
        if(!isNaN(monthInPeriod))
            periodFactor = 3 / monthInPeriod;

        productAmount.investment = amount * (quarterCount * periodFactor + 1);

        if(quarterIncome > 0)
        {
            totalAmountFactor = periodFactor * (1 + (1 + quarterIncome) * (1 - Math.pow(1 + quarterIncome, quarterCount-1)) / (1-(1+quarterIncome)));
            productAmount.totalAmount = amount * Math.pow(1 + quarterIncome, quarterCount) + amount * totalAmountFactor;
        }
        else
            productAmount.totalAmount = productAmount.investment;

        productAmount.income = productAmount.totalAmount - productAmount.investment;
        return productAmount;
    },
    calculatePensionIncome: function(startAmount, quarterAmount, income, quarterCount)
    {
        var quarterIncome = income/4/100;
        var productAmount = new ProductAmount();
        var totalAmountFactor = 0;

        productAmount.investment = startAmount + quarterAmount * (quarterCount);

        if(quarterIncome > 0)
        {
            totalAmountFactor =  (1 + (1 + quarterIncome) * (1 - Math.pow(1 + quarterIncome, quarterCount-1)) / (1-(1+quarterIncome)));
            productAmount.totalAmount = startAmount * Math.pow(1 + quarterIncome, quarterCount) + quarterAmount * totalAmountFactor;
        }
        else
            productAmount.totalAmount = productAmount.investment;

        productAmount.income = productAmount.totalAmount - productAmount.investment;
        return productAmount;
    },
    calculateIncome: function(portfolioType, amount, income, quarterCount)
    {
        var productAmount = new ProductAmount();
        var quarterIncome = income/4/100;
        if(portfolioType == 'START_CAPITAL')
        {
            productAmount.investment = amount;
            productAmount.totalAmount = amount * Math.pow(1 + quarterIncome, quarterCount);
            productAmount.income = productAmount.totalAmount - productAmount.investment;
        }
        else if(portfolioType == 'QUARTERLY_INVEST')
        {
            productAmount.investment = amount * quarterCount;
            if(quarterIncome>0)
                productAmount.totalAmount = amount + amount * (1 + quarterIncome) * (1 - Math.pow(1 + quarterIncome, quarterCount-1)) / (1-(1+quarterIncome));
            else
                productAmount.totalAmount = productAmount.investment;
            productAmount.income = productAmount.totalAmount - productAmount.investment;
        }
        return productAmount;
    },
    //вычисл€ет сумму вложений в портфель portfolioType дл€ того чтобы через quarterCount кварталов в портфеле было wantedAmount
    calculateStartAmount: function(portfolioType,wantedAmount, income, quarterCount)
    {
        var quarterIncome = income/4/100;
        var startAmount = null;
        if(portfolioType == 'START_CAPITAL')
        {
            startAmount = wantedAmount/ Math.pow(1+quarterIncome,quarterCount);
        }
        else if(portfolioType == 'QUARTERLY_INVEST')
        {
            if(quarterIncome > 0)
                startAmount = wantedAmount / (1 + (1 + quarterIncome) * (1 - Math.pow(1 + quarterIncome,quarterCount-1)) / (1-(1+quarterIncome)));
            else
                startAmount = wantedAmount / quarterCount;
        }
        return startAmount;
    },
    //вычисл€ет доходность портфел€ portfolioType при которой в данном портфеле при стартовых вложени€х startAmount
    //через quarterCount будет wantedAmount
    //если wantedAmount не может быть достигнута даже при maxIncome, то возвращаем null
    calculateWantedIncome: function(portfolioType, wantedAmount, startAmount, quarterCount, maxIncome)
    {
        var wantedIncome = null;
        if(portfolioType == 'START_CAPITAL')
        {
            wantedIncome = (Math.pow(wantedAmount/startAmount,1/quarterCount) - 1) * 4 * 100;
            if(wantedIncome > maxIncome)
                wantedIncome = null;
        }
        else if(portfolioType == 'QUARTERLY_INVEST')
        {
            //примен€ем метод половинного делени€
            var minIncm = 0;
            var maxIncm = maxIncome;
            var tempAmount = this.calculateIncome('QUARTERLY_INVEST',startAmount,maxIncm,quarterCount);
            if(tempAmount.totalAmount < wantedAmount)
                return wantedIncome;
            //ищем с точностью до 0.1
            while(maxIncm - minIncm > 0.1)
            {
                wantedIncome = (minIncm + maxIncm)/2;
                tempAmount = this.calculateIncome('QUARTERLY_INVEST',startAmount,wantedIncome,quarterCount);
                if(tempAmount.totalAmount > wantedAmount)
                    maxIncm = wantedIncome;
                else
                    minIncm = wantedIncome;
            }
        }
        return wantedIncome;
    },
    /**
     * ¬ычисл€ет доходность счЄта и спасибо
     * @param amount внесЄнные средства
     * @param accountPercent процентна€ ставка по вкладу
     * @param thanksPercent процентна€ ставка спасибо
     * @param yearCount количество лет
     */
    calculateIncomeForCreditCard : function(amount, accountPercent, thanksPercent, yearCount)
    {
        var accountProductAmount = new ProductAmount();
        var thanksProductAmount = new ProductAmount();
        if(accountPercent)
        {
            accountProductAmount.investment = amount;
            accountProductAmount.totalAmount = amount*Math.pow((1+ accountPercent/400.0), 4*yearCount);
            accountProductAmount.income = accountProductAmount.totalAmount - accountProductAmount.investment;
        }

        if(thanksPercent)
        {
            thanksProductAmount.investment = amount*yearCount*12;
            thanksProductAmount.income = yearCount*12*amount*thanksPercent/100.0;
            thanksProductAmount.totalAmount = thanksProductAmount.investment + thanksProductAmount.income;
        }

        return {
            account: accountProductAmount,
            thanks : thanksProductAmount
        };
    },

    /**
     * ¬ычисл€ет доходность счЄта и спасибо
     * @param amount внесЄнные средства
     * @param accountPercent процентна€ ставка по вкладу     
     * @param yearCount количество лет
     */
    calculateIncomeForCreditCardByType : function(amount, yearCount, type)
    {
        var accountProductAmount = new ProductAmount();
        var res = calculateBonus[type.type](amount, yearCount, type.bonus, type.devider);
        accountProductAmount.investment = amount*yearCount*12;
        accountProductAmount.income = res;
        accountProductAmount.totalAmount = accountProductAmount.investment + accountProductAmount.income;
        return accountProductAmount;
    },
    /**
     * ƒобавл€ет к дате заданное количество кварталов
     * @param date - дата
     * @param quarterCount - количество кварталов, которое необходимо добавить
     */
    addQuarters:function(date,quarterCount)
    {
        date.setMonth(date.getMonth() + quarterCount * 3);
    },
    /**
     * ¬озвращает следующий квартал
     * @param date - дата
     * @returns {Date} - следующий квартал дл€ переданной даты
     */
    nextQuarter:function(date)
    {
        var result = new Date(date);
        result.setMonth(result.getMonth() + 3);
        return result;
    },
    /**
     * ¬озвращает последний квартал дл€ переданного года
     * @param date - дата
     * @returns {Date} - следующий квартал дл€ переданной даты
     */
    getLastQuarterForYear:function(date)
    {
        var result = new Date(date);
        result.setMonth(9);
        return result;
    },
    getDiffInQuarters: function(date1,date2)
    {
        var years = date2.getFullYear() - date1.getFullYear();
        var quarters = this.getQuarter(date2) - this.getQuarter(date1);
        return years*4 + quarters;
    },
    getQuarter: function(date)
    {
        return parseInt(date.getMonth()/3) + 1;
    },
    /**
     * ¬ычисл€ем суммы платежа по кредиту в квартал
     * @param loanAmount - сумма кредита
     * @param loanRate - ставка по кредиту(годова€)
     * @param loanDuration - срок кредита в кварталах
     * @returns {number} платеж по кредиту в квартал
     */
    calculateQuarterPaymentForLoan: function(loanAmount,loanRate,loanDuration)
    {
        if(isNaN(loanAmount))
            return 0;

        var quarterRate = loanRate/4/100;
        var k = Math.pow(1+quarterRate,loanDuration);
        return loanAmount * quarterRate * k / (k-1);
    }
};

var insuranceCalculatorList = new Array();

function getInsuranceCalculator(name)
{
    return insuranceCalculatorList[name];
}

function addInsuranceCalculator(name, insuranceSum, period, periodicity, resultContainer)
{
    var calculator = new InsuranceCalculator(insuranceSum, period, periodicity, resultContainer);
    insuranceCalculatorList[name] = calculator;
    calculator.updateCalculatingInsuranceSum();
}

function InsuranceCalculator(insuranceSum, periodicity, period, resultContainer)
{
    var insuranceSumValue = parseFloatVal(insuranceSum);
    var periodValue = parseFloatVal(period);
    var periodicityValue = parseFloatVal(periodicity);

    function calc()
    {
        if(isNaN(insuranceSumValue))
            return 0;

        if (periodicityValue == 0)
            return insuranceSumValue;

        return insuranceSumValue / (periodValue * 12 / periodicityValue);
    };

    this.updateCalculatingInsuranceSum = function()
    {
        resultContainer.html(FloatToString(calc(), 2, " "));
    };

    this.updatePeriodValue = function(newValue)
    {
        periodValue = parseFloatVal(newValue);
        this.updateCalculatingInsuranceSum();
    };

    this.updatePeriodicityValue = function(newPeriodicityValue, newPeriodValue)
    {
        periodicityValue = parseFloatVal(newPeriodicityValue);
        if (newPeriodValue != undefined)
            periodValue = parseFloatVal(newPeriodValue);   
        this.updateCalculatingInsuranceSum();
    };

    this.updateInsuranceSumValue = function(newValue)
    {
        insuranceSumValue = parseFloatVal(newValue);
        this.updateCalculatingInsuranceSum();
    };

};
