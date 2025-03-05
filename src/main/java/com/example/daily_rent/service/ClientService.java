package com.example.daily_rent.service;

import com.example.daily_rent.dto.ClientDto;

public interface ClientService {
    ClientDto save(ClientDto dto);

    void delete(Integer id);
}
