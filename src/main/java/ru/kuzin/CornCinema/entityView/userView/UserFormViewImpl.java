package ru.kuzin.CornCinema.entityView.userView;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import com.blazebit.persistence.view.EntityViewManager;
import com.blazebit.persistence.view.SerializableEntityViewManager;
import com.blazebit.persistence.view.StaticImplementation;
import com.blazebit.persistence.view.spi.type.EntityViewProxy;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import ru.kuzin.CornCinema.models.Role;
import ru.kuzin.CornCinema.models.User;
import ru.kuzin.CornCinema.validators.PasswordMatching;
import ru.kuzin.CornCinema.validators.UniqueUserName;


@StaticImplementation(UserFormView.class)
@PasswordMatching(password = "password", confirmPassword = "confirmPassword", message = "password and confirm do not match")
public class UserFormViewImpl implements UserFormView, EntityViewProxy {
	
	public static volatile EntityViewManager ENTITY_VIEW_MANAGER;
	public static final SerializableEntityViewManager SERIALIZABLE_ENTITY_VIEW_MANAGER = new SerializableEntityViewManager(UserFormViewImpl.class, ENTITY_VIEW_MANAGER);
	
	private Integer id;
	@UniqueUserName
	@NotEmpty(message = "user name must not be empty")
	@Size(min = 2, max = 64, message = "user name must be between 2 and 32 characters")
	private String userName;
	private String password;
	private String confirmPassword;
	@Email(regexp = "^[A-Za-z0-9+_.-]+@(.+)$", message = "email must be not empty and valid")
	private String email;
	LocalDateTime registrationDate;	
	private Set<Role> roles = new HashSet<>();
	
	public UserFormViewImpl() {
		
	}
	
	public UserFormViewImpl(UserFormViewImpl noop, Map<String, Object> optionalParameters) {
		this.id = null;
		this.userName = null;
		this.password = null;
		this.confirmPassword = null;
		this.email = null;
		this.registrationDate = null;
	}
	
	public UserFormViewImpl(Integer id) {
		this.id = id;
		this.userName = null;
		this.password = null;
		this.confirmPassword = null;
		this.email = null;
		this.registrationDate = null;
	}
	
	public UserFormViewImpl(Integer id, String userName, String password, String confirmPassword, String email, LocalDateTime registrationDate) {
		this.id = id;
		this.userName = userName;
		this.password = password;
		this.confirmPassword = confirmPassword;
		this.email = email;
		this.registrationDate = registrationDate;
	}
	
	public UserFormViewImpl(UserFormViewImpl noop, int offset, Object[] tuple) {
		this.id = (Integer) tuple[offset + 0];
		this.userName = (String) tuple[offset + 1];
		this.password = (String) tuple[offset + 2];
		this.confirmPassword = (String) tuple[offset + 3];
		this.email = (String) tuple[offset + 4];
		this.registrationDate = (LocalDateTime) tuple[offset + 5];
	}
	
	@Override
	public Integer getId() {return id;}
	@Override
	public String getUserName() {return userName;}
	@Override
	public String getPassword() {return password;}
	public String getConfirmPassword() {return confirmPassword;}
	@Override
	public String getEmail() {return email;}
	@Override
	public LocalDateTime getRegistrationDate() {return registrationDate;}
	@Override
	public Set<Role> getRoles() {return roles;}
	
	@Override
	public void setUserName(String userName) {this.userName = userName;}
	@Override
	public void setPassword(String password) {this.password = password;}
	public void setConfirmPassword(String confirmPassword) {this.confirmPassword = confirmPassword;}
	@Override
	public void setEmail(String email) {this.email = email;}
	@Override
	public void setRegistrationDate(LocalDateTime registrationDate) {this.registrationDate = registrationDate;}
	
	@Override
	public Class<?> $$_getJpaManagedClass() {
		return User.class;
	}
	
	@Override
	public Class<?> $$_getJpaManagedBaseClass() {
		return User.class;
	}
	
	@Override
	public Class<?> $$_getEntityViewClass() {
		return UserFormView.class;
	}
	
	@Override
	public boolean $$_isNew() {
		return false;
	}
	
	@Override
	public boolean $$_isReference() {
		return false;
	}
	
	@Override
	public void $$_setIsReference(boolean isReference) {
	
	}
	
	@Override
	public Object $$_getId() {
		return id;
	}
	
	@Override
	public Object $$_getVersion() {
		return null;
	}
	
	@Override
	public int hashCode() {
		return Objects.hashCode(id);
	}
	
	@Override
    public boolean equals(Object obj) {
		if (this == obj) {
            return true;
        }
        if (obj == null || this.$$_getId() == null) {
            return false;
        }
        if (obj instanceof EntityViewProxy) {
            EntityViewProxy other = (EntityViewProxy) obj;
            if (this.$$_getJpaManagedBaseClass() == other.$$_getJpaManagedBaseClass() && this.$$_getId().equals(other.$$_getId())) {
                return true;
            } else {
                return false;
            }
        }
        if (obj instanceof UserFormView) {
        	UserFormView other = (UserFormView) obj;
            if (!Objects.equals(this.getId(), other.getId())) {
                return false;
            }
            return true;
        }
		return false;
	}

	
	
}
