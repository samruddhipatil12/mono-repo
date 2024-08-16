package org.dnyanyog.repo;

import java.util.List;
import java.util.Optional;
import org.dnyanyog.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

@Repository
@Component
public interface DirectoryRepository extends JpaRepository<User, Long> {
  List<User> findByMobileNumber(Long mobileNumber);

  Optional<User> findByUserid(String userid);
}
