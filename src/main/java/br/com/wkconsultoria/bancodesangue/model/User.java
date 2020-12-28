package br.com.wkconsultoria.bancodesangue.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonProperty.Access;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter @Setter @NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private long id;
    
    @Column
    private String name;
    
    @Column
    private String username;
    
    @Column
    @JsonProperty(access = Access.WRITE_ONLY)
    private String password;

    @Column
    private boolean enabled;
    
    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.MERGE)
    @JoinTable(name = "user_roles", 
    	joinColumns = { @JoinColumn(name = "user_id") }, 
    	inverseJoinColumns = { @JoinColumn(name = "role_id") })
    private List<Role> roles;
    
    @Transient
    private String token;
}