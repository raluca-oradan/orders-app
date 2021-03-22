package com.javaAdvanced.ordersapp.CUSTOMER.dao;

import com.javaAdvanced.ordersapp.CUSTOMER.model.CustomerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface CustomerRepository extends JpaRepository<CustomerEntity, Long> {
    @Query(value = "SELECT * FROM customers c " +
            "WHERE c.user_id = :user_entity_id", nativeQuery = true)
    public CustomerEntity getCustomerByUserEntityId(long user_entity_id);

    @Modifying //e pentru ca modificam starea bazei de date
    @Transactional //e pentru ca daca cumva update-ul failuie intr-un mod sa stie sa revina la starea de la care s-a
    // plecat. Poate tu dai acolo la update o parola mai lunga decat e limita pusa pe baza de date si
    // da eroare, nah, anotarea asta face ca parola sa ramana la fel in caz de erori
    @Query(value = " UPDATE customers " +
            " SET name = :customerName, phone_number = :customerPhone_number, address = :customerAdress" +
            " WHERE id = :id ", nativeQuery = true)
    public void update(long id, String customerName, String customerPhone_number, String customerAdress);
}
