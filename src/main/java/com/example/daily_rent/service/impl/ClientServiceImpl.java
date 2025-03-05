package com.example.daily_rent.service.impl;

import com.example.daily_rent.dto.ClientDto;
import com.example.daily_rent.entity.Client;
import com.example.daily_rent.mapper.ClientMapper;
import com.example.daily_rent.repository.BookingRepository;
import com.example.daily_rent.repository.ClientRepository;
import com.example.daily_rent.service.ClientService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class ClientServiceImpl implements ClientService {
    private final ClientRepository clientRepository;
    private final ClientMapper clientMapper;
    private final BookingRepository bookingRepository;

    @Override
    public ClientDto save(ClientDto dto) {
        Client client = clientMapper.toEntity(dto);
        clientRepository.save(client);
        return clientMapper.toDto(client);
    }

    @Override
    public void delete(Integer id) {
        bookingRepository.deleteAllByClientId(id);
        clientRepository.deleteById(id);
    }
}
