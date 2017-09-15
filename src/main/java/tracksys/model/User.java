package tracksys.model;
import javax.persistence.*;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@Entity
@Table(name="users")
//@JsonIdentityInfo(generator=ObjectIdGenerators.IntSequenceGenerator.class, property="@id")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
/*	@OneToOne(cascade=CascadeType.ALL,  mappedBy="user")
	private Previliges previliges;*/
	
	
	
	@NotNull
	@Column(name = "usr_name", nullable = false)	
	private String username;

	@NotNull
	@Column(name = "usr_psw", nullable = false)	
	private String password;

	@NotNull
	@Column(name = "address", nullable = false)	
	private String address;

	@NotNull
	@Column(name = "phone", nullable = false)	
	private long phone;

	@NotNull
	@Column(name = "email", nullable = false)	
	private String email;

	@NotNull
	@Column(name = "usertype", nullable = false)	
	private String type;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	/*
	@OneToMany(cascade=CascadeType.ALL, mappedBy="user",fetch = FetchType.EAGER)  
	private List<Previliges> previliges;
	
	public List<Previliges> getPreviliges() {
		return previliges;
	}

	public void setPreviliges(List<Previliges> previliges) {
		this.previliges = previliges;
	}*/

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public long getPhone() {
		return phone;
	}

	public void setPhone(long phone) {
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
}
