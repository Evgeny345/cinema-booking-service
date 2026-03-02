package ru.kuzin.CornCinema.models;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "user", schema = "public")
@Setter
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class User {
	
	@EqualsAndHashCode.Include
	private Integer id;
	private String userName;
	private String password;
	private String email;
	private LocalDateTime registrationDate;
	private Set<Role> roles = new HashSet<>();
	private Set<Ticket> tickets  = new HashSet<>();
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "user_id")
	public Integer getId() {return id;}
	@Column(name = "user_name", nullable = false, unique = true, length = 64)
	public String getUserName() {return userName;}
	@Column(name = "user_password", nullable = false, length = 256)
	public String getPassword() {return password;}
	@Column(name = "email", nullable = false, length = 128)
	public String getEmail() {return email;}
	@Column(name = "registration_date", nullable = false)
	public LocalDateTime getRegistrationDate() {return registrationDate;}
	@ManyToMany
	@JoinTable(name = "user_role", 
			   joinColumns = @JoinColumn(name = "user_id"),
			   inverseJoinColumns = @JoinColumn(name = "role_id"))
	public Set<Role> getRoles() {return roles;}
	@OneToMany(mappedBy = "user", cascade = {CascadeType.MERGE, CascadeType.PERSIST})
	public Set<Ticket> getTickets() {return tickets;}
	
	public void addTicket(Ticket ticket) {
		tickets.add(ticket);
		ticket.setUser(this);
	}
	
}
