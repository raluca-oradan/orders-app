package com.javaAdvanced.ordersapp.USER.dao;
import com.javaAdvanced.ordersapp.USER.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

    Optional<UserEntity> findByEmail(String email);

    @Query(value = "(SELECT CAST (COUNT(*) AS BIT) AS Expr" +
            " FROM users u " +
            "WHERE u.email = :email )", nativeQuery = true)

    public Boolean isEmailAlreadyUsed(String email);

    @Modifying //e pentru ca modificam starea bazei de date
    @Transactional //e pentru ca daca cumva update-ul failuie intr-un mod sa stie sa revina la starea de la care s-a
    // plecat. Poate tu dai acolo la update o parola mai lunga decat e limita pusa pe baza de date si
    // da eroare, nah, anotarea asta face ca parola sa ramana la fel in caz de erori
    @Query(value = " UPDATE users " +
            " SET email = :userEmail, password = :userPassword, role = :userRole" +
            " WHERE id = :id ", nativeQuery = true)
    public void update(long id, String userEmail, String userPassword, Integer userRole);
}
