package tracksys.model;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
@Table(name="company")
public class Company {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@NotNull
	@Size(min = 3, max = 100)
	@Column(name = "name", nullable = false)
	private String name;

	@NotNull
	@Size(min = 2, max = 100)
	@Column(name = "address", nullable = false)
	private String address;

	@NotNull
	@Size(min = 2, max = 100)
	@Column(name = "city", nullable = false)
	private String city;

	@NotNull
	@Column(name = "tin", nullable = false)
	private int tin;

	@Column(name = "phonenumber", nullable = false)
	private int phonenumber;
	
	@OneToMany(cascade=CascadeType.ALL, mappedBy="company") 
	private Set<Group> groups;

	public Company() { }

	public Company(int id) { 
		this.id = id;
	}

	public Company(String name, String address, String city,int tin, int phonenumber) {
		this.name = name;
		this.address = address;
		this.city = city;
		this.tin = tin;
		this.phonenumber = phonenumber;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public int getTin() {
		return tin;
	}

	public void setTin(int tin) {
		this.tin = tin;
	}

	public int getPhonenumber() {
		return phonenumber;
	}

	public void setPhonenumber(int phonenumber) {
		this.phonenumber = phonenumber;
	}

	public Set<Group> getGroups() {
		return groups;
	}

	public void setGroups(Set<Group> groups) {
		this.groups = groups;
	}



}
