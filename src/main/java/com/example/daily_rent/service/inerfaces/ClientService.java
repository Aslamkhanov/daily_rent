package com.example.daily_rent.service.inerfaces;

import com.example.daily_rent.dto.ClientDto;
import com.example.daily_rent.entity.Client;

public interface ClientService {
    ClientDto save(ClientDto dto);
}
