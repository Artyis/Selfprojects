package ru.gor.app.controllers.restControllers;

import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.*;
import ru.gor.app.models.dto.criticDtos.CreateCriticDto;
import ru.gor.app.models.entity.Critic;
import ru.gor.app.service.CriticService;

import javax.validation.Valid;
import java.util.List;
@RestController
@RequestMapping("/critics")
@Api(value = "critic", tags = "API")
public class CriticRestController {
    private CriticService criticService;

    public CriticRestController(CriticService criticService) {
        this.criticService = criticService;
    }

    @GetMapping
    public List<Critic> allCritics() {
        return criticService.getCriticDto().getCriticList();
    }

    @GetMapping("/{id}")
    public CreateCriticDto getCriticById(@PathVariable("id") int id) {
        return criticService.getCriticDto(id);
    }

    @PostMapping
    public CreateCriticDto save(@RequestBody @Valid CreateCriticDto createDto) {
        criticService.save(createDto);

        return createDto;
    }

    @PutMapping("/{id}")
    public CreateCriticDto editCritic(@PathVariable int id, @RequestBody @Valid CreateCriticDto criticDto) {
        criticService.editCritic(criticDto, id);

        return criticDto;
    }

    @DeleteMapping("/{id}")
    public void deleteCritic(@PathVariable("id") int id) {
        criticService.deleteCritic(id);
    }

}
