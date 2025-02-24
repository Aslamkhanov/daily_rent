package com.example.daily_rent.service;

import com.example.daily_rent.dto.ClientDto;
import com.example.daily_rent.entity.Client;
import com.example.daily_rent.mapper.ClientMapper;
import com.example.daily_rent.repository.ClientRepository;
import com.example.daily_rent.service.inerfaces.ClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ClientServiceImpl implements ClientService {
    private final ClientRepository clientRepository;
    private final ClientMapper clientMapper;

    @Override
    public ClientDto save(ClientDto dto) {
        Client client = clientMapper.toEntity(dto);
        Client newClient = clientRepository.save(client);
        return clientMapper.toDto(newClient);
    }
}
