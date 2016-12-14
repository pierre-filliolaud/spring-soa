package fr.efrei.backend.web;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import fr.efrei.backend.domain.User;
import fr.efrei.backend.service.UserService;
import fr.efrei.backend.web.util.HeaderUtil;

@RestController
@RequestMapping("/")
public class UserResource {

	private final Logger log = LoggerFactory.getLogger(UserResource.class);

	@Autowired
	private UserService userService;

	@RequestMapping(value = "/sampleusers",
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<User> createSampleUser() throws URISyntaxException {
		User user = new User();
		user.setFirstName("pierre");
		user.setLastName("filliolaud");
		log.debug("REST request to save Sample User : {}", user);
		if (user.getId() != null) {
			return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("user", "idexists", "A new user cannot already have an ID")).body(null);
		}
		User result = userService.save(user);
		return ResponseEntity.created(new URI("/users/" + result.getId()))
				.headers(HeaderUtil.createEntityCreationAlert("user", result.getId().toString()))
				.body(result);
	}

	@RequestMapping(value = "/users",
			method = RequestMethod.POST,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<User> createUser(@RequestBody User user) throws URISyntaxException {
		log.debug("REST request to save User : {}", user);
		if (user.getId() != null) {
			return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("user", "idexists", "A new user cannot already have an ID")).body(null);
		}
		User result = userService.save(user);
		return ResponseEntity.created(new URI("/users/" + result.getId()))
				.headers(HeaderUtil.createEntityCreationAlert("user", result.getId().toString()))
				.body(result);
	}

	@RequestMapping(value = "/users",
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public List<User> getAllUsers()
			throws URISyntaxException {
		log.debug("REST request to get a page of Users");
		List<User> users = userService.findAll(); 
		return users;
	}

}
