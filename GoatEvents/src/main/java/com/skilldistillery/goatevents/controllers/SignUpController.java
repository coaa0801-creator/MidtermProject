package com.skilldistillery.goatevents.controllers;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.skilldistillery.goatevents.data.GoatDAO;
import com.skilldistillery.goatevents.data.UserDAO;
import com.skilldistillery.goatevents.entities.Address;
import com.skilldistillery.goatevents.entities.User;
import com.skilldistillery.goatevents.entities.Venue;

@Controller
public class SignUpController {

	@Autowired
	private GoatDAO dao;
	@Autowired
	private UserDAO userDao;

	@RequestMapping(path = "signUpInput.do")
	public String signUpInput() {
		return "signUpInput";
	}

	@RequestMapping(path = "signUp.do")
	public String signUp(User newUser, Address address, Venue venue, Model model, HttpSession session) {
		User user = userDao.addUser(newUser);
		session.setAttribute("loginUser", user);
		if (user != null) {
			if (user.getImage() == null) {
				user.setImage(
						"https://thumbs.dreamstime.com/b/default-avatar-profile-icon-social-media-user-vector-default-avatar-profile-icon-social-media-user-vector-portrait-176194876.jpg");
				userDao.saveUser(user);
			}
			session.setAttribute("loginUser", user);
		}

		User updatedUser = dao.getUserByID(user.getId());
		if (address != null && !address.getStreet().equals("")) {
			System.err.println("*********************************" + address);
			Address userAddress = userDao.addAddress(address);
			user.setAddress(userAddress);
			session.setAttribute("loginUser", updatedUser);

			if (venue != null) {
				venue.setAddress(userAddress);
				System.err.println("*********************************" + venue);
				venue.setUser(user);
				Venue venueAddress = userDao.addVenue(venue);
				updatedUser.addManagerVenue(venueAddress);
				userDao.saveUser(updatedUser);
				updatedUser = dao.getUserByID(user.getId());
				session.setAttribute("loginUser", updatedUser);
			}
		}
		boolean isVendor = userDao.isVendor(user);
		if (isVendor == true) {
			System.err.println(updatedUser.getManagerVenues());
			model.addAttribute("venues", updatedUser.getManagerVenues());
			System.err.println(user);
			return "vendorProfilePage";
		}

		model.addAttribute("eventFavoritesList", user.getEvents());
		model.addAttribute("venueFavoritesList", user.getVenues());
		return "userProfilePage";

	}

	@RequestMapping(path = "login.do", method = RequestMethod.POST)
	public String loginUser(Model model, String email, String password, HttpSession session) {
		User user;
		try {
			user = userDao.login(email, password);
			boolean isVendor = userDao.isVendor(user);
			boolean isAdmin = userDao.isAdmin(user);
			List<User> all = userDao.findAllUsers();
			int index = 0;
			for (User userfind : all) {
				String username = userfind.getUsername();
				if (username.equals("1")) {
					break;
				}
				index++;
			}
			all.remove(index);
			if (user != null) {
				if (user.getImage() == null) {
					user.setImage(
							"https://thumbs.dreamstime.com/b/default-avatar-profile-icon-social-media-user-vector-default-avatar-profile-icon-social-media-user-vector-portrait-176194876.jpg");
					userDao.saveUser(user);
				}
				session.setAttribute("loginUser", user);
			}

			if (isAdmin == true) {
				model.addAttribute("events", dao.findAllEvents());
				model.addAttribute("venues", userDao.findAllVenues());
				model.addAttribute("users", all);
				System.out.println(user);
				return "admin";
			}
			if (isVendor == true) {
				model.addAttribute("events", user.getEvents());
				model.addAttribute("venues", user.getVenues());
				System.out.println(user);
				return "vendorProfilePage";
			}
			model.addAttribute("eventFavoritesList", user.getEvents());
			model.addAttribute("venueFavoritesList", user.getVenues());
			return "userProfilePage";
		} catch (Exception e) {
			return "errorPage";
		}
	}

	@RequestMapping(path = "logout.do", method = RequestMethod.GET)
	public String logoutUser(Model model, HttpSession session) {
		if (session.getAttribute("loginUser") != null) {
			session.removeAttribute("loginUser");

		}
		model.addAttribute("eventList", dao.findAllEvents());

		return "Home";
	}

}
