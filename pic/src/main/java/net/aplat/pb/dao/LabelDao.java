package net.aplat.pb.dao;

import net.aplat.pb.entity.Label;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LabelDao extends JpaRepository<Label, Long> {
    Optional<Label> getLabelByName(String name);
}
