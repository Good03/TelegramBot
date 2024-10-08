package org.pollux.model.interfaces;

import org.pollux.model.entities.VapeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VapesRepository extends JpaRepository<VapeEntity, Long> {
    VapeEntity findByBrand(String brand);
    VapeEntity findByModel(String model);
    VapeEntity findByPuffs(String puffs);
}
