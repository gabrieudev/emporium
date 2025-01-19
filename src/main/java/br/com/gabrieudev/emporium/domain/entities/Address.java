package br.com.gabrieudev.emporium.domain.entities;

import java.util.UUID;

public class Address {
    private UUID id;
    private User user;
    private String name;
    private String city;
    private String country;
    private String postalCode;
    private String state;
    private Boolean isDefault;
    
    public UUID getId() {
        return id;
    }
    public void setId(UUID id) {
        this.id = id;
    }
    public User getUser() {
        return user;
    }
    public void setUser(User user) {
        this.user = user;
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
    public String getPostalCode() {
        return postalCode;
    }
    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }
    public String getState() {
        return state;
    }
    public void setState(String state) {
        this.state = state;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public Boolean getIsDefault() {
        return isDefault;
    }
    public void setIsDefault(Boolean isDefault) {
        this.isDefault = isDefault;
    }
    
    public Address(UUID id, User user, String name, String city, String country, String postalCode, String state,
            Boolean isDefault) {
        this.id = id;
        this.user = user;
        this.name = name;
        this.city = city;
        this.country = country;
        this.postalCode = postalCode;
        this.state = state;
        this.isDefault = isDefault;
    }
    
    public Address() {
    }
    
}
