<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<!DOCTYPE html>
<html>
<head>
<title>Mapping ducks</title>
<link rel="stylesheet" type="text/css" href="/css/style.css" />
<script type="text/javascript" src="/js/decorate.js"></script>
<script type="text/javascript" src="http://maps.google.com/maps/api/js?key=AIzaSyC3B3dOo-JW2NONXxRE29gpYGyVi8nLAbw&sensor=false"></script>
<script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.10.2/jquery.min.js"></script>
<script type="text/javascript">
	window.onload = function(){
		if (window.jQuery==undefined) {
			document.write("<script src=/js/jquery-1.10.2.min.js><\/script>");
	     }
		layoutinit();
	 }
</script>
<script type="text/javascript">
    var map;
    var geocoder; //2a
    var initialLocation;
    function initialize() {
    	initialLocation = new google.maps.LatLng(40.18106193018659,116.64238214492798);
        geocoder = new google.maps.Geocoder(); //2b
        var duckOptions = {
            zoom: 12,
            center: initialLocation,
            mapTypeId: google.maps.MapTypeId.HYBRID
        };
        map = new google.maps.Map(document.getElementById("map_canvas"), duckOptions);
        var marker = new google.maps.Marker({
            position: initialLocation,
            map: map
        });
        google.maps.event.addListener(map, 'click', function(event) {
            placeMarker(event.latLng);
        });
    }
     
    function placeMarker(location) {
        var marker = new google.maps.Marker({
            position: location,
            map: map
        });
        //3 - all code until
        $("#longitude").val(location.lng()); 
        $("#latitude").val(location.lat());
        geocoder.geocode( { 'latLng': location}, function(results, status) {
            if (status == google.maps.GeocoderStatus.OK) {
                $("#gaddress").val(results[0].formatted_address);
            } else {
                alert("Geocode was not successful for the following reason: " + status);
            }
        });
        //the end of (3)
        map.setCenter(location);
    }
    
    $(function(){
    	initialize();
    	layoutinit();
    });
</script>
</head>
<body>
   	<div id="body">
		<div id="site-logo"></div>
		<div id="site-menu"></div>
		<div id="site-content">
			<div id="map_canvas" style="width:100%; height:600px"></div>
		    <div id="marker_data">		        
		            <table style="background:white; width:100%;">
		                <tr>
		                    <td>Latitude</td>
		                    <td><input type="text" name="latitude" id="latitude" size="66" /></td>
		                </tr>
		                <tr>
		                    <td>Longitude</td>
		                    <td><input type="text" name="longitude" id="longitude" size="66" /></td>
		                </tr>
		                <tr>
		                    <td>Address</td>
		                    <td><input type="text" name="longitude" id="gaddress" size="66" /></td>
		                </tr>
		            </table>		       		 
		    </div>
		</div>
		<div id="site-footer"></div>
	</div>     
</body>
</html>