package com.example.MyHealth.controller;

import com.example.MyHealth.dto.HealthForm;
import com.example.MyHealth.entity.Health;
import com.example.MyHealth.repository.HealthRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Slf4j
@Controller
public class HealthController {
    @Autowired
    private HealthRepository healthRepository;
    @GetMapping("/health/record")
    public String newHealth() {
        return "health/record";
    }
    @PostMapping("/health/create")
    public String createHealth(HealthForm form) {
        log.info(form.toString());
        Health health = form.toEntity();
        log.info(health.toString());
        Health saved = healthRepository.save(health);
        log.info(saved.toString());
        return "redirect:/health/date" + saved.getId();
    }

    @GetMapping("/health/date{id}")
    public String show(@PathVariable Long id, Model model) {
        log.info("id = " + id);
        Health healthEntity = healthRepository.findById(id).orElse(null);
        model.addAttribute("health", healthEntity);
        return "health/show";
    }

    @GetMapping("/health/dateAll")
    public String index(Model model) {
        List<Health> healthEntityList = healthRepository.findAll();
        model.addAttribute("healthList", healthEntityList);
        return "health/index";
    }

    @GetMapping("/health/date{id}/edit")
    public String edit(@PathVariable Long id, Model model) {
        // 수정할 데이터 가져오기
        Health healthEntity = healthRepository.findById(id).orElse(null);
        // 모델에 데이터 등록하기
        model.addAttribute("health", healthEntity);
        // 뷰 페이지 설정하기
        return "health/edit";
    }
    @PostMapping("/health/update")
    public String update(HealthForm form) {
        Health healthEntity = form.toEntity();
        log.info(healthEntity.toString());
        Health target = healthRepository.findById(healthEntity.getId()).orElse(null);
        if(target != null) {
            healthRepository.save(healthEntity);
        }

        return "redirect:/health/date" + healthEntity.getId();
    }

    @GetMapping("/health/date{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes rttr) {
        log.info("삭제 요청이 들어왔습니다!!");
        Health target = healthRepository.findById(id).orElse(null);
        log.info(target.toString());
        if(target != null){
            healthRepository.delete(target);
            rttr.addFlashAttribute("msg","삭제됐습니다!!");
        }
        return "redirect:/health/record";
    }

}
