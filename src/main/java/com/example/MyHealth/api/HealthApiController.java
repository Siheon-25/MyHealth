package com.example.MyHealth.api;

import com.example.MyHealth.dto.HealthForm;
import com.example.MyHealth.entity.Health;
import com.example.MyHealth.repository.HealthRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
public class HealthApiController {
    @Autowired
    private HealthRepository healthRepository;
    // GET
    @GetMapping("/api/health")
    public List<Health> index() {
        return healthRepository.findAll();
    }
    @GetMapping("/api/health/date{id}")
    public Health show(@PathVariable Long id) {
        return healthRepository.findById(id).orElse(null);
    }
    // POST
    @PostMapping("/api/health")
    public Health create(@RequestBody HealthForm dto) {
        Health health = dto.toEntity();
        return healthRepository.save(health);
    }
    // PATCH
    @PatchMapping("/api/health/date{id}")
    public ResponseEntity<Health> update(@PathVariable Long id,
                         @RequestBody HealthForm dto) {
        // 1. DTO -> 엔티티 변환하기
        Health health = dto.toEntity();
        log.info("id: {}, health: {}", id, health.toString());
        // 2. 타깃 조회하기
        Health target = healthRepository.findById(id).orElse(null);
        // 3. 잘못된 요청 처리하기
        if(target == null || id != health.getId()) {
            log.info("잘못된 요청! id: {}, health: {}", id, health.toString());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
        // 4. 업데이트 및 정상 응답(200)하기
        target.patch(health);
        Health updated = healthRepository.save(target);
        return ResponseEntity.status(HttpStatus.OK).body(updated);
    }
    // DELETE
    @DeleteMapping("/api/health/date{id}")
    public ResponseEntity<Health> delete(@PathVariable Long id) {
        // 1. 대상 찾기
        Health target = healthRepository.findById(id).orElse(null);
        // 2. 잘못된 요청 처리하기
        if(target == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
        // 3. 대상 삭제하기
        healthRepository.delete(target);
        return ResponseEntity.status(HttpStatus.OK).body(null);

    }
}
