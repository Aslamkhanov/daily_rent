package com.example.daily_rent;

import com.example.daily_rent.dto.AdvertCreateDto;
import com.example.daily_rent.dto.AdvertDtoRs;
import com.example.daily_rent.dto.BookingCreateDto;
import com.example.daily_rent.dto.BookingDtoRs;
import com.example.daily_rent.dto.ClientDto;
import com.example.daily_rent.entity.Apartment;
import com.example.daily_rent.entity.ApartmentType;
import com.example.daily_rent.repository.ApartmentRepository;
import com.example.daily_rent.service.AdvertService;
import com.example.daily_rent.service.ClientService;
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
    private ApartmentRepository apartmentRepository;
    @Autowired
    private AdvertService advertService;
    @Autowired
    private ClientService clientService;

    private ClientDto createClientDto() {
        return clientService.save(ClientDto.builder()
                .name(CLIENT_NAME)
                .email(CLIENT_EMAIL)
                .build());
    }

    private Apartment createApartment() {
        return apartmentRepository.save(Apartment.builder()
                .city(CITY)
                .street(STREET)
                .house(HOUSE)
                .apartmentType(APARTMENT_TYPE)
                .build());
    }

    private AdvertDtoRs createAdvert() {
        Apartment apartment = createApartment();
        AdvertCreateDto advertCreateDto = AdvertCreateDto.builder()
                .price(PRICE)
                .description(DESCRIPTION)
                .apartmentId(apartment.getId())
                .isActive(true)
                .build();
        return advertService.save(advertCreateDto);
    }

    private BookingCreateDto bookingCreateDto(AdvertDtoRs advertDtoRs, ClientDto clientDto,
                                              LocalDate start, LocalDate end) {
        return BookingCreateDto.builder()
                .advertId(advertDtoRs.getId())
                .startDate(start)
                .endDate(end)
                .client(ClientDto.builder()
                        .id(clientDto.getId())
                        .name(clientDto.getName())
                        .email(clientDto.getEmail())
                        .build())
                .build();
    }

    @Test
    @DisplayName("Успешное бронирование, при незаполненности id у клиента. Проверить что создан новый клиент")
    public void createSuccessWithoutClientId() {
        AdvertDtoRs advertDtoRs = createAdvert();
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
        AdvertDtoRs advertDtoRs = createAdvert();
        ClientDto clientDto = createClientDto();
        BookingCreateDto bookingCreateDto = bookingCreateDto(advertDtoRs, clientDto,
                NEW_START_DATE, NEW_END_DATE);

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
        AdvertDtoRs advertDtoRs = createAdvert();
        ClientDto clientDto = createClientDto();
        BookingCreateDto bookingCreateDto = bookingCreateDto(advertDtoRs, clientDto,
                START_DATE, END_DATE);

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

        BookingCreateDto overlappingBookingRequest = bookingCreateDto(advertDtoRs, clientDto,
                START_DATE, END_DATE);

        given(requestSpecification)
                .body(overlappingBookingRequest)
                .post()
                .then()
                .statusCode(HttpStatus.CONFLICT.value());
    }

    @Test
    @DisplayName("Неуспешное бронирование при существующем бронировании на эти даты (случай 4.2.3)")
    public void bookingFailsIfDatesOverlapCaseSecondSituation() {
        AdvertDtoRs advertDtoRs = createAdvert();
        ClientDto clientDto = createClientDto();
        BookingCreateDto firstBookingRequest = bookingCreateDto(advertDtoRs, clientDto,
                START_DATE_OVERLAP_CASE_1, END_DATE_OVERLAP_CASE_1);

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

        BookingCreateDto overlappingBookingRequest = bookingCreateDto(advertDtoRs, clientDto,
                START_DATE_OVERLAP_CASE_1, END_DATE_OVERLAP_CASE_1);

        given(requestSpecification)
                .body(overlappingBookingRequest)
                .post()
                .then()
                .statusCode(HttpStatus.CONFLICT.value());
    }

    @Test
    @DisplayName("Неуспешное бронирование при существующем бронировании на эти даты (случай 4.2.4)")
    public void bookingFailsIfDatesOverlapCaseThirdSituation() {
        AdvertDtoRs advertDtoRs = createAdvert();
        ClientDto clientDto = createClientDto();
        BookingCreateDto firstBookingRequest = bookingCreateDto(advertDtoRs, clientDto,
                START_DATE_OVERLAP_CASE_2, END_DATE_OVERLAP_CASE_2);

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

        BookingCreateDto overlappingBookingRequest = bookingCreateDto(advertDtoRs, clientDto,
                START_DATE_OVERLAP_CASE_2, END_DATE_OVERLAP_CASE_2);

        given(requestSpecification)
                .body(overlappingBookingRequest)
                .post()
                .then()
                .statusCode(HttpStatus.CONFLICT.value());
    }
}
