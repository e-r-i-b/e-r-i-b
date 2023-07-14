// Класс для обеспечения плавного перемешения блоков по экрану за скролом страници
function DivFloat(divId, topLimit, topMargin)
{
    // Верхняя граница скола
    this.topLimit = (topLimit == null)?63:topLimit;
    // Отступ сверху
    this.topMargin = (topMargin == null)?12:topMargin;
    // Объект для скролла
    this.obj;
    // Таймер
    this.floatTimer;
    // ID div`a
    this.id = divId;

    // функция инициализации необходимо выполнить после загрузки DOM
    this.init = function (self)
    {
        if (self == null) self = this;
        self.obj = ensureElement(self.id);
        self.move();
    };
    // Метод обеспечивающий эфект плавного скольжения
    this.floatEffect = function  (from, to, self)
    {
        if (self == null) self = this;
        var hep = (to - from) / 8;
        //защита от зацикливания
        if (hep < 5 && hep > 0 ) hep = 5;
        if (hep < 0 && hep > -5 ) hep = -5;

        var halfTo = from + hep;
        // правка погрешности смещения
        if (halfTo > to && hep > 0 || halfTo < to && hep < 0)
            halfTo = to;
        self.obj.style.top = halfTo + "px";
        if (hep > 0 && halfTo < to || hep < 0 && halfTo > to)
            this.floatTimer = setTimeout(function ()
            {
                self.floatEffect(halfTo, to, self);
            }, 20);
    };
    // Метод для обеспечения привязки дива к определеннуму месту
    this.move = function ()
        {
           if ( !(this.obj != undefined && this.obj != null)) return ;
           // Текущий отступ от начала документа
           var scroll= getScrollTop();
           // Размер окна браузера
           var screen = screenSize();
           // ычисление текущего положения блока
           var fromPx = this.topLimit;
           if (this.obj.style.top != '')
           {
               var reg = /\d*/
               fromPx = parseInt( reg.exec(this.obj.style.top) );
           }
           // Если размер экрана меньше размера блока, то фиксируем его на месте
           var height = this.obj.offsetHeight;
            if (height + this.topMargin >= screen.h)
              scroll = fromPx-this.topMargin;
            // если отступ от начала документа меньше лимита то фиксируем див на лимите
            if (scroll < this.topLimit) scroll = this.topLimit - this.topMargin;

            var toPx = scroll+this.topMargin;
            //Если положение не сменилось то не выполняем эфект скольжения
            if( fromPx != toPx);
            {
            clearTimeout(this.floatTimer);
            this.floatEffect (fromPx, toPx, this);
            }

        };
    // Метод для привязки объекта к событию скрола
    this.doOnScroll = function ()
    {
        var scrollTimer;
        var scroll = window.onscroll;
        var self = this;

        window.onscroll = function () {
            if (scroll!=null) scroll();
            clearTimeout(scrollTimer)
            scrollTimer = setTimeout(function(){ self.move() }, 50); 
        };
    }
    // Запуск инициализации после завершения загрузки документа
    if (typeof(divId) != 'string')
        this.obj = divId;
    else
        doOnLoad(this.init, this);
}