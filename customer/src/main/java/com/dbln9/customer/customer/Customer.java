package com.dbln9.customer.customer;

import com.dbln9.customer.customer.enums.Country;
import com.dbln9.customer.customer.enums.Role;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Customer implements UserDetails {
    @Id
    @GeneratedValue(generator = "customer_id_sequence", strategy = GenerationType.SEQUENCE)
    @SequenceGenerator(sequenceName = "customer_id_sequence", name = "customer_id_sequence")
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;
    private String password;

    private String firstname;
    private String lastname;
    private String phone;
    private String address;
    private String city;
    private Integer postcode;
    @Enumerated(EnumType.STRING)
    private Country country;

    @Enumerated(EnumType.STRING)
    private Role role;

    private String verificationCode;
    private Boolean activated;

    @ElementCollection
    @CollectionTable(name = "productsInTheCard")
    private List<Long> productsInTheCard;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

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
        return activated;
    }
}
