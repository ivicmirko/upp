package root.demo.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name="systemuser")
public class SystemUser {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@Column
	private String name;
	
	@Column
	private String surname;
	
	@Column
	private String city;
	
	@Column
	private String country;
	
	@Column
	private String title;
	
	@Column
	private String email;
	
	@Column
	private String username;
	
	@Column
	private String password;
	
	@Column
	private boolean active;
	
	@Column
	private String reviewer; //yes/no/asking
	
	@Column
	private String authority;
	
	@ManyToMany(cascade = CascadeType.ALL, fetch=FetchType.EAGER)
    private List<ScienceArea> scienceAreas = new ArrayList<>();
	
	public SystemUser() {
		
	}

	public SystemUser(long id, String name, String surname, String city, String country, String title, String email,
			String username, String password, boolean active, String reviewer, String authority,
			List<ScienceArea> scienceAreas) {
		super();
		this.id = id;
		this.name = name;
		this.surname = surname;
		this.city = city;
		this.country = country;
		this.title = title;
		this.email = email;
		this.username = username;
		this.password = password;
		this.active = active;
		this.reviewer = reviewer;
		this.authority = authority;
		this.scienceAreas = scienceAreas;
	}

	public String getAuthority() {
		return authority;
	}

	public void setAuthority(String authority) {
		this.authority = authority;
	}


	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSurname() {
		return surname;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

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

	public String getReviewer() {
		return reviewer;
	}

	public void setReviewer(String reviewer) {
		this.reviewer = reviewer;
	}

	public List<ScienceArea> getScienceAreas() {
		return scienceAreas;
	}

	public void setScienceAreas(List<ScienceArea> scienceAreas) {
		this.scienceAreas = scienceAreas;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}
	
	
	

}
