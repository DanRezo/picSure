package controllers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.databind.ObjectMapper;

import data.UserDAO;
import entities.User;

@RestController
public class UserController {
	
	@Autowired
	private UserDAO userDAO;
	
	@RequestMapping(value = "user/{userId}", method = RequestMethod.GET)
    public User index(HttpServletRequest req, HttpServletResponse res, @PathVariable Integer userId) {
    	return userDAO.show(userId);
    }
	
	@RequestMapping(value="user/{userId}", method=RequestMethod.PUT)
	public User update(HttpServletRequest req, HttpServletResponse res, @PathVariable Integer userId, @RequestBody String jsonUser) {
		try {
			ObjectMapper mapper = new ObjectMapper();
			User mappedUser = mapper.readValue(jsonUser, User.class);
			return userDAO.update(userId, mappedUser);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	@RequestMapping(value = "user/", method = RequestMethod.POST)
	public User create(HttpServletRequest req, HttpServletResponse res, @RequestBody String jsonUser) {
		try {
			ObjectMapper mapper = new ObjectMapper();
			User mappedUser = mapper.readValue(jsonUser, User.class);
			return userDAO.create(mappedUser);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	@RequestMapping(value="user/{userId}", method= RequestMethod.DELETE)
	public Boolean destroy(HttpServletRequest req, HttpServletResponse res, @PathVariable Integer userId) {
		return userDAO.destroy(userId);
	}
}
