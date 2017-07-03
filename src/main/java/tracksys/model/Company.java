package tracksys.model;

import java.util.Set;

import javax.persistence.*;
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
	@Size(min = 3, max = 100)
	@Column(name = "mailingname", nullable = false)
	private String mailingname;	

	@NotNull
	@Size(min = 2, max = 100)
	@Column(name = "address", nullable = false)
	private String address;

	@NotNull
	@Column(name = "country")
	private int country;
	
	@NotNull
	@Column(name = "companystate")
	private int state;	

	@NotNull
	@Column(name = "pincode")
	private int pincode;

	@Column(name = "phone")
	private int phone;
	
	@NotNull
	@Size(min = 2, max = 100)
	@Column(name = "email", nullable = false)
	private String email;
	
	@Size(min = 2, max = 100)
	@Column(name = "currencesymbol", nullable = true)
	private String currencesymbol;
	
	@NotNull
	@Column(name = "companytype", nullable = false)
	private int type;
	
	
	@NotNull
	@Size(min = 2, max = 100)
	@Column(name = "yearstart", nullable = false)
	private String yearstart;
	
	@NotNull
	@Size(min = 2, max = 100)
	@Column(name = "booksstart", nullable = false)
	private String booksstart;
	
	
/*	@ManyToMany(cascade=CascadeType.ALL) 
    @JoinTable(name="company_user",
        joinColumns=
            @JoinColumn(name="companyid", referencedColumnName="id"),
        inverseJoinColumns=
            @JoinColumn(name="userid", referencedColumnName="id")
        )
	private Set<User> users;*/

	
	
	/*@OneToMany(cascade=CascadeType.ALL, mappedBy="company") 
	private Set<Group> groups;
	
    public Set<Group> getGroups() {
		return groups;
	}

	public void setGroups(Set<Group> groups) {
		this.groups = groups;
	}*/


/*	public Set<User> getUsers() {
		return users;
	}

	public void setUsers(Set<User> users) {
		this.users = users;
	}*/

	public Company() { }

	public Company(int id) { 
		this.id = id;
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

	public String getMailingname() {
		return mailingname;
	}

	public void setMailingname(String mailingname) {
		this.mailingname = mailingname;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public int getCountry() {
		return country;
	}

	public void setCountry(int country) {
		this.country = country;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public int getPincode() {
		return pincode;
	}

	public void setPincode(int pincode) {
		this.pincode = pincode;
	}

	public int getPhone() {
		return phone;
	}

	public void setPhone(int phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getCurrencesymbol() {
		return currencesymbol;
	}

	public void setCurrencesymbol(String currencesymbol) {
		this.currencesymbol = currencesymbol;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getYearstart() {
		return yearstart;
	}

	public void setYearstart(String yearstart) {
		this.yearstart = yearstart;
	}

	public String getBooksstart() {
		return booksstart;
	}

	public void setBooksstart(String booksstart) {
		this.booksstart = booksstart;
	}


}
