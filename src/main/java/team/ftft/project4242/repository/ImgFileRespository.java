package team.ftft.project4242.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import team.ftft.project4242.domain.ImgFile;

public interface ImgFileRespository extends JpaRepository<ImgFile,Long> {
}
