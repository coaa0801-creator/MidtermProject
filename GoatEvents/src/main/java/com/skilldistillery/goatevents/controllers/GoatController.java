package com.skilldistillery.goatevents.controllers;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.skilldistillery.goatevents.data.GoatDAO;
import com.skilldistillery.goatevents.data.UserDAO;
import com.skilldistillery.goatevents.entities.Event;
import com.skilldistillery.goatevents.entities.User;

@Controller
public class GoatController {

	@Autowired
	private GoatDAO dao;
	@Autowired
	private UserDAO userDao;

	@RequestMapping(path = { "/", "home.do" })
	public String home(Model model, HttpSession session) {
		List<Event> events = dao.findAllEvents();
		for (Event event : events) {
			if(event.getImage() == null) {
				event.setImage("resources/images/RockingOut3.jpg");
			}
		}
		model.addAttribute("eventList", events);
		return "Home";
	}

	@RequestMapping(path = "user.do")
	public String userProfile(Model model, HttpSession session) {
		User user = (User) session.getAttribute("loginUser");
		List<User> all = userDao.findAllUsers();
		int index = 0;
		for (User userfind : all) {
			String username = userfind.getUsername();
			if(username.equals("1")){
				break;
			}
			index++;
		}
		all.remove(index);
			model.addAttribute("events", user.getEvents());
			model.addAttribute("venues", user.getVenues());
			model.addAttribute("venueFavoritesList", user.getVenues());
			model.addAttribute("eventFavoritesList", user.getEvents());
			boolean isAdmin = userDao.isAdmin(user);		

			if (isAdmin == true) {
				model.addAttribute("events", dao.findAllEvents());
				model.addAttribute("venues", userDao.findAllVenues());
				model.addAttribute("users", all);
				System.out.println(user);
				return "admin";
			}
		boolean isVendor = userDao.isVendor(user);
		if(isVendor == true) {
			
		System.out.println(user);
		return "vendorProfilePage";
		}
		return "userProfilePage";
	}


	@RequestMapping(path = "deactivateUser.do")
	public String deactivateUser(Model model, HttpSession session, int id) {
		return "user";
	}

}
