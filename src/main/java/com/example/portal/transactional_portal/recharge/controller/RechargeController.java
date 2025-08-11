package com.example.portal.transactional_portal.recharge.controller;

import com.example.portal.transactional_portal.integration.puntored.dto.SuppliersResponsePuntored;
import com.example.portal.transactional_portal.recharge.dto.RechargeRequest;
import com.example.portal.transactional_portal.recharge.dto.RechargeResponse;
import com.example.portal.transactional_portal.recharge.service.RechargeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/recharge")
public class RechargeController {

    private final RechargeService rechargeService;

    public RechargeController(RechargeService rechargeService) {
        this.rechargeService = rechargeService;
    }

    @Operation(description = "Recharge")
    @ApiResponse(responseCode = "200", description = "success")
    @PostMapping("/")
    public ResponseEntity<RechargeResponse> recharge(@Valid @RequestBody RechargeRequest recharge) {
        RechargeResponse rechargeResponse = rechargeService.createRecharge(recharge);
        return new ResponseEntity<>(rechargeResponse, HttpStatus.OK);
    }

    @Operation(description = "Get recharge by ID")
    @ApiResponse(responseCode = "200", description = "success")
    @GetMapping("/{id}")
    public ResponseEntity<RechargeResponse> getRecharge(@Parameter(description = "uuid of recharge", required = true) @PathVariable("id") UUID rechargeId){
        RechargeResponse rechargeResponse = rechargeService.getRechargeById(rechargeId);
        return new ResponseEntity<>(rechargeResponse, HttpStatus.OK);
    }

    @Operation(description = "Get suppliers list")
    @ApiResponse(responseCode = "200", description = "success")
    @GetMapping("/suppliers")
    public ResponseEntity<List<SuppliersResponsePuntored>> getSuppliers(){
        List<SuppliersResponsePuntored> rechargeResponse = rechargeService.getSuppliers();
        return new ResponseEntity<>(rechargeResponse, HttpStatus.OK);
    }
}
