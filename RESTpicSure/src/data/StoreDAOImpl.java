package data;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import entities.Inventory;
import entities.InventoryItem;
import entities.Store;
import entities.User;

@Transactional
@Repository
public class StoreDAOImpl implements StoreDAO {

	@PersistenceContext
	private EntityManager em;

	@Autowired
	private InventoryDAO inventoryDAO;

	@Override
	public Store show(Integer id) {
		return em.find(Store.class, id);
	}
	
	@Override
	public List<Store> indexEquipment(Integer equipmentId) {
		String q = "SELECT s FROM Store s JOIN FETCH Inventory i ON s.id = i.store.id JOIN FETCH InventoryItem ii ON i.id = ii.inventory.id JOIN FETCH Equipment e ON ii.equipment.id = e.id WHERE e.id = :id";
		return em.createQuery(q, Store.class).setParameter("id", equipmentId).getResultList();
	}
	
	@Override
	public List<Store> index() {
		String q = "SELECT s FROM Store s";
		return em.createQuery(q, Store.class).getResultList();
	}
	
	@Override 
	public Inventory showInventory(Integer storeId) {
		String q = "SELECT i FROM Inventory i WHERE i.store.id = :id";
		return em.createQuery(q, Inventory.class).setParameter("id", storeId).getSingleResult();
	}

	@Override
	public Store update(Integer id, Store s) {
		Store store = em.find(Store.class, id);
		store.setEmail(s.getEmail());
		store.setName(s.getName());
		store.setPhone(s.getPhone());
		store.setInventory(s.getInventory());
		store.setActive(s.getActive());

		em.persist(store);
		em.flush();
		return store;
	}

	@Override
	public Store create(Integer id, Store s) {
		List<User> users = new ArrayList<>();
		s.setUsers(users);
		s.getUsers().add(em.find(User.class, id));

		em.persist(s);
		em.flush();

		List<InventoryItem> items = new ArrayList<>();
		Inventory inventory = new Inventory();
		inventory.setIventoryItems(items);

		inventoryDAO.create(s.getId(), inventory);
		return s;
	}

	@Override
	public Boolean destroy(Integer id) {
		try {
			em.remove(em.find(Store.class, id));
			return true;
		} catch (Exception e) {
			return false;
		}
	}
}
