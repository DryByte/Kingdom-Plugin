package me.kingdom.database.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

import java.util.UUID;

@Entity(name = "Kingdom")
public class KingdomModel {
    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private String tag;
    private UUID founder;
}
