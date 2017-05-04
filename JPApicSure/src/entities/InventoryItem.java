package entities;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class InventoryItem {

	// fields
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	private boolean active;

	@ManyToOne
	@JoinColumn(name="equipmentId")
	private Equipment equipment;

	@ManyToOne
	@JoinColumn(name="inventoryId")
	private Inventory inventory;
	
	@ManyToMany(mappedBy="inventoryitems")
	private List<ReservationItem> reservationItems;

	@OneToMany(mappedBy="inventoryItem")
	private List<CartItem> cartItems;


	// gets and sets

	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public boolean isActive() {
		return active;
	}


	public void setActive(boolean active) {
		this.active = active;
	}


	public Equipment getEquipment() {
		return equipment;
	}


	public void setEquipment(Equipment equipment) {
		this.equipment = equipment;
	}


	public Inventory getInventory() {
		return inventory;
	}


	public void setInventory(Inventory inventory) {
		this.inventory = inventory;
	}


	public List<ReservationItem> getReservationItems() {
		return reservationItems;
	}


	public void setReservationItems(List<ReservationItem> reservationItems) {
		this.reservationItems = reservationItems;
	}


	public List<CartItem> getCartItems() {
		return cartItems;
	}


	public void setCartItems(List<CartItem> cartItems) {
		this.cartItems = cartItems;
	}

	// toString
	@Override
	public String toString() {
		return "InventoryItem [id=" + id + ", active=" + active + ", equipment=" + equipment + ", inventory=";

	}
}