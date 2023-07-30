package net.aplat.pb.dao;

import net.aplat.pb.entity.Picture;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PictureDao extends JpaRepository<Picture, Long> {
}
