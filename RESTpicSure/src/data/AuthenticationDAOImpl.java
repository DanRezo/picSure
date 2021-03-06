package data;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;

import entities.User;

@Transactional
@Repository
@RequestMapping("auth")
public class AuthenticationDAOImpl implements AuthenticationDAO {

	@PersistenceContext
	private EntityManager em;

	@Autowired
	private PasswordEncoder encoder;
	
	@Autowired
	private CartDAO cartDAO;

	@Override
	public User register(User u) {
		String passwordSha = encoder.encode(u.getPassword());
		u.setPassword(passwordSha);
		u.setActive(true);
		
		em.persist(u);
		em.flush();
		cartDAO.create(u.getId());
		return u;
	}

	@Override
	public User authenticateUser(User u) throws NoResultException {
		String query = "SELECT u from User u WHERE u.username = :username";
		User managedUser = em.createQuery(query, User.class).setParameter("username", u.getUsername())
				.getSingleResult();
//		if (encoder.matches(u.getPassword(), managedUser.getPassword()) && managedUser.getActive()) {
			return managedUser;
//		} else {
//			System.out.println("in null");
//			return null;
//		}
	}

}
