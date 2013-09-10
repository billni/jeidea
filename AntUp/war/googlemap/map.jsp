<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<!DOCTYPE html>
<html>
<head>
<title>Mapping ducks</title>
<meta charset="utf-8">
<link rel="stylesheet" type="text/css" href="/css/style.css" />
<script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.10.2/jquery.min.js"></script>
<script type="text/javascript">
	window.onload = function(){
		if (window.jQuery==undefined) {
			document.write("<script src=/js/jquery-1.10.2.min.js><\/script>");
	     }
	 }
</script>
<script type="text/javascript">
    var map;
    var geocoder; //2a
    var initialLocation;
    function initialize() {
    	initialLocation = new google.maps.LatLng(-44.6895642,169.1320571);
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
    
    function loadScript() {
    	  var script = document.createElement("script");
    	  script.type = "text/javascript";
    	  script.src = "http://maps.google.com/maps/api/js?key=AIzaSyC3B3dOo-JW2NONXxRE29gpYGyVi8nLAbw&sensor=false&callback=initialize";
    	  document.body.appendChild(script);
    	}
    window.onload = loadScript;
</script>
</head>
<body>
    <h1>Mapping Ducks</h1>
    <div id="map_canvas"></div>
    <div id="marker_data">
        <form id="createForm" action="/new" method="post" accept-charset="utf-8" style="background:white">
            <table>
                <tr>
                    <td>Title</td>
                    <td><input type="text" name="title" id="title" size="66"/></td>
                </tr>
                <tr>
                    <td>Description</td>
                    <td><textarea rows="4" cols="46" name="description" id="description"></textarea>
                </tr>
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
            </table><br/><br/>
            <input type="submit" value="Save"/>
        </form>
    </div>
</body>
</html>