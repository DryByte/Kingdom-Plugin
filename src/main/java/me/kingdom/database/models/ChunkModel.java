package me.kingdom.database.models;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

import jakarta.persistence.ManyToOne;
import me.kingdom.database.models.KingdomModel;

@Entity(name = "Chunk")
public class ChunkModel {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne
    private KingdomModel kingdom;
}
