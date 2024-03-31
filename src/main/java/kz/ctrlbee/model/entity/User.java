package kz.ctrlbee.model.entity;


import jakarta.persistence.*;
import kz.ctrlbee.model.enumuration.Role;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "users")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User implements UserDetails {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;


    @Column(name = "email")
    private String email;

    @Column(name = "name")
    private String name;

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "birth_day")
    private LocalDate birthDay;

    @Column(name = "country")
    private String country;

    @Column(name = "profile_image_path")
    private String profileImagePath;

    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    private Role role;

    @Column(name = "status")
    private String status;


    @OneToMany(mappedBy = "owner")
    private List<Task> tasks = new ArrayList<>();

    @OneToMany(mappedBy = "owner")
    private List<Room> rooms = new ArrayList<>();

    @ManyToMany
    private List<Room> joinedRooms = new ArrayList<>();

    @OneToMany
    private List<Post> posts = new ArrayList<>();

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(this.role.name()));
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
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
}
