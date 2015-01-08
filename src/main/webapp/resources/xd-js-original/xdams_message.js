jQuery.i18n.properties({  
	  name:'Messages',
	  path:'path/to/i18n/files/',
	  mode:'map',
	  language:'ru_RU',
	  callback: function() {
	    // I specified mode: 'map' because the 'vars' mode
	    // will add a lot of global JS vars to the page.

	    // Accessing a simple value through the map
	    $.i18n.prop('msg_hello');

	    alert($.i18n.prop('msg_hello'));
	  }
});	