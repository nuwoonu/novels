package com.example.novels.novel.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.novels.member.dto.RegisterDTO;
import com.example.novels.novel.service.NovelMemberService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@RequiredArgsConstructor
@RequestMapping("/api/member")
@RestController
public class MemberController {

    private final NovelMemberService novelMemberService;

    @PostMapping("/register")
    public ResponseEntity<String> register(@Valid @RequestBody RegisterDTO dto) {
        log.info("회원가입 요청 {}", dto.getEmail());
        try {
            novelMemberService.register(dto);
            return ResponseEntity.ok("회원가입 성공");
        } catch (IllegalStateException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
