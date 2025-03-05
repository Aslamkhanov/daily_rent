package com.example.daily_rent;

import com.example.daily_rent.dto.ClientDto;
import com.example.daily_rent.entity.Advert;
import com.example.daily_rent.entity.Apartment;
import com.example.daily_rent.entity.ApartmentType;
import com.example.daily_rent.entity.Booking;
import com.example.daily_rent.entity.Client;
import com.example.daily_rent.repository.AdvertRepository;
import com.example.daily_rent.repository.ApartmentRepository;
import com.example.daily_rent.repository.BookingRepository;
import com.example.daily_rent.repository.ClientRepository;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import java.math.BigDecimal;
import java.time.LocalDate;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NO_CONTENT;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@AutoConfigureMockMvc
@Sql(value = "classpath:clear-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
public class ClientControllerTest {
    private static final String CLIENT_NAME = "test name";
    private static final String CLIENT_EMAIL = "test@email.com";
    private static final String CITY = "test city";
    private static final String STREET = "test street";
    private static final String HOUSE = "test house";
    private static final ApartmentType APARTMENT_TYPE = ApartmentType.ONE_ROOM;
    private static final BigDecimal PRICE = BigDecimal.valueOf(500);
    private static final String DESCRIPTION = "test advert";
    private static final BigDecimal TOTAL_PRICE = BigDecimal.valueOf(100);

    private final RequestSpecification requestSpecification = new RequestSpecBuilder()
            .setBasePath("/client")
            .setContentType(ContentType.JSON)
            .log(LogDetail.ALL)
            .build();
    private final ResponseSpecification responseSpecification = new ResponseSpecBuilder()
            .log(LogDetail.ALL)
            .build();
    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private BookingRepository bookingRepository;
    @Autowired
    private AdvertRepository advertRepository;
    @Autowired
    private ApartmentRepository apartmentRepository;

    @Test
    @DisplayName("Успешное создание Клиента")
    public void createSuccess() {
        ClientDto clientDto = ClientDto.builder()
                .email(CLIENT_EMAIL)
                .name(CLIENT_NAME)
                .build();

        ClientDto result = given(requestSpecification)
                .body(clientDto)
                .post()
                .then()
                .spec(responseSpecification)
                .statusCode(CREATED.value())
                .extract()
                .as(ClientDto.class);

        assertNotNull(result.getId());
        assertEquals(CLIENT_EMAIL, result.getEmail());
        assertEquals(CLIENT_NAME, result.getName());
        assertEquals(1, clientRepository.findAll().size());
    }

    @Test
    @DisplayName("Успешное удаление Клиента по id")
    public void deleteSuccess() {
        ClientDto clientDto = ClientDto.builder()
                .email(CLIENT_EMAIL)
                .name(CLIENT_NAME)
                .build();

        ClientDto createdClient = given(requestSpecification)
                .body(clientDto)
                .post()
                .then()
                .spec(responseSpecification)
                .statusCode(CREATED.value())
                .extract()
                .as(ClientDto.class);

        given(requestSpecification)
                .delete("/{id}", createdClient.getId())
                .then()
                .spec(responseSpecification)
                .statusCode(NO_CONTENT.value());

        assertEquals(0, clientRepository.findAll().size());
    }

    @Test
    @DisplayName("Успешное удаление Клиента и его бронирований")
    public void deleteClientWithBookings() {
        ClientDto clientDto = ClientDto.builder()
                .email(CLIENT_EMAIL)
                .name(CLIENT_NAME)
                .build();

        ClientDto createdClient = given(requestSpecification)
                .body(clientDto)
                .post()
                .then()
                .spec(responseSpecification)
                .statusCode(CREATED.value())
                .extract()
                .as(ClientDto.class);

        Apartment apartment = apartmentRepository.findById(1)
                .orElseGet(() -> apartmentRepository.save(Apartment.builder()
                        .city(CITY)
                        .street(STREET)
                        .house(HOUSE)
                        .apartmentType(APARTMENT_TYPE)
                        .build()));

        Advert advert = advertRepository.findById(1)
                .orElseGet(() -> advertRepository.save(Advert.builder()
                        .apartment(apartment)
                        .price(PRICE)
                        .isActive(true)
                        .description(DESCRIPTION)
                        .build()));

        Client managedClient = clientRepository.findById(createdClient.getId()).orElseThrow();

        Booking booking = Booking.builder()
                .client(managedClient)
                .startDate(LocalDate.now())
                .endDate(LocalDate.now().plusDays(2))
                .price(TOTAL_PRICE)
                .advert(advert)
                .build();
        bookingRepository.save(booking);

        given(requestSpecification)
                .delete("/{id}", createdClient.getId())
                .then()
                .spec(responseSpecification)
                .statusCode(NO_CONTENT.value());

        assertFalse(clientRepository.existsById(createdClient.getId()));
        assertEquals(0, bookingRepository.count());
    }
}
