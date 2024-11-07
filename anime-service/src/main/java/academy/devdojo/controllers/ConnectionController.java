package academy.devdojo.controllers;

import external.dependency.Connection;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("v1/connections")
@Slf4j
@RequiredArgsConstructor
public class ConnectionController {
    private final Connection connectionMySql;

    @GetMapping
    public ResponseEntity<Connection> getConnections() {
        return ResponseEntity.ok(connectionMySql);
    }

}
