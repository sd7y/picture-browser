package net.aplat.pb.dao;

import net.aplat.pb.entity.PictureSet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PictureSetDao extends JpaRepository<PictureSet, Long> {
    Optional<PictureSet> findPictureSetByTitleAndGroupName(String title, String group);

    List<PictureSet> findPictureSetsByGroupName(String group);
}
