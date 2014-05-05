jQuery(document).ready(function(){
	$('#viewPreview').change(function() {
		if($(this).is(":checked")) {
		   $('input[name=viewInputPreview]').remove();
		   $('#searchForm').append('<input type="text" name="viewInputPreview" value="true"/>');
		   $('#searchForm').append('<input type="text" name="pageName" value="titlePhoto"/>');
		   $('#searchForm').submit();
		}else{
			$('input[name=viewInputPreview]').remove();
			$('input[name=pageName]').remove();
			$('#searchForm').submit();
		}
	});
var jcarousel = $('.jcarousel');

jcarousel.on('jcarousel:reload jcarousel:create', function () {
	var width = jcarousel.innerWidth();
	if (width >= 600) {
			width = width / 3;
	} else if (width >= 350) {
		width = width / 2;
	}
	jcarousel.jcarousel('items').css('width', width + 'px');
	}).jcarousel({
		wrap: 'circular'
	   });

$('.jcarousel-control-prev').jcarouselControl({
		target: '-=1'
	});

$('.jcarousel-control-next').jcarouselControl({
		target: '+=1'
	});

$('.jcarousel-pagination').on('jcarouselpagination:active', 'a', function() {
		$(this).addClass('active');
	}).on('jcarouselpagination:inactive', 'a', function() {
		$(this).removeClass('active');
	}).on('click', function(e) {
		e.preventDefault();
	}).jcarouselPagination({
		perPage: 1,
		item: function(page) {
			return '<a href="#' + page + '">' + page + '</a>';
		}
	});



});