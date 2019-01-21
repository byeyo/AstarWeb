<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>

<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style>
#canvas {
  margin-top: 10%;
  margin-left: 10%;
}
</style>
 <%
    String strObs = (String)request.getAttribute("obsJsonStr");
    String strPath = (String)request.getAttribute("pathJsonStr");
    String strsize = (String)request.getAttribute("mapSize");
    %>
</head>
 <script>
  function draw(unitSize) {
	  var canvas = document.getElementById('canvas');
	  var size = <%= strsize %>;
	  var json = <%= strPath %>;
	  var obs = <%=  strObs %>;
	  if (canvas.getContext) {
	    var ctx = canvas.getContext('2d');
	    drawObs(obs, ctx, unitSize);
	    drawGrid(size.x, unitSize, ctx);
		var length = drawPath(json, ctx , unitSize);
		console.log(length);
	  }
	  
	}
  function drawGrid(mapSize, unitSize,ctx) {
	  ctx.beginPath();
	  for(var i = 0 ; i <= mapSize ; i ++){
		 ctx.moveTo(0, i * unitSize);
		 ctx.lineTo(mapSize * unitSize, i * unitSize);
	  }
	   for(var i = 0 ; i <= mapSize ; i ++){
			 ctx.moveTo(i * unitSize , 0);
			 ctx.lineTo(i * unitSize, mapSize * unitSize);
	  } 
	  ctx.strokeStyle = 'blue'; 
	  ctx.stroke();
	  ctx.closePath();
  }
  function drawPath(json, ctx, size){
	   var length = 0;
	   var x1 = json[0].x * size;
	   var y1 = json[0].y * size; 
	   ctx.beginPath();
	   ctx.arc(json[0].x * size,json[0].y * size,3,0,Math.PI*2,true);
	   console.log(x1+" "+y1);
	    for( var i = 1 ; i < json.length ; i ++){
			var x = json[i].x * size;
			var y = json[i].y * size;
			ctx.lineTo(x, y );
			//ctx.arc(x,y,3,0,Math.PI*2,true);
			//ctx.fillText((x+11)+','+(y+11), x, y);
			ctx.moveTo(x, y );
			length += Math.sqrt(Math.abs(x - x1) * Math.abs(x - x1) + Math.abs(y - y1) * Math.abs(y - y1));
			x1 = x;
			y1 = y ;
			console.log(x/size+" "+y/size);
		}	
	    length /= size;
	    ctx.strokeStyle = 'red'; 
	    ctx.lineWidth = 2 ;
	    ctx.strokeStyle = 'r'; 
	    ctx.font = '35px Arial';
	   // ctx.fillText(length,500, 500);
	    ctx.stroke();
	    ctx.closePath();	
	    
	    
	    return length;
  }
  function drawObs(json, ctx, size){
	   ctx.beginPath();
	   ctx.fillStyle = 'gray'; 
	    for( var i = 0 ; i < json.length ; i ++){
			var x = json[i].x * size;
			var y = json[i].y * size;
			ctx.fillRect(x, y, size, size);
		}	
	   
	    ctx.stroke();
	    ctx.closePath();	   	   
 }
  </script>
<body onload="draw(10)">
    <canvas id="canvas" width="150" height="100"></canvas>
</body>
 
</html>