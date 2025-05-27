package dev.admin.store.service.command;

import dev.admin.store.repository.StoreRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class StoreCommandServiceImpl implements StoreCommandService {
    private final StoreRepository storeRepository;


}
