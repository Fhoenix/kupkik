<%@ page import="com.kupkik.model.*" %>

<html>
  <body>
  <h1>kupkik</h1> 

	Hello <%= ((UserWithPassword)request.getAttribute("currentUser")).getName()  %> 

  </body>
</html>