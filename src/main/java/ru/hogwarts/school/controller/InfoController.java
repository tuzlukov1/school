package ru.hogwarts.school.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.hogwarts.school.service.InfoService;

import java.util.stream.Stream;

@RestController
@RequestMapping("/info")
public class InfoController {

    private InfoService infoService;

    public InfoController(InfoService infoService) {
        this.infoService = infoService;
    }

    @GetMapping("/getPort")
    public Integer getPort() {
        return infoService.getPort();
    }

    @GetMapping("/getCalculate")
    public Integer getCalculate() {
        return Stream.iterate(1, a -> a +1).limit(1_000_000).parallel().reduce(0, (a, b) -> a + b);
    }
}
