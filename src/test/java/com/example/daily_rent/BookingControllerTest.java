package com.example.daily_rent;

import com.example.daily_rent.dto.AdvertCreateDto;
import com.example.daily_rent.dto.AdvertDtoRs;
import com.example.daily_rent.dto.BookingCreateDto;
import com.example.daily_rent.dto.BookingDtoRs;
import com.example.daily_rent.dto.ClientDto;
import com.example.daily_rent.entity.Apartment;
import com.example.daily_rent.entity.ApartmentType;
import com.example.daily_rent.entity.Client;
import com.example.daily_rent.repository.ApartmentRepository;
import com.example.daily_rent.repository.ClientRepository;
import com.example.daily_rent.service.AdvertService;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.jdbc.Sql;

import java.math.BigDecimal;
import java.time.LocalDate;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@AutoConfigureMockMvc
@Sql(value = "classpath:clear-data.sql", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
public class BookingControllerTest {

    private static final String CLIENT_NAME = "test name";
    private static final String CLIENT_EMAIL = "test@email.com";
    private static final String NEW_CLIENT_NAME = "new test name";
    private static final String NEW_CLIENT_EMAIL = "newtest@email.com";
    private static final String CITY = "test city";
    private static final String STREET = "test street";
    private static final String HOUSE = "test house";
    private static final ApartmentType APARTMENT_TYPE = ApartmentType.ONE_ROOM;
    private static final BigDecimal PRICE = BigDecimal.TEN;
    private static final String DESCRIPTION = "test advert";
    private static final LocalDate START_DATE = LocalDate.of(
            2025, 3, 5);
    private static final LocalDate END_DATE = LocalDate.of(
            2025, 3, 6);
    private static final LocalDate NEW_START_DATE = LocalDate.of(
            2025, 3, 10);
    private static final LocalDate NEW_END_DATE = LocalDate.of(
            2025, 3, 15);
    private static final LocalDate START_DATE_OVERLAP_CASE_1 = LocalDate.of(
            2025, 9, 29);
    private static final LocalDate END_DATE_OVERLAP_CASE_1 = LocalDate.of(
            2025, 10, 2);
    private static final LocalDate START_DATE_OVERLAP_CASE_2 = LocalDate.of(
            2025, 10, 9);
    private static final LocalDate END_DATE_OVERLAP_CASE_2 = LocalDate.of(
            2025, 10, 11);

    private final RequestSpecification requestSpecification = new RequestSpecBuilder()
            .setBasePath("/booking")
            .setContentType(ContentType.JSON)
            .log(LogDetail.ALL)
            .build();
    private final ResponseSpecification responseSpecification = new ResponseSpecBuilder()
            .log(LogDetail.ALL)
            .build();
    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private ApartmentRepository apartmentRepository;
    @Autowired
    private AdvertService advertService;

    private Client savedClient;
    private AdvertDtoRs advertDtoRs;

    @BeforeEach
    public void setUp() {
        savedClient = clientRepository.save(Client.builder()
                .name(CLIENT_NAME)
                .email(CLIENT_EMAIL)
                .build());

        Apartment savedApartment = apartmentRepository.save(Apartment.builder()
                .city(CITY)
                .street(STREET)
                .house(HOUSE)
                .apartmentType(APARTMENT_TYPE)
                .build());

        AdvertCreateDto advertCreateDto = AdvertCreateDto.builder()
                .price(PRICE)
                .description(DESCRIPTION)
                .apartmentId(savedApartment.getId())
                .isActive(true)
                .build();
        advertDtoRs = advertService.save(advertCreateDto);
    }

    @Test
    @DisplayName("Успешное бронирование, при незаполненности id у клиента. Проверить что создан новый клиент")
    public void createSuccessWithoutClientId() {
        BookingCreateDto bookingCreateDto = BookingCreateDto.builder()
                .advertId(advertDtoRs.getId())
                .startDate(NEW_START_DATE)
                .endDate(NEW_END_DATE)
                .client(ClientDto.builder()
                        .name(NEW_CLIENT_NAME)
                        .email(NEW_CLIENT_EMAIL)
                        .build())
                .build();

        BookingDtoRs response = given(requestSpecification)
                .body(bookingCreateDto)
                .post()
                .then()
                .spec(responseSpecification)
                .statusCode(HttpStatus.CREATED.value())
                .extract()
                .as(BookingDtoRs.class);

        assertNotNull(response);
        assertNotNull(response.getId());
    }

    @Test
    @DisplayName("Успешное бронирование, при указанном id у клиента")
    public void createSuccessWithClientId() {
        BookingCreateDto bookingCreateDto = BookingCreateDto.builder()
                .advertId(advertDtoRs.getId())
                .startDate(NEW_START_DATE)
                .endDate(NEW_END_DATE)
                .client(ClientDto.builder()
                        .id(savedClient.getId())
                        .name(savedClient.getName())
                        .email(savedClient.getEmail())
                        .build())
                .build();

        BookingDtoRs response = given(requestSpecification)
                .body(bookingCreateDto)
                .post()
                .then()
                .spec(responseSpecification)
                .statusCode(HttpStatus.CREATED.value())
                .extract()
                .as(BookingDtoRs.class);

        assertNotNull(response);
        assertNotNull(response.getId());
    }

    @Test
    @DisplayName("Неуспешное бронирование при существующем бронировании на эти даты (случай 4.2.2)")
    public void bookingFailsIfDatesOverlapFirstSituation() {
        BookingCreateDto bookingCreateDto = BookingCreateDto.builder()
                .advertId(advertDtoRs.getId())
                .startDate(START_DATE)
                .endDate(END_DATE)
                .client(ClientDto.builder()
                        .id(savedClient.getId())
                        .name(savedClient.getName())
                        .email(savedClient.getEmail())
                        .build())
                .build();

        BookingDtoRs firstBookingResponse = given(requestSpecification)
                .body(bookingCreateDto)
                .post()
                .then()
                .spec(responseSpecification)
                .statusCode(HttpStatus.CREATED.value())
                .extract()
                .as(BookingDtoRs.class);

        assertNotNull(firstBookingResponse);
        assertNotNull(firstBookingResponse.getId());

        BookingCreateDto overlappingBookingRequest = BookingCreateDto.builder()
                .advertId(advertDtoRs.getId())
                .startDate(START_DATE)
                .endDate(END_DATE)
                .client(ClientDto.builder()
                        .id(savedClient.getId())
                        .name(savedClient.getName())
                        .email(savedClient.getEmail())
                        .build())
                .build();

        given(requestSpecification)
                .body(overlappingBookingRequest)
                .post()
                .then()
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    @DisplayName("Неуспешное бронирование при существующем бронировании на эти даты (случай 4.2.3)")
    public void bookingFailsIfDatesOverlapCaseSecondSituation() {
        BookingCreateDto firstBookingRequest = BookingCreateDto.builder()
                .advertId(advertDtoRs.getId())
                .startDate(START_DATE_OVERLAP_CASE_1)
                .endDate(END_DATE_OVERLAP_CASE_1)
                .client(ClientDto.builder()
                        .id(savedClient.getId())
                        .name(savedClient.getName())
                        .email(savedClient.getEmail())
                        .build())
                .build();

        BookingDtoRs firstBookingResponse = given(requestSpecification)
                .body(firstBookingRequest)
                .post()
                .then()
                .spec(responseSpecification)
                .statusCode(HttpStatus.CREATED.value())
                .extract()
                .as(BookingDtoRs.class);

        assertNotNull(firstBookingResponse);
        assertNotNull(firstBookingResponse.getId());

        BookingCreateDto overlappingBookingRequest = BookingCreateDto.builder()
                .advertId(advertDtoRs.getId())
                .startDate(START_DATE_OVERLAP_CASE_1)
                .endDate(END_DATE_OVERLAP_CASE_1)
                .client(ClientDto.builder()
                        .id(savedClient.getId())
                        .name(savedClient.getName())
                        .email(savedClient.getEmail())
                        .build())
                .build();

        given(requestSpecification)
                .body(overlappingBookingRequest)
                .post()
                .then()
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    @DisplayName("Неуспешное бронирование при существующем бронировании на эти даты (случай 4.2.4)")
    public void bookingFailsIfDatesOverlapCaseThirdSituation() {
        BookingCreateDto firstBookingRequest = BookingCreateDto.builder()
                .advertId(advertDtoRs.getId())
                .startDate(START_DATE_OVERLAP_CASE_2)
                .endDate(END_DATE_OVERLAP_CASE_2)
                .client(ClientDto.builder()
                        .id(savedClient.getId())
                        .name(savedClient.getName())
                        .email(savedClient.getEmail())
                        .build())
                .build();

        BookingDtoRs firstBookingResponse = given(requestSpecification)
                .body(firstBookingRequest)
                .post()
                .then()
                .spec(responseSpecification)
                .statusCode(HttpStatus.CREATED.value())
                .extract()
                .as(BookingDtoRs.class);

        assertNotNull(firstBookingResponse);
        assertNotNull(firstBookingResponse.getId());

        BookingCreateDto overlappingBookingRequest = BookingCreateDto.builder()
                .advertId(advertDtoRs.getId())
                .startDate(START_DATE_OVERLAP_CASE_2)
                .endDate(END_DATE_OVERLAP_CASE_2)
                .client(ClientDto.builder()
                        .id(savedClient.getId())
                        .name(savedClient.getName())
                        .email(savedClient.getEmail())
                        .build())
                .build();

        given(requestSpecification)
                .body(overlappingBookingRequest)
                .post()
                .then()
                .statusCode(HttpStatus.BAD_REQUEST.value());
    }
}
