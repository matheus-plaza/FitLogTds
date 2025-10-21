package io.github.matheusplaza.fitlogtds.controller;

import io.github.matheusplaza.fitlogtds.controller.dto.DashboardDTO;
import io.github.matheusplaza.fitlogtds.service.DashboardService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/dashboard")
public class DashboardController {

    private final DashboardService dashboardService;

    public DashboardController(DashboardService dashboardService) {
        this.dashboardService = dashboardService;
    }

    //TODO: Temporariamente, vamos passar o ID do usuário pela URL.
    // Quando eu implementar a segurança, este ID virá do usuário autenticado.
    @GetMapping("/user/{userId}")
    public ResponseEntity<DashboardDTO> getDashboard(@PathVariable Long userId) {
        DashboardDTO dashboardData = dashboardService.getDashboardData(userId);
        return ResponseEntity.ok(dashboardData);
    }
}