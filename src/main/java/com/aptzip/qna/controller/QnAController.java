package com.aptzip.qna.controller;

import com.aptzip.qna.dto.request.AnswerRequest;
import com.aptzip.qna.dto.request.QnARegistRequest;
import com.aptzip.qna.dto.response.*;
import com.aptzip.qna.service.QnAService;
import com.aptzip.user.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/qna")
@RequiredArgsConstructor
public class QnAController {

    private final QnAService qnAService;

    @PostMapping("/regist")
    public ResponseEntity<RegistResponse> createQnA(@AuthenticationPrincipal User user, @RequestBody QnARegistRequest qnARegistRequest){
        RegistResponse result = qnAService.qnaSave(user, qnARegistRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(result);
    }

    @GetMapping("/list")
    public ResponseEntity<List<QnAListResponse>> getQnAList(){
        List<QnAListResponse> result = qnAService.getQnAList();
        return ResponseEntity.ok(result);
    }

    @GetMapping("/detail/{qnaUuid}")
    public ResponseEntity<DetailResponse> getQnADetail(@AuthenticationPrincipal User user, @PathVariable("qnaUuid") String qnaUuid){
        DetailResponse detailResponse = qnAService.qnaDetail(user, qnaUuid);
        return ResponseEntity.ok(detailResponse);
    }

    @DeleteMapping("/delete/{qnaUuid}")
    public ResponseEntity<Void> deleteQnA(@AuthenticationPrincipal User user, @PathVariable("qnaUuid") String qnaUuid){
        qnAService.deleteQnA(user, qnaUuid);
        return ResponseEntity.ok().build();
    }

    @PostMapping("/answerRegist/{qnaUuid}")
    public ResponseEntity<Void> createAnswer(@AuthenticationPrincipal User user, @PathVariable("qnaUuid") String qnaUuid, @RequestBody AnswerRequest answerRequest){
        qnAService.answerSave(user, qnaUuid, answerRequest);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping("/answerDelete/{qnaAnsUuid}")
    public ResponseEntity<Void> deleteAnswer(@PathVariable("qnaAnsUuid") String qnaAnsUuid){
        qnAService.deleteAnswer(qnaAnsUuid);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/userList")
    public ResponseEntity<List<QnAListResponse>> getUserQnAList(@AuthenticationPrincipal User user){
        List<QnAListResponse> result = qnAService.getUserQnAList(user);
        return ResponseEntity.ok(result);
    }
}
