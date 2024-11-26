package com.alatoo.reshu_ort.controllers;

import com.alatoo.reshu_ort.dto.ResultDTO;
import com.alatoo.reshu_ort.services.result.ResultService;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/v1")
public class ResultController {
    private final ResultService resultService;

    private final String RESULT_PATH = "/results";
    private final String ID_PATH = "/{id}";

    public ResultController(ResultService resultService) {
        this.resultService = resultService;
    }

    @GetMapping(RESULT_PATH)
    public List<ResultDTO> getAllResults() {
        return resultService.findAllUserResults();
    }

    @GetMapping(RESULT_PATH + "/test" + ID_PATH)
    public List<ResultDTO> getAllResultsOfTest(@PathVariable Long id) {
        return resultService.findAllResultsByTestId(id);
    }

    @GetMapping(RESULT_PATH + ID_PATH)
    public ResultDTO getResultById(@PathVariable Long id) {
        return resultService.getResultById(id);
    }

    @PostMapping(RESULT_PATH)
    public ResultDTO saveResult(@RequestBody ResultDTO dto) {
        return resultService.saveResult(dto);
    }

    @PutMapping(RESULT_PATH + ID_PATH)
    public ResultDTO updateResult(@PathVariable Long id, @RequestBody ResultDTO resultDTO) {
        resultService.getResultById(id);
        resultDTO.setTestId(id);
        return resultService.saveResult(resultDTO);
    }

    @DeleteMapping(RESULT_PATH + ID_PATH)
    public void deleteResult(@PathVariable Long id) {
        resultService.deleteResult(id);
    }
}
