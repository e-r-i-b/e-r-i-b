require(['../requirecfg'], function()
{
	function start()
	{
		setTimeout(function()
		{
			if (window['ymaps'])
			{
				ymaps.ready(function()
				{
					require(['sberbankMaps', 'marionette'], function(maps, Mn)
					{
						if (ymaps.geolocation)
						{
							/**
							 * Апликация которое все это дело заводит
							 */
							var YMapsApplication = new (Mn.Application.extend
							({
								onStart : function(options)
								{
									(new maps.viewType(options)).render();
								}
							}));

							YMapsApplication.start
							({
								ymaps      : ymaps,
								serviceUrl : document.config.serviceUrl
							});
						}
					});
				});
			}
			else
			{
				setTimeout(function()
				{
					start();

				}, 500);
			}

		}, 500);
	}

	start();
});
