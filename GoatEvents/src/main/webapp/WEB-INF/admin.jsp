<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet"
	href="https://cdn.jsdelivr.net/npm/bootstrap@4.5.3/dist/css/bootstrap.min.css"
	integrity="sha384-TX8t27EcRE3e/ihU7zmQxVncDAy5uIKz4rEkgIXeMed4M0jlfIDPvg6uqKI2xXr2"
	crossorigin="anonymous">
<link rel="stylesheet" href="resources/styles.css">
<link rel="stylesheet" href="resources/signUpStyles.css">
<link rel="stylesheet" href="resources/homeStyles.css">
<link rel="stylesheet" href="resources/searchstyles.css">
<meta charset="UTF-8">
<title>User Page (Login)</title>
</head>
<body><div>
	<header>
		<c:choose>
			<c:when test="${empty sessionScope.loginUser}">
				<ul id="menu">
					<li><a href="home.do"><img
							src="https://svgsilh.com/svg/44670.svg" height=35px></a></li>
					<li>
						<div class="search-container">
							<form id="searchForm" action="search.do">
								<input id="searchText" type="text" placeholder="Search.."
									name="search">
								<button id="searchSubmit" type="submit">Search</button>
							</form>
						</div>
					</li>
					<li id="signup"><a href="signUpInput.do">Sign up FREE</a></li>
					<li id="drop" class="dropdown"><input id="dropcheck"
						class="dropcheck" type="checkbox"> <label for="dropcheck"
						class="dropbtn">Log in ▼</label>
						<div class="dropdown-content">
							<form action="login.do" method="POST">
								<fieldset id="inputs">
									<input id="email" type="text" name="email"
										placeholder="Your email address" required> <input
										id="password" type="password" name="password"
										placeholder="Password" required>
								</fieldset>
								<fieldset id="actions">
									<input type="submit" id="submit" value="Log in">
								</fieldset>
							</form>
						</div></li>
				</ul>
			</c:when>
			<c:otherwise>
				<ul id="menu">
					<li><a href="home.do"><img
							src="https://svgsilh.com/svg/44670.svg" height=35px></a></li>
					<li id="signup"><a href="user.do"><img
							src="${sessionScope.loginUser.image}" height=45px width=45px></a>
					<li>

						<li id="logout"><form action="logout.do">

							<button id="searchSubmit" type="submit">Logout</button>
						</form>
					</li>

						<div class="search-container">
							<form id="searchForm" action="search.do">
								<input id="searchText" type="text" placeholder="Search.."
									name="search">
								<button id="searchSubmit" type="submit">Search</button>
							</form>
						</div>
				</ul>
			</c:otherwise>
		</c:choose>
	</header>
</div>

	<h1>Welcome ${sessionScope.loginUser.username}!</h1>

	<br>

	<br>

	<div><h1>List of Venues:</h1>

	<table class="table table-striped table-hover, table-dark">
		<thead class="thead thead-dark">
			<tr>
				<th>Venue</th>
				<th>Address</th>
				<th> </th>
			</tr>
		</thead>
		<tbody>
			<c:if test="${! empty  venues}">
				<c:forEach items="${venues}" var="v">
					<tr>

						<td><a>${v.name}</a></td>
						<td>${v.address.street} ${v.address.city}, ${v.address.state} ${v.address.zip}
						</td><td><form action="removeVenue.do"><button name="id" type="submit" value="${v.id}">Remove Venue</button></form></td>
					</tr>
				</c:forEach>
			</c:if></tbody></table></div>
	<br>

	<br>
	<h1>List of Events:</h1>
	<table class="table table-striped table-hover, table-dark">
		<thead class="thead thead-dark">
			<tr>
				<th>Date</th>
				<th>Venue</th>
				<th>Event</th>
				<th>Start Time</th>
				<th> </th>
				
			</tr>
		</thead>
		<tbody>
			<c:if test="${! empty  events}">
				<c:forEach items="${events}" var="e">
					<tr>

						<td><a>${e.eventDate}</a></td>
						<td>${e.venue.name}</td>
						<td>${e.title}</td>
						<td>${e.startTime}</td>
						<td><form action="removeEvent.do"><button name="id" type="submit" value="${e.id}">Remove Event</button></form></td>
					</tr>
				</c:forEach>
			</c:if></tbody></table>
			
	<h1>List of Users:</h1>
	<table class="table table-striped table-hover, table-dark">
		<thead class="thead thead-dark">
			<tr>
				<th>Username</th>
				<th>First Name</th>
				<th>Last Name</th>
				<th> </th>
				
			</tr>
		</thead>
		<tbody>
			<c:if test="${! empty  users}">
				<c:forEach items="${users}" var="u">
					<tr>
						<td><a>${u.username}</a></td>
						<td>${u.firstName}</td>
						<td>${u.lastName}</td>
						<td><form action="deactivateUser.do"><button name="id" type="submit" value="${u.id}">Deactivate User</button></form></td>
					</tr>
				</c:forEach>
			</c:if></tbody></table>


			<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"
				integrity="sha384-DfXdz2htPH0lsSSs5nCTpuj/zy4C+OGpamoFVy38MVBnE+IbbVYUew+OrCXaRkfj"
				crossorigin="anonymous"></script>
			<script
				src="https://cdn.jsdelivr.net/npm/popper.js@1.16.1/dist/umd/popper.min.js"
				integrity="sha384-9/reFTGAW83EW2RDu2S0VKaIzap3H66lZH81PoYlFhbGU+6BZp6G7niu735Sk7lN"
				crossorigin="anonymous"></script>
			<script
				src="https://cdn.jsdelivr.net/npm/bootstrap@4.5.3/dist/js/bootstrap.min.js"
				integrity="sha384-w1Q4orYjBQndcko6MimVbzY0tgp4pWB4lZ7lr30WKz0vr/aWKhXdBNmNb5D92v7s"
				crossorigin="anonymous"></script>
</body>
</html>