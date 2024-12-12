package io.gitHub.AugustoMello09.tarefas.domain.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@EqualsAndHashCode(of = "id")
@Getter
@Setter
@Entity
@Table(name = "tb_usuario")
public class Usuario implements Serializable, UserDetails {
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.UUID)
	private UUID id;
	
	private String name;
	
	@Column(unique = true, nullable = true)
	private String email;
	
	@JsonIgnore
	private String password;
	
	private Boolean notification;
	
	@ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinTable(name = "tb_usuario_cargo", joinColumns = @JoinColumn(name = "usuario_id"), inverseJoinColumns = @JoinColumn(name = "cargo_id"))
	private Set<Cargo> cargos = new HashSet<>();
	
	@OneToMany(mappedBy = "usuario")
	private List<Tarefa> tarefas = new ArrayList<>();

	public Usuario(UUID id, String name, String email, String password, Boolean notification) {
		super();
		this.id = id;
		this.name = name;
		this.email = email;
		this.password = password;
		this.notification = notification;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return cargos.stream().map(car -> new SimpleGrantedAuthority(car.getAuthority()))
				.collect(Collectors.toList());
	}
	
	@JsonIgnore
	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public String getUsername() {
		return email;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}
	
	public boolean hasCargo(String cargoNome) {
		for (Cargo role : cargos) {
			if (role.getAuthority().equals(cargoNome)) {
				return true;
			}
		}
		return false;
	}
}
